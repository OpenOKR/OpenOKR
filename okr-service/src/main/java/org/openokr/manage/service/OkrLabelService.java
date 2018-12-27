package org.openokr.manage.service;

import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.LabelEntity;
import org.openokr.manage.entity.LabelEntityCondition;
import org.openokr.manage.vo.LabelVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public List<LabelVO> getAllLabel() throws BusinessException {
        List<LabelVO> labelVOList = new ArrayList<>();
        LabelEntityCondition labelCondition = new LabelEntityCondition();
        List<LabelEntity> entityList = this.selectByCondition(labelCondition);
        if (entityList!=null && entityList.size()>0) {
            labelVOList = BeanUtils.copyToNewList(entityList, LabelVO.class);
        }
        return  labelVOList;
    }


}