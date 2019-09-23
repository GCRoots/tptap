package tapDame.hard_control.home.server;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.speech.AipSpeech;
import org.json.JSONArray;
import org.json.JSONObject;
import tapDame.dao.DailyWaterDao;
import tapDame.dao.HomeContralDao;
import tapDame.dao.HomeStatusDao;
import tapDame.dao.ipm.DailyWaterDaoImp;
import tapDame.dao.ipm.HomeContralDaoImp;
import tapDame.dao.ipm.HomeStatusDaoImp;
import tapDame.pojo.DailyWater;
import tapDame.pojo.Data;
import tapDame.pojo.HomeContral;
import tapDame.pojo.HomeStatus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeServerMethods {

    //设置APPID/AK/SK
    public static final String APP_ID = "16946201";
    public static final String API_KEY = "vXnoafPAGsufYcnNn0GhdyHr";
    public static final String SECRET_KEY = "9PQ2fXXDr6GfQq2vRbDsyVWREq131Pk4";

    private HomeContralDao homeContralDao=new HomeContralDaoImp();
    private HomeStatusDao homeStatusDao=new HomeStatusDaoImp();
    private DailyWaterDao dailyWaterDao=new DailyWaterDaoImp();


    public String homeWaterContral(Data data) throws IOException {

        Data reData=new Data();
        HomeContral homeContral;

        String image=data.getImage();
        String sound=data.getSound();

        image=imageReco("/home/abc/Desktop/hands.jpg");
        sound=soundReco("/home/abc/Desktop/a.m4a");

        System.out.println(image);

        if (image.equals(null)&&sound.equals(null)){
            return null;
        }else if (sound.equals("洗手")&&!image.equals("手")){
            return null;
        }else {
            homeContral=homeContralDao.findByType(image);
            HomeStatus homeStatus=homeStatusDao.findByHId(data.getTapId());

            Date date=new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
            String idDate=data.getTapId()+"_"+dateFormat.format(date);
            DailyWater dailyWater=dailyWaterDao.findByIdDate(idDate);

            if (dailyWater==null){
                dailyWater.setIdDate(idDate);
                dailyWaterDao.insertDailyWater(dailyWater);
                dailyWater=dailyWaterDao.findByIdDate(dateFormat.toString());
            }

            String water=homeContral.getWater();
            System.out.println(water);
            reData.setWater(water);


            int today=Integer.parseInt(dailyWater.getTodayUsed())+Integer.parseInt(water);
            dailyWater.setTodayUsed(String.valueOf(today));

            com.alibaba.fastjson.JSONObject jsonObject=JSON.parseObject(dailyWater.getPurpose());
            String type=jsonObject.getString(sound);
            int types=Integer.parseInt(type)+Integer.parseInt(water);
            jsonObject.put(sound,String.valueOf(types));
            dailyWater.setPurpose(JSON.toJSONString(jsonObject));

            dailyWaterDao.updateDailyWater(dailyWater);

            String res= JSON.toJSONString(reData);

            return res;

        }
    }

//    图像识别
    private String imageReco(String path){

        AipImageClassify aipImage = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        aipImage.setConnectionTimeoutInMillis(2000);
        aipImage.setSocketTimeoutInMillis(60000);

        // 果蔬
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "1");

        JSONObject garderStuff = aipImage.ingredient(path, options);

//        System.out.println(garderStuff.toString(2));
//        System.out.println();

        JSONArray result=garderStuff.getJSONArray("result");

//        System.out.println(result.toString(2));
//        System.out.println();

        String name=result.getJSONObject(0).getString("name");

//        System.out.println(name);
//        System.out.println();

        if (!name.equals("非果蔬食材")){
            //返回name以供调用数据库
            return name;

        }else {
            // 百科
            // 传入可选参数调用接口
            HashMap<String, String> elseOptions = new HashMap<String, String>();
            elseOptions.put("baike_num", "6");

            JSONObject res = aipImage.advancedGeneral(path, elseOptions);

//            System.out.println(res.toString(2));

            List<String> allknow=new ArrayList<String>();

            JSONArray result1=res.getJSONArray("result");

//            System.out.println(result1.toString(2));

            for (int i=0;i<result1.length();i++){
                JSONObject jsonObject=result1.getJSONObject(i);
                allknow.add(jsonObject.getString("keyword"));
            }

//            System.out.println(allknow.toString());

            if (allknow.contains("手")||allknow.contains("手指")||allknow.contains("手掌")){
                return "手";
            }
        }
        return null;
    }

//    语音识别
    private String soundReco(String path) throws IOException {

        // 初始化一个AipSpeech
        AipSpeech aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        aipSpeech.setConnectionTimeoutInMillis(2000);
        aipSpeech.setSocketTimeoutInMillis(60000);


//        JSONObject asrRes = aipSpeech.asr(path, "pcm", 16000, null);
        JSONObject asrRes = aipSpeech.asr(path, "m4a", 16000, null);
//        System.out.println(asrRes);

//        // 对语音二进制数据进行识别
//        byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
//        JSONObject asrRes2 = aipSpeech.asr(data, "m4a", 16000, null);
//        System.out.println(asrRes2);

        JSONArray jsonArray=asrRes.getJSONArray("result");

        String ret=jsonArray.getString(0);
//        System.out.println(ret);

        System.out.println(ret.contains("洗手"));

        if (ret.contains("洗手")){
            return "洗手";

        }else if (ret.contains("水果")){
            return "水果";

        } else {
            return null;
        }

    }

}
