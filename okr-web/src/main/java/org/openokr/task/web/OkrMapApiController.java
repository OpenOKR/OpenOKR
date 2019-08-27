package org.openokr.task.web;

import com.zzheng.framework.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.manage.service.IOkrObjectService;
import org.openokr.manage.vo.OkrMapVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * okr地图
 * @author caijq
 * @create 2019/8/27
 */
@Controller
@RequestMapping("/api/okrMap")
@Api(value = "地图相关接口",description = "OkrMapApiController")
public class OkrMapApiController extends BaseController {

    @Autowired
    private IOkrObjectService okrObjectService;

    @ApiOperation(value = "获取okr地图", notes = "获取okr地图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "timeSessionId", value = "timeSessionId", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/getOkrMap.json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<List<OkrMapVO>> getOkrMap(String timeSessionId) {
        ResponseData<List<OkrMapVO>> result = new ResponseData<>();
        try {
            OkrMapVO okrMapVO = okrObjectService.getMap(timeSessionId);
            List<OkrMapVO> list = new ArrayList<>();
            list.add(okrMapVO);
            result.setData(list);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("获取okr地图 异常：{},参数:[{}]", e.getMessage(), timeSessionId, e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }catch (Exception e){
            logger.error("获取okr地图 异常：{},参数:[{}]", e.getMessage(), timeSessionId, e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

}
