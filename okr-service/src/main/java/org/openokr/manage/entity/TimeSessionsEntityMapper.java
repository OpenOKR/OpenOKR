package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.TimeSessionsEntity;
import org.openokr.manage.entity.TimeSessionsEntityCondition;

public interface TimeSessionsEntityMapper extends BaseMapper {
    long countByCondition(TimeSessionsEntityCondition example);

    int deleteByCondition(TimeSessionsEntityCondition example);

    List<TimeSessionsEntity> selectByCondition(TimeSessionsEntityCondition example);

    int updateByCondition(@Param("record") TimeSessionsEntity record, @Param("example") TimeSessionsEntityCondition example);
}