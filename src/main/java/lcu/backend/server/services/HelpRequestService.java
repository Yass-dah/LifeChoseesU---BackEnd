package lcu.backend.server.services;

import lcu.backend.server.persistence.HelpRequest;
import lcu.backend.server.persistence.HelpRequestRepository;
import lcu.backend.server.persistence.User;
import lcu.backend.server.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpRequestService {
    private final HelpRequestRepository helpRequestRepo;
    private final UserRepository userRepo;

    public HelpRequestService(HelpRequestRepository helpRequestRepo, UserRepository userRepo) {
        this.helpRequestRepo = helpRequestRepo;
        this.userRepo = userRepo;
    }

    // Getters
    public List<HelpRequest> getAllRequests() {
        return helpRequestRepo.findAll();
    }

    public List<HelpRequest> getRequestsByStatus(String status) {
        return helpRequestRepo.findByStatus(status);
    }

    public List<HelpRequest> getRequestsByUrgency(String urgency) {
        return helpRequestRepo.findByUrgency(urgency);
    }

    public List<HelpRequest> getRequestsByType(String type) {
        return helpRequestRepo.findByType(type);
    }

    public List<HelpRequest> getRequestsByCountry(String country) {
        return helpRequestRepo.findByCountry(country);
    }

    public List<HelpRequest> getAvailableRequests() {
        return helpRequestRepo.findByMediatorIsNull();
    }

    public List<HelpRequest> getMediatorRequests(String username) {
        return helpRequestRepo.findByMediatorUsername(username);
    }

    public List<HelpRequest> getRequesterRequests(String username) {
        return helpRequestRepo.findByRequesterUsername(username);
    }

    // Operation methods
    public HelpRequest createRequest(HelpRequest request) {
        request.setStatus("IN_ATTESA");
        request.setMediator(null);
        return helpRequestRepo.save(request);
    }

    public boolean assignRequest(Integer requestId, String mediatorUsername) {
        HelpRequest request = helpRequestRepo.findById(requestId).orElse(null);
        User mediator = userRepo.findById(mediatorUsername).orElse(null);

        if (request != null && mediator != null) {
            boolean validReq = request.getMediator() == null && request.getStatus().equals("IN_ATTESA");
            if (validReq) {
                request.setMediator(mediator);
                request.setStatus("IN_GESTIONE");
                helpRequestRepo.save(request);
                return true;
            }
        }
        return false;
    }

    public boolean resolveRequest(Integer requestId, String mediatorUsername) {
        HelpRequest request = helpRequestRepo.findById(requestId).orElse(null);

        if (request != null && request.getMediator() != null) {
            boolean validMed = request.getMediator().getUsername().equals(mediatorUsername);
            boolean validReq = validMed && request.getStatus().equals("IN_GESTIONE");
            if (validReq) {
                request.setStatus("RISOLTO");
                helpRequestRepo.save(request);
                return true;
            }
        }
        return false;
    }

    public boolean addAnswer(Integer requestId, String mediatorUsername, String answer) {
        HelpRequest request = helpRequestRepo.findById(requestId).orElse(null);

        if (request != null && request.getMediator() != null) {
            boolean validMed = request.getMediator().getUsername().equals(mediatorUsername);
            boolean validReq = validMed && request.getStatus().equals("IN_GESTIONE");
            if (validReq) {
                request.setAidAnswer(answer);
                helpRequestRepo.save(request);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRequest(Integer requestId) {
        if (helpRequestRepo.existsById(requestId)) {
            helpRequestRepo.deleteById(requestId);
            return true;
        }
        return false;
    }
}
