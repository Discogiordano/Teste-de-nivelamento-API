package com.hospital.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PlanoDeSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "planoDeSaude")
    @JsonIgnore
    private List<FichaPaciente> fichas;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<FichaPaciente> getFichas() { return fichas; }
    public void setFichas(List<FichaPaciente> fichas) { this.fichas = fichas; }
}
