package org.openokr.application.web;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.web.util.WebUtils;
import org.openokr.application.shiro.filter.FormAuthenticationFilter;
import org.openokr.application.shiro.AuthRealm;
import org.openokr.application.shiro.RSAService;
import org.openokr.utils.IdGen;
import org.openokr.utils.StringUtils;
import org.openokr.utils.UserUtils;
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
        return "common/login";
    }

    @GetMapping(value = "/login.htm")
    public String login(HttpServletRequest request, Model model) {
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
            return "redirect:/";
        }

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
            message = "用户或密码错误, 请重试.";
        }

        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        modelMap.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

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
}