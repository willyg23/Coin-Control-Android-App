package IP103_roundtrip1.roundtrip1.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnore
    private Admin admin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String firstName;

    private int responseID;

    private String lastName;

    private String email;

    private String userName;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "goalsID")
    private Goals goals;




    @OneToOne
    @JoinColumn(name = "Yreport_ID")
    private YearlyReport userYearlyReport;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "expenseID")
    private List<Expense> expenses;

    public Users(String firstName, String lastName, String Email, String UserName, String password, int responseID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = Email;
        this.password = password;
        this.userName = UserName;
        expenses = new ArrayList<>();
    }

    public Users() {
        expenses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpenses(Expense expenses) {
        this.expenses.add(expenses);
    }

    public YearlyReport getUserYearlyReport() {
        return userYearlyReport;
    }

    public void setUserYearlyReport(YearlyReport userYearlyReport) {
        this.userYearlyReport = userYearlyReport;
    }
}

