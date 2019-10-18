package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.sys.vo.UserVO;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.sys.vo.request.UserSelectVO;
import org.openokr.task.vo.TaskUserInfoVO;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public interface IUserService {

    UserVOExt getById(String id);

    UserVOExt getByUserName(String userName);

    Page findByPageLikeInputValue(Page page, String inputValue, UserVOExt user);

    ResponseResult resetPassword(String id);

    ResponseResult addOrModify(UserVOExt userVOExt, String userId);

    ResponseResult delete(String id);

    ResponseResult editPassword(String userId, String oldPassword, String newPassword, String confirmNewPassword);

    long countByOrganizationId(String organizationId);

    UserVOExt getTeamOwnerUserByTeamId(String teamId);

    /**
     * 获取用户选择列表
     * @return
     * @throws BusinessException
     */
    UserSelectVO getUserSelectInfo() throws BusinessException;

    /**
     * 获取任务关联人员信息
     * @param taskId
     * @return
     * @throws BusinessException
     */
    List<TaskUserInfoVO> getTaskUserInfoList(String taskId) throws BusinessException;

    /**
     * 获取用户权限list，用户id若为空查询所有用户，不为空则查询指定用户权限
     * @param userVO
     * @return
     * @throws BusinessException
     */
    List<UserVO> getUserRole(UserVO userVO) throws BusinessException;

    /**
     * 判断用户是否为管理员
     * @param userId
     * @return
     * @throws BusinessException
     */
    boolean checkUserIsAdmin(String userId) throws BusinessException;

    /**
     * 同步更新okr用户，来自ldap的用户与okr用户进行遍历比对，如果存在ldap存在的用户则新增okr对应的用户
     * 可使用 userRole | orgId 来筛选用于筛选的 okr 用户结果
     * @param userVOList
     * @param userRole 用户角色类型，用于筛选okr用户的角色
     *                 00 : ldap用户中 管理员  对应 yqb-okr-user 管理员用户
     *                 01 ：ldap用户中普通用户 对应 yqb-okr-user 普通用户
     *                 若存在 ldap 多于 okr 的用户，也将用该 userRole 创建新用户
     *                 相关关系在 RoleEnum 中进行对应与添加
     * @throws BusinessException
     */
    void mergeUserFromLdap(List<UserVO> userVOList, String userRole) throws BusinessException;


    /**
     * 根据用户角色+组织id筛选用户
     * @param role
     * @param orgId
     * @return
     * @throws BusinessException
     */
    List<UserVO> getUserListByCondition(String role, String orgId) throws BusinessException;

    /**
     * 同步来自 ldap 系统用户
     * @throws BusinessException
     */
    void syncUserFromLdap() throws BusinessException;
}