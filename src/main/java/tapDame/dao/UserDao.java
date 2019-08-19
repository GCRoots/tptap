package tapDame.dao;

import tapDame.pojo.User;

public interface UserDao {
    User findByPhone(String phone);
    void insertUser(User user);
    void updateUser(User user);
    void delUser(String phone);




}
