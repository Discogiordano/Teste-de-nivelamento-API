package com.hospital.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FichaPacienteRequestDto {
    @NotBlank
    private String nomePaciente;

    @NotBlank
    private String numeroCarteira;

    @NotNull
    private Long especialidadeId;

    @NotNull
    private Long planoDeSaudeId;

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getNumeroCarteira() {
        return numeroCarteira;
    }

    public void setNumeroCarteira(String numeroCarteira) {
        this.numeroCarteira = numeroCarteira;
    }

    public Long getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(Long especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public Long getPlanoDeSaudeId() {
        return planoDeSaudeId;
    }

    public void setPlanoDeSaudeId(Long planoDeSaudeId) {
        this.planoDeSaudeId = planoDeSaudeId;
    }
}
