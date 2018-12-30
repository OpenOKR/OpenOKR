package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.TeamsEntity;
import org.openokr.manage.entity.TeamsEntityCondition;

public interface TeamsEntityMapper extends BaseMapper {
    long countByCondition(TeamsEntityCondition example);

    int deleteByCondition(TeamsEntityCondition example);

    List<TeamsEntity> selectByCondition(TeamsEntityCondition example);

    int updateByCondition(@Param("record") TeamsEntity record, @Param("example") TeamsEntityCondition example);
}