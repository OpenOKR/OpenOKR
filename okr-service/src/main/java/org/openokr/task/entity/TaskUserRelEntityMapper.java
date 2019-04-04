package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.TaskUserRelEntity;
import org.openokr.task.entity.TaskUserRelEntityCondition;

public interface TaskUserRelEntityMapper extends BaseMapper {
    long countByCondition(TaskUserRelEntityCondition example);

    int deleteByCondition(TaskUserRelEntityCondition example);

    List<TaskUserRelEntity> selectByCondition(TaskUserRelEntityCondition example);

    int updateByCondition(@Param("record") TaskUserRelEntity record, @Param("example") TaskUserRelEntityCondition example);
}