package org.openokr.task.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.request.WeeklySearchVO;
import org.openokr.task.vo.WeeklyVO;

import java.util.List;

/**
 * 周报service
 * @author yuxinzh
 * @create 2019/3/4
 */
public interface IWeeklyManageService {

    /**
     * 根据条件查询周报列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyVO> getWeeklyList(WeeklySearchVO condition) throws BusinessException;

    /**
     * 批量新增周报
     * @param dailyList
     * @throws BusinessException
     */
    void insertWeeklyList(List<WeeklyVO> dailyList,String userId,String reportStartDayStr,String reportEndDayStr) throws BusinessException;
}
