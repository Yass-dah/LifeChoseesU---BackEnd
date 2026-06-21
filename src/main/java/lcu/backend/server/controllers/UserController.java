package lcu.backend.server.controllers;

import jakarta.servlet.http.HttpSession;
import lcu.backend.server.persistence.AidAnswer;
import lcu.backend.server.persistence.HelpRequest;
import lcu.backend.server.persistence.Location;
import lcu.backend.server.services.HelpRequestService;
import lcu.backend.server.services.LocationService;
import lcu.backend.server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://life-chooses-u.vercel.app", allowCredentials = "true")
public class UserController {
    private final HelpRequestService helpRequestService;
    private final UserService userService;
    private final LocationService locationService;

    public UserController(HelpRequestService helpRequestService, UserService userService, LocationService locationService) {
        this.helpRequestService = helpRequestService;
        this.userService = userService;
        this.locationService = locationService;
    }

    // Permission checkers
    private boolean checkReqPerm(String username, Integer reqId) {
        if(username == null)
            return false;
        HelpRequest request = helpRequestService.getRequestById(reqId);
        if(request == null)
            return false;
        return username.equals(request.getRequester());
    }

    private boolean checkMedPerm(String username, Integer reqId) {
        if(username == null)
            return false;
        HelpRequest request = helpRequestService.getRequestById(reqId);
        if(request == null)
            return false;
        return username.equals(request.getMediator());
    }

    // Routes
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

    @GetMapping("/hr/{id}/response")
    public ResponseEntity<AidAnswer> getResponse(
            @PathVariable Integer id,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (!(checkMedPerm(username, id) || checkReqPerm(username, id)))
            return ResponseEntity.status(403).body(null);
        AidAnswer aidAnswer = helpRequestService.getAidAnswer(id);
        return ResponseEntity.ok(aidAnswer);
    }

    @GetMapping("/hr/{id}/assign")
    public ResponseEntity<Boolean> assign(
            @PathVariable Integer id,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || userService.isUserRequester(username))
            return ResponseEntity.status(401).body(false);
        return ResponseEntity.ok(helpRequestService.assignRequest(id, username));
    }

    @GetMapping("/hr/{id}/resolve")
    public ResponseEntity<Boolean> resolve(
            @PathVariable Integer id,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        return checkMedPerm(username, id) ?
                ResponseEntity.ok(helpRequestService.resolveRequest(id, username)) :
                ResponseEntity.status(403).body(false);
    }

    @PostMapping("/hr/{id}/answer")
    public ResponseEntity<Boolean> answer(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body,
            HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (!checkMedPerm(username, id))
            return ResponseEntity.status(403).body(false);
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
        Location location = locationService.addLocation(body.get("location"),
                locationService.getCountry(body.get("country")));
        HelpRequest req = new HelpRequest(body.get("title"),
                location,
                body.get("description"),
                body.get("type"),
                body.get("urgency"),
                userService.findByUsername(username),
                "true".equals(body.get("anonymous")));
        return ResponseEntity.ok(helpRequestService.createRequest(req));
    }

    @GetMapping("/hr/{id}/anonymous/{flag}")
    public ResponseEntity<Boolean> anonymous(
            @PathVariable Integer id,
            @PathVariable boolean flag,
            HttpSession session){
        String username = (String) session.getAttribute("username");
        return checkReqPerm(username, id) ?
                ResponseEntity.ok(helpRequestService.setRequestAnonymous(id, flag)) :
                ResponseEntity.status(403).body(false);
    }

    @DeleteMapping("/hr/{id}/delete")
    public ResponseEntity<Boolean> delete(
            @PathVariable Integer id,
            HttpSession session){
        String username = (String) session.getAttribute("username");
        return checkReqPerm(username, id) ?
                ResponseEntity.ok(helpRequestService.deleteRequest(id)) :
                ResponseEntity.status(403).body(false);
    }
}
