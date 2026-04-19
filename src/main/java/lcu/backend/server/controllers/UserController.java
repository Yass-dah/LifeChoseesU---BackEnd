package lcu.backend.server.controllers;

import jakarta.servlet.http.HttpSession;
import lcu.backend.server.persistence.HelpRequest;
import lcu.backend.server.services.HelpRequestService;
import lcu.backend.server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
    private final HelpRequestService helpRequestService;
    private final UserService userService;

    public UserController(HelpRequestService helpRequestService, UserService userService) {
        this.helpRequestService = helpRequestService;
        this.userService = userService;
    }

    @GetMapping("/mediator/{username}")
    public ResponseEntity<List<HelpRequest>> getMediatorRequests(@PathVariable String username) {
        if (username == null || userService.isUserRequester(username))
            return ResponseEntity.status(401).body(null);
        return ResponseEntity.ok(helpRequestService.getMediatorRequests(username));
    }

    @GetMapping("/requester/{username}")
    public ResponseEntity<List<HelpRequest>> getRequesterRequests(@PathVariable String username) {
        if (username == null || userService.isUserMediator(username))
            return ResponseEntity.status(401).body(null);
        return ResponseEntity.ok(helpRequestService.getRequesterRequests(username));
    }

    @PostMapping("/hr/{id}/assign")
    public ResponseEntity<Boolean> assign(
            @PathVariable Integer id,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || userService.isUserRequester(username))
            return ResponseEntity.status(401).body(false);
        return ResponseEntity.ok(helpRequestService.assignRequest(id, username));
    }

    @PostMapping("/hr/{id}/resolve")
    public ResponseEntity<Boolean> resolve(
            @PathVariable Integer id,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || userService.isUserRequester(username))
            return ResponseEntity.status(401).body(false);
        return ResponseEntity.ok(helpRequestService.resolveRequest(id, username));
    }

    @PostMapping("/hr/{id}/answer")
    public ResponseEntity<Boolean> answer(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || userService.isUserRequester(username))
            return ResponseEntity.status(401).body(false);
        String answer = body.get("answer");
        return ResponseEntity.ok(helpRequestService.addAnswer(id, username, answer));
    }

    @PostMapping("/hr/new")
    public ResponseEntity<HelpRequest> createRequest(
            @RequestBody Map<String, String> body,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || userService.isUserMediator(username))
            return ResponseEntity.status(401).body(null);
        HelpRequest req = new HelpRequest(body.get("title"),
                body.get("location"),
                body.get("description"),
                body.get("type"),
                body.get("urgency"),
                body.get("country"),
                userService.findByUsername(username),
                "true".equals(body.get("anonymous")));
        return ResponseEntity.ok(helpRequestService.createRequest(req));
    }
}
