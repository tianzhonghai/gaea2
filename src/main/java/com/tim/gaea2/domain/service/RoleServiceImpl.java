package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.RoleAndPermissionPO;
import com.tim.gaea2.domain.entity.RolePermissionPO;
import com.tim.gaea2.domain.repository.RoleMapper;
import com.tim.gaea2.domain.repository.RolePermissionPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tianzhonghai on 2017/5/8.
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleAndPermissionPO> getRolePermissionsByRoleIds(long[] roleIds) {
        List<RoleAndPermissionPO> list = roleMapper.selectRolePermissionsByRoleIds(roleIds);
        return list;
    }

    @Override
    public List<RoleAndPermissionPO> getRolePermissionByUserId(long userId) {
        List<RoleAndPermissionPO> list = roleMapper.selectRolePermissionByUserId(userId);
        return list;
    }
}
