package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.sys.vo.MenuVOExt;
import org.openokr.sys.vo.PermissionVO;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public interface IMenuService {

    ResponseResult addOrModify(MenuVOExt menuVOExt, List<PermissionVO> permissionVOList);

    ResponseResult delete(String id);

    List<MenuVOExt> findAll();

    List<MenuVOExt> findContainPermissionOfAll();

    MenuVOExt getById(String id);

    List<MenuVOExt> findByUserId(String userId);

    MenuVOExt findTreeOfViewByUserId(String userId);

    List<MenuVOExt> findOfViewByUserId(String userId);
}