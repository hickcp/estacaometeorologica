package com.pi.estacaometeorologica.dto.dado;

import com.pi.estacaometeorologica.entity.Dado;
import com.pi.estacaometeorologica.entity.Iot;

import java.time.LocalDateTime;

public record DadoDetalhamento(Long id, Float altitude, Float umidade, Float pressao, Float temperatura,
                               LocalDateTime dataRegistro, String nomeIot) {
    public DadoDetalhamento(Dado dado, Iot iot){
        this(dado.getId(), dado.getAltitude(), dado.getUmidade(), dado.getPressao(),
                dado.getTemperatura(), dado.getDataRegistro(),iot.getNome());
    }
}
