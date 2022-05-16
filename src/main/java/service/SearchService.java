package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.ExchangeDTO;
import persistence.dto.SearchRequestDTO;

public class SearchService {
    private SqlSessionFactory sqlSessionFactory;
    private DAO[] daos = new DAO[31];

    public SearchService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
    }

    public DTO searchFromDate(SearchRequestDTO searchRequestDTO) {
        int countryCode = Country.getCode(searchRequestDTO.getForex());
        if(countryCode != -1){

        }
        DTO result = new DTO();
        return result;
    }

}
