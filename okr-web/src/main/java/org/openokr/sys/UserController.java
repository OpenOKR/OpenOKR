package org.openokr.sys;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.UserVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequiresPermissions("User:view")
    @RequestMapping(value = "/sys/user.htm")
    public String index(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "sys/user";
    }

    @RequiresPermissions("User:view")
    @RequestMapping(value = "/sys/user/findByPageLikeInputValue.json")
    @ResponseBody
    public Page findByPageLikeInputValue(int currentPage, int pageSize, String inputValue) {
        Page page = new Page(currentPage, pageSize);
        return userService.findByPageLikeInputValue(page, inputValue, getCurrentUser());
    }

    @RequiresPermissions("User:edit")
    @RequestMapping(value = "/sys/user/resetPassword.json")
    @ResponseBody
    public ResponseResult resetPassword(String userId) {
        return userService.resetPassword(userId);
    }

    @RequiresPermissions("User:edit")
    @RequestMapping(value = "/sys/user/save.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult save(@JsonPathParam("$.vo") UserVOExt vo) {
        return userService.addOrModify(vo, getCurrentUserId());
    }

    @RequiresPermissions("User:delete")
    @RequestMapping(value = "/sys/user/delete.json")
    @ResponseBody
    public ResponseResult delete(String id) {
        return userService.delete(id);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/sys/user/profile.htm")
    public String profile() {
        return "sys/user_profile";
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/sys/user/updatePassword.json")
    @ResponseBody
    public ResponseResult updatePassword(String oldPassword, String newPassword, String confirmNewPassword) {
        return userService.updatePassword(this.getCurrentUserId(), oldPassword, newPassword, confirmNewPassword);
    }
}