package org.openokr.manage.web;

import org.openokr.application.web.BaseController;
import org.openokr.manage.service.IOkrLabelService;
import org.openokr.manage.vo.LabelVO;
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
@RequestMapping("/manage/okrLabel")
public class OkrLabelController extends BaseController {

    @Autowired
    private IOkrLabelService okrLabelService;


    /**
     * 获取所有标签数据
     */
    @RequestMapping(value = "/getAllLabel.json")
    @ResponseBody
    public List<LabelVO> getAllLabel() {
        return okrLabelService.getAllLabel();
    }
}