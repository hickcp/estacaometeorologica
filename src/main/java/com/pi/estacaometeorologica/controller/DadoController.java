package com.pi.estacaometeorologica.controller;

import com.pi.estacaometeorologica.dto.dado.DadoCadastro;
import com.pi.estacaometeorologica.dto.dado.DadoDetalhamento;
import com.pi.estacaometeorologica.dto.dado.DadoParaMedia;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoMenorQueZeroException;
import com.pi.estacaometeorologica.infra.exceptions.dado.DadoNaoEncontradoNaDataException;
import com.pi.estacaometeorologica.infra.exceptions.generic.DataFuturaInformadaException;
import com.pi.estacaometeorologica.infra.exceptions.iot.IotNaoCadastradoException;
import com.pi.estacaometeorologica.service.DadoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<DadoParaMedia>> getMediaDiaria(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataFim,
            @RequestParam Long idIot) throws DadoNaoEncontradoNaDataException, DataFuturaInformadaException {
        return ResponseEntity.ok(service.getMediaDiaria(dataInicio, dataFim, idIot));
    }

    @GetMapping("/mensal")
    public ResponseEntity<List<DadoParaMedia>> getMediaMensal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataFim,
            @RequestParam Long idIot) throws DadoNaoEncontradoNaDataException, DataFuturaInformadaException {
        return ResponseEntity.ok(service.getMediaMensal(dataInicio, dataFim, idIot));
    }

    @GetMapping("/anual")
    public ResponseEntity<List<DadoParaMedia>> getMediaAnual(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataFim,
            @RequestParam Long idIot) throws DadoNaoEncontradoNaDataException, DataFuturaInformadaException {
        return ResponseEntity.ok(service.getMediaAnual(dataInicio, dataFim, idIot));
    }

    @GetMapping("/atual/{idIot}")
    public ResponseEntity<DadoDetalhamento> getDado(@PathVariable Long idIot) throws DadoNaoEncontradoNaDataException {
        return ResponseEntity.ok(service.getDado(idIot));
    }
}
