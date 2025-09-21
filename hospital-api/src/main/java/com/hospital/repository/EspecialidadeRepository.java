package com.hospital.repository;

import com.hospital.model.Especialidade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class EspecialidadeRepository {
    @PersistenceContext
    EntityManager em;

    public List<Especialidade> findAll() {
        return em.createQuery("SELECT e FROM Especialidade e", Especialidade.class).getResultList();
    }

    public Especialidade findById(Long id) {
        return em.find(Especialidade.class, id);
    }
}
