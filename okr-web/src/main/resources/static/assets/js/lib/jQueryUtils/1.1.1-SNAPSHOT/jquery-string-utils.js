;
(function ($) {
    $.StringUtils = $.StringUtils || {};
    $.extend($.StringUtils, {
        getEntityName: function (objectName) {
            var objectProp = (objectName.substr(objectName.lastIndexOf(".") + 1));
            objectProp = (objectProp.substring(0, 1).toLowerCase()) + objectProp.substring(1, objectProp.length);
            return objectProp;
        },
        getEntityId: function (objectName) {
            return $.StringUtils.getEntityName(objectName) + "Id";
        },
        firstLowerCase: function (str) {
            return str.substring(0, 1).toLowerCase() + str.substring(1, str.length);
        },
        firstUpperCase: function (str) {
            return str.substring(0, 1).toUpperCase() + str.substring(1, str.length);
        },
        isEmpty: function (str) {
            return str ? false : true;
        },
        leftTrim: function (str) {
            return str.replace(/(^[\s]*)/, "");
        },
        rightTrim: function (str) {
            return str.replace(/(\s*$)/g, "");
        },
        allTrim: function (str) {
            return str.replace(/\s+/g, "");
        },
        /**
         * Simple string-templating. Accepts a string template as the first argument. The second is optional: If
         * specified, it is used to replace placeholders in the first argument. Example: $.StringUtils.format(“Please
         * enter a value between {0} and {1}.”, 4, 8) result : “Please enter a value between 4 and 8.”
         */
        format: function (format) {
            var args = $.makeArray(arguments).slice(1);
            if (format == null) {
                format = "";
            }
            return format.replace(/\{(\d+)\}/g, function (m, i) {
                return args[i];
            });
        },
        /**
         * 判断字符串是否以给定字符串开始
         *
         * @param str
         * @param searchString 查找的字符串
         * @param position 起始查找位置
         * @returns {boolean}
         */
        startsWith: function (str, searchString, position) {
            position = position || 0;
            return String(str).indexOf(searchString, position) === position;
        },

        startsWithIgnoreCase: function (str, searchString, position) {
            str = (str != null && str.toUpperCase()) || str;
            return this.startsWith(str, searchString.toUpperCase(), position);
        },
        /**
         * @param str
         * @param searchString 查找的字符串
         * @param position 起始查找位置
         * @returns {boolean}
         */
        endsWith: function (str, searchString, position) {
            position = position || str.length;
            position = position - String(searchString).length;
            var lastIndex = String(str).lastIndexOf(searchString);
            return lastIndex !== -1 && lastIndex === position;
        },

        endsWithIgnoreCase: function (str, searchString, position) {
            str = (str != null && str.toUpperCase()) || str;
            return this.endsWith(str.toUpperCase(), searchString.toUpperCase(), position);
        },

        // e.g. 'I-like-cookies'.camelCase(); // returns 'ILikeCookies'
        camelCase: function (str) {
            return String(str).replace(/-\D/g, function (match) {
                return match.charAt(1).toUpperCase();
            });
        },

        // e.g. 'ILikeCookies'.hyphenate(); // returns '-i-like-cookies'
        hyphenate: function (str) {
            return String(str).replace(/[A-Z]/g, function (match) {
                return ('-' + match.charAt(0).toLowerCase());
            });
        },
        repeat: function (str, n) {
            return new Array(n + 1).join(String(str));
        },
        toHex: function (str) {
            var val = "";
            for (var i = 0; i < str.length; i++) {
                if (val == "")
                    val = '\\x' + str.charCodeAt(i).toString(16);
                else
                    val += '\\x' + str.charCodeAt(i).toString(16);
            }
            return val;
        },
        /**
         * 生产32位的UUID
         */
        uuid: function (len, radix) {
            var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split(''),
                uuid = [], i;
            radix = radix || chars.length;
            if (len) {
                // Compact form
                for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
            } else {
                // rfc4122, version 4 form
                var r;
                // rfc4122 requires these characters
                uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
                uuid[14] = '4';
                // Fill in random data.  At i==19 set the high bits of clock sequence as
                // per rfc4122, sec. 4.1.5
                for (i = 0; i < 36; i++) {
                    if (!uuid[i]) {
                        r = 0 | Math.random() * 16;
                        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                    }
                }
            }
            return uuid.join('');
        }
    });
})(jQuery);