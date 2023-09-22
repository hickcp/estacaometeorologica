package com.pi.estacaometeorologica.controller;

import com.pi.estacaometeorologica.dto.dado.DadoCadastro;
import com.pi.estacaometeorologica.dto.dado.DadoMediaDiaria;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;
import com.pi.estacaometeorologica.infra.exceptions.iot.IotNaoCadastradoException;
import com.pi.estacaometeorologica.service.DadoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("dados")
public class DadoController {

    @Autowired
    private DadoService service;

    @Transactional
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadoCadastro dadoCadastro)
            throws DadoMenorQueZeroException, IotNaoCadastradoException {

        return ResponseEntity.ok(service.cadastrarDado(dadoCadastro));
    }

    @GetMapping("/diaria")
    public ResponseEntity<List<DadoMediaDiaria>> getMediaDiaria(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam Long idIot){
        return ResponseEntity.ok(service.getMediaDiaria(dataInicio, dataFim, idIot));
    }
}
