import IP103_roundtrip1.roundtrip1.Model.Expense;
import IP103_roundtrip1.roundtrip1.Model.Goals;
import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.Roundtrip1Application;
import IP103_roundtrip1.roundtrip1.controller.HomepageController;
import IP103_roundtrip1.roundtrip1.repository.ExpenseRepo;
import IP103_roundtrip1.roundtrip1.repository.GoalsRepo;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(Roundtrip1Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class homepagetesting {

    @LocalServerPort
    int port;

    @Autowired
    UserRepo userRepo;

    @Autowired
    GoalsRepo goalsRepo;

    @Autowired
    ExpenseRepo expenseRepo;

    @Autowired
    private HomepageController homepageController;

    @BeforeEach
    public void setUp() {
        // You can add initialization logic here if needed
    }

    @Test
    public void testGetWeeklyExpenseAmountWithExpenses() {
        Response response = given()
                .port(port)
                .when()
                .get("/weeklyExpenseAmount/{weekNumber}", 1);

        System.out.println("Response body: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        double totalExpenseAmount = response.getBody().as(Double.class);
        assertEquals(0.0, totalExpenseAmount);
    }

    @Test
    public void testUserExpense() {
        Expense sampleExpense = new Expense();
        sampleExpense.setExpenseAmount(50.0);

        Response response = given()
                .port(port)
                .contentType("application/json")
                .body(sampleExpense)
                .when()
                .post("/newExpense");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testAddExpenseAndUpdateWeeklyStats() {
        Users testUser = new Users();
        testUser.setUserName("testUser");
        Expense expense = new Expense();
        expense.setExpenseAmount(50.0);
        testUser.setExpenses(new ArrayList<>());
        testUser.getExpenses().add(expense);

        userRepo.save(testUser);

        LocalDate currentDate = LocalDate.now();

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(expense)
                .when()
                .put("/addExpense/testUser");

        int statusCode = response.getStatusCode();
        assertEquals(HttpStatus.OK.value(), statusCode);

        List<Double> result = response.jsonPath().getList("", Double.class);
        ArrayList<Double> resultList = new ArrayList<>(result);

        double totalWeeklyExpense = resultList.get(0);
        double totalWeeklySaving = resultList.get(1);

        double expectedTotalWeeklyExpense = 60.0;
        double expectedTotalWeeklySaving = 200.0;

        assertEquals(expectedTotalWeeklyExpense, totalWeeklyExpense, 0.01);
        assertEquals(expectedTotalWeeklySaving, totalWeeklySaving, 0.01);
    }

    @Test
    public void testGetAllExpenses() {
        List<Expense> allExpensesInDatabase = expenseRepo.findAll();
        List<Expense> expectedExpenses = new ArrayList<>(allExpensesInDatabase);
        List<Expense> actualExpenses = homepageController.getAllExpenses();
        assertEquals(expectedExpenses.size(), actualExpenses.size());
    }

    @Test
    public void testCalculation() {
        Users user = new Users();
        user.setId(1);
        user.setUserName("testUser");

        Goals goals = new Goals();
        goals.setId(1);
        goals.setCurrentBankBalance(1000.0);
        goals.setDesiredBankBalance(1500.0);
        goals.setMonthlyIncome(4000.0);
        goals.setTimeEstimate(4);

        List<Expense> expenses = Collections.emptyList();

        Expense result = homepageController.calculation(user.getId());

        assertEquals(1, result.getUser().getId());
        assertEquals(1500.0, result.getWeeklySavingsGoal());
        assertEquals(250.0, result.getWeeklyAllowanceGoal());
    }
}
