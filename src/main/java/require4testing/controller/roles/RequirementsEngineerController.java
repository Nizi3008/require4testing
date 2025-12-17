package require4testing.controller.roles;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.model.Requirement;
import require4testing.service.RequirementService;

@Named("requirementsEngineerController")
@SessionScoped
public class RequirementsEngineerController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private RequirementService requirementService;

    private Requirement newRequirement = new Requirement();

    // --- CREATE ---
    public String saveRequirement() {

        // ID generieren (DB-unabhängig)
        String generatedId = requirementService.generateNextRequirementId();
        newRequirement.setId(generatedId);

        requirementService.save(newRequirement);

        newRequirement = new Requirement();

        return "/views/requirements/dashboard.xhtml?faces-redirect=true";
    }

    // --- READ ---
    public Requirement findRequirement(String id) {
        return requirementService.findByBusinessId(id);
    }

    public List<Requirement> getRequirements() {
        return requirementService.findAll();
    }

    // --- Getter / Setter ---
    public Requirement getNewRequirement() {
        return newRequirement;
    }

    public void setNewRequirement(Requirement newRequirement) {
        this.newRequirement = newRequirement;
    }
}