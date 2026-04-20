package lcu.backend.server.session;

import jakarta.servlet.http.HttpSession;
import lcu.backend.server.persistence.User;
import lcu.backend.server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<SessionData> getUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null)
            return ResponseEntity.ok(new SessionData("", "", "Not logged"));
        return ResponseEntity.ok(new SessionData(username, userService.getRole(username), "Logged"));
    }

    @GetMapping("/login")
    public ResponseEntity<SessionData> login(
            HttpSession session,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String role) {
        String existingUser = (String) session.getAttribute("username");
        if (existingUser != null) {
            if (existingUser.equals(username))
                return ResponseEntity.ok(new SessionData(username,
                        (String) session.getAttribute("role"), "User already authenticated."));
            return ResponseEntity.badRequest().body(new SessionData("",
                    (String) session.getAttribute("role"), "Another user authenticated."));
        }
        boolean validRole = (role != null && role.equals(userService.getRole(username)));
        boolean validCredentials = userService.checkCredentials(username, password);
        boolean auth = validRole && validCredentials;
        if (auth) {
            session.setAttribute("username", username);
            session.setAttribute("role", role);
            session.setMaxInactiveInterval(900);
            return ResponseEntity.ok(new SessionData(username, role, "Log in successful."));
        }
        return ResponseEntity.status(401).body(new SessionData("", "", "Invalid credentials."));
    }

    @GetMapping("/logout")
    public ResponseEntity<SessionData> logout(HttpSession session) {
        String existingUser = (String) session.getAttribute("username");
        if (existingUser == null)
            return ResponseEntity.ok(new SessionData("","", "No user to log out."));
        session.invalidate();
        return ResponseEntity.ok(new SessionData("", "", "User successfully logged out."));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        String role = body.get("role");

        if (userService.userExists(username))
            return ResponseEntity.status(401).body("username already exists");
        User user = userService.registerUser(new User(username, email, password, role));
        return ResponseEntity.ok("User " + user.getUsername() + " successfully registered");
    }
}
