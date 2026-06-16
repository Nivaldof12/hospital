package com.hospital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.model.Paciente;
import com.hospital.repository.PacienteRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteControllerIntegrationTest {

    private static Long pacienteId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    @Order(1)
    void teste1_deveCadastrarPacienteAtravesDaApi() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("nome", "Pedro Santos");
        request.put("cpf", "11122233344");
        request.put("dataNascimento", "1995-06-10");
        request.put("telefone", "11988887777");

        String response = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Pedro Santos"))
                .andExpect(jsonPath("$.cpf").value("11122233344"))
                .andExpect(jsonPath("$.dataNascimento").value("1995-06-10"))
                .andExpect(jsonPath("$.telefone").value("11988887777"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Paciente pacienteSalvo = objectMapper.readValue(response, Paciente.class);
        pacienteId = pacienteSalvo.getId();

        assertTrue(pacienteRepository.existsById(pacienteId));
        Paciente persistido = pacienteRepository.findById(pacienteId).orElseThrow();
        assertEquals("Pedro Santos", persistido.getNome());
        assertEquals("11122233344", persistido.getCpf());
        assertEquals(LocalDate.of(1995, 6, 10), persistido.getDataNascimento());
        assertEquals("11988887777", persistido.getTelefone());
    }

    @Test
    @Order(2)
    void teste2_deveBuscarPacienteCadastrado() throws Exception {
        mockMvc.perform(get("/pacientes/{id}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pacienteId))
                .andExpect(jsonPath("$.nome").value("Pedro Santos"))
                .andExpect(jsonPath("$.cpf").value("11122233344"))
                .andExpect(jsonPath("$.dataNascimento").value("1995-06-10"))
                .andExpect(jsonPath("$.telefone").value("11988887777"));

        Paciente persistido = pacienteRepository.findById(pacienteId).orElseThrow();
        assertEquals("Pedro Santos", persistido.getNome());
        assertEquals("11122233344", persistido.getCpf());
    }

    @Test
    @Order(3)
    void teste3_deveListarTodosOsPacientes() throws Exception {
        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(pacienteId))
                .andExpect(jsonPath("$[0].nome").value("Pedro Santos"))
                .andExpect(jsonPath("$[0].cpf").value("11122233344"))
                .andExpect(jsonPath("$[0].dataNascimento").value("1995-06-10"))
                .andExpect(jsonPath("$[0].telefone").value("11988887777"));

        assertEquals(1, pacienteRepository.count());
        assertTrue(pacienteRepository.existsById(pacienteId));
    }

    @Test
    @Order(4)
    void teste4_deveExcluirPaciente() throws Exception {
        assertTrue(pacienteRepository.existsById(pacienteId));

        mockMvc.perform(delete("/pacientes/{id}", pacienteId))
                .andExpect(status().isNoContent());

        assertFalse(pacienteRepository.existsById(pacienteId));
        assertEquals(0, pacienteRepository.count());

        mockMvc.perform(get("/pacientes/{id}", pacienteId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").exists());
    }
}
