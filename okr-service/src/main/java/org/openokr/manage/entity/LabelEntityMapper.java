package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.LabelEntity;
import org.openokr.manage.entity.LabelEntityCondition;

public interface LabelEntityMapper extends BaseMapper {
    long countByCondition(LabelEntityCondition example);

    int deleteByCondition(LabelEntityCondition example);

    List<LabelEntity> selectByCondition(LabelEntityCondition example);

    int updateByCondition(@Param("record") LabelEntity record, @Param("example") LabelEntityCondition example);
}