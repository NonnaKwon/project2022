package persistence.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import persistence.dto.KoreaDTO;

import java.util.List;

public interface KoreaMapper {
    @Select("SELECT * FROM korea")
    @Results(id="koreaSet", value = {
            @Result(property = "date",column = "date"),
            @Result(property = "unit",column = "unit"),
            @Result(property = "ttb",column = "ttb"),
            @Result(property = "tts",column = "tts"),
            @Result(property = "deal",column = "deal"),
            @Result(property = "bkpr",column = "bkpr")
    })
    public List<KoreaDTO> selectAll();

    @Insert("INSERT INTO korea values (#{date}, #{unit}, #{ttb}, #{tts}, #{deal}, #{bkpr})")
    public boolean insert(KoreaDTO koreaDTO);
}
