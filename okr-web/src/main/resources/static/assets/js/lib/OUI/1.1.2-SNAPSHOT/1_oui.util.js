;
/**
 * 依赖 jQuery
 */
(function ($) {
    "use strict";
    //
    $.oui = $.oui || {};
    //
    $.extend(true, $.oui, {
        utils: {
            /**
             * 与元素相关工具方法
             */
            element: {
                /**
                 * 获得要元素设置高度为从起始top的位置到窗口底部的值
                 * @param jqEpr jqEpr 元素或jQuery expression
                 * @param paddingHeight 底部的留白高度
                 * @returns {number}
                 */
                getHeightToBottom: function (jqEpr, paddingHeight) {
                    var winHeight = $(window).height(),
                        $elements = $(jqEpr);
                    paddingHeight = paddingHeight || 0;
                    return winHeight - $elements.offset().top - paddingHeight;
                },

                /**
                 * 获得要元素设置高度为从起始top的位置到窗口底部的值
                 * @param jqEpr jqEpr 元素或jQuery expression
                 * @param paddingWidth 右边留白宽度
                 * @returns {number}
                 */
                getWidthToRight: function (jqEpr, paddingWidth) {
                    var winWidth = $(window).width(),
                        $elements = $(jqEpr);
                    paddingWidth = paddingWidth || 0;
                    return winWidth - $elements.offset().left - paddingWidth;
                },

                /**
                 * 将给定元素设置高度为从起始top的位置到窗口底部（paddingHeight为底部空白）
                 * @param jqEpr 自动高度的jQuery对象或表达式（ "#id" 或者 $("#id") ）
                 * @param paddingHeight 预留部分
                 */
                setHeightToBottom: function (jqEpr, paddingHeight) {
                    var winHeight = $(window).height(),
                        $elems = $(jqEpr);
                    paddingHeight = paddingHeight || 0;
                    $elems.each(function () {
                        var $that = $(this);
                        $that.outerHeight(winHeight - $that.offset().top - paddingHeight);
                    });
                },

                /**
                 * 自动高度，只能针对非控件元素
                 * 不适用于
                 *      1、js控件
                 *      2、重叠设置（内部包括的元素 也设置了自动高度）
                 * @param jqEpr 自动高度的jQuery对象或表达式（ "#id" 或者 $("#id") ）
                 * @param paddingHeight 预留部分
                 */
                autoHeight: function (jqEpr, paddingHeight) {
                    var elmHeight = $.oui.utils.element.getHeightToBottom(jqEpr, paddingHeight),
                        $elm = $(jqEpr),
                        $window = $(window),
                        timeObj = -1;
                    //
                    $elm.css("height", elmHeight);
                    //
                    $window.off("resize.autoHeight", jqEpr);
                    //
                    $window.on("resize.autoHeight", jqEpr, function () {
                        //清除时间对象，防止反复并发
                        window.clearTimeout(timeObj);
                        //
                        timeObj = window.setTimeout(function () {
                            elmHeight = $.oui.utils.element.getHeightToBottom(jqEpr, paddingHeight);
                            $elm.css("height", elmHeight);
                            window.clearTimeout(timeObj);
                        }, 100);//设定间隔时间，只有停下300毫秒不动后，再执行调整
                    });
                },

                /**
                 * 自动宽度，只能针对非控件元素
                 * 不适用于
                 *      1、js控件
                 *      2、重叠设置（内部包括的元素 也设置了自动宽度）
                 * @param jqEpr 自动高度的jQuery对象或表达式（ "#id" 或者 $("#id") ）
                 * @param paddingWidth 预留部分
                 */
                autoWidth: function (jqEpr, paddingWidth) {
                    var elmWidth = $.oui.utils.element.getWidthToRight(jqEpr, paddingWidth),
                        $elm = $(jqEpr),
                        $window = $(window),
                        timeObj = -1;
                    //
                    $elm.css("width", elmWidth);
                    //
                    $window.off("resize.autoWidth", jqEpr);
                    //
                    $window.on("resize.autoWidth", jqEpr, function () {
                        //清除时间对象，防止反复并发
                        window.clearTimeout(timeObj);
                        //
                        timeObj = window.setTimeout(function () {
                            elmWidth = $.oui.utils.element.getWidthToRight(jqEpr, paddingWidth);
                            $elm.css("width", elmWidth);
                            window.clearTimeout(timeObj);
                        }, 100);//设定间隔时间，只有停下300毫秒不动后，再执行调整
                    });
                },

                /**
                 * 居中显示
                 * @param jqEpr 自动高度的jQuery对象或表达式（ "#id" 或者 $("#id") ）
                 * @param config (支持偏移配置，例如：{offset: {top: -100,left:-50}} 向上偏移100，向左偏移50)
                 */
                showCenter: function (jqEpr, config) {
                    var $elm = $(jqEpr),
                        elmWidth = $elm.width(),
                        elmHeight = $elm.height(),
                        $window = $(window),
                        winWidth = $window.width(),
                        winHeight = $window.height(),
                        positionLeft = -1,
                        positionTop = -1;
                    //
                    if (winWidth > elmWidth) {
                        positionLeft = (winWidth - elmWidth) / 2;
                        if (config.offset && config.offset.left) {
                            positionLeft += config.offset.left;
                        }
                    }
                    if (winHeight > elmHeight) {
                        positionTop = (winHeight - elmHeight) / 2;
                        if (config.offset && config.offset.top) {
                            positionTop += config.offset.top;
                        }
                    }
                    if (positionLeft != -1) {
                        $elm.css({left: positionLeft});
                    }
                    if (positionTop != -1) {
                        $elm.css({top: positionTop});
                    }
                    $elm.show();
                },
                /**
                 * 获取当前屏幕 与 标屏(1024*768)的 宽度差
                 */
                getStandardWinDiffWidth: function () {
                    return $(window).width() - 1024;
                },

                /**
                 * 获取当前屏幕 与 标屏(1024*768)的 高度差
                 */
                getStandardWinDiffHeight: function () {
                    return $(window).height() - 768;
                },

                /**
                 * 获得元素离页面左边偏移量
                 * @param element
                 * @returns {Number|number}
                 */
                getElementLeft: function (element) {
                    var actualLeft = element.offsetLeft;
                    var current = element.offsetParent();

                    while (current !== null) {
                        actualLeft += current.offsetLeft;
                        current = current.offsetParent;
                    }

                    return actualLeft;
                },

                /**
                 * 获得元素离页面上边偏移量
                 * @param element
                 * @returns {Number|number}
                 */
                getElementTop: function (element) {
                    var actualTop = element.offsetTop;
                    var current = element.offsetParent;

                    while (current !== null) {
                        actualTop += current.offsetTop;
                        current = current.offsetParent;
                    }

                    return actualTop;
                },

                /**
                 * 获得元素在页面相对于视窗的位置
                 * @param element
                 * @returns {*}
                 */
                getBoundingClientRect: function (element) {
                    var scrollTop = document.documentElement.scrollTop;
                    var scrollLeft = document.documentElement.scrollLeft;

                    if (element.getBoundingClientRect) {
                        if (typeof arguments.callee.offset !== "number") {
                            var temp = document.createElement("div");

                            temp.style.cssText = "position:absolute; left: 0; top:0;";
                            document.body.appendChild(temp);
                            arguments.callee.offset = -temp.getBoundingClientRect().top - scrollTop;
                            document.body.removeChild(temp);
                            temp = null;
                        }
                        var rect = element.getBoundingClientRect();
                        var offset = arguments.callee.offset;

                        return {
                            left: rect.left + offset,
                            right: rect.right + offset,
                            top: rect.top + offset,
                            bottom: rect.bottom + offset
                        }
                    } else {
                        var actualLeft = $.oui.utils.element.getElementLeft(element);
                        var actualTop = $.oui.utils.element.getElementTop(element);

                        return {
                            left: actualLeft - scrollLeft,
                            right: actualLeft + element.offsetWidth - scrollLeft,
                            top: actualTop - scrollTop,
                            bottom: actualTop + element.offsetHeight - scrollTop
                        }
                    }
                }

            },
            /**
             * 与字符相关工具方法
             */
            string: {
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
                }
            },

            /**
             * 日期工具
             */
            date: {

                /**
                 * 将日期对象 格式化 为 格式字符
                 * @param date 日期对象
                 * @param dateFormat  目标格式 yyyy-MM-dd , dd/MM/yyyy , yyyy-MM-dd HH:mm:ss 等
                 * @returns {string}
                 */
                format: function (date, dateFormat) {
                    if (!date) return date;
                    var format_fun_map,
                        result = "",
                        dateUtil = $.oui.utils.date;
                    //
                    dateFormat = dateUtil._parseFormat(dateFormat);
                    format_fun_map = {
                        h: function (d) {
                            var h = d.getHours();
                            h > 12 ? h = h - 12 : h;

                            if (h == 0) {
                                return 12;
                            }
                            return h;
                        },
                        hh: function (d) {

                            var h = d.getHours();
                            h > 12 ? h = h - 12 : h;

                            if (h == 0) {
                                return 12;
                            }
                            return dateUtil._addZero(h);
                        },
                        HH: function (d) {
                            return dateUtil._addZero(d.getHours());
                        },
                        /**
                         * @return {number}
                         */
                        M: function (d) {
                            return d.getMonth() + 1;
                        },
                        MM: function (d) {
                            return dateUtil._addZero(d.getMonth() + 1);
                        },
                        m: function (d) {
                            return d.getMinutes();
                        },
                        mm: function (d) {
                            return dateUtil._addZero(d.getMinutes());
                        },
                        s: function (d) {
                            return d.getSeconds();
                        },
                        ss: function (d) {
                            return dateUtil._addZero(d.getSeconds());
                        },
                        yyyy: function (d) {
                            return d.getFullYear();
                        },
                        yy: function (d) {
                            return d.getYear() % 100 < 10 ? "0" : "" + d.getYear() % 100
                        },
                        d: function (d) {
                            return d.getDate();
                        },
                        dd: function (d) {
                            return dateUtil._addZero(d.getDate());
                        }
                    };
                    if (dateFormat.parts.length) {
                        for (var i = 0, cnt = dateFormat.parts.length; i < cnt; i++) {
                            result += dateFormat.separators[i] + format_fun_map[dateFormat.parts[i]](date);
                        }
                    }
                    return result;
                },

                /**
                 * 日期反格式化
                 * 日期字符串 转为 日期对象
                 * @param dateString 日期字符
                 * @param dateFormat    日期格式
                 * @returns {*}
                 *
                 * test result
                 *      parseDate("2015-09-10","yyyy-MM-dd")->Sat Oct 10 2015 00:00:00 GMT+0800 (中国标准时间)
                 *      parseDate("2015/12/10","yyyy/MM/dd")->Thu Dec 10 2015 00:00:00 GMT+0800 (中国标准时间)
                 *      parseDate("01/12/2015 28:01:12","dd/MM/yyyy HH:mm:ss")->Wed Dec 02 2015 04:01:12 GMT+0800 (中国标准时间)
                 */
                unFormat: function (dateString, dateFormat) {
                    if (!dateString) return dateString;
                    if (dateString instanceof Date) return dateString;
                    var parts, val, part, format_fun_map,
                        dateJSONObj = {
                            yyyy: 0, M: 0, d: 0, h: 0, m: 0, s: 0
                        };
                    //setters_order = ['yyyy', 'yy', 'MM', 'M', 'dd', 'd', 'hh', 'h', 'HH', 'H', 'mm', 'm', 'ss', 's'],
                    dateFormat = $.oui.utils.date._parseFormat(dateFormat);
                    parts = dateString && dateString.toString().match(/[^ -\/:-@\[-`{-~\t\n\rTZ]+/g) || [];
                    //result = new Date(0, 0, 0, 0, 0, 0, 0);
                    //format_fun_map = {
                    //    h: function (d, v) {
                    //        return d.setHours(v);
                    //    },
                    //    M: function (d, v) {
                    //        v -= 1;
                    //        return d.setMonth(v);
                    //    },
                    //    m: function (d, v) {
                    //        return d.setMinutes(v);
                    //    },
                    //    s: function (d, v) {
                    //        return d.setSeconds(v);
                    //    },
                    //    yyyy: function (d, v) {
                    //        return d.setFullYear(v);
                    //    },
                    //    yy: function (d, v) {
                    //        return d.setFullYear(new Date().getFullYear() - new Date().getFullYear() % 100 + v);
                    //    },
                    //    d: function (d, v) {
                    //        return d.setDate(v);
                    //    }
                    //};
                    //format_fun_map['dd'] = format_fun_map['d'];
                    //format_fun_map['ss'] = format_fun_map['s'];
                    //format_fun_map['mm'] = format_fun_map['m'];
                    //format_fun_map['MM'] = format_fun_map['M'];
                    //format_fun_map['HH'] = format_fun_map['H'] = format_fun_map['hh'] = format_fun_map['h'];
                    //if (parts.length == format.parts.length) {
                    //    for (var i = 0, cnt = format.parts.length; i < cnt; i++) {
                    //        val = parseInt(parts[i], 10);
                    //        part = format.parts[i];
                    //        format_fun_map[format.parts[i]](result, val);
                    //    }
                    //}
                    //return result;

                    format_fun_map = {
                        h: function (v) {
                            dateJSONObj.h = v;
                        },
                        M: function (v) {
                            v -= 1;
                            dateJSONObj.M = v;
                        },
                        m: function (v) {
                            dateJSONObj.m = v;
                        },
                        s: function (v) {
                            dateJSONObj.s = v;
                        },
                        yyyy: function (v) {
                            dateJSONObj.yyyy = v;
                        },
                        yy: function (v) {
                            dateJSONObj.yyyy = v;
                        },
                        d: function (v) {
                            dateJSONObj.d = v;
                        }
                    };

                    format_fun_map['dd'] = format_fun_map['d'];
                    format_fun_map['ss'] = format_fun_map['s'];
                    format_fun_map['mm'] = format_fun_map['m'];
                    format_fun_map['MM'] = format_fun_map['M'];
                    format_fun_map['HH'] = format_fun_map['H'] = format_fun_map['hh'] = format_fun_map['h'];
                    if (parts.length == dateFormat.parts.length) {
                        for (var i = 0, cnt = dateFormat.parts.length; i < cnt; i++) {
                            val = parseInt(parts[i], 10);
                            part = dateFormat.parts[i];
                            format_fun_map[dateFormat.parts[i]](val);
                        }
                    }
                    return new Date(dateJSONObj.yyyy, dateJSONObj.M, dateJSONObj.d,
                        dateJSONObj.h, dateJSONObj.m, dateJSONObj.s);
                },

                /**
                 * 私有
                 * @param format
                 * @returns {{separators: Array, parts: (Array|{index: number, input: string}|*|{ID, CLASS, NAME, ATTR, TAG, CHILD, POS, PSEUDO})}}
                 * @private
                 */
                _parseFormat: function (format) {
                    //yyyy-MM-dd hh:mm:ss
                    var format_part_pattern = /hh?|HH?|ss?|dd?|DD?|mm?|MM?|yy(?:yy)?/g,
                        // User as the delimiter of format
                        separators = format.replace(format_part_pattern, '\0').split('\0'),
                        parts = format.match(format_part_pattern);
                    if (!separators || !separators.length || !parts || parts.length == 0) {
                        throw new Error('Invalid date format.');
                    }
                    return {separators: separators, parts: parts};
                },

                /**
                 * 私有方法不建议调用
                 * 不满10 补0
                 * @param n
                 * @returns {string}
                 * @private
                 */
                _addZero: function (n) {
                    return n < 10 ? "0" + n : n;
                }
            },

            /**
             * 数值工具
             */
            number: {

                /**
                 *
                 * @param number 数值(非空)
                 * @returns {boolean}
                 */
                isNum: function (number) {
                    return typeof number === 'number' && isFinite(number);
                },

                /**
                 * 货币/数值/金额 格式化器
                 * @param number 数值(非空)
                 * @param decimalPlaces 小数位(可空)
                 * @param thousandsSeparator 千分位分隔符(可空)
                 * @param decimalSeparator 小数符，默认"."(可空)
                 * @param prefix 前缀(可空)
                 * @param suffix 后缀(可空)
                 * @returns {*}
                 */
                currency: function (number, decimalPlaces, thousandsSeparator, decimalSeparator, prefix, suffix) {
                    thousandsSeparator = thousandsSeparator != null ? thousandsSeparator : "";
                    prefix = prefix != null ? prefix : "";
                    suffix = suffix != null ? suffix : "";
                    if (!$.oui.utils.number.isNum(number)) {
                        number *= 1;
                    }
                    if ($.oui.utils.number.isNum(number)) {
                        var bNegative = (number < 0);
                        var sOutput = String(number);
                        var sDecimalSeparator = decimalSeparator || ".";
                        var nDotIndex;
                        if ($.oui.utils.number.isNum(decimalPlaces)) {
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
                 * 货币/数值/金额 反格式化器
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
                unCurrency: function (number, thousandsSeparator, decimalSeparator, prefix, suffix) {
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
                }

            }
        }
    });

})(jQuery);