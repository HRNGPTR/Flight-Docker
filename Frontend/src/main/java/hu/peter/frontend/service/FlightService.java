package hu.peter.frontend.service;

import hu.peter.frontend.config.RestConfig;
import hu.peter.frontend.dto.FlightDto;
import hu.peter.frontend.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FlightService {

    private RestConfig restConfig;
    private final RestTemplate restTemplate;

    private final String FLIGHT_CONTROLLER = "http://localhost:8080/api/flight";
        @Value("${backendUri}")
        String backendUri;

    @Autowired
    public FlightService(RestConfig restConfig) {
        restTemplate = restConfig.getRestTemplate( new RestTemplateBuilder() );
    }

    public List<FlightDto> getFlightOnDate(String from, String to, LocalDate date) {
//        String url = FLIGHT_CONTROLLER.concat("/from/{from}/to/{to}/date/{date}");
        String url = backendUri.concat("/api/flight/from/{from}/to/{to}/date/{date}");
        try {
            ResponseEntity<FlightDto[]> responseEntity = restTemplate.getForEntity(url, FlightDto[].class, from, to, date);
            return Arrays.asList(responseEntity.getBody());
        } catch (HttpClientErrorException | NullPointerException e ) {
            return new ArrayList<>();
        }
    }
    public FlightDto getFlightById(Long id) {
//        String url = FLIGHT_CONTROLLER.concat("/id/{id}");
        String url = backendUri.concat("/api/flight/id/{id}");
        //TODO: try - catch
            ResponseEntity<FlightDto> responseEntity = restTemplate.getForEntity(url, FlightDto.class, id);
            return responseEntity.getBody();

    }

    //TODO: finomitas
    public boolean reserveSeat(ReservationDto reservation) {
//        String url = FLIGHT_CONTROLLER.concat("/reserve");
        String url = backendUri.concat("/api/flight/reserve");
        ResponseEntity<HttpStatus> responseEntity = restTemplate.postForEntity(url,reservation,HttpStatus.class);
        if(responseEntity.getStatusCode()==HttpStatus.OK) return true;
        return false;
//        if(responseEntity.getStatusCode()==HttpStatus.BAD_REQUEST)
//            return false;
    }
}
