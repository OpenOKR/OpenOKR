<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="我的消息"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrMessage.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wapper">
    <p class="crumbs-bar">
        <a href="${contextPath}/index.htm?flag=true">OKR管理系统</a>
        <i class="iconfont icon-arrowR"></i>
        <a href="${contextPath}/manage/okrMessage/index.htm">消息中心</a>
    </p>
    <div class="card-box">
        <div class="card-title">
            <div class="fl">
                <span class="title">消息列表</span>
            </div>
        </div>
        <div class="card-area" id="messageList">

        </div>
    </div>
</div>
<div id="page" style="text-align: center;"></div>
<%@ include file="/views/application/_include_bottom.jsp"%>