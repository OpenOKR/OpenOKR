package org.openokr.task.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.sys.service.IMenuService;
import org.openokr.task.vo.MyTaskCountInfoVO;
import org.openokr.task.request.TaskInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * OKR报工--日常报工首页控制器
 *
 * @Author linaer
 * @Date 2019/03/01
 */
@Controller
@RequestMapping("/task")
@Api(value = "日常报工首页控制器相关接口",description = "TaskIndexController")
public class TaskIndexController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @RequiresPermissions("Role:view")
    @ApiOperation(value = "查询我的项目数据", notes = "查询我的项目数据")
    @ApiImplicitParams(
            {
            }
    )
    @RequestMapping(value = "/index/getMyTaskInfoVO.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<MyTaskCountInfoVO>> getMyTaskInfoVO() {
        String userId = this.getCurrentUserId();
        return null;
    }

    @RequiresPermissions("Role:view")
    @ApiOperation(value = "查询我管理的项目数据", notes = "查询我管理的项目数据")
    @ApiImplicitParams(
            {
            }
    )
    @RequestMapping(value = "/index/getMyManageTaskInfoVO.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<MyTaskCountInfoVO>> getMyManageTaskInfoVO() {
        String userId = this.getCurrentUserId();
        return null;
    }

    @RequiresPermissions("Role:view")
    @ApiOperation(value = "查询近期报工数据", notes = "查询近期报工数据")
    @ApiImplicitParams(
            {
            }
    )
    @RequestMapping(value = "/index/getRecentTaskInfoVO.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<TaskInfoVO>> getRecentTaskInfoVO() {
        String userId = this.getCurrentUserId();
        return null;
    }


    @RequiresPermissions("Role:view")
    @ApiOperation(value = "查询本季度客户数据", notes = "查询本季度客户耗能数据")
    @ApiImplicitParams(
            {
            }
    )
    @RequestMapping(value = "/index/getCustomerTaskInfoVO.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<TaskInfoVO>> getCustomerTaskInfoVO() {
        String userId = this.getCurrentUserId();
        return null;
    }

}