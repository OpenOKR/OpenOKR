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
    List<WeeklyStatisticVO> getStatisticByTask(WeeklyStaSearchVO condition) throws BusinessException;

    /**
     * 按产品类别查询周报饼图
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getWeeklyPieByTask(WeeklyStaSearchVO condition) throws BusinessException;

    /**
     * 按人员所属查询列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getStatisticByOrg(WeeklyStaSearchVO condition) throws BusinessException;

    /**
     * 按人员所属查询周报饼图 sql统计维度错误，考虑到页面上现在是直接用左侧列表的数据，所以懒得改了，有需要再说
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getWeeklyPieByOrg(WeeklyStaSearchVO condition) throws BusinessException;
}
