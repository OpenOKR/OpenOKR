<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/_include/_html_doctype.jsp"%>
<html>
<head>
    <!-- Google Tag Manager -->
    <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
        new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','GTM-5PFLZQK');</script>
    <!-- End Google Tag Manager -->
    <%@include file="/views/_include/_html_meta.jsp"%>
    <%@include file="/views/_include/_html_title.jsp"%>
    <%@include file="/views/_include/_html_link_shortcuticon.jsp"%>
    <c:choose>
        <c:when test="${pageTitle == '用户管理' || pageTitle == '组织管理' || pageTitle == '角色管理' || pageTitle == '菜单管理'}">
            <c:if test="${jsDebug}">
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/global.css"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/style.css"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/layer.css"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/oldGlobal.css"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/oldStyle.css"/>
            </c:if>
            <c:if test="${!jsDebug}">
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/oldGlobal.min.css?ver=${staticResourceVersion}"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/oldStyle.min.css?ver=${staticResourceVersion}"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/global.min.css?ver=${staticResourceVersion}"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/style.min.css?ver=${staticResourceVersion}"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/layer.min.css?ver=${staticResourceVersion}"/>
            </c:if>
        </c:when>
        <c:otherwise>
            <c:if test="${jsDebug}">
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/global.css"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/style.css"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/layer.css"/>
            </c:if>
            <c:if test="${!jsDebug}">
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/global.min.css?ver=${staticResourceVersion}"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/style.min.css?ver=${staticResourceVersion}"/>
                <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/layer.min.css?ver=${staticResourceVersion}"/>
            </c:if>
        </c:otherwise>
    </c:choose>
</head>