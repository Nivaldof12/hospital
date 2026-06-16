package com.hospital.config;

import com.hospital.model.Medico;
import com.hospital.model.Paciente;
import com.hospital.repository.MedicoRepository;
import com.hospital.repository.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public DataLoader(MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void run(String... args) {
        if (medicoRepository.count() == 0) {
            medicoRepository.save(new Medico("Dr. Carlos Cardoso", "CRM12345", "Cardiologista"));
            medicoRepository.save(new Medico("Dra. Ana Souza", "CRM67890", "Ortopedista"));
        }

        if (pacienteRepository.count() == 0) {
            pacienteRepository.save(new Paciente(
                    "João Silva",
                    "12345678901",
                    LocalDate.of(1985, 3, 15),
                    "11999990001"
            ));
            pacienteRepository.save(new Paciente(
                    "Maria Oliveira",
                    "98765432100",
                    LocalDate.of(1990, 7, 22),
                    "11999990002"
            ));
        }
    }
}
