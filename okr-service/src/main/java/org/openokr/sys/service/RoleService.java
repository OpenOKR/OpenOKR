package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.MapUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.RoleEntity;
import org.openokr.sys.entity.RoleEntityCondition;
import org.openokr.sys.vo.RoleVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzheng on 2018/12/18.
 */
@Service
@Transactional
public class RoleService extends BaseServiceImpl implements IRoleService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public List<RoleVOExt> findByUserId(String userId, boolean loadPermissions) {
        String sql = "SELECT t1.id,t1.name FROM t_okr_sys_role t1, t_okr_sys_user_role t2, t_okr_sys_user t3 " +
                "WHERE t1.id = t2.role_id AND t3.id = t2.user_id AND t3.id = #{userId}";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Map<String, Object>> maps = this.getDao().selectListByDynamicSql(sql, params);
        List<RoleVOExt> vos = MapUtils.mapListToBeanList(maps, RoleVOExt.class);
        //逐个角色设置权限
        if (loadPermissions) {
            for (RoleVOExt vo : vos) {
                vo.setPermissionList(loadPermissionsByRoleId(vo.getId()));
            }
        }
        return vos;
    }

    @Override
    public Page findByPageLikeInputValue(Page page, String inputValue) {
        RoleEntityCondition condition = new RoleEntityCondition();
        condition.setPage(page);
        if (StringUtils.isNotEmpty(inputValue)) {
            condition.createCriteria().andNameLikeIgnoreCase("%" + inputValue + "%");
        }
        Page resultPage = this.selectPageByCondition(condition);
        resultPage.setRecords(toVOExtList(resultPage.getRecords()));
        return resultPage;
    }

    /**
     * 根据角色Id加载权限
     *
     * @param roleId 角色Id
     * @return 权限集合
     */
    @Override
    public List<String> loadPermissionsByRoleId(String roleId) {
        String sql = "select t1.permission_id from t_okr_sys_role_permission t1, t_okr_sys_role t2 where t2.id = t1.role_id and t2.id = #{roleId}";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("roleId", roleId);
        Map<String, Object> map = this.getDao().selectMapByDynamicSql(sql, parameterMap, "permission_id");
        List<String> permissionList = new ArrayList<>();
        permissionList.addAll(map.keySet());
        return permissionList;
    }

    @Override
    public ResponseResult addOrModify(RoleVOExt roleVOExt, List<String> permissionIds) {
        RoleEntity saveEntity = null;
        if (countByName(roleVOExt.getId(), roleVOExt.getName()) > 0) {
            return new ResponseResult(roleVOExt.getName() + " 已经存在", false);
        }
        if (roleVOExt.getId() == null) {//新增
            //保存
            RoleEntity entity = BeanUtils.copyToNewBean(roleVOExt, RoleEntity.class);
            saveEntity = this.saveWithQuery(entity);
        } else if (roleVOExt.getId() != null) {//修改
            //保存
            RoleEntity entity = BeanUtils.copyToNewBean(roleVOExt, RoleEntity.class);
            saveEntity = this.saveWithQuery(entity);
            //删除 角色关联权限
            if (StringUtils.isNotEmpty(saveEntity.getId())) {
                rolePermissionService.deleteByRoleId(saveEntity.getId());
            }
        }
        //
        if (saveEntity != null) {
            //批量新增
            rolePermissionService.batchAdd(permissionIds, saveEntity.getId());
            return new ResponseResult("保存成功");
        } else {
            return new ResponseResult("保存失败", false);
        }
    }

    @Override
    public ResponseResult delete(String id) {
        if (userRoleService.countByRoleId(id) > 0) {
            return new ResponseResult("删除失败，还有用户正在使用，不允许删除", false);
        }
        //删除 角色关联权限
        rolePermissionService.deleteByRoleId(id);
        //删除角色
        if (this.deleteByPrimaryKey(RoleEntity.class, id) > 0) {
            return new ResponseResult("删除成功");
        } else {
            return new ResponseResult("删除失败", false);
        }
    }

    @Override
    public Page findPageByLikeNameExcludeIds(Page page, String inputValue, List<String> excludeIds) {
        RoleEntityCondition condition = new RoleEntityCondition();
        RoleEntityCondition.Criteria criteria = condition.createCriteria();
        if (excludeIds != null && !excludeIds.isEmpty()) {
            criteria.andIdNotIn(excludeIds);
        }
        if (StringUtils.isNotEmpty(inputValue)) {
            criteria.andNameLikeIgnoreCase("%" + inputValue + "%");
        }
        condition.setPage(page);
        return this.selectPageByCondition(condition);
    }

    @Override
    public List<RoleVOExt> findAllList() {
        List<Map<String, Object>> list = this.getMyBatisDao().selectListByDynamicSql("SELECT t1.id, t1.name FROM t_okr_sys_role t1");
        return BeanUtils.copyToNewList(list, RoleVOExt.class);
    }

    private List<RoleVOExt> toVOExtList(List<Object> entityList) {
        List<RoleVOExt> resultList = new ArrayList<>();
        for (Object roleEntity : entityList) {
            RoleVOExt role = BeanUtils.copyToNewBean(roleEntity, RoleVOExt.class);
            resultList.add(role);
        }
        return resultList;
    }

    private long countByName(String id, String name) {
        RoleEntityCondition condition = new RoleEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name);
        } else {
            condition.createCriteria().andNameEqualTo(name);
        }
        return this.countByCondition(condition);
    }
}