package hu.peter.backend.util;

import hu.peter.backend.dto.*;
import hu.peter.backend.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {

    /* entity to dto */
    public static AirportDto airportToDto(Airport ap) {
        return new AirportDto(ap.getId(),ap.getCity(),ap.getCountry());
    }
    public static AirplaneTypeDto airplaneTypeToDto(AirplaneType apt) {
        return new AirplaneTypeDto(apt.getId(),apt.getTypeName(),apt.getNumOfRows(),apt.getSeatsPerRows());
    }
    public static PassengerDto passengerToDto(Passenger p) {
        if( p == null )
            return null;
        return new PassengerDto(p.getId(),p.getFirstName(),p.getSecondName(),p.getSex(),p.getPassport());
    }
    public static SeatDto seatToDto(Seat s) {
        SeatDto seatDto = new SeatDto();
        PassengerDto p = passengerToDto(s.getPassenger());
        seatDto.setPassenger(p);
        seatDto.setPrice(s.getPrice());
        seatDto.setId(s.getId());
        return seatDto;
    }
    public static AirplaneDto airplaneToDto(Airplane ap) {
        AirplaneTypeDto apt = airplaneTypeToDto(ap.getAirplaneType());
        List<SeatDto> seatDtos = ap.getSeats().stream().map(DtoConverter::seatToDto).collect(Collectors.toList());
        return new AirplaneDto(ap.getId(),apt, ap.getSeatPrice(), seatDtos);

    }
    public static FlightDto flightToDto(Flight f) {
        AirportDto dpt = airportToDto(f.getDeparture());
        AirportDto arv = airportToDto(f.getArrival());
        AirplaneDto ap = airplaneToDto(f.getAirplane());
        return new FlightDto(f.getId(), dpt,arv,f.getDate(),f.getDateHour(),f.getTravelTime(),f.getArrivalTime(),f.getSeatPrice(),ap);
    }
}
