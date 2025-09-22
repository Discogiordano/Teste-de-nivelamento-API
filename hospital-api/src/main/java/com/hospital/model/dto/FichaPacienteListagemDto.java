package com.hospital.model.dto;


public record FichaPacienteListagemDto(
        Long id,
        String nomePaciente,
        String numeroCarteiraPlano,
        String nomeEspecialidade,
        String nomePlanoDeSaude
) {}