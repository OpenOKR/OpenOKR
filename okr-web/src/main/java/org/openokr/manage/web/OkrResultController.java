package org.openokr.manage.web;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.application.web.BaseController;
import org.openokr.manage.service.IOkrResultService;
import org.openokr.manage.vo.ResultsExtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * OKR关键结果
 *
 * @author hjh
 */
@Controller
@RequestMapping("manage/okrResult")
public class OkrResultController extends BaseController {

    @Autowired
    private IOkrResultService okrResultService;

    /**
     * 新增或编辑关键结果页面
     * @return
     */
    @RequestMapping(value = "/okrResultForm.htm")
    @ResponseBody
    public String okrResultForm(Model model, String resultId) {
        if(!StringUtils.isEmpty(resultId)) {
            ResultsExtVO resultVO = okrResultService.editResult(resultId);
            model.addAttribute("resultVO", resultVO);
        }
        return "manage/okrResultForm";
    }

    /**
     * 保存关键结果
     * @return
     */
    @RequestMapping(value = "/saveResult.json")
    @ResponseBody
    public ResponseResult saveResult(ResultsExtVO resultVO) {
        resultVO.setCreateUserId(getCurrentUserId());
        ResponseResult responseResult = okrResultService.saveResult(resultVO);
        return responseResult;
    }

    /**
     * 删除关键结果
     * @return
     */
    @RequestMapping(value = "/deleteResult.json")
    @ResponseBody
    public ResponseResult deleteResult(String resultId, String userId) {
        ResponseResult responseResult = okrResultService.deleteResult(resultId, userId);
        return responseResult;
    }

}