package org.openokr.db.service;

import com.google.common.collect.Maps;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.task.vo.SearchConditionVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
