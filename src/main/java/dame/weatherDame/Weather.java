package dame.weatherDame;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Weather {

    public static void main(String[] args) {

        Weather weather=new Weather();
//        String s=weather.getWeatherInform("天津");
//        System.out.println(s);

//        String re=weather.sendGet("https://www.tianqiapi.com/api/?version=v1");
//        System.out.println(re);


        //参数字符串，如果拼接在请求链接之后，需要对中文进行 URLEncode   字符集 UTF-8
        String param = "key=a9f72b3f74a24439863952cd65a6c082&location=天津";
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        PrintWriter out = null;
        try {
            //接口地址
            String url = "https://free-api.heweather.net/s6/weather/now";
            URL    uri = new URL(url);
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

            JSONObject jsonObject=new JSONObject(sb.toString());
            System.out.println(sb.toString());
            System.out.println(jsonObject.toString(2));

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







    }

    //根据城市获取天气信息的java代码
    //cityName 是你要取得天气信息的城市的中文名字，如“北京”，“深圳”
    public String  getWeatherInform(String cityName){

        //百度天气API
        String baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=W69oaDTCfuGwzNwmtVvgWfGH";
        StringBuffer strBuf;

        try {
            //通过浏览器直接访问http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ
            //5slgyqGDENN7Sy7pw29IUvrZ 是我自己申请的一个AK(许可码)，如果访问不了，可以自己去申请一个新的ak
            //百度ak申请地址：http://lbsyun.baidu.com/apiconsole/key
            //要访问的地址URL，通过URLEncoder.encode()函数对于中文进行转码
            baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location="+
                    URLEncoder.encode(cityName, "utf-8")+
                    "&output=json&ak=Y2zKjccvcRPcqfu4hLvepVRfqVjR2y4f";
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        strBuf = new StringBuffer();

        try{
            URL url = new URL(baiduUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return strBuf.toString();
    }


    public String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader
                    (new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
