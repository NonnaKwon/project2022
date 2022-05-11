import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Protocol {
    private static byte[] paket;


    public static byte[] convertObjectToBytes(byte type, byte code, Object obj) throws IOException {
        byte[] objByteArr;
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            objByteArr = boas.toByteArray();
        }
        paket = new byte[6+objByteArr.length];
        byte[] sizeArr = intToByte(objByteArr.length);
        paket[0] = type;
        paket[1] = code;
        for(int i=0;i<4;i++){
            paket[2+i] = sizeArr[i];
        }
        for(int i=0;i<sizeArr.length;i++){
            paket[6+i] = sizeArr[i];
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