package require4testing.controller.roles;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import require4testing.model.Requirement;
import require4testing.service.RequirementService;

@Named("requirementsEngineerController")
@SessionScoped
public class RequirementsEngineerController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private RequirementService requirementService;

    private Requirement newRequirement = new Requirement();

    // Wird vom createRequirement.xhtml aufgerufen
    public String saveRequirement() {
        requirementService.save(newRequirement);
        // Formular-Bean zurücksetzen
        newRequirement = new Requirement();
        // Zur Dashboard-Seite der Requirements navigieren
        return "/views/requirements/dashboard.xhtml?faces-redirect=true";
    }

    // Für Tabelle im Dashboard
    public List<Requirement> getAllRequirements() {
        return requirementService.findAll();
    }

    // Getter/Setter
    public Requirement getNewRequirement() {
        return newRequirement;
    }

    public void setNewRequirement(Requirement newRequirement) {
        this.newRequirement = newRequirement;
    }
}