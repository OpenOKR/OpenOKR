package org.openokr.manage.service;


import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.MessagesExtVO;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrMapVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.openokr.sys.vo.UserVO;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.sys.vo.request.TreeDataVO;
import org.openokr.task.vo.TaskKrInfoVO;

import java.util.List;

/**
 * 目标管理service
 * Created by hjh on 2018/12/11.
 */
public interface IOkrObjectService {

    /**
     * 获取当前用户所有的OKR列表信息(包括个人、团队、公司的OKR)
     *
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getAllOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 根据O获取KR所有的协同者
     *
     * @param objectId
     * @param limitAmount 查询个数
     * @return
     */
    List<UserVO> getJoinUsersByObjectId(String objectId, Integer limitAmount);

    /**
     * 根据类型获取OKR列表
     *
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getOkrListByType(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取个人OKR
     *
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getPersonalOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取团队OKR
     *
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getTeamOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取公司OKR
     *
     * @param searchVO
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getCompanyOkrList(OkrObjectSearchVO searchVO) throws BusinessException;

    /**
     * 获取目标详情
     *
     * @param objectId
     * @return
     * @throws BusinessException
     */
    ObjectivesExtVO getObjectById(String objectId) throws BusinessException;

    /**
     * 保存目标
     *
     * @param objectVO
     * @return
     * @throws BusinessException
     */
    ResponseResult saveObject(ObjectivesExtVO objectVO) throws BusinessException;

    /**
     * 删除目标
     *
     * @param objectId 目标ID
     * @param userId
     * @return
     * @throws BusinessException
     */
    ResponseResult deleteObject(String objectId, String userId) throws BusinessException;

    /**
     * 获取父目标下拉数据
     *
     * @param userId
     * @param type
     * @return
     * @throws BusinessException
     */
    List<ObjectivesExtVO> getParentObject(String userId, String type) throws BusinessException;

    /**
     * 目标审核提交
     *
     * @param objectId
     * @param userVOExt
     * @return
     */
    ResponseResult auditSubmit(String objectId, UserVOExt userVOExt);

    /**
     * 目标审核（同步处理消息状态）
     *
     * @param messagesExtVO
     * @param currentUserId
     * @return
     */
    ResponseResult auditConfirm(MessagesExtVO messagesExtVO, String currentUserId);

    /**
     * 获取当前用户所有的OKR树状接口信息
     * @param currentUserId
     * @return
     * @throws BusinessException
     */
    List<TreeDataVO> findAllOkrTreeData(String currentUserId) throws BusinessException;

    /**
     * 获取任务目标列表
     * @param taskId
     * @param type
     * @return
     * @throws BusinessException
     */
    List<TaskKrInfoVO> getTaskObjectList(String taskId, String type)  throws BusinessException;


    /**
     * 获取目标协同人数
     * @param krId
     * @return
     * @throws BusinessException
     */
    Integer countObjectRelUserNum(String krId)  throws BusinessException;

    /**
     * 获取okr地图
     * @param timeSessionId
     * @return
     * @throws BusinessException
     */
    OkrMapVO getMap(String timeSessionId) throws BusinessException;

}
