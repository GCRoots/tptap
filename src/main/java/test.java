import tapDame.dao.FarmContralDao;
import tapDame.dao.UserDao;
import tapDame.dao.ipm.FarmContralDaoImp;
import tapDame.dao.ipm.UserDaoImp;
import tapDame.pojo.FarmControl;
import tapDame.pojo.User;

import java.io.IOException;

public class test {



    public static void main(String[] args) throws IOException {

        UserDao userServer=new UserDaoImp();

        FarmContralDao farmContralDao=new FarmContralDaoImp();

        FarmControl farmControl=farmContralDao.findByHT("70","27");

//        FarmControl farmControl1=new FarmControl();
//        farmControl1.setHumidity("70");
//        farmControl1.setNeed("40");
//        farmControl1.setTmp("29");
//
//        farmContralDao.addFarmContral(farmControl1);

        System.out.println(farmControl.toString());

    }

}
