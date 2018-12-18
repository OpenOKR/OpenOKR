package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.MenuEntity;
import org.openokr.sys.entity.MenuEntityCondition;

public interface MenuEntityMapper extends BaseMapper {
    long countByCondition(MenuEntityCondition example);

    int deleteByCondition(MenuEntityCondition example);

    List<MenuEntity> selectByCondition(MenuEntityCondition example);

    int updateByCondition(@Param("record") MenuEntity record, @Param("example") MenuEntityCondition example);
}