package IP103_roundtrip1.roundtrip1.repository;

import IP103_roundtrip1.roundtrip1.Model.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface AdvisorRepo extends JpaRepository<Advisor, Long> {

    Advisor findById(int id);

    Advisor findByUsername(String username);

    Advisor findByEmail(String Email);

    @Transactional
    void deleteByUsername(String userName);

}
