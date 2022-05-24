package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class AlertObject implements Serializable {
    private String currencytmp;
    private String alertAmount;
}
