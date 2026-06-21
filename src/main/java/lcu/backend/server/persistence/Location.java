package lcu.backend.server.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "city", nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "country", nullable = false)
    private Country country;

    public Location() {}

    public Location(String city, Country country) {
        this.city = city;
        this.country = country;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country.getName();
    }
}
