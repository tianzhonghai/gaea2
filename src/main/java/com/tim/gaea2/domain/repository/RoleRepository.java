package com.tim.gaea2.domain.repository;

import com.tim.gaea2.domain.entity.generated.PermissionEntity;

import java.util.List;

/**
 * Created by Tim on 2017/7/7.
 */
public interface RoleRepository {
    /**
     * 获取用户所有权限
     * @param userId
     * @return
     */
    List<PermissionEntity> getAllUserPermission(int userId);
}
