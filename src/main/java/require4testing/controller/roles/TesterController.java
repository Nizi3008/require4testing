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

	public void saveResult(Long itemDbId, String result) {
		if (itemDbId == null || result == null || result.isBlank())
			return;
		testRunItemService.updateResult(itemDbId, result);
		reload();
	}

	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
		reload();
	}
}