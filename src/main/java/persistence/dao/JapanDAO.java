package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.JapanDTO;
import persistence.dto.YuanDTO;
import persistence.mapper.JapanMapper;
import persistence.mapper.YuanMapper;

import java.util.List;

public class JapanDAO {
    SqlSessionFactory sqlSessionFactory;

    public JapanDAO(SqlSessionFactory sqlSessionFctory){
        this.sqlSessionFactory = sqlSessionFctory;
    }

    public List<JapanDTO> selectAll(){
        List<JapanDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        JapanMapper mapper = session.getMapper(JapanMapper.class);
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

    public boolean insert(JapanDTO japanDTO){
        boolean b = false;
        SqlSession session = sqlSessionFactory.openSession();
        JapanMapper mapper = session.getMapper(JapanMapper.class);
        try{
            b = mapper.insert(japanDTO);
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
