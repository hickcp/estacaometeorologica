package com.pi.estacaometeorologica.service;

import com.pi.estacaometeorologica.dto.dado.DadoCadastro;
import com.pi.estacaometeorologica.dto.dado.DadoDetalhamento;
import com.pi.estacaometeorologica.dto.dado.DadoMediaDiaria;
import com.pi.estacaometeorologica.entity.Dado;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;
import com.pi.estacaometeorologica.infra.exceptions.iot.IotNaoCadastradoException;
import com.pi.estacaometeorologica.infra.validacoes.dado.ValidaDado;
import com.pi.estacaometeorologica.infra.validacoes.iot.ValidaIot;
import com.pi.estacaometeorologica.repository.DadoRepository;
import com.pi.estacaometeorologica.repository.IotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<DadoMediaDiaria> getMediaDiaria(LocalDateTime dataInicio, LocalDateTime dataFim, Long idIot) {
        List<Dado> dadosParaMedia = repository.getDados(dataInicio, dataFim, idIot);
        List<DadoDetalhamento> dadoDetalhamentosList = dadosParaMedia.stream().map(p -> new DadoDetalhamento(p, p.getIot())).toList();
        List<DadoMediaDiaria> dadoMediaDiarias = new ArrayList<DadoMediaDiaria>();

        int dia = dataInicio.getDayOfMonth();
        int j = 0;
        int contador = 0;
        Float somaPressao = 0f;
        Float somaUmidade = 0f;
        Float somaAltitude = 0f;
        Float somaTemperatura = 0f;

        while (dia <= dataFim.getDayOfMonth()) {
            if (j < dadoDetalhamentosList.size()) {
                if (dia == dadoDetalhamentosList.get(j).dataRegistro().getDayOfMonth()) {
                    somaPressao += dadoDetalhamentosList.get(j).pressao();
                    somaAltitude += dadoDetalhamentosList.get(j).altitude();
                    somaUmidade += dadoDetalhamentosList.get(j).umidade();
                    somaTemperatura += dadoDetalhamentosList.get(j).temperatura();

                    j++;
                    contador++;
                }
                else {
                    if(somaPressao != 0f && somaAltitude != 0f && somaUmidade != 0f && somaTemperatura != 0f){
                        somaPressao = somaPressao / contador;
                        somaAltitude = somaAltitude / contador;
                        somaUmidade = somaUmidade / contador;
                        somaTemperatura = somaTemperatura / contador;

                        dadoMediaDiarias.add(new DadoMediaDiaria(dadoDetalhamentosList.get(j).dataRegistro(), somaPressao, somaAltitude, somaUmidade, somaTemperatura));

                        contador = 0;
                    }
                    somaPressao = 0f;
                    somaAltitude = 0f;
                    somaUmidade = 0f;
                    somaTemperatura = 0f;
                    contador =  0;
                    dia++;
                }
            }else{
                if(somaPressao != 0f && somaAltitude != 0f && somaUmidade != 0f && somaTemperatura != 0f){

                    somaPressao = somaPressao / contador;
                    somaAltitude = somaAltitude / contador;
                    somaUmidade = somaUmidade / contador;
                    somaTemperatura = somaTemperatura / contador;

                    dadoMediaDiarias.add(new DadoMediaDiaria(dadoDetalhamentosList.get(j - 1).dataRegistro(), somaPressao, somaAltitude, somaUmidade, somaTemperatura));

                    contador = 0;
                }
                break;
            }
        }
        return dadoMediaDiarias;
    }

}
