<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageJs" value="${staticContextPath}/assets/js/common/login.js"/>
<c:set var="pageTitle" value="OKR管理系统"/>
<%@ include file="/views/application/_include_top_login.jsp" %>
<body class="login-body">
    <div class="login-out">
        <h1 class="logo"><img src="${staticContextPath}/assets/images/logo-login.png" alt="logo" ></h1>
        <img src="${staticContextPath}/assets/images/sub-login.png" alt="" class="logo-sub"/>
        <div class="login">
            <div class="login-in">
                <div class="login-form">
                    <h3 class="login-tit"><span>欢迎回来！</span></h3>
                    <p class="login-desc"><span>离你的目标越来越近啦～</span></p>
                    <form id="loginForm" action="${contextPath}/login.htm" method="post">
                        <!-- item-focus 鼠标移入效果，item-error输入报错状态，item-success输入成确状态 -->
                        <div class="item ">
                            <label class="lab-sub"> 用户 </label>
                            <input id="username" name="username" type="text"/>
                        </div>
                        <div class="item">
                            <label class="lab-sub"> 密码 </label>
                            <input id="password" name="password" type="password" onkeydown="submitFrom(this.event)"/>
                            <div class="msg-box error"><i class="iconfont icon-waring"></i>${message}</div>
                        </div>
                        <!-- 输错三次出现验证码 -->
                        <div class="item item-code" style="display: none;" id="validateCodeDiv">
                            <label class="lab-sub"> 验证码 </label>
                            <input id="validateCode" name="validateCode" type="text"/>
                        </div>
                        <button class="btn btn-primary waves-effect waves-light" id="loginBtn" type="submit">登录</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
    var pageObj = {message: '${message}', loginFailCount: '${loginFailCount}'}
</script>
<%@ include file="/views/application/_include_bottom.jsp" %>