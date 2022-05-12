package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class GraphRequestDTO implements Serializable {
    private String startDate;
    private String endDate;
    private String country;
}
