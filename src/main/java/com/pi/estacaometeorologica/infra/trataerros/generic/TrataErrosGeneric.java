package com.pi.estacaometeorologica.infra.trataerros.generic;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrataErrosGeneric {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity trataErro400(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados informados inválido!"); }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity trataErro404(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dado ou Iot não encontrado!");
    }
}
