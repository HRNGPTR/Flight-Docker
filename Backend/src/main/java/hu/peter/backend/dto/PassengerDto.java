package hu.peter.backend.dto;

import hu.peter.backend.entity.Passenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PassengerDto implements Serializable {
    private Long id;
    private String firstName;
    private String secondName;
    private Passenger.Sex sex;
    private String passport;
}
