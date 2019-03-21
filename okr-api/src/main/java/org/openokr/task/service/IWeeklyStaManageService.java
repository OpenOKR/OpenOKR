package org.openokr.task.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.request.WeeklyStaSearchVO;
import org.openokr.task.vo.WeeklyStatisticVO;

import java.util.List;

/**
 * @author yuxinzh
 * @create 2019/3/21
 */
public interface IWeeklyStaManageService {

    /**
     * 按产品类别查询列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getStatisticByTask(WeeklyStaSearchVO condition) throws BusinessException;

    /**
     * 按人员所属查询列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyStatisticVO> getStatisticByOrg(WeeklyStaSearchVO condition) throws BusinessException;
}
