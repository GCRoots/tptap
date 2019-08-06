package dame.imageDame;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.speech.AipSpeech;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SampleImage {
    //设置APPID/AK/SK
    public static final String APP_ID = "16946201";
    public static final String API_KEY = "vXnoafPAGsufYcnNn0GhdyHr";
    public static final String SECRET_KEY = "9PQ2fXXDr6GfQq2vRbDsyVWREq131Pk4";

    public static void main(String[] args) {
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        String path = "/home/abc/Desktop/apple.jpg";

        SampleImage sample=new SampleImage();
        sample.image(client,path);


    }

    public void image(AipImageClassify client,String images) {



        // 果蔬
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "1");

        JSONObject garderStuff = client.ingredient(images, options);
        System.out.println(garderStuff.toString(2));
        System.out.println();


        JSONArray result=garderStuff.getJSONArray("result");
        System.out.println(result.toString(2));
        System.out.println();


        String name=result.getJSONObject(0).getString("name");
        System.out.println(name);
        System.out.println();

        if (name!="非果蔬食材"){






        }else {


            // 百科
            // 传入可选参数调用接口
            HashMap<String, String> elseOptions = new HashMap<String, String>();
            elseOptions.put("baike_num", "5");


            JSONObject res = client.advancedGeneral(images, elseOptions);
            System.out.println(res.toString(2));

            List<String> allknow=new ArrayList<String>();

            JSONArray result1=res.getJSONArray("");




        }


    }



}
