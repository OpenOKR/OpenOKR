package org.openokr.db.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
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
     * page为null则查询全部
     * @param condition
     * @param page
     * @return
     * @throws BusinessException
     */
    List<WeeklyVO> getWeeklyList(WeeklySearchVO condition, Page page) throws BusinessException;

    /**
     * 根据条件统计周报分页数量
     * @param condition
     * @return
     * @throws BusinessException
     */
    Integer countWeeklyList(WeeklySearchVO condition) throws BusinessException;

    /**
     * 根据条件从日报数据中提炼出周报列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<WeeklyVO> getWeeklyListFromDaily(WeeklySearchVO condition) throws BusinessException;
}
