package com.pi.estacaometeorologica.dto.dado;

import com.pi.estacaometeorologica.entity.Dado;

import java.time.LocalDateTime;

public record DadoMediaDiaria(LocalDateTime dataDia, Float pressaoMedia, Float altitudeMedia, Float temperaturaMedia, Float umidadeMedia) {
        public DadoMediaDiaria(DadoDetalhamento dado){
            this(dado.dataRegistro(), dado.pressao(), dado.altitude(), dado.temperatura(), dado.umidade());
        }
}
