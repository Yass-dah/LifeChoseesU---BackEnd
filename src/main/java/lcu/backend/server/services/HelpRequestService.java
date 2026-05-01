package lcu.backend.server.services;

import lcu.backend.server.persistence.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HelpRequestService {
    private final LocationRepository locationRepo;
    private final HelpRequestRepository helpRequestRepo;
    private final UserRepository userRepo;
    private final AidAnswerRepository aidAnswerRepo;
    private final AssignmentRepository assignmentRepo;

    public HelpRequestService(HelpRequestRepository helpRequestRepo,
                              UserRepository userRepo,
                              LocationRepository locationRepository,
                              AssignmentRepository assignmentRepo,
                              AidAnswerRepository aidAnswerRepository) {
        this.helpRequestRepo = helpRequestRepo;
        this.userRepo = userRepo;
        this.locationRepo = locationRepository;
        this.aidAnswerRepo = aidAnswerRepository;
        this.assignmentRepo = assignmentRepo;
    }

    // Getters
    public HelpRequest getRequestById(int id) {
        return helpRequestRepo.findById(id).orElse(null);
    }

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
        List<Location> locations = locationRepo.findByCountry_Name(country);
        return helpRequestRepo.findByLocationIn(locations);
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

    public AidAnswer getAidAnswer(Integer requestId){
        if(aidAnswerRepo.existsByRequest_Id(requestId))
            return aidAnswerRepo.findByRequest_Id(requestId).orElse(null);
        else return new AidAnswer();
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
                Assignment assignment = new Assignment(mediator, request, "PRESA_CARICO");
                assignmentRepo.save(assignment);
                return true;
            }
        }
        return false;
    }

    public boolean resolveRequest(Integer requestId, String mediatorUsername) {
        HelpRequest request = helpRequestRepo.findById(requestId).orElse(null);

        if (request != null && request.getMediator() != null) {
            boolean validMed = request.getMediator().equals(mediatorUsername);
            boolean validReq = validMed && request.getStatus().equals("IN_GESTIONE");
            if (validReq) {
                request.setStatus("RISOLTO");
                helpRequestRepo.save(request);
                assignmentRepo.findByRequest_Id(requestId).ifPresent(a -> {
                    a.setResolved();
                    assignmentRepo.save(a);
                });
                return true;
            }
        }
        return false;
    }

    public boolean addAnswer(Integer requestId, String mediatorUsername, String answer) {
        HelpRequest request = helpRequestRepo.findById(requestId).orElse(null);

        if (request != null && request.getMediator() != null) {
            boolean validMed = request.getMediator().equals(mediatorUsername);
            boolean validReq = validMed && request.getStatus().equals("IN_GESTIONE");
            if (validReq) {
                Optional<AidAnswer> existing = aidAnswerRepo.findByRequest_Id(requestId);
                AidAnswer aidAnswer;
                if (existing.isPresent()) {
                    aidAnswer = existing.get();
                    aidAnswer.setAnswer(answer);
                    aidAnswer.setModifiedAt(LocalDateTime.now());
                } else aidAnswer = new AidAnswer(answer, request, LocalDateTime.now());
                aidAnswerRepo.save(aidAnswer);
                return true;
            }
        }
        return false;
    }

    public boolean setRequestAnonymous(Integer requestId, boolean flag) {
        HelpRequest request = helpRequestRepo.findById(requestId).orElse(null);
        if(request != null && (flag != request.isAnonymous())) {
            request.setAnonymous(flag);
            helpRequestRepo.save(request);
            return true;
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
