package tapDame.dao;

import org.apache.ibatis.annotations.Param;
import tapDame.pojo.FarmControl;

public interface FarmContralDao {
    FarmControl findByHT(@Param("humidity") String humidity,@Param("tmp") String tmp);
    void insertFarmContral(FarmControl farmControl);
    void updateFarmContral(FarmControl farmControl);

}
