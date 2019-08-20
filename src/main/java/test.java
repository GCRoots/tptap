import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import tapDame.dao.*;
import tapDame.dao.ipm.*;
import tapDame.pojo.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

//        Date date = new Date();
//        System.out.println(date);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
//        System.out.println(dateFormat.format(date));
//        SimpleDateFormat dateFormat_min = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//设置当前时间的格式，为年-月-日 时-分-秒
//        System.out.println(dateFormat_min.format(date));

        String json="{\"HeWeather6\":[{\"basic\":{\"cid\":\"CN101030100\",\"location\":\"天津\",\"parent_city\":\"天津\",\"admin_area\":\"天津\",\"cnty\":\"中国\",\"lat\":\"39.12559509\",\"lon\":\"117.19018555\",\"tz\":\"+8.00\"},\"update\":{\"loc\":\"2019-08-20 09:42\",\"utc\":\"2019-08-20 01:42\"},\"status\":\"ok\",\"now\":{\"cloud\":\"97\",\"cond_code\":\"101\",\"cond_txt\":\"多云\",\"fl\":\"27\",\"hum\":\"68\",\"pcpn\":\"0.0\",\"pres\":\"1007\",\"tmp\":\"25\",\"vis\":\"16\",\"wind_deg\":\"206\",\"wind_dir\":\"西南风\",\"wind_sc\":\"2\",\"wind_spd\":\"6\"}}]}\n";

        org.json.JSONObject now=new JSONObject(json).getJSONArray("HeWeather6")
                .getJSONObject(0).getJSONObject("now");

        org.json.JSONObject basic=new JSONObject(json).getJSONArray("HeWeather6")
                .getJSONObject(0).getJSONObject("basic");

        String location=basic.getString("location");
        String cond_txt=now.getString("cond_txt");
        String tmp=now.getString("tmp");
        String hum=now.getString("hum");

        Map<String,String> map=new HashMap();
        map.put("location",location);
        map.put("cond_txt",cond_txt);
        map.put("tmp",tmp);
        map.put("hum",hum);

        JSONObject jsonObject=new JSONObject(map);

        System.out.println(now.toString(2));

        System.out.println(basic.toString(2));

        System.out.println(jsonObject.toString(2));



    }

}
