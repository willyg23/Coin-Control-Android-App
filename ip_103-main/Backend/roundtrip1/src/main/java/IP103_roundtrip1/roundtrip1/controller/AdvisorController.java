package IP103_roundtrip1.roundtrip1.controller;

import IP103_roundtrip1.roundtrip1.Model.Advisor;
import IP103_roundtrip1.roundtrip1.Model.Expense;
import IP103_roundtrip1.roundtrip1.Services.AdvisorServices;
import IP103_roundtrip1.roundtrip1.repository.AdminRepo;
import IP103_roundtrip1.roundtrip1.repository.AdvisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdvisorController {

    @Autowired
    AdvisorRepo advisorRepo;

    @Autowired
    AdvisorServices advisorServices;

    @Autowired
    AdminRepo adminRepo;

    @Operation(summary = "Returns all advisor data",
            description = "Returns list of advisor objects including firstname, lastname, email, username, and password",
            tags = { "advisor", "get" })
    @GetMapping("/advisor/all")
    List<Advisor> GetAllUsers() {return advisorRepo.findAll();}


    @Operation(summary = "Returns an advisor data's by id",
            description = "Returns advisor objects including firstname, lastname, email, username, and password when " +
                    "id is passed as a parameter",
            tags = { "id", "get" })
    @GetMapping("/findAdvisor/{id}")
    Advisor getUserById(@PathVariable int id){
        return advisorRepo.findById(id);
    }

    @Operation(summary = "Returns an advisor's data by username",
            description = "Returns a of user objects including firstname, lastname, email, username, and password when " +
                    "username is passed as a parameter",
            tags = { "advisor username", "get" })
    @GetMapping("/findAdvisorName/{advisorUsername}")
    Advisor getUserByUsername(@PathVariable String advisorUsername){return advisorRepo.findByUsername(advisorUsername); }


    @Operation(summary = "Adds a new advisor the database",
            description = "Checks if the username or email giving from the json object is in the database if it is " +
                    "return a 3 or a 2 to the frontend to let them know else adds a new advisor to the database and to the admin list of advisors ",
            tags = { "New advisor", "post" })
    @PostMapping("/advisor/register")
    public int checkUsername(@RequestBody Advisor newRegister) {
        boolean exists = advisorServices.isUsernameExists(newRegister.getAdvisorUsername());
        boolean emailUsed = advisorServices.isEmailUsed(newRegister.getEmail());

        if (exists) {
            return 3;
        }
        if(emailUsed) {
            return 2;
        }
        newRegister.setAdmin(adminRepo.findById(1));
        adminRepo.findById(1).getAdvisorList().add(newRegister);
        advisorRepo.save(newRegister);
        return 4;
    }

    @Operation(summary = "Allows the advisor to login to the app",
            description = "checks if the username provided is in the database if it is not returns a one for to the " +
                    "frontend if it is then checks if the password matches the one in the database and returns 1 if it is else return 0 if it is not",
            tags = { "advisor login", "post" })
    @PostMapping("/advisor/login")
    public int loginUser(@RequestBody Advisor advisor) {
        boolean exists = advisorServices.isUsernameExists(advisor.getAdvisorUsername());
        if(!exists) {
            return -1;
        }
        if(advisorRepo.findByUsername(advisor.getAdvisorUsername()).getAdvisorPassword().equals(advisor.getAdvisorPassword())) {
            return 1;
        }else {
            return 0;
        }
    }

    @Operation(summary = "Gets the advisor's advisee's total expense",
            description = "Finds the advisee's expense information by their username",
            tags = { "expense", "get" })
    @GetMapping("/advisor/user/{advisorUsername}")
    public Expense adviseeTotalExpense(@PathVariable String advisorUsername) {
        return advisorRepo.findByUsername(advisorUsername).getUsers().getExpenses().get(advisorRepo.findByUsername(advisorUsername).getUsers().getExpenses().size()-1);
    }
}
