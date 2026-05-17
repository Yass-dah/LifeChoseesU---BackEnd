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

    public boolean userExistsUsername(String username) {
        return this.userRepo.existsById(username);
    }

    public boolean userExistsEmail(String email) {
        return this.userRepo.existsByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepo.findById(username).orElse(null);
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

    public String getCountry(String username) {
        Optional<User> user = this.userRepo.findById(username);
        return user.map(User::getCountry).orElse(null);
    }

    public boolean checkCredentials(String username, String password) {
        return this.userRepo.existsByUsernameAndPassword(username, password);
    }

    public User registerUser(User user) {
        return userRepo.save(user);
    }
}
