package org.openokr.application.framework.resolver;

import com.jayway.jsonpath.JsonPath;
import com.zzheng.framework.base.utils.JSONUtils;
import net.minidev.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.openokr.application.framework.annotation.JsonPathParam;
import org.openokr.application.framework.constant.RequestConstant;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * json path 方式的提取 ajax 上传的json参数 https://code.google.com/p/json-path/
 * 
 * @author anysome
 */
public class JPathArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(JsonPathParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String body = getRequestBody(webRequest);
		// read 没法一步到位的类型转换
		//System.err.println(parameter.getParameterAnnotation(JsonPathParam.class).value());
		Object val = JsonPath.read(body, parameter.getParameterAnnotation(JsonPathParam.class).value());
		if (val instanceof JSONObject) {
			JSONObject obj = (JSONObject) val;
			Class<?> clz = parameter.getParameterType();
			// 又转了一次
			return JSONUtils.stringToBean(obj.toJSONString(), clz);
		} else {
			return val;
		}
	}

	private String getRequestBody(NativeWebRequest webRequest) {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		String jsonBody = (String) servletRequest.getAttribute(JSONBODYATTRIBUTE);
		if (jsonBody == null) {
			try {
				String body = IOUtils.toString(servletRequest.getInputStream(),
						RequestConstant.Charset.UTF_8.getValue());
				servletRequest.setAttribute(JSONBODYATTRIBUTE, body);
				return body;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return jsonBody;
	}
}
