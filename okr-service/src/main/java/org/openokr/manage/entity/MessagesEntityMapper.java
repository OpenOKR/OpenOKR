package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.MessagesEntity;
import org.openokr.manage.entity.MessagesEntityCondition;

public interface MessagesEntityMapper extends BaseMapper {
    long countByCondition(MessagesEntityCondition example);

    int deleteByCondition(MessagesEntityCondition example);

    List<MessagesEntity> selectByCondition(MessagesEntityCondition example);

    int updateByCondition(@Param("record") MessagesEntity record, @Param("example") MessagesEntityCondition example);
}