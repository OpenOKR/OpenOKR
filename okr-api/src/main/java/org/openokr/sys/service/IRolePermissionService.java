package org.openokr.sys.service;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/20.
 */
public interface IRolePermissionService {

    int deleteByPermissionIds(List<String> permissionIdList);

    int countByPermissionId(String permissionId);

    int deleteByRoleId(String roleId);

    int batchAdd(List<String> permissionIds, String roleId);
}