package com.pi.estacaometeorologica.service;

import com.pi.estacaometeorologica.dto.dado.DadoCadastro;
import com.pi.estacaometeorologica.dto.dado.DadoDetalhamento;
import com.pi.estacaometeorologica.dto.dado.DadoParaMedia;
import com.pi.estacaometeorologica.entity.Dado;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoNaoEncontradoNaDataException;
import com.pi.estacaometeorologica.infra.exceptions.generic.DataFuturaInformadaException;
import com.pi.estacaometeorologica.infra.exceptions.iot.IotNaoCadastradoException;
import com.pi.estacaometeorologica.infra.validacoes.dado.ValidaDado;
import com.pi.estacaometeorologica.infra.validacoes.iot.ValidaIot;
import com.pi.estacaometeorologica.repository.DadoRepository;
import com.pi.estacaometeorologica.repository.IotRepository;
import com.pi.estacaometeorologica.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DadoService {

    @Autowired
    private DadoRepository repository;

    @Autowired
    private IotRepository iotRepository;

    public DadoDetalhamento cadastrarDado(DadoCadastro dadoCadastro)
            throws DadoMenorQueZeroException, IotNaoCadastradoException {

        Dado dados = new Dado(dadoCadastro);
        ValidaDado.validaValor(dados);
        var iot = iotRepository.getIotById(dadoCadastro.idIot());
        ValidaIot.existeIot(iot);
        repository.save(dados);

        return new DadoDetalhamento(dados, iot);
    }

    public DadoDetalhamento getDado(Long idIot) throws DadoNaoEncontradoNaDataException {
        List<Dado> dados = repository.getDado(idIot, PageRequest.of(0, 1));
        if(dados == null){
            throw new DadoNaoEncontradoNaDataException("Não há dados para a data informada");
        }
        Dado dado = dados.get(0);
        return new DadoDetalhamento(dado, dado.getIot());
    }

    public List<DadoParaMedia> getMediaDiaria(LocalDate dataInicio, LocalDate dataFim, Long idIot)
            throws DadoNaoEncontradoNaDataException, DataFuturaInformadaException {

        if(dataFim.isAfter(LocalDate.now())){
            throw new DataFuturaInformadaException();
        }
        List<Dado> dadosParaMedia = repository.getDados(dataInicio, dataFim, idIot);

        if(dadosParaMedia.isEmpty() || dadosParaMedia == null){
            throw new DadoNaoEncontradoNaDataException("Não há dados para a data informada");
        }
        List<DadoParaMedia> dadoParaMediaDiaria = fazMediaDado(dadosParaMedia, Utils.MEDIA_DIARIA);

        return dadoParaMediaDiaria;
    }


    public List<DadoParaMedia> getMediaMensal(LocalDate dataInicio, LocalDate dataFim, Long idIot)
            throws DadoNaoEncontradoNaDataException, DataFuturaInformadaException{

        if(dataFim.isAfter(LocalDate.now())){
            throw new DataFuturaInformadaException();
        }
        List<Dado> dadosParaMedia = repository.getDados(dataInicio, dataFim, idIot);

        if(dadosParaMedia.isEmpty() || dadosParaMedia == null){
            throw new DadoNaoEncontradoNaDataException("Não há dados para a data informada");
        }

        List<DadoParaMedia> dadoParaMediaMensal = fazMediaDado(dadosParaMedia, Utils.MEDIA_MENSAL);
        return dadoParaMediaMensal;
    }

    public List<DadoParaMedia> getMediaAnual(LocalDate dataInicio, LocalDate dataFim, Long idIot)
            throws DadoNaoEncontradoNaDataException, DataFuturaInformadaException{

        if(dataFim.isAfter(LocalDate.now())){
            throw new DataFuturaInformadaException();
        }
        List<Dado> dadosParaMedia = repository.getDados(dataInicio, dataFim, idIot);

        if(dadosParaMedia.isEmpty() || dadosParaMedia == null){
            throw new DadoNaoEncontradoNaDataException("Não há dados para a data informada");
        }

        List<DadoParaMedia> dadoParaMediaAnual = fazMediaDado(dadosParaMedia, Utils.MEDIA_MENSAL);
        return dadoParaMediaAnual;
    }



    private List<DadoParaMedia> fazMediaDado(List<Dado> dadosParaMedia, String tipoMedia){
        int ano = 0;
        int mes = 0;
        LocalDate data = null;
        List<DadoDetalhamento> dadoDetalhamentosList = dadosParaMedia.stream().map(p -> new DadoDetalhamento(p, p.getIot())).toList();

        Map<LocalDate, List<DadoDetalhamento>> dadosPorTipoMedia = new HashMap<>();

        for(DadoDetalhamento dado : dadoDetalhamentosList){
            if(tipoMedia.equals(Utils.MEDIA_MENSAL)) {
                 mes = dado.dataRegistro().getMonthValue();
                 ano = dado.dataRegistro().getYear();
                 data = LocalDate.of(ano, mes, 1);
            }
            if(tipoMedia.equals(Utils.MEDIA_DIARIA)){
                 data = dado.dataRegistro();
            }
            if(tipoMedia.equals(Utils.MEDIA_ANUAL)){
                 ano = dado.dataRegistro().getYear();
                 data = LocalDate.of(ano, 1, 1);
            }

            if(!dadosPorTipoMedia.containsKey(data)){
                List<DadoDetalhamento> registros = new ArrayList<>(){{
                    add(dado);
                }};
                dadosPorTipoMedia.put(data, registros);
            }else{
                List<DadoDetalhamento> registros = dadosPorTipoMedia.get(data);
                registros.add(dado);
            }
        }

        List<DadoParaMedia> dadoParaMediaArrayList = new ArrayList<>();
        for(Map.Entry<LocalDate, List<DadoDetalhamento>> entry : dadosPorTipoMedia.entrySet()){
            List<DadoDetalhamento> dados = entry.getValue();
            Float temperatura = 0f;
            Float umidade = 0f;
            Float pressao = 0f;
            Float altitude = 0f;
            for(DadoDetalhamento dado : entry.getValue()){
                temperatura += dado.temperatura();
                umidade += dado.umidade();
                pressao += dado.pressao();
                altitude += dado.altitude();
            }
            temperatura = temperatura / dados.size();
            umidade = umidade / dados.size();
            pressao = pressao / dados.size();
            altitude = altitude / dados.size();
            DadoParaMedia dadoParaMedia = new DadoParaMedia(entry.getKey(), pressao, altitude, temperatura, umidade);
            dadoParaMediaArrayList.add(dadoParaMedia);
        }


        return dadoParaMediaArrayList;
    }

}
