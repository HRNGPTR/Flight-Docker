package hu.peter.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SeatDto implements Serializable {
    private Long id;
    private Integer price;
    private PassengerDto passenger;
}
