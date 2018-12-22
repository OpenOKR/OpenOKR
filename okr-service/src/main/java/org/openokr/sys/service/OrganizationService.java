package org.openokr.sys.service;

import com.zzheng.framework.base.utils.MapUtils;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.vo.OrganizationVOExt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzheng on 2018/12/21.
 */
@Service
@Transactional
public class OrganizationService extends BaseServiceImpl implements IOrganizationService {

    @Override
    public List<OrganizationVOExt> findAll() {
        String sql = "SELECT t.id,t.parent_id AS \"parentId\",t2.name AS \"parentName\",t.description,t.name,t.code" +
                " FROM t_okr_sys_organization t, t_okr_sys_organization t2 WHERE t.parent_id = t2.id" +
                " UNION SELECT t3.id,t3.parent_id AS \"parentId\",'' AS \"parentName\",t3.description,t3.name,t3.code" +
                " FROM t_okr_sys_organization t3 WHERE t3.id='1'";
        List<Map<String, Object>> maps = this.getDao().selectListByDynamicSql(sql);
        return MapUtils.mapListToBeanList(maps, OrganizationVOExt.class);
    }
}