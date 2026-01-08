package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.TestRun;
import require4testing.service.TestRunService;

@Named("testerController")
@SessionScoped
public class TesterController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TestRunService testRunService;

    private String testerName;
    private List<TestRun> assignedTestRuns = new ArrayList<>();

    public void setTesterName(String testerName) {
        this.testerName = testerName;
        reload();
    }

    public String getTesterName() {
        return testerName;
    }

    public List<TestRun> getAssignedTestRuns() {
        return assignedTestRuns;
    }

    public void reload() {
        if (testerName == null || testerName.isBlank()) {
            assignedTestRuns = new ArrayList<>();
        } else {
            assignedTestRuns = testRunService.findByTesterName(testerName);
        }
    }

    public void saveResult(Long dbId, String result) {
        if (dbId == null || result == null || result.isBlank()) return;
        testRunService.updateResult(dbId, result);
        reload();
    }
}