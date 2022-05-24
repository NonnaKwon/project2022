package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@ToString
public class ReqAlertDTO implements Serializable {
    private static final long serialVersionUID = 2L;
    private ArrayList<AlertObject> timerArrayList;
}
