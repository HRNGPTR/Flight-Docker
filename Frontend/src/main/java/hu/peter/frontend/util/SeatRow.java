package hu.peter.frontend.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SeatRow {
    private Integer num;
    private List<SeatRepresentation> row;

    public SeatRow(Integer num ,List<SeatRepresentation> seatRepresentations) {
        this.num = num;
        row = seatRepresentations;
    }


}
