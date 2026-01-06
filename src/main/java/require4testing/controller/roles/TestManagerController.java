package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.Requirement;
import require4testing.model.TestCase;
import require4testing.model.TestRun;
import require4testing.model.User;
import require4testing.service.TestCaseService;

@Named("testManagerController")
@SessionScoped
public class TestManagerController implements Serializable {

    private static final long serialVersionUID = 1L;

    // =======================
    // PROTOTYPISCHE LISTEN
    // =======================
    private List<TestRun> testRuns = new ArrayList<>();
    private List<User> testers = new ArrayList<>();

    // =======================
    // SERVICES
    // =======================
    @Inject
    private TestCaseService testCaseService;

    // =======================
    // AUSWAHLFELDER (JSF)
    // =======================
    private String selectedRequirementId;
    private Long selectedTestCaseDbId;
    private String selectedTester;

    public TestManagerController() {
        testers.add(new User("Tester1", "111", "tester"));
        testers.add(new User("Tester2", "222", "tester"));
        testers.add(new User("Tester3", "333", "tester"));
    }


    // =======================
    // TESTCASES AUS DB NACH REQUIREMENT
    // =======================
    public List<TestCase> getTestCasesForSelectedRequirement() {

        List<TestCase> list = new ArrayList<>();

        if (selectedRequirementId == null || selectedRequirementId.isEmpty()) {
            return list;
        }

        for (TestCase tc : testCaseService.findAll()) {
            if (tc.getRequirement() != null &&
                tc.getRequirement().getId().equals(selectedRequirementId)) {
                list.add(tc);
            }
        }
        return list;
    }

    // =======================
    // PROTOTYP: TESTRUN ANLEGEN
    // =======================
    public String saveTestRun() {

        if (selectedTestCaseDbId == null) {
            return null;
        }

        TestCase selectedTestCase =
                testCaseService.findByDbId(selectedTestCaseDbId);

        if (selectedTestCase == null) {
            return null;
        }

        TestRun tr = new TestRun();

        //NEU: fachliche TestRun-ID
        tr.setId(generateNextTestRunId());

        tr.setTestCase(selectedTestCase);
        tr.setTesterName(selectedTester);
        tr.setTestResult("OFFEN");

        testRuns.add(tr);

        // Reset
        selectedRequirementId = null;
        selectedTestCaseDbId = null;
        selectedTester = null;

        return "/views/testmanager/dashboard.xhtml?faces-redirect=true";
    }
    
    private String generateNextTestRunId() {
        int next = testRuns.size() + 1;
        return String.format("TR-%03d", next);
    }

    // =======================
    // GETTER & SETTER (JSF)
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

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public List<User> getTesters() {
        return testers;
    }
}