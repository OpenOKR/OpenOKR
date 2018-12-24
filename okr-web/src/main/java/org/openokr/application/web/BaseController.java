package org.openokr.application.web;

import com.zzheng.framework.base.utils.JSONUtils;
import org.openokr.sys.vo.UserVOExt;
import org.openokr.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhengzheng on 2018/12/19.
 */
@Component
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

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
}