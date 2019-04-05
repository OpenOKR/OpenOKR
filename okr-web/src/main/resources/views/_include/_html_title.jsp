<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${pageTitle}</title>
<script>

  function isPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
      "SymbianOS", "Windows Phone",
      "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
      if (userAgentInfo.indexOf(Agents[v]) > 0) {
        flag = false;
        break;
      }
    }
    return flag;
  }
  //如果不是pc端，则跳转到移动端登录
  if(!isPC()){
    window.location.href = '/okrApp/Login';
  }
</script>