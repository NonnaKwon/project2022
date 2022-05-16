package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.SearchRequestDTO;
import persistence.dto.SearchResponseDTO;

public class SearchService {
    private SqlSessionFactory sqlSessionFactory;
    private DAO[] daos = new DAO[31];

    public SearchService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
    }

    public SearchResponseDTO searchFromDate(SearchRequestDTO searchRequestDTO) {
        int countryCode = Country.getCode(searchRequestDTO.getForex());
        SearchResponseDTO result = daos[countryCode].selectOneDto(searchRequestDTO.getDateInput());
        return result;
    }

}
