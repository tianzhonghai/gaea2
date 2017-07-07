package com.tim.gaea2.domain.repository.generated;

import com.tim.gaea2.domain.entity.generated.UserRoleEntity;
import com.tim.gaea2.domain.entity.generated.UserRoleEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    long countByExample(UserRoleEntityExample example);

    int deleteByExample(UserRoleEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleEntity record);

    int insertSelective(UserRoleEntity record);

    List<UserRoleEntity> selectByExample(UserRoleEntityExample example);

    UserRoleEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRoleEntity record, @Param("example") UserRoleEntityExample example);

    int updateByExample(@Param("record") UserRoleEntity record, @Param("example") UserRoleEntityExample example);

    int updateByPrimaryKeySelective(UserRoleEntity record);

    int updateByPrimaryKey(UserRoleEntity record);
}