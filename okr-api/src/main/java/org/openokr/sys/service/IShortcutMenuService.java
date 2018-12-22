package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.sys.vo.ShortcutMenuVOExt;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/20.
 */
public interface IShortcutMenuService {

    ResponseResult save(String userId, List<String> menuIds);

    List<ShortcutMenuVOExt> findByUserId(String userId);

    int deleteByMenuId(String menuId);

    int deleteByUserId(String userId);
}