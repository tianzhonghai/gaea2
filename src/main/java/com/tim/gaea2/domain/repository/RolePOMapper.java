package com.tim.gaea2.domain.repository;

import com.tim.gaea2.domain.entity.RolePO;
import com.tim.gaea2.domain.entity.RolePOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePOMapper {
    long countByExample(RolePOExample example);

    int deleteByExample(RolePOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RolePO record);

    int insertSelective(RolePO record);

    List<RolePO> selectByExample(RolePOExample example);

    RolePO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RolePO record, @Param("example") RolePOExample example);

    int updateByExample(@Param("record") RolePO record, @Param("example") RolePOExample example);

    int updateByPrimaryKeySelective(RolePO record);

    int updateByPrimaryKey(RolePO record);
}