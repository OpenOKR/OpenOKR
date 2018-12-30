var ajaxUtil = ajaxUtil || {

    /**
     * 错误
     * @param xhr
     * @param errorMsg
     * @param errorThrow
     */
    error: function (xhr, errorMsg, errorThrow) {
        var _arguments = arguments;
        require(["Tips"], function () {
            if (xhr.responseJSON && xhr.responseJSON.message) {
                tips({type: Tips.TYPE.WARN, content: xhr.responseJSON.message});
            } else {
                require(["Tips"], function () {
                    if (xhr.status !== 200 && ts !== "parsererror") {
                        TipsUtil.error("系统服务发生错误，" + xhr.status + ":" + xhr.statusText);
                    } else if (xhr.status === 200 && errorMsg === "parsererror") {
                        TipsUtil.error("后台服务响应的数据格式错误，" + errorThrow);
                    } else if (console && console.log) {
                        for (var index in _arguments) {
                            console.log(_arguments[index]);
                        }
                    }
                });
            }
        });
    },

    /**
     * 在进行ajax请求时锁屏
     * @param props ajax参数
     * @param successCallBack 成功回调函数
     */
    ajaxWithBlock: function (props, successCallBack) {
        require(["jQueryBlockUI"], function () {
            props = $.extend({}, {
                beforeSend: function (xhr) {
                    //打开等待条
                    blockUIUtil.show();
                },
                success: function (response) {
                    //关闭等待条
                    blockUIUtil.hide();
                    if (successCallBack) {
                        successCallBack(response);
                    } else {
                        require(["Tips"], function () {
                            if ($.isPlainObject(response) && response.success != null && !response.success) {
                                TipsUtil.error(response.message);
                            } else {
                                TipsUtil.info(response.message);
                            }
                        });
                    }
                },
                error: function (xhr, errorMsg, errorThrow) {
                    //关闭等待条
                    blockUIUtil.hide();
                    ajaxUtil.error(xhr, errorMsg, errorThrow);
                }
            }, props);
            $.ajax(props);
        });
    }

};

/**
 * 设置全局 AJAX 默认选项。
 */
$.ajaxSetup({
    type: "POST",
    async: true,
    global: true,
    complete: function (jqXHR) {
        require(["Tips"], function () {
            var _timeObj;
            if (jqXHR.status === 501) {
                //如果是登陆超时，主动调到登陆页
                tips({type: 1, content: "登陆超时，3秒后回到首页！"});
                //
                _timeObj = window.setTimeout(function () {
                    top.location.href = App["contextPath"] + "/";
                    window.clearTimeout(_timeObj);
                }, 3000);
            } else if (jqXHR.status === 800) { //800 is session invalidate status.
                //如果是Session失效，主动调到登陆页
                tips({type: 1, content: "Session失效，即将跳转到登录页面..."});
                //
                _timeObj = window.setTimeout(function () {
                    top.location.href = App["contextPath"] + "/";
                    window.clearTimeout(_timeObj);
                }, 3000);
            }
        });
    },
    error: ajaxUtil.error
});