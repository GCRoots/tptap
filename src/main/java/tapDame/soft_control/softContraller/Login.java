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

        if (password.equals(user.getPassword())){
            redata.setSuccess("t");
        }else {
            redata.setSuccess("f");
        }

        String res= JSON.toJSONString(redata);
        return res;



    }

//    注册
    public void register(Data data){
        Data redata=new Data();

        String phone=data.getPhone();
        String password=data.getPassword();
        String idCode=data.getIdCode();



    }

//    验证码
    public void idCode(Data data){
        Data redata=new Data();

        String phone=data.getPhone();
        String idCode=data.getIdCode();




    }


}
