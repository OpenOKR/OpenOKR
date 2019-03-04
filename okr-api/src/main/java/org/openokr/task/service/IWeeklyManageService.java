package org.openokr.task.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
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
     * 根据条件查询周报分页
     * @param condition
     * @param page
     * @return
     * @throws BusinessException
     */
    Page queryPage(WeeklySearchVO condition,Page page) throws BusinessException;

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

    /**
     * 从日报中提炼出周期内的周报对象列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyVO> getWeeklyListFromDaily(WeeklySearchVO condition) throws BusinessException;
}
