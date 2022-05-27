package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AnalysisDTO;
import persistence.dto.DTO;
import persistence.dto.ResSearchDTO;
import persistence.mapper.Mapper;
import persistence.mapper.MapperList;
import persistence.mapper.analysis.AnalysisMapper;

import java.util.ArrayList;

public class AnalysisDAO {
    SqlSessionFactory sqlSessionFactory;
    private int kind;

    // kind (0: highs/lows 1:macd 2:rsi)
    public AnalysisDAO(SqlSessionFactory sqlSessionFctory, int kind){
        this.sqlSessionFactory = sqlSessionFctory;
        this.kind = kind;
    }

    public ArrayList<AnalysisDTO> selectAll(){
        ArrayList<AnalysisDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        AnalysisMapper mapper = (AnalysisMapper) session.getMapper(MapperList.getAnalysisMapperList[kind]);
        try{
            list = mapper.selectAll();
            session.commit();
        }catch(Exception e){
            e.getStackTrace();
            session.rollback();
        }finally {
            session.close();
        }
        return list;
    }

    public double selectFigure(String forex){
        double result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        AnalysisMapper mapper = (AnalysisMapper) session.getMapper(MapperList.getAnalysisMapperList[kind]);
        try{
            result = mapper.selectFigure(forex);
            session.commit();
        }catch(Exception e){
            e.getStackTrace();
            session.rollback();
        }finally {
            session.close();
        }
        return result;
    }

    public boolean insert(AnalysisDTO dto){
        boolean b = false;
        SqlSession session = sqlSessionFactory.openSession();
        AnalysisMapper mapper = (AnalysisMapper) session.getMapper(MapperList.getAnalysisMapperList[kind]);
        try{
            b = mapper.insert(dto);
            session.commit();
        }catch (Exception e) {
            e.getStackTrace();
            session.rollback();
        }finally {
            session.close();
        }
        return b;
    }


}
