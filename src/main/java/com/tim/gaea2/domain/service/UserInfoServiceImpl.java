package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.UserPO;
import com.tim.gaea2.domain.entity.UserPOExample;
import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.entity.UserRolePOExample;
import com.tim.gaea2.domain.model.SysUser;
import com.tim.gaea2.domain.repository.UserPOMapper;
import com.tim.gaea2.domain.repository.UserRolePOMapper;
import com.tim.gaea2.domain.repository.generated.UserMapper;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tim on 2017/2/12.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRolePOMapper userRolePOMapper;

    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @Override
    public SysUser getUserVoByUserId(long id) {
        UserPO userPO = userMapper.selectByPrimaryKey(id);

        SysUser userVO = new SysUser();
        if (userPO != null) {
            userVO = toUserVO(userPO);
        }
        return userVO;
    }

    @Override
    public SysUser getUserVoByUserName(String userName) {
        UserPOExample userPOExample = new UserPOExample();
        userPOExample.createCriteria().andUserNameEqualTo(userName);
        List<UserPO> userPOs = userMapper.selectByExample(userPOExample);

        SysUser userVO = null;
        if(userPOs != null && userPOs.size() > 0){
            userVO = toUserVO(userPOs.get(0));
        }
        return userVO;
    }

    private SysUser toUserVO(UserPO userPO) {
        org.dozer.Mapper mapper = getMapper();
        //org.dozer.Mapper mapper = org.dozer.DozerBeanMapperSingletonWrapper.getInstance(); //new DozerBeanMapper();
        SysUser userVO = mapper.map(userPO, SysUser.class);

//        UserVO userVO = new UserVO();
//        userVO.setId(userPO.getId());
//        userVO.setUserName(userPO.getUserName());
//        userVO.setPassword(userPO.getPassword());
//        userVO.setState(userPO.getState());
        return userVO;
    }

    private Mapper getMapper() {
        Mapper mapper = null;
        try {
            mapper = dozerBean.getObject();
        }catch (Exception ex){
            throw new RuntimeException("获取dozerMapper异常",ex);
        }
        return mapper;
    }

    @Override
    public void addUserVO(SysUser userVO) {
//        UserPO userPO = new UserPO();
//        userPO.setUserName(userVO.getUserName());
//        userPO.setPassword(userVO.getPassword());
//        userPO.setCreateTime(new Date());
//        //userPO.setState();

        org.dozer.Mapper mapper = getMapper();
        UserPO userPO = mapper.map(userVO, UserPO.class);
        userPO.setCreateTime(new Date());
        userMapper.insert(userPO);
    }

    @Override
    public List<SysUser> getAllUserVOs() {
        List<SysUser> list = new ArrayList<>();
        List<UserPO> userPOs = userMapper.selectByExample(null);

        for (UserPO po : userPOs) {

            list.add(toUserVO(po));
        }
        return list;
    }

    @Override
    public List<UserRolePO> getUserRolesByUserId(long userId) {
        UserRolePOExample userRolePOExample = new UserRolePOExample();
        userRolePOExample.createCriteria().andUserIdEqualTo(userId);
        List<UserRolePO> list = userRolePOMapper.selectByExample(userRolePOExample);

        return list;
    }
}
