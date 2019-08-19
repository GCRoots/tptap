package tapDame.dao;

import tapDame.pojo.DailyWater;

public interface DailyWaterDao {

    DailyWater findByIdDate(String idDate);
    void insertDailyWater(DailyWater dailyWater);
    void updateDailyWater(DailyWater dailyWater);

}
