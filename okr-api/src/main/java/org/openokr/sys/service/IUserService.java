package org.openokr.sys.service;

import org.openokr.sys.vo.UserVOExt;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public interface IUserService {

    UserVOExt getById(String id);

    UserVOExt getByUserName(String userName);
}