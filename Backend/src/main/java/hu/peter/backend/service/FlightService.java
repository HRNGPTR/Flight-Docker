package hu.peter.backend.service;

import hu.peter.backend.dto.PassengerDto;
import hu.peter.backend.dto.ReservationDto;
import hu.peter.backend.dto.SeatDto;
import hu.peter.backend.entity.*;
import hu.peter.backend.exception.ReserveSeatException;
import hu.peter.backend.repository.FlightRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final AirplaneTypeService airplaneTypeService;
    private final PassengerService passengerService;
    private final SeatService seatService;
    private final UserReservationService reservationService;

    public FlightService(FlightRepository flightRepository, AirportService airportService
            , AirplaneTypeService airplaneTypeService, PassengerService passengerService
            , SeatService seatService, UserReservationService reservationService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
        this.airplaneTypeService = airplaneTypeService;
        this.passengerService = passengerService;
        this.seatService = seatService;
        this.reservationService = reservationService;
    }

    //=====CREATE=====
    public void createFlight(String from, String to, LocalDate date,LocalTime dateHour, LocalTime arrivalTime, Integer price, String airplaneType) {
        Optional<Airport> departure = airportService.getAirport(from);
        Optional<Airport> arrival = airportService.getAirport(to);
        Optional<AirplaneType> type = airplaneTypeService.getAirplaneTypeByName(airplaneType);
        if( departure.isPresent() && arrival.isPresent() && type.isPresent() ) {
            Flight flight = new Flight( departure.get(), arrival.get(), date, dateHour, arrivalTime, price, type.get());
            flightRepository.save(flight);

        } else {
            System.err.println("Error while creating flight");
        }
    }
    //=====READ=====
    public List<Flight> getAllFlight() {
        return flightRepository.findAll();
    }
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
    public List<Flight> getAllFlightByDepartureAndArrival(String from, String to) {
        Optional<Airport> departure = airportService.getAirport(from);
        Optional<Airport> arrival = airportService.getAirport(to);
        if(departure.isPresent() && arrival.isPresent() ) {
            return  flightRepository.findFlightsByDepartureAndArrival(departure.get(), arrival.get());
        }
        return null;
    }
    public List<Flight> getFlightByDepartureAndArrivalAndDate(String from, String to, LocalDate date) {
        Optional<Airport> departure = airportService.getAirport(from);
        Optional<Airport> arrival = airportService.getAirport(to);
        if(departure.isPresent() && arrival.isPresent() )
            return flightRepository.findFlightByDepartureAndArrivalAndDate(departure.get(), arrival.get(), date);
        return flightRepository.findFlightByDepartureAndArrivalAndDate(null,null, date);
    }
    public List<Flight> getFlightByDepartureAndArrivalAndDateAndHour(String from, String to, LocalDate date, LocalTime hour) {
        Optional<Airport> departure = airportService.getAirport(from);
        Optional<Airport> arrival = airportService.getAirport(to);
        if(departure.isPresent() && arrival.isPresent() )
            return flightRepository.findFlightByDepartureAndArrivalAndDateAndDateHour(departure.get(), arrival.get(), date, hour);
        return flightRepository.findFlightByDepartureAndArrivalAndDate(null,null, date);
    }
    //Update
    @Transactional(rollbackFor = {SQLException.class})
    public void reserveSeat(ReservationDto reservation) throws SQLException, ReserveSeatException, DataIntegrityViolationException {
        Optional<Flight> flight = flightRepository.findById(reservation.getFlightId());
        if(!flight.isPresent()) throw new ReserveSeatException("Flight not found: wrong id");

        Optional<Seat> seat = seatService.getSeatById(reservation.getSeat().getId());
        if(!seat.isPresent()) throw new ReserveSeatException("Seat not found: wrong id");

        SeatDto seatDto = reservation.getSeat();
        if(seatDto.getPassenger() == null) throw new ReserveSeatException("Passenger not found: null passed");

        if( seat.get().getPassenger() == null ) {
            PassengerDto pdto = seatDto.getPassenger();
            Passenger p = passengerService.createAndGetPassenger(pdto.getFirstName()
                    , pdto.getSecondName(),pdto.getSex(),pdto.getPassport());

            seat.get().setPassenger(p);
            seatService.saveSeat(seat.get());

                Reservation res = new Reservation();
                res.setUsername(reservation.getUsername());
                res.setFlight(flight.get());
                res.setSeat(seat.get());
                reservationService.addReservation(res);

            flightRepository.save(flight.get());
        } else throw new ReserveSeatException("Seat already reserved");
    }
}
