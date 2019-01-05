package com.ziroom.service.user;

import java.util.List;

/**
 * Created by codey on 2019/1/5.
 */
public interface UserRelationService {

    /**
     * @author codey
     * @description
     * @date 2019/1/5 12:56
     * @param
     * @return
     */
    void addUserFriendshipScore(List<String> uidLis);

    /**
     * @author codey
     * @description 扣除用户信用分
     * @date 2019/1/5 15:27
     * @param
     * @return
     */
    void deductDriverFriendshipScore(String driverUid,List<String> passengerUidList);
}
