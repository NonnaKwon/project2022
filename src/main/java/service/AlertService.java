package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.AlertRequestDTO;
import persistence.dto.AlertResponseDTO;
import persistence.dto.DTO;
import readAPI.ReadData;

import java.util.ArrayList;

public class AlertService {
    private SqlSessionFactory sqlSessionFactory;
    private DAO[] daos = new DAO[31];
    private ArrayList<DTO> nowData;

    public AlertService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
        nowData = ReadData.dayTimeRead(sqlSessionFactory);
    }

    public AlertResponseDTO alertAmountService(AlertRequestDTO requestDTO){
        AlertResponseDTO result = new AlertResponseDTO();

        return result;
    }
}
