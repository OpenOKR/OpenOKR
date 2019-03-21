package org.openokr.task.service;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.db.service.IWeeklyStaDBService;
import org.openokr.task.request.WeeklyStaSearchVO;
import org.openokr.task.vo.WeeklyStatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<WeeklyStatisticVO> getStatisticByOrg(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getStatisticByOrg-按人员所属查询列表";
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

            return weeklyStaDBService.getStatisticByOrg(condition);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }
}
