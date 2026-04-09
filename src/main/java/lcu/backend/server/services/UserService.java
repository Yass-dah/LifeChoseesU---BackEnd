package lcu.backend.server.services;

import lcu.backend.server.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean userExists(String username) {
        return this.userRepo.existsById(username);
    }

    public boolean isUserRequester(String username) {
        return this.userRepo.findById("requester").get().getRole().equals("requester");
    }

    public boolean isUserMediator(String username) {
        return this.userRepo.findById("mediator").get().getRole().equals("mediator");
    }

    public boolean checkCredentials(String username, String password) {
        return this.userRepo.existsByUsernameAndPassword(username, password);
    }
}
