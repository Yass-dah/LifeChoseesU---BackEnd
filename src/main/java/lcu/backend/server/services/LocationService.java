package lcu.backend.server.services;

import lcu.backend.server.persistence.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {
    private final CountryRepository countryRepository;
    private final LocationRepository locationRepository;

    public LocationService(CountryRepository countryRepository,
                           LocationRepository locationRepository) {
        this.countryRepository = countryRepository;
        this.locationRepository = locationRepository;
    }

    public List<Country> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        List<Object[]> stats = countryRepository.countRequestsByCountry();
        Map<String, Integer> map = new HashMap<>();

        for (Object[] row : stats)
            map.put((String) row[0], ((Long) row[1]).intValue());
        for (Country c : countries)
            c.setConflictQt(map.getOrDefault(c.getName(), 0));
        return countries;
    }

    public Country getCountry(String country) {
        return countryRepository.findByName(country).orElse(null);
    }

    public Location addLocation(String city, Country country){
        return locationRepository.save(new Location(city, (country)));
    }
}
