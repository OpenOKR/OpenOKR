package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.ObjectivesEntity;
import org.openokr.manage.entity.ObjectivesEntityCondition;

public interface ObjectivesEntityMapper extends BaseMapper {
    long countByCondition(ObjectivesEntityCondition example);

    int deleteByCondition(ObjectivesEntityCondition example);

    List<ObjectivesEntity> selectByCondition(ObjectivesEntityCondition example);

    int updateByCondition(@Param("record") ObjectivesEntity record, @Param("example") ObjectivesEntityCondition example);
}