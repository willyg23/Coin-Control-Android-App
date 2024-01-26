package IP103_roundtrip1.roundtrip1.controller;

import IP103_roundtrip1.roundtrip1.Model.Admin;
import IP103_roundtrip1.roundtrip1.Services.UserServices;
import IP103_roundtrip1.roundtrip1.repository.AdminRepo;
import IP103_roundtrip1.roundtrip1.repository.AdvisorRepo;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Tag(name = "Admin" ,description = "Admin abilities API")
@RestController
public class AdminController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserServices userServices;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    AdvisorRepo advisorRepo;


    @PostConstruct
    public void initialize() {
        Admin Admin = new Admin("admin" , "admin123", "admin123@iastate.edu");
        if(adminRepo.count() == 0) {
            adminRepo.save(Admin);
        }
    }

    @Operation(summary = "Returns all user usernames",
            description = "Returns an arraylist of all user username",
            tags = { "admin", "get" })
    @GetMapping("/admin/allUsers")
    ArrayList<String> allUsername() {

        int length = adminRepo.findById(1).getUsersList().size();
        ArrayList<String> usernames = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            usernames.add(adminRepo.findById(1).getUsersList().get(i).getUserName());
        }
        adminRepo.save(adminRepo.findById(1));
        return usernames;
    }

    @Operation(summary = "Returns all admin usernames",
            description = "Returns an arraylist of all admin usernames",
            tags = { "admin advisor", "get" })
    @GetMapping("/admin/all/Advisor")
    ArrayList<String> getAdvisor() {

        int length = adminRepo.findById(1).getAdvisorList().size();

        ArrayList<String> advisors = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            advisors.add(adminRepo.findById(1).getAdvisorList().get(i).getAdvisorUsername());
        }

        return advisors;
    }

    @Operation(summary = "Allows the admin to delete a user",
            description = "Allows the admin to delete an user when username is include in the path",
            tags = { "admin", "delete" })
    @DeleteMapping("/admin/delete/user/{userName}")
    int deleteUser(@PathVariable String userName){
        userRepo.deleteByUserName(userName);
        return 100;
    }

    @Operation(summary = "Allows the admin to delete a advisor",
            description = "Allows the admin to delete an advisor when username is include in the path",
            tags = { "admin advisor", "delete" })
    @DeleteMapping("/admin/delete/advisor/{userName}")
    int deleteAdvisor(@PathVariable String userName){
        advisorRepo.deleteByUsername(userName);
        return 200;
    }

}

