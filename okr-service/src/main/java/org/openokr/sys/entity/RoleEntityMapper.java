package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.RoleEntity;
import org.openokr.sys.entity.RoleEntityCondition;

public interface RoleEntityMapper extends BaseMapper {
    long countByCondition(RoleEntityCondition example);

    int deleteByCondition(RoleEntityCondition example);

    List<RoleEntity> selectByCondition(RoleEntityCondition example);

    int updateByCondition(@Param("record") RoleEntity record, @Param("example") RoleEntityCondition example);
}