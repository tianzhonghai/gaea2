package com.tim.gaea2.domain.service;

import java.util.List;

/**
 * Created by Tim on 2017/2/12.
 */
public interface UserInfoService {
    UserVO getUserVoByUserId(long id);

    void addUserVO(UserVO userVO);

    List<UserVO> getAllUserVOs();
}
