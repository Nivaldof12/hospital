package com.hospital.controller;

import com.hospital.dto.ConsultaRequest;
import com.hospital.model.Consulta;
import com.hospital.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<Consulta> cadastrar(@Valid @RequestBody ConsultaRequest request) {
        Consulta salva = consultaService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }
}
