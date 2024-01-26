package IP103_roundtrip1.roundtrip1.controller;

import IP103_roundtrip1.roundtrip1.Model.Expense;
import IP103_roundtrip1.roundtrip1.Model.YearlyReport;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import IP103_roundtrip1.roundtrip1.repository.YearlyReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.ObjectUtils.isEmpty;


@RestController
public class YearlyReportController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    YearlyReportRepo yearlyReportRepo;



    @Operation(summary = "Get User Yearly Report",
            description = "Retrieves the yearly report for a specific user.",
            tags = { "yearly report", "get" })
    @GetMapping("/report/{userID}")
    private YearlyReport giveUserReport(@PathVariable int userID) {
        YearlyReport report = new YearlyReport();
        if(isEmpty(userRepo.findById(userID).getUserYearlyReport())) {
            report.setUsers(userRepo.findById(userID));
            report.setInitialBalance(userRepo.findById(userID).getGoals().getCurrentBankBalance());
            report.setDesiredBalance(userRepo.findById(userID).getGoals().getDesiredBankBalance());
            report.setTotalExpenses(userRepo.findById(userID).getExpenses().stream().mapToDouble(Expense::getTotalWeeklyExpenses).sum());
            report.setTotalSavings(userRepo.findById(userID).getExpenses().stream().mapToDouble(Expense::getTotalWeeklySavings).sum());
            userRepo.findById(userID).setUserYearlyReport(report);
            yearlyReportRepo.save(report);
            userRepo.save(userRepo.findById(userID));
            return report;
        }else {
            userRepo.findById(userID).getUserYearlyReport().setInitialBalance(userRepo.findById(userID).getGoals().getCurrentBankBalance());
            userRepo.findById(userID).getUserYearlyReport().setDesiredBalance(userRepo.findById(userID).getGoals().getDesiredBankBalance());
            userRepo.findById(userID).getUserYearlyReport().setTotalExpenses(userRepo.findById(userID).getExpenses().stream().mapToDouble(Expense::getTotalWeeklyExpenses).sum());
            userRepo.findById(userID).getUserYearlyReport().setTotalSavings(userRepo.findById(userID).getExpenses().stream().mapToDouble(Expense::getTotalWeeklySavings).sum());
            yearlyReportRepo.save(userRepo.findById(userID).getUserYearlyReport());
            userRepo.save(userRepo.findById(userID));
        }

        return userRepo.findById(userID).getUserYearlyReport();
    }
}

