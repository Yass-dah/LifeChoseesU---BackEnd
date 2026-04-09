package lcu.backend.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    ArrayList<User> findByRole(String role);
    boolean findByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
}