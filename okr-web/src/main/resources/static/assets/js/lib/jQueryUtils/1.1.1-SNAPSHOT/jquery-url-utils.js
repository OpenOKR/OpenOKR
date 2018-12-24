;
(function ($) {
    $.UrlUtils = $.UrlUtils || {};
    $.extend($.UrlUtils, {
        /**
         * 获得url中传递的参数值
         *  例： 获取URL指定url的指定参数
         *      var url = "/test.htm?param1=1&param2=2";
         *      getParameter("param1",url) 返回 1
         *      url为空，默认为location.href
         *
         *      var token = $.UrlUtils.getParameter("token");
         *
         * @param paramName 参数名
         * @param url 参数值
         * @returns {*}
         */
        getParameter: function (paramName, url) {
            if ($.trim(url) === "") {
                url = location.href;
            }
            var rs = new RegExp("(^|)" + paramName + "=([^&]*)(&|$)", "gi").exec(url), tmp;
            if (tmp = rs) {
                return tmp[2];
            }
            // parameter cannot be found
            return "";
        },

        /**
         * 替换url中参数值
         * @param paramName 参数名
         * @param paramValue 参数值
         * @param url url
         * @returns {string}
         */
        replaceParameter: function (paramName, paramValue, url) {
            if($.trim(url) === "") {
                url = location.href;
            }
            var rs = new RegExp("(^|)(" + paramName + ")=([^&]*)", "gi");
            return url.replace(rs, "$2=" + paramValue);
        },

        /**
         *设置url参数
         * 如果url存在该参数，则用给定的参数值去替换原来的参数值
         * 否则，则在URL中追加此参数
         * @param paramName 参数名
         * @param paramValue 参数值
         * @param url   url
         * @returns {string}
         */
        addOrReplaceParameter: function (paramName, paramValue, url) {
            if ($.trim(url) === "") {
                url = location.href;
            }

            if (this.getParameter(paramName, url) !== "") {
                return this.replaceParameter(paramName, paramValue, url);
            }
            return url + (url.indexOf("?") > 0 ? "&" : "?") + (paramName + "=" + paramValue);
        }
    });
})(jQuery);