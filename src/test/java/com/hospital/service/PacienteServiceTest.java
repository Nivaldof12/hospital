package com.hospital.service;

import com.hospital.exception.ResourceNotFoundException;
import com.hospital.model.Paciente;
import com.hospital.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void deveCadastrarPaciente() {
        Paciente paciente = new Paciente("João Silva", "12345678901", LocalDate.of(1985, 3, 15), "11999990001");
        Paciente salvo = new Paciente("João Silva", "12345678901", LocalDate.of(1985, 3, 15), "11999990001");
        salvo.setId(1L);

        when(pacienteRepository.save(any(Paciente.class))).thenReturn(salvo);

        Paciente resultado = pacienteService.cadastrar(paciente);

        assertNotNull(resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        verify(pacienteRepository).save(paciente);
    }

    @Test
    void deveBuscarPacientePorId() {
        Paciente paciente = new Paciente("Maria Oliveira", "98765432100", LocalDate.of(1990, 7, 22), "11999990002");
        paciente.setId(1L);

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Paciente resultado = pacienteService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Maria Oliveira", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoPacienteNaoExistir() {
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPorId(99L));
    }

    @Test
    void deveRemoverPaciente() {
        when(pacienteRepository.existsById(1L)).thenReturn(true);

        pacienteService.remover(1L);

        verify(pacienteRepository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoRemoverPacienteInexistente() {
        when(pacienteRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.remover(99L));
        verify(pacienteRepository, never()).deleteById(99L);
    }
}
