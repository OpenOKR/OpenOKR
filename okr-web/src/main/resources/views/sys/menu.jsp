<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="菜单管理"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/sys/menu.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wrapper">
    <%-- 上部功能按钮区 --%>
    <div class="top-search">
        <div class="fl">
            <div class="site-search-pop search-bar search-bar-no-icon">
                <div class="sj-search-title-con">
                    <input id="queryMenuTree" type="text" placeholder="请输入菜单名称">
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
                <div id="queryMenuTreeContainer"></div>
            </div>
            <div class="column-right">
                <form id="menuForm">
                    <ul class="form-grid font6">
                        <li class="col-sm-12 ">
                            <label class="form-lab">菜单名称<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="name" type="text" class="inp inp-royal mr20" value="" placeholder="菜单名称">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">上级菜单<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="parentName" type="text" class="inp inp-royal mr20" value="" placeholder="上级菜单">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">优先级<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="priority" type="text" class="inp inp-royal mr20" value="" placeholder="优先级">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">链接<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="url" type="text" class="inp inp-royal mr20" value="#" placeholder="链接">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">权限前缀码<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="permissionPrefixCode" type="text" class="inp inp-royal mr20" value="" placeholder="权限前缀码">
                                <span class="m-tip-block">
		                            <i class="iconfont icon-wenhao"></i>例："用户管理"的权限前缀码为"User"，查看权限码：User:view、新增/修改/保存 权限码：User:edit、删除权限码：User:delete
		                        </span>
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">描述</label>
                            <div class="form-control">
                                <input name="description" type="text" class="inp inp-royal mr20" value="" placeholder="描述">
                            </div>
                        </li>
                    </ul>

                    <div class="pt5">
                        <div class="tab-content">
                            <div class="tab-pane active">
			                    	<span class="pointer mr5" onclick="pageObj.addPermission();">
										<i class="iconfont icon-add" style="position: relative;top:-1px;"></i>
										添加权限
									</span>
                                <span class="pointer" onclick="pageObj.deletePermission();">
										<i class="iconfont icon-del" style="position: relative;top:-1px;"></i>
										删除权限
									</span>
                                <div>
                                    <table id="permissionGrid"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="mt10">
                        <a class="btn btn-success" onclick="pageObj.save();">保存</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/views/application/_include_bottom.jsp" %>