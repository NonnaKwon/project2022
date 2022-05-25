package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class DTO implements Serializable {
    private static final long serialVersionUID = 2L;
    private String date;
    private String unit;
    private String ttb;
    private String tts;
    private String deal;
    private String bkpr;

}
