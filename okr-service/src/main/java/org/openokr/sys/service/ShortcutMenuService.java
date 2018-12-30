package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.MapUtils;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.ShortcutMenuEntity;
import org.openokr.sys.entity.ShortcutMenuEntityCondition;
import org.openokr.sys.vo.ShortcutMenuVOExt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzheng on 2018/12/20.
 */
@Service
@Transactional
public class ShortcutMenuService extends BaseServiceImpl implements IShortcutMenuService {

    @Override
    public ResponseResult save(String userId, List<String> menuIds) {
        //先删除
        ShortcutMenuEntityCondition condition = new ShortcutMenuEntityCondition();
        condition.createCriteria().andUserIdEqualTo(userId);
        this.deleteByCondition(condition);
        //批量新增
        List<ShortcutMenuEntity> addShortcutMenuEntities = new ArrayList<>();
        for (String id : menuIds) {
            ShortcutMenuEntity entity = new ShortcutMenuEntity();
            entity.setUserId(userId);
            entity.setMenuId(id);
            addShortcutMenuEntities.add(entity);
        }
        this.insert(addShortcutMenuEntities);
        //
        return new ResponseResult();
    }

    @Override
    public List<ShortcutMenuVOExt> findByUserId(String userId) {
        String sql = "SELECT t1.id,t2.id AS \"menuId\",t2.name AS \"menuName\",t2.url  AS \"menuUrl\" " +
                "FROM t_okr_sys_shortcut_menu t1, t_okr_sys_menu t2 WHERE t1.menu_id = t2.id AND t1.user_id = #{userId}";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("userId", userId);
        List<Map<String, Object>> mapList = this.getDao().selectListByDynamicSql(sql, parameterMap);
        return MapUtils.mapListToBeanList(mapList, ShortcutMenuVOExt.class);
    }

    @Override
    public int deleteByMenuId(String menuId) {
        ShortcutMenuEntityCondition condition = new ShortcutMenuEntityCondition();
        condition.createCriteria().andMenuIdEqualTo(menuId);
        return this.deleteByCondition(condition);
    }

    @Override
    public int deleteByUserId(String userId) {
        ShortcutMenuEntityCondition condition = new ShortcutMenuEntityCondition();
        condition.createCriteria().andUserIdEqualTo(userId);
        return this.deleteByCondition(condition);
    }
}