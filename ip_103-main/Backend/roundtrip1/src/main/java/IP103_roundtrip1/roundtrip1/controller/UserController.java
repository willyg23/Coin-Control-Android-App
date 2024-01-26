package IP103_roundtrip1.roundtrip1.controller;
import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.Model.Goals;
import IP103_roundtrip1.roundtrip1.Model.Admin;
import IP103_roundtrip1.roundtrip1.Services.UserServices;
import IP103_roundtrip1.roundtrip1.repository.AdminRepo;
import IP103_roundtrip1.roundtrip1.repository.GoalsRepo;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "User", description = "User abilities APIs")
@RestController
public class UserController {

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private UserServices userServices;

    @Autowired
    GoalsRepo goalsRepo;

    @Operation(summary = "Returns all user data",
            description = "Returns list of user objects including firstname, lastname, email, username, and password",
            tags = { "user", "get" })
    @GetMapping("/user/all")
    List<Users> GetAllUsers() {
        return userRepo.findAll();
    }

    @Operation(summary = "Returns an user data's by id",
            description = "Returns a of user objects including firstname, lastname, email, username, and password when " +
                    "id is passed as a parameter",
            tags = { "userid", "get" })
    @GetMapping("/finduser/{userID}")
    Users getUserById(@PathVariable int userID){
        return userRepo.findById(userID);
    }

    @Operation(summary = "Returns an user's data by username",
            description = "Returns a of user objects including firstname, lastname, email, username, and password when " +
                    "username is passed as a parameter",
            tags = { "user username", "get" })
    @GetMapping("/findusername/{username}")
    Users getUserByUsername(@PathVariable String username){return userRepo.findByUserName(username); }


    @Operation(summary = "Adds a new user the database",
            description = "Checks if the username or email giving from the json object is in the database if it is " +
                    "return a 3 or a 2 to the frontend to let them know else adds a new user to the database and to the admin list of users ",
            tags = { "New user", "post" })
    @PostMapping("/user/register")
    public int checkUsername(@RequestBody Users newRegister) {
        boolean exists = userServices.isUsernameExists(newRegister.getUserName());
        boolean emailUsed = userServices.isEmailUsed(newRegister.getEmail());

        if (exists) {
            return 3;
        }
        if(emailUsed) {
            return 2;
        }
        newRegister.setAdmin(adminRepo.findById(1));
        adminRepo.findById(1).getUsersList().add(newRegister);
        userRepo.save(newRegister);
        return 4;
    }

    @Operation(summary = "Allows the user to login to the app",
            description = "checks if the username provided is in the database if it is not returns a one for to the " +
                    "frontend if it is then checks if the password matches the one in the database and returns 1 if it is else return 0 if it is not",
            tags = { "user login", "post" })
    @PostMapping("/user/login")
    public int loginUser(@RequestBody Users user) {
        boolean exists = userServices.isUsernameExists(user.getUserName());
        if(!exists) {
            return -1;
        }
        if(userRepo.findByUserName(user.getUserName()).getPassword().equals(user.getPassword())) {
            return 1;
        }else {
            return 0;
        }
    }

    @Operation(summary = "Returns the username by user id",
            description = "Returns the username when user id is passed as a parameter",
            tags = { "username", "get" })
    @GetMapping("/user/{userID}")
    String getUsernameById(@PathVariable int userID) {
        Users user = userRepo.findById(userID);
        if (user != null) {
            return user.getUserName();
        } else {
            throw new RuntimeException("User not found with id: " + userID);
        }
    }


}

