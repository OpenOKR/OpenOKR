package org.openokr.manage.web;

import org.openokr.application.web.BaseController;
import org.openokr.manage.service.IOkrTimeSessionsService;
import org.openokr.manage.vo.TimeSessionsExtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * OKR标签管理
 *
 * @author hjh
 */
@Controller
@RequestMapping("/manage/okrTimeSessions")
public class OkrTimeSessionsController extends BaseController {

    @Autowired
    private IOkrTimeSessionsService okrTimeSessionsService;


    /**
     * 获取时间段列表
     */
    @RequestMapping(value = "/getTimeSessionList.json")
    @ResponseBody
    public List<TimeSessionsExtVO> getTimeSessionList() {
        return okrTimeSessionsService.getTimeSessionList();
    }
}