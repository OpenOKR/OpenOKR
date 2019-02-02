package org.openokr.manage.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.application.framework.service.OkrBaseService;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrMapService extends OkrBaseService implements IOkrMapService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrMapMapper";

    @Override
    public List<ObjectivesExtVO> getCompanyOkrList(OkrObjectSearchVO searchVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", searchVO.getObjectId());//查看OKR的详情时才需要传该参数
        params.put("keyword", searchVO.getKeyword());
        params.put("executeStatus", searchVO.getExecuteStatus());
        params.put("timeSessionId", searchVO.getTimeSessionId());
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getCompanyOkrList", params);
        return objectivesExtList;
    }


    @Override
    public List<ObjectivesExtVO> getChildrenOkrList(String objectId) {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        List<ObjectivesExtVO> objectivesExtList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getChildrenOkrList", params);
        return objectivesExtList;
    }

}
