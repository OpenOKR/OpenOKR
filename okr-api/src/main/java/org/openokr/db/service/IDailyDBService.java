package org.openokr.db.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.vo.DailyVO;
import org.openokr.task.request.DailySearchVO;

import java.util.List;

/**
 * 日报db-service
 * @author yuxinzh
 * @create 2019/2/28
 */
public interface IDailyDBService {

    /**
     * 根据条件查询日报列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    List<DailyVO> getDailyList(DailySearchVO condition) throws BusinessException;

}
