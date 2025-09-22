package com.hospital.repository;

import com.hospital.model.FichaPaciente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FichaPacienteRepository implements PanacheRepository<FichaPaciente> {
    public List<FichaPaciente> findByFilters(String nomePaciente, Long especialidadeId, Long planoDeSaudeId) {
        String query = "1=1";
        if (nomePaciente != null && !nomePaciente.isEmpty()) query += " and nomePaciente like ?1";
        if (especialidadeId != null) query += " and especialidade.id = ?2";
        if (planoDeSaudeId != null) query += " and planoDeSaude.id = ?3";
        return find(query,
            nomePaciente != null ? "%" + nomePaciente + "%" : null,
            especialidadeId,
            planoDeSaudeId
        ).list();
    }

    public boolean existsByPlanoAndEspecialidade(Long planoDeSaudeId, Long especialidadeId, String numeroCarteiraPlano) {
        return count("planoDeSaude.id = ?1 and especialidade.id = ?2 and numeroCarteiraPlano = ?3", planoDeSaudeId, especialidadeId, numeroCarteiraPlano) > 0;
    }

    public boolean existsByPlanoAndEspecialidade(Long planoDeSaudeId, Long especialidadeId, String numeroCarteiraPlano, Long excludeId) {
        return count("planoDeSaude.id = ?1 and especialidade.id = ?2 and numeroCarteiraPlano = ?3 and id <> ?4", planoDeSaudeId, especialidadeId, numeroCarteiraPlano, excludeId) > 0;
    }


    public List<FichaPaciente> findByNomeUpcase(String nomePacienteUp) {
        if (nomePacienteUp == null) return List.of();
        return find("UPPER(nomePaciente) like ?1", "%" + nomePacienteUp + "%").list();
    }

    public List<FichaPaciente> findByNumeroPlano(String numeroCarteiraPlano) {
        if (numeroCarteiraPlano == null) return List.of();
        return find("numeroCarteiraPlano like ?1", numeroCarteiraPlano + "%").list();
    }

    public List<FichaPaciente> findByEspecialidade(Long especialidadeId) {
        if (especialidadeId == null) return List.of();
        return find("especialidade.id = ?1", especialidadeId).list();
    }

    public List<FichaPaciente> findByPlano(Long planoId) {
        if (planoId == null) return List.of();
        return find("planoDeSaude.id = ?1", planoId).list();
    }

    public List<FichaPaciente> findByEspecialidadeNomeUpcase(String especialidadeNomeUp) {
        if (especialidadeNomeUp == null) return List.of();
        return find("UPPER(especialidade.nome) like ?1", "%" + especialidadeNomeUp + "%").list();
    }

    public List<FichaPaciente> findByPlanoNomeUpcase(String planoNomeUp) {
        if (planoNomeUp == null) return List.of();
        return find("UPPER(planoDeSaude.nome) like ?1", "%" + planoNomeUp + "%").list();
    }
}
