var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {
        initForm: function () {
            require(["validation", "RSA"], function () {
                $("#loginForm").validate({
                    rules: {
                        username: {required:true},
                        password: {required:true}
                    },
                    messages: {
                        username: {required: "请填写用户名."},
                        password: {required: "请填写密码."},
                        validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
                    },
                    submitHandler: function (form) {
                        $.ajax({
                            async: false,
                            type: 'get',
                            url: App.contextPath + "pkey.json?_t=" + Math.random(),
                            success: function (rs) {
                                var rsaKey = new RSAKey();
                                rsaKey.setPublic(b64tohex(rs.modulus), b64tohex(rs.exponent));
                                var encryPasswd = hex2b64(rsaKey.encrypt($('#password').val()));
                                $('#password').val(encryPasswd);
                                form.submit();
                            }
                        });
                        return false;
                    }
                });
            });
        },

        initEvent:function () {
            //登录页效果
            $(".item input").focus(function(){
                var $parent=$(this).parents(".item");
                $parent.addClass("item-focus");
                if($(this).val().length>0){
                    $parent.addClass("item-change");
                }
            }).blur(function(){
                var $parent=$(this).parents(".item");
                if($(this).val().length==0){
                    $parent.removeClass("item-focus");
                }
                $parent.removeClass("item-change");
            }).keyup(function(){
                var $parent=$(this).parents(".item");
                if($(this).val().length>0){
                    $parent.addClass("item-change");
                }else {
                    $parent.removeClass("item-change");
                }
            });
        }
    });

    $(window).ready(function () {
        if (window.self !== window.top) {
            top.window.location.href = App["contextPath"];
        }
        pageObj.initForm();
        pageObj.initEvent();

        //登录验证码请求
        if (parseInt(pageObj.loginFailCount) > 3) {
            $('#validateCodeDiv').show();
            $('#validateCodeDiv').append("<img id='validateCodeImg' src='" + App.contextPath + "/validateCodeServlet'/>");
        }
        //登录错误消息
        if (pageObj.message !== '') {
            $(".item").addClass("item-error");
        }
    });
});