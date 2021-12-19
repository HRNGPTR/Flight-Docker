package hu.peter.backend.repository;

import hu.peter.backend.entity.Airport;
import hu.peter.backend.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    List<Flight> findFlightByDepartureAndArrivalAndDate(Airport departure, Airport arrival, LocalDate date);
    List<Flight> findFlightByDepartureAndArrivalAndDateAndDateHour(Airport departure, Airport arrival, LocalDate date, LocalTime dateHour);
    List<Flight> findFlightsByDepartureAndArrival(Airport departure, Airport arrival);
}
