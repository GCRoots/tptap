package tapDame.soft_control.softContraller;

import com.alibaba.fastjson.JSON;
import tapDame.dao.UserDao;
import tapDame.dao.ipm.UserDaoImp;
import tapDame.pojo.Data;
import tapDame.pojo.User;

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

        String phone=data.getPhone();
        String password=data.getPassword();
        String idCode=data.getIdCode();



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
