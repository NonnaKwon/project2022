package service;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.*;
import readAPI.ReadData;

import java.util.ArrayList;

public class AlertService {
    private DAO[] daos = new DAO[31];
    private ArrayList<DTO> nowData;

    public AlertService(SqlSessionFactory sqlSessionFactory){
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
        nowData = ReadData.dayTimeRead(sqlSessionFactory);
    }

    public ResAlertDTO alertAmountService(ReqAlertDTO requestDTO){
        ResAlertDTO result = new ResAlertDTO();
        ArrayList<ReqAlertObject> list = requestDTO.getReqList();
        ArrayList<ResAlertObject> resultList = new ArrayList<>();

        for(int i=0;i<list.size();i++){
            ReqAlertObject tmp = list.get(i);
            ResAlertObject resultTmp = new ResAlertObject();

            resultTmp.setCurrencytmp(tmp.getCurrencytmp());
            resultTmp.setAlertAmount(tmp.getAlertAmount());

            //도달했는지 계산
            int country = Country.getCode(tmp.getCurrencytmp());
            DTO data = nowData.get(country); //오늘 데이터 접근
            int bkpr = Integer.parseInt(data.getBkpr()); //오늘 bkpr 데이터
            int amount = Integer.parseInt(tmp.getAlertAmount());
            if(amount <= bkpr){ //희망 가격을 넘으면
                resultTmp.setJudgement(true);
            }else{ //희망가격을 넘지못하면
                resultTmp.setJudgement(false);
            }
            resultList.add(resultTmp);
        }

        result.setResList(resultList);
        return result;
    }
}
