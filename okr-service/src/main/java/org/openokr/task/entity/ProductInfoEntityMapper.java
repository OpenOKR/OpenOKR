package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.ProductInfoEntity;
import org.openokr.task.entity.ProductInfoEntityCondition;

public interface ProductInfoEntityMapper extends BaseMapper {
    long countByCondition(ProductInfoEntityCondition example);

    int deleteByCondition(ProductInfoEntityCondition example);

    List<ProductInfoEntity> selectByCondition(ProductInfoEntityCondition example);

    int updateByCondition(@Param("record") ProductInfoEntity record, @Param("example") ProductInfoEntityCondition example);
}