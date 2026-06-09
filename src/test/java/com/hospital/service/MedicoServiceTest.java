package com.hospital.service;

import com.hospital.model.Medico;
import com.hospital.repository.ConsultaRepository;
import com.hospital.repository.MedicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private MedicoService medicoService;

    @Test
    void deveCadastrarMedico() {
        Medico medico = new Medico("Dr. Carlos", "CRM11111", "Cardiologista");
        Medico salvo = new Medico("Dr. Carlos", "CRM11111", "Cardiologista");
        salvo.setId(1L);

        when(medicoRepository.save(any(Medico.class))).thenReturn(salvo);

        Medico resultado = medicoService.cadastrar(medico);

        assertNotNull(resultado.getId());
        assertEquals("Cardiologista", resultado.getEspecialidade());
        verify(medicoRepository).save(medico);
    }

    @Test
    void deveListarMedicos() {
        Medico medico = new Medico("Dra. Ana", "CRM22222", "Ortopedista");
        medico.setId(1L);

        when(medicoRepository.findAll()).thenReturn(List.of(medico));

        List<Medico> resultado = medicoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Ortopedista", resultado.get(0).getEspecialidade());
    }
}
