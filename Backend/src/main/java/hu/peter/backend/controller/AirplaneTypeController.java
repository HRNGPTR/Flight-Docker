package hu.peter.backend.controller;

import hu.peter.backend.dto.AirplaneTypeDto;
import hu.peter.backend.entity.AirplaneType;
import hu.peter.backend.service.AirplaneTypeService;
import hu.peter.backend.util.DtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/api/airplane_type")
public class AirplaneTypeController {

    private final AirplaneTypeService airplaneTypeService;
    private final DtoConverter dtoConverter = new DtoConverter();

    public AirplaneTypeController(AirplaneTypeService airplaneTypeService) {
        this.airplaneTypeService = airplaneTypeService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AirplaneTypeDto> getAirplaneTypeByCityName(@PathVariable("name") String name) {
        Optional<AirplaneType> apt = airplaneTypeService.getAirplaneTypeByName(name);
        if(apt.isPresent()) {
            AirplaneTypeDto aptDto = dtoConverter.airplaneTypeToDto(apt.get());
            return new ResponseEntity<>( aptDto , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/add")
    public ResponseEntity addAirplaneType( @RequestBody @Valid AirplaneTypeDto apt) {
        try {
            airplaneTypeService.createAirplaneType(apt.getTypeName(), apt.getNumOfRows(), apt.getNumOfRows());
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
