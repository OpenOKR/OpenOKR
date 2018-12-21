package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.sys.vo.RoleVOExt;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public interface IRoleService {

    List<RoleVOExt> findByUserId(String userId, boolean loadPermissions);

    Page findByPageLikeInputValue(Page page, String inputValue);

    List<String> loadPermissionsByRoleId(String roleId);

    ResponseResult addOrModify(RoleVOExt roleVOExt, List<String> permissionIds);

    ResponseResult delete(String id);

    Page findPageByLikeNameExcludeIds(Page page, String inputValue, List<String> excludeIds);
}