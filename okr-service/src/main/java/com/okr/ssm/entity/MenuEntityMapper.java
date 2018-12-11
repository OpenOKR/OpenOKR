package com.okr.ssm.entity;

import com.jh.framework.mybatis.mapper.BaseMapper;
import com.okr.ssm.entity.MenuEntity;
import com.okr.ssm.entity.MenuEntityCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuEntityMapper extends BaseMapper {
    int countByCondition(MenuEntityCondition example);

    int deleteByCondition(MenuEntityCondition example);

    List<MenuEntity> selectByCondition(MenuEntityCondition example);

    int updateByCondition(@Param("record") MenuEntity record, @Param("example") MenuEntityCondition example);
}