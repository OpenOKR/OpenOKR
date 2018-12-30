package org.openokr.manage.web;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.manage.enumerate.ExecuteStatusEnum;
import org.openokr.manage.enumerate.ResultMetricUnitEnum;
import org.openokr.manage.service.IOkrResultService;
import org.openokr.manage.vo.CheckinsExtVO;
import org.openokr.manage.vo.ResultsExtVO;
import org.openokr.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * OKR关键结果
 *
 * @author hjh
 */
@Controller
@RequestMapping("/manage/okrResult")
public class OkrResultController extends BaseController {

    @Autowired
    private IOkrResultService okrResultService;

    /**
     * 新增或编辑关键结果页面
     * @return
     */
    @RequestMapping(value = "/okrResultForm.htm")
    public String okrResultForm(Model model, String resultId, String objectId) {
        ResultsExtVO resultVO = okrResultService.editResult(resultId, objectId);
        if (resultVO == null) {
            resultVO = new ResultsExtVO();
            resultVO.setObjectId(objectId);
        }
        model.addAttribute("metricUnitEnumList", ResultMetricUnitEnum.toList());
        model.addAttribute("resultVO", resultVO);
        model.addAttribute("endTs", resultVO.getEndTs() == null ? "" : DateUtils.formatDate(resultVO.getEndTs(),
                com.zzheng.framework.base.utils.DateUtils.yyyy_MM_dd));
        return "manage/okrResultForm";
    }

    /**
     * 保存关键结果
     * @return
     */
    @RequestMapping(value = "/saveResult.json")
    @ResponseBody
    public ResponseResult saveResult(@JsonPathParam("$.resultVO") ResultsExtVO resultVO) {
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
    public ResponseResult deleteResult(String resultId) {
        ResponseResult responseResult = okrResultService.deleteResult(resultId, getCurrentUserId());
        return responseResult;
    }

    /**
     * 编辑KR进度页面
     * @return
     */
    @RequestMapping(value = "/checkinsForm.htm")
    public String checkinsForm(Model model, String resultId, String objectId) {
        model.addAttribute("resultId", resultId);
        model.addAttribute("executeStatusEnum", ExecuteStatusEnum.toList());
        ResultsExtVO resultsExtVO = okrResultService.editResult(resultId, objectId);
        model.addAttribute("resultVO", resultsExtVO);
        return "manage/checkinsForm";
    }

    /**
     * 保存KR进度
     * @return
     */
    @RequestMapping(value = "/saveCheckins.json")
    @ResponseBody
    public ResponseResult saveCheckins(@JsonPathParam("$.checkinVO") CheckinsExtVO checkinsVO) {
        checkinsVO.setCreateUserId(getCurrentUserId());
        checkinsVO.setCreateTs(new Date());
        ResponseResult responseResult = okrResultService.saveCheckins(checkinsVO);
        return responseResult;
    }
}