package IP103_roundtrip1.roundtrip1.repository;
import IP103_roundtrip1.roundtrip1.Model.Goals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalsRepo extends JpaRepository<Goals, Long> {
    Goals findById(int id);
}

