package com.pi.estacaometeorologica.infra.validacoes.dado;

import com.pi.estacaometeorologica.entity.Dado;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;

public class ValidaDado {
    public static void validaValor(Dado dados) throws DadoMenorQueZeroException{
        StringBuffer message = new StringBuffer("Dado não pode ser menor que zero: \n");
        if(dados.getAltitude() < 0){
            message.append("altitude, ");
        }

        if(dados.getPressao() < 0){
            message.append("pressao, ");
        }

        if(dados.getUmidade() < 0){
            message.append("umidade, ");
        }


        message.delete(message.length() -2,message.length());
        message.append(".");

        if(dados.getAltitude() < 0 || dados.getUmidade() < 0 || dados.getPressao() < 0) {
            throw new DadoMenorQueZeroException(message.toString());
        }


    }
}
