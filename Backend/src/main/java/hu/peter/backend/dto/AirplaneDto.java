package hu.peter.backend.dto;

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
public class AirplaneDto implements Serializable {
    private Long id;
    private AirplaneTypeDto airplaneType;
    private Integer seatPrice;
    private List<SeatDto> seats;

}
