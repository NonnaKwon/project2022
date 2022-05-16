package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class AlertResponseDTO {
    private boolean reach;
    private int num;
}
