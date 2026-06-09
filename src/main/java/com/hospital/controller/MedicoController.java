package com.hospital.controller;

import com.hospital.dto.MedicoRankingDTO;
import com.hospital.model.Medico;
import com.hospital.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<Medico> cadastrar(@Valid @RequestBody Medico medico) {
        Medico salvo = medicoService.cadastrar(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        return ResponseEntity.ok(medicoService.listar());
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<MedicoRankingDTO>> listarPorQuantidadeConsultas() {
        return ResponseEntity.ok(medicoService.listarPorQuantidadeConsultas());
    }
}
