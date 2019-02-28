package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.TaskEntity;
import org.openokr.task.entity.TaskEntityCondition;

public interface TaskEntityMapper extends BaseMapper {
    long countByCondition(TaskEntityCondition example);

    int deleteByCondition(TaskEntityCondition example);

    List<TaskEntity> selectByCondition(TaskEntityCondition example);

    int updateByCondition(@Param("record") TaskEntity record, @Param("example") TaskEntityCondition example);
}