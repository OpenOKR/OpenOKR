package org.openokr.db.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.request.WeeklySearchVO;
import org.openokr.task.vo.WeeklyVO;

import java.util.List;

/**
 * 周报DB-service
 * @author yuxinzh
 * @create 2019/3/4
 */
public interface IWeeklyDBService {

    /**
     * 根据条件查询周报
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyVO> getWeeklyList(WeeklySearchVO condition) throws BusinessException;
}
