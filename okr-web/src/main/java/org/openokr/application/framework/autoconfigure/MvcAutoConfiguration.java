package org.openokr.application.framework.autoconfigure;

import org.openokr.application.framework.resolver.JPathArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author xiezm
 */
@Configuration
@EnableConfigurationProperties(MvcProperties.class)
public class MvcAutoConfiguration extends WebMvcConfigurerAdapter {

    private static Logger logger = LoggerFactory.getLogger(MvcAutoConfiguration.class);

    private static final String COMPONENT_ID = "SpringMvc";

    @Autowired
    MvcProperties properties;

    /**
     * 日期格式转换
     * <pre>
     *     <bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
     *         <property name="formatters">
     *             <list>
     *                 <bean class="com.onlyou.framework.spring.format.DateFormatter"/>
     *             </list>
     *          </property>
     *      </bean>
     * </pre>
     *
     * @param registry
     */
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        //如果配置多个
//        if (properties.getFormatters() != null && properties.getFormatters().size() > 0) {
//            for (Class<FormatterRegistry> formatter : properties.getFormatters()) {
//                //
//                registry.addFormatter((Formatter<?>) BeanUtils.instantiate(formatter));
//                logger.info(COMPONENT_ID + " 添加格式转换类：" + formatter.getName());
//            }
//        } else {
//            //否则使用默认
//            registry.addFormatter(new DateFormatter());
//            logger.info(COMPONENT_ID + " 添加格式转换类：" + DateFormatter.class.getName());
//        }
//        super.addFormatters(registry);
//    }

    /**
     * 对 @JsonPathParam 注解的支持
     * <pre>
     *      <mvc:annotation-driven conversion-service="conversionService">
     *          <mvc:argument-resolvers>
     *              <bean class="com.onlyou.framework.spring.web.method.support.JPathArgumentResolver"/>
     *          </mvc:argument-resolvers>
     *          略
     *      </mvc:annotation-driven>
     * </pre>
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //如果配置多个
        if (properties.getArgumentResolvers() != null && properties.getArgumentResolvers().size() > 0) {
            for (Class<HandlerMethodArgumentResolver> argumentResolver : properties.getArgumentResolvers()) {
                //
                argumentResolvers.add(BeanUtils.instantiate(argumentResolver));
                logger.info(COMPONENT_ID + "List<HandlerMethodArgumentResolver> argumentResolvers 添加：" + argumentResolver.getName());
            }
        } else {
            argumentResolvers.add(new JPathArgumentResolver());
            logger.info(COMPONENT_ID + " 添加对 @JsonPathParam 参数注解的支持：" + JPathArgumentResolver.class.getName());
        }
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 数据映射转换
     * <pre>
     *     <mvc:annotation-driven conversion-service="conversionService">
     *         略
     *         <mvc:message-converters register-defaults="false">
     *             <bean class="org.springframework.http.converter.BufferedImageHttpMessageConverter"/>
     *             <bean class="org.springframework.http.converter.ByteArrayHttpMessageConverter"/>
     *             <bean class="org.springframework.http.converter.StringHttpMessageConverter"/>
     *             <bean class="org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter"/>
     *             <bean class="org.springframework.http.converter.xml.SourceHttpMessageConverter"/>
     *             <bean class="org.springframework.http.converter.json.MappingJacksonHttpMessageConverter">
     *                 <property name="supportedMediaTypes">
     *                     <list>
     *                         <value>text/html;charset=UTF-8</value>
     *                         <value>application/json;charset=UTF-8</value>
     *                         <value>application/*+json;charset=UTF-8</value>
     *                     </list>
     *                 </property>
     *                 <property name="objectMapper">
     *                     <bean class="com.onlyou.sample.application.org.codehaus.jackson.map.SampleObjectMapper"/>
     *                 </property>
     *              </bean>
     *          </mvc:message-converters>
     *      </mvc:annotation-driven>
     * </pre>
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //
        converters.add(new BufferedImageHttpMessageConverter());
        logger.info(COMPONENT_ID + " 添加 Jackson 转化器：" + BufferedImageHttpMessageConverter.class.getName());
        converters.add(new ByteArrayHttpMessageConverter());
        logger.info(COMPONENT_ID + " 添加 Jackson 转化器：" + ByteArrayHttpMessageConverter.class.getName());
        converters.add(new StringHttpMessageConverter());
        logger.info(COMPONENT_ID + " 添加 Jackson 转化器：" + StringHttpMessageConverter.class.getName());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        logger.info(COMPONENT_ID + " 添加 Jackson 转化器：" + AllEncompassingFormHttpMessageConverter.class.getName());
        converters.add(new SourceHttpMessageConverter());
        logger.info(COMPONENT_ID + " 添加 Jackson 转化器：" + SourceHttpMessageConverter.class.getName());
        //
        MappingJackson2HttpMessageConverter converter = BeanUtils.instantiate(MappingJackson2HttpMessageConverter.class);
        converter.setSupportedMediaTypes(properties.getJackson2HttpMessageConverter().resolveSupportedMediaTypes());
        for (MediaType mediaType : properties.getJackson2HttpMessageConverter().resolveSupportedMediaTypes()) {
            logger.info(COMPONENT_ID + " 支持 Jackson 类型：" + mediaType.toString());
        }
        converter.setObjectMapper(BeanUtils.instantiate(properties.getJackson2HttpMessageConverter().getObjectMapper()));
        logger.info(COMPONENT_ID + " Jackson 转化器配置类：" + properties.getJackson2HttpMessageConverter().getObjectMapper().getName());
        converters.add(converter);
        logger.info(COMPONENT_ID + " 添加 Jackson 转化器：" + MappingJackson2HttpMessageConverter.class.getName());
        //
        super.configureMessageConverters(converters);
    }

    /**
     * 异常处理日志
     * <pre>
     *      <bean id="exceptionResolver" class="com.onlyou.framework.log.LogExceptionResolver">
     *          <property name="exceptionMappings">
     *              <props>
     *                  <prop key="java.lang.Throwable">error</prop>
     *              </props>
     *          </property>
     *      </bean>
     * </pre>
     *
     * @param exceptionResolvers
     */
//    @Override
//    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        LogExceptionResolver handlerExceptionResolver = new LogExceptionResolver();
//        if (StringUtils.isNotEmpty(properties.getHandlerExceptionResolver().getDefaultErrorView())) {
//            handlerExceptionResolver.setDefaultErrorView(properties.getHandlerExceptionResolver().getDefaultErrorView());
//        }
//        if (properties.getHandlerExceptionResolver().getDefaultStatusCode() != null) {
//            handlerExceptionResolver.setDefaultStatusCode(properties.getHandlerExceptionResolver().getDefaultStatusCode());
//        }
//        exceptionResolvers.add(handlerExceptionResolver);
//        logger.info(COMPONENT_ID + " 添加异常分析类：" + LogExceptionResolver.class.getName() + " 创建完成");
//        super.extendHandlerExceptionResolvers(exceptionResolvers);
//    }

    /**
     * 文件上传支持
     * <pre>
     *      <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
     *  </pre>
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public CommonsFileUploadSupport commonsFileUploadSupport() {
        logger.info(COMPONENT_ID + " 文件上传支持：" + CommonsMultipartResolver.class.getName() + " 创建完成");
        return new CommonsMultipartResolver();
    }
}