package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.UserPO;
import com.tim.gaea2.domain.entity.UserPOExample;
import com.tim.gaea2.domain.repository.UserPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Tim on 2017/2/12.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserPOMapper userMapper ;

    @Override
    public UserVO getUserVoByUserId(long id) {
//        UserPOExample userPOExample = new UserPOExample();
//        userPOExample.createCriteria().andIdEqualTo(id);

        UserPO userPO = userMapper.selectByPrimaryKey(id);

        UserVO userVO = new UserVO();
        if(userPO != null) {
            userVO.setId(userPO.getId());
            userVO.setUserName(userPO.getUserName());
            userVO.setPassword(userPO.getPassword());
            userVO.setState(userPO.getState());
        }
        return userVO;
    }

    @Override
    public void addUserVO(UserVO userVO) {
        UserPO po = new UserPO();
        po.setUserName(userVO.getUserName());
        po.setPassword(userVO.getPassword());
        po.setCreateTime(new Date());
        //po.setState();
        userMapper.insert(po);
    }
}
