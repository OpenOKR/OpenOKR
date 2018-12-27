package org.openokr.manage.service;


import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.TeamsExtVO;
import org.openokr.manage.vo.TeamsVO;

import java.util.List;

/**
 * 团队管理service
 * Created by hjh on 2018/12/24.
 */
public interface IOkrTeamService {

    /**
     * 获取用户的所有团队信息
     * @param userId
     * @return
     * @throws BusinessException
     */
    List<TeamsExtVO> getTeamByUserId(String userId) throws BusinessException;

    /**
     * 获取目标的影响团队
     * @param objectId
     * @return
     * @throws BusinessException
     */
    List<TeamsVO> getObjectTeamRel(String objectId) throws BusinessException;
}
