package com.hospital.repository;

import com.hospital.model.PlanoDeSaude;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class PlanoDeSaudeRepository {
    @PersistenceContext
    EntityManager em;

    public List<PlanoDeSaude> findAll() {
        return em.createQuery("SELECT p FROM PlanoDeSaude p", PlanoDeSaude.class).getResultList();
    }

    public PlanoDeSaude findById(Long id) {
        return em.find(PlanoDeSaude.class, id);
    }
}
