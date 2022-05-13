package service;

import controller.Protocol;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.ExchangeDTO;

import java.io.IOException;

public class ExchangeService {
    private SqlSessionFactory sqlSessionFactory;
    private DAO[] daos = new DAO[31];

    public ExchangeService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
    }

    public double koreaToOtherService(ExchangeDTO exchangeDTO, DTO nowDTO) {
        long amount = exchangeDTO.getAmount(); //예를들어 1200원이면
        String bkpr = nowDTO.getBkpr().replace(",","");
        double result = (double)amount / Integer.parseInt(bkpr); // 1달러 이렇게

        return result;
    }



    public double otherToKoreaService(ExchangeDTO exchangeDTO, DTO nowDTO)  {
        long amount = exchangeDTO.getAmount(); //예를들어 1달러면
        String bkpr = nowDTO.getBkpr().replace(",","");
        double result = Integer.parseInt(bkpr) * amount; //1200원 이렇게

        return result;

    }

}
