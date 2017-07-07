package com.tim.gaea2.domain.repository.generated;

import com.tim.gaea2.domain.entity.generated.PermissionEntity;
import com.tim.gaea2.domain.entity.generated.PermissionEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {
    long countByExample(PermissionEntityExample example);

    int deleteByExample(PermissionEntityExample example);

    int deleteByPrimaryKey(Integer permissionid);

    int insert(PermissionEntity record);

    int insertSelective(PermissionEntity record);

    List<PermissionEntity> selectByExample(PermissionEntityExample example);

    PermissionEntity selectByPrimaryKey(Integer permissionid);

    int updateByExampleSelective(@Param("record") PermissionEntity record, @Param("example") PermissionEntityExample example);

    int updateByExample(@Param("record") PermissionEntity record, @Param("example") PermissionEntityExample example);

    int updateByPrimaryKeySelective(PermissionEntity record);

    int updateByPrimaryKey(PermissionEntity record);
}