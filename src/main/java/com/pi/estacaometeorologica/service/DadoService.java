package com.pi.estacaometeorologica.service;

import com.pi.estacaometeorologica.dto.dado.DadoCadastro;
import com.pi.estacaometeorologica.dto.dado.DadoDetalhamento;
import com.pi.estacaometeorologica.entity.Dado;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;
import com.pi.estacaometeorologica.infra.validacoes.dado.ValidaDado;
import com.pi.estacaometeorologica.repository.DadoRepository;
import com.pi.estacaometeorologica.repository.IotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DadoService {

    @Autowired
    private DadoRepository repository;

    @Autowired
    private IotRepository iotRepository;


    public DadoDetalhamento cadastrarDado(DadoCadastro dadoCadastro) throws DadoMenorQueZeroException {
        Dado dados = new Dado(dadoCadastro);
        ValidaDado.validaValor(dados);
        repository.save(dados);
        var iot = iotRepository.getReferenceById(dadoCadastro.idIot());

        return new DadoDetalhamento(dados, iot);
    }

}
