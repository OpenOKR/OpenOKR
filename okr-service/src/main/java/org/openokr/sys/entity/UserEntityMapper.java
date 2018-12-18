package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.openokr.sys.entity.UserEntity;
import org.openokr.sys.entity.UserEntityCondition;

public interface UserEntityMapper extends BaseMapper {
    long countByCondition(UserEntityCondition example);

    int deleteByCondition(UserEntityCondition example);

    List<UserEntity> selectByCondition(UserEntityCondition example);

    int updateByCondition(@Param("record") UserEntity record, @Param("example") UserEntityCondition example);
}