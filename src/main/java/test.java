import tapDame.dao.UserDao;
import tapDame.dao.ipm.UserDaoImp;
import tapDame.pojo.User;

import java.io.IOException;

public class test {



    public static void main(String[] args) throws IOException {

        UserDao userServer=new UserDaoImp();
        User user=new User();
        user.setPhone("1234567789");
        user.setPassword("147852");

        User user1=userServer.findByPhone("123456");
        System.out.println(user1.getPassword());
    }

}
