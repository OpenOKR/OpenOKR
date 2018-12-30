package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.MapUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.mybatis.entity.BaseEntity;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.PermissionEntity;
import org.openokr.sys.entity.PermissionEntityCondition;
import org.openokr.sys.vo.PermissionVO;
import org.openokr.sys.vo.PermissionVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zhengzheng on 2018/12/18.
 */
@Service
@Transactional
public class PermissionService extends BaseServiceImpl implements IPermissionService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Override
    public List<PermissionVOExt> findByUserId(String userId) {
        String sql = "SELECT t.id,t.name,t.menu_id as \"menuId\",t.description,t.code " +
                "FROM t_okr_sys_permission t, t_okr_sys_role_permission t1, t_okr_sys_user_role t2 " +
                "WHERE t.id = t1.permission_id and t1.role_id = t2.role_id and t2.user_id = #{userId}";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Map<String, Object>> maps = this.getMyBatisDao().selectListByDynamicSql(sql, params);
        return MapUtils.mapListToBeanList(maps, PermissionVOExt.class);
    }

    @Override
    public ResponseResult addModifyOrDelete(String menuId, List<PermissionVO> permissionVOList) {
        List<PermissionVO> addVOList = new ArrayList<>();
        List<PermissionVO> modifyVOList = new ArrayList<>();
        List<PermissionVO> deleteVOList = new ArrayList<>();
        for (PermissionVO permissionVO : permissionVOList) {
            if (permissionVO.getRowState() == BaseEntity.RowStateEnum.ADDED.getValue()) {
                addVOList.add(permissionVO);
            } else if (permissionVO.getRowState() == BaseEntity.RowStateEnum.MODIFIED.getValue()) {
                modifyVOList.add(permissionVO);
            } else if (permissionVO.getRowState() == BaseEntity.RowStateEnum.DELETED.getValue()) {
                deleteVOList.add(permissionVO);
            }
        }
        StringBuilder modifyBuilder = new StringBuilder();
        StringBuilder deleteBuilder = new StringBuilder();
        //判断 修改 的集合是否已经被引用
        for (PermissionVO modifyEntity : modifyVOList) {
            if (rolePermissionService.countByPermissionId(modifyEntity.getId()) > 0) {
                modifyBuilder.append(modifyEntity.getCode());
                modifyBuilder.append(',');
            }
        }
        //判断 删除 的集合是否已经被引用
        for (PermissionVO deleteEntity : deleteVOList) {
            if (rolePermissionService.countByPermissionId(deleteEntity.getId()) > 0) {
                deleteBuilder.append(deleteEntity.getCode());
                deleteBuilder.append(',');
            }
        }
        String allMsg = "";
        if (StringUtils.isNotEmpty(modifyBuilder)) {
            allMsg += modifyBuilder + " 已经分配使用中，无法修改。";
        }
        if (StringUtils.isNotEmpty(deleteBuilder)) {
            allMsg += deleteBuilder + " 已经分配使用中，无法删除。";
        }
        if (StringUtils.isNotEmpty(allMsg)) {
            return new ResponseResult(allMsg, false);
        }
        //新增
        for (PermissionVO addEntity : addVOList) {
            addEntity.setMenuId(menuId);
        }
        this.insertList(BeanUtils.copyToNewList(addVOList, PermissionEntity.class));
        //修改
        this.update(BeanUtils.copyToNewList(modifyVOList, PermissionEntity.class));
        //删除
        this.delete(BeanUtils.copyToNewList(deleteVOList, PermissionEntity.class));
        return new ResponseResult(true);
    }

    @Override
    public Set<String> findIdsByMenuId(String menuId) {
        String sql = "SELECT id FROM t_okr_sys_permission WHERE menu_id = #{menuId}";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("menuId", menuId);
        Map<String, String> map = this.getDao().selectMapByDynamicSql(sql, parameterMap, "id");
        return map.keySet();
    }

    @Override
    public ResponseResult countByPermissionIds(Set<String> permissionIds) {
        StringBuilder existBuilder = new StringBuilder();
        String sql = "SELECT t1.name,t1.code FROM t_okr_sys_permission t1, t_okr_sys_role_permission t2 WHERE t1.id = t2.permission_id and t1.id=#{id}";
        for (String permissionId : permissionIds) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("id", permissionId);
            Map<String, Object> map = this.getDao().selectOneByDynamicSql(sql, parameterMap);
            PermissionEntity entity = MapUtils.mapToBean(map, PermissionEntity.class);
            if (entity != null) {
                existBuilder.append(entity.getCode());
                existBuilder.append(',');
            }
        }
        if (StringUtils.isNotEmpty(existBuilder)) {
            existBuilder.append(" 已经配置使用中");
            return new ResponseResult(existBuilder.toString(), false);
        } else {
            return new ResponseResult(true);
        }
    }

    @Override
    public int deleteByMenuId(String menuId) {
        PermissionEntityCondition condition = new PermissionEntityCondition();
        condition.createCriteria().andMenuIdEqualTo(menuId);
        return this.deleteByCondition(condition);
    }

    @Override
    public List<PermissionVOExt> findByMenuId(String menuId) {
        PermissionEntityCondition condition = new PermissionEntityCondition();
        condition.createCriteria().andMenuIdEqualTo(menuId);
        List<PermissionEntity> entityList = this.selectByCondition(condition);
        return BeanUtils.copyToNewList(entityList, PermissionVOExt.class);
    }
}