package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import require4testing.model.Requirement;

@ApplicationScoped
public class RequirementService {

    private EntityManagerFactory emf;

    public RequirementService() {
        this.emf = Persistence.createEntityManagerFactory("require4testingPU");
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    // --- CREATE ---
    public void save(Requirement requirement) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(requirement);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // --- READ ---
    public List<Requirement> findAll() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                "SELECT r FROM Requirement r", Requirement.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
    
 // --- BUSINESS-ID erzeugen ---
    public String generateNextRequirementId() {
        EntityManager em = createEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(r) FROM Requirement r", Long.class
            ).getSingleResult();

            long next = count + 1;
            return String.format("REQ-%03d", next);
        } finally {
            em.close();
        }
    }

    // --- FIND by business ID ---
    public Requirement findByBusinessId(String id) {
        EntityManager em = createEntityManager();
        try {
            List<Requirement> result = em.createQuery(
                "SELECT r FROM Requirement r WHERE r.id = :id", Requirement.class
            ).setParameter("id", id)
             .getResultList();

            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
}