package lcu.backend.server.persistence;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aid_answer")
public class AidAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "answer", nullable = false)
    private String answer;

    @OneToOne
    @JoinColumn(name = "request_id", nullable = false)
    private HelpRequest request;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    public AidAnswer() {}

    @PrePersist
    public void onCreate() {
        this.modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

    // Getters
    public String getAnswer() {
        return answer;
    }

    public int getId() {
        return id;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}