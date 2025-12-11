package require4testing.controller.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import require4testing.controller.roles.TesterController;
import require4testing.model.User;

@Named
@ViewScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private User user;

    private List<User> userListe;

    @Inject
    private TesterController testerController;

    public LoginController() {
        this.userListe = new ArrayList<>();

        // Dummy-User für Prototyp
        userListe.add(new User("Testmanager", "111", "testmanager"));
        userListe.add(new User("Tester1", "111", "tester"));
        userListe.add(new User("Tester2", "111", "tester"));
        userListe.add(new User("Tester3", "111", "tester"));
        userListe.add(new User("RE", "111", "requirementsEngineer"));
        userListe.add(new User("Testauthor", "111", "testCaseAuthor"));

        this.user = new User();
    }

    public String login() {

        for (User u : userListe) {
            if (u.getName().equals(username) && u.getPasswort().equals(password)) {
                this.user = u;

                switch (u.getRole()) {
                    case "requirementsEngineer":
                        return "/views/requirements/dashboard.xhtml?faces-redirect=true";

                    case "testCaseAuthor":
                        return "/views/testcases/dashboard.xhtml?faces-redirect=true";

                    case "tester":
                        testerController.setTesterName(user.getName());
                        return "/views/tester/dashboard.xhtml?faces-redirect=true";

                    case "testmanager":
                        return "/views/testmanager/dashboard.xhtml?faces-redirect=true";
                }
            }
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Login falsch!"));
        return null;
    }

    // Getter & Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}