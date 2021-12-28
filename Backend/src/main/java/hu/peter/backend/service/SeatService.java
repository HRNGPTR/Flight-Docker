package hu.peter.backend.service;

import hu.peter.backend.entity.Seat;
import hu.peter.backend.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }


    //=====READ=====
    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    //Update
    public void saveSeat(Seat seat) {
        seatRepository.save(seat);
    }
}
