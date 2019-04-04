package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.ApportionCategoryEntity;
import org.openokr.task.entity.ApportionCategoryEntityCondition;

public interface ApportionCategoryEntityMapper extends BaseMapper {
    long countByCondition(ApportionCategoryEntityCondition example);

    int deleteByCondition(ApportionCategoryEntityCondition example);

    List<ApportionCategoryEntity> selectByCondition(ApportionCategoryEntityCondition example);

    int updateByCondition(@Param("record") ApportionCategoryEntity record, @Param("example") ApportionCategoryEntityCondition example);
}