<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="OKR视图"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrObjectList.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wapper">
    <p class="crumbs-bar">
        <a href="${contextPath}/index.htm">OKR管理系统</a>
        <i class="iconfont icon-arrowR"></i>
        <a href="${contextPath}/object/init.htm">OKR</a>
    </p>
    <div class="card-box">
        <div class="top-search uim tc">
            <ul class="nav-tabs2 ui-primary">
                <li class="active" onclick="pageObj.tabClick(this, '1', null);">我的OKR</li>
                <c:if test="${teamList != null && teamList.size() > 0}">
                    <c:forEach items="${teamList}" var="team">
                        <li onclick="pageObj.tabClick(this, '2', '${team.id}');">${team.name}</li>
                    </c:forEach>
                </c:if>
                <li onclick="pageObj.tabClick(this, '3', null)">公司OKR</li>
            </ul>
        </div>
        <div class="tab-content mt10">
            <div class="tab-pane active">
                <div class="top-search">
                    <label class="ind-title">关键字：</label>
                    <input name="keyword" type="text" class="inp inp-big" placeholder="请输入关键字" />
                    <label class="ind-title">执行状态：</label>
                    <div class="select select-big">
                        <input type="text" class=" " placeholder="请选择" />
                        <i class="icon"></i>
                    </div>
                    <a class="btn btn-primary waves-effect waves-light"><i class="icon-search"></i>搜索</a>
                    <div class="action" onclick="pageObj.addObject();">
                        <a class="btn btn-primary waves-effect waves-light"><i class="icon-add"></i>新建OKR</a>
                    </div>
                </div>
                <div class=" card-area2" id="okrObjectList"></div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/views/application/_include_bottom.jsp"%>