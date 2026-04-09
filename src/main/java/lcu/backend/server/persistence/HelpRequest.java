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

    @Column(name = "country", nullable = false)
    private String country;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "mediator_id")
    private User mediator;

    @Column(name = "anonymous", nullable = false)
    private boolean anonymous;

    @Column(name = "aid_answer")
    private String aidAnswer;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public HelpRequest() {}

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

    public User getRequester() {
        return requester;
    }

    public User getMediator() {
        return mediator;
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
}
