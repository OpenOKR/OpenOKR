package org.openokr.sys.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.PageResponseData;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.UserVO;
import org.openokr.task.request.TaskSearchVO;
import org.openokr.task.request.TeamTaskSearchVO;
import org.openokr.task.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * OKR报工--任务管理控制器
 *
 * @Author linaer
 * @Date 2019/03/01
 */
@Controller
@RequestMapping("/sys")
@Api(value = "系统基础通用服务接口",description = "SystemBasicController")
public class SysBasicController extends BaseController {
    @Autowired
    IUserService userService;

    @ApiOperation(value = "查询当前用户权限列表", notes = "查询当前用户权限列表")
    @ApiImplicitParams(
            {
            }
    )
    @RequestMapping(value = "/getCurrentUserRole.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<List<UserVO>> getCurrentUserRole() {
        ResponseData<List<UserVO>> result = new ResponseData<>();
        UserVO userVO = new UserVO();
        try {
            userVO.setId(this.getCurrentUserId());
            List<UserVO> userVOList = userService.getUserRole(userVO);
            result.setData(userVOList);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("查询当前用户权限列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(userVO), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("查询当前用户权限列表 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(userVO), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }



}