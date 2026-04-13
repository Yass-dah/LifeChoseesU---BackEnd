package lcu.backend.server.controllers;

import lcu.backend.server.persistence.HelpRequest;
import lcu.backend.server.services.HelpRequestService;
import lcu.backend.server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
    private final UserService userService;
    private final HelpRequestService helpRequestService;

    public UserController(UserService userService, HelpRequestService helpRequestService) {
        this.userService = userService;
        this.helpRequestService = helpRequestService;
    }

    @GetMapping("/mediator/{username}")
    public ResponseEntity<List<HelpRequest>> getMediatorRequests(@PathVariable String username) {
        return ResponseEntity.ok(helpRequestService.getMediatorRequests(username));
    }

    @GetMapping("/requester/{username}")
    public ResponseEntity<List<HelpRequest>> getRequesterRequests(@PathVariable String username) {
        return ResponseEntity.ok(helpRequestService.getRequesterRequests(username));
    }

    @PostMapping("/hr/{id}/assign")
    public ResponseEntity<Boolean> assign(
            @PathVariable Integer id,
            @RequestBody String username) {
        return ResponseEntity.ok(helpRequestService.assignRequest(id, username));
    }

    @PostMapping("/hr/{id}/resolve")
    public ResponseEntity<Boolean> resolve(
            @PathVariable Integer id,
            @RequestBody String username) {
        return ResponseEntity.ok(helpRequestService.resolveRequest(id, username));
    }

    @PostMapping("/hr/{id}/answer/{username}")
    public ResponseEntity<Boolean> answer(
            @PathVariable Integer id,
            @PathVariable String username,
            @RequestBody String answer) {
        return ResponseEntity.ok(helpRequestService.addAnswer(id, username, answer));
    }
}
