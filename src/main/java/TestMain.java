import controller.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.AnalysisDAO;
import persistence.dto.AnalysisDTO;
import persistence.dto.ReqTableDTO;
import persistence.dto.ResTableDTO;
import persistence.dto.TableData;
import service.AnalysisService;

import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        testAna();
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
}
