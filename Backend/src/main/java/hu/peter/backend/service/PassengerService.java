package hu.peter.backend.service;

import hu.peter.backend.entity.Passenger;
import hu.peter.backend.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    //=====CREATE=====
    public void createPassenger(String firstName, String secondName, Passenger.Sex sex, String passport) {
        Passenger passenger = new Passenger(firstName,secondName,sex,passport);
        passengerRepository.save( passenger );
    }
    public Passenger createAndGetPassenger(String firstName, String secondName, Passenger.Sex sex, String passport) {
        return new Passenger(firstName,secondName,sex,passport);
    }
    //=====READ=====
    public Optional<Passenger> getPassenger() {
        return null;
    }
}
