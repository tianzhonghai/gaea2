package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.generated.UserEntity;
import com.tim.gaea2.domain.entity.generated.UserEntityExample;
import com.tim.gaea2.domain.model.SysUser;
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
    private DozerBeanMapperFactoryBean dozerBean;

    @Override
    public SysUser getUserVoByUserId(int id) {
        UserEntity userPO = userMapper.selectByPrimaryKey(id);

        SysUser userVO = new SysUser();
        if (userPO != null) {
            userVO = toUserVO(userPO);
        }
        return userVO;
    }

    @Override
    public SysUser getUserVoByUserName(String userName) {
        UserEntityExample userPOExample = new UserEntityExample();
        userPOExample.createCriteria().andAccountEqualTo(userName);
        List<UserEntity> userPOs = userMapper.selectByExample(userPOExample);

        SysUser userVO = null;
        if(userPOs != null && userPOs.size() > 0){
            userVO = toUserVO(userPOs.get(0));
        }
        return userVO;
    }

    private SysUser toUserVO(UserEntity userPO) {
        org.dozer.Mapper mapper = getMapper();
        //org.dozer.Mapper mapper = org.dozer.DozerBeanMapperSingletonWrapper.getInstance(); //new DozerBeanMapper();
        SysUser userVO = mapper.map(userPO, SysUser.class);
        if(userVO != null) {
            userVO.setId(userPO.getUserid());
        }
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
        org.dozer.Mapper mapper = getMapper();
        UserEntity userPO = mapper.map(userVO, UserEntity.class);
        userPO.setCreatedtime(new Date());
        userMapper.insert(userPO);
    }

    @Override
    public List<SysUser> getAllUserVOs() {
        List<SysUser> list = new ArrayList<>();
        List<UserEntity> userPOs = userMapper.selectByExample(null);

        for (UserEntity po : userPOs) {
            list.add(toUserVO(po));
        }
        return list;
    }
}
