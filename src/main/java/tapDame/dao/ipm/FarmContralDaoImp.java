package tapDame.dao.ipm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tapDame.dao.FarmContralDao;
import tapDame.pojo.FarmControl;

import java.io.IOException;
import java.io.InputStream;

public class FarmContralDaoImp implements FarmContralDao {

    private SqlSession session;

    @Override
    public FarmControl findByHT(String humidity,String tmp) {

        FarmControl farmControl=new FarmControl();

        System.out.println(humidity+"\t");
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();

            FarmContralDao farmContralDao=session.getMapper(FarmContralDao.class);
            farmControl=farmContralDao.findByHT(humidity,tmp);

            session.commit();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return farmControl;

    }

    @Override
    public void addFarmContral(FarmControl farmControl) {
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();
            session.insert("addFarmControl",farmControl);

            session.commit();

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateFarmContral(FarmControl farmControl) {
        try {
            InputStream inputStream= Resources.getResourceAsStream("mybatis/mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session=sqlSessionFactory.openSession();
            session.insert("updateFarmContral",farmControl);

            session.commit();

        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
