package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.LogEntity;
import org.openokr.manage.entity.LogEntityCondition;

public interface LogEntityMapper extends BaseMapper {
    long countByCondition(LogEntityCondition example);

    int deleteByCondition(LogEntityCondition example);

    List<LogEntity> selectByCondition(LogEntityCondition example);

    int updateByCondition(@Param("record") LogEntity record, @Param("example") LogEntityCondition example);
}