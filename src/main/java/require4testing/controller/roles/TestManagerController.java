package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import require4testing.model.Requirement;
import require4testing.model.TestCase;
import require4testing.model.TestRun;
import require4testing.model.User;

@Named("testManagerController")
@SessionScoped
public class TestManagerController implements Serializable {

    private static final long serialVersionUID = 1L;

    // LISTEN
    private List<TestRun> testRuns = new ArrayList<>();
    private List<TestCase> availableTestCases = new ArrayList<>();
    private List<User> testers = new ArrayList<>();

    // Auswahlfelder aus createTestRun.xhtml
    private String selectedRequirementId;   // WICHTIG
    private String selectedTestCaseId;      // WICHTIG
    private String selectedTester;          // WICHTIG

    public TestManagerController() {
        testers.add(new User("Tester1", "111", "tester"));
        testers.add(new User("Tester2", "222", "tester"));
        testers.add(new User("Tester3", "333", "tester"));
    }

    // Vom TestCaseAuthorController aufgerufen
    public void addTestCase(TestCase tc) {
        System.out.println("ADD TC: " + tc.getId() + " / " + tc.getRequirementId());
        availableTestCases.add(tc);
    }

    // Für dashboard: Testfälle eines Requirements
    public List<TestCase> testCasesForRequirement(Requirement req) {
        List<TestCase> list = new ArrayList<>();

        if (req == null || req.getId() == null) return list;

        for (TestCase tc : availableTestCases) {
            if (tc.getRequirementId() != null &&
                tc.getRequirementId().equals(req.getId())) {
                list.add(tc);
            }
        }
        return list;
    }

    // Für createTestRun.xhtml
    public List<TestCase> getTestCasesForSelectedRequirement() {
        List<TestCase> list = new ArrayList<>();

        if (selectedRequirementId == null || selectedRequirementId.isEmpty()) {
            return list;
        }

        for (TestCase tc : availableTestCases) {
            if (tc.getRequirementId() != null &&
                    tc.getRequirementId().equals(selectedRequirementId)) {
                list.add(tc);
            }
        }
        return list;
    }

    // SPEICHERN DES TESTLAUFS
    public String saveTestRun() {

        // Testfall finden
        TestCase chosen = null;
        for (TestCase tc : availableTestCases) {
            if (tc.getId().equals(selectedTestCaseId)) {
                chosen = tc;
                break;
            }
        }

        TestRun tr = new TestRun();

        // ID generieren
        int nextId = testRuns.size() + 1;
        tr.setId("TR-" + String.format("%03d", nextId));

        tr.addTestCase(chosen);
        tr.setTesterName(selectedTester);

        testRuns.add(tr);

        // Reset
        selectedRequirementId = null;
        selectedTestCaseId = null;
        selectedTester = null;

        return "/views/testmanager/dashboard.xhtml?faces-redirect=true&includeViewParams=true";
    }

    // GETTER & SETTER – WICHTIG FÜR JSF!

    public String getSelectedRequirementId() {
        return selectedRequirementId;
    }

    public void setSelectedRequirementId(String selectedRequirementId) {
        this.selectedRequirementId = selectedRequirementId;
    }

    public String getSelectedTestCaseId() {
        return selectedTestCaseId;
    }

    public void setSelectedTestCaseId(String selectedTestCaseId) {
        this.selectedTestCaseId = selectedTestCaseId;
    }

    public String getSelectedTester() {
        return selectedTester;
    }

    public void setSelectedTester(String selectedTester) {
        this.selectedTester = selectedTester;
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public List<TestCase> getAvailableTestCases() {
        return availableTestCases;
    }

    public List<User> getTesters() {
        return testers;
    }
    
}