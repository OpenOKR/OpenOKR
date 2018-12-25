package org.openokr.manage.service;


import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.LabelVO;

import java.util.List;

/**
 * 标签管理service
 * Created by hjh on 2018/12/25.
 */
public interface IOkrLabelService {

    /**
     * 获取目标的关联标签
     * @param objectId
     * @return
     * @throws BusinessException
     */
    List<LabelVO> getObjectLabelRel(String objectId) throws BusinessException;
}
