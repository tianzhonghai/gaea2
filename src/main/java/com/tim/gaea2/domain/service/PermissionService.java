package com.tim.gaea2.domain.service;

import com.tim.gaea2.domain.entity.generated.PermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tianzhonghai on 2017/5/8.
 */
public interface PermissionService {
    List<PermissionEntity> getAllUserPermission(int userId);
}
