package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.ReqCalculationDTO;
import persistence.dto.ResCalculationDTO;
import persistence.dto.DTO;
import readAPI.ReadData;

import java.util.ArrayList;

public class ExchangeService {
    private SqlSessionFactory sqlSessionFactory;
    private DAO[] daos = new DAO[31];
    private ArrayList<DTO> nowData;

    public ExchangeService(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
        nowData = ReadData.dayTimeRead(sqlSessionFactory);
    }

    public ResCalculationDTO otherToKoreaService(ReqCalculationDTO requestDTO)  {
        int countryCode = Country.getCode(requestDTO.getCurrencytmp());
        DTO dto = nowData.get(countryCode);
        String option = requestDTO.getExchangeOption();

        long amount = requestDTO.getCurrentExchange();
        double calNum = 0;
        if(option.equals("TTS")){
            calNum = Double.parseDouble(dto.getTts());
        }else if(option.equals("TTB")){
            calNum = Double.parseDouble(dto.getTtb());
        }else if(option.equals("DEA")){
            calNum = Double.parseDouble(dto.getDeal());
        }else if(option.equals("BKP")){
            calNum = Double.parseDouble(dto.getBkpr());
        }else{
            System.out.println("옵션 오류");
            return null;
        }

        ResCalculationDTO result = new ResCalculationDTO();
        result.setResultExchange(amount * calNum);

        return result;
    }

    public ResCalculationDTO koreaToOtherService(ReqCalculationDTO requestDTO) {
        int countryCode = Country.getCode(requestDTO.getCurrencytmp());
        DTO dto = nowData.get(countryCode);
        String option = requestDTO.getExchangeOption();

        long amount = requestDTO.getCurrentExchange();
        double calNum = 0;
        if(option.equals("TTS")){
            calNum = Double.parseDouble(dto.getTts());
        }else if(option.equals("TTB")){
            calNum = Double.parseDouble(dto.getTtb());
        }else if(option.equals("DEA")){
            calNum = Double.parseDouble(dto.getDeal());
        }else if(option.equals("BKP")){
            calNum = Double.parseDouble(dto.getBkpr());
        }else{
            System.out.println("옵션 오류");
            return null;
        }

        ResCalculationDTO result = new ResCalculationDTO();
        result.setResultExchange(amount / calNum);

        return result;
    }




}
