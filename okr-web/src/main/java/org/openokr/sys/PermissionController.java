package org.openokr.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.web.BaseController;
import org.openokr.sys.service.IPermissionService;
import org.openokr.sys.vo.PermissionVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController extends BaseController {

    @Autowired
    private IPermissionService permissionService;

    @RequiresPermissions("Menu:view")
    @GetMapping(value = "/sys/permission/findByMenuId.json")
    @ResponseBody
    public List<PermissionVOExt> findByMenuId(String menuId) {
        return permissionService.findByMenuId(menuId);
    }

}