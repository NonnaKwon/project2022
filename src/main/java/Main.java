import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import persistence.dao.YuanDAO;
import persistence.dto.KoreaDTO;
import persistence.dto.YuanDTO;
import readAPI.ReadData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        ReadData readData = new ReadData(sqlSessionFactory);
        //readData.allDataRead("2018");

    }

}