package org.openokr.sys;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.sys.service.IRoleService;
import org.openokr.sys.vo.RoleVOExt;
import org.openokr.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/sys/role.htm")
    public String index() {
        return "sys/role";
    }

    @RequiresPermissions("Role:view")
    @PostMapping(value = "/sys/role/findByPageLikeInputValue.json")
    @ResponseBody
    public Page findByPageLikeInputValue(int currentPage, int pageSize, String inputValue) {
        Page page = new Page(currentPage, pageSize);
        return roleService.findByPageLikeInputValue(page, inputValue);
    }

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/sys/role/loadPermissionsByRoleId.json")
    @ResponseBody
    public List<String> loadPermissionsByRoleId(String roleId) {
        return roleService.loadPermissionsByRoleId(roleId);
    }

    @RequiresPermissions("Role:edit")
    @PostMapping(value = "/sys/role/save.json")
    @ResponseBody
    public ResponseResult save(@JsonPathParam("$.vo") RoleVOExt vo,
                               @JsonPathParam("$.permissionIds") List<String> permissionIds) {
        if (StringUtils.isBlank(vo.getId())) {
            vo.setCreateUserId(getCurrentUserId());
            vo.setCreateTs(new Date());
        } else {
            vo.setUpdateUserId(getCurrentUserId());
            vo.setUpdateTs(new Date());
        }
        return roleService.addOrModify(vo, permissionIds);
    }

    @RequiresPermissions("Role:delete")
    @GetMapping(value = "/sys/role/delete.json")
    @ResponseBody
    public ResponseResult delete(String id) {
        return roleService.delete(id);
    }

    @PostMapping(value = "/sys/role/findByCurrentUserId.json")
    @ResponseBody
    public List<RoleVOExt> findByCurrentUserId() {
        return roleService.findByUserId(getCurrentUserId(), false);
    }

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/sys/role/findByUserId.json")
    @ResponseBody
    public List<RoleVOExt> findByUserId(String userId) {
        return roleService.findByUserId(userId, false);
    }

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/sys/role/findByLikeNameExcludeIds.json")
    @ResponseBody
    public Page findByLikeNameExcludeIds(@JsonPathParam("$.currentPage") int currentPage,
                                         @JsonPathParam("$.pageSize") int pageSize,
                                         @JsonPathParam("$.inputValue") String inputValue,
                                         @JsonPathParam("$.excludeIds") List<String> excludeIds) {
        return roleService.findPageByLikeNameExcludeIds(new Page(currentPage, pageSize), inputValue, excludeIds);
    }
}