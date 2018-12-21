package org.openokr.sys;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.shiro.AuthRealm;
import org.openokr.sys.service.IMenuService;
import org.openokr.sys.vo.MenuVOExt;
import org.openokr.sys.vo.PermissionVO;
import org.openokr.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @RequiresPermissions("Menu:view")
    @GetMapping(value = "/sys/menu.htm")
    public String index() {
        return "sys/menu";
    }

    @RequiresPermissions("Menu:view")
    @GetMapping(value = "/sys/menu/findContainPermissionOfAll.json")
    @ResponseBody
    public List<MenuVOExt> findContainPermissionOfAll() {
        return menuService.findContainPermissionOfAll();
    }

    @RequiresPermissions("Menu:view")
    @GetMapping(value = "/sys/menu/findAll.json")
    @ResponseBody
    public List<MenuVOExt> findAll() {
        return menuService.findAll();
    }

    @RequiresPermissions("Menu:edit")
    @PostMapping(value = "/sys/menu/save.json")
    @ResponseBody
    public ResponseResult save(@JsonPathParam("$.vo") MenuVOExt vo,
                               @JsonPathParam("$.permissionEntityMapList") List<Map<String, Object>> permissionEntityMapList) {
        if (permissionEntityMapList != null && !permissionEntityMapList.isEmpty()) {
            List<PermissionVO> permissionEntities = MapUtils.mapListToBeanList(permissionEntityMapList, PermissionVO.class);
            return menuService.addOrModify(vo, permissionEntities);
        } else {
            return menuService.addOrModify(vo, null);
        }
    }

    //@RequiresPermissions("Menu:view")这里不限制权限，SQL查询指定关联了用户
    @GetMapping(value = "/sys/menu/findTreeOfView.json")
    @ResponseBody
    public MenuVOExt findTreeOfView() {
        AuthRealm.Principal principal = UserUtils.getPrincipal();
        return menuService.findTreeOfViewByUserId(principal.getId());
    }

    @RequiresPermissions("Menu:view")
    @GetMapping(value = "/sys/menu/findOfView.json")
    @ResponseBody
    public List<MenuVOExt> findOfView() {
        AuthRealm.Principal principal = UserUtils.getPrincipal();
        return menuService.findOfViewByUserId(principal.getId());
    }

    @RequiresPermissions("Menu:delete")
    @GetMapping(value = "/sys/menu/delete.json")
    @ResponseBody
    public ResponseResult delete(String id) {
        return menuService.delete(id);
    }

}