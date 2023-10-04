package com.pi.estacaometeorologica.service;

import com.pi.estacaometeorologica.dto.dado.DadoCadastro;
import com.pi.estacaometeorologica.dto.dado.DadoDetalhamento;
import com.pi.estacaometeorologica.entity.Dado;
import com.pi.estacaometeorologica.entity.Iot;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;
import com.pi.estacaometeorologica.infra.exceptions.iot.IotNaoCadastradoException;
import com.pi.estacaometeorologica.repository.DadoRepository;
import com.pi.estacaometeorologica.repository.IotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DadoServiceTest {

    @InjectMocks
    DadoService service;

    @Mock
    DadoRepository dadoRepository;

    @Mock
    IotRepository iotRepository;


    @Test
    void cadastrarDado() {
        DadoCadastro dadoCadastro = new DadoCadastro(1f,1f,1f,1f,1L);
        Iot iot = new Iot(1L,"Teste", null);
        Mockito.when(iotRepository.getIotById(dadoCadastro.idIot())).thenReturn(iot);
        DadoDetalhamento dadoDetalhamento;
        try {
            dadoDetalhamento = service.cadastrarDado(dadoCadastro);
        } catch (DadoMenorQueZeroException e) {
            throw new RuntimeException(e);
        } catch (IotNaoCadastradoException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(dadoCadastro.altitude(), dadoDetalhamento.altitude());
        Assertions.assertEquals(dadoCadastro.pressao(), dadoDetalhamento.pressao());
        Assertions.assertEquals(dadoCadastro.temperatura(), dadoDetalhamento.temperatura());
        Assertions.assertEquals(dadoCadastro.umidade(), dadoDetalhamento.umidade());
        Assertions.assertEquals(iot.getNome(), dadoDetalhamento.nomeIot());
        


    }
}