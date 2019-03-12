package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.sys.vo.request.UserSelectVO;

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

    ResponseResult updatePassword(String userId, String oldPassword, String newPassword, String confirmNewPassword);

    long countByOrganizationId(String organizationId);

    UserVOExt getTeamOwnerUserByTeamId(String teamId);

    /**
     * 获取用户选择列表
     * @return
     * @throws BusinessException
     */
    UserSelectVO getUserSelectInfo() throws BusinessException;
}