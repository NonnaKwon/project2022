package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.ReqAlertDTO;
import persistence.dto.ResAlertDTO;
import service.AlertService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AlertController {
    private AlertService alertService;

    public AlertController(){
        alertService = new AlertService(MyBatisConnectionFactory.getSqlSessionFactory());
    }


    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case Protocol.CODE_REQ_ALERT_:
                alertAmount(data);
                break;
            case 2:

                break;
            default:

        }
    }

    public void alertAmount(byte[] data) throws IOException, ClassNotFoundException {
        ReqAlertDTO requestDTO = (ReqAlertDTO) Protocol.convertBytesToObject(data);
        ResAlertDTO result = alertService.alertAmountService(requestDTO);
        Protocol.responseToClient(Protocol.TYPE_RES_ALERT,Protocol.CODE_RES_ALERT_,result);

    }
}
