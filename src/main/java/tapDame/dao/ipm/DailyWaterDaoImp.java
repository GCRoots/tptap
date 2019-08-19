package tapDame.dao.ipm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tapDame.dao.DailyWaterDao;
import tapDame.pojo.DailyWater;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author shipengfei
 * @data 19-8-17
 */
public class DailyWaterDaoImp implements DailyWaterDao {
    private SqlSession session;

    @Override
    public DailyWater findByIdDate(String idDate) {
        DailyWater dailyWater=new DailyWater();

        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();

            DailyWaterDao dailyWaterDao=session.getMapper(DailyWaterDao.class);
            dailyWater=dailyWaterDao.findByIdDate(idDate);

            session.commit();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return dailyWater;
    }

    @Override
    public void insertDailyWater(DailyWater dailyWater) {
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();
            session.insert("insertDailyWater",dailyWater);

            session.commit();

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateDailyWater(DailyWater dailyWater) {
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();
            session.insert("updateDailyWater",dailyWater);

            session.commit();

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
