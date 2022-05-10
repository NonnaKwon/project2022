package persistence.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import persistence.dto.AmericaDTO;
import persistence.dto.YuanDTO;

import java.util.List;

public interface AmericaMapper {
    @Select("SELECT * FROM america")
    @Results(id="americaSet", value = {
            @Result(property = "date",column = "date"),
            @Result(property = "unit",column = "unit"),
            @Result(property = "ttb",column = "ttb"),
            @Result(property = "tts",column = "tts"),
            @Result(property = "deal",column = "deal"),
            @Result(property = "bkpr",column = "bkpr")
    })
    public List<AmericaDTO> selectAll();

    @Insert("INSERT INTO america values (#{date}, #{unit}, #{ttb}, #{tts}, #{deal}, #{bkpr})")
    public boolean insert(AmericaDTO americaDTO);
}
