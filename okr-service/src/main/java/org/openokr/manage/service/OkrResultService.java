package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.openokr.application.framework.service.OkrBaseService;
import org.openokr.manage.entity.ResultUserRelaEntity;
import org.openokr.manage.entity.ResultUserRelaEntityCondition;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.vo.LogVO;
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
public class OkrResultService extends OkrBaseService implements IOkrResultService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrResultMapper";

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
        logVO.setMessage("删除关键结果:"+ resultsEntity.getName() +"");
        logVO.setCreateTs(new Date());
        logVO.setCreateUserId(userId);
        this.saveOkrLog(logVO);
        responseResult.setInfo("删除成功");
        return responseResult;
    }

    @Override
    public ResponseResult saveResult(ResultsExtVO resultVO) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        String resultId = resultVO.getId();
        String userId = resultVO.getCreateUserId();
        if (StringUtils.isEmpty(resultId)) { //新增
            ResultsEntity entity = new ResultsEntity();
            BeanUtils.copyProperties(resultVO, entity);
            entity.setStatus("1");//未开始状态
            entity.setDelFlag("0");//删除状态:否
            entity.setCreateTs(new Date());
            this.insert(entity);
            resultId = entity.getId();
        } else { //更新
            ResultsEntity entity = this.selectByPrimaryKey(ResultsEntity.class, resultId);
            resultVO.setStatus(entity.getStatus());//状态不能修改
            resultVO.setUpdateTs(new Date());
            resultVO.setUpdateUserId(userId);
            BeanUtils.copyProperties(resultVO, entity);
            this.update(entity);

            // 更新关键结果协同人员
            ResultUserRelaEntityCondition resultUserRelCondition = new ResultUserRelaEntityCondition();
            resultUserRelCondition.createCriteria().andResultIdEqualTo(resultId);
            List<ResultUserRelaEntity> resultUserRelList = this.selectByCondition(resultUserRelCondition);
            // 先删除旧数据
            if (resultUserRelList !=null && resultUserRelList.size()>0) {
                this.delete(resultUserRelList);
            }
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

        responseResult.setInfo("保存成功");
        return responseResult;
    }

    @Override
    public ResultsExtVO editResult(String resultId) throws BusinessException {
        ResultsExtVO resultVO = new ResultsExtVO();
        ResultsEntity entity = this.selectByPrimaryKey(ResultsEntity.class, resultId);
        BeanUtils.copyProperties(entity, resultVO);

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


}