package org.openokr.personal;

import org.openokr.personal.service.IPersonalOkrService;
import org.openokr.ssm.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IPersonalOkrService personalOkrService;

    /**
     * 个人OKR初始化页面
     *
     * @return
     */
    @RequestMapping(value = "/init.htm")
    public String personalOkr(Model model) {
        MenuVO menuVO = new MenuVO();
        menuVO = personalOkrService.getPersonalOkr(menuVO);
        return "personal/personalOkr";
    }
}