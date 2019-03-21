package org.openokr.task.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.openokr.application.web.BaseController;
import org.openokr.common.vo.response.ResponseData;
import org.openokr.task.service.IApportionCategoryManageService;
import org.openokr.task.vo.ApportionCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yuxinzh
 * @create 2019/3/21
 */
@Controller
@RequestMapping("/api/category/")
@Api(value = "分摊类型接口",description = "ApportionCategoryController")
public class ApportionCategoryController extends BaseController {

    @Autowired
    private IApportionCategoryManageService apportionCategoryManageService;

    @ApiOperation(value = "获取分摊类别信息列表", notes = "获取分摊类别信息列表")
    @RequestMapping(value = "/getCategoryList.json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<List<ApportionCategoryVO>> getCategoryList() {
        ResponseData<List<ApportionCategoryVO>> result = new ResponseData<>();
        try {
            List<ApportionCategoryVO> list = apportionCategoryManageService.getApportionCategoryList();

            result.setData(list);
            result.setCode(0);
            result.setSuccess(true);
        } catch (BusinessException e){
            logger.error("按产品类别查询列表 错误：{}", e.getMessage(), e);
            result.setCode(6000);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            logger.error("按产品类别查询列表 异常：{}", e.getMessage(), e);
            result.setCode(7000);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
