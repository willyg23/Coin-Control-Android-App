package com.example.coincontrol.model;

public class YearlyReport
{
    private int id;
    private User user;
    private double initialBalance;
    private double desiredBalance;
    private double totalExpenses;
    private double totalSavings;


    public YearlyReport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getDesiredBalance() {
        return desiredBalance;
    }

    public void setDesiredBalance(double desiredBalance) {
        this.desiredBalance = desiredBalance;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
    }
}
