package org.openokr.framework;

import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import org.apache.jasper.deploy.JspPropertyGroup;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ServletContainer implements EmbeddedServletContainerCustomizer {

    @Value("${server.context-path:/}")
    private String contextPath;

    @Value("${spring.http.encoding.charset:UTF-8}")
    private String encoding;

    @Value("${server.session.timeout:30}")
    private Integer timeout;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if (timeout < 300) {
            throw new RuntimeException("server.session.timeout 是以秒计时，暂时不能低于5分钟,有问题请联系开发人员");
        }
        if (container instanceof UndertowEmbeddedServletContainerFactory) {
            final UndertowEmbeddedServletContainerFactory undertow = (UndertowEmbeddedServletContainerFactory) container;
            final UndertowDeploymentInfoCustomizer customizer = new UndertowDeploymentInfoCustomizer() {
                @Override
                public void customize(DeploymentInfo deploymentInfo) {
                    deploymentInfo.setClassLoader(ServletContainer.class.getClassLoader())
                            .setContextPath(contextPath)
                            .setDeploymentName("ApplicationWeb")
                            .setUrlEncoding(encoding)
                            .setDefaultEncoding(encoding)
                            .setResourceManager(new DefaultResourceLoader(ServletContainer.class))
                            .addServlet(JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp"));
                    final HashMap<String, TagLibraryInfo> tagLibraryInfo = TldLocator.createTldInfos();
                    JspServletBuilder.setupDeployment(deploymentInfo, new HashMap<String, JspPropertyGroup>(), tagLibraryInfo, new HackInstanceManager());
                }
            };
            undertow.addDeploymentInfoCustomizers(customizer);
        }
    }

    public static class DefaultResourceLoader extends ClassPathResourceManager {
        public DefaultResourceLoader(final Class<?> clazz) {
            super(clazz.getClassLoader(), "");
        }
    }
}
