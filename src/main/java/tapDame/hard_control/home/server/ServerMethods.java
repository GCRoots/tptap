package tapDame.hard_control.home.server;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;
import tapDame.pojo.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerMethods {

    //设置APPID/AK/SK
    public static final String APP_ID = "16946201";
    public static final String API_KEY = "vXnoafPAGsufYcnNn0GhdyHr";
    public static final String SECRET_KEY = "9PQ2fXXDr6GfQq2vRbDsyVWREq131Pk4";





    public static void main(String[] args) throws IOException {
        ServerMethods methods=new ServerMethods();
        methods.select();



    }

    public Data update(String jsonString){

        System.out.println("updata");
        System.out.println(jsonString);

        return null;

    }

    public Data select() throws IOException {
        Data reData=new Data();

//        String image=data.getImage();
//        String sound=data.getSound();


        imageReco("/home/abc/Desktop/apple2.jpg");
        soundReco("/home/abc/Desktop/a.m4a");

        return reData;
    }

    public Data judge(Data data){
        Data reData=new Data();

        String image=data.getImage();
        String sound=data.getSound();


        return reData;
    }

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
        System.out.println(garderStuff.toString(2));
        System.out.println();

        JSONArray result=garderStuff.getJSONArray("result");
        System.out.println(result.toString(2));
        System.out.println();

        String name=result.getJSONObject(0).getString("name");
        System.out.println(name);
        System.out.println();

        if (!name.equals("非果蔬食材")){
            //返回name以供调用数据库
            return name;

        }else {
            // 百科
            // 传入可选参数调用接口
            HashMap<String, String> elseOptions = new HashMap<String, String>();
            elseOptions.put("baike_num", "6");

            JSONObject res = aipImage.advancedGeneral(path, elseOptions);
            System.out.println(res.toString(2));

            List<String> allknow=new ArrayList<String>();

            JSONArray result1=res.getJSONArray("result");
            System.out.println(result1.toString(2));

            for (int i=0;i<result1.length();i++){
                JSONObject jsonObject=result1.getJSONObject(i);
                allknow.add(jsonObject.getString("keyword"));
            }

            System.out.println(allknow.toString());

            if (allknow.contains("手")||allknow.contains("手指")){
                return "手";
            }
        }
        return null;
    }

    private String soundReco(String path) throws IOException {

        // 初始化一个AipSpeech
        AipSpeech aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        aipSpeech.setConnectionTimeoutInMillis(2000);
        aipSpeech.setSocketTimeoutInMillis(60000);


//        JSONObject asrRes = aipSpeech.asr(path, "pcm", 16000, null);
        JSONObject asrRes = aipSpeech.asr(path, "m4a", 16000, null);
        System.out.println(asrRes);

        // 对语音二进制数据进行识别
        byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        JSONObject asrRes2 = aipSpeech.asr(data, "m4a", 16000, null);
        System.out.println(asrRes2);

        return null;
    }



}
