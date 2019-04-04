package org.openokr.db.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.task.vo.DailyVO;
import org.openokr.task.request.DailySearchVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 日报db-service
 * @author yuxinzh
 * @create 2019/2/28
 */
public interface IDailyDBService {

    /**
     * 根据条件查询日报列表
     * page为空则查询全部
     * @param condition
     * @param page
     * @return
     * @throws BusinessException
     */
    List<DailyVO> getDailyList(DailySearchVO condition, Page page) throws BusinessException;

    /**
     * 根据条件统计日报分页数量
     * @param condition
     * @return
     * @throws BusinessException
     */
    Integer countDailyList(DailySearchVO condition) throws BusinessException;

    /**
     * 统计任务占用总工时
     * @param taskId
     * @return
     * @throws BusinessException
     */
    BigDecimal getTotalWorkingHoursByTaskId(String taskId) throws BusinessException;
}
