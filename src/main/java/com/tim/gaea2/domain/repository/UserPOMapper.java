package com.tim.gaea2.domain.repository;

import com.tim.gaea2.domain.entity.UserPO;
import com.tim.gaea2.domain.entity.UserPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPOMapper {
    long countByExample(UserPOExample example);

    int deleteByExample(UserPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserPO record);

    int insertSelective(UserPO record);

    List<UserPO> selectByExample(UserPOExample example);

    UserPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByExample(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByPrimaryKeySelective(UserPO record);

    int updateByPrimaryKey(UserPO record);
}