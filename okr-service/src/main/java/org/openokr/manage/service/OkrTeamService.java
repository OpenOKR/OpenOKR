package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.manage.entity.TeamUserRelaEntity;
import org.openokr.manage.entity.TeamUserRelaEntityCondition;
import org.openokr.manage.entity.TeamsEntity;
import org.openokr.manage.vo.TeamsExtVO;
import org.openokr.manage.vo.TeamsVO;
import org.openokr.sys.vo.UserVO;
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
        responseResult.setMessage("团队已解散");
        return responseResult;
    }

    @Override
    public TeamsExtVO getByTeamId(String id) {
        return null;
    }
}