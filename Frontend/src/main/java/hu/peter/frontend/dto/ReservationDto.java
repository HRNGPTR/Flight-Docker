package hu.peter.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto implements Serializable {
    private String username;
    private Long flightId;
    private SeatDto seat;
}

