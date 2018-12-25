package org.openokr.application.framework.container;


import org.apache.jasper.deploy.FunctionInfo;
import org.apache.jasper.deploy.TagAttributeInfo;
import org.apache.jasper.deploy.TagFileInfo;
import org.apache.jasper.deploy.TagInfo;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.apache.jasper.deploy.TagLibraryValidatorInfo;
import org.apache.jasper.deploy.TagVariableInfo;
import org.jboss.annotation.javaee.Icon;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.jsp.TldMetaDataParser;
import org.jboss.metadata.parser.util.NoopXMLResolver;
import org.jboss.metadata.web.spec.AttributeMetaData;
import org.jboss.metadata.web.spec.FunctionMetaData;
import org.jboss.metadata.web.spec.TagFileMetaData;
import org.jboss.metadata.web.spec.TagMetaData;
import org.jboss.metadata.web.spec.TldMetaData;
import org.jboss.metadata.web.spec.VariableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StopWatch;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * Adapted from: https://github.com/fourcube/undertow-jsp-sample/blob/master/src/main/java/com/grieger/sample/undertow/TldLocator.java
 */
public class TldLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TldLocator.class);

    static HashMap<String, TagLibraryInfo> createTldInfos() {
        final URLClassLoader loader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader);
        final Resource[] resources;
        final String locationPattern = "classpath*:META-INF/*.tld";
        try {
            resources = resolver.getResources(locationPattern);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error while retrieving resources for location pattern '%s'.", locationPattern, e));
        }
        final HashMap<String, TagLibraryInfo> tagLibInfos = new HashMap<>();
        final StopWatch watch = new StopWatch();
        watch.start();
        for (Resource resource : resources) {
            LOGGER.info("TLD Resource: " + resource.getFilename());
            InputStream is = null;
            try {
                is = resource.getInputStream();
                final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                inputFactory.setXMLResolver(NoopXMLResolver.create());
                final XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(is);
                final TldMetaData tldMetadata = TldMetaDataParser.parse(xmlReader);
                final TagLibraryInfo taglibInfo = getTagLibraryInfo(tldMetadata);
                if (!tagLibInfos.containsKey(taglibInfo.getUri())) {
                    tagLibInfos.put(taglibInfo.getUri(), taglibInfo);
                }
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ignore) {
                }
            }
        }
        watch.stop();
        LOGGER.info(watch.shortSummary());
        return tagLibInfos;
    }

    static TagLibraryInfo getTagLibraryInfo(TldMetaData tldMetaData) {
        final TagLibraryInfo tagLibraryInfo = new TagLibraryInfo();
        tagLibraryInfo.setTlibversion(tldMetaData.getTlibVersion());
        if (tldMetaData.getJspVersion() == null) {
            tagLibraryInfo.setJspversion(tldMetaData.getVersion());
        } else {
            tagLibraryInfo.setJspversion(tldMetaData.getJspVersion());
        }
        tagLibraryInfo.setShortname(tldMetaData.getShortName());
        tagLibraryInfo.setUri(tldMetaData.getUri());
        if (tldMetaData.getDescriptionGroup() != null) {
            tagLibraryInfo.setInfo(tldMetaData.getDescriptionGroup().getDescription());
        }
        // Validator
        if (tldMetaData.getValidator() != null) {
            final TagLibraryValidatorInfo tagLibraryValidatorInfo = new TagLibraryValidatorInfo();
            tagLibraryValidatorInfo.setValidatorClass(tldMetaData.getValidator().getValidatorClass());
            if (tldMetaData.getValidator().getInitParams() != null) {
                for (ParamValueMetaData paramValueMetaData : tldMetaData.getValidator().getInitParams()) {
                    tagLibraryValidatorInfo.addInitParam(paramValueMetaData.getParamName(),
                            paramValueMetaData.getParamValue());
                }
            }
            tagLibraryInfo.setValidator(tagLibraryValidatorInfo);
        }
        // Tag
        if (tldMetaData.getTags() != null) {
            for (TagMetaData tagMetaData : tldMetaData.getTags()) {
                final TagInfo tagInfo = new TagInfo();
                tagInfo.setTagName(tagMetaData.getName());
                tagInfo.setTagClassName(tagMetaData.getTagClass());
                tagInfo.setTagExtraInfo(tagMetaData.getTeiClass());
                if (tagMetaData.getBodyContent() != null) {
                    tagInfo.setBodyContent(tagMetaData.getBodyContent().toString());
                }
                tagInfo.setDynamicAttributes(tagMetaData.getDynamicAttributes());
                // Description group
                if (tagMetaData.getDescriptionGroup() != null) {
                    final DescriptionGroupMetaData descriptionGroup = tagMetaData.getDescriptionGroup();
                    if (descriptionGroup.getIcons() != null && descriptionGroup.getIcons().value() != null
                            && (descriptionGroup.getIcons().value().length > 0)) {
                        final Icon icon = descriptionGroup.getIcons().value()[0];
                        tagInfo.setLargeIcon(icon.largeIcon());
                        tagInfo.setSmallIcon(icon.smallIcon());
                    }
                    tagInfo.setInfoString(descriptionGroup.getDescription());
                    tagInfo.setDisplayName(descriptionGroup.getDisplayName());
                }
                // Variable
                if (tagMetaData.getVariables() != null) {
                    for (VariableMetaData variableMetaData : tagMetaData.getVariables()) {
                        final TagVariableInfo tagVariableInfo = new TagVariableInfo();
                        tagVariableInfo.setNameGiven(variableMetaData.getNameGiven());
                        tagVariableInfo.setNameFromAttribute(variableMetaData.getNameFromAttribute());
                        tagVariableInfo.setClassName(variableMetaData.getVariableClass());
                        tagVariableInfo.setDeclare(variableMetaData.getDeclare());
                        if (variableMetaData.getScope() != null) {
                            tagVariableInfo.setScope(variableMetaData.getScope().toString());
                        }
                        tagInfo.addTagVariableInfo(tagVariableInfo);
                    }
                }
                // Attribute
                if (tagMetaData.getAttributes() != null) {
                    for (AttributeMetaData attributeMetaData : tagMetaData.getAttributes()) {
                        final TagAttributeInfo tagAttributeInfo = new TagAttributeInfo();
                        tagAttributeInfo.setName(attributeMetaData.getName());
                        tagAttributeInfo.setType(attributeMetaData.getType());
                        tagAttributeInfo.setReqTime(attributeMetaData.getRtexprvalue());
                        tagAttributeInfo.setRequired(attributeMetaData.getRequired());
                        tagAttributeInfo.setFragment(attributeMetaData.getFragment());
                        if (attributeMetaData.getDeferredValue() != null) {
                            tagAttributeInfo.setDeferredValue("true");
                            tagAttributeInfo.setExpectedTypeName(attributeMetaData.getDeferredValue().getType());
                        } else {
                            tagAttributeInfo.setDeferredValue("false");
                        }
                        if (attributeMetaData.getDeferredMethod() != null) {
                            tagAttributeInfo.setDeferredMethod("true");
                            tagAttributeInfo
                                    .setMethodSignature(attributeMetaData.getDeferredMethod().getMethodSignature());
                        } else {
                            tagAttributeInfo.setDeferredMethod("false");
                        }
                        tagInfo.addTagAttributeInfo(tagAttributeInfo);
                    }
                }
                tagLibraryInfo.addTagInfo(tagInfo);
            }
        }
        // Tag files
        if (tldMetaData.getTagFiles() != null) {
            for (TagFileMetaData tagFileMetaData : tldMetaData.getTagFiles()) {
                final TagFileInfo tagFileInfo = new TagFileInfo();
                tagFileInfo.setName(tagFileMetaData.getName());
                tagFileInfo.setPath(tagFileMetaData.getPath());
                tagLibraryInfo.addTagFileInfo(tagFileInfo);
            }
        }
        // Function
        if (tldMetaData.getFunctions() != null) {
            for (FunctionMetaData functionMetaData : tldMetaData.getFunctions()) {
                final FunctionInfo functionInfo = new FunctionInfo();
                functionInfo.setName(functionMetaData.getName());
                functionInfo.setFunctionClass(functionMetaData.getFunctionClass());
                functionInfo.setFunctionSignature(functionMetaData.getFunctionSignature());
                tagLibraryInfo.addFunctionInfo(functionInfo);
            }
        }
        return tagLibraryInfo;
    }
}

