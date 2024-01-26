package IP103_roundtrip1.roundtrip1.controller;
import IP103_roundtrip1.roundtrip1.Model.Expense;
import IP103_roundtrip1.roundtrip1.Model.Goals;
import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.repository.ExpenseRepo;
import IP103_roundtrip1.roundtrip1.repository.GoalsRepo;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class HomepageController {

    private final List<Expense> expenses = new ArrayList<>();
    Expense expense = new Expense();

    @Autowired
    ExpenseRepo expenseRepo;

    @Autowired
    GoalsRepo goalsRepo;

    @Autowired
    UserRepo userRepo;
    public int gid = 0;

    @PostConstruct
    public void initialize() {
        //    gid = userRepo.findById(1).getId();
        //    expenseRepo.save(new Expense(100.00, 1, 1, gid));  // Week 1, Day 1 (Sunday)
        //    expenseRepo.save(new Expense(50.0, 2, 2, gid));   // Week 2, Day 2 (Monday)
        //    expenseRepo.save(new Expense(75.0, 3, 2, gid));   // Week 2, Day 3 (Tuesday)
        //    expenseRepo.save(new Expense(60.0, 4, 3, gid));   // Week 3, Day 4 (Wednesday)
        //    expenseRepo.save(new Expense(90.0, 5, 3, gid));   // Week 3, Day 5 (Thursday)
    }

    @Operation(summary = "Get Weekly Expense Amount",
            description = "Retrieves the total expense amount for a specific week.",
            tags = { "expense", "get" })
    @GetMapping("/weeklyExpenseAmount/{weekNumber}")
    public double getWeeklyExpenseAmount(@PathVariable int weekNumber) {
        // Get all expenses for the specified week number from the database
        List<Expense> expensesForWeek = expenseRepo.findByWeekNumber(weekNumber);

        // Calculate the total expense amount for the week
        double totalExpenseAmount = expensesForWeek.stream()
                .mapToDouble(Expense::getExpenseAmount)
                .sum();

        return totalExpenseAmount;
    }

    @Operation(summary = "Get Current Weekly Expense Amount",
            description = "Retrieves the total expense amount for the current week.",
            tags = { "expense", "get" })
    @GetMapping("/currentWeeklyExpenseAmount")
    public double getCurrentWeeklyExpenseAmount() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the week number based on your desired week numbering system
        int weekNumber = calculateWeekNumber(currentDate);

        // Get all expenses for the current week from the database
        List<Expense> expensesForWeek = expenseRepo.findByWeekNumber(weekNumber);

        // Calculate the total expense amount for the week
        double totalCurrentExpenseAmount = expensesForWeek.stream()
                .mapToDouble(Expense::getExpenseAmount)
                .sum();

        return totalCurrentExpenseAmount;
    }


    @Operation(summary = "Add New Expense",
            description = "Adds a new expense to the database.",
            tags = { "expense", "post" })
    @PostMapping("/newExpense")
    public Expense UserExpense(@RequestBody Expense expenseR) {
        expenseRepo.save(expenseR);
        return expenseR;
    }

    /**
     * Helper method that calculates the week number for a given date based on Sunday as the start of the week.
     *
     * @param date The date for which to calculate the week number.
     * @return The calculated week number.
     */
    private int calculateWeekNumber(LocalDate date) {
        // Get the day of the week for the given date (Sunday is 7, Monday is 1, etc.)
        int dayOfWeek = date.getDayOfWeek().getValue();

        // Calculate the week number based on Sunday as the start of the week
        // Week number increases on Sundays
        int weekNumber = date.get(WeekFields.SUNDAY_START.weekOfYear());

        // If the current day of the week is not Sunday (i.e., dayOfWeek is not 7),
        // increment the week number by 1 to align with your week numbering system
        if (dayOfWeek != 7) {
            weekNumber++;
        }

        return weekNumber;
    }

    @Operation(summary = "Update Weekly Expenses and Savings",
            description = "Calculates and updates the user's weekly expenses and savings based on the new expense.",
            tags = { "expense", "put" })
    @PutMapping("/addExpense/{userID}")
    public ArrayList<Double> addExpense(@PathVariable int userID, @RequestBody Expense oldExpense) {
        LocalDate currentDate = LocalDate.now();
        Users user = userRepo.findById(userID);
        int weekNumber = calculateWeekNumber(currentDate);
        double totalWeeklyExpense = user.getExpenses().get(user.getExpenses().size()-1).getTotalWeeklyExpenses();
        totalWeeklyExpense += oldExpense.getExpenseAmount();

        double totalWeeklySaving = user.getExpenses().get(user.getExpenses().size()-1).getWeeklySavingsGoal();
        user.getExpenses().get(user.getExpenses().size()-1).setTotalWeeklyExpenses(totalWeeklyExpense);
        user.getExpenses().get(user.getExpenses().size()-1).setExpenseAmount(0);
        user.getExpenses().set(user.getExpenses().size()-1,user.getExpenses().get(user.getExpenses().size()-1));
        if (user.getExpenses().get(user.getExpenses().size()-1).getWeeklyAllowanceGoal() < totalWeeklyExpense) {
            totalWeeklySaving = totalWeeklySaving - (totalWeeklyExpense - user.getExpenses().get(user.getExpenses().size()-1).getWeeklyAllowanceGoal());
            user.getExpenses().set(user.getExpenses().size()-1,user.getExpenses().get(user.getExpenses().size()-1)).setTotalWeeklySavings(totalWeeklySaving);
        } else {
            user.getExpenses().set(user.getExpenses().size()-1,user.getExpenses().get(user.getExpenses().size()-1)).setTotalWeeklySavings(totalWeeklySaving);
        }
        userRepo.save(user);


        ArrayList<Double> result = new ArrayList<>();
        result.add(totalWeeklyExpense);
        result.add(totalWeeklySaving);
        return result;
    }


    @Operation(summary = "Get All Expenses",
            description = "Retrieves all expenses from the database.",
            tags = { "expense", "get" })
    @GetMapping("/allExpenses")
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }


    @Operation(summary = "Calculate User Goals",
            description = "Calculates and returns the user's current expense information.",
            tags = { "expense", "get" })
    @GetMapping("/calculateGoals/{userID}")
    public Expense calculation(@PathVariable int userID) {
        LocalDate currentDate = LocalDate.now();
        int weekNum = calculateWeekNumber(currentDate);
        // Calculate weekly income (Monthly Income / 4)
        int Goals_id = 1;
        int user_id = 0;
        Users user = userRepo.findById(userID);
        user_id = user.getId();
        Goals goals = goalsRepo.findById(user_id);
        List<Expense> expenseList = userRepo.findById(user_id).getExpenses();
        if(!expenseList.isEmpty()) {
            Expense currentExpense = expenseList.get(expenseList.size()-1);
            if (currentExpense.getWeekNumber() == calculateWeekNumber(LocalDate.now())) {
                currentExpense.setDayOfWeek(currentDate.getDayOfWeek().getValue());
                return currentExpense;
            }
        }

        Expense newExpense = new Expense();

        double currentBankBalance = goals.getCurrentBankBalance();
        double desiredBankBalance = goals.getDesiredBankBalance();
        double monthlyIncome = goals.getMonthlyIncome();

        double weeklyIncome = monthlyIncome / 4;
        double weeksUntilGoal = goals.getTimeEstimate();

        // Calculate the weekly savings goal (Desired Bank Balance - Current Bank Balance) / Number of weeks until goal
        double weeklySavingsGoal = (desiredBankBalance - currentBankBalance) / weeksUntilGoal;

        // Calculate the weekly allowance (Weekly Income - Weekly Savings Goal)
        double weeklyAllowance = weeklyIncome - weeklySavingsGoal;

        newExpense.setWeeklySavingsGoal(weeklySavingsGoal);
        newExpense.setWeeklyAllowanceGoal(weeklyAllowance);
        newExpense.setUser(user);
        newExpense.setTotalWeeklySavings(weeklySavingsGoal);
        newExpense.setWeekNumber(weekNum);
        newExpense.setDayOfWeek(currentDate.getDayOfWeek().getValue());
        user.addExpenses(newExpense);
        expenseRepo.save(newExpense);
        userRepo.save(user);
        return newExpense;
    }

}
