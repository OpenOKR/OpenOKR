package org.openokr.sys.service;

import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.UserEntity;
import org.openokr.sys.entity.UserEntityCondition;
import org.openokr.sys.vo.UserVOExt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhengzheng on 2018/12/18.
 */
@Service
@Transactional
public class UserService extends BaseServiceImpl implements IUserService {

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
}