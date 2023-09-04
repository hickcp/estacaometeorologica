package com.pi.estacaometeorologica.infra.exceptions.dado;

public class DadoMenorQueZeroException extends Exception{
    public DadoMenorQueZeroException(String message){
        super(message);
    }
}
