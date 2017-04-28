package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.UserPO;
import com.tim.gaea2.domain.entity.UserPOExample;
import com.tim.gaea2.domain.repository.UserPOMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tim on 2017/2/12.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserPOMapper userMapper;

    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @Override
    public UserVO getUserVoByUserId(long id) {
//        UserPOExample userPOExample = new UserPOExample();
//        userPOExample.createCriteria().andIdEqualTo(id);

        UserPO userPO = userMapper.selectByPrimaryKey(id);

        UserVO userVO = new UserVO();
        if (userPO != null) {
            userVO = toUserVO(userPO);
        }
        return userVO;
    }

    private UserVO toUserVO(UserPO userPO) {
        org.dozer.Mapper mapper = null;
        try {
            mapper = dozerBean.getObject();
        }catch (Exception ex){}
        //org.dozer.Mapper mapper = org.dozer.DozerBeanMapperSingletonWrapper.getInstance(); //new DozerBeanMapper();
        UserVO userVO = mapper.map(userPO, UserVO.class);

//        UserVO userVO = new UserVO();
//        userVO.setId(userPO.getId());
//        userVO.setUserName(userPO.getUserName());
//        userVO.setPassword(userPO.getPassword());
//        userVO.setState(userPO.getState());
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

    @Override
    public List<UserVO> getAllUserVOs() {
        List<UserVO> list = new ArrayList<>();
        List<UserPO> userPOs = userMapper.selectByExample(null);

        for (UserPO po : userPOs) {

            list.add(toUserVO(po));
        }
        return list;
    }
}
