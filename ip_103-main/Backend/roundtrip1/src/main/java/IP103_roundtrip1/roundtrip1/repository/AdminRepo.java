package IP103_roundtrip1.roundtrip1.repository;

import IP103_roundtrip1.roundtrip1.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Long> {

    Admin findById(int id);

}
