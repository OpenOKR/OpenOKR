package org.openokr.personal.service;

import com.jh.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.ssm.entity.MenuEntity;
import org.openokr.ssm.entity.MenuEntityCondition;
import org.openokr.ssm.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonalOkrService extends BaseServiceImpl implements IPersonalOkrService {

    private final static String MAPPER_NAMESPACE = "com.okr.personal.sqlmapper.PersonalOkrMapper";

    public MenuVO getPersonalOkr(MenuVO menuVO) {
        MenuEntityCondition condition = new MenuEntityCondition();
        condition.createCriteria().andNameEqualTo("用户管理");
        MenuEntity entity = this.selectOneByCondition(condition);
        BeanUtils.copyProperties(entity, menuVO);
        return menuVO;
//        Map<String, Object> params = new HashMap<>();
//        params.put("interMeetingId", interMeetingId);
//        List<InterMeetingDetailVOExt> vos = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".findByInterMeetingId", params);
    }

}