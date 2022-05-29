package controller;

import persistence.dto.ReqCalculationDTO;
import persistence.dto.ResCalculationDTO;
import service.CalculateService;

import java.io.IOException;

public class CalculateController {
    private CalculateService calculateService;

    public CalculateController(){
        calculateService = new CalculateService(MyBatisConnectionFactory.getSqlSessionFactory());

    }

    //1~n 나라 코드 정하기
    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case Protocol.CODE_REQ_CALCUALTE: // 외화 -> 한국돈
                otherToKoreaExchange(data);
                break;
            default:
        }
    }

    public void otherToKoreaExchange(byte[] data){
        ReqCalculationDTO requestDTO = null;
        try {
            requestDTO = (ReqCalculationDTO) Protocol.convertBytesToObject(data);
            ResCalculationDTO result = calculateService.otherToKoreaService(requestDTO);
            Protocol.responseToClient(Protocol.TYPE_RES_CALCULATE,Protocol.CODE_RES_CALCUALTE,result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }





}