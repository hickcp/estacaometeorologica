package com.pi.estacaometeorologica.infra.exceptions.generic;

public class DataFuturaInformadaException extends Exception{
    public DataFuturaInformadaException(){
        super("Data futura não é permitida!");
    }
}
