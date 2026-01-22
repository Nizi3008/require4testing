package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import require4testing.model.TestRunItem;

@ApplicationScoped
public class TestRunItemService {

	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("require4testingPU");

	private EntityManager em() {
		return EMF.createEntityManager();
	}

	public void save(TestRunItem item) {
		EntityManager em = em();
		try {
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public void update(TestRunItem item) {
		EntityManager em = em();
		try {
			em.getTransaction().begin();
			em.merge(item);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public TestRunItem findByDbId(Long dbId) {
		if (dbId == null)
			return null;

		EntityManager em = em();
		try {
			return em.find(TestRunItem.class, dbId);
		} finally {
			em.close();
		}
	}

	public List<TestRunItem> findAll() {
		EntityManager em = em();
		try {
			return em.createQuery("SELECT it FROM TestRunItem it ORDER BY it.dbId DESC", TestRunItem.class)
					.getResultList();
		} finally {
			em.close();
		}
	}

	/**
	 * Liefert alle TestRunItems für einen Tester. Join über it.testRun.testerName.
	 */
	public List<TestRunItem> findByTesterName(String testerName) {
		if (testerName == null || testerName.isBlank())
			return List.of();

		EntityManager em = em();
		try {
			return em
					.createQuery("SELECT it " + "FROM TestRunItem it " + "JOIN it.testRun tr "
							+ "WHERE tr.testerName = :name " + "ORDER BY it.dbId DESC", TestRunItem.class)
					.setParameter("name", testerName).getResultList();
		} finally {
			em.close();
		}
	}

	/**
	 * Ergebnis pro Item setzen (z.B. OFFEN / PASSED / FAILED)
	 */
	public void updateResult(Long itemDbId, String result) {
		if (itemDbId == null || result == null || result.isBlank())
			return;

		EntityManager em = em();
		try {
			em.getTransaction().begin();
			em.createQuery("UPDATE TestRunItem it SET it.testResult = :res WHERE it.dbId = :id")
					.setParameter("res", result).setParameter("id", itemDbId).executeUpdate();

			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}