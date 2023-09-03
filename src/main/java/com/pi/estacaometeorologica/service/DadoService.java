package com.pi.estacaometeorologica.service;

import com.pi.estacaometeorologica.repository.DadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DadoService {

    @Autowired
    private DadoRepository repository;

}
