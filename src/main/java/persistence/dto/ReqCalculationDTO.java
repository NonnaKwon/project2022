package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ReqCalculationDTO implements Serializable {
    private static final long serialVersionUID = 2L;
    private String currencytmp;
    private String exchangeOption;
    private long currentExchange;


}
