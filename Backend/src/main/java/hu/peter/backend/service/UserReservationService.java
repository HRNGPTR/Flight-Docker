package hu.peter.backend.service;

import hu.peter.backend.dto.UserReservationDto;
import hu.peter.backend.entity.Reservation;
import hu.peter.backend.repository.ReservationRepository;
import hu.peter.backend.util.DtoConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReservationService {

    private final ReservationRepository reservationRepository;

    public UserReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<UserReservationDto> getUserReservationByUsername(String username) {
        List<Reservation> reservations = reservationRepository.findReservationByUsername(username);
        List<UserReservationDto> userReservations = new ArrayList<>();
        if(reservations!=null) {
            for( Reservation r : reservations ) {
                userReservations.add( new UserReservationDto( r.getUsername(), DtoConverter.flightToDto(r.getFlight())
                        , DtoConverter.seatToDto(r.getSeat()) ) );
            }
        }
        return userReservations;
    }
}
