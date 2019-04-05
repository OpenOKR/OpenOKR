package org.openokr.application.exception;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * BmsExceptionHandler
 * @author zhouyw
 * @date 2017.08.22
 */
@ControllerAdvice
public class BmsExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * biz
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public String bizErrorHandler(BusinessException e, Model model){
        logger.error("biz error-->e={}", e.getMessage(),e);
        model.addAttribute("errMsg",e.getMessage());
        return "/error";
    }
    /**
     * 权限未受权
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public ResponseResult jsonErrorHandler(HttpServletRequest req, UnauthorizedException e){
        logger.error("未授权", e);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setMessage("未授权");
        return responseResult;
    }
}