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

	// CREATE
	public String saveTestCase() {

		Requirement requirement = requirementService.findByBusinessId(selectedRequirementId);
		newTestCase.setRequirement(requirement);

		String generatedId = String.format("TC-%03d", testCaseService.findAll().size() + 1);
		newTestCase.setId(generatedId);

		testCaseService.save(newTestCase);

		resetForm();

		return "/views/testcases/dashboard.xhtml?faces-redirect=true";
	}

	// READ
	public List<TestCase> getTestCases() {
		return testCaseService.findAll();
	}

	public List<Requirement> getAllRequirements() {
		return requirementService.findAll();
	}

	// Helpers
	private void resetForm() {
		newTestCase = new TestCase();
		selectedRequirementId = null;
	}

	// Getter / Setter
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