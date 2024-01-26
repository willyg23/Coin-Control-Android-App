package IP103_roundtrip1.roundtrip1.Services;

import IP103_roundtrip1.roundtrip1.Model.Advisor;
import IP103_roundtrip1.roundtrip1.repository.AdvisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvisorServices {

    @Autowired
    AdvisorRepo advisorRepo;

    public boolean isUsernameExists(String username) {
        Advisor advisor = advisorRepo.findByUsername(username);
        return advisor != null;
    }

    public boolean isEmailUsed(String Email) {
        Advisor advisor = advisorRepo.findByEmail(Email);
        return advisor != null;
    }

    public boolean passwordCheck(String Username, String Password) {
        String advisor = advisorRepo.findByUsername(Username).getAdvisorPassword();
        return advisor != null;
    }
}
