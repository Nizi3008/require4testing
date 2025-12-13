package require4testing.model;

import java.io.Serializable;


public class Requirement implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;          // REQ-001
    private String title;
    private String description;

    public Requirement() {
    }

    public Requirement(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // --- Getter & Setter ---

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