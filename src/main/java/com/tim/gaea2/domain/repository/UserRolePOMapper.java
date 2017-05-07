package com.tim.gaea2.domain.repository;

import com.tim.gaea2.domain.entity.UserRolePO;
import com.tim.gaea2.domain.entity.UserRolePOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRolePOMapper {
    long countByExample(UserRolePOExample example);

    int deleteByExample(UserRolePOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRolePO record);

    int insertSelective(UserRolePO record);

    List<UserRolePO> selectByExample(UserRolePOExample example);

    UserRolePO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserRolePO record, @Param("example") UserRolePOExample example);

    int updateByExample(@Param("record") UserRolePO record, @Param("example") UserRolePOExample example);

    int updateByPrimaryKeySelective(UserRolePO record);

    int updateByPrimaryKey(UserRolePO record);
}