package org.openokr.manage.web;

import com.zzheng.framework.adapter.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.web.BaseController;
import org.openokr.manage.service.IOkrMessageService;
import org.openokr.manage.service.IOkrObjectService;
import org.openokr.manage.service.IOkrTeamService;
import org.openokr.manage.vo.*;
import org.openokr.sys.vo.request.TreeDataVO;
import org.openokr.utils.StringUtils;
import org.openokr.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * OKR目标管理
 *
 * @author hjh
 */
@Controller
@RequestMapping("/manage/okrObject")
@Api(value = "OKR目标管理相关接口",description = "OkrObjectController")
public class OkrObjectController extends BaseController {

    @Autowired
    private IOkrObjectService okrObjectService;

    @Autowired
    private IOkrTeamService okrTeamService;

    @Autowired
    private IOkrMessageService okrMessageService;

    @GetMapping(value = "/init.htm")
    public String index(Model model) throws Exception {
        List<TeamsExtVO> teamList = okrTeamService.getTeamByUserId(getCurrentUserId());
        model.addAttribute("teamList", teamList);
        model.addAttribute("currentUserId", getCurrentUserId());
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
        if (StringUtils.isEmpty(searchVO.getUserId())) {
            searchVO.setUserId(getCurrentUserId());
        }
        List<ObjectivesExtVO> objectivesExtList = okrObjectService.getOkrListByType(searchVO);
        responseResult.setInfo(objectivesExtList);
        return responseResult;
    }

    /**
     * 获取OKR地图
     * @return
     */
    @RequestMapping(value = "/getOkrMap.json")
    @ResponseBody
    public ResponseResult getOkrMap(@JsonPathParam("$.searchVO") OkrObjectSearchVO searchVO) {

        ResponseResult responseResult = new ResponseResult();
        if (StringUtils.isEmpty(searchVO.getUserId())) {
            searchVO.setUserId(getCurrentUserId());
        }
        OkrMapVO okrMapVO = okrObjectService.getMap(searchVO.getTimeSessionId());
        List<OkrMapVO> list = new ArrayList<>();
        list.add(okrMapVO);
        responseResult.setInfo(list);
        return responseResult;
    }

    /**
     * OKR详情页面
     */
    @GetMapping(value = "/okrDetail.htm")
    public String okrDetail(String id, Model model) {
        ObjectivesExtVO objectivesExtVO = okrObjectService.getObjectById(id);
        String type = objectivesExtVO.getType();
        String editFlag = "0";
        if (type.equals("1") && objectivesExtVO.getOwnerId().equals(getCurrentUserId())) {
            editFlag = "1";
        } else if (type.equals("2") && objectivesExtVO.getOwnerId().equals(getCurrentUserId())) {
            editFlag = "1";
        } else if (type.equals("3") && UserUtils.getSubject().isPermitted("company:edit")) {
            editFlag = "1";
        }
        // 判断时间段是否是未激活的
        if (!objectivesExtVO.getTimeSessionId().equals(getCurrentTimeSessionId())) {
            editFlag = "0";
        }
        model.addAttribute("id", id);
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
        ObjectivesExtVO object = okrObjectService.getObjectById(searchVO.getObjectId());
        responseResult.setInfo(object);
        return responseResult;
    }

    /**
     * 新增或编辑目标页面
     * @return
     */
    @RequestMapping(value = "/okrObjectForm.htm")
    public String okrObjectForm(Model model, String objectId, String type, String teamId) {
        ObjectivesExtVO objectVO = okrObjectService.getObjectById(objectId);
        if (objectVO == null) {
            objectVO = new ObjectivesExtVO();
            objectVO.setType(type);
            objectVO.setTeamId(teamId);
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

    /**
     * 目标提交审核
     */
    @PostMapping(value = "/auditSubmit.json")
    @ResponseBody
    public ResponseResult auditSubmit(@JsonPathParam("$.id") String id) {
        ResponseResult result = okrObjectService.auditSubmit(id, getCurrentUser());
        return result;
    }

    /**
     * 目标审核页面
     * @return
     */
    @GetMapping(value = "/audit.htm")
    public String audit(String id, Model model) {
        MessagesVO message = okrMessageService.getById(id);
        model.addAttribute("message", message);
        return "manage/okrObjectAudit";
    }

    /**
     * 目标审核确认逻辑处理
     */
    @PostMapping(value = "/auditConfirm.json")
    @ResponseBody
    public ResponseResult auditConfirm(@JsonPathParam("vo") MessagesExtVO messagesExtVO) {
        ResponseResult result = okrObjectService.auditConfirm(messagesExtVO, getCurrentUserId());
        return result;
    }

    @ApiOperation(value = "获取当前用户所有OKR树状数据", notes = "获取当前用户所有OKR树状数据")
    @RequiresPermissions("user")
    @PostMapping(value = "/findAllOkrTreeData.json")
    @ResponseBody
    public List<TreeDataVO> findAllOkrTreeData() {
        List<TreeDataVO> list = okrObjectService.findAllOkrTreeData(getCurrentUserId());
        return list;
    }
}