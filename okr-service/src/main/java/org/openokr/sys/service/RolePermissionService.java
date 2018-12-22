package org.openokr.sys.service;

import com.zzheng.framework.base.utils.UUIDUtils;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.RolePermissionEntity;
import org.openokr.sys.entity.RolePermissionEntityCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzheng on 2018/12/20.
 */
@Service
@Transactional
public class RolePermissionService extends BaseServiceImpl implements IRolePermissionService {

    @Override
    public int deleteByPermissionIds(List<String> permissionIdList) {
        if (!permissionIdList.isEmpty()) {
            RolePermissionEntityCondition condition = new RolePermissionEntityCondition();
            condition.createCriteria().andPermissionIdIn(permissionIdList);
            return this.deleteByCondition(condition);
        } else {
            return 0;
        }
    }

    @Override
    public long countByPermissionId(String permissionId) {
        RolePermissionEntityCondition condition = new RolePermissionEntityCondition();
        condition.createCriteria().andPermissionIdEqualTo(permissionId);
        return this.countByCondition(condition);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        RolePermissionEntityCondition rolePermissionEntityCondition = new RolePermissionEntityCondition();
        rolePermissionEntityCondition.createCriteria().andRoleIdEqualTo(roleId);
        return this.deleteByCondition(rolePermissionEntityCondition);
    }

    @Override
    public int batchAdd(List<String> permissionIds, String roleId) {
        //批量新增
        if (!permissionIds.isEmpty()) {
            List<RolePermissionEntity> permissionEntities = new ArrayList<>();
            for (String permission : permissionIds) {
                RolePermissionEntity permissionEntity = new RolePermissionEntity();
                permissionEntity.setId(UUIDUtils.getStringValue());
                permissionEntity.setPermissionId(permission);
                permissionEntity.setRoleId(roleId);
                permissionEntities.add(permissionEntity);
            }
            return this.insertList(permissionEntities);
        } else {
            return 0;
        }
    }
}