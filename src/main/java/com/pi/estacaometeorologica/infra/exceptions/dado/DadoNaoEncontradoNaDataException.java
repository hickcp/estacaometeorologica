package com.pi.estacaometeorologica.infra.exceptions.dado;

public class DadoNaoEncontradoNaDataException extends Exception{
    public DadoNaoEncontradoNaDataException(String message){
        super(message);
    }
}
