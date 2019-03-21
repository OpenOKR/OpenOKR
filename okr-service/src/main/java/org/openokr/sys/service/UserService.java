package org.openokr.sys.service;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.application.utils.PasswordUtil;
import org.openokr.sys.entity.UserEntity;
import org.openokr.sys.entity.UserEntityCondition;
import org.openokr.sys.vo.UserRoleVO;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.sys.vo.request.UserSelectOgrVO;
import org.openokr.sys.vo.request.UserSelectUserVO;
import org.openokr.sys.vo.request.UserSelectVO;
import org.openokr.task.vo.DailyVO;
import org.openokr.task.vo.TaskUserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
            return new ResponseResult("密码修改失败，该用户不存在", false);
        }
        //加密密码，加盐
        PasswordUtil.HashPassword hashPassword = PasswordUtil.encrypt(defaultPassword);
        entity.setSalt(hashPassword.getSalt());
        entity.setPassword(hashPassword.getPassword());
        //
        if (this.update(entity) > 0) {
            return new ResponseResult("密码修改成功");
        } else {
            return new ResponseResult("密码修改失败", false);
        }
    }

    @Override
    public ResponseResult addOrModify(UserVOExt userVOExt, String userId) {
        UserEntity saveUserEntity = null;
        if (this.countByUsername(userVOExt.getId(), userVOExt.getUserName()) > 0) {
            ResponseResult result = new ResponseResult("保存失败，" + userVOExt.getUserName() + " 已经存在", false);
            result.setCode("1");
            result.setSuccess(false);
            return result;
        }
        if (this.countByEmail(userVOExt.getId(), userVOExt.getEmail()) > 0) {
            ResponseResult result = new ResponseResult("保存失败，Email：" + userVOExt.getEmail() + " 已经存在", false);
            result.setCode("1");
            result.setSuccess(false);
            return result;
        }
        if (this.countByPhone(userVOExt.getId(), userVOExt.getPhone()) > 0) {
            ResponseResult result = new ResponseResult("保存失败，手机号：" + userVOExt.getPhone() + " 已经存在", false);
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
            return new ResponseResult("保存成功");
        } else {
            return new ResponseResult("保存失败", false);
        }
    }

    @Override
    public ResponseResult delete(String id) {
        if ("1".equals(id)) {
            return new ResponseResult("删除失败，系统管理员不允许删除", false);
        }
        //删除关联的角色
        userRoleService.deleteByUserId(id);
        //
        if (this.deleteByPrimaryKey(UserEntity.class, id) > 0) {
            //删除用户的快捷菜单
            shortcutMenuService.deleteByUserId(id);
            //
            return new ResponseResult("删除成功");
        } else {
            return new ResponseResult("删除失败", false);
        }
    }

    @Override
    public ResponseResult updatePassword(String userId, String oldPassword, String newPassword, String confirmNewPassword) {
        UserEntityCondition condition = new UserEntityCondition();
        condition.createCriteria().andIdEqualTo(userId);
        UserEntity entity = this.selectOneByCondition(condition);
        if (entity == null) {
            return new ResponseResult("密码修改失败，该用户不存在", false);
        }
        /*//加密密码，加盐
        PasswordUtil.HashPassword hashOldPassword = PasswordUtil.encrypt(oldPassword);
        if (!hashOldPassword.password.equals(entity.getPassword())) {
            return new ResponseResult("旧密码不正确", false);
        }*/
        if (!newPassword.equals(confirmNewPassword)) {
            return new ResponseResult("两次新密码输入不一致", false);
        }
        //
        //加密密码，加盐
        PasswordUtil.HashPassword hashNewPassword = PasswordUtil.encrypt(newPassword);
        entity.setSalt(hashNewPassword.getSalt());
        entity.setPassword(hashNewPassword.getPassword());
        if (this.update(entity) > 0) {
            return new ResponseResult("密码修改成功");
        } else {
            return new ResponseResult("密码修改失败", false);
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