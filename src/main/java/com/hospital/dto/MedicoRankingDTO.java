package com.hospital.dto;

import com.hospital.model.Medico;

public class MedicoRankingDTO {

    private Long id;
    private String nome;
    private String crm;
    private String especialidade;
    private long totalConsultas;

    public MedicoRankingDTO() {
    }

    public MedicoRankingDTO(Medico medico, long totalConsultas) {
        this.id = medico.getId();
        this.nome = medico.getNome();
        this.crm = medico.getCrm();
        this.especialidade = medico.getEspecialidade();
        this.totalConsultas = totalConsultas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public long getTotalConsultas() {
        return totalConsultas;
    }

    public void setTotalConsultas(long totalConsultas) {
        this.totalConsultas = totalConsultas;
    }
}
