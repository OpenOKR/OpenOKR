<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="OKR目标详情"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrObjectDetail.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wapper">
    <%-- 修改公司okr目标参数--%>
    <c:set var="companyEditFlag" value="0"/>
    <so:hasPermission name="company:edit">
        <c:set var="companyEditFlag" value="1"/>
    </so:hasPermission>
    <input id="companyEditFlag" type="hidden" value="${companyEditFlag}">
    <p class="crumbs-bar">
        <a href="${contextPath}/index.htm?flag=true">OKR管理系统</a>
        <i class="iconfont icon-arrowR"></i>
        <a href="${contextPath}/manage/okrObject/init.htm">OKR详情</a>
    </p>
    <div class="card-box">
        <div class=" card-area2" id="detail">

        </div>
        <div class="card-title mt10">
            <div class="fl">
                <span class="title">历史操作记录</span>
            </div>
        </div>
        <div class="card-area2">
            <div class="area-process" id="historyList"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var pageObj = {id : '${id}', type: '${type}', editFlag: '${editFlag}', userId: '${userId}'};
</script>
<%@ include file="/views/application/_include_bottom.jsp" %>