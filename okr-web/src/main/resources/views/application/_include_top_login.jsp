<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/_include/_html_doctype.jsp"%>
<html>
<head>
    <%@include file="/views/_include/_html_meta.jsp"%>
    <%@include file="/views/_include/_html_title.jsp"%>
    <%@include file="/views/_include/_html_link_shortcuticon.jsp"%>
    <c:if test="${jsDebug}">
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/login.css"/>
    </c:if>
    <c:if test="${!jsDebug}">
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/login.min.css?ver=${staticResourceVersion}"/>
    </c:if>
</head>