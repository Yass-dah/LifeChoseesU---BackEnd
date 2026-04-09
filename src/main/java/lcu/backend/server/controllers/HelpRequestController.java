package lcu.backend.server.controllers;

import lcu.backend.server.services.HelpRequestService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpRequestController {
    private final HelpRequestService helpRequestService;

    public HelpRequestController(HelpRequestService helpRequestService) {
        this.helpRequestService = helpRequestService;
    }
}
