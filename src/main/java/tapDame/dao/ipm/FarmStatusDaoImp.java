package tapDame.dao.ipm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tapDame.dao.FarmStatusDao;
import tapDame.pojo.FarmStatus;

import java.io.IOException;
import java.io.InputStream;

public class FarmStatusDaoImp implements FarmStatusDao {

    private SqlSession session;

    @Override
    public FarmStatus findByFId(String id) {
        FarmStatus farmStatus=new FarmStatus();

        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();

            FarmStatusDao homeStatusDao=session.getMapper(FarmStatusDao.class);
            farmStatus=homeStatusDao.findByFId(id);

            session.commit();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return farmStatus;
    }

    @Override
    public void insertFarmStatus(FarmStatus farmStatus) {
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();
            session.insert("addFarmStatus",farmStatus);

            session.commit();

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateFarmStatus(FarmStatus farmStatus) {
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();
            session.insert("updateFarmStatus",farmStatus);

            session.commit();

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delFarmStatus(String id) {
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();
            session.insert("delFarmStatus",id);

            session.commit();

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
