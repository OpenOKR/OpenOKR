package org.openokr.manage.service;

import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.exception.BusinessException;
import org.openokr.application.framework.service.OkrBaseService;
import org.openokr.manage.entity.TimeSessionsEntity;
import org.openokr.manage.entity.TimeSessionsEntityCondition;
import org.openokr.manage.vo.TimeSessionsExtVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OkrTimeSessionsService extends OkrBaseService implements IOkrTimeSessionsService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrTimeSessionsMapper";

    public List<TimeSessionsExtVO> getTimeSessionList() throws BusinessException {
        TimeSessionsEntityCondition sessionsCondition = new TimeSessionsEntityCondition();
        sessionsCondition.setOrderByClause("create_ts desc");
        List<TimeSessionsEntity> timeSessionsEntities = this.selectByCondition(sessionsCondition);
        List<TimeSessionsExtVO> timeSessionList = BeanUtils.copyToNewList(timeSessionsEntities, TimeSessionsExtVO.class);
        return timeSessionList;
    }

    public TimeSessionsExtVO getDefaultTimeSession() throws BusinessException {
        TimeSessionsExtVO sessionsExtVO = new TimeSessionsExtVO();
        TimeSessionsEntityCondition sessionsCondition = new TimeSessionsEntityCondition();
        sessionsCondition.createCriteria().andIsActivateEqualTo("1");//当前时间段
        TimeSessionsEntity timeSessionsEntity = this.selectOneByCondition(sessionsCondition);
        BeanUtils.copyBean(timeSessionsEntity, sessionsExtVO);
        return sessionsExtVO;
    }
}