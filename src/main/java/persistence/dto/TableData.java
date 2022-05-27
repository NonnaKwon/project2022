package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class TableData implements Serializable {
    private static final long serialVersionUID = 2L;
    String type; //분석종류
    String figure; //수치
    String analysis; //분석점수
}
