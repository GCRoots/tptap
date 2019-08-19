package tapDame.dao;

import tapDame.pojo.HomeStatus;

public interface HomeStatusDao {
    HomeStatus findByHId(String id);
    void insertHomeStatus(HomeStatus homeStatus);
    void updateHomeStatus(HomeStatus homeStatus);
    void delHomeStatus(String id);


}
