package org.openokr.task.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.PageResponseData;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.task.request.TaskSearchVO;
import org.openokr.task.service.ITaskManageService;
import org.openokr.task.vo.TaskDetailVO;
import org.openokr.task.vo.TaskSaveVO;
import org.openokr.task.vo.TaskVO;
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

    @Autowired
    private ITaskManageService taskManageService;

    @ApiOperation(value = "分页查询任务列表数据", notes = "分页查询任务列表数据")
    @ApiImplicitParams(
            {
            }
    )
    @RequestMapping(value = "/getTaskListByPage.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<PageResponseData<List<TaskVO>>> getTaskListByPage(@RequestBody TaskSearchVO vo) {
        ResponseData<PageResponseData<List<TaskVO>>> result = new ResponseData<>();
        try {
            Page page = new Page(vo.getCurrentPage(), vo.getPageSize());
            page = taskManageService.getTakListByPage(page,vo);
            PageResponseData<List<TaskVO>> pageData = new PageResponseData<>();
            pageData.setCurrentPage(page.getCurrentPage());
            pageData.setPageSize(page.getPageSize());
            pageData.setTotalPage(page.getTotalPage());
            pageData.setTotalRecord(Long.valueOf(page.getTotalRecord()).intValue());
            List<TaskVO> record = JSONUtils.objectToList(page.getRecords(), TaskVO.class);
            pageData.setData(record);
            result.setData(pageData);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("分页查询任务列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("分页查询任务列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "新增任务")
    @RequestMapping(value = "/saveTask.json",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<TaskVO> saveTask(@RequestBody TaskSaveVO taskSaveVO){
        ResponseData<TaskVO> result = new ResponseData();
        try {
            TaskVO taskVO = taskManageService.saveTaskInfo(taskSaveVO);
            result.setData(taskVO);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("新增任务 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(taskSaveVO), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("新增任务 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(taskSaveVO), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
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
            taskManageService.delTaskInfoById(id);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("新增任务 异常：{},参数:[{}]", e.getMessage(), id, e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("新增任务 异常：{},参数:[{}]", e.getMessage(), id, e);
            result.setCode(7000);
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
    public ResponseData<TaskDetailVO> getTaskDetailInfo(String id){
        ResponseData result = new ResponseData();
        try {
            TaskDetailVO taskDetailVO = taskManageService.getTaskDetailById(id);
            result.setData(taskDetailVO);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("获取任务详情 异常：{},参数:[{}]", e.getMessage(), id, e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("获取任务详情 异常：{},参数:[{}]", e.getMessage(), id, e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}