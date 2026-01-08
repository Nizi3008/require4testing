package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import require4testing.model.TestRun;

@ApplicationScoped
public class TestRunService {

    private final EntityManagerFactory emf;

    public TestRunService() {
        this.emf = Persistence.createEntityManagerFactory("require4testingPU");
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

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

    public void update(TestRun testRun) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(testRun);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateResult(Long dbId, String result) {
        if (dbId == null || result == null || result.isBlank()) {
            return;
        }

        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE TestRun tr SET tr.testResult = :res WHERE tr.dbId = :id")
              .setParameter("res", result)
              .setParameter("id", dbId)
              .executeUpdate();
            em.getTransaction().commit();
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

    public List<TestRun> findAll() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery("SELECT tr FROM TestRun tr ORDER BY tr.dbId DESC", TestRun.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public List<TestRun> findByTesterName(String testerName) {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery(
                    "SELECT tr FROM TestRun tr WHERE tr.testerName = :name ORDER BY tr.dbId DESC",
                    TestRun.class
            ).setParameter("name", testerName)
             .getResultList();
        } finally {
            em.close();
        }
    }

    public String findMaxBusinessId() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery("SELECT MAX(tr.id) FROM TestRun tr", String.class)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }
}