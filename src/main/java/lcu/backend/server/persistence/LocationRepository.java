package lcu.backend.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findByCountry_Name(String countryName);
    List<Location> findByCityAndCountry_Name(String city, String countryName);
}