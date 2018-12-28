package org.openokr.manage.service;


import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.TimeSessionsExtVO;

import java.util.List;

/**
 * 时间段service
 * Created by hjh on 2018/12/27.
 */
public interface IOkrTimeSessionsService {

    /**
     * 获取时间段列表
     * @return
     * @throws BusinessException
     */
    List<TimeSessionsExtVO> getTimeSessionList() throws BusinessException;

    /**
     * 获取当前时间段
     * @return
     * @throws BusinessException
     */
    TimeSessionsExtVO getDefaultTimeSession() throws BusinessException;


}
