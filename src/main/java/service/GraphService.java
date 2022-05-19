package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.ReqGraphDTO;
import persistence.dto.ResGraphDTO;

import java.util.ArrayList;

public class GraphService {
    private SqlSessionFactory sqlSessionFactory;
    private DAO[] daos = new DAO[31];

    public GraphService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
    }


    //달, 년 이거 설명 듣고 적절하게 바꾸기
    public ResGraphDTO bkprGraphMonthService(ReqGraphDTO graphDTO) {
        ResGraphDTO result = new ResGraphDTO();
        int countryCode = Country.getCode(graphDTO.getForex());
        ArrayList<String> list = daos[countryCode].selectBkpr(graphDTO.getStartDate(),graphDTO.getEndDate());
        result.setList(list);
        return result;
    }

    public ResGraphDTO bkprGraphYearService(ReqGraphDTO graphDTO) {
        ResGraphDTO result = new ResGraphDTO();
        int countryCode = Country.getCode(graphDTO.getForex());
        ArrayList<String> list = daos[countryCode].selectBkpr(graphDTO.getStartDate(),graphDTO.getEndDate());
        result.setList(list);
        return result;
    }

}
