package tapDame.hard_control.farm.server;

import org.json.JSONArray;
import org.json.JSONObject;
import tapDame.dao.FarmContralDao;
import tapDame.dao.FarmStatusDao;
import tapDame.dao.ipm.FarmContralDaoImp;
import tapDame.dao.ipm.FarmStatusDaoImp;
import tapDame.pojo.Data;
import tapDame.pojo.FarmControl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerMethods {

    FarmContralDao farmContralDao=new FarmContralDaoImp();
    FarmStatusDao farmStatusDao=new FarmStatusDaoImp();


    public static void main(String[] args) {
        ServerMethods serverMethods=new ServerMethods();

        serverMethods.weather();
    }

    public void update(Data data){

        System.out.println("updata");



    }

    public Data farmWaterContral(Data data){

        Data reData=new Data();

        JSONObject jsonObject=new JSONObject(weather());

//        相对湿度
        String hum=jsonObject.getString("hum");
//        温度
        String tmp=jsonObject.getString("tmp");

        FarmControl farmControl=farmContralDao.findByHT(hum,tmp);

//        当前条件下，需要浇水的湿度
        int need=Integer.parseInt(farmControl.getNeed());
//        实际湿度
        int humidity=Integer.parseInt(data.getHumidity());

        if (humidity<need){
//            当前湿度与需要浇水的湿度差决定要浇水的量
//            算法后补
            int value=need-humidity;

            int water=value*10;
            System.out.println(water);

            reData.setWater(String.valueOf(water));
        }else {
            reData.setWater("0");
        }

        update(data);

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
//            System.out.println(jsonObject.toString(2));

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
