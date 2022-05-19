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

    public Controller(Socket conn) {
        try {
            this.conn = conn;
            sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
            Protocol.setStream(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        while(true){
            try{
                if(!conn.isConnected()) break;
                Protocol.receiveData();
            } catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }

}