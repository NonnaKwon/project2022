package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.ReqGraphDTO;
import persistence.dto.ResGraphDTO;
import readAPI.ReadData;
import service.GraphService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class GraphController {
    private ArrayList<DTO> nowData;

    private DAO[] daos = new DAO[31];
    private GraphService graphService;

    GraphController(){
        nowData = ReadData.dayTimeRead(MyBatisConnectionFactory.getSqlSessionFactory());
        graphService = new GraphService(MyBatisConnectionFactory.getSqlSessionFactory());
    }

    public void run(int code,byte[] data) throws IOException, ClassNotFoundException {
        switch (code){
            case Protocol.CODE_REQ_GRAPH_MONTH:
                bkprGraphMonth(data); //한달
                break;
            case Protocol.CODE_REQ_GRAPH_YEAR:
                bkprGraphYear(data); //일년
                break;
            case Protocol.CODE_REQ_GRAPH_3MONTH: //삼개월
                bkprGraphThreeMonth(data);
                    break;
            default:

        }
    }

    public void bkprGraphMonth(byte[] data) throws IOException, ClassNotFoundException {
        ReqGraphDTO reqGraphDTO = (ReqGraphDTO) Protocol.convertBytesToObject(data);
        ResGraphDTO result = graphService.bkprService(reqGraphDTO);
        Protocol.responseToClient(Protocol.TYPE_RES_GRAPH,Protocol.CODE_RES_GRAPH_MONTH,result);
    }

    public void bkprGraphYear(byte[] data) throws IOException, ClassNotFoundException {
        ReqGraphDTO reqGraphDTO = (ReqGraphDTO) Protocol.convertBytesToObject(data);
        ResGraphDTO result = graphService.bkprYearService(reqGraphDTO);
        Protocol.responseToClient(Protocol.TYPE_RES_GRAPH,Protocol.CODE_RES_GRAPH_YEAR,result);
    }

    public void bkprGraphThreeMonth(byte[] data) throws IOException, ClassNotFoundException {
        ReqGraphDTO reqGraphDTO = (ReqGraphDTO) Protocol.convertBytesToObject(data);
        ResGraphDTO result = graphService.bkprService(reqGraphDTO);
        Protocol.responseToClient(Protocol.TYPE_RES_GRAPH,Protocol.CODE_RES_GRAPH_3MONTH,result); //여기도 수정
    }


}
