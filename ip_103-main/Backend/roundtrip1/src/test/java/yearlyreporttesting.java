import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.Model.YearlyReport;
import IP103_roundtrip1.roundtrip1.Roundtrip1Application;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import IP103_roundtrip1.roundtrip1.repository.YearlyReportRepo;
import IP103_roundtrip1.roundtrip1.controller.YearlyReportController;
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

@SpringJUnitConfig(classes = {Roundtrip1Application.class, YearlyReportController.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class yearlyreporttesting {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        // RestAssured.baseURI = "http://localhost:8080";
    }


    @Autowired
    UserRepo userRepo;

    @Autowired
    YearlyReportRepo yearlyReportRepo;

    @Test
    public void testGiveUserReport() {
        Users sampleUser = new Users();
        sampleUser.setId(1);

        userRepo.save(sampleUser);

        Response response = given()
                .port(port)
                .when()
                .get("/report/{userID}", 1);

        int statusCode = response.getStatusCode();
        assertEquals(HttpStatus.OK.value(), statusCode);
    }
}
