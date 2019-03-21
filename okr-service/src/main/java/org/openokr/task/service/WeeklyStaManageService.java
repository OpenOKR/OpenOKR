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
import org.openokr.task.vo.chart.PieDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
                    // 月 todo
                    // 先获取1号所在周的周一，按周取数据，然后加七天继续，直到周日日期大于等于本月最后一天
                    // 对结果进行循环，然后分组
                    break;
                case WeeklyStatisticConstants.SEARCH_TYPE_YEAR:
                    // 年 todo
                    // 按月取数据，然后加一个月继续，直到十二月为止
                    // 对结果进行循环，然后分组
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
                    // 周 饼图
                    List<WeeklyStatisticVO> pieDataList = weeklyStaDBService.getWeeklyPieByOrg(condition);
                    this.handlePieData(chartVO, pieDataList);
                    break;
                case WeeklyStatisticConstants.SEARCH_TYPE_MONTH:
                    // 月 todo
                    break;
                case WeeklyStatisticConstants.SEARCH_TYPE_YEAR:
                    // 年 todo
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
            }
        }
        chartVO.setPieSeriesData(dataList);
    }
}
