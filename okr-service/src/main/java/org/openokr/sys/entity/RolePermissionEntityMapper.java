package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.RolePermissionEntity;
import org.openokr.sys.entity.RolePermissionEntityCondition;

public interface RolePermissionEntityMapper extends BaseMapper {
    long countByCondition(RolePermissionEntityCondition example);

    int deleteByCondition(RolePermissionEntityCondition example);

    List<RolePermissionEntity> selectByCondition(RolePermissionEntityCondition example);

    int updateByCondition(@Param("record") RolePermissionEntity record, @Param("example") RolePermissionEntityCondition example);
}