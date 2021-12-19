package hu.peter.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightDto implements Serializable {
    private Long id;
    private AirportDto departure;
    private AirportDto arrival;
    private LocalDate date;
    private LocalTime dateHour;
    private LocalTime travelTime;
    private LocalTime arrivalTime;
    private Integer seatPrice;
    private AirplaneDto airplane;
}
