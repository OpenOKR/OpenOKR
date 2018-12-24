;
(function ($) {
    $.NumberUtils = $.NumberUtils || {};
    $.extend($.NumberUtils, {

        /**
         *
         * @param number 数值(非空)
         * @returns {boolean}
         */
        isNumber: function (number) {
            return typeof number === 'number' && isFinite(number);
        },


        /**
         * 千分位格式化
         * @param number 数值(非空)
         * @param decimalPlaces 小数位(可空)
         * @param thousandsSeparator 千分位分隔符(可空)
         * @param decimalSeparator 小数符，默认"."(可空)
         * @param prefix 前缀(可空)
         * @param suffix 后缀(可空)
         * @returns {*}
         */
        thousandsFormat: function (number, decimalPlaces, thousandsSeparator, decimalSeparator, prefix, suffix) {
            thousandsSeparator = thousandsSeparator != null ? thousandsSeparator : ",";
            prefix = prefix != null ? prefix : "";
            suffix = suffix != null ? suffix : "";

            if (!$.NumberUtils.isNumber(number)) {
                number *= 1;
            }
            if ($.NumberUtils.isNumber(number)) {
                var bNegative = (number < 0);
                var sOutput = String(number);
                var sDecimalSeparator = decimalSeparator || ".";
                var nDotIndex;
                if ($.NumberUtils.isNumber(decimalPlaces)) {
                    // Round to the correct decimal place
                    var nDecimalPlaces = decimalPlaces;
                    var nDecimal = Math.pow(10, nDecimalPlaces);
                    sOutput = String(Math.round(number * nDecimal) / nDecimal);
                    nDotIndex = sOutput.lastIndexOf(".");
                    if (nDecimalPlaces > 0) {
                        // Add the decimal separator
                        if (nDotIndex < 0) {
                            sOutput += sDecimalSeparator;
                            nDotIndex = sOutput.length - 1;
                        }
                        // Replace the "."
                        else if (sDecimalSeparator !== ".") {
                            sOutput = sOutput.replace(".", sDecimalSeparator);
                        }
                        // Add missing zeros
                        while ((sOutput.length - 1 - nDotIndex) < nDecimalPlaces) {
                            sOutput += "0";
                        }
                    }
                }
                if (thousandsSeparator) {
                    var sThousandsSeparator = thousandsSeparator;
                    nDotIndex = sOutput.lastIndexOf(sDecimalSeparator);
                    nDotIndex = (nDotIndex > -1) ? nDotIndex : sOutput.length;
                    var sNewOutput = sOutput.substring(nDotIndex);
                    var nCount = -1, i;
                    for (i = nDotIndex; i > 0; i--) {
                        nCount++;
                        if ((nCount % 3 === 0) && (i !== nDotIndex) && (!bNegative || (i > 1))) {
                            sNewOutput = sThousandsSeparator + sNewOutput;
                        }
                        sNewOutput = sOutput.charAt(i - 1) + sNewOutput;
                    }
                    sOutput = sNewOutput;
                }
                // Prepend prefix
                sOutput = (prefix) ? prefix + sOutput : sOutput;
                // Append suffix
                sOutput = (suffix) ? sOutput + suffix : sOutput;
                return sOutput;

            }
            return number;
        },

        /**
         * 反千分位格式化
         * e.g.
         * $.NumberUtils.unThousandsFormat("￥1,123,254.25$", ",", ".", "￥", "$")
         * =>1123254.25
         *
         * $.NumberUtils.unThousandsFormat("1,123,254.25")
         * =>1123254.25
         *
         * @param number 数值(非空)
         * @param thousandsSeparator 千分位分隔符,默认英文逗号(,)
         * @param decimalSeparator 小数符，默认英文点号(.)
         * @param prefix 前缀(可空)
         * @param suffix 后缀(可空)
         * @returns {*}
         */
        unThousandsFormat: function (number, thousandsSeparator, decimalSeparator, prefix, suffix) {
            var numberStr = "" + number,
                re = /([\.\*\_\'\(\)\{\}\+\?\\])/g,
                sep, stripTag, ret;

            thousandsSeparator || (thousandsSeparator = ",");
            sep = thousandsSeparator.replace(re, "\\$1");
            stripTag = new RegExp(sep, "g");
            ret = numberStr;
            if (prefix && prefix.length) {
                ret = ret.substr(prefix.length);
            }
            if (suffix && suffix.length) {
                ret = ret.substr(0, ret.length - suffix.length);
            }
            ret = ret.replace(stripTag, '').replace(decimalSeparator, '.');

            return ret;
        },

        /**
         * 百分比格式化
         * @param cellVal
         * @returns {*}
         */
        percentFormat: function (cellVal) {
            return $.isNumeric(cellVal) ? cellVal * 100 + "%" : "";
        },

        /**
         * 加
         * @param v1 第一值
         * @param v2 第二值
         * @param precision 精度小数位（可空）
         * @returns {*}
         */
        add: function (v1, v2, precision) {

            //        Number("123") = 123
            //        Number("123a") = NaN
            //        Number(null) = 0
            //        Number("") = 0
            //        Number(" ") = 0
            //        Number("null") = NaN
            //        Number(undefined) = NaN
            //        Number("-123") = -123
            //        isNaN(NaN) = true
            //        (-num) = -9
            //        0/123 = 0
            //        123/0 = Infinity
            //        abc+0 = abc0
            //        null+0 = 0
            //        abc-0 = NaN
            //        null-0 = 0
            //        abc*0 = NaN
            //        null*0 = 0
            //        abc/0 = NaN
            //        null/0 = NaN
            //        NaN+0 = NaN
            //        NaN-0 = NaN
            //        NaN*0 = NaN
            //        NaN/0 = NaN

            if (v1 === null || v1 === undefined || v1 === "" || v1 === " ") {
                v1 = NaN;
            } else {
                v1 = Number(v1);
            }
            if (v2 === null || v2 === undefined || v2 === "" || v2 === " ") {
                v2 = NaN;
            } else {
                v2 = Number(v2);
            }
            if (!isNaN(v1) && !isNaN(v2)) {
                return $.NumberUtils.round(v1 + v2, precision);//v1，v2都有值
            } else if (isNaN(v1) && !isNaN(v2)) {
                return $.NumberUtils.round(v2, precision);  //v1 没值以0计算
            } else if (!isNaN(v1) && isNaN(v2)) {
                return $.NumberUtils.round(v1, precision);  //v2 没值以0计算
            } else if (isNaN(v1) && isNaN(v2)) {
                return null;                                //v1，v2 没值
            }
        },

        /**
         * 减
         * @param v1 第一值
         * @param v2 第二值
         * @param precision 精度小数位（可空）
         * @returns {*}
         */
        sub: function (v1, v2, precision) {
            if (v1 === null || v1 === undefined || v1 === "" || v1 === " ") {
                v1 = NaN;
            } else {
                v1 = Number(v1);
            }
            if (v2 === null || v2 === undefined || v2 === "" || v2 === " ") {
                v2 = NaN;
            } else {
                v2 = Number(v2);
            }
            if (!isNaN(v1) && !isNaN(v2)) {
                return $.NumberUtils.round(v1 - v2, precision);//v1，v2都有值
            } else if (isNaN(v1) && !isNaN(v2)) {
                return $.NumberUtils.round(0 - v2, precision);  //v1 没值以0计算
            } else if (!isNaN(v1) && isNaN(v2)) {
                return $.NumberUtils.round(v1 - 0, precision);  //v2 没值以0计算
            } else if (isNaN(v1) && isNaN(v2)) {
                return null;                                //v1，v2 没值
            }
        },

        /**
         * 乘
         * @param v1 第一值
         * @param v2 第二值
         * @param precision 精度小数位（可空）
         * @returns {*}
         */
        multiply: function (v1, v2, precision) {
            if (v1 === null || v1 === undefined || v1 === "" || v1 === " ") {
                v1 = NaN;
            } else {
                v1 = Number(v1);
            }
            if (v2 === null || v2 === undefined || v2 === "" || v2 === " ") {
                v2 = NaN;
            } else {
                v2 = Number(v2);
            }
            if (!isNaN(v1) && !isNaN(v2)) {
                return $.NumberUtils.round(v1 * v2, precision);//v1，v2都有值
            } else if (isNaN(v1) && !isNaN(v2)) {
                return 0;                           //v1 没值以0计算
            } else if (!isNaN(v1) && isNaN(v2)) {
                return 0;                           //v2 没值以0计算
            } else if (isNaN(v1) && isNaN(v2)) {
                return null;                        //v1，v2 没值
            }
        },

        /**
         * 除
         * @param v1 第一值
         * @param v2 第二值
         * @param precision 精度小数位（可空）
         * @returns {*}
         */
        div: function (v1, v2, precision) {
            if (v1 === null || v1 === undefined || v1 === "" || v1 === " ") {
                v1 = NaN;
            } else {
                v1 = Number(v1);
            }
            if (v2 === null || v2 === undefined || v2 === "" || v2 === " ") {
                v2 = NaN;
            } else {
                v2 = Number(v2);
            }
            if (!isNaN(v1) && !isNaN(v2)) {
                //被除数不能为0
                if (v2 === 0) {
                    return null;
                }
                return $.NumberUtils.round(v1 / v2, precision);//v1，v2都有值
            } else if (isNaN(v1) && !isNaN(v2)) {
                return $.NumberUtils.round(0 / v2, precision);//v1 没值以0计算
            } else if (!isNaN(v1) && isNaN(v2)) {
                return null;                            //v2 没值以，被除数不能为0
            } else if (isNaN(v1) && isNaN(v2)) {
                return null;                            //v1，v2 没值
            }
        },

        /**
         * 四舍五入
         * @param v1 值
         * @param precision 精度小数位（可空）
         * @returns {*}
         */
        round: function (v1, precision) {
            if (v1 === null || v1 === undefined || v1 === "" || v1 === " ") {
                v1 = NaN;
            } else {
                v1 = Number(v1);
            }
            if (!isNaN(v1)) {
                //因为Number(null)==0,必须排除
                //precision允许是0，不能直接判断if(precision){}
                if (precision != null && !isNaN(Number(precision))) {
                    return Number(v1).toFixed(precision) * 1;
                } else {
                    return Number(v1) * 1;
                }
            } else {
                return null;
            }
        }
    });
})(jQuery);