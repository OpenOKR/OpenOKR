package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.UserRoleEntity;
import org.openokr.sys.entity.UserRoleEntityCondition;

public interface UserRoleEntityMapper extends BaseMapper {
    long countByCondition(UserRoleEntityCondition example);

    int deleteByCondition(UserRoleEntityCondition example);

    List<UserRoleEntity> selectByCondition(UserRoleEntityCondition example);

    int updateByCondition(@Param("record") UserRoleEntity record, @Param("example") UserRoleEntityCondition example);
}