package com.okr.personal.service;

import com.jh.framework.mybatis.service.impl.BaseServiceImpl;
import com.okr.ssm.entity.MenuEntity;
import com.okr.ssm.entity.MenuEntityCondition;
import com.okr.ssm.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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