package require4testing.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import require4testing.model.TestRun;

@ApplicationScoped
public class TestRunService {

	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("require4testingPU");

	private EntityManager em() {
		return EMF.createEntityManager();
	}

	// =======================
	// CREATE
	// =======================
	public void save(TestRun testRun) {
		EntityManager em = em();
		try {
			em.getTransaction().begin();
			em.persist(testRun); // Items werden wegen CascadeType.ALL automatisch mitpersistiert
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	// =======================
	// UPDATE (ganzer TestRun)
	// =======================
	public void update(TestRun testRun) {
		EntityManager em = em();
		try {
			em.getTransaction().begin();
			em.merge(testRun);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	// =======================
	// UPDATE Ergebnis pro Item (wichtig für Tester!)
	// =======================
	public void updateItemResult(Long itemDbId, String result) {
		if (itemDbId == null || result == null || result.isBlank())
			return;

		EntityManager em = em();
		try {
			em.getTransaction().begin();
			em.createQuery("UPDATE TestRunItem i SET i.testResult = :res WHERE i.dbId = :id")
					.setParameter("res", result).setParameter("id", itemDbId).executeUpdate();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	// =======================
	// READ: by PK
	// =======================
	public TestRun findByDbId(Long dbId) {
		if (dbId == null)
			return null;

		EntityManager em = em();
		try {
			return em.find(TestRun.class, dbId);
		} finally {
			em.close();
		}
	}

	// =======================
	// READ: alle TestRuns inkl. Items + TestCase (für Dashboard)
	// =======================
	public List<TestRun> findAll() {
		EntityManager em = em();
		try {
			return em
					.createQuery("SELECT DISTINCT tr " + "FROM TestRun tr " + "LEFT JOIN FETCH tr.items it "
							+ "LEFT JOIN FETCH it.testCase tc " + "ORDER BY tr.dbId DESC", TestRun.class)
					.getResultList();
		} finally {
			em.close();
		}
	}

	// =======================
	// READ: TestRuns pro Tester inkl. Items + TestCase (für Tester-Dashboard)
	// =======================
	public List<TestRun> findByTesterName(String testerName) {
		if (testerName == null || testerName.isBlank())
			return List.of();

		EntityManager em = em();
		try {
			return em.createQuery("SELECT DISTINCT tr " + "FROM TestRun tr " + "LEFT JOIN FETCH tr.items it "
					+ "LEFT JOIN FETCH it.testCase tc " + "WHERE tr.testerName = :name " + "ORDER BY tr.dbId DESC",
					TestRun.class).setParameter("name", testerName).getResultList();
		} finally {
			em.close();
		}
	}

	// =======================
	// Business-ID max (TR-###)
	// =======================
	public String findMaxBusinessId() {
		EntityManager em = em();
		try {
			return em.createQuery("SELECT MAX(tr.id) FROM TestRun tr", String.class).getSingleResult();
		} finally {
			em.close();
		}
	}
}