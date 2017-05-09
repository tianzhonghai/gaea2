package com.tim.gaea2.domain.service;


import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.model.SysUser;

import java.util.List;

/**
 * Created by Tim on 2017/2/12.
 */
public interface UserInfoService {
    SysUser getUserVoByUserId(long id);

    SysUser getUserVoByUserName(String userName);

    void addUserVO(SysUser userVO);

    List<SysUser> getAllUserVOs();

    List<UserRolePO> getUserRolesByUserId(long userId);
}
