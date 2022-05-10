package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AmericaDTO;
import persistence.dto.JapanDTO;
import persistence.mapper.AmericaMapper;
import persistence.mapper.JapanMapper;

import java.util.List;

public class AmericaDAO {
    SqlSessionFactory sqlSessionFactory;

    public AmericaDAO(SqlSessionFactory sqlSessionFctory){
        this.sqlSessionFactory = sqlSessionFctory;
    }

    public List<AmericaDTO> selectAll(){
        List<AmericaDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        AmericaMapper mapper = session.getMapper(AmericaMapper.class);
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

    public boolean insert(AmericaDTO americaDTO){
        boolean b = false;
        SqlSession session = sqlSessionFactory.openSession();
        AmericaMapper mapper = session.getMapper(AmericaMapper.class);
        try{
            b = mapper.insert(americaDTO);
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
