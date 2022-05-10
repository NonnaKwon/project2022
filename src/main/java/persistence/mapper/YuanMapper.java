package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.YuanDTO;

import java.util.List;

public interface YuanMapper {
    @Select("SELECT * FROM yuan")
    @Results(id="yuanSet", value = {
            @Result(property = "date",column = "date"),
            @Result(property = "unit",column = "unit"),
            @Result(property = "ttb",column = "ttb"),
            @Result(property = "tts",column = "tts"),
            @Result(property = "deal",column = "deal"),
            @Result(property = "bkpr",column = "bkpr")
    })
    public List<YuanDTO> selectAll();

    @Insert("INSERT INTO yuan values (#{date}, #{unit}, #{ttb}, #{tts}, #{deal}, #{bkpr})")
    public boolean insert(YuanDTO yuanDTO);

}
