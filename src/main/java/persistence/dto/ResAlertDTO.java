package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@ToString
@Getter
public class ResAlertDTO implements Serializable {
    private boolean reach;
    private int num;
}
