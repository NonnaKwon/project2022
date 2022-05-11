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

                ExchangeController exchange = new ExchangeController(sqlSessionFactory,dis,dos);
                GraphController grap = new GraphController(sqlSessionFactory,dis,dos);

                byte[] data = dis.readNBytes(size);

                switch (type){
                    case 1 :
                        System.out.println("여까지"+data);
                        exchange.run(code ,data);
                        break;
                    case 2 :
                        grap.run(code,dis.readNBytes(size));
                        break;
                    case 3 :
                        break;
                }

            }catch (Exception e){

            }
        }
    }

}