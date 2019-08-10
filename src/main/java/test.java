import com.alibaba.fastjson.JSON;
import tapDame.dao.FarmContralDao;
import tapDame.dao.FarmStatusDao;
import tapDame.dao.UserDao;
import tapDame.dao.ipm.FarmContralDaoImp;
import tapDame.dao.ipm.FarmStatusDaoImp;
import tapDame.dao.ipm.UserDaoImp;
import tapDame.pojo.Data;
import tapDame.pojo.FarmControl;
import tapDame.pojo.FarmStatus;
import tapDame.pojo.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {



    public static void main(String[] args) throws IOException {

        UserDao userServer=new UserDaoImp();
        FarmContralDao farmContralDao=new FarmContralDaoImp();
        FarmStatusDao farmStatusDao=new FarmStatusDaoImp();


        String s="{\"humidity\":\"39\",\"tapId\":\"1\",\"camera\":\"f\"," +
                "\"sensor1\":\"t\",\"sensor2\":\"t\",\"sensor3\":\"f\",\"sensor4\":\"f\",\"sensor5\":\"f\"}";
        Data data= JSON.parseObject(s,Data.class);



        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String lastTime=df.format(new Date());

        FarmStatus farmStatus=new FarmStatus();
        farmStatus.setTapId(data.getTapId());
        farmStatus.setCamera(data.getCamera());
        farmStatus.setSensor1(data.getSensor1());
        farmStatus.setSensor2(data.getSensor2());
        farmStatus.setSensor3(data.getSensor3());
        farmStatus.setSensor4(data.getSensor4());
        farmStatus.setSensor5(data.getSensor5());
        farmStatus.setHumidity(data.getHumidity());
        farmStatus.setLastTime(lastTime);
//
        FarmStatus farmStatus1=farmStatusDao.findByFId(data.getTapId());
        System.out.println(farmStatus1.toString());
//        farmStatus.setLastTime(farmStatus1.getLastTime());

//        farmStatusDao.updateFarmStatus(farmStatus);


    }

}
