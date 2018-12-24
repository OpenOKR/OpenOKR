<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="组织管理"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/sys/organization.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wrapper">
    <%-- 上部功能按钮区 --%>
    <div class="top-search">
        <div class="fl">
            <div class="site-search-pop search-bar search-bar-no-icon">
                <div class="sj-search-title-con">
                    <input id="queryOrganizationTree" type="text" placeholder="请输入机构名称">
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
                <div id="queryOrganizationTreeContainer"></div>
            </div>
            <div class="column-right">
                <form id="organizationForm">
                    <ul class="form-grid font4">
                        <li class="col-sm-12 ">
                            <label class="form-lab">机构名称*<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="name" type="text" class="inp inp-royal mr20" value="" placeholder="机构名称">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">上级机构*<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input id="parentName" name="parentName" type="text" class="inp inp-royal mr20" value="" placeholder="上级机构">
                                <input id="parentId" name="parentId" type="hidden">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">编码*<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="code" type="text" class="inp inp-royal mr20" value="" placeholder="编码">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">描述</label>
                            <div class="form-control">
                                <input name="description" type="text" class="inp inp-royal mr20" value="" placeholder="描述">
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