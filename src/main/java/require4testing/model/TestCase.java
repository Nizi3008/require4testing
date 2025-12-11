package require4testing.model;

import java.io.Serializable;
import java.time.LocalDate;

public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;               
    private String title;
    private String description;
    private String requirementId;

    public TestCase() {}

    // Neuer Hauptkonstruktor
    public TestCase(String id, String title, String description, String requirementId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requirementId = requirementId;
    }

    // ALTER Konstruktor für Dummy-Test-Cases der Tester
    public TestCase(String id, String title, String description) {
        this(id, title, description, null);
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequirementId() { return requirementId; }
    public void setRequirementId(String requirementId) { this.requirementId = requirementId; }
    
    private String testResult;   // "passed" / "failed"
    private String testComment;  // Kommentar des Testers

    public String getTestResult() { return testResult; }
    public void setTestResult(String testResult) { this.testResult = testResult; }
}