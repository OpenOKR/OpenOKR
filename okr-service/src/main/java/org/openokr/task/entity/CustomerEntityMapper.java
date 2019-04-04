package org.openokr.task.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.task.entity.CustomerEntity;
import org.openokr.task.entity.CustomerEntityCondition;

public interface CustomerEntityMapper extends BaseMapper {
    long countByCondition(CustomerEntityCondition example);

    int deleteByCondition(CustomerEntityCondition example);

    List<CustomerEntity> selectByCondition(CustomerEntityCondition example);

    int updateByCondition(@Param("record") CustomerEntity record, @Param("example") CustomerEntityCondition example);
}