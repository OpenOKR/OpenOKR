package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.openokr.application.framework.service.OkrBaseService;
import org.openokr.manage.entity.LogEntity;
import org.openokr.manage.entity.LogEntityCondition;
import org.openokr.manage.entity.ObjectLabelRelaEntity;
import org.openokr.manage.entity.ObjectLabelRelaEntityCondition;
import org.openokr.manage.entity.ObjectTeamRelaEntity;
import org.openokr.manage.entity.ObjectTeamRelaEntityCondition;
import org.openokr.manage.entity.ObjectivesEntity;
import org.openokr.manage.entity.ObjectivesEntityCondition;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.entity.ResultsEntityCondition;
import org.openokr.manage.enumerate.ObjectivesTypeEnum;
import org.openokr.manage.vo.LabelVO;
import org.openokr.manage.vo.LogVO;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.openokr.manage.vo.ResultsExtVO;
import org.openokr.manage.vo.TeamsVO;
import org.openokr.sys.vo.UserVO;
import org.springframework.beans.BeanUtils;
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

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrObjectMapper";

    @Override
    public List<ObjectivesExtVO> getAllOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", searchVO.getUserId());
        params.put("limitAmount", searchVO.getLimitAmount());
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
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getPersonalOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", searchVO.getUserId());
        params.put("keyword", searchVO.getKeyword());
        params.put("objectId", searchVO.getObjectId());//查看OKR的详情时才需要传该参数
        params.put("executeStatus", searchVO.getExecuteStatus());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getPersonalOkrList", params);
        //设置KR信息
        this.setKrInfo(objectivesExtList, searchVO);
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getTeamOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", searchVO.getUserId());
        params.put("teamId", searchVO.getUserId());
        params.put("objectId", searchVO.getObjectId());//查看OKR的详情时才需要传该参数
        params.put("keyword", searchVO.getKeyword());
        params.put("executeStatus", searchVO.getExecuteStatus());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getTeamOkrList", params);
        //设置KR信息
        this.setKrInfo(objectivesExtList, searchVO);
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getCompanyOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", searchVO.getObjectId());//查看OKR的详情时才需要传该参数
        params.put("keyword", searchVO.getKeyword());
        params.put("executeStatus", searchVO.getExecuteStatus());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getCompanyOkrList", params);
        //设置KR信息
        this.setKrInfo(objectivesExtList, searchVO);
        return objectivesExtList;
    }

    @Override
    public List<LogVO> getOperateRecordList(String objectId, List<String> resultIds) throws BusinessException {
        List<LogVO> operateRecordList = new ArrayList<>();
        LogEntityCondition logCondition = new LogEntityCondition();
        //1、目标
        LogEntityCondition.Criteria criteria = logCondition.createCriteria();
        criteria.andBizTypeEqualTo("1").andBizIdEqualTo(objectId);
        //2、关键结果
        LogEntityCondition.Criteria criteria2 = logCondition.or();
        criteria2.andBizTypeEqualTo("2").andBizIdIn(resultIds);
        logCondition.setOrderByClause("create_ts desc");
        List<LogEntity> logEntityList = this.selectByCondition(logCondition);
        if (logEntityList != null && logEntityList.size()>0) {
            for(LogEntity logEntity : logEntityList) {
                LogVO logVO = new LogVO();
                BeanUtils.copyProperties(logEntity, logVO);
                operateRecordList.add(logVO);
            }
        }
        return operateRecordList;
    }

    @Override
    public ObjectivesExtVO editObject(String objectId) throws BusinessException {
        ObjectivesExtVO objectVO = new ObjectivesExtVO();
        ObjectivesEntity entity = this.selectByPrimaryKey(ObjectivesEntity.class, objectId);
        if (entity == null) {
            return null;
        }
        BeanUtils.copyProperties(entity, objectVO);

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
        if (StringUtils.isEmpty(objectId)) { //新增
            ObjectivesEntity entity = new ObjectivesEntity();
            BeanUtils.copyProperties(objectVO, entity);
            entity.setVisibility("1");//默认公开
            entity.setStatus("1");//未提交状态
            entity.setDelFlag("0");//删除状态:否
            entity.setCreateTs(new Date());
            this.insert(entity);
            objectId = entity.getId();
        } else { //更新
            ObjectivesEntity entity = new ObjectivesEntity();
            BeanUtils.copyProperties(objectVO, entity);
            entity.setUpdateTs(new Date());
            entity.setUpdateUserId(userId);
            this.update(entity);

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
        }
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
        if (parentList !=null && parentList.size()>0) {
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

    /**
     * 设置KR信息
     */
    private void setKrInfo(List<ObjectivesExtVO> objectivesExtList, OkrObjectSearchVO searchVO) {
        if (objectivesExtList != null && objectivesExtList.size()>0) {
            for (ObjectivesExtVO objectivesExtVO : objectivesExtList) {
                List<String> resultIds = new ArrayList<>();
                // 获取KR信息
                ResultsEntityCondition resultsCondition = new ResultsEntityCondition();
                resultsCondition.createCriteria().andObjectIdEqualTo(objectivesExtVO.getId())
                        .andDelFlagEqualTo("0");
                List<ResultsEntity> resultsList = this.selectByCondition(resultsCondition);
                List<ResultsExtVO> resultsExtList = new ArrayList<>();
                if (resultsList !=null) {
                    for (ResultsEntity resultsEntity : resultsList) {
                        ResultsExtVO resultsExtVO = new ResultsExtVO();
                        BeanUtils.copyProperties(resultsEntity, resultsExtVO);
                        List<UserVO> userList = okrResultService.getJoinUsersByResultId(resultsExtVO.getId(), searchVO.getLimitAmount());
                        if (userList != null) {
                            resultsExtVO.setJoinUsers(userList);
                        }
                        resultsExtList.add(resultsExtVO);
                        resultIds.add(resultsEntity.getId());
                    }
                }
                objectivesExtVO.setResultsExtList(resultsExtList);

                //获取OKR的历史操作记录
                if (StringUtils.isNotEmpty(searchVO.getObjectId())) { //目前业务,单个目标才需要展示历史操作记录
                    List<LogVO> operateRecordList = this.getOperateRecordList(searchVO.getObjectId(), resultIds);
                    objectivesExtVO.setOperateRecordList(operateRecordList);
                }
            }
        }
    }


}