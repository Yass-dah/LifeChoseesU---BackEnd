package lcu.backend.server.persistence;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "help_requests")
public class HelpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "urgency", nullable = false)
    private String urgency;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "requester_username", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "mediator_username")
    private User mediator;

    @Column(name = "anonymous", nullable = false)
    private boolean anonymous;

    @Column(name = "aid_answer")
    private String aidAnswer;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public HelpRequest() {}

    public HelpRequest(String title,
                       String location,
                       String description,
                       String type,
                       String urgency,
                       String country,
                       User requester,
                       boolean anonymous){
        this.title = title;
        this.location = location;
        this.description = description;
        this.type = type;
        this.urgency = urgency;
        this.country = country;
        this.requester = requester;
        this.anonymous = anonymous;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public String getRequester() {
        return requester != null ? requester.getUsername() : null;
    }

    public String getMediator() {
        return mediator != null ? mediator.getUsername() : null;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public String getAidAnswer() {
        return aidAnswer;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Basic setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMediator(User mediator) {
        this.mediator = mediator;
    }

    public void setAidAnswer(String aidAnswer) {
        this.aidAnswer = aidAnswer;
    }
}
