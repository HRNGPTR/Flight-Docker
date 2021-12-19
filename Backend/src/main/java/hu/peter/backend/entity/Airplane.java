package hu.peter.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Airplane {

    private final int DEFAULT_SEATS_PER_ROW = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private AirplaneType airplaneType;
    private Integer seatPrice;  //not really necessary(redundant)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Seat> seats;

    public Airplane(AirplaneType airplaneType, Integer seatPrice) {
        this.airplaneType = airplaneType;
        this.seatPrice = seatPrice;

        seats = new ArrayList<>();
        int rows = airplaneType.getNumOfRows();
        int columns = airplaneType.getSeatsPerRows();
        columns = DEFAULT_SEATS_PER_ROW; //for front-end reasons (not ideal, but at this point necessary)

        for( int row = 0; row < rows; row++ ) {
            for( int column = 0; column < columns; column++ ) {
                Seat seat = new Seat(seatPrice);
                seats.add(seat);
            }
        }
    }
}
