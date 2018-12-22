package org.openokr.application.web;

import org.openokr.utils.StringUtils;
import org.openokr.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by zhengzheng on 2018/12/19.
 */
@Controller
public class IndexController extends BaseController {

    @GetMapping(value = "/index.htm")
    public String index(Model model) throws Exception {
        String userName = getCurrentUser().getRealName();
        if (StringUtils.isEmpty(userName)) {
            userName = UserUtils.getUser().getUserName();
        }
        model.addAttribute("userName", userName);
        return "common/index";
    }

    @GetMapping(value = "/main.htm")
    public String main(Model model) {
        return "common/main";
    }
}