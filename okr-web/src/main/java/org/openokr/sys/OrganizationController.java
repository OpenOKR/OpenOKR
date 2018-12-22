package org.openokr.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.web.BaseController;
import org.openokr.sys.service.IOrganizationService;
import org.openokr.sys.vo.OrganizationVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrganizationController extends BaseController {

    @Autowired
    private IOrganizationService organizationService;

    @RequiresPermissions("Organization:view")
    @PostMapping(value = "/sys/organization/findAll.json")
    @ResponseBody
    public List<OrganizationVOExt> findAll() {
        return organizationService.findAll();
    }

}