package org.openokr.manage.web;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.manage.service.IOkrObjectService;
import org.openokr.manage.service.IOkrTeamService;
import org.openokr.manage.vo.ObjectivesExtVO;
import org.openokr.manage.vo.OkrObjectSearchVO;
import org.openokr.manage.vo.TeamsExtVO;
import org.openokr.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * OKR目标管理
 *
 * @author hjh
 */
@Controller
@RequestMapping("/manage/okrObject")
public class OkrObjectController extends BaseController {

    @Autowired
    private IOkrObjectService okrObjectService;

    @Autowired
    private IOkrTeamService okrTeamService;

    @GetMapping(value = "/init.htm")
    public String index(Model model) throws Exception {
        List<TeamsExtVO> teamList = okrTeamService.getTeamByUserId(getCurrentUserId());
        model.addAttribute("teamList", teamList);
        return "manage/okrObjectList";
    }

    /**
     * 获取OKR列表
     * @return
     */
    @RequestMapping(value = "/getOkrListByType.json")
    @ResponseBody
    public ResponseResult getOkrListByType(@JsonPathParam("$.searchVO") OkrObjectSearchVO searchVO) {
        ResponseResult responseResult = new ResponseResult();
        searchVO.setUserId(getCurrentUserId());
        List<ObjectivesExtVO> objectivesExtList = okrObjectService.getOkrListByType(searchVO);
        responseResult.setInfo(objectivesExtList);
        return responseResult;
    }

    /**
     * OKR详情页面
     */
    @GetMapping(value = "/okrDetail.htm")
    public String okrDetail(String id, String type, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("type", type);
        String editFlag = "0";
        if (type.equals("1")) {
            editFlag = "1";
        } else if (type.equals("2") && okrObjectService.editObject(id).getOwnerId().equals(getCurrentUserId())) {
            editFlag = "1";
        } else if (type.equals("3") && UserUtils.getSubject().isPermitted("company:edit")) {
            editFlag = "1";
        }
        model.addAttribute("editFlag", editFlag);
        return "manage/okrObjectDetail";
    }

    /**
     * OKR详情展示
     * @return
     */
    @RequestMapping(value = "/getOkrDetail.json")
    @ResponseBody
    public ResponseResult getOkrDetail(@JsonPathParam("$.searchVO") OkrObjectSearchVO searchVO) {
        ResponseResult responseResult = new ResponseResult(true, null);
        searchVO.setUserId(getCurrentUserId());
        ObjectivesExtVO object = okrObjectService.getOkrListByType(searchVO).get(0);
        responseResult.setInfo(object);
        return responseResult;
    }

    /**
     * 新增或编辑目标页面
     * @return
     */
    @RequestMapping(value = "/okrObjectForm.htm")
    public String okrObjectForm(Model model, String objectId, String type) {
        ObjectivesExtVO objectVO = okrObjectService.editObject(objectId);
        if (objectVO == null) {
            objectVO = new ObjectivesExtVO();
            objectVO.setType(type);
        }
        model.addAttribute("objectVO", objectVO);
        return "manage/okrObjectForm";
    }

    /**
     * 保存目标
     * @return
     */
    @RequestMapping(value = "/saveObject.json")
    @ResponseBody
    public ResponseResult saveObject(@JsonPathParam("$.objectVO") ObjectivesExtVO objectVO) {
        objectVO.setCreateUserId(getCurrentUserId());
        ResponseResult responseResult = okrObjectService.saveObject(objectVO);
        return responseResult;
    }

    /**
     * 删除目标
     * @return
     */
    @RequestMapping(value = "/deleteObject.json")
    @ResponseBody
    public ResponseResult deleteObject(String objectId) {
        ResponseResult responseResult = okrObjectService.deleteObject(objectId, getCurrentUserId());
        return responseResult;
    }

    /**
     * 获取父目标下拉数据
     * @return
     */
    @RequestMapping(value = "/getParentObject.json")
    @ResponseBody
    public List<ObjectivesExtVO> getParentObject(String type) {
        return okrObjectService.getParentObject(getCurrentUserId(), type);
    }
}