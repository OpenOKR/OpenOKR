package org.openokr.sys;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.sys.service.IRoleService;
import org.openokr.sys.vo.RoleVOExt;
import org.openokr.utils.CacheUtils;
import org.openokr.utils.StringUtils;
import org.openokr.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sys")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/role.htm")
    public String index() {
        return "sys/role";
    }

    @RequiresPermissions("Role:view")
    @PostMapping(value = "/role/findByPageLikeInputValue.json")
    @ResponseBody
    public Page findByPageLikeInputValue(int currentPage, int pageSize, String inputValue) {
        Page page = new Page(currentPage, pageSize);
        return roleService.findByPageLikeInputValue(page, inputValue);
    }

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/role/loadPermissionsByRoleId.json")
    @ResponseBody
    public List<String> loadPermissionsByRoleId(String roleId) {
        return roleService.loadPermissionsByRoleId(roleId);
    }

    @RequiresPermissions("Role:edit")
    @PostMapping(value = "/role/save.json")
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
        UserUtils.clearCache(getCurrentUser());
        return roleService.addOrModify(vo, permissionIds);
    }

    @RequiresPermissions("Role:delete")
    @GetMapping(value = "/role/delete.json")
    @ResponseBody
    public ResponseResult delete(String id) {
        return roleService.delete(id);
    }

    @PostMapping(value = "/role/findAllList.json")
    @ResponseBody
    public List<RoleVOExt> findAllList() {
        return roleService.findAllList();
    }

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/role/findByUserId.json")
    @ResponseBody
    public List<RoleVOExt> findByUserId(String userId) {
        return roleService.findByUserId(userId, false);
    }

    @RequiresPermissions("Role:view")
    @GetMapping(value = "/role/findByLikeNameExcludeIds.json")
    @ResponseBody
    public Page findByLikeNameExcludeIds(@JsonPathParam("$.currentPage") int currentPage,
                                         @JsonPathParam("$.pageSize") int pageSize,
                                         @JsonPathParam("$.inputValue") String inputValue,
                                         @JsonPathParam("$.excludeIds") List<String> excludeIds) {
        return roleService.findPageByLikeNameExcludeIds(new Page(currentPage, pageSize), inputValue, excludeIds);
    }
}