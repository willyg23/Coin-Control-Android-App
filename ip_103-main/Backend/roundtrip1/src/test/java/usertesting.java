import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.Roundtrip1Application;
import IP103_roundtrip1.roundtrip1.repository.AdminRepo;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import IP103_roundtrip1.roundtrip1.Services.UserServices;
import IP103_roundtrip1.roundtrip1.controller.UserController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {Roundtrip1Application.class, UserController.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class usertesting {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        // RestAssured.baseURI = "http://localhost:8080";
    }


    @Autowired
    AdminRepo adminRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserServices userServices;

    @Test
    public void testGetAllUsers() {
        Response response = given()
                .port(port)
                .when()
                .get("/user/all");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testGetUserById() {
        Users sampleUser = new Users();
        sampleUser.setId(1);

        userRepo.save(sampleUser);

        Response response = given()
                .port(port)
                .when()
                .get("/finduser/{userID}", 1);

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testGetUserByUsername() {
        Users sampleUser = new Users();
        sampleUser.setUserName("testUser");

        userRepo.save(sampleUser);

        Response response = given()
                .port(port)
                .when()
                .get("/findusername/{username}", "testUser");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testCheckUsername() throws JSONException {
        JSONObject userJson = new JSONObject();
        userJson.put("username", "newUser");
        userJson.put("email", "newUser@example.com");
        userJson.put("id", 2);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userJson.toString())
                .when()
                .post("/user/register");

        response.then().statusCode(200);

        int result = response.getBody().as(Integer.class);

        assertEquals(3, result);
    }


    @Test
    public void testUserLogin() throws JSONException {

        JSONObject userJson = new JSONObject();
        userJson.put("userName", "user");
        userJson.put("password", "123");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userJson.toString())
                .when()
                .post("/user/login");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        assertEquals(-1, response.getBody().as(Integer.class));
    }


    @Test
    public void testGetUsernameById() throws JSONException {
            JSONObject newUser = new JSONObject()
                    .put("username", "drew123")
                    .put("password", "testPassword")
                    .put("email", "test@example.com")
                    .put("firstname", "Drew")
                    .put("lastname", "Doe")
                    .put("id", 1);

            Response response = RestAssured.given()
                    .header("Content-Type", ContentType.JSON.toString())
                    .when()
                    .get("/finduser/{id}", newUser.get("id"));

            int statusCode = response.getStatusCode();
            assertEquals(200, statusCode);

            try {
                assertEquals(1, newUser.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
}