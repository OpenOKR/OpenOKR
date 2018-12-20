package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.OrganizationEntity;
import org.openokr.sys.entity.OrganizationEntityCondition;

public interface OrganizationEntityMapper extends BaseMapper {
    long countByCondition(OrganizationEntityCondition example);

    int deleteByCondition(OrganizationEntityCondition example);

    List<OrganizationEntity> selectByCondition(OrganizationEntityCondition example);

    int updateByCondition(@Param("record") OrganizationEntity record, @Param("example") OrganizationEntityCondition example);
}