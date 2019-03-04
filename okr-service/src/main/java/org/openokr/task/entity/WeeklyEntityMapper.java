package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.WeeklyEntity;
import org.openokr.task.entity.WeeklyEntityCondition;

public interface WeeklyEntityMapper extends BaseMapper {
    long countByCondition(WeeklyEntityCondition example);

    int deleteByCondition(WeeklyEntityCondition example);

    List<WeeklyEntity> selectByCondition(WeeklyEntityCondition example);

    int updateByCondition(@Param("record") WeeklyEntity record, @Param("example") WeeklyEntityCondition example);
}