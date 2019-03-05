package org.openokr.task.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.PageResponseData;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.task.request.WeeklySearchVO;
import org.openokr.task.service.IWeeklyManageService;
import org.openokr.task.vo.WeeklyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yuxinzh
 * @create 2019/3/5
 */
@Controller
@RequestMapping("/api/daily")
@Api(value = "周报相关接口",description = "DailyController")
public class WeeklyApiController extends BaseController {

    @Autowired
    private IWeeklyManageService weeklyManageService;

    @ApiOperation(value = "分页查询周报列表数据", notes = "分页查询周报列表数据")
    @RequestMapping(value = "/getDailyPage.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<PageResponseData<List<WeeklyVO>>> getDailyPage(@RequestBody WeeklySearchVO vo) {
        ResponseData<PageResponseData<List<WeeklyVO>>> result = new ResponseData<>();
        try {
            Page page = new Page(vo.getCurrentPage(), vo.getPageSize());
            vo.setReportUserId(this.getCurrentUserId());
            page = weeklyManageService.queryPage(vo,page);
            PageResponseData<List<WeeklyVO>> pageData = this.reBuildPageData(page,WeeklyVO.class);
            result.setData(pageData);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("分页查询周报列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("分页查询周报列表数据 异常：{},参数:[{}]", e.getMessage(), JSON.toJSONString(vo), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
