package hu.peter.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Passenger {
    public enum Sex {
        MALE,
        FEMALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;
    private Sex sex;
    @Column(unique = true)
    private String passport;

    public Passenger(String firstName, String secondName, Sex sex, String passport) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.sex = sex;
        this.passport = passport;
    }
}
