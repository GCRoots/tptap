package tapDame.soft_control.softContraller;

import com.alibaba.fastjson.JSON;
import tapDame.dao.DailyWaterDao;
import tapDame.dao.HomeStatusDao;
import tapDame.dao.ipm.DailyWaterDaoImp;
import tapDame.dao.ipm.HomeStatusDaoImp;
import tapDame.pojo.DailyWater;
import tapDame.pojo.Data;
import tapDame.pojo.HomeStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shipengfei
 * @data 19-8-11
 */
public class Home {

    private HomeStatusDao homeStatusDao=new HomeStatusDaoImp();
    private DailyWaterDao dailyWaterDao=new DailyWaterDaoImp();

    public String homePage(Data data){
        Data redata=new Data();

        String tapId=data.getTapId();

        HomeStatus homeStatus=homeStatusDao.findByHId(tapId);

        String microphone=homeStatus.getMicrophone();
        String camera=homeStatus.getCamera();
        String ifUsed=homeStatus.getIfUsed();

        redata.setMicrophone(microphone);
        redata.setCamera(camera);
        redata.setIfUsed(ifUsed);

        String res= JSON.toJSONString(redata);
        return res;
    }

    public String waterConsumption(Data data){
        Data redata=new Data();

        String tapId=data.getTapId();

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日

        Date date=new Date();
        String idDate=tapId+"_"+dateFormat.format(date);

        DailyWater dailyWater=dailyWaterDao.findByIdDate(idDate);

        if (dailyWater==null){
            return null;
        }

        String purpose=dailyWater.getPurpose();
        String todayUsed=dailyWater.getTodayUsed();

        redata.setPurpose(purpose);
        redata.setTodayUsed(todayUsed);

        String res= JSON.toJSONString(redata);
        return res;
    }

}
