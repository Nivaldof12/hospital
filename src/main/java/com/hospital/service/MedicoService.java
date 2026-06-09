package com.hospital.service;

import com.hospital.dto.MedicoRankingDTO;
import com.hospital.model.Medico;
import com.hospital.repository.ConsultaRepository;
import com.hospital.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    public MedicoService(MedicoRepository medicoRepository, ConsultaRepository consultaRepository) {
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
    }

    public Medico cadastrar(Medico medico) {
        return medicoRepository.save(medico);
    }

    public List<Medico> listar() {
        return medicoRepository.findAll();
    }

    public List<MedicoRankingDTO> listarPorQuantidadeConsultas() {
        return medicoRepository.findAll().stream()
                .map(medico -> new MedicoRankingDTO(medico, consultaRepository.countByMedicoId(medico.getId())))
                .sorted(Comparator.comparingLong(MedicoRankingDTO::getTotalConsultas).reversed())
                .toList();
    }
}
