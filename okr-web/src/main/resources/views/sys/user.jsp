<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="用户管理"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/sys/user.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div class="wrapper">
    <%-- 上部功能按钮区 --%>
    <div class="top-search">
        <div class="fl">
            <div class="site-search-pop search-bar search-bar-no-icon">
                <div class="sj-search-title-con">
                    <input id="queryUserCombo" type="text" placeholder="请输入登录账号或姓名或手机号码">
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
                <div id="queryUserComboContainer"></div>
            </div>
            <div class="column-right">
                <form id="userForm">
                    <ul class="form-grid font4">
                        <li class="col-sm-12 ">
                            <label class="form-lab">用户名<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="userName" type="text"   minlength="2" maxlength="20" class="inp inp-royal mr20" value="" placeholder="用户名">
                                <span class="m-tip-block">
		                            <i class=" iconfont icon-wenhao"></i>请输入2-20位字符、字母、数字、以及汉字，不能包含非法字符
		                        </span>
                            </div>
                        </li>
                        <li class="col-sm-12">
                            <label class="form-lab">是否启用<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
		                        <span class="checkbox-inline mr20">
                                    <input type="checkbox" name="active" checked="checked">
		                        </span>
                                <span class="m-tip-block mt3">
		                            <i class="iconfont icon-wenhao"></i>一旦禁用，该用户将无法登录系统
		                        </span>
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">所属机构<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input id="organizationName" name="organizationName" type="text" class="inp inp-royal mr20" value="" placeholder="所属机构">
                                <input id="orgCode" name="organizationCode" type="hidden">
                            </div>
                        </li>
                        <input id="organizationId" name="organizationId" type="hidden">
                        <li class="col-sm-12 ">
                            <label class="form-lab">真实姓名<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="realName" maxlength="20"  type="text" class="inp inp-royal mr20" value="" placeholder="真实姓名">
                            </div>
                        </li>
                        <li class="col-sm-12 " style="display: none;">
                            <label class="form-lab">E-Mail<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input name="email" type="text" class="inp inp-royal mr20" value="" placeholder="E-Mail">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">手机号码</label>
                            <div class="form-control">
                                <input name="phone" minlength="11" maxlength="11"  onkeypress = "return event.keyCode>=48&&event.keyCode<=57||event.keyCode==46" type="text" class="inp inp-royal mr20" value="" placeholder="请输入11位手机号码">
                            </div>
                        </li>
                        <li class="col-sm-12">
                            <label class="form-lab">初始密码</label>
                            <div class="form-control">
		                        <span class="initial-password-text">123456
		                            <a class="link ml10 mr10" onclick="pageObj.resetPassword();">重置密码</a>
		                        </span>
                                <span class="m-tip-block">
		                            <i class="iconfont icon-wenhao"></i>用户可进入个人中心修改密码；
		                        </span>
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <label class="form-lab">用户角色<span class="required"><i class="iconfont icon-xinghao"></i></span></label>
                            <div class="form-control">
                                <input id="roleName" name="roleName" type="text" class="inp inp-royal mr20" value="" placeholder="用户角色">
                                <input id="roleId" name="roleId" type="hidden">
                            </div>
                        </li>
                        <li class="col-sm-12 ">
                            <div class="form-lab">
                                <a class="btn btn-success" onclick="pageObj.save();">保存</a>
                            </div>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var pageObj = {currentOrganizationId: '${currentUser.organizationId}'};
</script>
<%@ include file="/views/application/_include_bottom.jsp" %>