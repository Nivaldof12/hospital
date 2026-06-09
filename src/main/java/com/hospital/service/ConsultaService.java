package com.hospital.service;

import com.hospital.dto.ConsultaRequest;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.model.Consulta;
import com.hospital.model.Medico;
import com.hospital.model.Paciente;
import com.hospital.repository.ConsultaRepository;
import com.hospital.repository.MedicoRepository;
import com.hospital.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public Consulta cadastrar(ConsultaRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com id: " + request.getPacienteId()));

        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Médico não encontrado com id: " + request.getMedicoId()));

        Consulta consulta = new Consulta(
                request.getDataConsulta(),
                request.getObservacoes(),
                paciente,
                medico
        );

        return consultaRepository.save(consulta);
    }
}
