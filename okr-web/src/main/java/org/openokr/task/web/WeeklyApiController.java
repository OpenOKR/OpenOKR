package org.openokr.task.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.PageResponseData;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.task.request.WeeklySearchVO;
import org.openokr.task.service.IWeeklyManageService;
import org.openokr.task.vo.WeeklyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yuxinzh
 * @create 2019/3/5
 */
@Controller
@RequestMapping("/api/daily")
@Api(value = "周报相关接口",description = "WeeklyController")
public class WeeklyApiController extends BaseController {

    private static final String MONDAY_DATE = "mondayDate";
    private static final String SUNDAY_DATE = "sundayDate";

    @Autowired
    private IWeeklyManageService weeklyManageService;

    @ApiOperation(value = "分页查询周报列表数据", notes = "分页查询周报列表数据")
    @RequestMapping(value = "/getWeeklyPage.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<PageResponseData<List<WeeklyVO>>> getWeeklyPage(@RequestBody WeeklySearchVO vo) {
        ResponseData<PageResponseData<List<WeeklyVO>>> result = new ResponseData<>();
        try {
            Page page = new Page(vo.getCurrentPage(), vo.getPageSize());
            vo.setReportUserId(this.getCurrentUserId());
            page = weeklyManageService.queryPage(vo,page);
            PageResponseData<List<WeeklyVO>> pageData = this.reBuildPageData(page,WeeklyVO.class);
            result.setData(pageData);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("分页查询周报列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("分页查询周报列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "获取本周初始化的周报数据", notes = "用于进入周报填写页面的时候，从日报中提取出周报的初始化数据")
    @RequestMapping(value = "/initWeeklyList.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<WeeklyVO>> initWeeklyList() {
        ResponseData<List<WeeklyVO>> result = new ResponseData<>();
        try {
            // 获取周一和周日的日期
            Map<String,String> weekDayMap = this.getWeekDate();

            WeeklySearchVO searchVO = new WeeklySearchVO();
            searchVO.setReportUserId(this.getCurrentUserId());
            searchVO.setReportStartDateStrEquals(weekDayMap.get(MONDAY_DATE));
            searchVO.setReportEndDateStrEquals(weekDayMap.get(SUNDAY_DATE));
            List<WeeklyVO> weeklyList = weeklyManageService.getWeeklyListFromDaily(searchVO);
            result.setData(weeklyList);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("获取本周初始化的周报数据 异常：{}", e.getMessage(), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("获取本周初始化的周报数据 异常：{}", e.getMessage(), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "保存日报列表", notes = "保存日报列表")
    @RequestMapping(value = "/saveWeeklyList.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> saveWeeklyList(@RequestBody List<WeeklyVO> weeklyList) {
        ResponseData<String> result = new ResponseData<>();
        try {
            // 获取周一和周日的日期
            Map<String,String> weekDayMap = this.getWeekDate();

            weeklyManageService.insertWeeklyList(weeklyList, this.getCurrentUserId(),weekDayMap.get(MONDAY_DATE),weekDayMap.get(SUNDAY_DATE));
            result.setData("保存成功");
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("保存日报列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(weeklyList), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("保存日报列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(weeklyList), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 查询当前日期所在周的周一与周日日期
     * @return
     */
    private Map<String,String> getWeekDate() {
        Map<String,String> map = Maps.newHashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek == 1){
            // 周日放后面
            dayWeek = 8;
        }

        logger.info("要计算日期为:{}", sdf.format(cal.getTime()));

        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);
        logger.info("所在周星期一的日期：{}",weekBegin);

        // 周一直接加6天就是周日
        cal.add(Calendar.DATE, 6);
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);
        logger.info("所在周星期日的日期：{}" , weekEnd);

        map.put(MONDAY_DATE, weekBegin);
        map.put(SUNDAY_DATE, weekEnd);
        return map;
    }
}
