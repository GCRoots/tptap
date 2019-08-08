package tapDame.dao;

import tapDame.pojo.FarmControl;

public interface FarmContralDao {
    FarmControl findByHW(String humidity,String weather);
    void addFarmContral(FarmControl farmControl);
    void updateFarmContral(FarmControl farmControl);

}
