package IP103_roundtrip1.roundtrip1.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double expenseAmount;
    private int dayOfWeek;
    private int weekNumber;

    private double totalWeeklyExpenses;
    private double totalWeeklySavings;
    private double weeklyAllowanceGoal;
    private double weeklySavingsGoal;



    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;


    public Expense(double expenseAmount, int dayOfWeek, int weekNumber) {
        this.expenseAmount = expenseAmount;
        this.dayOfWeek = dayOfWeek;
        this.weekNumber = weekNumber;
    }

    public Expense() {

    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalWeeklyExpenses() {
        return totalWeeklyExpenses;
    }

    public void setTotalWeeklyExpenses(double totalWeeklyExpenses) {
        this.totalWeeklyExpenses = totalWeeklyExpenses;
    }

    public double getWeeklyAllowanceGoal() { return weeklyAllowanceGoal; }

    public double getWeeklySavingsGoal() { return weeklySavingsGoal; }

    public void setWeeklyAllowanceGoal(double weeklyAllowanceGoal) { this.weeklyAllowanceGoal = weeklyAllowanceGoal; }

    public void setWeeklySavingsGoal(double weeklySavingsGoal) { this.weeklySavingsGoal = weeklySavingsGoal; }

    public double getTotalWeeklySavings() {
        return totalWeeklySavings;
    }

    public void setTotalWeeklySavings(double totalWeeklySavings) {
        this.totalWeeklySavings = totalWeeklySavings;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}

