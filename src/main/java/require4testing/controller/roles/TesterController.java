package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.TestCase;
import require4testing.model.TestRun;

@Named("testerController")
@SessionScoped
public class TesterController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String testerName;

    private List<TestCase> assignedTestCases = new ArrayList<>();

    @Inject
    private TestManagerController testManagerController;

    // ========================
    // Tester setzt Namen
    // ========================
    public void setTesterName(String name) {
        this.testerName = name;
        reloadAssignedTestCases();
    }

    // ========================
    // Echte Testfälle laden
    // ========================
    private void reloadAssignedTestCases() {

        assignedTestCases.clear();

        for (TestRun tr : testManagerController.getTestRuns()) {

            if (tr.getTesterName() != null && tr.getTesterName().equals(testerName)) {

                assignedTestCases.addAll(tr.getTestCases());
            }
        }
    }

    // ========================
    // Ergebnis direkt im Dashboard speichern
    // ========================
    public void updateResult(TestCase tc, String result) {
        tc.setTestResult(result);
    }

    public List<TestCase> getAssignedTestCases() {
        reloadAssignedTestCases();   // <<< jedes Mal aktualisieren
        return assignedTestCases;
    }
}