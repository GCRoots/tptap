import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import tapDame.dao.*;
import tapDame.dao.ipm.*;
import tapDame.pojo.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {



    public static void main(String[] args) throws IOException {

        UserDao userServer = new UserDaoImp();
        FarmContralDao farmContralDao = new FarmContralDaoImp();
        FarmStatusDao farmStatusDao = new FarmStatusDaoImp();
        DailyWaterDao dailyWaterDao = new DailyWaterDaoImp();
        HomeStatusDao homeStatusDao = new HomeStatusDaoImp();

//
//        DailyWater dailyWater=dailyWaterDao.findByDate("");
//        String purpose=dailyWater.getPurpose();
//        System.out.println(purpose);
//
//        JSONObject jsonObject=JSON.parseObject(purpose);
//        System.out.println(jsonObject.toJSONString());
//        System.out.println(jsonObject.getString("洗手"));

        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        System.out.println(dateFormat.format(date));
        SimpleDateFormat dateFormat_min = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//设置当前时间的格式，为年-月-日 时-分-秒
        System.out.println(dateFormat_min.format(date));
    }

}
