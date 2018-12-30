package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.manage.entity.ObjectLabelRelaEntity;
import org.openokr.manage.entity.ObjectLabelRelaEntityCondition;

public interface ObjectLabelRelaEntityMapper extends BaseMapper {
    long countByCondition(ObjectLabelRelaEntityCondition example);

    int deleteByCondition(ObjectLabelRelaEntityCondition example);

    List<ObjectLabelRelaEntity> selectByCondition(ObjectLabelRelaEntityCondition example);

    int updateByCondition(@Param("record") ObjectLabelRelaEntity record, @Param("example") ObjectLabelRelaEntityCondition example);
}