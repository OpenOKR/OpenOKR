package org.openokr.task.service;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.common.constant.WeeklyConstants;
import org.openokr.db.service.IWeeklyDBService;
import org.openokr.task.entity.WeeklyEntity;
import org.openokr.task.request.WeeklySearchVO;
import org.openokr.task.vo.WeeklyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yuxinzh
 * @create 2019/3/4
 */
@Service
@Transactional
public class WeeklyManageService extends BaseServiceImpl implements IWeeklyManageService {

    @Autowired
    private IWeeklyDBService weeklyDBService;

    @Override
    public Page queryPage(WeeklySearchVO condition, Page page) throws BusinessException {
        String methodName = "getDailyList-根据条件查询周报分页";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            if (page == null) {
                page = new Page();
            }

            int count = weeklyDBService.countWeeklyList(condition);
            if (count >= 0) {
                List<WeeklyVO> list = weeklyDBService.getWeeklyList(condition,page);
                page.setTotalRecord(count);
                page.setRecords(list);
            }

            return page;
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyVO> getWeeklyList(WeeklySearchVO condition) throws BusinessException {
        String methodName = "getWeeklyList-根据条件查询周报列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件为空");
            }

            return weeklyDBService.getWeeklyList(condition,null);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public void insertWeeklyList(List<WeeklyVO> weeklyList,String userId,String reportStartDayStr,String reportEndDayStr) throws BusinessException {
        String methodName = "insertWeeklyList-批量新增周报";
        try {
            if (weeklyList == null || weeklyList.isEmpty()) {
                throw new BusinessException("新增列表为空");
            }
            if (StringUtils.isBlank(userId)) {
                throw new BusinessException("用户ID为空");
            }

            // 先查询本周期是否有周报
            WeeklySearchVO searchVO = new WeeklySearchVO();
            searchVO.setReportUserId(userId);
            searchVO.setReportStartDateStrEquals(reportStartDayStr);
            searchVO.setReportEndDateStrEquals(reportEndDayStr);
            List<WeeklyVO> weeklyVOList = this.getWeeklyList(searchVO);
            if (weeklyVOList != null && !weeklyVOList.isEmpty()) {
                throw new BusinessException("本周已有周报");
            }

            // 插入列表
            for (WeeklyVO weeklyVO :weeklyList) {
                weeklyVO.setId(null);
                weeklyVO.setAuditStatus(WeeklyConstants.AUDIT_STATUS_WAITING);
                weeklyVO.setCreateTs(new Date());
                weeklyVO.setCreateUserId(userId);
                weeklyVO.setUpdateTs(new Date());
                weeklyVO.setUpdateUserId(userId);
                this.insertWeeklyData(weeklyVO);
            }
        } catch (BusinessException e) {
            logger.error("{} 失败，[weeklyList]->{}，[userId]->{},[reportStartDayStr]->{},[reportEndDayStr]->{}",methodName, JSON.toJSONString(weeklyList),reportStartDayStr,reportEndDayStr,e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[weeklyList]->{}，[userId]->{},[reportStartDayStr]->{},[reportEndDayStr]->{}",methodName, JSON.toJSONString(weeklyList),reportStartDayStr,reportEndDayStr,e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyVO> getWeeklyListFromDaily(WeeklySearchVO condition) throws BusinessException {
        String methodName = "getWeeklyListFromDaily-从日报中提炼出周期内的周报对象列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件为空");
            }
            if (StringUtils.isBlank(condition.getReportUserId())) {
                throw new BusinessException("用户ID为空");
            }
            if (StringUtils.isBlank(condition.getReportStartDateStr())) {
                throw new BusinessException("开始时间为空");
            }
            if (StringUtils.isBlank(condition.getReportEndDateStr())) {
                throw new BusinessException("结束时间为空");
            }

            return weeklyDBService.getWeeklyListFromDaily(condition);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    private void insertWeeklyData(WeeklyVO weeklyVO) throws BusinessException{
        String methodName = "insertWeeklyData-新增周报";
        try {
            if (weeklyVO == null ) {
                throw new BusinessException("新增对象为空");
            }
            if (StringUtils.isBlank(weeklyVO.getTaskId())) {
                throw new BusinessException("项目ID为空");
            }
            if (StringUtils.isBlank(weeklyVO.getRelTaskId())) {
                throw new BusinessException("关联项目ID为空");
            }
            if (weeklyVO.getReportStartDate() == null) {
                throw new BusinessException("开始时间为空");
            }
            if (weeklyVO.getReportEndDate() == null) {
                throw new BusinessException("结束时间为空");
            }
            if (weeklyVO.getDuration() == null) {
                throw new BusinessException("耗费总工时为空");
            }

            WeeklyEntity entity = new WeeklyEntity();
            this.insert(entity);
        } catch (BusinessException e) {
            logger.error("{} 失败，[weeklyVO]->{}",methodName, JSON.toJSONString(weeklyVO),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[weeklyVO]->{}",methodName, JSON.toJSONString(weeklyVO),e);
            throw new BusinessException(e);
        }
    }
}
