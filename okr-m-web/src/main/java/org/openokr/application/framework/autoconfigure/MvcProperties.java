package org.openokr.application.framework.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openokr.application.framework.converter.mapper.DefaultObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiezm
 */
@ConfigurationProperties(prefix = MvcProperties.PREFIX)
public class MvcProperties {

    public static final String PREFIX = "framework.mvc";

    private java.util.List<Class<HandlerMethodArgumentResolver>> argumentResolvers;

    private java.util.List<Class<FormatterRegistry>> formatters;

    public List<Class<HandlerMethodArgumentResolver>> getArgumentResolvers() {
        return argumentResolvers;
    }

    public void setArgumentResolvers(List<Class<HandlerMethodArgumentResolver>> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    public List<Class<FormatterRegistry>> getFormatters() {
        return formatters;
    }

    public void setFormatters(List<Class<FormatterRegistry>> formatters) {
        this.formatters = formatters;
    }

    private final Jackson2HttpMessageConverter jackson2HttpMessageConverter = new Jackson2HttpMessageConverter();

    private final HandlerExceptionResolver handlerExceptionResolver = new HandlerExceptionResolver();

    public Jackson2HttpMessageConverter getJackson2HttpMessageConverter() {
        return jackson2HttpMessageConverter;
    }

    public HandlerExceptionResolver getHandlerExceptionResolver() {
        return handlerExceptionResolver;
    }

    /**
     * JacksonHttpMessageConverter
     */
    public static class Jackson2HttpMessageConverter {

        public Jackson2HttpMessageConverter() {
            //设置默认
            if (supportedMediaTypes.size() == 0) {
                supportedMediaTypes.add("text/html;charset=UTF-8");
                supportedMediaTypes.add("application/json;charset=UTF-8");
                supportedMediaTypes.add("application/*+json;charset=UTF-8");
            }
            if (this.objectMapper == null) {
                this.objectMapper = DefaultObjectMapper.class;
            }
        }

        private List<String> supportedMediaTypes = new ArrayList<>();

        private Class<? extends ObjectMapper> objectMapper;

        public List<String> getSupportedMediaTypes() {
            return supportedMediaTypes;
        }

        public void setSupportedMediaTypes(List<String> supportedMediaTypes) {
            this.supportedMediaTypes = supportedMediaTypes;
        }

        public Class<? extends ObjectMapper> getObjectMapper() {
            return objectMapper;
        }

        public void setObjectMapper(Class<? extends ObjectMapper> objectMapper) {
            this.objectMapper = objectMapper;
        }

        public List<MediaType> resolveSupportedMediaTypes() {
            if (getSupportedMediaTypes().size() > 0) {
                List<MediaType> list = new ArrayList<>();
                for (String mediaTypeStr : getSupportedMediaTypes()) {
                    list.add(MediaType.valueOf(mediaTypeStr));
                }
                return list;
            } else {
                return null;
            }
        }

    }

    /**
     * HandlerExceptionResolver
     */
    public static class HandlerExceptionResolver {

        private String defaultErrorView;

        private Integer defaultStatusCode;

        public String getDefaultErrorView() {
            return defaultErrorView;
        }

        public void setDefaultErrorView(String defaultErrorView) {
            this.defaultErrorView = defaultErrorView;
        }

        public Integer getDefaultStatusCode() {
            return defaultStatusCode;
        }

        public void setDefaultStatusCode(Integer defaultStatusCode) {
            this.defaultStatusCode = defaultStatusCode;
        }
    }

}
