package require4testing.controller.roles;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.Requirement;
import require4testing.model.TestCase;
import require4testing.service.RequirementService;
import require4testing.service.TestCaseService;

@Named("testCaseAuthorController")
@SessionScoped
public class TestCaseAuthorController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TestCaseService testCaseService;

    @Inject
    private RequirementService requirementService;

    private TestCase newTestCase = new TestCase();
    private String selectedRequirementId;

    // =======================
    // TESTFALL SPEICHERN (persistent)
    // =======================
    public String saveTestCase() {

        // Requirement aus DB laden
        Requirement requirement =
                requirementService.findByBusinessId(selectedRequirementId);

        newTestCase.setRequirement(requirement);

        // Business-ID generieren
        String generatedId =
                String.format("TC-%03d", testCaseService.findAll().size() + 1);

        newTestCase.setId(generatedId);

        // Persistieren
        testCaseService.save(newTestCase);

        // Reset
        newTestCase = new TestCase();
        selectedRequirementId = null;

        return "/views/testcases/dashboard.xhtml?faces-redirect=true";
    }

    // =======================
    // READ (Dashboard)
    // =======================
    public List<TestCase> getTestCases() {
        return testCaseService.findAll();
    }

    // =======================
    // REQUIREMENTS für Dropdown
    // =======================
    public List<Requirement> getAllRequirements() {
        return requirementService.findAll();
    }

    // =======================
    // Getter / Setter
    // =======================
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