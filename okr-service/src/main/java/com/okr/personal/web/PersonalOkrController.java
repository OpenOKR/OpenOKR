package com.okr.personal.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 个人OKR
 *
 * @author hjh
 */
@Controller
@RequestMapping("/personal")
public class PersonalOkrController {

    /**
     * 个人OKR初始化页面
     *
     * @return
     */
    @RequestMapping(value = "/init.htm")
    public String personalOkr(Model model) {
        return "personal/personalOkr";
    }
}