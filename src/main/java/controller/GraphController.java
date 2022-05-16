package controller;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.GraphDTO;
import readAPI.ReadData;
import service.Country;
import service.GraphService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class GraphController {
    private ArrayList<DTO> nowData;
    private DataInputStream dis;
    private DataOutputStream dos;
    private DAO[] daos = new DAO[31];
    private GraphService graphService;

    GraphController(SqlSessionFactory sqlSessionFactory, DataInputStream dis, DataOutputStream dos){
        nowData = ReadData.dayTimeRead(sqlSessionFactory);
        this.dis = dis;
        this.dos = dos;
        graphService = new GraphService(sqlSessionFactory);
    }

    public void run(int code,byte[] data) throws IOException, ClassNotFoundException {
        switch (code){
            case 1:
                bkprGraph(data);
                break;
            case 2:
                break;
            default:
        }
    }

    public void bkprGraph(byte[] data) throws IOException, ClassNotFoundException {
        GraphDTO graphRequestDTO = (GraphDTO) Protocol.convertBytesToObject(data);
        ArrayList<String> list = graphService.bkprGraphService(graphRequestDTO);
        dos.write(Protocol.convertObjectToBytes(2,1,list));
    }


}
