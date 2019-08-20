package tapDame.soft_control.softContraller;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import tapDame.dao.FarmStatusDao;
import tapDame.dao.ipm.FarmStatusDaoImp;
import tapDame.pojo.Data;
import tapDame.pojo.FarmStatus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shipengfei
 * @data 19-8-11
 */
public class Farm {

    private FarmStatusDao farmStatusDao=new FarmStatusDaoImp();

    public String farmPage(Data data){
        Data redata=new Data();

        String tapId=data.getTapId();

        FarmStatus farmStatus=farmStatusDao.findByFId(tapId);

        redata.setSensor1(farmStatus.getSensor1());
        redata.setSensor2(farmStatus.getSensor2());
        redata.setSensor3(farmStatus.getSensor3());
        redata.setSensor4(farmStatus.getSensor4());
        redata.setSensor5(farmStatus.getSensor5());
        redata.setLastTime(farmStatus.getLastTime());
        redata.setHumidity(farmStatus.getHumidity());
        redata.setWeather(weatherSoft());

        String res= JSON.toJSONString(redata);
        return res;

    }

    private String weatherSoft(){
        //参数字符串，如果拼接在请求链接之后，需要对中文进行 URLEncode   字符集 UTF-8
        String param = "key=a9f72b3f74a24439863952cd65a6c082&location=天津";
        StringBuilder sb = new StringBuilder();
        String ret=null;
        InputStream is = null;
        BufferedReader br = null;
        PrintWriter out = null;
        try {
            //接口地址
            String url = "https://free-api.heweather.net/s6/weather/now";
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("accept", "*/*");
            //发送参数
            connection.setDoOutput(true);
            out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();


            //接收结果
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            //缓冲逐行读取
            while ( ( line = br.readLine() ) != null ) {
                sb.append(line);
            }

            JSONObject object=new JSONObject(sb.toString()).getJSONArray("HeWeather6")
                    .getJSONObject(0);
            JSONObject now=object.getJSONObject("now");
            JSONObject basic=object.getJSONObject("basic");

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

            ret=jsonObject.toString();


        } catch ( Exception ignored ) {
        } finally {
            //关闭流
            try {
                if(is!=null){
                    is.close();
                }
                if(br!=null){
                    br.close();
                }
                if (out!=null){
                    out.close();
                }
            } catch ( Exception ignored ) {}
        }


        return ret;
    }

}
