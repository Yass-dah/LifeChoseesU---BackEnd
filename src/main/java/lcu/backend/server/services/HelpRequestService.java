package lcu.backend.server.services;

import lcu.backend.server.persistence.HelpRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class HelpRequestService {
    private final HelpRequestRepository helpRequestRepo;

    public HelpRequestService(HelpRequestRepository helpRequestRepo) {
        this.helpRequestRepo = helpRequestRepo;
    }
}
