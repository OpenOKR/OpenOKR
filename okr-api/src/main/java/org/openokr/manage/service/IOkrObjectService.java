package org.openokr.manage.service;


import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.LogVO;
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
     * 根据类型或者OKR列表
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getOkrListByType(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取个人OKR
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getPersonalOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取团队OKR
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getTeamOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取公司OKR
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getCompanyOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取OKR的历史操作记录
     * @param objectId
     * @param resultIds KR的所有ID
     * @return
     * @throws BusinessException
     */
    List<LogVO> getOperateRecordList(String objectId, List<String> resultIds) throws BusinessException;

    /**
     * 编辑目标
     * @param objectId
     * @return
     * @throws BusinessException
     */
    ObjectivesExtVO editObject(String objectId) throws BusinessException;

    /**
     * 保存目标
     * @param objectVO
     * @return
     * @throws BusinessException
     */
    ResponseResult saveObject(ObjectivesExtVO objectVO) throws BusinessException;

    /**
     * 删除目标
     * @param objectId 目标ID
     * @param userId
     * @return
     * @throws BusinessException
     */
    ResponseResult deleteObject(String objectId, String userId) throws BusinessException;


}