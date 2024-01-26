package IP103_roundtrip1.roundtrip1.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Goals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JsonIgnore
    private Users users;


    private int timeEstimate;

    private double monthlyIncome;

    private double currentBankBalance;

    private double desiredBankBalance;

    public Goals() {
    }

    public Goals(int timeEstimate, double monthlyIncome, double currentBankBalance, double desiredBankBalance) {
        this.timeEstimate = timeEstimate;
        this.monthlyIncome = monthlyIncome;
        this.currentBankBalance = currentBankBalance;
        this.desiredBankBalance = desiredBankBalance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getCurrentBankBalance() {
        return currentBankBalance;
    }

    public void setCurrentBankBalance(double currentBankBalance) {
        this.currentBankBalance = currentBankBalance;
    }

    public double getDesiredBankBalance() {
        return desiredBankBalance;
    }

    public void setDesiredBankBalance(double desiredBankBalance) {
        this.desiredBankBalance = desiredBankBalance;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}

