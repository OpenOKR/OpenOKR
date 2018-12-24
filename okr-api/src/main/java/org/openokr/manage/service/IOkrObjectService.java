package org.openokr.manage.service;


import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.openokr.sys.vo.UserVO;

import java.util.List;

/**
 * 目标管理service
 * Created by hjh on 2018/12/11.
 */
public interface IOkrObjectService {

    /**
     * 获取当前用户所有的OKR列表信息(包括个人、团队、公司的OKR)
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getAllOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 根据O获取KR所有的协同者
     * @param objectId
     * @param limitAmount 查询个数
     * @return
     */
    List<UserVO> getJoinUsersByObjectId(String objectId, Integer limitAmount);

    /**
     * 获取KR的参与人员
     * @param resultId
     * @param limitAmount
     * @return
     * @throws BusinessException
     */
    List<UserVO> getJoinUsersByResultId(String resultId, Integer limitAmount) throws BusinessException;

    /**
     * 获取个人OKR
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getPersonalOkrList(OkrObjectSearchVO searchVO) throws BusinessException;
}
