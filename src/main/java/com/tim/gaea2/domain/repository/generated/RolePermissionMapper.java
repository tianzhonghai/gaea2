package com.tim.gaea2.domain.repository.generated;

import com.tim.gaea2.domain.entity.generated.RolePermissionEntity;
import com.tim.gaea2.domain.entity.generated.RolePermissionEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper {
    long countByExample(RolePermissionEntityExample example);

    int deleteByExample(RolePermissionEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionEntity record);

    int insertSelective(RolePermissionEntity record);

    List<RolePermissionEntity> selectByExample(RolePermissionEntityExample example);

    RolePermissionEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermissionEntity record, @Param("example") RolePermissionEntityExample example);

    int updateByExample(@Param("record") RolePermissionEntity record, @Param("example") RolePermissionEntityExample example);

    int updateByPrimaryKeySelective(RolePermissionEntity record);

    int updateByPrimaryKey(RolePermissionEntity record);
}