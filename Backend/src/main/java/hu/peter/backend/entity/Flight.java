package hu.peter.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Airport departure;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Airport arrival;

    private LocalDate date; //mikor indul
    private LocalTime dateHour; //hany orakkor indul
    private LocalTime travelTime; //mennyi ideig tart
    private LocalTime arrivalTime; //mikor erkezik meg
    private Integer seatPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private Airplane airplane;

    public Flight(Airport departure, Airport arrival, LocalDate date
            ,LocalTime dateHour,LocalTime arrivalTime
            , Integer seatPrice, AirplaneType airplaneType) {
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.dateHour = dateHour;
        this.arrivalTime = arrivalTime;
        this.seatPrice = seatPrice;
        this.airplane = new Airplane(airplaneType, seatPrice);

        int hour = Math.abs(arrivalTime.getHour() - dateHour.getHour() );
        int minute = Math.abs(arrivalTime.getMinute() - dateHour.getMinute() );
        this.travelTime = LocalTime.of(hour,minute);
    }
}
