package org.openokr.manage.service;


import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.TeamsVO;

import java.util.List;

/**
 * 团队管理service
 * Created by hjh on 2018/12/24.
 */
public interface IOkrTeamService {

    /**
     * 获取用户的所有团队信息(不包括公司团队)
     * @param userId
     * @return
     * @throws BusinessException
     */
    List<TeamsVO> getTeamByUserId(String userId) throws BusinessException;
}
