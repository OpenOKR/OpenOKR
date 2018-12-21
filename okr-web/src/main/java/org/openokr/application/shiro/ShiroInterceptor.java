package org.openokr.application.shiro;

import com.zzheng.framework.spring.context.utils.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhengzheng on 2018/5/14.
 */
public class ShiroInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ShiroInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        IUserService userService = SpringContextUtil.getBean(IUserService.class);
        log.debug("进行系统用户管理拦截，处理相关权限问题。");
        // 1. SecurityUtils获取session中的用户信息
        // HttpSession session=request.getSession();
        try {
            AuthRealm.Principal principal = (AuthRealm.Principal) SecurityUtils.getSubject().getPrincipal();
            if (principal != null && StringUtils.isNotEmpty(principal.getUserName())) {
                // 2. 获取数据库中的用户数据
                UserVOExt dataUser = userService.getByUserName(principal.getUserName());
                // 3. 对比session中用户的version和数据库中的是否一致
                if (dataUser != null) {
                    // 3.1 一样，放行
                    return true;
                } else {
                    // 3.2 不一样，这里统一做退出登录处理；//TODO 使用redis缓存用户权限数据，根据用户更新、用户权限更新；做对应的处理。
                    SecurityUtils.getSubject().logout();
                    // 重定向
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
                }
            }
        } catch (Exception e) {
            // 3.2 不一样，这里统一做退出登录处理；//TODO 使用redis缓存用户权限数据，根据用户更新、用户权限更新；做对应的处理。
            SecurityUtils.getSubject().logout();
            // 重定向
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}