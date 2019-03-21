package org.openokr.db.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.task.request.WeeklyStaSearchVO;
import org.openokr.task.vo.WeeklyStatisticVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author yuxinzh
 * @create 2019/3/20
 */
@Service
@Transactional
public class WeeklyStaDBService extends BaseServiceImpl implements IWeeklyStaDBService {

    private static final String MAPPER_NAMESPACE = "org.openokr.task.sqlmapper.WeeklyStaMapper.";
    private static final String CONDITION = "condition";

    @Override
    public List<WeeklyStatisticVO> getStatisticByTask(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getStatisticByTask-按产品类别查询列表";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getStatisticByTask",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyStatisticVO> getWeeklyPieByTask(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getWeeklyPieByTask-按产品类别查询周报饼图";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getWeeklyPieByTask",params);
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
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getStatisticByOrg",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyStatisticVO> getWeeklyPieByOrg(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getWeeklyPieByOrg-按人员所属查询周报饼图";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getWeeklyPieByOrg",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }
}
