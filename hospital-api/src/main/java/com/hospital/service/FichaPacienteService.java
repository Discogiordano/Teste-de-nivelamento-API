package com.hospital.service;

import com.hospital.model.FichaPaciente;
import com.hospital.model.dto.FichaPacienteRequestDto;
import com.hospital.model.PlanoDeSaude;
import com.hospital.model.Especialidade;
import com.hospital.repository.FichaPacienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class FichaPacienteService {
    private final FichaPacienteRepository repository;
    @PersistenceContext
    EntityManager em;

    public FichaPacienteService(FichaPacienteRepository repository) {
        this.repository = repository;
    }

    public List<FichaPaciente> listar(String nomePaciente, Long especialidadeId, Long planoDeSaudeId) {
        return repository.findByFilters(nomePaciente, especialidadeId, planoDeSaudeId);
    }

    @Transactional
    public Object criar(FichaPacienteRequestDto dto) {
        String numeroCarteira = dto.getNumeroCarteira();
        if (numeroCarteira == null || numeroCarteira.length() > 255 || numeroCarteira.length() < 6 || !numeroCarteira.matches("^[A-Za-z0-9]+$")) {
            return "O número da carteira do plano deve ser alfanumérico, entre 6 e 255 caracteres.";
        }
        PlanoDeSaude plano = em.find(PlanoDeSaude.class, dto.getPlanoDeSaudeId());
        if (plano == null) {
            return "Plano de saúde informado não existe.";
        }
        Especialidade especialidade = em.find(Especialidade.class, dto.getEspecialidadeId());
        if (especialidade == null) {
            return "Especialidade informada não existe.";
        }
        if (repository.existsByPlanoAndEspecialidade(dto.getPlanoDeSaudeId(), dto.getEspecialidadeId(), numeroCarteira)) {
            return "Esta especialidade " + dto.getEspecialidadeId() + " já foi utilizada para o plano " + dto.getPlanoDeSaudeId();
        }
        FichaPaciente ficha = new FichaPaciente();
        ficha.setNomePaciente(dto.getNomePaciente());
        ficha.setNumeroCarteiraPlano(numeroCarteira);
        ficha.setPlanoDeSaude(plano);
        ficha.setEspecialidade(especialidade);
        repository.persist(ficha);
        return ficha;
    }

    @Transactional
    public Object atualizar(Long id, FichaPacienteRequestDto dto) {
        FichaPaciente ficha = repository.findById(id);
        if (ficha == null) return "Ficha não encontrada";
        PlanoDeSaude plano = em.find(PlanoDeSaude.class, dto.getPlanoDeSaudeId());
        if (plano == null) {
            return "Plano de saúde informado não existe.";
        }
        Especialidade especialidade = em.find(Especialidade.class, dto.getEspecialidadeId());
        if (especialidade == null) {
            return "Especialidade informada não existe.";
        }
        if (repository.existsByPlanoAndEspecialidade(dto.getPlanoDeSaudeId(), dto.getEspecialidadeId(), dto.getNumeroCarteira(), id)) {
            return "Esta especialidade " + dto.getEspecialidadeId() + " já foi utilizada para o plano " + dto.getPlanoDeSaudeId();
        }
        ficha.setNomePaciente(dto.getNomePaciente());
        ficha.setNumeroCarteiraPlano(dto.getNumeroCarteira());
        ficha.setPlanoDeSaude(plano);
        ficha.setEspecialidade(especialidade);
        repository.persist(ficha);
        return ficha;
    }

    @Transactional
    public void deletar(Long id) {
        FichaPaciente ficha = repository.findById(id);
        if (ficha != null) {
            repository.delete(ficha);
        }
    }
}
