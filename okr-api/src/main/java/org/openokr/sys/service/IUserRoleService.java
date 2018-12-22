package org.openokr.sys.service;

import org.openokr.sys.vo.UserRoleVO;

/**
 * Created by zhengzheng on 2018/12/20.
 */
public interface IUserRoleService {

    long countByRoleId(String roleId);

    int deleteByUserId(String userId);

    void add(UserRoleVO userRoleVO);
}