package com.tim.gaea2.domain.repository;

import com.tim.gaea2.domain.entity.RolePermissionPO;
import com.tim.gaea2.domain.entity.RolePermissionPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionPOMapper {
    long countByExample(RolePermissionPOExample example);

    int deleteByExample(RolePermissionPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionPO record);

    int insertSelective(RolePermissionPO record);

    List<RolePermissionPO> selectByExample(RolePermissionPOExample example);

    RolePermissionPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RolePermissionPO record, @Param("example") RolePermissionPOExample example);

    int updateByExample(@Param("record") RolePermissionPO record, @Param("example") RolePermissionPOExample example);

    int updateByPrimaryKeySelective(RolePermissionPO record);

    int updateByPrimaryKey(RolePermissionPO record);
}