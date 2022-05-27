package persistence.mapper.analysis;

import org.apache.ibatis.annotations.*;
import persistence.dto.AnalysisDTO;

import java.util.ArrayList;

public interface MacdMapper extends AnalysisMapper{
    String tableName = "macd";

    @Select("SELECT * FROM "+tableName)
    @Results(id="allset", value = {
            @Result(property = "forex",column = "forex"),
            @Result(property = "figure",column = "figure"),
    })
    public ArrayList<AnalysisDTO> selectAll();

    @Select("SELECT figure FROM "+tableName+" WHERE forex = #{forex}")
    public double selectFigure(@Param("forex") String forex);

    @Insert("INSERT INTO "+tableName+" values (#{forex}, #{figure})")
    public boolean insert(AnalysisDTO dto);

}
