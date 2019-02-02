<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="OKR列表视图"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrObjectList.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wapper">
    <p class="crumbs-bar">
        <a href="${contextPath}/index.htm?flag=true">OKR管理系统</a>
        <i class="iconfont icon-arrowR"></i>
        <a href="${contextPath}/manage/okrObject/init.htm">OKR列表</a>
    </p>
    <div class="card-box">
        <div class="top-search uim tc">
            <ul class="nav-tabs2 ui-primary">
                <li class="active" onclick="pageObj.tabClick(this, '1', null);">我的OKR</li>
                <c:if test="${teamList != null && teamList.size() > 0}">
                    <c:forEach items="${teamList}" var="team">
                        <li data-edit="${team.editFlag}" onclick="pageObj.tabClick(this, '2', '${team.id}');">${team.name}</li>
                    </c:forEach>
                </c:if>
                <li onclick="pageObj.tabClick(this, '3', '1')">公司OKR</li>
            </ul>
        </div>
        <div class="tab-content mt10">
            <div class="tab-pane active">
                <div class="top-search">
                    <label class="ind-title">关键字：</label>
                    <input id="keyword" name="keyword" type="text" class="inp inp-big" placeholder="请输入关键字" />
                    <label class="ind-title">执行状态：</label>
                    <div class="select select-big">
                        <input id="statusName" name="statusName" type="text" placeholder="请选择" />
                        <input id="status" name="status" type="hidden" />
                        <i class="icon"></i>
                    </div>
                    <label class="ind-title" id="realNameLabel">用户：</label>
                    <div class="select select-big" id="realNameDiv">
                        <input id="realName" name="realName" type="text" placeholder="请选择" />
                        <input id="userId" name="userId" type="hidden" />
                        <i class="icon"></i>
                    </div>
                    <a id="search" onclick="pageObj.loadOKRObjects(pageObj.currentType, pageObj.currentTeamId);"
                       class="btn btn-primary waves-effect waves-light"><i class="icon-search"></i>搜索</a>
                    <c:set var="companyEditFlag" value="0"/>
                    <so:hasPermission name="company:edit">
                        <c:set var="companyEditFlag" value="1"/>
                    </so:hasPermission>
                    <input id="companyEditFlag" type="hidden" value="${companyEditFlag}">
                    <div class="action" id="editObject" onclick="pageObj.editObject(null);">
                        <a class="btn btn-primary waves-effect waves-light"><i class="icon-add"></i>新建OKR</a>
                    </div>
                </div>
                <div id="okrObjectList"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var pageObj = {currentUserId: '${currentUserId}'};
</script>
<%@ include file="/views/application/_include_bottom.jsp"%>