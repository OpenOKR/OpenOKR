package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.TeamUserRelaEntity;
import org.openokr.manage.entity.TeamUserRelaEntityCondition;

public interface TeamUserRelaEntityMapper extends BaseMapper {
    long countByCondition(TeamUserRelaEntityCondition example);

    int deleteByCondition(TeamUserRelaEntityCondition example);

    List<TeamUserRelaEntity> selectByCondition(TeamUserRelaEntityCondition example);

    int updateByCondition(@Param("record") TeamUserRelaEntity record, @Param("example") TeamUserRelaEntityCondition example);
}