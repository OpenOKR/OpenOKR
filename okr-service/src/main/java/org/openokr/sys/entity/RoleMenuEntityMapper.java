package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.RoleMenuEntity;
import org.openokr.sys.entity.RoleMenuEntityCondition;

public interface RoleMenuEntityMapper extends BaseMapper {
    long countByCondition(RoleMenuEntityCondition example);

    int deleteByCondition(RoleMenuEntityCondition example);

    List<RoleMenuEntity> selectByCondition(RoleMenuEntityCondition example);

    int updateByCondition(@Param("record") RoleMenuEntity record, @Param("example") RoleMenuEntityCondition example);
}