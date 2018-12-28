<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="新增/编辑团队"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrTeamForm.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<style type="text/css">
    ul.ztree {border: 1px; height:320px;overflow-y:auto;}
</style>
<div>
    <form id="teamForm" class="form-gray-box clearfix">
        <input id="id" name="id" type="hidden" value="${teamsExtVO.id}" />
        <div class="">
            <ul class="form-grid font4">
                <li class="col-sm-12">
                    <label class="form-lab mt20">团队LOGO：</label>
                    <div class="form-control">
                        <div class="part-item ui-mx">
                            <i class="iconfont icon-close"></i>
                            <span><img src="${teamsExtVO.icon}"/></span>
                        </div>
                    </div>
                </li>
                <li class="col-sm-12">
                    <label class="form-lab">团队名称：</label>
                    <div class="form-control">
                        <input id="name" name="name" type="text" value="${teamsExtVO.name}" class="inp" placeholder="请输入团队名称" />
                    </div>
                </li>
                <li class="col-sm-12">
                    <label class="form-lab">父团队：</label>
                    <div class="form-control">
                        <div class="search">
                            <input id="parentName" name="parentName" type="text" value="${teamsExtVO.parentName}" class="inp" placeholder="请选择父团队" />
                            <input id="parentId" name="parentId" type="hidden" value="${teamsExtVO.parentId}"/>
                            <em></em>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="tree-info clearfix">
            <div class="tree-info-item ui-off" style="width: 100%;">
                <div class="tree-title">请选择团队成员</div>
                <label class="search">
                    <input id="searchKey" name="searchKey" type="text">
                    <em class="icon"></em>
                </label>
                <div class="tree-box">
                    <div class="tree-con" style="height: 320px;" id="tree-box5">
                        <ul id="ulUserTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    var pageObj = {teamId: '${teamsExtVO.id}'}
</script>
<%@ include file="/views/application/_include_bottom.jsp"%>