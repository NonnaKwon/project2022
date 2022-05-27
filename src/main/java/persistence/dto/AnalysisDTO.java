package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AnalysisDTO implements Serializable {
    private static final long serialVersionUID = 2L;
    String forex;
    double figure;
}
