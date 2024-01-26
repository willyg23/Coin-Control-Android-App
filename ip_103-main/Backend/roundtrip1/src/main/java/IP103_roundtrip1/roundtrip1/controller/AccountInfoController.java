package IP103_roundtrip1.roundtrip1.controller;


import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.Services.UserServices;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Account Info Page Backend", description = "Account Info page backend abilities api")
@RestController
public class AccountInfoController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserServices userServices;

    @Operation(summary = "Returns an user data's by id",
            description = "Returns a of user objects including firstname, lastname, email, username, and password when id is passed as a parameter",
            tags = { "userid", "get" })
    @GetMapping("/currentUser/infoFromID/{id}")
    Users getUserById(@PathVariable int id){
        return userRepo.findById(id);
    }

    @Operation(summary = "Returns an user's data by username",
            description = "Returns a of user objects including firstname, lastname, email, username, and password when username is passed as a parameter",
            tags = { "user username", "get" })
    @GetMapping("/currentUser/infoFrom/{username}")
    Users getUserByUsername(@PathVariable String username){
        return userRepo.findByUserName(username);
    }


    @Operation(summary = "Allows a user to change there username",
            description = "Changes a user's username when the path includes the old username and the new username",
            tags = { "update username", "put" })
    @PutMapping("/new/{newUsername}/username/{oldUsername}")
    public int ChangeUsername(@PathVariable String newUsername, @PathVariable String oldUsername) {
        userRepo.findByUserName(oldUsername).setUserName(newUsername);
        userRepo.save(userRepo.findByUserName(newUsername));
        return 5;
    }

    @Operation(summary = "Allows a user to change there password",
            description = "Changes a user's password when the path includes the username and the new password",
            tags = { "update password", "put" })
    @PutMapping("/new/password/{password}/{username}")
    public int ChangePassword(@PathVariable String username, @PathVariable String password) {
        userRepo.findByUserName(username).setPassword(password);
        userRepo.save(userRepo.findByUserName(username));
        return 6;
    }

    @Operation(summary = "Allows a user to change there email",
            description = "Changes a user email when the path includes the username and the new email",
            tags = { "update email", "put" })
    @PutMapping("/new/email/{email}/{username}")
    public int ChangeEmail(@PathVariable String username, @PathVariable String email) {
        userRepo.findByUserName(username).setEmail(email);
        userRepo.save(userRepo.findByUserName(username));
        return 7;
    }

    @Operation(summary = "Allows a user to logout of there account",
            description = "Returns a 10 letting the frontend know the user with username passed in the path wants to" +
                    " logout of their account.",
            tags = { "username logout", "post" })
    @PostMapping("/user/logout/{username}")
    public int userLogout(@PathVariable String username) {
        userRepo.findByUserName(username).setResponseID(10);
        userRepo.save(userRepo.findByUserName(username));
        return 10;
    }


}
