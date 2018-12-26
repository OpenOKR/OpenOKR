package org.openokr.application.shiro.filter;

import com.zzheng.framework.spring.context.utils.SpringContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.openokr.application.shiro.RSAService;
import org.openokr.application.shiro.UsernamePasswordToken;
import org.openokr.utils.CacheUtils;
import org.openokr.utils.StringUtils;
import org.openokr.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by baozi on 2017/6/12.
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    private static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_MESSAGE_PARAM = "message";
    public static final String DEFAULT_LOGIN_FAIL_COUNT = "loginFailCount";
    public static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private String messageParam = DEFAULT_MESSAGE_PARAM;
    private String loginFailCount = DEFAULT_LOGIN_FAIL_COUNT;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return this.executeLogin(request, response);
            } else {
                if ("XMLHttpRequest".equalsIgnoreCase(WebUtils.toHttp(request).getHeader("X-Requested-With"))) {
                    HttpServletResponse res = WebUtils.toHttp(response);
                    if (!res.isCommitted()) {
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                }
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }
            this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);

        RSAService rsaService = SpringContextUtil.getBean(RSAService.class);
        String password = rsaService.decryptParam("password", (HttpServletRequest) request); // rsa
        rsaService.removePrivateKey((HttpServletRequest) request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
        String captcha = getCaptcha(request);
        boolean mobile = isMobileLogin(request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getMobileLoginParam() {
        return mobileLoginParam;
    }

    protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

    public String getMessageParam() {
        return messageParam;
    }

    public String getLoginFailCount() {
        return loginFailCount;
    }

    /**
     * 登录成功之后跳转URL
     */
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_FAIL_COUNT_KEY + getUsername(request));
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }

    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className)
                || UnknownAccountException.class.getName().equals(className)) {
            message = "用户或密码错误, 请重试.";
        } else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        } else {
            message = "系统出现点问题，请稍后再试！";
            e.printStackTrace(); // 输出到控制台
        }
        String userName = getUsername(request);
        Object failCountObj = CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_FAIL_COUNT_KEY + userName);
        if (failCountObj == null) {
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_FAIL_COUNT_KEY + userName, 1);
        } else {
            Integer failCount = Integer.parseInt(failCountObj.toString()) + 1;
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_FAIL_COUNT_KEY + userName, failCount);
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        request.setAttribute(getLoginFailCount(), failCountObj);
        return true;
    }
}