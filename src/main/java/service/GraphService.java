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


    public ResGraphDTO bkprService(ReqGraphDTO graphDTO) {
        ResGraphDTO result = new ResGraphDTO();
        int countryCode = Country.getCode(graphDTO.getForex());
        ArrayList<String> list = daos[countryCode].selectBkpr(graphDTO.getStartDate(),graphDTO.getEndDate());
        result.setList(list);
        return result;
    }

    public ResGraphDTO bkprYearService(ReqGraphDTO graphDTO) {
        ArrayList<String> list = new ArrayList<>();
        ResGraphDTO result = new ResGraphDTO();
        int countryCode = Country.getCode(graphDTO.getForex());
        ArrayList<String> data = daos[countryCode].selectBkpr(graphDTO.getStartDate(),graphDTO.getEndDate());
        int divNum = data.size() / 12;

        String tmp;
        double sum = 0;
        for(int i=0;i<11;i++){
            sum = 0;
            for(int j=divNum*i ; j< (divNum*i + divNum) ; j++){
                tmp = (data.get(j)).replace(",","");
                sum += Double.parseDouble(tmp);
            }
            list.add(Double.toString(sum/divNum));
        }

        //마지막
        sum = 0;
        int count=0;
        for(int i=divNum*11 ; i<data.size() ; i++){
            tmp = (data.get(i)).replace(",","");
            sum += Double.parseDouble(tmp);
            count++;
        }
        list.add(Double.toString(sum/count));

        result.setList(list);
        return result;
    }


}
