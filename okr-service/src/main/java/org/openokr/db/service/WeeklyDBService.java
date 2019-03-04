package org.openokr.db.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.task.request.DailySearchVO;
import org.openokr.task.request.WeeklySearchVO;
import org.openokr.task.vo.DailyVO;
import org.openokr.task.vo.WeeklyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author yuxinzh
 * @create 2019/3/1
 */
@Service
@Transactional
public class WeeklyDBService extends BaseServiceImpl implements IWeeklyDBService {

    private static final String MAPPER_NAMESPACE = "org.openokr.task.sqlmapper.WeeklyMapper.";
    private static final String CONDITION = "condition";

    @Override
    public List<WeeklyVO> getWeeklyList(WeeklySearchVO condition) throws BusinessException {
        String methodName = "getWeeklyList-根据条件查询周报列表";
        try {
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,condition);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + "getWeeklyList",params);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw new BusinessException(e);
        }
    }
}
