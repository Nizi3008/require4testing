package require4testing.controller.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import require4testing.model.Requirement;

@Named("requirementsEngineerController")
@SessionScoped
public class RequirementsEngineerController implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Requirement> requirements = new ArrayList<>();  
    private Requirement newRequirement = new Requirement();

    public String saveRequirement() {

        int nextNumber = requirements.size() + 1;
        String generatedId = String.format("REQ-%03d", nextNumber); // REQ-001

        newRequirement.setId(generatedId);
        requirements.add(newRequirement);

        newRequirement = new Requirement();

        return "/views/requirements/dashboard.xhtml?faces-redirect=true";
    }
    
    public Requirement findRequirement(String id) {
        if (id == null) return null;

        for (Requirement r : requirements) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    // Getter
    public List<Requirement> getRequirements() {
        return requirements;
    }

    public Requirement getNewRequirement() {
        return newRequirement;
    }

    // Setter
    public void setNewRequirement(Requirement newRequirement) {
        this.newRequirement = newRequirement;
    }
}