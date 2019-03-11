package org.openokr.application.web;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.common.vo.response.PageResponseData;
import org.openokr.manage.service.IOkrTimeSessionsService;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhengzheng on 2018/12/19.
 */
@Component
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOkrTimeSessionsService okrTimeSessionsService;

    protected UserVOExt getCurrentUser() {
        return UserUtils.getUser();
    }

    protected String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    protected String getCurrentOrganizationId() {
        return getCurrentUser().getOrganizationId();
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        String str = JSONUtils.objectToString(object);
        try {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(str);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 当前时间段ID
     * @return
     * @throws BusinessException
     */
    public String getCurrentTimeSessionId() throws BusinessException {
        return okrTimeSessionsService.getDefaultTimeSession().getId();
    }

    /**
     * 深度复制分页对象
     * @param page
     * @param targetClass
     * @return
     */
    protected PageResponseData reBuildPageData(Page page, Class targetClass) {
        PageResponseData<List<?>> pageData = new PageResponseData<>();
        pageData.setCurrentPage(page.getCurrentPage());
        pageData.setPageSize(page.getPageSize());
        pageData.setTotalPage(page.getTotalPage());
        pageData.setTotalRecord((int)page.getTotalRecord());
        pageData.setData(cloneListObject(page.getRecords(), targetClass));
        return pageData;
    }

    /**
     * 深度复制对象
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    protected <T> T cloneObject(Object source, Class target) {
        String json = JSON.toJSONString(source);
        return (T) JSON.parseObject(json, target);
    }

    /**
     * 深度复制列表
     * @param source
     * @param targetClass
     * @return
     */
    protected <T, E> List<E> cloneListObject(Object source, Class targetClass) {
        String json = JSON.toJSONString(source);
        return JSON.parseArray(json, targetClass);
    }

}