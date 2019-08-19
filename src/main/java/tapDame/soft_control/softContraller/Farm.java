package tapDame.soft_control.softContraller;

import com.alibaba.fastjson.JSON;
import tapDame.pojo.Data;

/**
 * @author shipengfei
 * @data 19-8-11
 */
public class Farm {

    public String farmPage(Data data){
        Data redata=new Data();



        String res= JSON.toJSONString(redata);
        return res;

    }


}
