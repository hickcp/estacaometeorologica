package com.pi.estacaometeorologica.infra.validacoes.iot;

import com.pi.estacaometeorologica.entity.Iot;
import com.pi.estacaometeorologica.infra.exceptions.iot.IotNaoCadastradoException;

public class ValidaIot {

    public static void existeIot(Iot iot) throws IotNaoCadastradoException {
        if(iot == null){
            throw new IotNaoCadastradoException();
        }
    }
}
