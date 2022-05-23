package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.ReqSearchDTO;
import persistence.dto.ResSearchDTO;

public class SearchService {
    private SqlSessionFactory sqlSessionFactory;
    private DAO[] daos = new DAO[22];

    public SearchService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<22;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
    }

    public ResSearchDTO searchFromDate(ReqSearchDTO searchRequestDTO) {
        int countryCode = Country.getCode(searchRequestDTO.getForex());
        ResSearchDTO result = daos[countryCode].selectOneDto(searchRequestDTO.getDateInput());
        return result;
    }

}
