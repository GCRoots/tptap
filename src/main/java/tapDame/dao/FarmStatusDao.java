package tapDame.dao;

import tapDame.pojo.FarmStatus;
public interface FarmStatusDao {
    FarmStatus findByFId(String id);
    void insertFarmStatus(FarmStatus farmStatus);
    void updateFarmStatus(FarmStatus farmStatus);
    void delFarmStatus(String id);
}
