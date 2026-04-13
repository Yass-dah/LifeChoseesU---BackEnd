package lcu.backend.server.session;

import jakarta.servlet.http.HttpSession;
import lcu.backend.server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<SessionData> login(
            HttpSession session,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        String existingUser = (String) session.getAttribute("username");
        if (username == null || password == null) {
            if (existingUser == null) {
                return ResponseEntity.ok(new SessionData("",
                        "User not authenticated."));
            }
            return ResponseEntity.ok(new SessionData(existingUser,
                    "User autheticated."));
        }
        if (existingUser != null) {
            if (username.equals(existingUser)) {
                return ResponseEntity.ok(new SessionData(username,
                        "User already authenticated."));
            }
            return ResponseEntity.badRequest().body(new SessionData("",
                    "Another user authenticated."));
        }
        boolean auth = userService.checkCredentials(username, password);
        if (auth) {
            session.setAttribute("username", username);
            return ResponseEntity.ok(new SessionData(
                    username,
                    "Log in successful."
            ));
        }
        return ResponseEntity.status(401).body(
                new SessionData("",
                        "Invalid credentials."));
    }


    @GetMapping("/logout")
    public ResponseEntity<SessionData> logout(
            HttpSession session,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        String existingUser = (String) session.getAttribute("username");
        if (existingUser == null) {
            return ResponseEntity.ok(new SessionData("",
                    "No user to log out."));
        }
        session.invalidate();
        return ResponseEntity.ok(new SessionData("",
                "User successfully logged out."));
    }
}
