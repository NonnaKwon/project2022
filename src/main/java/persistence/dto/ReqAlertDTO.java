package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class ReqAlertDTO {
    private ArrayList<AlertObject> timerArrayList;
}
