package org.openokr.index.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.index.vo.ExecutionVO;
import org.openokr.manage.vo.ObjectivesVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class IndexService extends BaseServiceImpl implements IIndexService {

    private final static String MAPPER_NAMESPACE = "org.openokr.index.sqlmapper.IndexMapper";

    @Override
    public ExecutionVO execution(ObjectivesVO objectivesVO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("ownerId", objectivesVO.getOwnerId());
        params.put("type", objectivesVO.getType());
        return this.getMyBatisDao().selectOneBySql(MAPPER_NAMESPACE + ".findExecutionByOwnerId", params);
    }
}