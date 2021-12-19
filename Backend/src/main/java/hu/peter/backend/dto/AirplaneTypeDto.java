package hu.peter.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirplaneTypeDto implements Serializable {
    private Long id;
    @NotBlank(message = "must not be blank")
    private String typeName;
    @NotNull(message = "must not be null")
    private Integer numOfRows;
    @NotNull(message = "must not be null")
    private Integer seatsPerRows;
}
