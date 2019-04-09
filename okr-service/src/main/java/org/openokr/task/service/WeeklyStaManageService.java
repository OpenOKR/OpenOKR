package org.openokr.task.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.common.constant.WeeklyStatisticConstants;
import org.openokr.db.service.IWeeklyStaDBService;
import org.openokr.task.request.WeeklyStaSearchVO;
import org.openokr.task.vo.WeeklyChartVO;
import org.openokr.task.vo.WeeklyStatisticVO;
import org.openokr.task.vo.chart.LineDataVO;
import org.openokr.task.vo.chart.PieDataVO;
import org.openokr.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yuxinzh
 * @create 2019/3/21
 */
@Service
@Transactional
public class WeeklyStaManageService extends BaseServiceImpl implements IWeeklyStaManageService {

    @Autowired
    private IWeeklyStaDBService weeklyStaDBService;

    @Override
    public List<WeeklyStatisticVO> getStatisticByTask(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getStatisticByTask-按产品类别查询列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            if (StringUtils.isBlank(condition.getReportStartDateStr())) {
                throw new BusinessException("开始时间为空");
            }
            if (StringUtils.isBlank(condition.getCategoryId())) {
                throw new BusinessException("分摊类别为空");
            }
            if (StringUtils.isBlank(condition.getTeamId())) {
                throw new BusinessException("团队ID为空");
            }

            return weeklyStaDBService.getStatisticByTask(condition);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public WeeklyChartVO getChartByTask(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getChartByTask-按产品类别查询图表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            if (StringUtils.isBlank(condition.getReportStartDateStr())) {
                throw new BusinessException("开始时间为空");
            }
            if (StringUtils.isBlank(condition.getTeamId())) {
                throw new BusinessException("团队ID为空");
            }

            WeeklyChartVO chartVO = new WeeklyChartVO();

            switch (condition.getSearchType()) {
                case WeeklyStatisticConstants.SEARCH_TYPE_WEEK:
                    // 周 饼图
                    List<WeeklyStatisticVO> pieDataList = weeklyStaDBService.getWeeklyPieByTask(condition);
                    this.handlePieData(chartVO, pieDataList);
                    break;
                case WeeklyStatisticConstants.SEARCH_TYPE_MONTH:
                    // 月 折线图 粒度精确到每周，横坐标显示每周一日期
                    // 先获取1号所在周的周一，按周取数据，然后加七天继续，直到周一日期大于本月最后一天
                    // 对结果进行循环，然后分组
                    this.handleTaskMonthData(condition, chartVO);
                    break;
                case WeeklyStatisticConstants.SEARCH_TYPE_YEAR:
                    // 年 折线图 粒度到月
                    // 按月取数据，然后加一个月继续，直到十二月为止
                    // 对结果进行循环，然后分组
                    this.handleTaskYearData(condition, chartVO);
                    break;
                default:
                    break;
            }

            return chartVO;
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyStatisticVO> getStatisticByOrg(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getStatisticByOrg-按人员所属查询列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            if (StringUtils.isBlank(condition.getReportStartDateStr())) {
                throw new BusinessException("开始时间为空");
            }
            if (StringUtils.isBlank(condition.getTeamId())) {
                throw new BusinessException("团队ID为空");
            }

            return weeklyStaDBService.getStatisticByOrg(condition);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public WeeklyChartVO getChartByOrg(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getChartByTask-按产品类别查询图表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            if (StringUtils.isBlank(condition.getReportStartDateStr())) {
                throw new BusinessException("开始时间为空");
            }
            if (StringUtils.isBlank(condition.getTeamId())) {
                throw new BusinessException("团队ID为空");
            }

            WeeklyChartVO chartVO = new WeeklyChartVO();

            switch (condition.getSearchType()) {
                case WeeklyStatisticConstants.SEARCH_TYPE_WEEK:
                    // 周 不提供，直接用左侧列表数据即可

                    break;
                case WeeklyStatisticConstants.SEARCH_TYPE_MONTH:
                    // 月 折线图 粒度精确到每周，横坐标显示每周一日期
                    // 先获取1号所在周的周一，按周取数据，然后加七天继续，直到周一日期大于本月最后一天
                    // 对结果进行循环，然后分组
                    this.handleOrgMonthData(condition, chartVO);
                    break;
                case WeeklyStatisticConstants.SEARCH_TYPE_YEAR:
                    // 年 折线图 粒度到月
                    // 按月取数据，然后加一个月继续，直到十二月为止
                    // 对结果进行循环，然后分组
                    this.handleOrgYearData(condition, chartVO);
                    break;
                default:
                    break;
            }


            return chartVO;
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    private void handlePieData(WeeklyChartVO chartVO, List<WeeklyStatisticVO> pieDataList) {
        List<PieDataVO> dataList = Lists.newArrayList();
        PieDataVO pieData;
        if (pieDataList != null && !pieDataList.isEmpty()) {
            for (WeeklyStatisticVO data:pieDataList) {
                pieData = new PieDataVO();
                pieData.setName(data.getCategoryName());
                pieData.setValue(data.getDuration().setScale(2, BigDecimal.ROUND_HALF_UP));
                dataList.add(pieData);
            }
        }
        chartVO.setPieSeriesData(dataList);
    }

    public static Date getMonday(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    private void handleTaskMonthData(WeeklyStaSearchVO condition, WeeklyChartVO chartVO) throws ParseException {
        List<LineDataVO> monthDataList = Lists.newArrayList();
        List<String> monthXAxis = Lists.newArrayList();

        // 获取本月最后一天
        Date lastDay = DateUtils.stringToDate(condition.getReportEndDateStr());

        // 周一与周日
        Date monday = getMonday(DateUtils.stringToDate(condition.getReportStartDateStr()));

        WeeklyStaSearchVO weekCondition = new WeeklyStaSearchVO();
        weekCondition.setTeamId(condition.getTeamId());
        List<WeeklyStatisticVO> weekData;
        weekCondition.setSearchType(WeeklyStatisticConstants.SEARCH_TYPE_WEEK);
        int index = 0;
        while (monday.getTime()<=lastDay.getTime()) {
            monthXAxis.add(DateUtils.dateToString(monday,"MM-dd"));
            weekCondition.setReportStartDateStr(DateUtils.dateToString(monday));
            weekData = weeklyStaDBService.getStatisticByTask(weekCondition);

            if (weekData != null && !weekData.isEmpty()) {
                for (WeeklyStatisticVO data:weekData) {
                    LineDataVO line = new LineDataVO();
                    line.setName(data.getTaskName());
                    boolean flag = false;
                    for (LineDataVO lineVO:monthDataList){
                        if (lineVO.getName().equals(line.getName())) {
                            line = lineVO;
                            flag = true;
                            break;
                        }
                    }
                    this.handleLine(monthDataList, index, data, line, flag);
                }
            }

            // 时间顺延一周
            index++;
            monday = DateUtils.addDay(monday,7);
        }

        // 结尾补0，以防万一
        for (LineDataVO lineVO:monthDataList) {
            List<BigDecimal> dataList = lineVO.getData();
            if (dataList.size()<index) {
                for (int i = dataList.size(); i < index; i++) {
                    dataList.add(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }

        chartVO.setXAxisData(monthXAxis);
        chartVO.setLineSeriesData(monthDataList);
    }

    private void handleOrgMonthData(WeeklyStaSearchVO condition, WeeklyChartVO chartVO) throws ParseException {
        List<LineDataVO> monthDataList = Lists.newArrayList();
        List<String> monthXAxis = Lists.newArrayList();

        // 获取本月最后一天
        Date lastDay = DateUtils.stringToDate(condition.getReportEndDateStr());

        // 周一
        Date monday = getMonday(DateUtils.stringToDate(condition.getReportStartDateStr()));

        WeeklyStaSearchVO weekCondition = new WeeklyStaSearchVO();
        weekCondition.setTeamId(condition.getTeamId());
        List<WeeklyStatisticVO> weekData;
        weekCondition.setSearchType(WeeklyStatisticConstants.SEARCH_TYPE_WEEK);
        int index = 0;
        while (monday.getTime()<=lastDay.getTime()) {
            monthXAxis.add(DateUtils.dateToString(monday,"MM-dd"));
            weekCondition.setReportStartDateStr(DateUtils.dateToString(monday));
            weekData = weeklyStaDBService.getStatisticByOrg(weekCondition);

            if (weekData != null && !weekData.isEmpty()) {
                for (WeeklyStatisticVO data:weekData) {
                    LineDataVO line = new LineDataVO();
                    line.setName(data.getOrgName());
                    boolean flag = false;
                    for (LineDataVO lineVO:monthDataList){
                        if (lineVO.getName().equals(line.getName())) {
                            line = lineVO;
                            flag = true;
                            break;
                        }
                    }
                    this.handleLine(monthDataList, index, data, line, flag);
                }
            }

            // 时间顺延一周
            index++;
            monday = DateUtils.addDay(monday,7);
        }

        // 结尾补0，以防万一
        for (LineDataVO lineVO:monthDataList) {
            List<BigDecimal> dataList = lineVO.getData();
            if (dataList.size()<index) {
                for (int i = dataList.size(); i < index; i++) {
                    dataList.add(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }

        chartVO.setXAxisData(monthXAxis);
        chartVO.setLineSeriesData(monthDataList);
    }

    private void handleLine(List<LineDataVO> monthDataList, int index, WeeklyStatisticVO data, LineDataVO line, boolean flag) {
        if (flag){
            List<BigDecimal> dataList = line.getData();
            // 应对可能存在的，某一时间区间没有这个name，但本区间有的情况
            if (dataList.size() < index) {
                for (int i = index - dataList.size(); i < index; i++) {
                    dataList.add(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
            dataList.add(data.getDuration().setScale(2,BigDecimal.ROUND_HALF_UP));
        } else {
            List<BigDecimal> dataList = Lists.newArrayList();
            // 应对可能存在的，某一时间区间没有这个name，但本区间有的情况
            for (int i = 0;i < index;i++) {
                dataList.add(BigDecimal.ZERO.setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            dataList.add(data.getDuration().setScale(2,BigDecimal.ROUND_HALF_UP));
            line.setData(dataList);
            monthDataList.add(line);
        }
    }

    private void handleTaskYearData(WeeklyStaSearchVO condition, WeeklyChartVO chartVO) throws ParseException {
        List<LineDataVO> yearDataList = Lists.newArrayList();
        List<String> yearXAxis = Lists.newArrayList();

        // 时间，约定传进来的是当年1月1日的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.stringToDate(condition.getReportStartDateStr()));

        WeeklyStaSearchVO monthCondition = new WeeklyStaSearchVO();
        monthCondition.setTeamId(condition.getTeamId());
        List<WeeklyStatisticVO> monthData;
        monthCondition.setSearchType(WeeklyStatisticConstants.SEARCH_TYPE_MONTH);
        Date beginDate ;
        for (int month = 1;month <= 12 ;month ++) {
            cal.set(Calendar.MONTH, month-1 );
            beginDate = cal.getTime();

            yearXAxis.add(month+"月");
            monthCondition.setReportStartDateStr(DateUtils.dateToString(beginDate));
            monthData = weeklyStaDBService.getStatisticByTask(monthCondition);

            if (monthData != null && !monthData.isEmpty()) {
                for (WeeklyStatisticVO data:monthData) {
                    LineDataVO line = new LineDataVO();
                    line.setName(data.getTaskName());
                    boolean flag = false;
                    for (LineDataVO lineVO:yearDataList){
                        if (lineVO.getName().equals(line.getName())) {
                            line = lineVO;
                            flag = true;
                            break;
                        }
                    }
                    this.handleLine(yearDataList, month-1, data, line, flag);
                }
            }
        }

        // 结尾补0，以防万一
        for (LineDataVO lineVO:yearDataList) {
            List<BigDecimal> dataList = lineVO.getData();
            if (dataList.size()<12) {
                for (int i = dataList.size(); i < 12; i++) {
                    dataList.add(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }

        chartVO.setXAxisData(yearXAxis);
        chartVO.setLineSeriesData(yearDataList);
    }

    private void handleOrgYearData(WeeklyStaSearchVO condition, WeeklyChartVO chartVO) throws ParseException {
        List<LineDataVO> yearDataList = Lists.newArrayList();
        List<String> yearXAxis = Lists.newArrayList();

        // 时间，约定传进来的是当年1月1日的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.stringToDate(condition.getReportStartDateStr()));

        WeeklyStaSearchVO monthCondition = new WeeklyStaSearchVO();
        monthCondition.setTeamId(condition.getTeamId());
        List<WeeklyStatisticVO> monthData;
        monthCondition.setSearchType(WeeklyStatisticConstants.SEARCH_TYPE_MONTH);
        Date beginDate ;
        for (int month = 1;month <= 12 ;month ++) {
            cal.set(Calendar.MONTH, month-1 );
            beginDate = cal.getTime();

            yearXAxis.add(month+"月");
            monthCondition.setReportStartDateStr(DateUtils.dateToString(beginDate));
            monthData = weeklyStaDBService.getStatisticByOrg(monthCondition);

            if (monthData != null && !monthData.isEmpty()) {
                for (WeeklyStatisticVO data:monthData) {
                    LineDataVO line = new LineDataVO();
                    line.setName(data.getOrgName());
                    boolean flag = false;
                    for (LineDataVO lineVO:yearDataList){
                        if (lineVO.getName().equals(line.getName())) {
                            line = lineVO;
                            flag = true;
                            break;
                        }
                    }
                    this.handleLine(yearDataList, month-1, data, line, flag);
                }
            }
        }

        // 结尾补0，以防万一
        for (LineDataVO lineVO:yearDataList) {
            List<BigDecimal> dataList = lineVO.getData();
            if (dataList.size()<12) {
                for (int i = dataList.size(); i < 12; i++) {
                    dataList.add(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }

        chartVO.setXAxisData(yearXAxis);
        chartVO.setLineSeriesData(yearDataList);
    }

}
