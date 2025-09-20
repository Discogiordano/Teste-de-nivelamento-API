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
}
