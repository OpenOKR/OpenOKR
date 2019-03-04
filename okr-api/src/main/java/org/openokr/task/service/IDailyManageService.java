package org.openokr.task.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.request.DailySearchVO;
import org.openokr.task.vo.DailyVO;

import java.util.List;

/**
 * 日报service
 * @author yuxinzh
 * @create 2019/3/1
 */
public interface IDailyManageService {

    /**
     * 根据条件查询日报列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<DailyVO> getDailyList(DailySearchVO condition) throws BusinessException;

    /**
     * 批量新增日报
     * 日报无更新操作
     * @param dailyList
     * @throws BusinessException
     */
    void insertDailyList(List<DailyVO> dailyList) throws BusinessException;
}
