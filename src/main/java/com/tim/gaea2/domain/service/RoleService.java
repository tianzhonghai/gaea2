package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.RolePermissionPO;
import com.tim.gaea2.domain.entity.RolePermissionWithUrlPO;

import java.util.List;

/**
 * Created by tianzhonghai on 2017/5/8.
 */
public interface RoleService {
    List<RolePermissionWithUrlPO> getAllRoleResources();
}
