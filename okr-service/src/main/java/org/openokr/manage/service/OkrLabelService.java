package org.openokr.manage.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.vo.LabelVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrLabelService extends BaseServiceImpl implements IOkrLabelService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrLabelMapper";

    @Override
    public List<LabelVO> getObjectLabelRel(String objectId) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        List<LabelVO> relLabels = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getObjectLabelRel", params);
        return  relLabels;
    }


}