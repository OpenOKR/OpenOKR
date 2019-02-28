package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.TaskKrRelEntity;
import org.openokr.task.entity.TaskKrRelEntityCondition;

public interface TaskKrRelEntityMapper extends BaseMapper {
    long countByCondition(TaskKrRelEntityCondition example);

    int deleteByCondition(TaskKrRelEntityCondition example);

    List<TaskKrRelEntity> selectByCondition(TaskKrRelEntityCondition example);

    int updateByCondition(@Param("record") TaskKrRelEntity record, @Param("example") TaskKrRelEntityCondition example);
}