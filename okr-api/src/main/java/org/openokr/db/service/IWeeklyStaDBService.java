package org.openokr.db.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.request.WeeklyStaSearchVO;
import org.openokr.task.vo.WeeklyStatisticVO;

import java.util.List;

/**
 * 周报统计db-service
 * @author yuxinzh
 * @create 2019/3/20
 */
public interface IWeeklyStaDBService {

    /**
     * 按产品类别查询列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getStatisticByProduct(WeeklyStaSearchVO condition) throws BusinessException;

    /**
     * 按产品类别查询周报饼图
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getWeeklyPieByProduct(WeeklyStaSearchVO condition) throws BusinessException;

    /**
     * 按人员所属查询列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getStatisticByPerson(WeeklyStaSearchVO condition) throws BusinessException;

    /**
     * 按人员所属查询周报饼图
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getWeeklyPieByPerson(WeeklyStaSearchVO condition) throws BusinessException;
}
