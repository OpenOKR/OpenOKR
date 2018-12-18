package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.entity.ResultsEntityCondition;

public interface ResultsEntityMapper extends BaseMapper {
    long countByCondition(ResultsEntityCondition example);

    int deleteByCondition(ResultsEntityCondition example);

    List<ResultsEntity> selectByCondition(ResultsEntityCondition example);

    int updateByCondition(@Param("record") ResultsEntity record, @Param("example") ResultsEntityCondition example);
}