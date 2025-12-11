package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.Requirement;
import require4testing.model.TestCase;

@Named("testCaseAuthorController")
@SessionScoped
public class TestCaseAuthorController implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TestCase> testCases = new ArrayList<>();
    private TestCase newTestCase = new TestCase();
    private String selectedRequirementId;

    @Inject
    private TestManagerController testManagerController;

    @Inject
    private RequirementsEngineerController requirementsEngineerController;


    // =======================
    // TESTFALL SPEICHERN
    // =======================
    public String saveTestCase() {

        // ID generieren
        int nextNumber = testCases.size() + 1;
        String generatedId = String.format("TC-%03d", nextNumber);
        newTestCase.setId(generatedId);

        // Requirement zuweisen
        newTestCase.setRequirementId(selectedRequirementId);

        // In eigene Liste
        testCases.add(newTestCase);

        // Manager informieren
        testManagerController.addTestCase(newTestCase);

        // Zurücksetzen
        newTestCase = new TestCase();
        selectedRequirementId = null;

        return "/views/testcases/dashboard.xhtml?faces-redirect=true";
    }


    // =======================
    // Getter für Dropdown
    // =======================
    public List<Requirement> getAllRequirements() {
        return requirementsEngineerController.getRequirements();
    }


    // Getter/Setter

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public TestCase getNewTestCase() {
        return newTestCase;
    }

    public void setNewTestCase(TestCase newTestCase) {
        this.newTestCase = newTestCase;
    }

    public String getSelectedRequirementId() {
        return selectedRequirementId;
    }

    public void setSelectedRequirementId(String selectedRequirementId) {
        this.selectedRequirementId = selectedRequirementId;
    }
}