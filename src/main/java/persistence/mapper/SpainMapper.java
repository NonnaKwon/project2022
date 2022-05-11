package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.DTO;

import java.util.List;
import java.util.ArrayList;
public interface SpainMapper extends Mapper {
    String tableName = "spain";

    @Select("SELECT * FROM "+tableName)
    @Results(id="americaSet", value = {
            @Result(property = "date",column = "date"),
            @Result(property = "unit",column = "unit"),
            @Result(property = "ttb",column = "ttb"),
            @Result(property = "tts",column = "tts"),
            @Result(property = "deal",column = "deal"),
            @Result(property = "bkpr",column = "bkpr")
    })
    public ArrayList<DTO> selectAll();

    @Select("SELECT bkpr FROM "+tableName+" WHERE date = #{date}")
    public String selectOneBkpr(String date);

    @Select("SELECT bkpr FROM "+tableName+" WHERE date BETWEEN #{startDate} AND #{endDate}")
    public ArrayList<String> selectBkpr(@Param("startDate") String startDate,@Param("endDate") String endDate);

    @Insert("INSERT INTO "+tableName+" values (#{date}, #{unit}, #{ttb}, #{tts}, #{deal}, #{bkpr})")
    public boolean insert(DTO dto);
}

