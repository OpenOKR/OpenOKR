package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.sys.vo.UserVOExt;

import java.util.Set;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public interface IUserService {

    UserVOExt getById(String id);

    UserVOExt getByUserName(String userName);

    Set<String> findPermissionById(String id);

    Page findByPageLikeInputValue(Page page, String inputValue, UserVOExt user);

    ResponseResult resetPassword(String id);

    ResponseResult addOrModify(UserVOExt userVOExt, String userId);

    ResponseResult delete(String id);

    ResponseResult updatePassword(String userId, String oldPassword, String newPassword, String confirmNewPassword);
}