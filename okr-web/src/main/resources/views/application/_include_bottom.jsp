<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-5PFLZQK"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->
<c:if test="${pageJs!=null}">
    <%@include file="/views/_include/_js_variable.jsp"%>
    <!--[if lt IE 9]>
    <%@include file="/views/_include/_json2_js.jsp"%>
    <![endif]-->
    <c:if test="${jsDebug}">
        <script src="${staticContextPath}/assets/js/lib/require/2.1.11/require.config.js"></script>
        <script src="${staticContextPath}/assets/js/lib/require/2.1.11/require.config.application.js"></script>
        <script src="${staticContextPath}/assets/js/lib/require/2.1.11/require.js" data-main="${pageJs}"></script>
    </c:if>
    <c:if test="${!jsDebug}">
        <script type="text/javascript" src="${staticContextPath}/assets/js/lib/require/2.1.11/require.all.min.js?ver=${staticResourceVersion}" data-main="<%=pageContext.getAttribute("pageJs").toString().replace(".js",".min.js")%>?ver=${staticResourceVersion}"></script>
    </c:if>
</c:if>
</html>