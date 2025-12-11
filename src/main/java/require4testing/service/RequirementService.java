package require4testing.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import require4testing.model.Requirement;

@Named
@ApplicationScoped
public class RequirementService {

    @Inject
    private JpaUtil jpaUtil;

    public void save(Requirement req) {
        EntityManager em = jpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // Wenn ID neu ist → persist, sonst → merge
            if (em.find(Requirement.class, req.getId()) == null) {
                em.persist(req);
            } else {
                em.merge(req);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e; // oder Logging
        } finally {
            em.close();
        }
    }

    public List<Requirement> findAll() {
        EntityManager em = jpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Requirement r", Requirement.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public Requirement findById(String id) {
        EntityManager em = jpaUtil.createEntityManager();
        try {
            return em.find(Requirement.class, id);
        } finally {
            em.close();
        }
    }

    public void delete(String id) {
        EntityManager em = jpaUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Requirement r = em.find(Requirement.class, id);
            if (r != null) {
                em.remove(r);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}