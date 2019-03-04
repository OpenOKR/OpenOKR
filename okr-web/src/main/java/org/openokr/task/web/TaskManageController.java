package org.openokr.task.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.PageResponseData;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.sys.service.IMenuService;
import org.openokr.task.vo.MyTaskCountInfoVO;
import org.openokr.task.request.TaskInfoVO;
import org.openokr.task.request.TaskSearchVO;
import org.openokr.task.vo.TaskSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * OKR报工--任务管理控制器
 *
 * @Author linaer
 * @Date 2019/03/01
 */
@Controller
@RequestMapping("/task")
@Api(value = "任务管理控制器相关接口",description = "TaskManageController")
public class TaskManageController extends BaseController {

    private final static int ERROR_CODE = 40;

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
    public List<MyTaskCountInfoVO> getMyTaskInfoVO() {
        String userId = this.getCurrentUserId();
        return null;
    }

    @ApiOperation(value = "分页查询任务列表数据", notes = "分页查询任务列表数据")
    @ApiImplicitParams(
            {
            }
    )
    @RequestMapping(value = "/getTaskListByPage.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<PageResponseData<List<TaskInfoVO>>> getTaskListByPage(@RequestBody TaskSearchVO vo) {
        ResponseData<PageResponseData<List<TaskInfoVO>>> result = new ResponseData<>();
        try {
            Page page = new Page(vo.getCurrentPage(), vo.getPageSize());
            PageResponseData<List<TaskInfoVO>> pageData = new PageResponseData<>();
            pageData.setCurrentPage(page.getCurrentPage());
            pageData.setPageSize(page.getPageSize());
            pageData.setTotalPage(page.getTotalPage());
            //pageData.setTotalRecord(page.getRecords());
            //List<TaskInfoVO> record = JSONUtils..cloneForList(page.getRecords(), TaskInfoVO.class);
            //pageData.setData(record);
            result.setData(pageData);
        } catch (Exception e) {
            logger.error("查询费项列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(ERROR_CODE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "新增任务")
    @RequestMapping(value = "/saveTask.json",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveTask(@RequestBody TaskSaveVO taskSaveVO){
        ResponseData responseData = new ResponseData();
        try {
            responseData.setCode(0);
            responseData.setMessage("success");
        } catch (Exception e) {
            responseData.setCode(ERROR_CODE);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }

    @ApiOperation(value = "删除任务",notes = "根据任务id删除任务")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "任务ID",dataType = "string",paramType = "query")
            }
    )
    @RequestMapping(value = "/deleteTask.json",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData deleteTask(String id){
        ResponseData result = new ResponseData();
        try {
            result.setCode(0);
        }catch (Exception e) {
            result.setCode(ERROR_CODE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "获取任务详情",notes = "根据任务id获取任务详情")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "任务ID",dataType = "string",paramType = "query")
            }
    )
    @RequestMapping(value = "/getTaskDetailInfo.json",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<TaskInfoVO> getTaskDetailInfo(String id){
        ResponseData<TaskInfoVO> result = new ResponseData();
        try {
            result.setCode(0);
        }catch (Exception e) {
            result.setCode(ERROR_CODE);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}