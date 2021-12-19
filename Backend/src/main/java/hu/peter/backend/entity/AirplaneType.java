package hu.peter.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AirplaneType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String typeName;
    private Integer numOfRows;  //sorok szama
    private Integer seatsPerRows; //soronkent hany ules van

    public AirplaneType(String typeName, Integer numOfRows, Integer seatsPerRows) {
        this.typeName = typeName;
        this.numOfRows = numOfRows;
        this.seatsPerRows = seatsPerRows;
    }
}
