package org.openokr.manage.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;

import java.util.List;

/**
 * 地图管理service
 *
 */
public interface IOkrMapService {

    /**
     * 获取公司OKR
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getCompanyOkrList(OkrObjectSearchVO searchVO) throws BusinessException;


    /**
     * 根据目标ID获取所有状态为已确认的子目标
     * @param objectId
     * @return
     **/
    List<ObjectivesExtVO> getChildrenOkrList(String objectId) ;

}
