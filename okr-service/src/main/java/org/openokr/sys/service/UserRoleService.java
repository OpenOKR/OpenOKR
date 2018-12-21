package org.openokr.sys.service;

import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.UserRoleEntityCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhengzheng on 2018/12/20.
 */
@Service
@Transactional
public class UserRoleService extends BaseServiceImpl implements IUserRoleService {

    @Override
    public int countByRoleId(String roleId) {
        //先查询用户的所有 角色ID集合
        UserRoleEntityCondition userRoleEntityCondition = new UserRoleEntityCondition();
        userRoleEntityCondition.createCriteria().andRoleIdEqualTo(roleId);
        return countByCondition(userRoleEntityCondition);
    }
}