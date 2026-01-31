package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import require4testing.model.TestCase;
import require4testing.model.TestRun;
import require4testing.model.TestRunItem;
import require4testing.service.TestCaseService;
import require4testing.service.TestRunItemService;
import require4testing.service.TestRunService;

@Named("testManagerController")
@SessionScoped
public class TestManagerController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TestCaseService testCaseService;

	@Inject
	private TestRunService testRunService;

	@Inject
	private TestRunItemService testRunItemService;

	// Prototyp: feste Tester-Namen
	private static final List<String> TESTERS = List.of("Tester1", "Tester2", "Tester3");

	// Auswahlfelder (JSF)
	private String selectedRequirementId;
	private List<Long> selectedTestCaseDbIds = new ArrayList<>();
	private String selectedTester;

	// UI Events
	public void onRequirementChange() {
		selectedTestCaseDbIds.clear();
	}

	// READ: TestCases je Requirement
	public List<TestCase> getTestCasesForSelectedRequirement() {
		List<TestCase> list = new ArrayList<>();

		if (selectedRequirementId == null || selectedRequirementId.isBlank()) {
			return list;
		}

		for (TestCase tc : testCaseService.findAll()) {
			if (tc.getRequirement() != null && selectedRequirementId.equals(tc.getRequirement().getId())) {
				list.add(tc);
			}
		}
		return list;
	}

	// CREATE: 1 TestRun + mehrere Items
	public String saveTestRun() {

		if (selectedRequirementId == null || selectedRequirementId.isBlank()) {
			return null;
		}
		if (selectedTestCaseDbIds == null || selectedTestCaseDbIds.isEmpty()) {
			return null;
		}
		if (selectedTester == null || selectedTester.isBlank()) {
			return null;
		}

		TestRun tr = new TestRun();
		tr.setId(generateNextTestRunIdFromDb());
		tr.setTesterName(selectedTester);

		// TestRun zuerst speichern (dbId wird erzeugt)
		testRunService.save(tr);

		// Items je TestCase speichern
		for (Long tcDbId : selectedTestCaseDbIds) {
			TestCase tc = testCaseService.findByDbId(tcDbId);
			if (tc == null) {
				continue;
			}

			TestRunItem item = new TestRunItem();
			item.setTestRun(tr);
			item.setTestCase(tc);
			item.setTestResult("OFFEN");

			testRunItemService.save(item);
		}

		resetForm();

		return "/views/testmanager/dashboard.xhtml?faces-redirect=true";
	}

	// Helpers
	private void resetForm() {
		selectedRequirementId = null;
		selectedTestCaseDbIds.clear();
		selectedTester = null;
	}

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

	// Dashboard: TestRuns
	public List<TestRun> getTestRuns() {
		return testRunService.findAll();
	}

	// Getter / Setter
	public String getSelectedRequirementId() {
		return selectedRequirementId;
	}

	public void setSelectedRequirementId(String selectedRequirementId) {
		this.selectedRequirementId = selectedRequirementId;
	}

	public List<Long> getSelectedTestCaseDbIds() {
		return selectedTestCaseDbIds;
	}

	public void setSelectedTestCaseDbIds(List<Long> selectedTestCaseDbIds) {
		this.selectedTestCaseDbIds = selectedTestCaseDbIds;
	}

	public String getSelectedTester() {
		return selectedTester;
	}

	public void setSelectedTester(String selectedTester) {
		this.selectedTester = selectedTester;
	}

	public List<String> getTesters() {
		return TESTERS;
	}
}