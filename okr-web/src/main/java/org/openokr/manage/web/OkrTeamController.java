package org.openokr.manage.web;

import org.openokr.application.web.BaseController;
import org.openokr.manage.service.IOkrTeamService;
import org.openokr.manage.vo.TeamsExtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * OKR团队管理
 *
 * @author hjh
 */
@Controller
@RequestMapping("/manage/okrTeam")
public class OkrTeamController extends BaseController {

    @Autowired
    private IOkrTeamService okrTeamService;


    /**
     * 获取用户的所有团队信息
     */
    @RequestMapping(value = "/getTeamList.json")
    @ResponseBody
    public List<TeamsExtVO> getTeamList() {
        return okrTeamService.getTeamByUserId(getCurrentUserId());
    }
}