package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import require4testing.model.TestRunItem;
import require4testing.service.TestRunItemService;

@Named("testerController")
@SessionScoped
public class TesterController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TestRunItemService testRunItemService;

	private String testerName;
	private List<TestRunItem> assignedItems = new ArrayList<>();

	// READ
	public void reload() {
		if (testerName == null || testerName.isBlank()) {
			assignedItems = new ArrayList<>();
			return;
		}
		assignedItems = testRunItemService.findByTesterName(testerName);
	}

	public List<TestRunItem> getAssignedItems() {
		return assignedItems;
	}

	// SAVE
	public void saveAllResults() {
		if (assignedItems == null || assignedItems.isEmpty()) {
			return;
		}

		for (TestRunItem it : assignedItems) {
			if (it == null || it.getDbId() == null) {
				continue;
			}
			String res = it.getTestResult();
			if (res == null || res.isBlank()) {
				continue;
			}
			testRunItemService.updateResult(it.getDbId(), res);
		}

		reload();
	}

	// Getter / Setter
	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
		reload();
	}
}