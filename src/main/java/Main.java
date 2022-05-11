import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.AmericaDAO;
import persistence.dto.DTO;
import readAPI.ReadData;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        AmericaDAO americaDAO = new AmericaDAO(sqlSessionFactory);
        ArrayList<String> result = americaDAO.selectBkpr("20170104","20170201");

//        ReadData readData = new ReadData(sqlSessionFactory);
//        readData.allDataRead("2019");

    }

}