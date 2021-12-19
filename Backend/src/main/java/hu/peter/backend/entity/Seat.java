package hu.peter.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price;

    @OneToOne( cascade = CascadeType.ALL )
    private Passenger passenger;

    public Seat(Integer price) {
        this.price = price;
    }
}
