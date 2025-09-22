package com.hospital.controller;

import com.hospital.model.FichaPaciente;
import com.hospital.model.dto.FichaPacienteRequestDto;
import com.hospital.model.dto.FichaPacienteListagemDto;
import com.hospital.service.FichaPacienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/fichas-paciente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FichaPacienteController {
    private final FichaPacienteService service;

    public FichaPacienteController(FichaPacienteService service) {
        this.service = service;
    }

    @GET
    @Path("/listar-fichas")
    public List<FichaPaciente> listar(@QueryParam("nomePaciente") String nomePaciente,
                                      @QueryParam("especialidadeId") Long especialidadeId,
                                      @QueryParam("planoDeSaudeId") Long planoDeSaudeId) {
        return service.listar(nomePaciente, especialidadeId, planoDeSaudeId);
    }

    @POST
    @Path("cadastrar-ficha")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criar(@Valid FichaPacienteRequestDto dto) {
        Object result = service.criar(dto);
        if (result instanceof String) {
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("atualizar-ficha/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") Long id, @Valid FichaPacienteRequestDto dto) {
        Object result = service.atualizar(id, dto);
        if (result instanceof String) {
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        service.deletar(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/listar-fichas-todas")
    public List<FichaPacienteListagemDto> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/listar-fichas-por-nome")
    public List<FichaPacienteListagemDto> listarPorNome(@QueryParam("nomePaciente") String nomePaciente) {
        String nomeUp = nomePaciente != null ? nomePaciente.toUpperCase() : null;
        return service.listarPorNome(nomeUp);
    }

    @GET
    @Path("/listar-fichas-por-numero-plano")
    public List<FichaPacienteListagemDto> listarPorNumeroPlano(@QueryParam("numeroCarteiraPlano") String numeroCarteiraPlano) {
        if (numeroCarteiraPlano == null || numeroCarteiraPlano.isEmpty()) {
            return List.of();
        }
        return service.listarPorNumeroPlano(numeroCarteiraPlano);
    }

    @GET
    @Path("/listar-fichas-por-especialidade")
    public List<FichaPacienteListagemDto> listarPorEspecialidade(@QueryParam("especialidadeNome") String especialidadeNome) {
        String nomeUp = especialidadeNome != null ? especialidadeNome.toUpperCase() : null;
        return service.listarPorEspecialidadeNome(nomeUp);
    }

    @GET
    @Path("/listar-fichas-por-plano")
    public List<FichaPacienteListagemDto> listarPorPlano(@QueryParam("planoNome") String planoNome) {
        String nomeUp = planoNome != null ? planoNome.toUpperCase() : null;
        return service.listarPorPlanoNome(nomeUp);
    }

    @GET
    @Path("/buscar-ficha/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        FichaPaciente ficha = service.buscarPorId(id);
        if (ficha == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ficha não encontrada").build();
        }
        FichaPacienteListagemDto dto = new FichaPacienteListagemDto(
            ficha.getId(),
            ficha.getNomePaciente(),
            ficha.getNumeroCarteiraPlano(),
            ficha.getEspecialidade() != null ? ficha.getEspecialidade().getNome() : null,
            ficha.getPlanoDeSaude() != null ? ficha.getPlanoDeSaude().getNome() : null
        );
        return Response.ok(dto).build();
    }
}
