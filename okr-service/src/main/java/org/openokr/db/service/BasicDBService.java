package org.openokr.db.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.TeamUserRelaEntityCondition;
import org.openokr.manage.service.IOkrTeamService;
import org.openokr.manage.vo.TeamUserRelaVO;
import org.openokr.manage.vo.TeamsVO;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.UserVO;
import org.openokr.task.vo.SearchConditionVO;
import org.openokr.util.JSONCloneObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @author: cww
 * @DateTime: 2019/3/21 17:31
 */
@Service
@Transactional
public class BasicDBService extends BaseServiceImpl implements IBasicDBService {
    private static final String MAPPER_NAMSPACE = "org.openokr.task.sqlmapper.TaskManageMapper.";
    private static final String CONDITION = "condition";
    /**
     * @param conditionVO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<SearchConditionVO> getSearchCondition(SearchConditionVO conditionVO) throws BusinessException {
        try {
            String methodName = "getSearchCondition-根据条件查询全部报工搜索条件";
            if (conditionVO == null) {
                throw new BusinessException("查询条件对象为空");
            }
            Map<String,Object> params = Maps.newHashMap();
            params.put(CONDITION,conditionVO);
            List<SearchConditionVO> conditionVOList = this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE+"conditionQuerySql",params);
            return conditionVOList;
        } catch (BusinessException e) {
            logger.error("搜索条件查询异常 busi-error:{}-->[conditionVO]={}", e.getMessage(), JSONUtils.objectToString(conditionVO), e);
            throw e;
        } catch (Exception e) {
            logger.error("搜索条件查询异常 error:{}-->[conditionVO]={}", e.getMessage(), JSONUtils.objectToString(conditionVO), e);
            throw new BusinessException("搜索条件查询异常 失败");
        }
    }

    @Autowired
    IUserService userService;

    @Autowired
    IOkrTeamService okrTeamService;
    @Override
    public List<String> getUserIdListByAdminTeam(String userId) throws BusinessException {
        if (StringUtils.isEmpty(userId)){
            throw new BusinessException("用户查询参数为空");
        }
        UserVO userVO = new UserVO();
        userVO.setId(userId);
        List<UserVO> userVOList = userService.getUserRole(userVO);
        for (UserVO user:userVOList){
            if ("0".equals(user.getRoleType().substring(0,1))){
                logger.info("当前用户是管理员 userId:{}"+user.getId());
                //管理员返回所有任务
                //2020年1月3日17:28:53 管理员返回所有团队，不管是否有负责团队
                TeamsVO teamsVO = new TeamsVO();
                teamsVO.setOwnerId(user.getId());
                List<TeamsVO> teamsVOS  = null;
                if (teamsVOS==null){
                    logger.info("当前管理员用户查询负责的团队为空 - 将查询所有团队");
                    teamsVOS = okrTeamService.getAllTeams();
                }
                List<String> teamIdList = Lists.newArrayList();
                for (TeamsVO vo : teamsVOS){
                    teamIdList.add(vo.getId());
                }
                if (teamIdList.size() == 0){
                    return new ArrayList<>();
                }
                TeamUserRelaEntityCondition teamUserRelaEntityCondition =new TeamUserRelaEntityCondition();
                teamUserRelaEntityCondition.createCriteria().andTeamIdIn(teamIdList);
                List<TeamUserRelaVO> teamUserList = JSONCloneObject.cloneListObject(this.selectByCondition(teamUserRelaEntityCondition),TeamUserRelaVO.class);
                List<String> userIdList = Lists.newArrayList();
                for (TeamUserRelaVO vo : teamUserList){
                    userIdList.add(vo.getUserId());
                }
                return userIdList;
            }
        }
        return new ArrayList<>();
    }
}
