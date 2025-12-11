package require4testing.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "requirements")
public class Requirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String id;          // z.B. "REQ-001" (vom User vergeben)

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    public Requirement() {
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