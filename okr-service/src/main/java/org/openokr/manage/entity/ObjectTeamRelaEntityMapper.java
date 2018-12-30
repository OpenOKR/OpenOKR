package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.ObjectTeamRelaEntity;
import org.openokr.manage.entity.ObjectTeamRelaEntityCondition;

public interface ObjectTeamRelaEntityMapper extends BaseMapper {
    long countByCondition(ObjectTeamRelaEntityCondition example);

    int deleteByCondition(ObjectTeamRelaEntityCondition example);

    List<ObjectTeamRelaEntity> selectByCondition(ObjectTeamRelaEntityCondition example);

    int updateByCondition(@Param("record") ObjectTeamRelaEntity record, @Param("example") ObjectTeamRelaEntityCondition example);
}