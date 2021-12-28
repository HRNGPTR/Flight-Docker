package hu.peter.backend.service;

import hu.peter.backend.entity.Airport;
import hu.peter.backend.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @Mock
    AirportRepository airportRepository;

    @InjectMocks
    AirportService airportService;

    @BeforeEach
    public void init() {
        when(airportRepository.save(any(Airport.class)))
                .then(returnsFirstArg());
    }

    @Test
    public void testGetAirportByName() {
        Airport airportBp = airportService.createAirport("Budapest", "Magyarország");
        given(airportRepository.findAirportByCity("Budapest")).willReturn(java.util.Optional.ofNullable(airportBp));
        Optional<Airport> currentAirport = airportService.getAirport("Budapest");
        assertThat(currentAirport.get()).isSameAs(airportBp);
    }

    @Test
    public void testGetAllAirport() {
        Airport airportBp = airportService.createAirport("Budapest", "Magyarország");
        Airport airportBerlin = airportService.createAirport("Berlin", "Németország");
        given(airportRepository.findAll()).willReturn(asList(airportBp, airportBerlin));
//        given(airplaneTypeRepository.findAll()).willReturn(asList(airplaneType737, airplaneType747));
        List<Airport> airports = airportService.getAllAirport();
        assertThat(airports).containsExactly(airportBp, airportBerlin);
    }

    @Test
    public void testGetAirportBeginsWith() {
        Airport airportBp = airportService.createAirport("Budapest", "Magyarország");
        Airport airportBerlin = airportService.createAirport("Berlin", "Németország");
        given(airportRepository.findAirportsByCityStartsWith("B")).willReturn(asList(airportBp, airportBerlin));
        List<Airport> airportsBeginWithBCharacter = airportService.getAirportBeginsWith("B");
        assertThat(airportsBeginWithBCharacter).containsExactly(airportBp, airportBerlin);
        given(airportRepository.findAirportsByCityStartsWith("Be")).willReturn(asList(airportBerlin));
        List<Airport> airportsBeginWithBandECharacter = airportService.getAirportBeginsWith("Be");
        assertThat(airportsBeginWithBandECharacter).containsExactly(airportBerlin);

    }
}