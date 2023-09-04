package com.pi.estacaometeorologica.infra.exceptions.iot;

public class IotNaoCadastradoException extends Exception{
    public IotNaoCadastradoException(){
        super("Id do Iot não foi cadastrado/encontrado!");
    }
}
