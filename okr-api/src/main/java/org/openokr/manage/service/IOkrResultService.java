package org.openokr.manage.service;


import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.CheckinsExtVO;
import org.openokr.manage.vo.ResultsExtVO;
import org.openokr.sys.vo.UserVO;

import java.util.List;

/**
 * OKR 关键结果(result)service
 * Created by hjh on 2018/12/11.
 */
public interface IOkrResultService {

    /**
     * 编辑关键结果
     * @param resultId
     * @return
     * @throws BusinessException
     */
    ResultsExtVO editResult(String resultId) throws BusinessException;

    /**
     * 保存关键结果
     * @param resultVO
     * @return
     * @throws BusinessException
     */
    ResponseResult saveResult(ResultsExtVO resultVO) throws BusinessException;

    /**
     * 删除关键结果
     * @param resultId 关键结果ID
     * @param userId
     * @return
     * @throws BusinessException
     */
    ResponseResult deleteResult(String resultId, String userId) throws BusinessException;

    /**
     * 获取KR的参与人员
     * @param resultId
     * @param limitAmount
     * @return
     * @throws BusinessException
     */
    List<UserVO> getJoinUsersByResultId(String resultId, Integer limitAmount) throws BusinessException;

    /**
     * 编辑KR进度
     * @param resultId 关键结果ID
     * @return
     * @throws BusinessException
     */
    CheckinsExtVO editCheckins(String resultId) throws BusinessException;

    /**
     * 保存KR进度
     * @param checkinsVO
     * @return
     * @throws BusinessException
     */
    ResponseResult saveCheckins(CheckinsExtVO checkinsVO) throws BusinessException;

}
