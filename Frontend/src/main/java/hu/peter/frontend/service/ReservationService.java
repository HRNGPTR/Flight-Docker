package hu.peter.frontend.service;

import com.sun.jndi.toolkit.url.Uri;
import hu.peter.frontend.config.RestConfig;
import hu.peter.frontend.dto.UserReservationDto;
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
public class ReservationService {
    private RestConfig restConfig;
    private final RestTemplate restTemplate;

    private final String RESERVATION_CONTROLLER = "http://localhost:8080/api/reservation";

    @Value("${backendUri}")
    String backendUri;

    @Autowired
    public ReservationService(RestConfig restConfig) {
        restTemplate = restConfig.getRestTemplate( new RestTemplateBuilder() );
    }

    public List<UserReservationDto> getReservationByUsername(String username) {
//        String url = RESERVATION_CONTROLLER.concat("/username/{username}");
        String url = backendUri.concat("/api/reservation/username/{username}");
        try {
            ResponseEntity<UserReservationDto[]> responseEntity = restTemplate.getForEntity(url,UserReservationDto[].class,username);
            return Arrays.asList(responseEntity.getBody());
        } catch ( HttpClientErrorException | NullPointerException e ) {
            return new ArrayList<>();
        }
    }
}
