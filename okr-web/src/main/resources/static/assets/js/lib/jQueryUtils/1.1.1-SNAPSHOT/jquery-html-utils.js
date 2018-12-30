;
(function ($) {
    $.HtmlUtils = $.HtmlUtils || {};
    $.extend($.HtmlUtils, {

        /**
         * html 解码，
         * 例： &lt;a&gt;123&lt;/a&gt; 转为 <a>123</a>
         * @param value
         * @returns {*}
         */
        decode: function (value) {
            if (value && (value === '&nbsp;' || value === '&#160;' || (value.length === 1 && value.charCodeAt(0) === 160))) {
                return "";
            }
            return !value ? value : String(value).replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"').replace(/&amp;/g, "&");
        },

        /**
         * html 转码，
         * 例： <a>123</a> 转为 &lt;a&gt;123&lt;/a&gt;
         * @param value
         * @returns {string}
         */
        encode: function (value) {
            return !value ? value : String(value).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
        }

    });
})(jQuery);