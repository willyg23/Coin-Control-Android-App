import IP103_roundtrip1.roundtrip1.Model.Goals;
import IP103_roundtrip1.roundtrip1.Roundtrip1Application;
import IP103_roundtrip1.roundtrip1.repository.GoalsRepo;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import IP103_roundtrip1.roundtrip1.controller.GoalController;
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

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {Roundtrip1Application.class, GoalController.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class goaltesting {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        // RestAssured.baseURI = "http://localhost:8080";
    }


    @Autowired
    GoalsRepo goalsRepo;

    @Autowired
    UserRepo userRepo;

    @Test
    public void testGetAllUsersGoals() {
        Response response = given()
                .port(port)
                .when()
                .get("/goals/all");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testGetUserById() {
        Goals sampleGoal = new Goals();
        sampleGoal.setId(1);

        goalsRepo.save(sampleGoal);

        Response response = given()
                .port(port)
                .when()
                .get("/goals/{id}", 1);

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testUserGoals() {
        Goals newGoal = new Goals();
        newGoal.setTimeEstimate(26); // Assuming 26 weeks for simplicity

        Response response = given()
                .contentType(ContentType.JSON)
                .body(newGoal)
                .when()
                .post("/goals/post");

        int statusCode = response.getStatusCode();
        assertEquals(HttpStatus.OK.value(), statusCode);
    }
}
