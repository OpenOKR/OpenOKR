package org.openokr.index.service;


import com.zzheng.framework.exception.BusinessException;
import org.openokr.index.vo.ExecutionVO;
import org.openokr.manage.vo.ObjectivesVO;

/**
 * 首页service
 * Created by hjh on 2018/12/11.
 */
public interface IIndexService {

    /**
     * 获取首页-执行情况
     * @param objectivesVO
     * @return
     * @throws BusinessException
     */
    ExecutionVO execution(ObjectivesVO objectivesVO) throws BusinessException;
}
