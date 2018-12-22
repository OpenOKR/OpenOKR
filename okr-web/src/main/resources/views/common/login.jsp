<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageJs" value="${staticContextPath}/assets/js/common/login.js"/>
<c:set var="pageTitle" value="OKR管理系统"/>
<%@ include file="/views/application/_include_top_login.jsp" %>
<body>
    <form id="loginForm" action="${contextPath}/login.htm" method="post">
        <div>
            <div>
                <input id="username" name="username" type="text" placeholder="用户名"/>
            </div>
        </div>
        <div>
            <div>
                <input id="password" name="password" type="password" placeholder="用户密码" onkeydown="submitFrom(this.event)"/>
            </div>
        </div>
        <div style="display: none;">
            <input id="validateCode" name="validateCode" type="text" maxlength="4" placeholder="验证码"
                   onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;"/>
            <img id="validateCodeImg" src="${contextPath}/validateCodeServlet"> <a id="kanbuq" href="javascript:;">看不清，换一张</a> </div>
        </div>
        <div>
            <label><input type="checkbox" name="rememberMe" id="rememberMe">使我保持登录状态</label>
        </div>
        <div>
            <div>
                <input type="submit" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
            </div>
        </div>
    </form>
</body>
<%@ include file="/views/application/_include_bottom.jsp" %>