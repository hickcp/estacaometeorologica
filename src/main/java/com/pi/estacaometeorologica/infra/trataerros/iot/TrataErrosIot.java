package com.pi.estacaometeorologica.infra.trataerros.iot;

import com.pi.estacaometeorologica.infra.exceptions.iot.IotNaoCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrataErrosIot {

    @ExceptionHandler(IotNaoCadastradoException.class)
    public ResponseEntity iotNaoCadastrado404(IotNaoCadastradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
