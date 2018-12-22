package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.MapUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.MenuEntity;
import org.openokr.sys.entity.MenuEntityCondition;
import org.openokr.sys.vo.MenuVOExt;
import org.openokr.sys.vo.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * Created by zhengzheng on 2018/12/18.
 */
@Service
@Transactional
public class MenuService extends BaseServiceImpl implements IMenuService {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private IShortcutMenuService shortcutMenuService;

    @Override
    public ResponseResult addOrModify(MenuVOExt menuVOExt, List<PermissionVO> permissionVOList) {
        MenuEntity saveEntity = null;
        if (StringUtils.isEmpty(menuVOExt.getParentId())) {
            return new ResponseResult("保存失败，上级菜单不能为空", false);
        }
        if (countName(menuVOExt.getId(), menuVOExt.getName()) > 0) {
            return new ResponseResult("保存失败，" + menuVOExt.getName() + " 已经存在", false);
        }
        if (countByPermissionPrefixCode(menuVOExt.getId(), menuVOExt.getPermissionPrefixCode()) > 0) {
            return new ResponseResult("保存失败，" + menuVOExt.getPermissionPrefixCode() + " 已经存在", false);
        }
        if (menuVOExt.getId() == null) {//新增
            //保存
            MenuEntity entity = BeanUtils.copyToNewBean(menuVOExt, MenuEntity.class);
            saveEntity = this.saveWithQuery(entity);
        } else if (menuVOExt.getId() != null) {//修改
            if (menuVOExt.getId().equals(menuVOExt.getParentId())) {
                return new ResponseResult("保存失败，当前菜单的上级不能是自己", false);
            }
            //保存
            MenuEntity entity = BeanUtils.copyToNewBean(menuVOExt, MenuEntity.class);
            saveEntity = this.saveWithQuery(entity);
        }
        //
        if (saveEntity != null) {
            ResponseResult responseResult = new ResponseResult();
            //保存(新增、修改、删除)权限
            if (permissionVOList != null && !permissionVOList.isEmpty()) {
                responseResult = permissionService.addModifyOrDelete(saveEntity.getId(), permissionVOList);
            }
            if (responseResult.isSuccess()) {
                return new ResponseResult("保存成功");
            } else {
                //主动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return responseResult;
            }
        } else {
            return new ResponseResult("保存失败", false);
        }
    }

    @Override
    public ResponseResult delete(String id) {
        if ("1".equals(id)) {
            return new ResponseResult("删除失败，不允许删除根菜单", false);
        }
        //判断子
        if (countChildrenByParentId(id) > 0) {
            return new ResponseResult("删除失败，存在子菜单关联，不允许删除", false);
        }
        //查询菜单权限Id集合
        Set<String> permissionIds = permissionService.findIdsByMenuId(id);
        //判断角色权限表是否关联
        ResponseResult responseResult = permissionService.countByPermissionIds(permissionIds);
        if (!responseResult.isSuccess()) {
            responseResult.setMessage(responseResult.getMessage() + "，不允许删除");
            return responseResult;
        }
        //删除菜单权限
        permissionService.deleteByMenuId(id);
        List<String> permissionIdList = new ArrayList<>();
        permissionIdList.addAll(permissionIds);
        //删除已配置的菜单权限的角色
        rolePermissionService.deleteByPermissionIds(permissionIdList);
        //删除快捷菜单关联
        shortcutMenuService.deleteByMenuId(id);
        //
        if (this.deleteByPrimaryKey(MenuEntity.class, id) > 0) {
            return new ResponseResult("删除成功");
        } else {
            return new ResponseResult("删除失败");
        }
    }

    @Override
    public List<MenuVOExt> findAll() {
        //需要联合查询 根菜单
        String sql = "SELECT * FROM (SELECT t.id,t.description,t.name,t.priority,t.url,t.permission_prefix_code AS \"permissionPrefixCode\",t.parent_id AS \"parentId\",t2.name AS \"parentName\"" +
                " FROM t_okr_sys_menu t, t_okr_sys_menu t2 WHERE t.parent_id = t2.id " +
                " UNION SELECT t3.id,t3.description,t3.name,t3.priority,t3.url,t3.permission_prefix_code AS \"permissionPrefixCode\",t3.parent_id AS \"parentId\",'' AS \"parentName\"" +
                " FROM t_okr_sys_menu t3 WHERE t3.id = '1' order by priority ) as ta where ta.\"parentName\" <> ''";
        List<Map<String, Object>> maps = this.getDao().selectListByDynamicSql(sql);
        return MapUtils.mapListToBeanList(maps, MenuVOExt.class);
    }

    @Override
    public List<MenuVOExt> findContainPermissionOfAll() {
        List<MenuVOExt> list = this.findAll();
        //加载 permission
        for (MenuVOExt vo : list) {
            vo.setPermissionVOExtList(permissionService.findByMenuId(vo.getId()));
        }
        return list;
    }

    @Override
    public MenuVOExt getById(String id) {
        String sql = "SELECT t.id,t.description,t.name,t.priority,t.url,t.parent_id as \"parentId\",t.permission_prefix_code as \"permissionPrefixCode\" FROM t_okr_sys_menu t where ( id = #{id} ) ";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("id", id);
        Map<String, Object> map = this.getDao().selectOneByDynamicSql(sql, parameterMap);
        MenuVOExt vo = MapUtils.mapToBean(map, MenuVOExt.class);
        //组装子数据
        buildChildren(vo);
        return vo;
    }

    @Override
    public List<MenuVOExt> findByUserId(String userId) {
        String sql = "SELECT t.id,t.description,t.name,t.priority,t.url,t.parent_id as \"parentId\", t.permission_prefix_code as \"permissionPrefixCode\" " +
                "FROM t_okr_sys_menu t,t_okr_sys_role_menu t1, t_okr_sys_user_role t2 " +
                "where t.id = t1.menu_id and t2.role_id = t1.role_id and t2.user_id = #{userId}";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Map<String, Object>> maps = this.getMyBatisDao().selectListByDynamicSql(sql, params);
        return MapUtils.mapListToBeanList(maps, MenuVOExt.class);
    }

    @Override
    public MenuVOExt findTreeOfViewByUserId(String userId) {
        List<MenuVOExt> list = this.findOfViewByUserId(userId);
        List<MenuVOExt> rootList = buildTree(list);
        return rootList.get(0);
    }

    @Override
    public List<MenuVOExt> findOfViewByUserId(String userId) {
        String sql = "SELECT t1.id, t1.description, t1.name, t1.priority, t1.url, t1.parent_id AS \"parentId\", t1.permission_prefix_code as \"permissionPrefixCode\"" +
                " FROM t_okr_sys_menu t1 WHERE" +
                " (t1.permission_prefix_code || ':view') IN" +
                " (SELECT t6.code FROM t_okr_sys_role_permission t2, t_okr_sys_role t3, t_okr_sys_user t4, t_okr_sys_user_role t5,t_okr_sys_permission t6" +
                " WHERE t3.id = t2.role_id AND t3.id = t5.role_id AND t4.id = t5.user_id AND t2.permission_id=t6.id AND t6.code LIKE '%view' AND t4.id = #{userId}) OR t1.id='1' "
                + " ORDER BY priority";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("userId", userId);
        List<Map<String, Object>> maps = this.getDao().selectListByDynamicSql(sql, parameterMap);
        return MapUtils.mapListToBeanList(maps, MenuVOExt.class);
    }

    /**
     * 组装子数据
     *
     * @param vo 实体vo
     */
    private void buildChildren(MenuVOExt vo) {
        List<MenuVOExt> childrenVos = this.findChildren(vo.getId());
        vo.setChildren(childrenVos);
        for (MenuVOExt childrenVo : childrenVos) {
            buildChildren(childrenVo);
        }
    }

    /**
     * 查找子数据
     *
     * @param id 主键
     */
    private List<MenuVOExt> findChildren(String id) {
        String sql = "SELECT t.id,t.description,t.name,t.priority,t.url,t.parent_id as \"parentId\",t.permission_prefix_code as \"permissionPrefixCode\" FROM t_okr_sys_menu t where ( parent_id = #{id} ) ";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("id", id);
        List<Map<String, Object>> map = this.getDao().selectListByDynamicSql(sql, parameterMap);
        return MapUtils.mapListToBeanList(map, MenuVOExt.class);
    }

    /**
     * 构造根结构数据
     *
     * @param allMenu 集合
     * @return 集合
     */
    private List<MenuVOExt> buildTree(List<MenuVOExt> allMenu) {
        List<MenuVOExt> rootMenus = new ArrayList<>();
        for (MenuVOExt rootMenu : allMenu) {
            //父ID为空 或者 等于自己，就是顶级
            if (rootMenu.getParentId() == null || rootMenu.getParentId().equals(rootMenu.getId())) {
                rootMenu.setChildren(new ArrayList<MenuVOExt>(0));
                rootMenus.add(rootMenu);
                break;
            }
        }
        // 删除parentId = null;
        if (rootMenus.isEmpty()) {
            throw new BusinessException("根菜单不能为空");
        }
        allMenu.removeAll(rootMenus);
        makeChildren(rootMenus, allMenu);
        return rootMenus;
    }

    /**
     * 组装子数据
     *
     * @param parent   父集合
     * @param children 子集合
     */
    private void makeChildren(List<MenuVOExt> parent, List<MenuVOExt> children) {
        if (children.isEmpty()) {
            return;
        }
        List<MenuVOExt> tmp = new ArrayList<>();
        for (MenuVOExt vo1 : parent) {
            for (MenuVOExt vo2 : children) {
                vo2.setChildren(new ArrayList<MenuVOExt>(0));
                if (vo1.getId().equals(vo2.getParentId())) {
                    vo1.getChildren().add(vo2);
                    tmp.add(vo2);
                }
            }
        }
        if (!tmp.isEmpty()) {
            children.removeAll(tmp);
            makeChildren(tmp, children);
        }
    }

    /**
     * 统计 name 一样的数量
     * @param id
     * @param name
     * @return
     */
    private long countName(String id, String name) {
        MenuEntityCondition condition = new MenuEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name);
        } else {
            condition.createCriteria().andNameEqualTo(name);
        }
        return this.countByCondition(condition);
    }

    /**
     * 统计 permission_prefix_code 一样的数量
     * @param id
     * @param permissionPrefixCode
     * @return
     */
    private long countByPermissionPrefixCode(String id, String permissionPrefixCode) {
        MenuEntityCondition condition = new MenuEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andPermissionPrefixCodeEqualTo(permissionPrefixCode);
        } else {
            condition.createCriteria().andPermissionPrefixCodeEqualTo(permissionPrefixCode);
        }
        return this.countByCondition(condition);
    }

    /**
     * 统计子节点的数量
     * @param parentId
     * @return
     */
    private long countChildrenByParentId(String parentId) {
        MenuEntityCondition condition = new MenuEntityCondition();
        condition.createCriteria().andParentIdEqualTo(parentId);
        return this.countByCondition(condition);
    }
}