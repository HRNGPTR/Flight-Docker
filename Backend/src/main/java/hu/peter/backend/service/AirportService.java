package hu.peter.backend.service;

import hu.peter.backend.entity.Airport;
import hu.peter.backend.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    //=====CREATE=====
    public void createAirport(String name, String country) {
        Airport airport = new Airport(name,country);
        airportRepository.save(airport);
    }
    //=====READ=====
    public Optional<Airport> getAirport(String cityName) {
        return airportRepository.findAirportByCity(cityName);
    }
    public List<Airport> getAllAirport() {
        return airportRepository.findAll();
    }
    public List<Airport> getAirportBeginsWith(String prefix) {
        return airportRepository.findAirportsByCityStartsWith(prefix);
    }
    //Update
    //=====DELETE=====
//    public void deleteAirport(String cityName) {
//        Optional<Airport> airport = airportRepository.findAirportByCity(cityName);
//        if(airport.isPresent())
//            airportRepository.delete(airport.get());
//    }
}
