package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.DailyEntity;
import org.openokr.task.entity.DailyEntityCondition;

public interface DailyEntityMapper extends BaseMapper {
    long countByCondition(DailyEntityCondition example);

    int deleteByCondition(DailyEntityCondition example);

    List<DailyEntity> selectByCondition(DailyEntityCondition example);

    int updateByCondition(@Param("record") DailyEntity record, @Param("example") DailyEntityCondition example);
}