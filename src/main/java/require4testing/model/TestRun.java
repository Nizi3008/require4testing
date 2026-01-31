package require4testing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "testruns")
public class TestRun implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dbId;

	@Column(nullable = false, unique = true)
	private String id; // TR-001

	@Column(nullable = false)
	private String testerName;

	@OneToMany(mappedBy = "testRun", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TestRunItem> items = new ArrayList<>();

	public TestRun() {
	}

	public Long getDbId() {
		return dbId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	public List<TestRunItem> getItems() {
		return items;
	}

	public void setItems(List<TestRunItem> items) {
		this.items = (items != null) ? items : new ArrayList<>();
	}

	public void addItem(TestRunItem item) {
		if (item == null)
			return;
		items.add(item);
		item.setTestRun(this);
	}

	public void removeItem(TestRunItem item) {
		if (item == null)
			return;
		items.remove(item);
		item.setTestRun(null);
	}
}