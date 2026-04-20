package lcu.backend.server.controllers;

import lcu.backend.server.persistence.Country;
import lcu.backend.server.persistence.HelpRequest;
import lcu.backend.server.services.HelpRequestService;
import lcu.backend.server.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help-requests")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class HelpRequestController {
    private final HelpRequestService helpRequestService;
    private final LocationService locationService;

    public HelpRequestController(HelpRequestService helpRequestService, LocationService locationService) {
        this.helpRequestService = helpRequestService;
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<HelpRequest>> getHelpRequests() {
        return ResponseEntity.ok(helpRequestService.getAllRequests());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<HelpRequest>> getHelpRequestsByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(helpRequestService.getRequestsByType(type));
    }

    @GetMapping("/urgency/{urgency}")
    public ResponseEntity<List<HelpRequest>> getHelpRequestsByUrgency(@PathVariable("urgency") String urgency) {
        return ResponseEntity.ok(helpRequestService.getRequestsByUrgency(urgency));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<HelpRequest>> getHelpRequestsByStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok(helpRequestService.getRequestsByStatus(status));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<HelpRequest>> getHelpRequestsByCountry(@PathVariable("country") String country) {
        return ResponseEntity.ok(helpRequestService.getRequestsByCountry(country));
    }

    @GetMapping("/available")
    public ResponseEntity<List<HelpRequest>> getAvailable() {
        return ResponseEntity.ok(helpRequestService.getAvailableRequests());
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries() {
        return ResponseEntity.ok(locationService.getAllCountries());
    }
}
