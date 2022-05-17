package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReqSearchDTO {
    private String dateInput;
    private String forex;
}
