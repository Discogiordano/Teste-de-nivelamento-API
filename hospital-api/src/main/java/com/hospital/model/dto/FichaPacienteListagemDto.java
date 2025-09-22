package com.hospital.model.dto;

public record FichaPacienteListagemDto(
        String nomePaciente,
        String numeroCarteiraPlano,
        String nomeEspecialidade,
        String nomePlanoDeSaude
) {}