package IP103_roundtrip1.roundtrip1.controller;
import IP103_roundtrip1.roundtrip1.Model.Goals;
import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.repository.GoalsRepo;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Id;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Survey Page Backend", description = "Survey Page Backend APIs")
@RestController
public class GoalController {

    @Autowired
    GoalsRepo goalsRepo;

    @Autowired
    UserRepo userRepo;

    @Operation(summary = "Returns all goal objects",
            description = "Returns list of goal objects including timeEstimate, monthlyIncome, currentBankBalance, and " +
                    "desiredBankBalance",
            tags = { "goals", "get" })
    @GetMapping("/goals/all")
    List<Goals> GetAllUsersGoals() {return goalsRepo.findAll();}

    @Operation(summary = "Returns a goal objects",
            description = "Returns a goal objects including timeEstimate, monthlyIncome, currentBankBalance, and " +
                    "desiredBankBalance when id is included in the path",
            tags = { "goalsId", "get" })
    @GetMapping("/goals/{id}")
    Goals getUserById(@PathVariable int id){
        return goalsRepo.findById(id);
    }


    @Operation(summary = "Creates a new goal object",
            description = "Checks if goal object includes 26 or 52 weeks than save the new goal to tha database. Then " +
                    "maps the goal object to the user with the corresponding id using a one to one connection.",
            tags = { "new goal", "post" })
    @PostMapping("/goals/post")
    public int UserGoals(@RequestBody Goals newGoals) {
        goalsRepo.save(newGoals);
        int goals_id = newGoals.getId();
        int users_id = getUserById(goals_id).getId();
        Goals goals = goalsRepo.findById(goals_id);
        Users users = userRepo.findById(users_id);
        goals.setUsers(users);
        users.setGoals(goals);
        userRepo.save(users);
        return users_id;
    }
}

