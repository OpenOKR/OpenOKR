package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.openokr.application.framework.service.OkrBaseService;
import org.openokr.manage.entity.LogEntity;
import org.openokr.manage.entity.LogEntityCondition;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.entity.ResultsEntityCondition;
import org.openokr.manage.enumerate.ObjectivesTypeEnum;
import org.openokr.manage.vo.LogVO;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.openokr.manage.vo.ResultsExtVO;
import org.openokr.sys.vo.UserVO;
import org.springframework.beans.BeanUtils;
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
    public List<UserVO> getJoinUsersByResultId(String resultId, Integer limitAmount) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("resultId", resultId);
        params.put("limitAmount", limitAmount);
        List<UserVO> joinUsers = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getJoinUsersByResultId", params);
        return joinUsers;
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
    public ResponseResult deleteObject(String objectId, String userId) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();

        responseResult.setInfo("删除成功");
        return responseResult;
    }

    @Override
    public ResponseResult deleteResult(String resultId, String userId) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        ResultsEntity resultsEntity = this.selectByPrimaryKey(ResultsEntity.class, resultId);
        resultsEntity.setDelFlag("1");//设为已删除状态
        resultsEntity.setUpdateTs(new Date());
        resultsEntity.setUpdateUserId(userId);
        this.update(resultsEntity);

        // 保存操作记录
        LogVO logVO = new LogVO();
        logVO.setBizId(resultId);
        logVO.setBizType("2");
        logVO.setMessage("");
        logVO.setCreateTs(new Date());
        logVO.setCreateUserId(userId);
        this.saveOkrLog(logVO);
        responseResult.setInfo("删除成功");
        return responseResult;
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
                        List<UserVO> userList = this.getJoinUsersByResultId(resultsExtVO.getId(), searchVO.getLimitAmount());
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