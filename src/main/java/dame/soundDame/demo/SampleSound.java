package dame.soundDame.demo;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;

public class SampleSound {
    //设置APPID/AK/SK
    public static final String APP_ID = "16946201";
    public static final String API_KEY = "vXnoafPAGsufYcnNn0GhdyHr";
    public static final String SECRET_KEY = "9PQ2fXXDr6GfQq2vRbDsyVWREq131Pk4";


    public static void main(String[] args) throws IOException {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
//        JSONObject res = client.asr("/home/abc/Desktop/16k.pcm", "pcm", 16000, null);
//        System.out.println(res.toString(2));

        SampleSound sampleSound=new SampleSound();
        sampleSound.asr(client);


    }






    public void asr(AipSpeech client) throws IOException {
        // 对本地语音文件进行识别
        String path = "/home/abc/Desktop/a.m4a";
//        JSONObject asrRes = client.asr(path, "pcm", 16000, null);
//        System.out.println(asrRes);

        // 对语音二进制数据进行识别
        byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        JSONObject asrRes2 = client.asr(data, "m4a", 16000, null);
        System.out.println(asrRes2);

    }
}
