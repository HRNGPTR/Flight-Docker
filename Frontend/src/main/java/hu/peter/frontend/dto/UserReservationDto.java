package hu.peter.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReservationDto implements Serializable {
    private String username;
    private FlightDto flightDto;
    private SeatDto seatDto;

    public String seatNumber() {
        if(seatDto.getPassenger()==null) return " - ";
        int index = 0;
        List<SeatDto> seats = flightDto.getAirplane().getSeats();
        for(;index<seats.size();index++) {
            PassengerDto p = seats.get(index).getPassenger();
            System.out.println(seatDto.getPassenger());
            if(p==null) continue;
            if( seatDto.getPassenger().getSecondName().equals(p.getSecondName())
                && seatDto.getPassenger().getFirstName().equals(p.getFirstName())
                && seatDto.getPassenger().getSex().equals(p.getSex())
                && seatDto.getPassenger().getPassport().equals(p.getPassport())) {
                break;
                }
        }
        System.out.println("INDEX:"+index);
        int row = index / 6 +  1;
        String[] letters = {"A","B","C","D","E","F"};
        String letter = letters[ index%6 ];
        return Integer.toString(row).concat(letter);
    }
}