<%@ page import="com.zzheng.framework.base.utils.PropertiesUtils" %>
<% String contextPath = request.getContextPath();
	String staticResourceVersion = PropertiesUtils.getProperty("app.staticResourceVersion");
	String jsDebug = PropertiesUtils.getProperty("app.jsDebug"); %>
<% if (jsDebug.equals("true")) { %>
<% if (contextPath != null && (contextPath.equals("/") || contextPath.equals(""))) { %>
<c:set var="contextPath" value=""/>
<c:set var="staticContextPath" value=""/>
<% } else { %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="staticContextPath" value="${pageContext.request.contextPath}"/>
<% } %>
<% } else { %>
<% if (contextPath != null && (contextPath.equals("/") || contextPath.equals(""))) { %>
<c:set var="contextPath" value=""/>
<c:set var="staticContextPath" value=""/>
<% } else { %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="staticContextPath" value="${pageContext.request.contextPath}"/>
<% } %>
<% } %>
<c:set var="jsDebug" value="<%=jsDebug%>"/>
<c:set var="staticResourceVersion" value="<%=staticResourceVersion%>"/>
<c:set var="language" value="zh_CN"/>