package org.openokr.application.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.openokr.application.shiro.AuthRealm;
import org.openokr.application.shiro.RSAService;
import org.openokr.application.shiro.ShiroConfiguration;
import org.openokr.application.shiro.UsernamePasswordToken;
import org.openokr.application.shiro.filter.FormAuthenticationFilter;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.sys.vo.request.UserLoginVO;
import org.openokr.utils.*;
import org.openokr.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzheng on 2018/12/19.
 */
@Controller
@Api(value = "登录控制器相关接口",description = "LoginController")
public class LoginController extends BaseController {

    @Autowired
    private RSAService rsaService;

    /**
     * 登录页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String root(HttpServletRequest request, Model model) {
        AuthRealm.Principal principal = UserUtils.getPrincipal();

        //如果已登录,跳转到管理首页
        if (principal != null && !principal.isMobileLogin()) {
            return "redirect:/index.htm";
        }
        Object failCountObj = CacheUtils.get(UserUtils.USER_CACHE,
                UserUtils.USER_CACHE_LOGIN_FAIL_COUNT_KEY + CookieUtils.getCookie(request, ShiroConfiguration.COOKIE_NAME));
        model.addAttribute(FormAuthenticationFilter.DEFAULT_LOGIN_FAIL_COUNT, failCountObj);
        return "common/login";
    }

    @GetMapping(value = "/login.htm")
    public String login(HttpServletRequest request, Model model) {
        Object failCountObj = CacheUtils.get(UserUtils.USER_CACHE,
                UserUtils.USER_CACHE_LOGIN_FAIL_COUNT_KEY + CookieUtils.getCookie(request, ShiroConfiguration.COOKIE_NAME));
        model.addAttribute(FormAuthenticationFilter.DEFAULT_LOGIN_FAIL_COUNT, failCountObj);
        return "common/login";
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/login.htm")
    public String fail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        AuthRealm.Principal principal = UserUtils.getPrincipal();

        // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            return "redirect:/index.htm";
        }

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        Integer loginFailCount = (Integer) request.getAttribute(FormAuthenticationFilter.DEFAULT_LOGIN_FAIL_COUNT);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
            message = "用户或密码错误, 请重试.";
        }

        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_LOGIN_FAIL_COUNT, loginFailCount);

        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

        // 如果是手机登录，则返回JSON字符串
        if (mobile) {
            return renderString(response, modelMap);
        }
        return "common/login";
    }

    /**
     * 获取RSA秘钥
     * @param request
     * @return
     */
    @GetMapping(value = "pkey.json")
    public @ResponseBody Map<String, String> publicKey(HttpServletRequest request) {
        RSAPublicKey rsaPublicKey = rsaService.generateKey(request);
        Map<String, String> ex = new HashMap<String, String>();
        ex.put("modulus", Base64.encodeBase64String(rsaPublicKey.getModulus().toByteArray()));
        ex.put("exponent", Base64.encodeBase64String(rsaPublicKey.getPublicExponent().toByteArray()));
        return ex;
    }


    /**
     * 登录失败
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "登录接口",notes = "登录接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "username",value = "用户名",dataType = "string"),
                    @ApiImplicitParam(name = "password",value = "密码",dataType = "string")
            }
    )
    @RequestMapping(value = "/devLogin.json",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData devLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLoginVO userLoginVO) {
        ResponseData result = new ResponseData<>();
        try {
            //得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
            Subject subject = SecurityUtils.getSubject();
            //char[] passwordArr = new char[]{'1','2','3','4','5','6'};
            char[] passwordArr = userLoginVO.getPassword().toCharArray();
            UsernamePasswordToken token = new UsernamePasswordToken(
                    userLoginVO.getUsername(),  //登录用户名
                    passwordArr,false,"0:0:0:0:0:0:0:1",null,false); //登录密码
/*        token.setRememberMe(false);
        token.setHost("0:0:0:0:0:0:0:1");*/
            //登录，即身份验证
            subject.login(token);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("接口登录 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(userLoginVO), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("接口登录 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(userLoginVO), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }
    /**
     * 登录失败
     * @return
     */
    @ApiOperation(value = "验证是否登录接口",notes = "验证是否登录接口")
    @RequestMapping(value = "/checkLoginStatus.json",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<Integer> checkLoginStatus() {
        ResponseData result = new ResponseData<>();
        try {
            if(org.apache.commons.lang3.StringUtils.isBlank(this.getCurrentUserId())){
                result.setData(0);
            }else{
                result.setData(1);
            }
            //登录，即身份验证
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("验证是否登录接口 异常：{},参数:[{}]", e.getMessage(),e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("验证是否登录接口 异常：{},参数:[{}]", e.getMessage(), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}