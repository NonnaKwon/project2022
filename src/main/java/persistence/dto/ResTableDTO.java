package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResTableDTO implements Serializable {
    private static final long serialVersionUID = 2L;
    private TableData[] tableData = new TableData[3];
}
