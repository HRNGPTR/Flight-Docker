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
public class PassengerDto implements Serializable {
    public enum Sex {
        MALE,
        FEMALE
    }
    private Long id;
    private String firstName;
    private String secondName;
    private Sex sex;
    private String passport;
}