package lcu.backend.server.services;

import lcu.backend.server.persistence.Country;
import lcu.backend.server.persistence.CountryRepository;
import lcu.backend.server.persistence.Location;
import lcu.backend.server.persistence.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final CountryRepository countryRepository;
    private final LocationRepository locationRepository;

    public LocationService(CountryRepository countryRepository, LocationRepository locationRepository) {
        this.countryRepository = countryRepository;
        this.locationRepository = locationRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountry(String country) {
        return countryRepository.findByName(country).orElse(null);
    }

    public Location addLocation(String city, Country country){
        return locationRepository.save(new Location(city, (country)));
    }
}
