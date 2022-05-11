package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GraphRequestDTO {
    private String startDate;
    private String endDate;
    private String country;
}
