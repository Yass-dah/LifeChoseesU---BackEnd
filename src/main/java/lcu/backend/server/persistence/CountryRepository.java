package lcu.backend.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    Optional<Country> findByName(String name);
    @Query("""
SELECT l.country.name, COUNT(hr)
FROM HelpRequest hr
JOIN hr.location l
GROUP BY l.country.name
""")
    List<Object[]> countRequestsByCountry();
}