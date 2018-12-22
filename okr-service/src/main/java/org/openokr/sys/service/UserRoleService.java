package org.openokr.sys.service;

import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.UserRoleEntity;
import org.openokr.sys.entity.UserRoleEntityCondition;
import org.openokr.sys.vo.UserRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhengzheng on 2018/12/20.
 */
@Service
@Transactional
public class UserRoleService extends BaseServiceImpl implements IUserRoleService {

    @Override
    public long countByRoleId(String roleId) {
        //先查询用户的所有 角色ID集合
        UserRoleEntityCondition userRoleEntityCondition = new UserRoleEntityCondition();
        userRoleEntityCondition.createCriteria().andRoleIdEqualTo(roleId);
        return countByCondition(userRoleEntityCondition);
    }

    @Override
    public int deleteByUserId(String userId) {
        UserRoleEntityCondition userRoleEntityCondition = new UserRoleEntityCondition();
        userRoleEntityCondition.createCriteria().andUserIdEqualTo(userId);
        return this.deleteByCondition(userRoleEntityCondition);
    }

    @Override
    public void add(UserRoleVO userRoleVO) {
        this.insert(toEntity(userRoleVO));
    }

    private UserRoleEntity toEntity(UserRoleVO userRole) {
        return BeanUtils.copyToNewBean(userRole, UserRoleEntity.class);
    }
}