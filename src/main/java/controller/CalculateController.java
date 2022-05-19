package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.ReqCalculationDTO;
import persistence.dto.ResCalculationDTO;
import service.ExchangeService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CalculateController {
    private ExchangeService exchangeService;

    public CalculateController(){
        exchangeService = new ExchangeService(MyBatisConnectionFactory.getSqlSessionFactory());

    }

    //1~n 나라 코드 정하기
    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case Protocol.CODE_REQ_CALCUALTE: // 외화 -> 한국돈
                koreaToOtherExchange(data);

                break;
            case 2: // 한국돈 -> 외화
                otherToKoreaExchange(data);
                break;
            default:

        }

    }

    public void otherToKoreaExchange(byte[] data){
        ReqCalculationDTO requestDTO = null;
        try {
            requestDTO = (ReqCalculationDTO) Protocol.convertBytesToObject(data);
            ResCalculationDTO result = exchangeService.otherToKoreaService(requestDTO);
            Protocol.responseToClient(1,1,result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void koreaToOtherExchange(byte[] data)  {
        try{
            ReqCalculationDTO requestDTO = (ReqCalculationDTO) Protocol.convertBytesToObject(data);
            ResCalculationDTO result = exchangeService.koreaToOtherService(requestDTO);
            Protocol.responseToClient(Protocol.TYPE_RES_CALCULATE,Protocol.CODE_RES_CALCUALTE,result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }







}