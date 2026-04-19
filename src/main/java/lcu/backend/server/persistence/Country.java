package lcu.backend.server.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "flag", nullable = false)
    private String flag;

    public Country() {}

    // Getters
    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }
}