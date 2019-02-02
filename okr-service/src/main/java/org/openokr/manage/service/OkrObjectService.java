package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import org.apache.commons.collections.ListUtils;
import org.openokr.application.framework.service.OkrBaseService;
import org.openokr.application.utils.GetChangeDateUtil;
import org.openokr.manage.entity.CheckinsEntity;
import org.openokr.manage.entity.CheckinsEntityCondition;
import org.openokr.manage.entity.LogEntity;
import org.openokr.manage.entity.LogEntityCondition;
import org.openokr.manage.entity.MessagesEntity;
import org.openokr.manage.entity.ObjectLabelRelaEntity;
import org.openokr.manage.entity.ObjectLabelRelaEntityCondition;
import org.openokr.manage.entity.ObjectTeamRelaEntity;
import org.openokr.manage.entity.ObjectTeamRelaEntityCondition;
import org.openokr.manage.entity.ObjectivesEntity;
import org.openokr.manage.entity.ObjectivesEntityCondition;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.entity.ResultsEntityCondition;
import org.openokr.manage.entity.TeamsEntity;
import org.openokr.manage.enumerate.MessageMarkEnum;
import org.openokr.manage.enumerate.MessageTypeEnum;
import org.openokr.manage.enumerate.ObjectivesStatusEnum;
import org.openokr.manage.enumerate.ObjectivesTypeEnum;
import org.openokr.manage.vo.CheckinsExtVO;
import org.openokr.manage.vo.LabelVO;
import org.openokr.manage.vo.LogVO;
import org.openokr.manage.vo.MessagesExtVO;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.openokr.manage.vo.ResultsExtVO;
import org.openokr.manage.vo.TeamsVO;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.OrganizationVOExt;
import org.openokr.sys.vo.UserVO;
import org.openokr.sys.vo.UserVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrObjectService extends OkrBaseService implements IOkrObjectService {

    @Autowired
    private IOkrTeamService okrTeamService;

    @Autowired
    private IOkrLabelService okrLabelService;

    @Autowired
    private IOkrResultService okrResultService;

    @Autowired
    private IUserService userService;

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrObjectMapper";

    @Override
    public List<ObjectivesExtVO> getAllOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", searchVO.getUserId());
        params.put("limitAmount", searchVO.getLimitAmount());
        params.put("timeSessionId", searchVO.getTimeSessionId());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getAllOkrList", params);
        if (objectivesExtList != null && objectivesExtList.size()>0) {
            for (ObjectivesExtVO objectivesExtVO : objectivesExtList) {
                List<UserVO> userList = this.getJoinUsersByObjectId(objectivesExtVO.getId(), searchVO.getLimitAmount());
                if (userList != null) {
                    objectivesExtVO.setJoinUsers(userList);
                }
            }
        }
        return objectivesExtList;
    }

    @Override
    public List<UserVO> getJoinUsersByObjectId(String objectId, Integer limitAmount) {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        params.put("limitAmount", limitAmount);
        List<UserVO> joinUsers = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getJoinUsersByObjectId", params);
        return joinUsers;
    }

    @Override
    public List<ObjectivesExtVO> getOkrListByType(OkrObjectSearchVO searchVO) throws BusinessException {
        List<ObjectivesExtVO> objectivesExtList = new ArrayList<>();
        if (ObjectivesTypeEnum.TYPE_1.getCode().equals(searchVO.getType())) { //个人OKR
            objectivesExtList = this.getPersonalOkrList(searchVO);
        } else if (ObjectivesTypeEnum.TYPE_2.getCode().equals(searchVO.getType())) { //团队OKR
            objectivesExtList = this.getTeamOkrList(searchVO);
        } else if (ObjectivesTypeEnum.TYPE_3.getCode().equals(searchVO.getType())) { //公司OKR
            objectivesExtList = this.getCompanyOkrList(searchVO);
        }
        // 设置KR信息
        this.setKrInfo(objectivesExtList, searchVO);
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getPersonalOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", searchVO.getUserId());
        params.put("keyword", searchVO.getKeyword());
        params.put("objectId", searchVO.getObjectId());//查看OKR的详情时才需要传该参数
        params.put("executeStatus", searchVO.getExecuteStatus());
        params.put("timeSessionId", searchVO.getTimeSessionId());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getPersonalOkrList", params);
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getTeamOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", searchVO.getUserId());
        params.put("teamId", searchVO.getTeamId());
        params.put("objectId", searchVO.getObjectId());//查看OKR的详情时才需要传该参数
        params.put("keyword", searchVO.getKeyword());
        params.put("executeStatus", searchVO.getExecuteStatus());
        params.put("timeSessionId", searchVO.getTimeSessionId());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getTeamOkrList", params);
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getCompanyOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", searchVO.getObjectId());//查看OKR的详情时才需要传该参数
        params.put("keyword", searchVO.getKeyword());
        params.put("executeStatus", searchVO.getExecuteStatus());
        params.put("timeSessionId", searchVO.getTimeSessionId());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getCompanyOkrList", params);
        return objectivesExtList;
    }

    @Override
    public ObjectivesExtVO getObjectById(String objectId) throws BusinessException {
        ObjectivesExtVO objectVO = new ObjectivesExtVO();
        ObjectivesEntity entity = this.selectByPrimaryKey(ObjectivesEntity.class, objectId);
        if (entity == null) {
            return null;
        }
        BeanUtils.copyBean(entity, objectVO);
        List<ObjectivesExtVO> list = new ArrayList<>();
        list.add(objectVO);

        // KR列表
        OkrObjectSearchVO searchVO = new OkrObjectSearchVO();
        searchVO.setObjectId(objectId);
        this.setKrInfo(list, searchVO);
        List<String> resultIds = new ArrayList<>();
        if (!objectVO.getResultsExtList().isEmpty()) {
            for (ResultsExtVO resultsExtVO : objectVO.getResultsExtList()) {
                resultIds.add(resultsExtVO.getId());
            }
        }
        // 历史操作，每周更新
        this.setOperateRecordInfo(objectVO, resultIds);

        // 所属团队
        TeamsEntity teamsEntity = this.selectByPrimaryKey(TeamsEntity.class, objectVO.getTeamId());
        objectVO.setTeamName(teamsEntity.getName());

        // 父目标
        if (StringUtils.isNotEmpty(objectVO.getParentId())) {
            ObjectivesEntity parentEntity = this.selectByPrimaryKey(ObjectivesEntity.class, objectVO.getParentId());
            objectVO.setParentName(parentEntity.getName());
        }

        // 获取影响团队
        List<TeamsVO> relTeams = okrTeamService.getObjectTeamRel(objectId);
        objectVO.setRelTeams(relTeams);

        // 获取目标关联标签
        List<LabelVO> relLabels = okrLabelService.getObjectLabelRel(objectId);
        objectVO.setRelLabels(relLabels);

        return objectVO;
    }

    @Override
    public ResponseResult saveObject(ObjectivesExtVO objectVO) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        String objectId = objectVO.getId();
        String userId = objectVO.getCreateUserId();
        ObjectivesEntity targetEntity; ObjectivesEntity originalEntity = new ObjectivesEntity();

        if (StringUtils.isEmpty(objectId)) {
            targetEntity = new ObjectivesEntity();
            BeanUtils.copyBean(objectVO, targetEntity);
        } else {
            targetEntity = this.selectByPrimaryKey(ObjectivesEntity.class, objectId);
            originalEntity = BeanUtils.copyToNewBean(targetEntity, ObjectivesEntity.class);
            BeanUtils.copyBean(objectVO, targetEntity);
        }

        // 获取object所属团队责任人，若修改的目标所属团队责任人和当前用户一致，直接将状态设置为已确认
        TeamsEntity teamsEntity = this.selectByPrimaryKey(TeamsEntity.class, objectVO.getTeamId());
        // 设置状态，当类型是 团队或公司时，不需要审核，其他情况下一律设置成未提交
        if (targetEntity.getType().equals(ObjectivesTypeEnum.TYPE_2.getCode()) || targetEntity.getType().equals(ObjectivesTypeEnum.TYPE_3.getCode())) {
            targetEntity.setStatus(ObjectivesStatusEnum.STATUS_3.getCode());
        } else if (teamsEntity.getOwnerId().equals(userId)) {
            targetEntity.setStatus(ObjectivesStatusEnum.STATUS_3.getCode());
        } else {
            targetEntity.setStatus(ObjectivesStatusEnum.STATUS_1.getCode());//一旦有修改,目标就要变成未提交状态
        }

        if (StringUtils.isEmpty(objectId)) { //新增
            targetEntity.setTimeSessionId(getCurrentTimeSessionId());
            targetEntity.setOwnerId(userId);
            targetEntity.setVisibility("1");//默认公开
            targetEntity.setDelFlag("0");//删除状态:否
            targetEntity.setCreateTs(new Date());
            this.insert(targetEntity);
        } else { //更新
            targetEntity.setUpdateTs(new Date());
            targetEntity.setUpdateUserId(userId);
            this.update(targetEntity);

            // 更新影响团队
            ObjectTeamRelaEntityCondition teamRelaCondition = new ObjectTeamRelaEntityCondition();
            teamRelaCondition.createCriteria().andObjectIdEqualTo(objectId);
            List<ObjectTeamRelaEntity> teamRelaList = this.selectByCondition(teamRelaCondition);
            // 先删除旧数据
            if (teamRelaList !=null && teamRelaList.size()>0) {
                this.delete(teamRelaList);
            }

            // 更新目标关联标签
            ObjectLabelRelaEntityCondition labelRelCondition = new ObjectLabelRelaEntityCondition();
            labelRelCondition.createCriteria().andObjectIdEqualTo(objectId);
            List<ObjectLabelRelaEntity> labelsRelList = this.selectByCondition(labelRelCondition);
            // 先删除旧数据
            if (labelsRelList !=null && labelsRelList.size()>0) {
                this.delete(labelsRelList);
            }
            // 更新检查变化的字段，写入历史操作日志
            setObjectLogInfo(originalEntity, targetEntity, teamRelaList, labelsRelList, objectVO.getRelTeams(), objectVO.getRelLabels());
        }

        objectId = targetEntity.getId();
        // 新增影响团队
        if (objectVO.getRelTeams() != null && objectVO.getRelTeams().size()>0) {
            List<ObjectTeamRelaEntity> teamsEntityList = new ArrayList<>();
            for (TeamsVO teamsVO : objectVO.getRelTeams()) {
                ObjectTeamRelaEntity relaEntity = new ObjectTeamRelaEntity();
                relaEntity.setObjectId(objectId);
                relaEntity.setTeamId(teamsVO.getId());
                relaEntity.setCreateTs(new Date());
                relaEntity.setCreateUserId(userId);
                teamsEntityList.add(relaEntity);
            }
            this.insertList(teamsEntityList);
        }

        // 新增目标关联标签
        if (objectVO.getRelLabels() != null && objectVO.getRelLabels().size()>0) {
            List<ObjectLabelRelaEntity> labelsRelList = new ArrayList<>();
            for (LabelVO labelVO : objectVO.getRelLabels()) {
                ObjectLabelRelaEntity relaEntity = new ObjectLabelRelaEntity();
                relaEntity.setObjectId(objectId);
                relaEntity.setLabelId(labelVO.getId());
                relaEntity.setCreateTs(new Date());
                relaEntity.setCreateUserId(userId);
                labelsRelList.add(relaEntity);
            }
            this.insertList(labelsRelList);
        }

        responseResult.setMessage("保存成功");
        return responseResult;
    }

    @Override
    public ResponseResult deleteObject(String objectId, String userId) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        //1、如果该目标已经是别人的父目标,则无法删除该目标
        ObjectivesEntityCondition parentCondition = new ObjectivesEntityCondition();
        parentCondition.createCriteria().andParentIdEqualTo(objectId);
        List<ObjectivesEntity> parentList = this.selectByCondition(parentCondition);

        int judge=0;
        for (ObjectivesEntity childrenEntity : parentList)
            if (childrenEntity.getDelFlag().equals("0")) {
                judge = 1;//有一个未删除就设为1
                break;
            }


        if (parentList !=null && parentList.size()>0 && judge==1) {
            responseResult.setMessage("该目标还存在子目标,无法删除");
            responseResult.setSuccess(false);
            return responseResult;
        }
        ObjectivesEntity entity = this.selectByPrimaryKey(ObjectivesEntity.class, objectId);
        entity.setDelFlag("1");//设为已删除状态
        entity.setUpdateTs(new Date());
        entity.setUpdateUserId(userId);
        this.update(entity);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @Override
    public List<ObjectivesExtVO> getParentObject(String userId, String type) throws BusinessException {
        OkrObjectSearchVO searchVO = new OkrObjectSearchVO();
        searchVO.setUserId(userId);
        List<ObjectivesExtVO> objectivesExtList = new ArrayList<>();
        if (ObjectivesTypeEnum.TYPE_2.getCode().equals(type)) {//获取团队目标
            objectivesExtList = this.getTeamOkrList(searchVO);
        } else if (ObjectivesTypeEnum.TYPE_3.getCode().equals(type)) {//获取公司目标
            objectivesExtList = this.getCompanyOkrList(searchVO);
        }
        return objectivesExtList;
    }

    @Override
    public ResponseResult auditSubmit(String objectId, UserVOExt currentUser) {
        ObjectivesEntity entity = this.selectByPrimaryKey(ObjectivesEntity.class, objectId);
        if (entity.getStatus().equals(ObjectivesStatusEnum.STATUS_2.getCode())) {
            return new ResponseResult(false, null, "目标正在审核");
        }
        if (entity.getStatus().equals(ObjectivesStatusEnum.STATUS_3.getCode())) {
            return new ResponseResult(false, null, "目标已审核通过，请勿重复提交");
        }
        // 具体的提交审核后需要处理的业务逻辑
        entity.setStatus(ObjectivesStatusEnum.STATUS_2.getCode());
        entity.setUpdateUserId(currentUser.getId());
        entity.setUpdateTs(new Date());
        this.update(entity);

        // 获取kr列表
        ObjectivesExtVO objectivesExtVO = this.getObjectById(objectId);
        StringBuilder content = new StringBuilder().append(currentUser.getRealName()).append(" 提交目标审核请求<br/>" +
                "目标名：").append(objectivesExtVO.getName()).append("<br/>");
        if (objectivesExtVO.getResultsExtList() != null && objectivesExtVO.getResultsExtList().size() > 0) {
            content.append("关键结果：<br/>");
            for (int i = 0; i < objectivesExtVO.getResultsExtList().size(); i++) {
                ResultsExtVO resultsExtVO = objectivesExtVO.getResultsExtList().get(i);
                content.append("K").append((i+1)).append(".").append(resultsExtVO.getName()).append("<br/>");
            }
        }

        // 获取团队负责人
        UserVOExt userVOExt = userService.getTeamOwnerUserByTeamId(objectivesExtVO.getTeamId());

        // 新建审核消息
        MessagesEntity messagesEntity = new MessagesEntity();
        messagesEntity.setTitle("目标审核请求");
        messagesEntity.setUserId(userVOExt.getId());
        messagesEntity.setContent(content.toString());
        messagesEntity.setType(MessageTypeEnum.TYPE_2.getCode());
        messagesEntity.setTargetId(entity.getId());
        messagesEntity.setIsProcessed("0");
        messagesEntity.setIsRead("0");
        messagesEntity.setMark(MessageMarkEnum.MARK_4.getCode());
        messagesEntity.setCreateUserId(userVOExt.getId());
        messagesEntity.setCreateTs(new Date());
        this.save(messagesEntity);
        return new ResponseResult(true, null, "提交成功");
    }

    @Override
    public ResponseResult auditConfirm(MessagesExtVO messagesExtVO, String currentUserId) {
        ObjectivesEntity entity = this.selectByPrimaryKey(ObjectivesEntity.class, messagesExtVO.getTargetId());
        if (messagesExtVO.getRadio().equals("0")) {
            entity.setStatus(ObjectivesStatusEnum.STATUS_4.getCode());
        } else {
            entity.setStatus(ObjectivesStatusEnum.STATUS_3.getCode());
        }
        // 更新object
        entity.setUpdateUserId(currentUserId);
        entity.setUpdateTs(new Date());
        this.update(entity);
        // 原消息进行更新
        MessagesEntity messagesEntity = this.selectByPrimaryKey(MessagesEntity.class, messagesExtVO.getId());
        messagesEntity.setIsProcessed("1");
        messagesEntity.setIsRead("1");
        messagesEntity.setUpdateUserId(currentUserId);
        messagesEntity.setUpdateTs(new Date());
        this.update(messagesEntity);
        // 新建消息，将同意或不同意封装成消息发回提交审核用户
        MessagesEntity newMessageEntity = new MessagesEntity();
        newMessageEntity.setTitle("目标审核" + ObjectivesStatusEnum.getByCode(entity.getStatus()).getName());
        newMessageEntity.setUserId(entity.getOwnerId());
        newMessageEntity.setContent(messagesExtVO.getContent());
        newMessageEntity.setType(MessageTypeEnum.TYPE_4.getCode());
        newMessageEntity.setTargetId(entity.getId());
        newMessageEntity.setIsProcessed("1");
        newMessageEntity.setIsRead("0");
        newMessageEntity.setMark(entity.getStatus().equals(ObjectivesStatusEnum.STATUS_3.getCode()) ? MessageMarkEnum.MARK_2.getCode() : MessageMarkEnum.MARK_3.getCode());
        newMessageEntity.setCreateUserId(currentUserId);
        newMessageEntity.setCreateTs(new Date());
        this.save(newMessageEntity);
        return new ResponseResult(true, null, "操作成功");
    }

    /**
     * 设置KR信息
     */
    private void setKrInfo(List<ObjectivesExtVO> objectivesExtList, OkrObjectSearchVO searchVO) {
        if (objectivesExtList != null && objectivesExtList.size() > 0) {
            for (ObjectivesExtVO objectivesExtVO : objectivesExtList) {
                // 获取KR信息
                ResultsEntityCondition resultsCondition = new ResultsEntityCondition();
                ResultsEntityCondition.Criteria criteria = resultsCondition.createCriteria();
                criteria.andObjectIdEqualTo(objectivesExtVO.getId()).andDelFlagEqualTo("0");
                if (StringUtils.isNotEmpty(searchVO.getExecuteStatus())) {
                    criteria.andStatusEqualTo(searchVO.getExecuteStatus());
                }
                resultsCondition.setOrderByClause("create_ts asc");
                List<ResultsEntity> resultsList = this.selectByCondition(resultsCondition);
                List<ResultsExtVO> resultsExtList = new ArrayList<>();
                if (resultsList != null && resultsList.size() > 0) {
                    for (ResultsEntity resultsEntity : resultsList) {
                        ResultsExtVO resultsExtVO = new ResultsExtVO();
                        BeanUtils.copyBean(resultsEntity, resultsExtVO);
                        List<UserVO> userList = okrResultService.getJoinUsersByResultId(resultsExtVO.getId(), searchVO.getLimitAmount());
                        if (userList != null) {
                            resultsExtVO.setJoinUsers(userList);
                        }
                        resultsExtList.add(resultsExtVO);
                    }
                }
                objectivesExtVO.setResultsExtList(resultsExtList);
            }
        }
    }

    /**
     * 设置历史操作，每周更新
     */
    private void setOperateRecordInfo(ObjectivesExtVO objectivesExtVO, List<String> resultIds) {
        // 获取OKR的历史操作记录
        List<LogVO> operateRecordList = this.getOperateRecordList(objectivesExtVO.getId(), resultIds);
        objectivesExtVO.setOperateRecordList(operateRecordList);
        // 获取OKR的每周更新记录
        List<CheckinsExtVO> checkinsVOList = this.getCheckinList(resultIds);
        objectivesExtVO.setCheckinsExtVOList(checkinsVOList);
    }

    private List<LogVO> getOperateRecordList(String objectId, List<String> resultIds) throws BusinessException {
        List<LogVO> operateRecordList = new ArrayList<>();
        LogEntityCondition logCondition = new LogEntityCondition();
        // bizId 业务id bizType 1、目标 2、关键结果
        LogEntityCondition.Criteria criteria = logCondition.createCriteria();
        criteria.andBizTypeEqualTo("1").andBizIdEqualTo(objectId);
        LogEntityCondition.Criteria criteria2 = logCondition.or();
        if (resultIds != null && resultIds.size() > 0) {
            criteria2.andBizTypeEqualTo("2").andBizIdIn(resultIds);
        }
        logCondition.setOrderByClause("create_ts desc");
        List<LogEntity> logEntityList = this.selectByCondition(logCondition);
        if (logEntityList != null && logEntityList.size() > 0) {
            operateRecordList = BeanUtils.copyToNewList(logEntityList, LogVO.class);
        }
        return operateRecordList;
    }

    private List<CheckinsExtVO> getCheckinList(List<String> resultIds) {
        List<CheckinsExtVO> checkinsVOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("resultIds", resultIds);
        if (resultIds != null && resultIds.size() > 0) {
            checkinsVOList = okrResultService.findCheckinList(params);
        }
        return checkinsVOList;
    }

    /**
     * 历史操作日志
     * @param originalEntity 原始的实体
     * @param targetEntity 页面修改后的实体
     * @param originalTeamRelList 原始影响团队
     * @param originalLabelsRelList 原始标签
     * @param targetRelTeams 修改后影响团队
     * @param targetRelLabels 修改后标签
     */
    private void setObjectLogInfo(ObjectivesEntity originalEntity, ObjectivesEntity targetEntity,
                                  List<ObjectTeamRelaEntity> originalTeamRelList, List<ObjectLabelRelaEntity> originalLabelsRelList,
                                  List<TeamsVO> targetRelTeams, List<LabelVO> targetRelLabels) {
        Map<String ,Object> compareMap = GetChangeDateUtil.compareFields(originalEntity, targetEntity,
                new String[]{"name", "confidenceLevel", "status", "parentId", "teamId"});
        StringBuilder message = new StringBuilder();
        if (compareMap != null && !compareMap.isEmpty()) {
            for (String key : compareMap.keySet()) {
                if (key.equals("name")){
                    message.append("目标名称修改为：").append(compareMap.get(key)).append("，");
                    continue;
                }
                if (key.equals("confidenceLevel")){
                    message.append("目标把握度修改为：").append(compareMap.get(key)).append("成，");
                    continue;
                }
                if (key.equals("status")) {
                    message.append("目标状态修改为：").append(ObjectivesStatusEnum.getByCode(compareMap.get(key).toString()).getName()).append("，");
                    continue;
                }
                if (key.equals("parentId")) {
                    ObjectivesEntity targetParentEntity = this.selectByPrimaryKey(ObjectivesEntity.class, targetEntity.getParentId());
                    if (targetParentEntity != null) {
                        message.append("目标父目标修改为：").append(targetParentEntity.getName());
                    } else {
                        message.append("目标父目标修改为：").append("空");
                    }
                    message.append("，");
                    continue;
                }
                if (key.equals("teamId")) {
                    TeamsEntity targetTeamEntity = this.selectByPrimaryKey(TeamsEntity.class, targetEntity.getTeamId());
                    message.append("目标所属团队修改为：").append(targetTeamEntity.getName()).append("，");
                }
            }
        }
        List<String> originalTeamRelaIds = new ArrayList<>(); List<String> targetTeamRelaIds = new ArrayList<>();
        List<String> originalLabelRelaIds = new ArrayList<>(); List<String> targetLabelRelaIds = new ArrayList<>();
        StringBuilder targetTeamRelaNames = new StringBuilder("["); StringBuilder targetLabelRelaNames = new StringBuilder("[");
        // 影响团队
        for (ObjectTeamRelaEntity entity : originalTeamRelList) {
            originalTeamRelaIds.add(entity.getTeamId());
        }
        for (TeamsVO vo : targetRelTeams) {
            targetTeamRelaIds.add(vo.getId());
            targetTeamRelaNames.append(vo.getName()).append("，");
        }
        if (!ListUtils.isEqualList(originalTeamRelaIds, targetTeamRelaIds)) {
            if (!targetTeamRelaIds.isEmpty()) {
                targetTeamRelaNames.deleteCharAt(targetTeamRelaNames.length() - 1).append("]");
            } else {
                targetTeamRelaNames.delete(0, targetTeamRelaNames.length()).append("空");
            }
            message.append("目标影响团队修改为：").append(targetTeamRelaNames.toString()).append("，");
        }

        // 标签
        for (ObjectLabelRelaEntity entity : originalLabelsRelList) {
            originalLabelRelaIds.add(entity.getLabelId());
        }
        for (LabelVO vo : targetRelLabels) {
            targetLabelRelaIds.add(vo.getId());
            targetLabelRelaNames.append(vo.getName()).append("，");
        }
        if (!ListUtils.isEqualList(originalLabelRelaIds, targetLabelRelaIds)) {
            if (!targetLabelRelaIds.isEmpty()) {
                targetLabelRelaNames.deleteCharAt(targetLabelRelaNames.length() - 1).append("]");
            } else {
                targetLabelRelaNames.delete(0, targetLabelRelaNames.length()).append("空");
            }
            message.append("目标标签修改为：").append(targetLabelRelaNames.toString()).append("，");
        }
        // 单独对description进行处理，之前没消息时直接添加，有消息去除掉，后再<br/>单独一行添加
        boolean editDescFlag = false;
        if (!originalEntity.getDescription().equals(targetEntity.getDescription())) {
            editDescFlag = true;
            if (message.toString().equals("")) {
                message.append("目标描述修改为：").append(targetEntity.getDescription());
            } else {
                String temp = message.substring(0, message.length() - 1);
                message.delete(0, message.length()).append(temp).append("<br/>目标描述修改为：").append(targetEntity.getDescription());
            }
        }
        if (StringUtils.isNotEmpty(message.toString())) {
            // 保存操作记录
            LogVO logVO = new LogVO();
            logVO.setBizId(targetEntity.getId());
            logVO.setBizType("1");
            logVO.setMessage(editDescFlag ? message.toString() : message.substring(0, message.length() - 1));
            logVO.setCreateTs(new Date());
            logVO.setCreateUserId(targetEntity.getUpdateUserId());
            this.saveOkrLog(logVO);
        }
    }

}