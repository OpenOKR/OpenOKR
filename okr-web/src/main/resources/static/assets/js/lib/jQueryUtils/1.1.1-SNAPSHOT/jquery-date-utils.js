;
(function ($) {
    $.DateUtils = $.DateUtils || {};
    $.extend($.DateUtils, {
        /**
         * 根据 指定日期 否则以当前日期 对象 返回 YYYY-MM-DD HH:MI:SS 格式日期字符
         * @param date
         * @returns {string}
         */
        getDateTimeString: function (date) {
            var newDate = date ? date : new Date();
            return newDate.getFullYear() + "-" + this._addZero((newDate.getMonth() + 1)) + "-" + this._addZero(newDate.getDate()) + " " + this._addZero(newDate.getHours()) + ":"
                + this._addZero(newDate.getMinutes()) + ":" + this._addZero(newDate.getSeconds());
        },

        /**
         * 根据 指定日期 否则以当前日期 对象 返回 YYYY-MM-DD HH:MI 格式日期字符
         * @param date
         * @returns {string}
         */
        getDateTimeMinutesString: function (date) {
            var newDate = date ? date : new Date();
            return newDate.getFullYear() + "-" + this._addZero((newDate.getMonth() + 1)) + "-" + this._addZero(newDate.getDate()) + " " + this._addZero(newDate.getHours()) + ":"
                + this._addZero(newDate.getMinutes());
        },

        /**
         * 根据 指定日期 否则以当前日期 对象 返回 YYYY-MM-DD 格式日期字符
         * @param date
         * @returns {string}
         */
        getDateString: function (date) {
            var newDate = date ? date : new Date();
            return newDate.getFullYear() + "-" + this._addZero((newDate.getMonth() + 1)) + "-" + this._addZero(newDate.getDate());
        },

        /**
         * 根据 指定日期 否则以当前日期 对象 返回 YYYY-MM 格式日期字符
         * @param date
         * @returns {string}
         */
        getYearAndMonthString: function (date) {
            var newDate = date ? date : new Date();
            return newDate.getFullYear() + "-" + this._addZero((newDate.getMonth() + 1));
        },

        /**
         * 根据 指定日期 否则以当前日期 对象 返回 MM-DD 格式日期字符
         * @param date
         * @returns {string}
         */
        getMonthAndDayString: function (date) {
            var newDate = date ? date : new Date();
            return this._addZero((newDate.getMonth() + 1)) + "-" + newDate.getDate();
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
        },

        /**
         * 将日期对象 格式化 为 格式字符
         * @param date 日期对象
         * @param fmt  目标格式 yyyy-MM-dd , dd/MM/yyyy , yyyy-MM-dd HH:mm:ss 等
         * @returns {string}
         */
        getFormatDateString: function (date, fmt) {
            if (!date) return date;
            var self = this, result, format_fun_map;
            fmt = self._parseFormat(fmt);
            result = "";
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
                    return self._addZero(h);
                },
                HH: function (d) {
                    return self._addZero(d.getHours());
                },
                /**
                 * @return {number}
                 */
                M: function (d) {
                    return d.getMonth() + 1;
                },
                MM: function (d) {
                    return self._addZero(d.getMonth() + 1);
                },
                m: function (d) {
                    return d.getMinutes();
                },
                mm: function (d) {
                    return self._addZero(d.getMinutes());
                },
                s: function (d) {
                    return d.getSeconds();
                },
                ss: function (d) {
                    return self._addZero(d.getSeconds());
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
                    return self._addZero(d.getDate());
                }
            };
            if (fmt.parts.length) {
                for (var i = 0, cnt = fmt.parts.length; i < cnt; i++) {
                    result += fmt.separators[i] + format_fun_map[fmt.parts[i]](date);
                }
            }
            return result;
        },

        /**
         * 将给定的字符串转成日期
         * 1.目前支持yyyy-MM-dd与yyyy-MM-dd hh:mm:ss两种格式的字符串，
         * 2.其中连接符不限,即2014-03-01与2014/03/01都可以
         * @param dateStr 日期字符串
         * @returns 日期
         */
        createDate: function (dateStr) {
            var
                regThree = /^\D*(\d{2,4})\D+(\d{1,2})\D+(\d{1,2})\D*$/,
                regSix = /^\D*(\d{2,4})\D+(\d{1,2})\D+(\d{1,2})\D+(\d{1,2})\D+(\d{1,2})\D+(\d{1,2})\D*$/,
                str = "",
                date = null;

            if (regThree.test(dateStr)) {
                str = dateStr.replace(/\s/g, "").replace(regThree, "$1/$2/$3");
                date = new Date(str);
            } else if (regSix.test(dateStr)) {
                str = dateStr.match(regSix);
                date = new Date(str[1], str[2] - 1, str[3], str[4], str[5], str[6]);
            }
            if (isNaN(date))
                return null;
            else
                return date;
        },

        /**
         * 将日期字符串 转为 日期对象
         * @param value
         * @param format
         * @returns {*}
         *
         * test result
         *      parseDate("2015-09-10","yyyy-MM-dd")->Sat Oct 10 2015 00:00:00 GMT+0800 (中国标准时间)
         *      parseDate("2015/12/10","yyyy/MM/dd")->Thu Dec 10 2015 00:00:00 GMT+0800 (中国标准时间)
         *      parseDate("01/12/2015 28:01:12","dd/MM/yyyy HH:mm:ss")->Wed Dec 02 2015 04:01:12 GMT+0800 (中国标准时间)
         */
        parseDate: function (value, format) {
            if (!value) return value;
            if (value instanceof Date) return value;
            var parts, val, part, format_fun_map,
                dateJSONObj = {
                    yyyy: 0, M: 0, d: 0, h: 0, m: 0, s: 0
                };
            //setters_order = ['yyyy', 'yy', 'MM', 'M', 'dd', 'd', 'hh', 'h', 'HH', 'H', 'mm', 'm', 'ss', 's'],
            format = this._parseFormat(format);
            parts = value && value.toString().match(/[^ -\/:-@\[-`{-~\t\n\rTZ]+/g) || [];
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
            if (parts.length == format.parts.length) {
                for (var i = 0, cnt = format.parts.length; i < cnt; i++) {
                    val = parseInt(parts[i], 10);
                    part = format.parts[i];
                    format_fun_map[format.parts[i]](val);
                }
            }
            return new Date(dateJSONObj.yyyy, dateJSONObj.M, dateJSONObj.d,
                dateJSONObj.h, dateJSONObj.m, dateJSONObj.s);
        },

        /**
         * 私有方法不建议调用
         * 不满10 补0
         * @param format
         * @returns {{separators: (Array|*), parts: *}}
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
        }
    });
})(jQuery);