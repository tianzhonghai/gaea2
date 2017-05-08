package com.tim.gaea2.domain.repository;

import com.tim.gaea2.domain.entity.RolePermissionWithUrlPO;

import java.util.List;

/**
 * Created by tianzhonghai on 2017/5/8.
 */
public interface RoleMapper {
    List<RolePermissionWithUrlPO> selectAllRolePermissions();
}
