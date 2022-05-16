package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AlertRequestDTO;
import persistence.dto.AlertResponseDTO;
import persistence.dto.CalculationRequestDTO;
import persistence.dto.CalculationResponseDTO;
import service.AlertService;
import service.ExchangeService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AlertController {
    private DataInputStream dis;
    private DataOutputStream dos;
    private AlertService alertService;

    public AlertController(SqlSessionFactory sqlSessionFactory, DataInputStream dis, DataOutputStream dos){
        alertService = new AlertService(sqlSessionFactory);
        this.dis = dis;
        this.dos = dos;
    }


    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case 1:
                alertAmount(data);
                break;
            case 2:

                break;
            default:

        }
    }

    public void alertAmount(byte[] data) throws IOException, ClassNotFoundException {
        AlertRequestDTO requestDTO = (AlertRequestDTO) Protocol.convertBytesToObject(data);
        AlertResponseDTO result = alertService.alertAmountService(requestDTO);
        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }


}
