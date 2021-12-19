package hu.peter.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirplaneTypeDto implements Serializable {
    private Long id;
    private String typeName;
    private Integer numOfRows;
    private Integer seatsPerRows;
}
