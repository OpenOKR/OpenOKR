package org.openokr.db.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.vo.SearchConditionVO;

import java.util.List;

/**
 * 基础 service
 * @author yuxinzh
 * @create 2019/2/28
 */
public interface IBasicDBService {

    /**
     * 若要同时查询多人的所有condition则 userId = null userIdList !=null
     * 如果两个同时为空则返回所有数据
     * @param conditionVO
     * @return
     * @throws BusinessException
     */
    List<SearchConditionVO> getSearchCondition(SearchConditionVO conditionVO) throws BusinessException;

    /**
     * 查询管理员管理的所有团队下的所有成员id列表
     * 接口里面会验证传入的用户是否是管理员
     * 返回的用户没去重，一个用户可能在多个团队
     * @param userId
     * @return
     * @throws BusinessException
     */
    List<String> getUserIdListByAdminTeam(String userId)throws BusinessException;

}
