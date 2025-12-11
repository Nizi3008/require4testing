package require4testing.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestRun implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;                        // ID des Testlaufs
    private String testerName;                // zugewiesener Tester
    private List<TestCase> testCases;         // mehrere Testfaelle
    private String title;
    private String description;

    // ----------------------------
    //   KONSTRUKTOR
    // ----------------------------
    public TestRun() {
        this.testCases = new ArrayList<>();
    }

    // ----------------------------
    //   GETTER & SETTER
    // ----------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }


    public List<TestCase> getTestCases() {
        return testCases;
    }
    

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    // Testfall hinzufügen
    public void addTestCase(TestCase tc) {
        this.testCases.add(tc);
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}