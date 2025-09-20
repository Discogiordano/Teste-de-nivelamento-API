package com.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

@Entity
public class FichaPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome do paciente é obrigatório.")
    private String nomePaciente;

    @NotNull(message = "O número da carteira do plano é obrigatório.")
    @Size(max = 255, message = "O número da carteira do plano deve ter no máximo 255 caracteres.")
    @Pattern(regexp = "^[A-Za-z0-9]{6,255}$", message = "O número da carteira do plano deve ser alfanumérico e ter entre 6 e 255 caracteres.")
    private String numeroCarteiraPlano;

    @ManyToOne
    @JoinColumn(name = "idPlanoDeSaude")
    @NotNull(message = "O plano de saúde é obrigatório.")
    private PlanoDeSaude planoDeSaude;

    @ManyToOne
    @JoinColumn(name = "idEspecialidade")
    @NotNull(message = "A especialidade é obrigatória.")
    private Especialidade especialidade;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }
    public String getNumeroCarteiraPlano() { return numeroCarteiraPlano; }
    public void setNumeroCarteiraPlano(String numeroCarteiraPlano) { this.numeroCarteiraPlano = numeroCarteiraPlano; }
    public PlanoDeSaude getPlanoDeSaude() { return planoDeSaude; }
    public void setPlanoDeSaude(PlanoDeSaude planoDeSaude) { this.planoDeSaude = planoDeSaude; }
    public Especialidade getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidade especialidade) { this.especialidade = especialidade; }
}
