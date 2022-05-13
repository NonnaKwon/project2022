package controller;

import org.apache.ibatis.session.SqlSessionFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Controller {
    private SqlSessionFactory sqlSessionFactory;
    private Socket conn;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Controller(Socket conn){
        this.conn = conn;
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
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
                GraphController graph = new GraphController(sqlSessionFactory,dis,dos);

                byte[] data = dis.readNBytes(size);

                switch (type){
                    case 1 :
                        exchange.run(code ,data);
                        continue;
                    case 2 :
                        graph.run(code,data);
                        continue;
                    case 3 :
                        continue;
                }

            }catch (SocketException e){
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}