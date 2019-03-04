package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.weeklyEntity;
import org.openokr.task.entity.weeklyEntityCondition;

public interface weeklyEntityMapper extends BaseMapper {
    long countByCondition(weeklyEntityCondition example);

    int deleteByCondition(weeklyEntityCondition example);

    List<weeklyEntity> selectByCondition(weeklyEntityCondition example);

    int updateByCondition(@Param("record") weeklyEntity record, @Param("example") weeklyEntityCondition example);
}