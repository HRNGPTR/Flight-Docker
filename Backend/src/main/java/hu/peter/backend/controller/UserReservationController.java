package hu.peter.backend.controller;

import hu.peter.backend.dto.UserReservationDto;
import hu.peter.backend.service.UserReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/reservation")
public class UserReservationController {

    private final UserReservationService userReservationService;

    public UserReservationController(UserReservationService userReservationService) {
        this.userReservationService = userReservationService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<UserReservationDto>> getUserReservations(@PathVariable("username") String username) {
        return new ResponseEntity<>( userReservationService.getUserReservationByUsername(username), HttpStatus.OK );
    }
}
