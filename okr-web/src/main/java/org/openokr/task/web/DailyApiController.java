package org.openokr.task.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.PageResponseData;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.task.request.DailySearchVO;
import org.openokr.task.request.TaskSearchVO;
import org.openokr.task.service.IDailyManageService;
import org.openokr.task.service.ITaskManageService;
import org.openokr.task.vo.DailyStasticsVO;
import org.openokr.task.vo.DailyVO;
import org.openokr.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 日报
 * @author yuxinzh
 * @create 2019/3/5
 */
@Controller
@RequestMapping("/api/daily")
@Api(value = "日报相关接口",description = "DailyController")
public class DailyApiController extends BaseController {

    @Autowired
    private IDailyManageService dailyManageService;

    @Autowired
    private ITaskManageService taskManageService;

    @ApiOperation(value = "分页查询日报列表数据", notes = "分页查询任务列表数据")
    @RequestMapping(value = "/getDailyPage.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<PageResponseData<List<DailyVO>>> getDailyPage(@RequestBody DailySearchVO vo) {
        ResponseData<PageResponseData<List<DailyVO>>> result = new ResponseData<>();
        try {
            Page page = null;
            //如果getCurrentPage/getPageSize有一个为空就不分页
            if (vo.getCurrentPage() !=null&&vo.getPageSize() !=null){
                page = new Page(vo.getCurrentPage(), vo.getPageSize());
            }
            vo.setReportUserId(this.getCurrentUserId());
            page = dailyManageService.queryPage(vo,page);
            PageResponseData<List<DailyVO>> pageData = this.reBuildPageData(page,DailyVO.class);
            result.setData(pageData);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("分页查询日报列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("分页查询日报列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "保存日报列表", notes = "保存日报列表")
    @RequestMapping(value = "/saveDailyList.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> saveDailyList(@RequestBody List<DailyVO> dailyList) {
        ResponseData<String> result = new ResponseData<>();
        try {
            Date date = new Date();
            if(dailyList!=null && !dailyList.isEmpty()){
                date = dailyList.get(0).getReportDay();
            }else{
                result.setCode(6000);
                result.setMessage("参数为空");
                result.setSuccess(false);
                return result;
            }
            dailyManageService.insertDailyList(dailyList,this.getCurrentUserId(), DateUtils.dateToString(date));
            result.setData("保存成功");
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("保存日报列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(dailyList), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }catch (Exception e){
            logger.error("保存日报列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(dailyList), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @ApiOperation(value = "删除日报列表", notes = "删除日报列表")
    @RequestMapping(value = "/deleteDailyList.json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<String> deleteDailyList(String id) {
        ResponseData<String> result = new ResponseData<>();
        try {
            Date date = new Date();
            if(id ==null || id.isEmpty()){
                result.setCode(6000);
                result.setMessage("参数为空");
                result.setSuccess(false);
                return result;
            }
            DailyVO dailyVO = new DailyVO();
            dailyVO.setId(id);
            dailyManageService.deleteDailyList(dailyVO);
            result.setData("删除成功");
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("删除日报列表 异常：{},参数:[{}]", e.getMessage(), id, e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }catch (Exception e){
            logger.error("删除日报列表 异常：{},参数:[{}]", e.getMessage(), id, e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @ApiOperation(value = "审核日报", notes = "审核日报")
    @RequestMapping(value = "/auditDaily.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> saveDailyList(@RequestBody DailyVO dailyVO) {
        ResponseData<String> result = new ResponseData<>();
        try {
            Date date = new Date();
            if(dailyVO == null){
                result.setCode(6000);
                result.setMessage("参数为空");
                result.setSuccess(false);
                return result;
            }
            if(StringUtils.isBlank(dailyVO.getId())){
                result.setCode(6000);
                result.setMessage("参数日报ID为空");
                result.setSuccess(false);
                return result;
            }
            if(StringUtils.isBlank(dailyVO.getAuditStatus())){
                result.setCode(6000);
                result.setMessage("参数审核状态为空");
                result.setSuccess(false);
                return result;
            }
            dailyVO.setAuditUserId(this.getCurrentUserId());
            dailyManageService.auditDailyInfo(dailyVO);
            result.setData("操作成功");
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("审核日报 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(dailyVO), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }catch (Exception e){
            logger.error("审核日报 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(dailyVO), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @ApiOperation(value = "获取全部报工统计信息", notes = "获取全部报工统计信息")
    @RequestMapping(value = "/getDailyStastics.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DailyStasticsVO> getDailyStastics(@RequestBody TaskSearchVO searchVO) {
        ResponseData<DailyStasticsVO> result = new ResponseData<>();
        try {
            Date date = new Date();
            if(searchVO == null){
                result.setCode(6000);
                result.setMessage("参数为空");
                result.setSuccess(false);
                return result;
            }
            searchVO.setCurrentUserId(this.getCurrentUserId());
            DailyStasticsVO dailyStasticsVO = taskManageService.getDailyStasticsByOwnerId(searchVO);
            result.setData(dailyStasticsVO);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("获取全部报工统计信息 异常：{},参数:[{}]", e.getMessage(), JSONUtils.objectToString(searchVO), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }catch (Exception e){
            logger.error("获取全部报工统计信息 异常：{},参数:[{}]", e.getMessage(), JSONUtils.objectToString(searchVO), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

}
