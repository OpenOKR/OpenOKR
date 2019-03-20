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
    public List<WeeklyStatisticVO> getStatisticByProduct(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getStatisticByProduct-按产品类别查询列表";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getStatisticByProduct",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyStatisticVO> getWeeklyPieByProduct(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getWeeklyPieByProduct-按产品类别查询周报饼图";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getWeeklyPieByProduct",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyStatisticVO> getStatisticByPerson(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getStatisticByPerson-按人员所属查询列表";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getStatisticByPerson",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }

    @Override
    public List<WeeklyStatisticVO> getWeeklyPieByPerson(WeeklyStaSearchVO condition) throws BusinessException {
        String methodName = "getWeeklyPieByPerson-按人员所属查询周报饼图";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getWeeklyPieByPerson",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition),e);
            throw new BusinessException(e);
        }
    }
}
