import controller.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.AnalysisDAO;
import persistence.dto.*;
import readAPI.HighsLows;
import readAPI.MACD;
import readAPI.RSI;
import readAPI.ReadData;
import service.AnalysisService;

import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        ArrayList<DTO> list = ReadData.dayTimeRead(sqlSessionFactory);
        for(int i=0;i<list.size();i++){
            System.out.println((list.get(i)).toString());
        }

    }

    public static void now(){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        ArrayList<DTO> list = ReadData.dayTimeRead(sqlSessionFactory);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).toString());
        }
    }
    public static void rsi(){
        RSI a = new RSI(7);
        a.doRsi();
    }

    public static void macd(){
        MACD macd = new MACD();
        macd.doMacd();
    }

    public static void highsLows(){
        HighsLows highsLows = new HighsLows();
        highsLows.doHighsLows();
    }

    public static void testInsert(){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        AnalysisDTO dto = new AnalysisDTO();
        dto.setForex("BHD");
        dto.setFigure(2.05332);
        AnalysisDAO dao = new AnalysisDAO(sqlSessionFactory,1);
        dao.insert(dto);
    }

    public static void testAna(){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        AnalysisService as = new AnalysisService(sqlSessionFactory);
        ReqTableDTO req = new ReqTableDTO();
        req.setForex("AED");

        ResTableDTO res = as.analysisService(req);

        TableData[] td = res.getTableData();
        for(int i=0;i<td.length;i++){
            System.out.println(td[i].toString());
        }
    }


    public static void testSelect(){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        AnalysisDAO dao = new AnalysisDAO(sqlSessionFactory,1);
        double result = dao.selectFigure("usd");
        System.out.println(result);
    }

    public static void testUpdate(){
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        AnalysisDAO dao = new AnalysisDAO(sqlSessionFactory,2);
        AnalysisDTO dto = new AnalysisDTO();
        dto.setFigure(1);
        dto.setForex("USD");
        dao.update(dto);
    }
}
