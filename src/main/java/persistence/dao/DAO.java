package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.DTO;
import persistence.mapper.Mapper;
import persistence.mapper.MapperList;

import java.util.ArrayList;

public class DAO {
    SqlSessionFactory sqlSessionFactory;
    private int country;

    public DAO(SqlSessionFactory sqlSessionFctory,int country){
        this.sqlSessionFactory = sqlSessionFctory;
        this.country = country;
    }

    public ArrayList<DTO> selectAll(){
        ArrayList<DTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        Mapper mapper = (Mapper) session.getMapper(MapperList.mapperList[country]);
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

    public String selectOneBkpr(String date){
        String result = null;
        SqlSession session = sqlSessionFactory.openSession();
        Mapper mapper = (Mapper) session.getMapper(MapperList.mapperList[country]);
        try{
            result = mapper.selectOneBkpr(date);
            session.commit();
        }catch(Exception e){
            e.getStackTrace();
            session.rollback();
        }finally {
            session.close();
        }
        return result;
    }


    public ArrayList<String> selectBkpr(String startDate, String endDate){
        ArrayList<String> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        Mapper mapper = (Mapper) session.getMapper(MapperList.mapperList[country]);
        try{
            list = mapper.selectBkpr(startDate,endDate);
            session.commit();
        }catch(Exception e){
            e.getStackTrace();
            System.out.println(e.toString());
            session.rollback();
        }finally {
            session.close();
        }
        return list;
    }

    public boolean insert(DTO dto){
        boolean b = false;
        SqlSession session = sqlSessionFactory.openSession();
        Mapper mapper = (Mapper) session.getMapper(MapperList.mapperList[country]);
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