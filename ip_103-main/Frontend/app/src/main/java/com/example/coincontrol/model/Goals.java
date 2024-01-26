package com.example.coincontrol.model;
import com.example.coincontrol.model.User;
public class Goals {

    private int id;

    private User users;

    private int timeEstimate;

    private double monthlyIncome;

    private double currentBankBalance;

    private double desiredBankBalance;

    public int getId() {
        return id;
    }

    public Goals() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
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
}
