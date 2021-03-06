package org.openokr.sys.web;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.sys.vo.request.UserSelectVO;
import org.openokr.task.vo.MyTaskCountInfoVO;
import org.openokr.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sys")
@Api(value = "用户管理相关接口",description = "UserController")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequiresPermissions("User:view")
    @RequestMapping(value = "/user.htm")
    public String index(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "sys/user";
    }

    @RequiresPermissions("User:view")
    @RequestMapping(value = "/user/findByPageLikeInputValue.json")
    @ResponseBody
    public Page findByPageLikeInputValue(int currentPage, int pageSize, String inputValue) {
        Page page = new Page(currentPage, pageSize);
        return userService.findByPageLikeInputValue(page, inputValue, getCurrentUser());
    }

    @RequiresPermissions("User:edit")
    @RequestMapping(value = "/user/resetPassword.json")
    @ResponseBody
    public ResponseResult resetPassword(String userId) {
        return userService.resetPassword(userId);
    }

    @RequiresPermissions("User:edit")
    @RequestMapping(value = "/user/save.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult save(@JsonPathParam("$.vo") UserVOExt vo) {
        UserUtils.clearCache(vo);
        return userService.addOrModify(vo, getCurrentUserId());
    }

    @RequiresPermissions("User:delete")
    @RequestMapping(value = "/user/delete.json")
    @ResponseBody
    public ResponseResult delete(String id) {
        return userService.delete(id);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/user/profile.htm")
    public String profile() {
        return "sys/user_profile";
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/user/editPassword.json")
    @ResponseBody
    public ResponseResult editPassword(@JsonPathParam("$.oldPassword") String oldPassword,
                                       @JsonPathParam("$.newPassword") String newPassword,
                                       @JsonPathParam("$.confirmPassword") String confirmNewPassword) {
        return userService.editPassword(this.getCurrentUserId(), oldPassword, newPassword, confirmNewPassword);
    }

    @ApiOperation(value = "获取用户选择列表", notes = "获取用户选择列表")
    @RequestMapping(value = "/user/getUserSelectInfo.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<UserSelectVO> getUserSelectInfo() {
        ResponseData<UserSelectVO> result = new ResponseData<>();
        try{
            UserSelectVO userSelectVO = userService.getUserSelectInfo();
            result.setData(userSelectVO);
            result.setCode(0);
        }catch (BusinessException e){
            logger.error("获取用户选择列表 异常", e.getMessage(), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("获取用户选择列表 异常", e.getMessage(), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}