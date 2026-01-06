package require4testing.controller.roles;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.TestCase;
import require4testing.model.TestRun;
import require4testing.service.TestCaseService;
import require4testing.service.TestRunService;

@Named("testerController")
@SessionScoped
public class TesterController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TestCaseService testCaseService;

    @Inject
    private TestRunService testRunService;

    private TestRun newTestRun = new TestRun();
    private Long selectedTestCaseDbId;

    // =======================
    // TESTLAUF SPEICHERN
    // =======================
    public String saveTestRun() {

        TestCase tc = testCaseService.findByDbId(selectedTestCaseDbId);
        newTestRun.setTestCase(tc);

        testRunService.save(newTestRun);

        newTestRun = new TestRun();
        selectedTestCaseDbId = null;

        return "/views/tester/dashboard.xhtml?faces-redirect=true";
    }

    // =======================
    // GETTER
    // =======================
    public List<TestCase> getTestCases() {
        return testCaseService.findAll();
    }

    public TestRun getNewTestRun() {
        return newTestRun;
    }

    public Long getSelectedTestCaseDbId() {
        return selectedTestCaseDbId;
    }

    public void setSelectedTestCaseDbId(Long selectedTestCaseDbId) {
        this.selectedTestCaseDbId = selectedTestCaseDbId;
    }
}