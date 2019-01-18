package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.DateUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.openokr.application.framework.service.OkrBaseService;
import org.openokr.application.utils.GetChangeDateUtil;
import org.openokr.manage.entity.CheckinsEntity;
import org.openokr.manage.entity.MessagesEntity;
import org.openokr.manage.entity.ObjectivesEntity;
import org.openokr.manage.entity.ResultUserRelaEntity;
import org.openokr.manage.entity.ResultUserRelaEntityCondition;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.entity.ResultsEntityCondition;
import org.openokr.manage.entity.TeamsEntity;
import org.openokr.manage.enumerate.ExecuteStatusEnum;
import org.openokr.manage.enumerate.MessageMarkEnum;
import org.openokr.manage.enumerate.MessageTypeEnum;
import org.openokr.manage.enumerate.ObjectivesStatusEnum;
import org.openokr.manage.enumerate.ObjectivesTypeEnum;
import org.openokr.manage.enumerate.ResultMetricUnitEnum;
import org.openokr.manage.vo.CheckinsExtVO;
import org.openokr.manage.vo.LogVO;
import org.openokr.manage.vo.ResultsExtVO;
import org.openokr.manage.vo.TeamsVO;
import org.openokr.sys.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrResultService extends OkrBaseService implements IOkrResultService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrResultMapper";

    @Autowired
    private IOkrTeamService okrTeamService;

    @Override
    public ResponseResult deleteResult(String resultId, String userId) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        ResultsEntity resultsEntity = this.selectByPrimaryKey(ResultsEntity.class, resultId);
        resultsEntity.setDelFlag("1");//设为已删除状态
        resultsEntity.setUpdateTs(new Date());
        resultsEntity.setUpdateUserId(userId);
        this.update(resultsEntity);

        //删除KR要更新O的进度
        this.calculateObjectProgress(resultsEntity, userId);
        addOrDelResultLogInfo("删除", resultsEntity, userId);

        responseResult.setSuccess(true).setMessage("删除成功");
        return responseResult;
    }

    @Override
    public ResponseResult saveResult(ResultsExtVO resultVO) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        String resultId = resultVO.getId();
        String userId = resultVO.getCreateUserId();
        if (StringUtils.isEmpty(resultId)) { //新增
            ResultsEntity entity = new ResultsEntity();
            BeanUtils.copyBean(resultVO, entity);
            entity.setOwnerId(userId);
            entity.setStatus(ExecuteStatusEnum.STATUS_0.getCode());//未开始状态
            entity.setDelFlag("0");//删除状态:否
            entity.setCreateTs(new Date());
            entity.setProgress(new BigDecimal(0));
            entity.setCurrentValue("0");

            //如果评价单位是"是否",要设置一下默认的目标值和起始值
            if(ResultMetricUnitEnum.TYPE_1.getCode().equals(entity.getMetricUnit())) {
                entity.setTargetValue("完成");
                entity.setInitialValue("未完成");
            } else {
                //计算KR的进度
                this.calculateResultProgress(entity, entity.getInitialValue());
            }
            // 新增KR信息
            this.insert(entity);
            resultId = entity.getId();

            //计算O的进度
            this.calculateObjectProgress(entity, userId);
            addOrDelResultLogInfo("新增", entity, entity.getCreateUserId());
        } else { //更新
            ResultsEntity targetEntity = this.selectByPrimaryKey(ResultsEntity.class, resultId);
            ResultsEntity originalEntity = BeanUtils.copyToNewBean(targetEntity, ResultsEntity.class);
            resultVO.setStatus(targetEntity.getStatus());//状态不能修改
            resultVO.setUpdateTs(new Date());
            resultVO.setUpdateUserId(userId);
            BeanUtils.copyBean(resultVO, targetEntity);

            //如果修改了KR的目标值：要重新计算进度
            this.calculateResultProgress(targetEntity, targetEntity.getCurrentValue());
            //计算O的进度
            this.calculateObjectProgress(targetEntity, userId);

            //更新KR信息
            this.update(targetEntity);

            // 更新关键结果协同人员
            ResultUserRelaEntityCondition resultUserRelCondition = new ResultUserRelaEntityCondition();
            resultUserRelCondition.createCriteria().andResultIdEqualTo(resultId);
            List<ResultUserRelaEntity> resultUserRelList = this.selectByCondition(resultUserRelCondition);
            // 先删除旧数据
            if (resultUserRelList !=null && resultUserRelList.size()>0) {
                this.delete(resultUserRelList);
            }
            // 更新检查变化的字段，写入历史操作日志
            setResultLogInfo(originalEntity, targetEntity, resultUserRelList, resultVO.getJoinUsers());
        }
        // 新增关键结果协同人员
        if (resultVO.getJoinUsers() != null && resultVO.getJoinUsers().size()>0) {
            List<ResultUserRelaEntity> resultUserRelList = new ArrayList<>();
            for (UserVO userVO : resultVO.getJoinUsers()) {
                ResultUserRelaEntity relEntity = new ResultUserRelaEntity();
                relEntity.setResultId(resultId);
                relEntity.setUserId(userVO.getId());
                relEntity.setStatus("0");//待确认状态
                relEntity.setCreateTs(new Date());
                relEntity.setCreateUserId(userId);
                resultUserRelList.add(relEntity);
            }
            this.insertList(resultUserRelList);
        }
        responseResult.setSuccess(true).setMessage("保存成功");
        return responseResult;
    }

    @Override
    public ResultsExtVO editResult(String resultId, String objectId) throws BusinessException {
        ResultsExtVO resultVO = new ResultsExtVO();
        ResultsEntity entity = this.selectByPrimaryKey(ResultsEntity.class, resultId);
        if (entity == null) {
            return null;
        }
        BeanUtils.copyBean(entity, resultVO);
        //获取该关键结果目标
        ObjectivesEntity objectivesEntity = this.selectByPrimaryKey(ObjectivesEntity.class, objectId);
        resultVO.setObjectId(objectivesEntity.getId());
        resultVO.setObjectName(objectivesEntity.getName());

        // 获取协同人员
        List<UserVO> joinUsers = this.getJoinUsersByResultId(resultId, null);
        resultVO.setJoinUsers(joinUsers);
        return resultVO;
    }

    @Override
    public List<UserVO> getJoinUsersByResultId(String resultId, Integer limitAmount) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("resultId", resultId);
        params.put("limitAmount", limitAmount);
        List<UserVO> joinUsers = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getJoinUsersByResultId", params);
        return joinUsers;
    }

    @Override
    public ResponseResult saveCheckins(CheckinsExtVO checkinsVO) throws BusinessException {
        String resultId = checkinsVO.getResultId();
        if (StringUtils.isEmpty(resultId)) {
            return new ResponseResult(false, null, "关键结果ID不能为空！");
        }
        ResultsEntity resultsEntity = this.selectByPrimaryKey(ResultsEntity.class, resultId);

        //KR进度每次都是新增,不改旧数据
        CheckinsEntity entity = new CheckinsEntity();
        BeanUtils.copyBean(checkinsVO, entity);
        entity.setCreateTs(new Date());
        entity.setMetricUnit(resultsEntity.getMetricUnit());
        this.insert(entity);

        //计算KR的进度
        this.calculateResultProgress(resultsEntity, entity.getCurrentValue());
        ObjectivesEntity objectivesEntity = this.selectByPrimaryKey(ObjectivesEntity.class, resultsEntity.getObjectId());

        //计算O的进度
        this.calculateObjectProgress(resultsEntity, checkinsVO.getCreateUserId());

        // 消息通知影响团队
        List<TeamsVO> teamsVOList = okrTeamService.getObjectTeamRel(resultsEntity.getObjectId());
        List<MessagesEntity> messagesEntityList = new ArrayList<>();
        for (TeamsVO teamsVO : teamsVOList) {
            // 每周更新所属团队的负责人是自己，不需要进行消息通知
            if (teamsVO.getOwnerId().equals(checkinsVO.getCreateUserId())) {
                continue;
            }
            MessagesEntity messagesEntity = new MessagesEntity();
            messagesEntity.setUserId(teamsVO.getOwnerId());
            messagesEntity.setTitle("每周更新通知");
            messagesEntity.setContent("目标：" + objectivesEntity.getName() + "，关键结果：" + resultsEntity.getName() +
                    "<br/>状态更新为：" + ExecuteStatusEnum.getByCode(checkinsVO.getStatus()).getName() +
                    "，执行单位：" + ResultMetricUnitEnum.getByCode(resultsEntity.getMetricUnit()).getName());
            if (StringUtils.isNotEmpty(checkinsVO.getCurrentValue())) {
                messagesEntity.setContent(messagesEntity.getContent() + " 当前值更新为：" + checkinsVO.getCurrentValue());
            }
            messagesEntity.setType(MessageTypeEnum.TYPE_4.getCode());
            messagesEntity.setTargetId(resultsEntity.getId());
            messagesEntity.setIsProcessed("1");
            messagesEntity.setIsRead("0");
            messagesEntity.setMark(MessageMarkEnum.MARK_4.getCode());
            messagesEntity.setCreateUserId(checkinsVO.getCreateUserId());
            messagesEntity.setCreateTs(new Date());
            messagesEntityList.add(messagesEntity);
        }
        if (!messagesEntityList.isEmpty()) {
            this.insertList(messagesEntityList);
        }

        resultsEntity.setStatus(entity.getStatus());
        resultsEntity.setCurrentValue(checkinsVO.getCurrentValue());
        resultsEntity.setUpdateUserId(checkinsVO.getCreateUserId());
        resultsEntity.setUpdateTs(new Date());
        this.update(resultsEntity);
        return new ResponseResult(true, null, "保存成功");
    }

    @Override
    public List<CheckinsExtVO> findCheckinList(Map<String, Object> params) {
        return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + ".findCheckinList", params);
    }

    //计算KR的进度
    private void calculateResultProgress(ResultsEntity entity, String currentValue) {
        if(ResultMetricUnitEnum.TYPE_1.getCode().equals(entity.getMetricUnit())) { //是否完成
           if ("1".equals(currentValue)) {// 如果是已完成,则把KR的状态也更新成已完成,并且进度改成百分百
               entity.setCurrentValue("1");
               entity.setPreProgress(entity.getProgress());
               entity.setProgress(new BigDecimal(100));
               entity.setStatus(ExecuteStatusEnum.STATUS_5.getCode());
            }
        } else { //百分比或者数值
            String targetValue = entity.getTargetValue();
            if (StringUtils.isNotEmpty(targetValue) && StringUtils.isNotEmpty(currentValue)) {
                BigDecimal bigTargetValue = new BigDecimal(targetValue);
                if (bigTargetValue.compareTo(BigDecimal.ZERO) == 1) {
                    BigDecimal preProgress = new BigDecimal(currentValue).
                            divide(bigTargetValue, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100));
                    entity.setProgress(preProgress);
                }
            }
        }
    }

    //计算O的进度
    private void calculateObjectProgress(ResultsEntity entity, String userId) {
        String objectId = entity.getObjectId();
        ResultsEntityCondition resultsEntityCondition = new ResultsEntityCondition();
        resultsEntityCondition.createCriteria().andObjectIdEqualTo(objectId).andDelFlagEqualTo("0").
                andIdNotEqualTo(entity.getId());
        List<ResultsEntity> resultsEntityList = this.selectByCondition(resultsEntityCondition);
        BigDecimal objectPreProgress = BigDecimal.ZERO;
        Integer count = 0;
        if (CollectionUtils.isNotEmpty(resultsEntityList)) {
            for (ResultsEntity resultsEntity : resultsEntityList) {
                objectPreProgress = objectPreProgress.add(resultsEntity.getProgress());
                count++;
            }
        }
        if ("0".equals(entity.getDelFlag())) {//当前KR是删除的情况,要排除掉
            count++;
            objectPreProgress = objectPreProgress.add(entity.getProgress());
        }
        objectPreProgress = objectPreProgress.divide(new BigDecimal(count), 2, BigDecimal.ROUND_UP);
        ObjectivesEntity objectivesEntity = this.selectByPrimaryKey(ObjectivesEntity.class, objectId);
        objectivesEntity.setProgress(objectPreProgress);

        // 获取object所属团队责任人，若修改的目标所属团队责任人和当前用户一致，直接将状态设置为已确认
        TeamsEntity teamsEntity = this.selectByPrimaryKey(TeamsEntity.class, objectivesEntity.getTeamId());
        // 设置状态，当类型是 团队或公司时，不需要审核，其他情况下一律设置成未提交
        if (objectivesEntity.getType().equals(ObjectivesTypeEnum.TYPE_2.getCode()) || objectivesEntity.getType().equals(ObjectivesTypeEnum.TYPE_3.getCode())) {
            objectivesEntity.setStatus(ObjectivesStatusEnum.STATUS_3.getCode());
        } else if (teamsEntity.getOwnerId().equals(userId)) {
            objectivesEntity.setStatus(ObjectivesStatusEnum.STATUS_3.getCode());
        } else {
            objectivesEntity.setStatus(ObjectivesStatusEnum.STATUS_1.getCode());//一旦有修改,目标就要变成未提交状态
        }
        objectivesEntity.setUpdateTs(new Date());
        objectivesEntity.setUpdateUserId(userId);
        this.update(objectivesEntity);
    }

    /**
     * 新增/删除 关键结果日志
     */
    private void addOrDelResultLogInfo(String pre, ResultsEntity entity, String currentUserId) {
        // 获取关键结果下标
        Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("objectId", entity.getObjectId());
        int idx = this.getMyBatisDao().selectOneBySql(MAPPER_NAMESPACE + ".getIdxById", params);

        // 保存新增操作记录
        LogVO logVO = new LogVO();
        logVO.setBizId(entity.getId());
        logVO.setBizType("2");
        logVO.setMessage(pre + "关键结果：KR" + idx + "，名称：" +  entity.getName());
        logVO.setCreateTs(new Date());
        logVO.setCreateUserId(currentUserId);
        this.saveOkrLog(logVO);
    }

    /**
     * 历史操作日志
     * @param originalEntity 原始的实体
     * @param targetEntity 页面修改后的实体
     * @param originalUserRelList 原始协同人
     * @param targetUserRelList 修改后协同人
     */
    private void setResultLogInfo(ResultsEntity originalEntity, ResultsEntity targetEntity,
                                  List<ResultUserRelaEntity> originalUserRelList, List<UserVO> targetUserRelList) {
        Map<String ,Object> compareMap = GetChangeDateUtil.compareFields(originalEntity, targetEntity,
                new String[]{"name", "description", "currentValue", "endTs", "status", "progress"});

        StringBuilder message = new StringBuilder(); boolean flag = false;
        Map<String, Object> params = new HashMap<>();
        params.put("id", originalEntity.getId());
        params.put("objectId", originalEntity.getObjectId());
        int idx = this.getMyBatisDao().selectOneBySql(MAPPER_NAMESPACE + ".getIdxById", params);
        message.append("关键结果标识：KR").append(idx).append("<br/>");

        if (compareMap != null && !compareMap.isEmpty()) {
            for (String key : compareMap.keySet()) {
                if (key.equals("name")){
                    message.append("关键结果名称修改为：").append(compareMap.get(key)).append("，");
                    flag = true;
                    continue;
                }
                if (key.equals("description")){
                    message.append("关键结果描述修改为：").append(compareMap.get(key)).append("，");
                    flag = true;
                    continue;
                }
                if (key.equals("currentValue")){
                    message.append("关键结果当前值修改为：").append(compareMap.get(key)).append("，");
                    flag = true;
                    continue;
                }
                if (key.equals("endTs")) {
                    message.append("关键结果完成时间修改为：").append(DateUtils.getDateStr((Date) compareMap.get(key))).append("，");
                    flag = true;
                    continue;
                }
                if (key.equals("status")) {
                    message.append("关键结果执行状态更新为：").append(ExecuteStatusEnum.getByCode(compareMap.get(key).toString()).getName()).append("，");
                    flag = true;
                    continue;
                }
                if (key.equals("progress")) {
                    message.append("关键结果执行进度更新为：").append(compareMap.get(key)).append("，");
                    flag = true;
                }
            }
        }
        List<String> originalUserRelaIds = new ArrayList<>(); List<String> targetUserRelaIds = new ArrayList<>();
        StringBuilder targetUserRelaNames = new StringBuilder("[");
        // 协同人
        for (ResultUserRelaEntity entity : originalUserRelList) {
            originalUserRelaIds.add(entity.getUserId());
        }
        for (UserVO vo : targetUserRelList) {
            targetUserRelaIds.add(vo.getId());
            targetUserRelaNames.append(vo.getRealName()).append("，");
        }
        if (!ListUtils.isEqualList(originalUserRelaIds, targetUserRelaIds)) {
            if (!targetUserRelaIds.isEmpty()) {
                targetUserRelaNames.deleteCharAt(targetUserRelaNames.length() - 1).append("]");
            } else {
                targetUserRelaNames.delete(0, targetUserRelaNames.length()).append("空");
            }
            message.append("关键结果协同人修改为：").append(targetUserRelaNames.toString()).append("，");
            flag = true;
        }

        if (flag) {
            // 保存操作记录
            LogVO logVO = new LogVO();
            logVO.setBizId(targetEntity.getId());
            logVO.setBizType("2");
            logVO.setMessage(message.substring(0, message.length() - 1));
            logVO.setCreateTs(new Date());
            logVO.setCreateUserId(targetEntity.getUpdateUserId());
            this.saveOkrLog(logVO);
        }
    }
}