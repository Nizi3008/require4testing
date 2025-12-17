package require4testing.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "requirements")
public class Requirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId; // technische Primär-ID

    @Column(name = "requirement_id", unique = true, nullable = false)
    private String id; // fachliche ID: REQ-001

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    public Requirement() {
    }

    public Requirement(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // --- Getter & Setter ---

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
}