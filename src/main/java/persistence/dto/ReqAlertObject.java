package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class ReqAlertObject implements Serializable {
    private static final long serialVersionUID = 2L;
    private String currencytmp; //
    private String alertAmount;
}
