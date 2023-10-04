package com.pi.estacaometeorologica.dto.dado;

import java.time.LocalDate;

public record DadoParaMedia(LocalDate dataDia, Float pressaoMedia, Float altitudeMedia, Float temperaturaMedia, Float umidadeMedia) {
        public DadoParaMedia(DadoDetalhamento dado){
            this(dado.dataRegistro(), dado.pressao(), dado.altitude(), dado.temperatura(), dado.umidade());
        }
}
