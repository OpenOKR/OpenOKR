package org.openokr.task.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zzheng.framework.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.openokr.application.web.BaseController;
import org.openokr.common.constant.WeeklyStatisticConstants;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.task.request.WeeklyStaSearchVO;
import org.openokr.task.service.IWeeklyStaManageService;
import org.openokr.task.vo.WeeklyChartVO;
import org.openokr.task.vo.WeeklyStatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yuxinzh
 * @create 2019/3/20
 */
@Controller
@RequestMapping("/api/weekly/sta")
@Api(value = "周报统计接口",description = "WeeklyStatisticController")
public class WeeklyStatisticController extends BaseController {

    @Autowired
    private IWeeklyStaManageService weeklyStaManageService;

    @ApiOperation(value = "按产品类别查询列表", notes = "按产品类别查询列表")
    @RequestMapping(value = "/getStatisticByTask.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<WeeklyStatisticVO>> getStatisticByTask(@RequestBody WeeklyStaSearchVO condition) {
        ResponseData<List<WeeklyStatisticVO>> result = new ResponseData<>();
        try {
            List<WeeklyStatisticVO> list = weeklyStaManageService.getStatisticByTask(condition);

            result.setData(list);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("按产品类别查询列表 错误：{},参数:[{}]", e.getMessage(), JSON.toJSONString(condition), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("按产品类别查询列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(condition), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "按产品类别查询图表", notes = "周-饼图（有），月-周折线图，年-月折线图(空)")
    @RequestMapping(value = "/getWeeklyChartByTask.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<WeeklyChartVO> getWeeklyChartByTask(@RequestBody WeeklyStaSearchVO vo) {
        ResponseData<WeeklyChartVO> result = new ResponseData<>();
        try {
            WeeklyChartVO chartVO = weeklyStaManageService.getChartByTask(vo);

            result.setData(chartVO);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("按产品类别查询图表 错误：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("按产品类别查询图表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "按人员所属查询列表", notes = "按人员所属查询列表")
    @RequestMapping(value = "/getStatisticByOrg.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<WeeklyStatisticVO>> getStatisticByOrg(@RequestBody WeeklyStaSearchVO condition) {
        ResponseData<List<WeeklyStatisticVO>> result = new ResponseData<>();
        try {
            List<WeeklyStatisticVO> list = weeklyStaManageService.getStatisticByOrg(condition);

            result.setData(list);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("按人员所属查询列表 错误：{},参数:[{}]", e.getMessage(), JSON.toJSONString(condition), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("按人员所属查询列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(condition), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "按人员所属查询图表", notes = "周-饼图（有），月-周折线图，年-月折线图(空)")
    @RequestMapping(value = "/getWeeklyChartByOrg.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<WeeklyChartVO> getWeeklyChartByOrg(@RequestBody WeeklyStaSearchVO vo) {
        ResponseData<WeeklyChartVO> result = new ResponseData<>();
        try {
            WeeklyChartVO chartVO = weeklyStaManageService.getChartByOrg(vo);

            result.setData(chartVO);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("按人员所属查询图表 错误：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("按人员所属查询图表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
