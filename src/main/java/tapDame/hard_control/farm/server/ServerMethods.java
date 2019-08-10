package tapDame.hard_control.farm.server;

import org.json.JSONArray;
import org.json.JSONObject;
import tapDame.pojo.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerMethods {


    public static void main(String[] args) {
        ServerMethods serverMethods=new ServerMethods();

        serverMethods.weather();
    }

    public Data update(String jsonString){

        System.out.println("updata");
        System.out.println(jsonString);

        return null;

    }

    public Data farmContral(Data data){
        Data reData=new Data();

        JSONObject jsonObject=new JSONObject(weather());

//        相对湿度
        String hum=jsonObject.getString("hum");
//        温度
        String tmp=jsonObject.getString("tnp");
//        实况天气
        String cond_txt=jsonObject.getString("cond_txt");







        String sensor1=data.getSensor1();




        return reData;
    }

    private String weather(){
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

            JSONObject jsonObject=new JSONObject(sb.toString()).getJSONArray("HeWeather6")
                    .getJSONObject(0).getJSONObject("now");
//            System.out.println(sb.toString());
            System.out.println(jsonObject.toString(2));

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
