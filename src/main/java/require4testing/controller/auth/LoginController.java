package require4testing.controller.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.controller.roles.TesterController;
import require4testing.model.User;

@Named
@ViewScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    User user;

    List<User> userListe;

    // Inject TesterController für später
    @Inject
    private TesterController testerController;

    public LoginController() {
        this.userListe = new ArrayList<User>();

        // Testmanager
        this.userListe.add(new User("Testmanager", "111", "testmanager"));

        // Mehrere Tester
        this.userListe.add(new User("Tester1", "111", "tester"));
        this.userListe.add(new User("Tester2", "111", "tester"));
        this.userListe.add(new User("Tester3", "111", "tester"));

        // Requirements Engineer
        this.userListe.add(new User("RE", "111", "requirementsEngineer"));

        // Testfallersteller
        this.userListe.add(new User("Testauthor", "111", "testCaseAuthor"));

        this.user = new User();
    }

    public void postValidateName(ComponentSystemEvent ev) throws AbortProcessingException {
        UIInput temp = (UIInput) ev.getComponent();
        this.name = (String) temp.getValue();
    }

    public void validateLogin(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

        String enteredPassword = (String) value;

        for (User u : userListe) {
            if (u.getName().equals(this.name) && u.getPasswort().equals(enteredPassword)) {
                this.user = u;
                return;
            }
        }

        throw new ValidatorException(new FacesMessage("Login falsch!"));
    }

    public String login() {

        if (user == null || user.getRole() == null) {
            return null;
        }

        switch (user.getRole()) {

            case "requirementsEngineer":
                return "/views/requirements/dashboard.xhtml?faces-redirect=true";

            case "testCaseAuthor":
                return "/views/testcases/dashboard.xhtml?faces-redirect=true";

            case "tester":
            	testerController.setTesterName(user.getName());
            	return "/views/tester/dashboard.xhtml?faces-redirect=true";

            case "testmanager":
                return "/views/testmanager/dashboard.xhtml?faces-redirect=true";

            default:
                return null;
        }
    }

    // Getter / Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}