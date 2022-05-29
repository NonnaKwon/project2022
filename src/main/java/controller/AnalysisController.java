package controller;

import persistence.dto.ReqAlertDTO;
import persistence.dto.ReqTableDTO;
import persistence.dto.ResAlertDTO;
import persistence.dto.ResTableDTO;
import service.AlertService;
import service.AnalysisService;

import java.io.IOException;

public class AnalysisController {
    private AnalysisService analysisService;

    public AnalysisController(){
        analysisService = new AnalysisService(MyBatisConnectionFactory.getSqlSessionFactory());
    }


    public void run(int code,byte[] data) throws IOException, ClassNotFoundException{
        switch (code){
            case Protocol.CODE_REQ_ANALYSIS : // 분석 요청 코드
                analysisControll(data);
                break;
            default:

        }
    }

    public void analysisControll(byte[] data) throws IOException, ClassNotFoundException {
        ReqTableDTO requestDTO = (ReqTableDTO) Protocol.convertBytesToObject(data);
        ResTableDTO result = analysisService.analysisService(requestDTO);
        Protocol.responseToClient(Protocol.TYPE_RES_ANALYSIS,Protocol.CODE_RES_ANALYSIS,result); // 응답 타입, 코드넣기
    }
}
