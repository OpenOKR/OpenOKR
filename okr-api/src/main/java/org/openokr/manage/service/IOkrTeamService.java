package org.openokr.manage.service;


import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.TeamsExtVO;
import org.openokr.manage.vo.TeamsVO;
import org.openokr.sys.vo.UserVO;
import org.openokr.task.request.TeamTaskSearchVO;

import java.util.List;

/**
 * 团队管理service
 * Created by hjh on 2018/12/24.
 */
public interface IOkrTeamService {

    /**
     * 获取用户的所有团队信息(不包括公司团队)
     * @param userId
     * @return
     * @throws BusinessException
     */
    List<TeamsExtVO> getTeamByUserId(String userId) throws BusinessException;

    /**
     * 根据用户ID/团队名获取团队信息(不包括公司团队)
     * @param teamTaskSearchVO
     * @return
     * @throws BusinessException
     */
    List<TeamsExtVO> getTeamByUserIdOrTeamName(TeamTaskSearchVO teamTaskSearchVO) throws BusinessException;

    /**
     * 获取用户关联的团队(影响团队或者参与的团队)
     * @param teamsVO
     * @return
     * @throws BusinessException
     */
    List<TeamsExtVO> getRelTeams(TeamsExtVO teamsVO) throws BusinessException;

    /**
     * 获取目标的影响团队
     * @param objectId
     * @return
     * @throws BusinessException
     */
    List<TeamsVO> getObjectTeamRel(String objectId) throws BusinessException;

    /**
     * 我创建的团队列表(排除公司团队)
     * @param userId
     * @return
     * @throws BusinessException
     */
    List<TeamsExtVO> createdTeam(String userId) throws BusinessException;

    /**
     * 保存团队信息
     * @param teamsVO
     * @return
     * @throws BusinessException
     */
    ResponseResult saveTeams(TeamsExtVO teamsVO) throws BusinessException;

    /**
     * 删除团队成员
     * @param userIds
     * @return
     * @throws BusinessException
     */
    ResponseResult deleteTeamUser(String teamId, List<String> userIds) throws BusinessException;
    /**
     * 解散团队
     * @param teamId
     * @return
     * @throws BusinessException
     */
    ResponseResult disbandTeam(String teamId) throws BusinessException;

    /**
     * 获取团队数据
     * @param id
     * @return
     */
    TeamsExtVO getByTeamId(String id);

    List<UserVO> getUsersByTeamId(String teamId);
}
