<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageJs" value="${staticContextPath}/assets/js/common/index.js"/>
<c:set var="pageTitle" value="OKR目标管理系统"/>
<%@ include file="/views/application/_include_top_main.jsp" %>
<body class="page-body" style="overflow: hidden;">
    <c:if test="${!flag}">
        <div class="header-bg">
            <div class="header container">
                <a href="" class="logo"></a>
                <div class="header-other">
                    <div class="selection">
                        <label class="lbl">目标时间：</label>
                        <input type="text" class="inp-sm" value="2018年第4季度"/>
                        <i class="icon"></i>
                    </div>
                </div>
                <ul class="hearder-menu" id="menuUL">
                    <li class="active">
                        <a onclick="mainObj.menuClick(this, '${contextPath}/index.htm?flag=true')">首页</a>
                    </li>
                </ul>
                <div class="most-set">
                    <div class="user-set">
                        <img src="${photoUrl}" class="user-img">
                        <input type="file"/>
                        <i class="iconfont icon-upload"></i>
                    </div>
                    <span class="user-name">${userName}</span>
                    <ul class="most-set-list">
                        <li><a href="${contextPath}/logout.htm"><i class="iconfont icon-out"></i>安全退出</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
        <div>
            <iframe id="mainContent" name="mainContent" src="${contextPath}/main.htm"
                    width="100%" frameborder="0" allowfullscreen scrolling="no"></iframe>
        </div>
    <script>
        var mainObj = {flag:${flag}};
    </script>
</body>
<%@ include file="/views/application/_include_bottom.jsp" %>