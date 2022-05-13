package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.ExchangeDTO;
import readAPI.ReadData;
import service.ExchangeService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExchangeController {
    private SqlSessionFactory sqlSessionFactory;
    private ArrayList<DTO> nowData;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ExchangeService exchangeService;

    public ExchangeController(SqlSessionFactory sqlSessionFactory,DataInputStream dis,DataOutputStream dos){
        nowData = ReadData.dayTimeRead(sqlSessionFactory);
        exchangeService = new ExchangeService(sqlSessionFactory);
        this.dis = dis;
        this.dos = dos;
    }

    //1~n 나라 코드 정하기
    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case 1: // 한국돈 -> 외화
                koreaToOtherExchange(data);
                break;
            case 2: // 외화 -> 한국돈
                otherToKoreaExchange(data);
                break;
            default:

        }

    }


    public void koreaToOtherExchange(byte[] data) throws IOException, ClassNotFoundException {
        ExchangeDTO exchangeDTO = (ExchangeDTO) Protocol.convertBytesToObject(data);
        DTO dto = nowData.get(exchangeDTO.getCountry1());

        double result = exchangeService.koreaToOtherService(exchangeDTO,dto);

        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }



    public void otherToKoreaExchange(byte[] data) throws IOException, ClassNotFoundException {
        ExchangeDTO exchangeDTO = (ExchangeDTO) Protocol.convertBytesToObject(data);
        DTO dto = nowData.get(exchangeDTO.getCountry1());

        double result = exchangeService.otherToKoreaService(exchangeDTO,dto);

        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }



}