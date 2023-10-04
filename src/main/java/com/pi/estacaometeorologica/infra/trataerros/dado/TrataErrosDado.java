package com.pi.estacaometeorologica.infra.trataerros.dado;

import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoNaoEncontradoNaDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrataErrosDado {

    @ExceptionHandler(DadoMenorQueZeroException.class)
    public ResponseEntity trataDadoMenorQueZero(DadoMenorQueZeroException ex){
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DadoNaoEncontradoNaDataException.class)
    public ResponseEntity trataDadoNaoEncontradoNaData(DadoNaoEncontradoNaDataException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
