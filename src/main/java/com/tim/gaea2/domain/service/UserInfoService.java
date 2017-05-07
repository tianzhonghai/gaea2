package com.tim.gaea2.domain.service;


import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.model.User;

import java.util.List;

/**
 * Created by Tim on 2017/2/12.
 */
public interface UserInfoService {
    User getUserVoByUserId(long id);

    User getUserVoByUserName(String userName);

    void addUserVO(User userVO);

    List<User> getAllUserVOs();

    List<UserRolePO> getUserRolesByUserId(long userId);
}
