package org.openokr.ldap;

import com.zzheng.framework.exception.BusinessException;

/**
 * @Desc:   关于 ldap 的用户相关服务
 * @author: cww
 * @DateTime: 2019/10/21 14:38
 */

public interface ILdapUserService {

    /**
     * 验证 ldap 用户权限
     * @param ldapAccount
     * @param ldapPwd
     * @return 返回用户列表信息 json 字符串
     * @throws BusinessException
     */
    String userPermissionValidation(String ldapAccount, String ldapPwd) throws BusinessException;


    /**
     * 使用某个 ldap 账户 查询指定 ldap 角色的用户列表
     * @param ldapAccount 用于连接 ldap 系统的账户
     * @param ldapPwd
     * @param findRole 要查询的用户角色类型 00 : ldap用户中 管理员 | 01 ldap用户中 yqb-okr-user 普通用户
     * @return  返回用户列表信息 json 字符串
     * @throws BusinessException
     */
    String getUserByFilter(String ldapAccount, String ldapPwd, String findRole) throws BusinessException;
}
