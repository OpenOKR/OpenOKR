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
    <c:if test="${jsDebug}">
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/login.css"/>
    </c:if>
    <c:if test="${!jsDebug}">
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/assets/css/login.min.css?ver=${staticResourceVersion}"/>
    </c:if>
</head>