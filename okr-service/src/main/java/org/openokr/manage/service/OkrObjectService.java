package org.openokr.manage.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.entity.ResultsEntityCondition;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.openokr.manage.vo.ResultsExtVO;
import org.openokr.sys.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrObjectService extends BaseServiceImpl implements IOkrObjectService {

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
        params.put("executeStatus", searchVO.getExecuteStatus());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getPersonalOkrList", params);
        //设置KR信息
        this.setKrInfo(objectivesExtList, searchVO.getLimitAmount());
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getTeamOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", searchVO.getUserId());
        params.put("keyword", searchVO.getKeyword());
        params.put("executeStatus", searchVO.getExecuteStatus());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getTeamOkrList", params);
        //设置KR信息
        this.setKrInfo(objectivesExtList, searchVO.getLimitAmount());
        return objectivesExtList;
    }

    @Override
    public List<ObjectivesExtVO> getCompanyOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", searchVO.getKeyword());
        params.put("executeStatus", searchVO.getExecuteStatus());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getCompanyOkrList", params);
        //设置KR信息
        this.setKrInfo(objectivesExtList, searchVO.getLimitAmount());
        return objectivesExtList;
    }

    /**
     * 设置KR信息
     */
    private void setKrInfo(List<ObjectivesExtVO> objectivesExtList, Integer limitAmount) {
        if (objectivesExtList != null && objectivesExtList.size()>0) {
            for (ObjectivesExtVO objectivesExtVO : objectivesExtList) {
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
                        List<UserVO> userList = this.getJoinUsersByResultId(resultsExtVO.getId(), limitAmount);
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


}