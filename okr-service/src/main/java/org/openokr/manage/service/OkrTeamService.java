package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.TeamUserRelaEntity;
import org.openokr.manage.entity.TeamUserRelaEntityCondition;
import org.openokr.manage.entity.TeamsEntity;
import org.openokr.manage.vo.TeamsExtVO;
import org.openokr.manage.vo.TeamsSearchVO;
import org.openokr.manage.vo.TeamsVO;
import org.openokr.sys.vo.UserVO;
import org.openokr.task.request.TeamTaskSearchVO;
import org.openokr.task.vo.TeamTaskCountInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrTeamService extends BaseServiceImpl implements IOkrTeamService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrTeamMapper";

    @Override
    public List<TeamsExtVO> getTeamByUserId(String userId) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<TeamsExtVO> teamsVOList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getTeamByUserId", params);
        return teamsVOList;
    }

    @Override
    public List<TeamsExtVO> getTeamByUserIdOrTeamName(TeamTaskSearchVO teamTaskSearchVO) throws BusinessException {
        try{
            if(teamTaskSearchVO==null){
                throw new BusinessException("查询参数为空，请确认!");
            }
            if(StringUtils.isBlank(teamTaskSearchVO.getUserId())){
                throw new BusinessException("用户ID为空，请确认!");
            }
            //2:获取用户的所有团队信息(不包括公司团队)
            Map<String, Object> params = new HashMap<>();
            params.put("userId", teamTaskSearchVO.getUserId());
            params.put("teamName", teamTaskSearchVO.getSearchKey());
            return this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getTeamByUserIdOrTeamName", params);
        } catch (BusinessException e) {
            logger.error("获取用户负责团队任务报工统计信息 busi-error:{}-->[teamTaskSearchVO]={}", e.getMessage(), JSONUtils.objectToString(teamTaskSearchVO), e);
            throw e;
        } catch (Exception e) {
            logger.error("获取用户负责团队任务报工统计信息 error:{}-->[teamTaskSearchVO]={}", e.getMessage(), JSONUtils.objectToString(teamTaskSearchVO), e);
            throw new BusinessException("获取用户负责团队任务报工统计信息 失败");
        }
    }

    @Override
    public List<TeamsExtVO> getRelTeams(TeamsExtVO teamsVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", teamsVO.getUserId());
        params.put("type", teamsVO.getType());
        List<TeamsExtVO> teamsVOList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getRelTeams", params);
        return teamsVOList;
    }

    @Override
    public List<TeamsVO> getObjectTeamRel(String objectId) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        List<TeamsVO> relTeams = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getObjectTeamRel", params);
        return  relTeams;
    }

    @Override
    public List<TeamsExtVO> createdTeam(String userId) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<TeamsExtVO> teamsVOList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getCreatedTeam", params);
        return teamsVOList;
    }

    @Override
    public ResponseResult saveTeams(TeamsExtVO teamsVO) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        String teamId = teamsVO.getId();
        String userId = teamsVO.getUserId();
        if (StringUtils.isEmpty(teamId)) { //新增
            TeamsEntity entity = new TeamsEntity();
            BeanUtils.copyBean(teamsVO, entity);
            entity.setOwnerId(userId);
            entity.setType("2");//其他团队
            entity.setCreateUserId(userId);
            entity.setCreateTs(new Date());
            this.insert(entity);
            teamId = entity.getId();
        } else { //更新
            TeamsEntity entity = this.selectByPrimaryKey(TeamsEntity.class, teamId);
            entity.setName(teamsVO.getName());
            entity.setDescription(teamsVO.getDescription());
            entity.setParentId(teamsVO.getParentId());
            entity.setIcon(teamsVO.getIcon());
            entity.setUpdateTs(new Date());
            entity.setUpdateUserId(userId);
            this.update(entity);

            // 更新团队成员
            TeamUserRelaEntityCondition userRelCondition = new TeamUserRelaEntityCondition();
            userRelCondition.createCriteria().andTeamIdEqualTo(teamId);
            List<TeamUserRelaEntity> teamUserRelList = this.selectByCondition(userRelCondition);
            // 先删除旧数据
            if (teamUserRelList !=null && teamUserRelList.size()>0) {
                this.delete(teamUserRelList);
            }
        }
        // 新增团队成员
        if (teamsVO.getTeamRelUsers() != null && teamsVO.getTeamRelUsers().size()>0) {
            List<TeamUserRelaEntity> userRelList = new ArrayList<>();
            for (UserVO userVO : teamsVO.getTeamRelUsers()) {
                TeamUserRelaEntity relEntity = new TeamUserRelaEntity();
                relEntity.setTeamId(teamId);
                relEntity.setUserId(userVO.getId());
                relEntity.setCreateTs(new Date());
                relEntity.setCreateUserId(userId);
                userRelList.add(relEntity);
            }
            // 当前用户也要作为团队成员添加进去
            TeamUserRelaEntity currentTeamUser = new TeamUserRelaEntity();
            currentTeamUser.setTeamId(teamId);
            currentTeamUser.setUserId(userId);
            currentTeamUser.setCreateTs(new Date());
            currentTeamUser.setCreateUserId(userId);
            userRelList.add(currentTeamUser);
            this.insertList(userRelList);
        }
        responseResult.setMessage("保存成功");
        return responseResult;
    }

    @Override
    public ResponseResult deleteTeamUser(String teamId, List<String> userIds) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        //删除团队成员
        TeamUserRelaEntityCondition userRelCondition = new TeamUserRelaEntityCondition();
        userRelCondition.createCriteria().andTeamIdEqualTo(teamId).andUserIdIn(userIds);
        this.deleteByCondition(userRelCondition);
        responseResult.setMessage("团队成员已更新");
        return responseResult;
    }

    @Override
    public ResponseResult disbandTeam(String teamId) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        //1、删除团队
        this.deleteByPrimaryKey(TeamsEntity.class, teamId);
        //2、删除团队成员
        TeamUserRelaEntityCondition userRelCondition = new TeamUserRelaEntityCondition();
        userRelCondition.createCriteria().andTeamIdEqualTo(teamId);
        this.deleteByCondition(userRelCondition);
        responseResult.setMessage("团队解散成功！");
        return responseResult;
    }

    @Override
    public TeamsExtVO getByTeamId(String id) {
        TeamsEntity entity = this.getMyBatisDao().selectByPrimaryKey(TeamsEntity.class, id);
        if (entity == null) {
            return new TeamsExtVO();
        }
        TeamsExtVO teamsExtVO = BeanUtils.copyToNewBean(entity, TeamsExtVO.class);
        if (!teamsExtVO.getId().equals(teamsExtVO.getParentId())) {
            TeamsEntity parent = this.getMyBatisDao().selectByPrimaryKey(TeamsEntity.class, teamsExtVO.getParentId());
            if (parent !=null) {
                teamsExtVO.setParentName(parent.getName());
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("teamId", teamsExtVO.getId());
        List<UserVO> userVOList = this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + ".findUserListByTeamId", params);
        List<String> userVOIds = new ArrayList<>();
        for (UserVO userVO : userVOList) {
            userVOIds.add(userVO.getId());
        }
        teamsExtVO.setTeamRelUsers(userVOList);
        teamsExtVO.setTeamRelUserIds(userVOIds);
        return teamsExtVO;
    }

    @Override
    public List<UserVO> getUsersByTeamId(String teamId) {
        if (StringUtils.isBlank(teamId)) {
            return null;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("teamId", teamId);
        return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + ".findUserListByTeamId", params);
    }

    @Override
    public TeamTaskCountInfoVO getTeamTaskCountInfo(TeamsSearchVO teamsSearchVO) throws BusinessException {
        try {
            if(teamsSearchVO == null){
                throw new BusinessException("查询参数为空");
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(teamsSearchVO.getId())){
                throw new BusinessException("查询参数团队ID为空");
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("teamId",teamsSearchVO.getId());
            paramMap.put("queryStartTime",teamsSearchVO.getQueryStartTime());
            paramMap.put("queryEndTime",teamsSearchVO.getQueryEndTime());
            return this.getMyBatisDao().selectOneBySql(MAPPER_NAMESPACE+".getTeamTaskCountInfo",paramMap);
        } catch (BusinessException e) {
            logger.error(" 获取团队成员数、累计占用工时、关联任务数 busi-error:{}-->[teamsSearchVO]={}", e.getMessage(),JSONUtils.objectToString(teamsSearchVO), e);
            throw e;
        } catch (Exception e) {
            logger.error(" 获取团队成员数、累计占用工时、关联任务数 error:{}-->[teamsSearchVO]={}", e.getMessage(), JSONUtils.objectToString(teamsSearchVO), e);
            throw new BusinessException(" 获取团队成员数、累计占用工时、关联任务数 失败");
        }
    }

    @Override
    public List<TeamsVO> getTeamListByUserOrType(TeamsVO teamsVO) throws BusinessException {
        try {
            if(teamsVO == null){
                throw new BusinessException("查询参数为空");
            }
            Map<String,Object> paramMap = new HashMap<>();
            if (StringUtils.isNotEmpty(teamsVO.getOwnerId())){
                paramMap.put("userId",teamsVO.getOwnerId());
            }else {
                paramMap.put("userId",null);
            }
            if (StringUtils.isNotEmpty(teamsVO.getType())){
                paramMap.put("type",teamsVO.getType());
            }else {
                paramMap.put("type",null);
            }
            return this.getMyBatisDao().selectOneBySql(MAPPER_NAMESPACE+".getAllTeamByUserIdOrType",paramMap);
        } catch (BusinessException e) {
            logger.error(" 根据用户或类型查询团队列表 busi-error:{}-->[teamsSearchVO]={}", e.getMessage(),JSONUtils.objectToString(teamsVO), e);
            throw e;
        } catch (Exception e) {
            logger.error(" 根据用户或类型查询团队列表 error:{}-->[teamsSearchVO]={}", e.getMessage(), JSONUtils.objectToString(teamsVO), e);
            throw new BusinessException(" 根据用户或类型查询团队列表 失败");
        }
    }
}