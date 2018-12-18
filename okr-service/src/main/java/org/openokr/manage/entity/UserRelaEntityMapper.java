package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.UserRelaEntity;
import org.openokr.manage.entity.UserRelaEntityCondition;

public interface UserRelaEntityMapper extends BaseMapper {
    long countByCondition(UserRelaEntityCondition example);

    int deleteByCondition(UserRelaEntityCondition example);

    List<UserRelaEntity> selectByCondition(UserRelaEntityCondition example);

    int updateByCondition(@Param("record") UserRelaEntity record, @Param("example") UserRelaEntityCondition example);
}