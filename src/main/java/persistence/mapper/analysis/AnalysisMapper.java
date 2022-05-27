package persistence.mapper.analysis;

import org.apache.ibatis.annotations.Param;
import persistence.dto.AnalysisDTO;

import java.util.ArrayList;

public interface AnalysisMapper {
    public ArrayList<AnalysisDTO> selectAll();
    public double selectFigure(@Param("forex") String forex);
    public boolean insert(AnalysisDTO dto);
}
