package org.openokr.db.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.vo.SearchConditionVO;

import java.util.List;

/**
 * 基础 service
 * @author yuxinzh
 * @create 2019/2/28
 */
public interface IBasicDBService {

    /**
     * @param conditionVO
     * @return
     * @throws BusinessException
     */
    List<SearchConditionVO> getSearchCondition(SearchConditionVO conditionVO) throws BusinessException;

}
