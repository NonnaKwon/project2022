package persistence.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import persistence.dto.DTO;

import java.util.List;

public interface AustraliaMapper {
    @Select("SELECT * FROM australia")
    @Results(id="australiaSet", value = {
            @Result(property = "date",column = "date"),
            @Result(property = "unit",column = "unit"),
            @Result(property = "ttb",column = "ttb"),
            @Result(property = "tts",column = "tts"),
            @Result(property = "deal",column = "deal"),
            @Result(property = "bkpr",column = "bkpr")
    })
    public List<DTO> selectAll();

    @Insert("INSERT INTO australia values (#{date}, #{unit}, #{ttb}, #{tts}, #{deal}, #{bkpr})")
    public boolean insert(DTO dto);
}

