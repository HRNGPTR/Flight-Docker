package hu.peter.frontend.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SeatRepresentation {
    public enum Status {
        RESERVED,
        SELECTED,
        FREE
    }
    private Integer id;
    private Status status;
    private boolean disabled;

    public SeatRepresentation(Integer id, Status status) {
        this.id = id;
        this.status = status;
        this.disabled = false;
    }
}
