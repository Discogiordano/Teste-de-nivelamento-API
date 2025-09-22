package com.hospital.service;

import com.hospital.model.FichaPaciente;
import com.hospital.model.dto.FichaPacienteRequestDto;
import com.hospital.model.dto.FichaPacienteListagemDto;
import com.hospital.model.PlanoDeSaude;
import com.hospital.model.Especialidade;
import com.hospital.repository.FichaPacienteRepository;
import com.hospital.repository.EspecialidadeRepository;
import com.hospital.repository.PlanoDeSaudeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FichaPacienteService {
    private final FichaPacienteRepository repository;
    private final EspecialidadeRepository especialidadeRepository;
    private final PlanoDeSaudeRepository planoDeSaudeRepository;

    public FichaPacienteService(FichaPacienteRepository repository, EspecialidadeRepository especialidadeRepository, PlanoDeSaudeRepository planoDeSaudeRepository) {
        this.repository = repository;
        this.especialidadeRepository = especialidadeRepository;
        this.planoDeSaudeRepository = planoDeSaudeRepository;
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
        PlanoDeSaude plano = planoDeSaudeRepository.findById(dto.getPlanoDeSaudeId());
        if (plano == null) {
            return "Plano de saúde informado não existe.";
        }
        Especialidade especialidade = especialidadeRepository.findById(dto.getEspecialidadeId());
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
        PlanoDeSaude plano = planoDeSaudeRepository.findById(dto.getPlanoDeSaudeId());
        if (plano == null) {
            return "Plano de saúde informado não existe.";
        }
        Especialidade especialidade = especialidadeRepository.findById(dto.getEspecialidadeId());
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

    public List<Especialidade> listarEspecialidades() {
        return especialidadeRepository.findAll();
    }

    public List<PlanoDeSaude> listarPlanosDeSaude() {
        return planoDeSaudeRepository.findAll();
    }

    public List<FichaPacienteListagemDto> listarTodos() {
        return repository.findAll().stream()
            .map(f -> new FichaPacienteListagemDto(
                f.getNomePaciente(),
                f.getNumeroCarteiraPlano(),
                f.getEspecialidade() != null ? f.getEspecialidade().getNome() : null,
                f.getPlanoDeSaude() != null ? f.getPlanoDeSaude().getNome() : null
            ))
            .toList();
    }

    public List<FichaPacienteListagemDto> listarPorNome(String nomePacienteUp) {
        return repository.findByNomeUpcase(nomePacienteUp).stream()
            .map(f -> new FichaPacienteListagemDto(
                f.getNomePaciente(),
                f.getNumeroCarteiraPlano(),
                f.getEspecialidade() != null ? f.getEspecialidade().getNome() : null,
                f.getPlanoDeSaude() != null ? f.getPlanoDeSaude().getNome() : null
            )).toList();
    }

    public List<FichaPacienteListagemDto> listarPorNumeroPlano(String numeroCarteiraPlano) {
        return repository.findByNumeroPlano(numeroCarteiraPlano).stream()
            .map(f -> new FichaPacienteListagemDto(
                f.getNomePaciente(),
                f.getNumeroCarteiraPlano(),
                f.getEspecialidade() != null ? f.getEspecialidade().getNome() : null,
                f.getPlanoDeSaude() != null ? f.getPlanoDeSaude().getNome() : null
            )).toList();
    }

    public List<FichaPacienteListagemDto> listarPorEspecialidade(Long especialidadeId) {
        return repository.findByEspecialidade(especialidadeId).stream()
            .map(f -> new FichaPacienteListagemDto(
                f.getNomePaciente(),
                f.getNumeroCarteiraPlano(),
                f.getEspecialidade() != null ? f.getEspecialidade().getNome() : null,
                f.getPlanoDeSaude() != null ? f.getPlanoDeSaude().getNome() : null
            )).toList();
    }

    public List<FichaPacienteListagemDto> listarPorPlano(Long planoId) {
        return repository.findByPlano(planoId).stream()
            .map(f -> new FichaPacienteListagemDto(
                f.getNomePaciente(),
                f.getNumeroCarteiraPlano(),
                f.getEspecialidade() != null ? f.getEspecialidade().getNome() : null,
                f.getPlanoDeSaude() != null ? f.getPlanoDeSaude().getNome() : null
            )).toList();
    }

    public List<FichaPacienteListagemDto> listarPorEspecialidadeNome(String especialidadeNomeUp) {
        return repository.findByEspecialidadeNomeUpcase(especialidadeNomeUp).stream()
            .map(f -> new FichaPacienteListagemDto(
                f.getNomePaciente(),
                f.getNumeroCarteiraPlano(),
                f.getEspecialidade() != null ? f.getEspecialidade().getNome() : null,
                f.getPlanoDeSaude() != null ? f.getPlanoDeSaude().getNome() : null
            )).toList();
    }

    public List<FichaPacienteListagemDto> listarPorPlanoNome(String planoNomeUp) {
        return repository.findByPlanoNomeUpcase(planoNomeUp).stream()
            .map(f -> new FichaPacienteListagemDto(
                f.getNomePaciente(),
                f.getNumeroCarteiraPlano(),
                f.getEspecialidade() != null ? f.getEspecialidade().getNome() : null,
                f.getPlanoDeSaude() != null ? f.getPlanoDeSaude().getNome() : null
            )).toList();
    }
}
