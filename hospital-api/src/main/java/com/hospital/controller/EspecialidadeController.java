package com.hospital.controller;

import com.hospital.model.Especialidade;
import com.hospital.service.FichaPacienteService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/especialidades")
@Produces(MediaType.APPLICATION_JSON)
public class EspecialidadeController {
    private final FichaPacienteService fichaPacienteService;

    public EspecialidadeController(FichaPacienteService fichaPacienteService) {
        this.fichaPacienteService = fichaPacienteService;
    }

    @GET
    @Path("/listar-especialidades")
    public List<Especialidade> listarEspecialidades() {
        return fichaPacienteService.listarEspecialidades();
    }
}
