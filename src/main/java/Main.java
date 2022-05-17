import controller.Controller;
import controller.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.*;

import java.net.Socket;

public class Main {
        public static void main(String[] args) {
            SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
            DAO dao = new DAO(sqlSessionFactory,21);
            ResSearchDTO dto = dao.selectOneDto("20220516");
            System.out.println(dto.toString());
//            try {
//                ServerSocket s_socket = new ServerSocket(8888);
//
//                BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);
//                ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,1, TimeUnit.HOURS,blockingQueue);
//                Socket conn;
//                System.out.println("서버 시작");
//
//                while(true){
//                    conn = s_socket.accept();
//                    threadPoolExecutor.execute(new Task(conn) {
//                    });
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        static class Task implements Runnable{
            Socket conn;

            public Task(Socket conn){
                this.conn = conn;
            }
            @Override
            public void run() {
                System.out.println(conn.getInetAddress()+"에서 연결 됨");
                Controller controller = new Controller(conn);
                controller.run();
            }
        }

}