package lcu.backend.server.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "flag", nullable = false)
    private String flag;

    @Column(name = "continent", nullable = false)
    private String continent;

    @Column(name = "main_language", nullable = false)
    private String language;

    @Column(name = "emergency_number", nullable = false)
    private String emergencyNumber;

    @Transient
    private int conflictQt;

    public Country() {}

    // Getters
    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getContinent() {
        return continent;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public int getConflictQt() {
        return conflictQt;
    }

    public void setConflictQt(int conflictQt) {
        this.conflictQt = conflictQt;
    }
}