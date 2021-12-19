package hu.peter.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto implements Serializable {
    @NotEmpty(message = "username must not be empty")
    private String username;
    @NotNull(message = "flightId must not be null")
    private Long flightId;
    @NotNull( message = "seat must not be empty")
    private SeatDto seat;
}
