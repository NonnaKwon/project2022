import org.apache.ibatis.session.SqlSessionFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Controller {
    private SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
    private Socket conn;
    private DataInputStream dis;
    private DataOutputStream dos;


    public Controller(Socket conn){
        this.conn = conn;
    }

    public void run(){
        try {
            dis = new DataInputStream(conn.getInputStream());
            dos = new DataOutputStream((conn.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try{
                if(!conn.isConnected()) break;
                byte[] typeAndCode = dis.readNBytes(2);
                int type = typeAndCode[0];
                int code = typeAndCode[1];
                byte[] dataSize = dis.readNBytes(4);
                int size = Protocol.byteToInt(dataSize);

                Exchange exchange = new Exchange();

                switch (type){
                    case 1 :
                        exchange.run(code ,dis.readNBytes(size));
                        break;
                    case 2 :
                        break;
                    case 3 :
                        break;

                }


            }catch (Exception e){

            }



        }



    }



}