package org.openokr.manage.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.vo.TeamsExtVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrTeamService extends BaseServiceImpl implements IOkrTeamService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrTeamMapper";

    @Override
    public List<TeamsExtVO> getTeamByUserId(String userId) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<TeamsExtVO> teamsVOList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getTeamByUserId", params);
        return teamsVOList;
    }

}