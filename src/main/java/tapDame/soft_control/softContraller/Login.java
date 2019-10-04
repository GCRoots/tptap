package tapDame.soft_control.softContraller;

import com.alibaba.fastjson.JSON;
import tapDame.dao.UserDao;
import tapDame.dao.ipm.UserDaoImp;
import tapDame.dao.redis.RedisOperating;
import tapDame.pojo.Data;
import tapDame.pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shipengfei
 * @data 19-8-11
 */
public class Login {

    private UserDao userDao=new UserDaoImp();


//    登录
    public String login(Data data){
        Data redata=new Data();

        String phone=data.getPhone();
        String password=data.getPassword();

        User user=userDao.findByPhone(phone);

        if (user!=null){
            if (password.equals(user.getPassword())){
                redata.setSuccess("t");
            }else {
                redata.setSuccess("f");
            }
        }else {
            redata.setSuccess("f");
        }

        String res= JSON.toJSONString(redata);
        return res;



    }

//    注册
    public String register(Data data){
        Data redata=new Data();

//        Map hash = new HashMap<String,String>();


        String phone=data.getPhone();
        String password=data.getPassword();
//        String idCode=data.getIdCode();

        User user=new User();
        user.setPhone(phone);
        user.setPassword(password);

//        hash.put(user.getPhone(),user.getPassword());
//        RedisOperating.hset("daily_water",hash);

        userDao.insertUser(user);

        if (userDao.findByPhone(phone)!=null)
            redata.setSuccess("t");
        else redata.setSuccess("f");

        String res= JSON.toJSONString(redata);
        return res;
    }

//    验证码
    public String idCode(Data data){
        Data redata=new Data();

        String phone=data.getPhone();
        String idCode=data.getIdCode();



        String res= JSON.toJSONString(redata);
        return res;

    }


}
