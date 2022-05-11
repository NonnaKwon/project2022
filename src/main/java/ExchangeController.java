import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.dto.ExchangeDTO;
import readAPI.ReadData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExchangeController {

    private DAO[] daos = new DAO[31];
    private ArrayList<DTO> nowData;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ExchangeController(SqlSessionFactory sqlSessionFactory,DataInputStream dis,DataOutputStream dos){
        nowData = ReadData.dayTimeRead();
        for(int i=0;i<31;i++){
            daos[i] = new DAO(sqlSessionFactory,i);
        }
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

        long amount = exchangeDTO.getAmount(); //예를들어 1200원이면
        double result = Integer.parseInt(dto.getBkpr()) / (double)amount; // 1달러 이렇게

        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }

    public void otherToKoreaExchange(byte[] data) throws IOException, ClassNotFoundException {
        ExchangeDTO exchangeDTO = (ExchangeDTO) Protocol.convertBytesToObject(data);
        DTO dto = nowData.get(exchangeDTO.getCountry1());

        long amount = exchangeDTO.getAmount(); //예를들어 1달러면
        long result = Integer.parseInt(dto.getBkpr()) * amount; //1200원 이렇게

        dos.write(Protocol.convertObjectToBytes(1,1,result));
    }



}