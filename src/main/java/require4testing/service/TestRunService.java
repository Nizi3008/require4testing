package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import require4testing.model.TestRun;

@ApplicationScoped
public class TestRunService {

    private EntityManagerFactory emf;

    public TestRunService() {
        this.emf = Persistence.createEntityManagerFactory("require4testingPU");
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    // =======================
    // CREATE
    // =======================
    public void save(TestRun testRun) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(testRun);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // =======================
    // READ
    // =======================
    public List<TestRun> findAll() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                "SELECT tr FROM TestRun tr",
                TestRun.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
}