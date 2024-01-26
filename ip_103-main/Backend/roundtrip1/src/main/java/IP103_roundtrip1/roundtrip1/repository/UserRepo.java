package IP103_roundtrip1.roundtrip1.repository;
import IP103_roundtrip1.roundtrip1.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserRepo extends JpaRepository<Users, Long> {

    Users findById(int id);

    Users findByUserName(String UserName);

    Users findByEmail(String Email);

    @Transactional
    void deleteByUserName(String userName);

}
