package com.hospital.controller;

import com.hospital.model.PlanoDeSaude;
import com.hospital.service.FichaPacienteService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/planosdesaude")
@Produces(MediaType.APPLICATION_JSON)
public class PlanoDeSaudeController {
    private final FichaPacienteService fichaPacienteService;

    public PlanoDeSaudeController(FichaPacienteService fichaPacienteService) {
        this.fichaPacienteService = fichaPacienteService;
    }

    @GET
    @Path("/listar-planosdesaude")
    public List<PlanoDeSaude> listarPlanosDeSaude() {
        return fichaPacienteService.listarPlanosDeSaude();
    }
}
