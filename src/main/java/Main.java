import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.DAO;
import persistence.dto.DTO;
import persistence.mapper.MapperList;
import readAPI.ReadData;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();

        ReadData readData = new ReadData(sqlSessionFactory);
        readData.allDataRead("2019");

    }

}