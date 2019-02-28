package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.ProjectInfoEntity;
import org.openokr.task.entity.ProjectInfoEntityCondition;

public interface ProjectInfoEntityMapper extends BaseMapper {
    long countByCondition(ProjectInfoEntityCondition example);

    int deleteByCondition(ProjectInfoEntityCondition example);

    List<ProjectInfoEntity> selectByCondition(ProjectInfoEntityCondition example);

    int updateByCondition(@Param("record") ProjectInfoEntity record, @Param("example") ProjectInfoEntityCondition example);
}