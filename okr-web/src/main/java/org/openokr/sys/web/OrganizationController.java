package org.openokr.sys.web;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.sys.service.IOrganizationService;
import org.openokr.sys.vo.OrganizationVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys")
public class OrganizationController extends BaseController {

    @Autowired
    private IOrganizationService organizationService;

    @RequiresPermissions("Organization:view")
    @GetMapping(value = "/organization.htm")
    public String index() {
        return "sys/organization";
    }

    @RequiresPermissions("Organization:view")
    @PostMapping(value = "/organization/findAll.json")
    @ResponseBody
    public List<OrganizationVOExt> findAll() {
        return organizationService.findAll();
    }

    @RequiresPermissions("Organization:edit")
    @PostMapping(value = "/organization/save.json")
    @ResponseBody
    public ResponseResult save(@JsonPathParam("$.vo") OrganizationVOExt vo) {
        return organizationService.addOrModify(vo);
    }

    @RequiresPermissions("Organization:delete")
    @GetMapping(value = "/organization/delete.json")
    @ResponseBody
    public ResponseResult delete(String id) {
        return organizationService.delete(id);
    }

    @GetMapping(value = "/organization/findCurrentAndChildren.json")
    @ResponseBody
    public List<OrganizationVOExt> findCurrentAndChildren() {
        return organizationService.findCurrentAndChildren(this.getCurrentOrganizationId());
    }

    @RequiresPermissions("user")
    @PostMapping(value = "/organization/findContainUserOfAll.json")
    @ResponseBody
    public List<Map<String, Object>> findContainUserOfAll() {
        return organizationService.findContainUserOfAll(getCurrentUserId());
    }
}