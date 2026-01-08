package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.TestCase;
import require4testing.model.TestRun;
import require4testing.model.User;
import require4testing.service.TestCaseService;
import require4testing.service.TestRunService;

@Named("testManagerController")
@SessionScoped
public class TestManagerController implements Serializable {

    private static final long serialVersionUID = 1L;

    // Fixe Tester-Liste (Prototype)
    private final List<User> testers = new ArrayList<>();

    @Inject
    private TestCaseService testCaseService;

    @Inject
    private TestRunService testRunService;

    // Auswahlfelder (JSF)
    private String selectedRequirementId;
    private Long selectedTestCaseDbId;
    private String selectedTester;

    public TestManagerController() {
        testers.add(new User("Tester1", "111", "tester"));
        testers.add(new User("Tester2", "222", "tester"));
        testers.add(new User("Tester3", "333", "tester"));
    }

    // =======================
    // Dropdown: TestCases je Requirement (aus DB)
    // =======================
    public List<TestCase> getTestCasesForSelectedRequirement() {
        List<TestCase> list = new ArrayList<>();

        if (selectedRequirementId == null || selectedRequirementId.isBlank()) {
            return list;
        }

        for (TestCase tc : testCaseService.findAll()) {
            if (tc.getRequirement() != null
                    && selectedRequirementId.equals(tc.getRequirement().getId())) {
                list.add(tc);
            }
        }
        return list;
    }

    // =======================
    // TestRun speichern (persistieren!)
    // =======================
    public String saveTestRun() {

        // Pflichtfelder prüfen
        if (selectedTestCaseDbId == null) {
            return null;
        }
        if (selectedTester == null || selectedTester.isBlank()) {
            return null;
        }

        // TestCase laden
        TestCase selectedTestCase = testCaseService.findByDbId(selectedTestCaseDbId);
        if (selectedTestCase == null) {
            return null;
        }

        // TestRun bauen
        TestRun tr = new TestRun();
        tr.setId(generateNextTestRunIdFromDb()); // TR-###
        tr.setTestCase(selectedTestCase);
        tr.setTesterName(selectedTester);
        tr.setTestResult("OFFEN");

        // Persistieren
        testRunService.save(tr);

        // Reset
        selectedRequirementId = null;
        selectedTestCaseDbId = null;
        selectedTester = null;

        return "/views/testmanager/dashboard.xhtml?faces-redirect=true";
    }

    // =======================
    // Business-ID aus DB ableiten (TR-###)
    // =======================
    private String generateNextTestRunIdFromDb() {
        String max = testRunService.findMaxBusinessId(); // z.B. "TR-014"
        int next = 1;

        if (max != null && max.startsWith("TR-") && max.length() >= 6) {
            try {
                next = Integer.parseInt(max.substring(3)) + 1;
            } catch (NumberFormatException ignored) {
                next = 1;
            }
        }
        return String.format("TR-%03d", next);
    }

    // =======================
    // Dashboard: Liste aus DB
    // =======================
    public List<TestRun> getTestRuns() {
        return testRunService.findAll();
    }

    // =======================
    // Getter/Setter (JSF)
    // =======================
    public String getSelectedRequirementId() {
        return selectedRequirementId;
    }

    public void setSelectedRequirementId(String selectedRequirementId) {
        this.selectedRequirementId = selectedRequirementId;
    }

    public Long getSelectedTestCaseDbId() {
        return selectedTestCaseDbId;
    }

    public void setSelectedTestCaseDbId(Long selectedTestCaseDbId) {
        this.selectedTestCaseDbId = selectedTestCaseDbId;
    }

    public String getSelectedTester() {
        return selectedTester;
    }

    public void setSelectedTester(String selectedTester) {
        this.selectedTester = selectedTester;
    }

    public List<User> getTesters() {
        return testers;
    }
}