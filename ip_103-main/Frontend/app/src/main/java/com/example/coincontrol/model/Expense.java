package com.example.coincontrol.model;

public class Expense {

    private int id;
    private double expenseAmount;
    private int dayOfWeek;
    private int weekNumber;
    private double totalWeeklyExpenses;
    private double totalWeeklySavings;
    private double weeklyAllowanceGoal;
    private double weeklySavingsGoal;
    private User user;

    public Expense() {

    }

    public double getWeeklyAllowanceGoal() {
        return weeklyAllowanceGoal;
    }

    public void setWeeklyAllowanceGoal(double weeklyAllowanceGoal) {
        this.weeklyAllowanceGoal = weeklyAllowanceGoal;
    }

    public double getWeeklySavingsGoal() {
        return weeklySavingsGoal;
    }

    public void setWeeklySavingsGoal(double weeklySavingsGoal) {
        this.weeklySavingsGoal = weeklySavingsGoal;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public double getTotalWeeklyExpenses() {
        return totalWeeklyExpenses;
    }

    public void setTotalWeeklyExpenses(double totalWeeklyExpenses) {
        this.totalWeeklyExpenses = totalWeeklyExpenses;
    }

    public double getTotalWeeklySavings() {
        return totalWeeklySavings;
    }

    public void setTotalWeeklySavings(double totalWeeklySavings) {
        this.totalWeeklySavings = totalWeeklySavings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


