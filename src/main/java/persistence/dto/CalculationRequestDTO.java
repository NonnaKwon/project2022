package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CalculationRequestDTO {
    private String currencytmp;
    private String exchangeOption;
    private long currentExchange;
}
