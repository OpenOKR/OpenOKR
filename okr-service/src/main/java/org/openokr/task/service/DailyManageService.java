package org.openokr.task.service;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.db.service.IDailyDBService;
import org.openokr.task.entity.DailyEntity;
import org.openokr.task.request.DailySearchVO;
import org.openokr.task.vo.DailyVO;
import org.openokr.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuxinzh
 * @create 2019/3/2
 */
@Service
@Transactional
public class DailyManageService extends BaseServiceImpl implements IDailyManageService {

    @Autowired
    private IDailyDBService dailyDBService;

    @Override
    public Page queryPage(DailySearchVO condition, Page page) throws BusinessException {
        String methodName = "getDailyList-根据条件查询日报列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            if (page == null) {
                page = new Page();
            }

            int count = dailyDBService.countDailyList(condition);
            if (count >= 0) {
                List<DailyVO> list = dailyDBService.getDailyList(condition,page);
                page.setTotalRecord(count);
                page.setRecords(list);
            }

            return page;
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw new BusinessException(e);
        }
    }

    @Override
    public List<DailyVO> getDailyList(DailySearchVO condition) throws BusinessException {
        String methodName = "getDailyList-根据条件查询日报列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            return dailyDBService.getDailyList(condition,null);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw new BusinessException(e);
        }
    }

    @Override
    public void insertDailyList(List<DailyVO> dailyList,String userId,String dateStr) throws BusinessException {
        String methodName = "insertDailyList-批量保存日报记录";
        try {
            if (dailyList == null || dailyList.isEmpty()){
                throw new BusinessException("没有需要保存的记录");
            }
            if (StringUtils.isBlank(userId)) {
                throw new BusinessException("用户ID为空");
            }
            if (StringUtils.isBlank(dateStr)) {
                throw new BusinessException("日报时间为空");
            }

            // 先查询是否已经保存过当日日报
            DailySearchVO searchVO = new DailySearchVO();
            searchVO.setReportDayStr(dateStr);
            searchVO.setReportUserId(userId);
            List<DailyVO> dailyVOList = this.getDailyList(searchVO);
            if (dailyVOList != null && !dailyVOList.isEmpty()) {
                throw new BusinessException("今日已填写过日报");
            }

            for (DailyVO dailyVO:dailyList) {
                dailyVO.setId(null);
                dailyVO.setReportDay(DateUtils.stringToDate(dateStr));
                dailyVO.setReportUserId(userId);
                dailyVO.setCreateUserId(userId);
                dailyVO.setCreateTs(new Date());
                dailyVO.setUpdateUserId(userId);
                dailyVO.setUpdateTs(new Date());
                this.insertDailyData(dailyVO);
            }
        } catch (BusinessException e) {
            logger.error("{} 失败，[dailyList]->{},[userId]->{},[dateStr]->{}",methodName, JSON.toJSONString(dailyList),userId,dateStr);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[dailyList]->{},[userId]->{},[dateStr]->{}",methodName, JSON.toJSONString(dailyList),userId,dateStr);
            throw new BusinessException(e);
        }
    }

    private void insertDailyData(DailyVO dailyVO) throws BusinessException{
        String methodName = "insertDailyData-保存日报记录";
        try {
            if (dailyVO == null){
                throw new BusinessException("保存对象为空");
            }
            if (StringUtils.isBlank(dailyVO.getTaskId())) {
                throw new BusinessException("项目ID为空");
            }
            if (dailyVO.getDuration() == null) {
                throw new BusinessException("报工时长为空");
            }

            DailyEntity entity = new DailyEntity();
            BeanUtils.copyProperties(dailyVO,entity);
            this.insert(entity);
        } catch (BusinessException e) {
            logger.error("{} 失败，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw new BusinessException(e);
        }
    }
}
