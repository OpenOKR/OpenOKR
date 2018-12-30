package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.ResultUserRelaEntity;
import org.openokr.manage.entity.ResultUserRelaEntityCondition;

public interface ResultUserRelaEntityMapper extends BaseMapper {
    long countByCondition(ResultUserRelaEntityCondition example);

    int deleteByCondition(ResultUserRelaEntityCondition example);

    List<ResultUserRelaEntity> selectByCondition(ResultUserRelaEntityCondition example);

    int updateByCondition(@Param("record") ResultUserRelaEntity record, @Param("example") ResultUserRelaEntityCondition example);
}