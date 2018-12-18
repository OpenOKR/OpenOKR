package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.ShortcutMenuEntity;
import org.openokr.sys.entity.ShortcutMenuEntityCondition;

public interface ShortcutMenuEntityMapper extends BaseMapper {
    long countByCondition(ShortcutMenuEntityCondition example);

    int deleteByCondition(ShortcutMenuEntityCondition example);

    List<ShortcutMenuEntity> selectByCondition(ShortcutMenuEntityCondition example);

    int updateByCondition(@Param("record") ShortcutMenuEntity record, @Param("example") ShortcutMenuEntityCondition example);
}