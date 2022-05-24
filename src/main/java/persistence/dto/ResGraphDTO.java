package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
@ToString
public class ResGraphDTO implements Serializable {
    private static final long serialVersionUID = 2L;
    private ArrayList<String> list;
}
