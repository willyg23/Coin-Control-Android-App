package IP103_roundtrip1.roundtrip1.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Admin {



    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userID")
    private List<Users> usersList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "advisorID")
    private List<Advisor> advisorList;

    @Id
    @GeneratedValue
    private int id;

    private String username;

    private String password;

    private String email;

    public Admin(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Admin() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Advisor> getAdvisorList() {
        return advisorList;
    }

    public void setAdvisorList(List<Advisor> advisorList) {
        this.advisorList = advisorList;
    }
}
