<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageJs" value="${staticContextPath}/assets/js/sys/role.js"/>
<c:set var="pageTitle" value="角色管理"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wapper">
    <p class="crumbs-bar">
        <a href="${contextPath}/index.htm">OKR管理系统</a>
        <i class="iconfont icon-arrowR"></i>
        <a>系统管理</a>
        <i class="iconfont icon-arrowR"></i>
        <a href="${contextPath}/sys/role.htm">角色管理</a>
    </p>
    <%-- 上部功能按钮区 --%>
    <div class="top-search">
        <div class="fl">
            <div class="site-search-pop search-bar search-bar-no-icon">
                <div class="sj-search-title-con">
                    <input id="queryRole" type="text" placeholder="请输入角色名称">
                </div>
            </div>
        </div>
        <!--搜索下拉框 end-->
        <div class="fr top-btnbar">
            <a class="btn btn-primary" onclick="pageObj.add();">新增</a>
            <a class="btn btn-success" onclick="pageObj.save();">保存</a>
            <a class="btn btn-default" onclick="pageObj.deleteFunc();">删除</a>
        </div>
    </div>
    <%-- 展示区 --%>
    <div class="mt10">
        <div class="clearfix">
            <div class="column-left m-box">
                <div id="queryRoleDropdown"></div>
            </div>
            <div class="column-right">
                <form id="roleForm">
                    <ul class="form-grid font4">
                        <li class="col-sm-12 ">
                            <label class="form-lab">角色名<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="name" type="text" class="inp inp-royal mr20" value="" placeholder="角色名">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">角色权限<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <div style="border: 1px solid #e3e6ec;">
                                    <ul id="ulMenuTree" class="ztree" style="overflow-y: auto;"></ul>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="mt10">
                        <a class="btn btn-success" onclick="pageObj.save();">保存</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/views/application/_include_bottom.jsp" %>