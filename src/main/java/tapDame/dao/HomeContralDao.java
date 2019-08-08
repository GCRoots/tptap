package tapDame.dao;


import tapDame.pojo.FarmControl;
import tapDame.pojo.HomeContral;

public interface HomeContralDao {
    HomeContral findByType(String type);
    void addHomeContral(HomeContral homeContral);
    void updateHomeContral(HomeContral homeContral);
}
