package lcu.backend.server.services;

import lcu.backend.server.persistence.User;
import lcu.backend.server.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<User> user = this.userRepo.findById(username);
        return user.map(value -> value.getRole().equals("RICHIEDENTE")).orElse(false);
    }

    public boolean isUserMediator(String username) {
        Optional<User> user = this.userRepo.findById(username);
        return user.map(value -> value.getRole().equals("MEDIATORE")).orElse(false);
    }

    public String getRole(String username) {
        Optional<User> user = this.userRepo.findById(username);
        return user.map(User::getRole).orElse(null);
    }

    public boolean checkCredentials(String username, String password) {
        return this.userRepo.existsByUsernameAndPassword(username, password);
    }
}
