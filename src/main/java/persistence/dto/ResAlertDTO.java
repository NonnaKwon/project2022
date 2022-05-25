package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@ToString
@Getter
public class ResAlertDTO implements Serializable {
    private static final long serialVersionUID = 2L;
    ArrayList<ResAlertObject> resList;
}
