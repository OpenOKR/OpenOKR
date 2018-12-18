package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.PermissionEntity;
import org.openokr.sys.entity.PermissionEntityCondition;

public interface PermissionEntityMapper extends BaseMapper {
    long countByCondition(PermissionEntityCondition example);

    int deleteByCondition(PermissionEntityCondition example);

    List<PermissionEntity> selectByCondition(PermissionEntityCondition example);

    int updateByCondition(@Param("record") PermissionEntity record, @Param("example") PermissionEntityCondition example);
}