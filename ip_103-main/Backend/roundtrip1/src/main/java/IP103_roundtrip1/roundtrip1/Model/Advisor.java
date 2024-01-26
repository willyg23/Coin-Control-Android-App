package IP103_roundtrip1.roundtrip1.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Advisor {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "Adminid")
    @JsonIgnore
    private Admin admin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String advisorUsername;

    private String advisorPassword;

    private String firstname;

    private String lastname;

    private String email;

    public Advisor() {
    }

    public Advisor(String advisorUsername, String password, String firstname, String lastname, String email) {
        this.advisorUsername = advisorUsername;
        this.advisorPassword = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdvisorUsername() {
        return advisorUsername;
    }

    public void setAdvisorUsername(String username) {
        this.advisorUsername = username;
    }

    public String getAdvisorPassword() {
        return advisorPassword;
    }

    public void setAdvisorPassword(String password) {
        this.advisorPassword = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
