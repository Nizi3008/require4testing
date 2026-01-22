package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import require4testing.model.TestCase;

@ApplicationScoped
public class TestCaseService {

	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("require4testingPU");

	private EntityManager em() {
		return EMF.createEntityManager();
	}

	public void save(TestCase testCase) {
		EntityManager em = em();
		try {
			em.getTransaction().begin();
			em.persist(testCase);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public List<TestCase> findAll() {
		EntityManager em = em();
		try {
			return em.createQuery("SELECT t FROM TestCase t ORDER BY t.dbId DESC", TestCase.class).getResultList();
		} finally {
			em.close();
		}
	}

	public List<TestCase> findByRequirementId(String requirementId) {
		if (requirementId == null || requirementId.isBlank())
			return List.of();

		EntityManager em = em();
		try {
			return em.createQuery("SELECT t FROM TestCase t WHERE t.requirement.id = :reqId ORDER BY t.dbId DESC",
					TestCase.class).setParameter("reqId", requirementId).getResultList();
		} finally {
			em.close();
		}
	}

	public TestCase findByDbId(Long dbId) {
		if (dbId == null)
			return null;

		EntityManager em = em();
		try {
			return em.find(TestCase.class, dbId);
		} finally {
			em.close();
		}
	}
}