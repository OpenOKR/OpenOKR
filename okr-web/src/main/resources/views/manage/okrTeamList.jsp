<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="团队建设"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrTeamList.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wapper">
    <p class="crumbs-bar">
        <a href="${contextPath}/index.htm?flag=true">OKR管理系统</a>
        <i class="iconfont icon-arrowR"></i>
        <a href="${contextPath}/manage/okrTeam/init.htm">团队建设</a>
    </p>
    <div class="card-box">
        <div class="top-search uim ">
            <ul class="nav-tabs ">
                <li class="active" onclick="pageObj.tabClick(this, '1')"><a>创建的团队</a></li>
                <li onclick="pageObj.tabClick(this, '2')"><a>参与的团队</a></li>
            </ul>
        </div>
        <div class="tab-content">
            <div class="tab-pane active">
                <div class="row" id="teamList"></div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/views/application/_include_bottom.jsp"%>