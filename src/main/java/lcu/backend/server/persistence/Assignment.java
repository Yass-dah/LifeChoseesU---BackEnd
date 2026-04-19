package lcu.backend.server.persistence;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mediator_username", nullable = false)
    private User mediator;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private HelpRequest request;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "assigned_at")
    private Timestamp assignedAt;

    public Assignment() {}

    // Getters
    public int getId() {
        return id;
    }

    public User getMediator() {
        return mediator;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public String getType() {
        return type;
    }

    public Timestamp getAssignedAt() {
        return assignedAt;
    }
}
