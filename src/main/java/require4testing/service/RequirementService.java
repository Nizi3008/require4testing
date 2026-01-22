package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import require4testing.model.Requirement;

@ApplicationScoped
public class RequirementService {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("require4testingPU");

    private EntityManager em() {
        return EMF.createEntityManager();
    }

    public void save(Requirement requirement) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(requirement);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Requirement> findAll() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT r FROM Requirement r ORDER BY r.dbId DESC", Requirement.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public String generateNextRequirementId() {
        EntityManager em = em();
        try {
            Long count = em.createQuery("SELECT COUNT(r) FROM Requirement r", Long.class)
                           .getSingleResult();
            long next = count + 1;
            return String.format("REQ-%03d", next);
        } finally {
            em.close();
        }
    }

    public Requirement findByBusinessId(String id) {
        if (id == null || id.isBlank()) return null;

        EntityManager em = em();
        try {
            List<Requirement> result = em.createQuery(
                    "SELECT r FROM Requirement r WHERE r.id = :id",
                    Requirement.class
            ).setParameter("id", id).getResultList();

            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
}