package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.TaskApportionEntity;
import org.openokr.task.entity.TaskApportionEntityCondition;

public interface TaskApportionEntityMapper extends BaseMapper {
    long countByCondition(TaskApportionEntityCondition example);

    int deleteByCondition(TaskApportionEntityCondition example);

    List<TaskApportionEntity> selectByCondition(TaskApportionEntityCondition example);

    int updateByCondition(@Param("record") TaskApportionEntity record, @Param("example") TaskApportionEntityCondition example);
}