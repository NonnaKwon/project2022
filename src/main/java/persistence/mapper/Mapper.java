package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import persistence.dto.DTO;
import persistence.dto.ResSearchDTO;

import java.util.ArrayList;

public interface Mapper {
    public ArrayList<DTO> selectAll();
    public ResSearchDTO selectOneDto(String date);
    public String selectOneBkpr(String date);
    public ArrayList<String> selectBkpr(@Param("startDate") String startDate, @Param("endDate") String endDate);
    public boolean insert(DTO dto);

}
