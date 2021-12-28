package hu.peter.backend.service;

import hu.peter.backend.entity.AirplaneType;
import hu.peter.backend.repository.AirplaneTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirplaneTypeService {

    private final AirplaneTypeRepository airplaneTypeRepository;

    public AirplaneTypeService(AirplaneTypeRepository airplaneTypeRepository) {
        this.airplaneTypeRepository = airplaneTypeRepository;
    }

    /*-----CREATE-----*/
    public AirplaneType createAirplaneType(String typeName, Integer numOfRows, Integer seatOfRows) {
        AirplaneType airplaneType = new AirplaneType(typeName, numOfRows, seatOfRows);
        return airplaneTypeRepository.save(airplaneType);
    }

    /*-----READ-----*/
    public List<AirplaneType> getAllAirplaneType() {
        return airplaneTypeRepository.findAll();
    }

    public Optional<AirplaneType> getAirplaneTypeByName(String typeName) {
        return airplaneTypeRepository.findAirplaneTypeByTypeName(typeName);
    }

}
