package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.sys.vo.PermissionVO;
import org.openokr.sys.vo.PermissionVOExt;

import java.util.List;
import java.util.Set;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public interface IPermissionService {

    List<PermissionVOExt> findByUserId(String userId);

    ResponseResult addModifyOrDelete(String menuId, List<PermissionVO> permissionVOList);

    Set<String> findIdsByMenuId(String menuId);

    ResponseResult countByPermissionIds(Set<String> permissionIds);

    int deleteByMenuId(String menuId);

    List<PermissionVOExt> findByMenuId(String menuId);
}