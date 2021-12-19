package hu.peter.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirportDto implements Serializable {
    private Long id;
    @NotBlank(message = "must not be empty")
    private String city;
    @NotBlank(message = "must not be empty")
    private String country;
}
