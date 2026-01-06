package require4testing.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "testruns")
public class TestRun implements Serializable {

    private static final long serialVersionUID = 1L;

    // =======================
    // TECHNISCHE DB-ID
    // =======================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId;

    // =======================
    // FACHLICHE TESTRUN-ID (TR-001)
    // =======================
    @Column(nullable = false, unique = true)
    private String id;

    // =======================
    // ZUGEORDNETER TESTFALL
    // =======================
    @ManyToOne(optional = false)
    @JoinColumn(name = "testcase_id")
    private TestCase testCase;

    // =======================
    // TESTER
    // =======================
    @Column(nullable = false)
    private String testerName;

    // =======================
    // TESTERGEBNIS
    // =======================
    @Column(nullable = false)
    private String testResult;   // OFFEN / PASSED / FAILED

    @Column(length = 2000)
    private String testComment;

    // =======================
    // KONSTRUKTOR
    // =======================
    public TestRun() {
        this.testResult = "OFFEN"; // Default
    }

    // =======================
    // GETTER / SETTER
    // =======================

    public Long getDbId() {
        return dbId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestComment() {
        return testComment;
    }

    public void setTestComment(String testComment) {
        this.testComment = testComment;
    }
}