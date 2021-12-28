package hu.peter.frontend.service;

import hu.peter.frontend.config.RestConfig;
import hu.peter.frontend.dto.AirportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AirportService {


    private RestConfig restConfig;
    private final RestTemplate restTemplate;

    private final String AIRPORT_CONTROLLER = "http://localhost:8080/api/airport";

    @Value("${backendUri}")
    String backendUri;

    @Autowired
    public AirportService(RestConfig restConfig) {
        restTemplate = restConfig.getRestTemplate( new RestTemplateBuilder() );
    }

    public List<AirportDto> getAllAirport() {
//        String url = AIRPORT_CONTROLLER.concat("/all");
        String url = backendUri.concat("/api/airport/all");
        ResponseEntity<AirportDto[]> responseEntity = restTemplate.getForEntity(url, AirportDto[].class);
        return Arrays.asList(responseEntity.getBody());
    }
    public List<AirportDto> getAirportByPrefix(String prefix) {
        try {
//            String url = AIRPORT_CONTROLLER.concat("/prefix/{prefix}");
            String url = backendUri.concat("/api/airport/prefix/{prefix}");
            ResponseEntity<AirportDto[]> responseEntity = restTemplate.getForEntity(url, AirportDto[].class, prefix);
            return Arrays.asList(responseEntity.getBody());
        }catch (HttpClientErrorException | NullPointerException e) {
            return new ArrayList<>(); //empty list
        }
    }
}
