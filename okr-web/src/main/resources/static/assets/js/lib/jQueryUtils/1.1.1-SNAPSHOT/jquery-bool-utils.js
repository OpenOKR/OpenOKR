;
(function ($) {
    $.BoolUtils = $.BoolUtils || {};
    $.extend($.BoolUtils, {
        /**
         * 将给定的值转为布尔类型
         * @param value： 要转为布尔类型的值
         * @returns {boolean}：布尔值
         */
        toBoolean: function (value) {
            if (value == null) {
                return false;
            }
            switch ($.type(value)) {
                case "string":
                    var val = $.trim(value);
                    return !(val === "" || val === "0" || val === "false");
                default :
                    return !!value;
            }
        }
    });
})(jQuery);