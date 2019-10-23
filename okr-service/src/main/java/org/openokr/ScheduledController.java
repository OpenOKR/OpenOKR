package org.openokr;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.openokr.enumerate.RoleEnum;
import org.openokr.ldap.ILdapUserService;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.UserListDataVO;
import org.openokr.sys.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description : 定时任务
 * @author: cww
 * @DateTime: 2018/12/28 20:16
 */
@Slf4j
@Component
public class ScheduledController {

    private Logger logger = LoggerFactory.getLogger(ScheduledController.class);
    @Value("${app.ldap.adminUser}")
    private String account;
    @Value("${app.ldap.pwd}")
    private String pwd;
    @Value("${app.ldap.sync-cycle}")
    private String cycle;
    @Value("${spring.profiles}")
    private String profile;
    @Autowired
    private ILdapUserService ldapUserService;
    @Autowired
    private IUserService userService;
    //
    @Scheduled(cron = "0 */1 * * * ?")
    public void uploadAirDailyUseData(){
        // 遍历用户角色 RoleEnum.getRoleArrayByProfile 进行同步 ldap 用户，如果要添加用户角色，
        // 除了在枚举类中添加相关角色信息，还需要在 org.openokr.ldap.LdapUserService.getUserByFilter
        // 中添加ldap过滤规则
        try {
            logger.info("定时同步来自 ldap 中的用户-开始");
            for (String role : RoleEnum.getRoleArrayByProfile(profile)) {
                String userListJsonStr = ldapUserService.getUserByFilter(account, pwd, role);
                UserListDataVO userListDataVO = JSON.parseObject(userListJsonStr, UserListDataVO.class);
                List<UserVO> userVOList = userListDataVO.getUserList();
                userService.mergeUserFromLdap(userVOList, role);
            }
            logger.info("定时同步来自 ldap 中的用户-结束");
        } catch (Exception e) {
            logger.warn("同步用户-失败 cause ：" + e.getCause());
        }
    }
}
