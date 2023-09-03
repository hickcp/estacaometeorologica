package com.pi.estacaometeorologica.dto.dado;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadoCadastro(
        @NotNull
        Float temperatura,

        @NotNull
        Float umidade,

        @NotNull
        Float pressao,

        @NotNull
        Float altitude,

        @NotNull
        Long idIot
                           ) {

}
