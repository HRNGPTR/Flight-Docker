package hu.peter.backend.controller;

import hu.peter.backend.dto.FlightDto;
import hu.peter.backend.dto.ReservationDto;
import hu.peter.backend.entity.Flight;
import hu.peter.backend.exception.ReserveSeatException;
import hu.peter.backend.service.FlightService;
import hu.peter.backend.util.DtoConverter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightDto>> getAllFlight() {
        try {
            List<FlightDto> flights = flightService.getAllFlight()
                    .stream().map( DtoConverter::flightToDto ).collect(Collectors.toList());
            return new ResponseEntity<>(flights, HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK); //empty list, no flight found
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable("id") Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        if(flight.isPresent()) {
            return new ResponseEntity<>( DtoConverter.flightToDto(flight.get()), HttpStatus.OK );
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/from/{from}/to/{to}")
    public ResponseEntity<List<FlightDto>> getFlightByDepartureAndArrival(@PathVariable("from") String from
            ,@PathVariable("to") String to) {
        try {
            List<FlightDto> flights = flightService.getAllFlightByDepartureAndArrival(from, to)
                    .stream().map( DtoConverter::flightToDto ).collect(Collectors.toList());
            return new ResponseEntity<>(flights, HttpStatus.OK );
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @GetMapping("/from/{from}/to/{to}/date/{date}")
    public ResponseEntity<List<FlightDto>> getFlightByDepartureAndArrivalAndDate(@PathVariable("from") String from
            ,@PathVariable("to") String to,@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        try {
            List<FlightDto> flight = flightService.getFlightByDepartureAndArrivalAndDate(from, to, date)
                    .stream().map(DtoConverter::flightToDto).collect(Collectors.toList());
            return new ResponseEntity<>(flight,HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>( new ArrayList<>(), HttpStatus.OK);
        }
    }
    @GetMapping("/from/{from}/to/{to}/date/{date}/hour/{hour}")
    public ResponseEntity<List<FlightDto>> getFlightByDepartureAndArrivalAndDateAndDateHour(@PathVariable("from") String from
            ,@PathVariable("to") String to,@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date
            , @PathVariable("hour") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime hour) {
        try {
            List<FlightDto> flights = flightService.getFlightByDepartureAndArrivalAndDateAndHour(from, to, date, hour)
                    .stream().map(DtoConverter::flightToDto).collect(Collectors.toList());
            return new ResponseEntity<>(flights,HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<HttpStatus> reserveSeat(@RequestBody @Valid ReservationDto reservation) {
        try {
            flightService.reserveSeat(reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException | ReserveSeatException | DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
