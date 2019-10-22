package org.openokr.sys.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Desc:
 * @author: cww
 * @DateTime: 2019/10/16 17:18
 */
@Data
public class UserListDataVO {
    List<UserVO> userList = Lists.newArrayList();

}
