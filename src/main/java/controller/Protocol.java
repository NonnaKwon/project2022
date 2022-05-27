package controller;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Protocol {
    private static DataOutputStream dos;
    private static DataInputStream dis;
    private static byte[] paket;

    public static void setStream(Socket conn) throws IOException {
        dos = new DataOutputStream(conn.getOutputStream());
        dis = new DataInputStream(conn.getInputStream());
    }

    //type
    public static final int TYPE_REQ_CALCULATE = 1;
    public static final int TYPE_REQ_GRAPH = 2;
    public static final int TYPE_REQ_ALERT = 3;
    public static final int TYPE_REQ_SEARCH = 4;

    public static final int TYPE_RES_CALCULATE = 11;
    public static final int TYPE_RES_GRAPH = 22;
    public static final int TYPE_RES_ALERT = 33;
    public static final int TYPE_RES_SEARCH = 44;

    //code
    //type -> 1
    public static final int CODE_REQ_CALCUALTE = 1;
    //type -> 2
    public static final int CODE_REQ_GRAPH_MONTH = 1;
    public static final int CODE_REQ_GRAPH_YEAR = 2;
    public static final int CODE_REQ_GRAPH_3MONTH = 3;
    //type -> 3
    public static final int CODE_REQ_ALERT_ = 0;
    //type -> 4
    public static final int CODE_REQ_SEARCH = 1;



    //type -> 11
    public static final int CODE_RES_CALCUALTE = 1;
    //type -> 22
    public static final int CODE_RES_GRAPH_MONTH = 1;
    public static final int CODE_RES_GRAPH_YEAR = 2;
    public static final int CODE_RES_GRAPH_3MONTH = 3;
    //type -> 33
    public static final int CODE_RES_ALERT_ = 0;
    //type -> 44
    public static final int CODE_RES_SEARCH = 1;


    public static void responseToClient(int type, int code, Object obj) throws IOException {
        paket = convertObjectToBytes(type,code,obj);
        dos.write(paket);
    }

    public static void receiveData() throws IOException, ClassNotFoundException {

        byte[] typeAndCode = new byte[0];
        typeAndCode = dis.readNBytes(2);
        int type = typeAndCode[0];
        int code = typeAndCode[1];
        byte[] byteSize = dis.readNBytes(4);
        int size = Protocol.byteToInt(byteSize);
        System.out.println("size : " + size);

        byte[] data = dis.readNBytes(size);

        CalculateController calculateController = new CalculateController();
        GraphController graphController = new GraphController();
        AlertController alertController = new AlertController();
        SearchController searchController = new SearchController();
        switch (type){
            case TYPE_REQ_CALCULATE :
                calculateController.run(code,data);
                break;
            case TYPE_REQ_GRAPH:
                graphController.run(code,data);
                break;
            case TYPE_REQ_ALERT:
                alertController.run(code,data);
                break;
            case TYPE_REQ_SEARCH:
                searchController.run(code,data);
                break;
        }



    }



    public static byte[] convertObjectToBytes(int type, int code, Object obj) throws IOException {
        byte[] objByteArr;
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            objByteArr = boas.toByteArray(); //객체가 들어있음
        }
        paket = new byte[6+objByteArr.length];
        byte[] sizeArr = intToByte(objByteArr.length);
        paket[0] = (byte)type;
        paket[1] = (byte)code;
        for(int i=0;i<4;i++){
            paket[2+i] = sizeArr[i];
        }
        for(int i=0;i<objByteArr.length;i++){
            paket[6+i] = objByteArr[i];
        }
        return paket;
    }

    public static Object convertBytesToObject(byte[] bytes)
            throws IOException, ClassNotFoundException {
        InputStream is = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        }
    }




    public static byte[] intToByte(int i){
        ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE/8);
        buff.putInt(i);
        buff.order(ByteOrder.BIG_ENDIAN);
        return buff.array();

    }

    public static int byteToInt(byte[] bytes){
        byte[] newBytes = new byte[4];
        final int size = Integer.SIZE/8;
        ByteBuffer buff = ByteBuffer.allocate(size);
        for(int i =0;i<size;i++) {
            if (i +bytes.length<size) {
                newBytes[i] = (byte) 0x00;
            }
            else {
                newBytes[i] = bytes[i+bytes.length-size];
            }
        }
        buff = ByteBuffer.wrap(newBytes);
        buff.order(ByteOrder.BIG_ENDIAN);
        return buff.getInt();
    }

}