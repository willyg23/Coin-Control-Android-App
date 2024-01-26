package IP103_roundtrip1.roundtrip1.repository;
import IP103_roundtrip1.roundtrip1.Model.Expense;
import IP103_roundtrip1.roundtrip1.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    Expense findById(int id);
    List<Expense> findByWeekNumber(int weekNumber);

}
