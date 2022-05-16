package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.CalculationRequestDTO;
import persistence.dto.CalculationResponseDTO;
import persistence.dto.DTO;
import readAPI.ReadData;
import service.Country;
import service.ExchangeService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExchangeController {
    private DataInputStream dis;
    private DataOutputStream dos;
    private ExchangeService exchangeService;

    public ExchangeController(SqlSessionFactory sqlSessionFactory,DataInputStream dis,DataOutputStream dos){
        exchangeService = new ExchangeService(sqlSessionFactory);
        this.dis = dis;
        this.dos = dos;
    }

    //1~n 나라 코드 정하기
    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case 1: // 외화 -> 한국돈
                otherToKoreaExchange(data);
                break;
            case 2: // 한국돈 -> 외화
                koreaToOtherExchange(data);
                break;
            default:

        }

    }

    public void otherToKoreaExchange(byte[] data) throws IOException, ClassNotFoundException {
        CalculationRequestDTO requestDTO = (CalculationRequestDTO) Protocol.convertBytesToObject(data);
        CalculationResponseDTO result = exchangeService.otherToKoreaService(requestDTO);
        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }

    public void koreaToOtherExchange(byte[] data) throws IOException, ClassNotFoundException {
        CalculationRequestDTO requestDTO = (CalculationRequestDTO) Protocol.convertBytesToObject(data);
        CalculationResponseDTO result = exchangeService.koreaToOtherService(requestDTO);
        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }







}