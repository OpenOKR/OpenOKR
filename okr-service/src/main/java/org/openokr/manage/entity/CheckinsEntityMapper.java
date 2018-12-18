package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.CheckinsEntity;
import org.openokr.manage.entity.CheckinsEntityCondition;

public interface CheckinsEntityMapper extends BaseMapper {
    long countByCondition(CheckinsEntityCondition example);

    int deleteByCondition(CheckinsEntityCondition example);

    List<CheckinsEntity> selectByCondition(CheckinsEntityCondition example);

    int updateByCondition(@Param("record") CheckinsEntity record, @Param("example") CheckinsEntityCondition example);
}