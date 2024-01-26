package IP103_roundtrip1.roundtrip1.Services;

import IP103_roundtrip1.roundtrip1.Model.Users;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    UserRepo userRepo;

    public boolean isUsernameExists(String username) {
        Users user = userRepo.findByUserName(username);
        return user != null;
    }

    public boolean isEmailUsed(String Email) {
        Users user = userRepo.findByEmail(Email);
        return user != null;
    }

    public boolean passwordCheck(String Username, String Password) {
        String user = userRepo.findByUserName(Username).getPassword();
        return user != null;
    }
}
