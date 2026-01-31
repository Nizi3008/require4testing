package require4testing.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "testrun_items", uniqueConstraints = @UniqueConstraint(columnNames = { "testrun_fk", "testcase_fk" }))
public class TestRunItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dbId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "testrun_fk", nullable = false)
	private TestRun testRun;

	@ManyToOne(optional = false)
	@JoinColumn(name = "testcase_fk", nullable = false)
	private TestCase testCase;

	@Column(nullable = false)
	private String testResult; // OFFEN / Bestanden / Fehlgeschlagen

	public TestRunItem() {
	}

	public Long getDbId() {
		return dbId;
	}

	public TestRun getTestRun() {
		return testRun;
	}

	public void setTestRun(TestRun testRun) {
		this.testRun = testRun;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
}