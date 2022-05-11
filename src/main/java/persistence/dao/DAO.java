package persistence.dao;

import persistence.dto.DTO;

import java.util.ArrayList;

public interface DAO {
    public boolean insert(DTO dto);
    public ArrayList<DTO> selectAll();
    public ArrayList<String> selectBkpr(String startDate,String endDate);
    public String selectOneBkpr(String date);
}