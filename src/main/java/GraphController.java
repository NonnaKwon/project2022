import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.GraphRequestDTO;
import readAPI.ReadData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GraphController {
    private ArrayList<DTO> nowData;
    private DataInputStream dis;
    private DataOutputStream dos;
    private DAO[] daos = new DAO[31];

    GraphController(SqlSessionFactory sqlSessionFactory, DataInputStream dis, DataOutputStream dos){
        nowData = ReadData.dayTimeRead();
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
        this.dis = dis;
        this.dos = dos;

    }

    public void run(int code,byte[] data) throws IOException, ClassNotFoundException {
        switch (code){
            case 1:
                graph(data);
                break;
            case 2:
                break;
            default:
        }
    }

    public void graph(byte[] data) throws IOException, ClassNotFoundException {
        GraphRequestDTO graphRequestDTO = (GraphRequestDTO) Protocol.convertBytesToObject(data);
        //int countryCode = Country.getCode(graphRequestDTO.getCountry());
        ArrayList<String> list = daos[graphRequestDTO.getCountry()].selectBkpr(graphRequestDTO.getStartDate(),graphRequestDTO.getEndDate());
        dos.write(Protocol.convertObjectToBytes(2,1,list));
    }

}
