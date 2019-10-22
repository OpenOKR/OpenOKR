package org.openokr.sys.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.application.utils.PasswordUtil;
import org.openokr.enumerate.OrgEnum;
import org.openokr.enumerate.RoleEnum;
import org.openokr.sys.entity.UserEntity;
import org.openokr.sys.entity.UserEntityCondition;
import org.openokr.sys.vo.UserRoleVO;
import org.openokr.sys.vo.UserVO;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.sys.vo.request.UserSelectOgrVO;
import org.openokr.sys.vo.request.UserSelectUserVO;
import org.openokr.sys.vo.request.UserSelectVO;
import org.openokr.task.vo.TaskUserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzheng on 2018/12/18.
 */
@Service
@Transactional
public class UserService extends BaseServiceImpl implements IUserService {

    private static final String MAPPER_NAMESPACE = "org.openokr.sys.sqlmapper.UserMapper";

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IShortcutMenuService shortcutMenuService;

    @Value("${app.defaultPassword}")
    private String defaultPassword;

    @Value("${spring.profiles}")
    private String profile;

    @Override
    public UserVOExt getById(String id) {
        UserEntity userEntity = this.getMyBatisDao().selectByPrimaryKey(UserEntity.class, id);
        return BeanUtils.copyToNewBean(userEntity, UserVOExt.class);
    }

    @Override
    public UserVOExt getByUserName(String userName) {
        UserEntityCondition condition = new UserEntityCondition();
        condition.createCriteria().andUserNameEqualTo(userName);
        UserEntity userEntity = this.getMyBatisDao().selectOneByCondition(condition);
        return BeanUtils.copyToNewBean(userEntity, UserVOExt.class);
    }

    @Override
    public Page findByPageLikeInputValue(Page page, String inputValue, UserVOExt user) {
        Map<String, Object> parameterMap = new HashMap<>();
        if (StringUtils.isNotEmpty(inputValue)) {
            parameterMap.put("inputValue", "%" + inputValue + "%");
        }
        parameterMap.put("currentUser", user);
        parameterMap.put("page", page);
        //
        int totalRecord = this.getDao().selectOneBySql(MAPPER_NAMESPACE + ".countFindByPageLikeInputValue", parameterMap);
        List<UserVOExt> voList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".findByPageLikeInputValue", parameterMap);
        //
        page.setTotalRecord(totalRecord);
        page.setRecords(voList);
        return page;
    }

    @Override
    public ResponseResult resetPassword(String id) {
        UserEntityCondition condition = new UserEntityCondition();
        condition.createCriteria().andIdEqualTo(id);
        UserEntity entity = this.selectOneByCondition(condition);
        if (entity == null) {
            return new ResponseResult(false, null, "密码修改失败，该用户不存在");
        }
        //加密密码，加盐
        PasswordUtil.HashPassword hashPassword = PasswordUtil.encrypt(defaultPassword);
        entity.setSalt(hashPassword.getSalt());
        entity.setPassword(hashPassword.getPassword());
        //
        if (this.update(entity) > 0) {
            return new ResponseResult("密码修改成功");
        } else {
            return new ResponseResult(false, null, "密码修改失败");
        }
    }

    @Override
    public ResponseResult addOrModify(UserVOExt userVOExt, String userId) {
        UserEntity saveUserEntity = null;
        if (this.countByUsername(userVOExt.getId(), userVOExt.getUserName()) > 0) {
            ResponseResult result = new ResponseResult(false, null, "保存失败，" + userVOExt.getUserName() + " 已经存在");
            result.setCode("1");
            result.setSuccess(false);
            return result;
        }
        if (this.countByEmail(userVOExt.getId(), userVOExt.getEmail()) > 0) {
            ResponseResult result = new ResponseResult(false, null, "保存失败，Email：" + userVOExt.getEmail() + " 已经存在");
            result.setCode("1");
            result.setSuccess(false);
            return result;
        }
        if (this.countByPhone(userVOExt.getId(), userVOExt.getPhone()) > 0) {
            ResponseResult result = new ResponseResult(false, null, "保存失败，手机号：" + userVOExt.getPhone() + " 已经存在");
            result.setCode("2");
            result.setSuccess(false);
            return result;
        }
        if (userVOExt.getId() == null) {//新增
            //加密密码，加盐
            PasswordUtil.HashPassword hashPassword = PasswordUtil.encrypt(defaultPassword);
            userVOExt.setSalt(hashPassword.getSalt());
            userVOExt.setPassword(hashPassword.getPassword());
            //保存
            UserEntity entity = BeanUtils.copyToNewBean(userVOExt, UserEntity.class);
            entity.setCreateUserId(userId);
            entity.setCreateTs(new Date());
            saveUserEntity = this.saveWithQuery(entity);
        } else if (userVOExt.getId() != null) {//修改
            //保存
            UserEntity entity = BeanUtils.copyToNewBean(userVOExt, UserEntity.class);
            entity.setUpdateUserId(userId);
            entity.setUpdateTs(new Date());
            saveUserEntity = this.saveWithQuery(entity);
            //先删除
            if (StringUtils.isNotEmpty(saveUserEntity.getId())) {
                userRoleService.deleteByUserId(saveUserEntity.getId());
            }
        }
        if (saveUserEntity != null) {
            //添加角色
            userRoleService.deleteByUserId(saveUserEntity.getId());
            UserRoleVO userRoleVO = new UserRoleVO();
            userRoleVO.setUserId(saveUserEntity.getId());
            userRoleVO.setRoleId(userVOExt.getRoleId());
            userRoleVO.setCreateUserId(userId);
            userRoleVO.setCreateTs(new Date());
            userRoleService.add(userRoleVO);
            //
            return new ResponseResult(true, null, "保存成功");
        } else {
            return new ResponseResult(false, null, "保存失败");
        }
    }

    @Override
    public ResponseResult delete(String id) {
        if ("1".equals(id)) {
            return new ResponseResult(false, null, "删除失败，系统管理员不允许删除");
        }
        //删除关联的角色
        userRoleService.deleteByUserId(id);
        //
        if (this.deleteByPrimaryKey(UserEntity.class, id) > 0) {
            //删除用户的快捷菜单
            shortcutMenuService.deleteByUserId(id);
            //
            return new ResponseResult(true, null, "删除成功");
        } else {
            return new ResponseResult(false, null, "删除失败");
        }
    }

    @Override
    public ResponseResult editPassword(String userId, String oldPassword, String newPassword, String confirmNewPassword) {
        UserEntityCondition condition = new UserEntityCondition();
        condition.createCriteria().andIdEqualTo(userId);
        UserEntity entity = this.selectOneByCondition(condition);
        if (entity == null) {
            return new ResponseResult(false, null, "密码修改失败，该用户不存在");
        }
        // 旧密码验证
        if (!PasswordUtil.validaPassword(oldPassword, entity.getSalt(), entity.getPassword())) {
            return new ResponseResult(false, null, "旧密码输入错误");
        }
        if (!newPassword.equals(confirmNewPassword)) {
            return new ResponseResult(false, null, "两次新密码输入不一致");
        }
        //
        //加密密码，加盐
        PasswordUtil.HashPassword hashNewPassword = PasswordUtil.encrypt(newPassword);
        entity.setSalt(hashNewPassword.getSalt());
        entity.setPassword(hashNewPassword.getPassword());
        if (this.update(entity) > 0) {
            return new ResponseResult(true, null, "密码修改成功");
        } else {
            return new ResponseResult(false, null, "密码修改失败");
        }
    }

    @Override
    public long countByOrganizationId(String organizationId) {
        UserEntityCondition condition = new UserEntityCondition();
        condition.createCriteria().andOrganizationIdEqualTo(organizationId);
        return this.countByCondition(condition);
    }

    @Override
    public UserVOExt getTeamOwnerUserByTeamId(String teamId) {
        Map<String, Object> params = new HashMap<>();
        params.put("teamId", teamId);
        UserVOExt userVOExt = this.getMyBatisDao().selectOneBySql(MAPPER_NAMESPACE + ".getTeamOwnerUserByTeamId", params);
        return userVOExt;
    }

    @Override
    public UserSelectVO getUserSelectInfo() throws BusinessException {
        UserSelectVO userSelectVO = new UserSelectVO();
        try{
            List<UserSelectOgrVO> ogrVOS = this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + ".selectUserDeptInfoList", null);
            if(ogrVOS != null && !ogrVOS.isEmpty()){
                userSelectVO.setDeptList(ogrVOS);
                Map<String, Object> params;
                List<UserSelectUserVO> userVOS;
                for(UserSelectOgrVO ogrVO:ogrVOS){
                    params = new HashMap<>();
                    params.put("orgId", ogrVO.getDeptId());
                    userVOS = this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + ".selectUserInfoListByDeptId", params);
                    ogrVO.setUserList(userVOS);
                }
            }
        } catch (BusinessException e) {
            logger.error("获取用户选择列表 busi-error:{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("获取用户选择列表 error:{}-->", e.getMessage(), e);
            throw new BusinessException("获取用户选择列表 失败");
        }
        return userSelectVO;
    }

    @Override
    public List<TaskUserInfoVO> getTaskUserInfoList(String taskId) throws BusinessException {
        List<TaskUserInfoVO> taskUserInfoVOS;
        try{
            //1:参数校验
            if(StringUtils.isBlank(taskId)){
                throw new BusinessException("任务ID为空，请确认!");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("taskId", taskId);
            taskUserInfoVOS = this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + ".selectTaskUserInfoList", params);
        } catch (BusinessException e) {
            logger.error("获取任务关联人员信息 busi-error:{}-->[taskId]={}", e.getMessage(),taskId, e);
            throw e;
        } catch (Exception e) {
            logger.error("获取任务关联人员信息 error:{}-->[taskId]={}", e.getMessage(),taskId, e);
            throw new BusinessException("获取任务关联人员信息 失败");
        }
        return taskUserInfoVOS;
    }

    @Override
    public List<UserVO> getUserRole(UserVO userVO) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            if (userVO!=null && org.apache.commons.lang3.StringUtils.isNotBlank(userVO.getId())){
                params.put("userId", userVO.getId());
            } else {
                params.put("userId", null);
            }
            List<UserVO> userVOList = this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE+".getUserRole", params);
            return userVOList;
        } catch (BusinessException e) {
            logger.error("获取用户权限列表异常 busi-error:{}-->[userVO]={}", e.getMessage(), JSON.toJSONString(userVO), e);
            throw e;
        } catch (Exception e) {
            logger.error("获取用户权限列表异常 error:{}-->[userVO]={}", e.getMessage(),JSON.toJSONString(userVO), e);
            throw new BusinessException("获取用户权限列表 失败");
        }
    }

    @Override
    public boolean checkUserIsAdmin(String userId) throws BusinessException {
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(userId)){
                throw new BusinessException("用户ID为空!");
            }
            UserVO userVO = new UserVO();
            userVO.setId(userId);
            List<UserVO> userVOS = this.getUserRole(userVO);
            if(userVOS!=null && !userVOS.isEmpty()){
                for(UserVO vo:userVOS){
                    if("0".equals(vo.getRoleType().substring(0,1))){
                        return true;
                    }
                }
            }
            return false;
        } catch (BusinessException e) {
            logger.error("判断用户是否为管理员 异常 busi-error:{}-->[userVO]={}", e.getMessage(), userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("判断用户是否为管理员 异常 error:{}-->[userVO]={}", e.getMessage(),userId, e);
            throw new BusinessException("判断用户是否为管理员 失败");
        }
    }

    @Override
    public void mergeUserFromLdap(List<UserVO> userVOList, String userRole) throws BusinessException {
        try {
            logger.info("ldap 用户定时同步-开始");
            //根据入参查询okr现有符合条件的用户
            String userRoleId = RoleEnum.getRoleId(userRole,profile);
            // 如果是同步来自ldap的普通用户，那么查询okr这边用户时则不过滤角色，即找出okr中的所有角色的用户，
            // 避免ldap中用户为普通用户，在okr中为管理员，每次同步都会出现要在okr中新增来自ldap中的普通用户，
            // 但是okr又存在该用户，只是该用户为管理员，而导致的新增失败
            List<UserVO> okrUserVOList;
            if ("00".equals(userRole)) {
                // 在比对两边系统管理用户时则需要对okr系统的用户角色进行过滤
                okrUserVOList = getUserListByCondition(userRoleId, null);
            } else {
                okrUserVOList = getUserListByCondition(null, null);
            }
            //需要在okr中新增的用户列表
            List<UserVOExt> toAddUser = Lists.newArrayList();
            boolean isExist = false;
            for (UserVO ldapUser : userVOList) {
                for (UserVO okrUser : okrUserVOList) {
                    // 目前只匹配用户名字段，如果存在一个用户在两个部门，那么只会在 okr 中新增一个用户，所属部门取决于先遍历到哪个部门
                    // 这是潜在问题，因为当前只能用这个字段
                    if (ldapUser.getUserName().equals(okrUser.getUserName())) {
                        isExist = true;
                        break;
                    }
                }
                //okr中需要增加用户场景
                if (!isExist) {
                    UserVOExt userVO = new UserVOExt();
                    userVO.setActive(true);
                    userVO.setUserName(ldapUser.getUserName());
                    userVO.setRealName(ldapUser.getRealName());
                    userVO.setEmail(ldapUser.getEmail());
                    userVO.setPhone(ldapUser.getPhone());
                    // 根据 ldap 查询回来的部门名称模糊匹配 okr 的部门名称获得 okr 这边的orgId，匹配不到，则默认先设置为研发部门
                    userVO.setOrganizationId(OrgEnum.getOrgIdByDesc(profile,ldapUser.getOrganizationName()));
                    userVO.setRoleId(userRoleId);
                    userVO.setPassword(defaultPassword);
                    ResponseResult result = addOrModify(userVO, null);
                    logger.info("ldap 用户定时同步 新增用户 " + ldapUser.getUserName() + " 结果 ：" + result.getMessage());
                    toAddUser.add(userVO);
                }
                isExist = false;
            }
            if (toAddUser.size() > 0) {
                logger.info("ldap 用户定时同步-需要新增用户完成 userList：{}", JSON.toJSONString(toAddUser));
            } else {
                logger.info("ldap 用户定时同步-不存在需要新增的用户-流程结束 ");
            }
        } catch (BusinessException e) {
            logger.error("ldap 用户定时同步 异常 busi-error:{}-->[userVOList]={}", e.getMessage(), JSON.toJSONString(userVOList), e);
            throw e;
        } catch (Exception e) {
            logger.error("ldap 用户定时同步 异常 error:{}-->[userVOList]={}", e.getMessage(),JSON.toJSONString(userVOList), e);
            throw new BusinessException("ldap 用户定时同步 失败");
        }
    }

    @Override
    public List<UserVO> getUserListByCondition(String role, String orgId) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("roleId", role);
            params.put("orgId", orgId);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE+".getUserListByCondition", params);
        } catch (BusinessException e) {
            logger.error("根据条件筛选用户 异常 busi-error:{}-->[role]={},[orgId]={}", e.getMessage(), role, orgId, e);
            throw e;
        } catch (Exception e) {
            logger.error("根据条件筛选用户 异常 error:{}-->[role]={},[orgId]={}", e.getMessage(), role, orgId, e);
            throw new BusinessException("根据条件筛选用户 失败");
        }
    }

    @Override
    public void syncUserFromLdap() throws BusinessException {

    }

    private long countByUsername(String id, String username) {
        UserEntityCondition condition = new UserEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andUserNameEqualTo(username);
        } else {
            condition.createCriteria().andUserNameEqualTo(username);
        }
        return this.countByCondition(condition);
    }

    private long countByEmail(String id, String email) {
        UserEntityCondition condition = new UserEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andEmailEqualTo(email);
        } else {
            condition.createCriteria().andEmailEqualTo(email);
        }
        return this.countByCondition(condition);
    }

    private long countByPhone(String id, String phone) {
        UserEntityCondition condition = new UserEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andPhoneEqualTo(phone);
        } else {
            condition.createCriteria().andPhoneEqualTo(phone);
        }
        return this.countByCondition(condition);
    }
}