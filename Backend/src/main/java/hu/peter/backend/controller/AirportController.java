package hu.peter.backend.controller;

import hu.peter.backend.dto.AirportDto;
import hu.peter.backend.entity.Airport;
import hu.peter.backend.service.AirportService;
import hu.peter.backend.util.DtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/airport")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("/prefix/{prefix}")
    public ResponseEntity<List<AirportDto>> getAirportBeginsWith(@PathVariable("prefix") String prefix) {
        try {
            List<AirportDto> airportDtos = airportService.getAirportBeginsWith(prefix)
                    .stream().map(DtoConverter::airportToDto).collect(Collectors.toList());
            return new ResponseEntity<>(airportDtos, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>( new ArrayList<>(),HttpStatus.OK);  //empty list
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<AirportDto>> getAllAirport() {
        try {
            List<AirportDto> airportDtos = airportService.getAllAirport().stream()
                    .map(DtoConverter::airportToDto).collect(Collectors.toList());
            return new ResponseEntity<>(airportDtos, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>( new ArrayList<>(),HttpStatus.OK);  //empty list
        }
    }
    @GetMapping("/city/{city}")
    public ResponseEntity<AirportDto> getAirportByCityName(@PathVariable("city") String city) {
        Optional<Airport> ap = airportService.getAirport(city);
        if(ap.isPresent()) {
            return new ResponseEntity<>(  DtoConverter.airportToDto(ap.get()) ,HttpStatus.OK);
        } else {
            return new ResponseEntity<>( null, HttpStatus.OK ); //not found
        }
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addAirport( @RequestBody @Valid AirportDto ap) {
        try {
            airportService.createAirport(ap.getCity(),ap.getCountry());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {  // try-catch not necceasary
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
