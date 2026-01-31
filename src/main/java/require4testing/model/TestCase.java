package require4testing.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "testcases")
public class TestCase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dbId; // technischer PK

	@Column(nullable = false, unique = true)
	private String id; // TC-001

	@Column(nullable = false)
	private String title;

	@Column(length = 2000)
	private String description;

	@ManyToOne(optional = false)
	@JoinColumn(name = "requirement_fk", nullable = false)
	private Requirement requirement;

	public TestCase() {
	}

	public TestCase(String id, String title, String description, Requirement requirement) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.requirement = requirement;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
}