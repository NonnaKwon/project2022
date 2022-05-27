import controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AlertMain {
    public static void main(String[] args) {
        try {
            ServerSocket s_Alertsocket = new ServerSocket(9000);

            BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,1, TimeUnit.HOURS,blockingQueue);
            Socket conn;
            System.out.println("알람서버 시작");

            while(true){
                conn = s_Alertsocket.accept();
                threadPoolExecutor.execute(new AlertMain.AlertTask(conn) {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static class AlertTask implements Runnable{
        Socket conn;
        public AlertTask(Socket conn){
            this.conn = conn;
        }
        @Override
        public void run() {
            Controller controller = new Controller(conn);
            controller.run();
        }
    }

}
