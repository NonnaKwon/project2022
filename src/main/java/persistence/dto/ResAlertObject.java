package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResAlertObject {
    private static final long serialVersionUID = 2L;
    private String currencytmp; //외화종류
    private String alertAmount; //알람설정한 값
    private boolean judgement; //도달했는디
}
