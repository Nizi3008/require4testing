package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import require4testing.model.TestCase;

@ApplicationScoped
public class TestCaseService {

    private EntityManagerFactory emf;

    public TestCaseService() {
        this.emf = Persistence.createEntityManagerFactory("require4testingPU");
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    // --- CREATE ---
    public void save(TestCase testCase) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(testCase);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // --- READ ---
    public List<TestCase> findAll() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                "SELECT t FROM TestCase t", TestCase.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public List<TestCase> findByRequirementId(String requirementId) {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                "SELECT t FROM TestCase t WHERE t.requirement.id = :reqId",
                TestCase.class
            ).setParameter("reqId", requirementId)
             .getResultList();
        } finally {
            em.close();
        }
    }
    
    public TestCase findByDbId(Long dbId) {
        EntityManager em = createEntityManager();
        try {
            return em.find(TestCase.class, dbId);
        } finally {
            em.close();
        }
    }
}