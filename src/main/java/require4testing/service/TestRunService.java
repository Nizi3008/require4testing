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
                "SELECT tr FROM TestRun tr ORDER BY tr.dbId DESC",
                TestRun.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public TestRun findByDbId(Long dbId) {
        if (dbId == null) return null;

        EntityManager em = createEntityManager();
        try {
            return em.find(TestRun.class, dbId);
        } finally {
            em.close();
        }
    }

    /**
     * Liefert die größte vorhandene fachliche ID (z.B. "TR-014") oder null,
     * falls noch keine existiert.
     */
    public String findMaxBusinessId() {
        EntityManager em = createEntityManager();
        try {
            List<String> res = em.createQuery(
                "SELECT tr.id FROM TestRun tr WHERE tr.id IS NOT NULL ORDER BY tr.id DESC",
                String.class
            ).setMaxResults(1).getResultList();

            return res.isEmpty() ? null : res.get(0);
        } finally {
            em.close();
        }
    }
}