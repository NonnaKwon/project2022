package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ExchangeDTO implements Serializable {
    private int country1;
    private int country2;
    private long amount;

}