package IP103_roundtrip1.roundtrip1.repository;

import IP103_roundtrip1.roundtrip1.Model.YearlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface YearlyReportRepo extends JpaRepository<YearlyReport, Long> {

    YearlyReport findById(int id);

    void deleteById(int id);
}
