package require4testing.model;

public class User {
    String name;
    String passwort;
    String role;   

    public User() {}

    public User(String name, String passwort, String role) {
        this.name = name;
        this.passwort = passwort;
        this.role = role;
    }

    public User(String name, String passwort) {
        this.name = name;
        this.passwort = passwort;
    }

    // Getter + Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPasswort() { return passwort; }
    public void setPasswort(String passwort) { this.passwort = passwort; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User u = (User) obj;
            return u.getName().equals(this.name) &&
                   u.getPasswort().equals(this.passwort);
        }
        return false;
    }
}