package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.DTO;
import persistence.mapper.AmericaMapper;
import persistence.mapper.SaudiMapper;

import java.util.List;

public class SaudiDAO implements DAO {
    SqlSessionFactory sqlSessionFactory;

    public SaudiDAO(SqlSessionFactory sqlSessionFctory){
        this.sqlSessionFactory = sqlSessionFctory;
    }

    public List<DTO> selectAll(){
        List<DTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        SaudiMapper mapper = session.getMapper(SaudiMapper.class);
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

    public boolean insert(DTO dto){
        boolean b = false;
        SqlSession session = sqlSessionFactory.openSession();
        SaudiMapper mapper = session.getMapper(SaudiMapper.class);
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
