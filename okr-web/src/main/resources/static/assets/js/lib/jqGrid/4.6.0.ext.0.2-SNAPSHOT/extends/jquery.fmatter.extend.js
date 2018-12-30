;
(function ($) {
    $.fn.fmatter.sjBoolean = function (cellval, options, rowObject, act) {
        var
            boolDefault = {trueValue: "是", falseValue: "否"},
            formatoptions = options.colModel.formatoptions || {},
            trueValue = formatoptions.trueValue || boolDefault.trueValue,
            falseValue = formatoptions.falseValue || boolDefault.falseValue;

        if (cellval == null || $.trim(String(cellval)) === "") {
            return "&nbsp;";
        }
        return cellval ? trueValue : falseValue;
    };

    $.fn.fmatter.sjBoolean.unformat = function (cellValue, options, cellTd) {
        var
            boolDefault = {trueValue: "是", falseValue: "否"},
            formatoptions = options.colModel !== undefined && options.colModel.formatoptions || {},
            trueValue = formatoptions.trueValue || boolDefault.trueValue,
            falseValue = formatoptions.falseValue || boolDefault.falseValue;

        return cellValue === trueValue ? true :
            cellValue === falseValue ? false :
                cellValue;
    };
    // ============================================================================================

    $.fn.fmatter.sjDate = function (cellval, opts, rwd, act) {
        if (!cellval) {
            return "&nbsp;";
        }
        var fmt = (opts && opts.colModel && opts.colModel.formatoptions) || "yyyy-MM-dd",
            milli = $.isNumeric(cellval) && Number(cellval) ||
                $.type(cellval) === "string" && Date.parse($.fn.fmatter.sjDate._createDate(cellval, fmt)) ||
                $.type(cellval) === "date" && Date.parse(cellval) || null;

        return milli == null ? "&nbsp" : $.fn.fmatter.sjDate._formateDate(fmt, Number(milli));
    };

    $.fn.fmatter.sjDate._formateDate = function (fmt, longDate) {
        var date = new Date(longDate);
        var o = {
            "M+": date.getMonth() + 1, // 月份
            "d+": date.getDate(), // 日
            "H+": date.getHours(), // 小时
            "h+": date.getHours(), // 小时
            "m+": date.getMinutes(), // 分
            "s+": date.getSeconds(), // 秒
            "q+": Math.floor((date.getMonth() + 3) / 3), // 季度
            "S": date.getMilliseconds()// 毫秒
        };

        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };

    /**
     * 将日期字符串转为日期对象
     * @param value
     * @param format
     * @returns {*}
     *
     * test result
     *      parseDate("2015-09-10","yyyy-MM-dd")->Sat Oct 10 2015 00:00:00 GMT+0800 (中国标准时间)
     *      parseDate("2015/12/10","yyyy/MM/dd")->Thu Dec 10 2015 00:00:00 GMT+0800 (中国标准时间)
     *      parseDate("01/12/2015 28:01:12","dd/MM/yyyy HH:mm:ss")->Wed Dec 02 2015 04:01:12 GMT+0800 (中国标准时间)
     */
    $.fn.fmatter.sjDate._createDate = function (value, format) {
        /*    var
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
         }*/
        if (!value) return value;
        if (value instanceof Date) return value;


        var parts, val, part, result, format_fun_map,
            dateJSONObj = {
                yyyy: 0, M: 0, d: 0, h: 0, m: 0, s: 0
            };
        //setters_order = ['yyyy', 'yy', 'MM', 'M', 'dd', 'd', 'hh', 'h', 'HH', 'H', 'mm', 'm', 'ss', 's'],

        format = _parseFormat(format);

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
        //
        //format_fun_map['dd'] = format_fun_map['d'];
        //format_fun_map['ss'] = format_fun_map['s'];
        //format_fun_map['mm'] = format_fun_map['m'];
        //format_fun_map['MM'] = format_fun_map['M'];
        //format_fun_map['HH'] = format_fun_map['H'] = format_fun_map['hh'] = format_fun_map['h'];
        //
        //
        //if (parts.length == format.parts.length) {
        //    for (var i = 0, cnt = format.parts.length; i < cnt; i++) {
        //        val = parseInt(parts[i], 10);
        //        part = format.parts[i];
        //        format_fun_map[format.parts[i]](result, val);
        //    }
        //}

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

        function _parseFormat(format) {
            //yyyy-MM-dd HH:mm:ss
            var format_part_pattern = /hh?|HH?|ss?|dd?|DD?|mm?|MM?|yy(?:yy)?/g,
                // User as the delimiter of format
                separators = format.replace(format_part_pattern, '\0').split('\0'),
                parts = format.match(format_part_pattern);
            if (!separators || !separators.length || !parts || parts.length == 0) {
                throw new Error('Invalid date format.');
            }
            return {separators: separators, parts: parts};
        }

        return new Date(dateJSONObj.yyyy, dateJSONObj.M, dateJSONObj.d,
            dateJSONObj.h, dateJSONObj.m, dateJSONObj.s);
    };

    $.fn.fmatter.sjDate.unformat = function (cellContent, options, cellTd) {
        if (cellContent == null || $.trim(String(cellContent)) === "") {
            return "";
        }
        var fmt = (options && options.colModel && options.colModel.formatoptions) || "yyyy-MM-dd";
        return Date.parse($.fn.fmatter.sjDate._createDate(cellContent, fmt));
    };

    //============================================================================================


    /**
     * e.g. colModel中使用举例如下
     * formatter: "sjOperate", formatoptions: {types: ["minus"]}
     */
    $.fn.fmatter.sjOperate = function (cellContent, options, cellTd) {
        var
            // operateType = ["add", "plus", "minus"],
            formatoptions = options.colModel.formatoptions,
            types = formatoptions && formatoptions.types || [],
            icons;


        //types = _.intersection(operateType, types);
        icons = $.map(types, function (value) {
            return $.jgrid.format("<span class='sj-operate-s sj-icon-{0}'></span>", value);
        });

        return $.jgrid.format("<div>{0}</div>", icons.join(""));
    };

    /**
     * e.g. colModel中使用举例如下
     * formatter: "sjTypeEnum", formatoptions: {typeEnum: {"1": "待处理", "2": "待审核", "3": "已审核"}}
     */
    $.fn.fmatter.sjTypeEnum = function (cellContent, options, cellTd) {
        var
            formatoptions = options.colModel.formatoptions,
            typeEnum = formatoptions && formatoptions.typeEnum || {};

        return typeEnum[cellContent] || "";
    };

    /**
     *
     * @param cellval
     * @param opts
     *                      例如:         {name:'price',
                                                 formatter:"currencyExt",
                                                 formatoptions:{
                                                   decimalSeparator:".",//小数点
                                                   thousandsSeparator:",",//千分位
                                                   decimalPlaces:2,//小数精度
                                                   prefix:"",//前缀符号，比如：货币符号￥，$；
                                                   suffix:"",//后缀符号，比如：百分比符号%；
                                                   defaultValue:'0.00',//默认值
                                                   isZeroSetEmpty:false //格式化后的值为0,并且  isZeroSetEmpty:true 将不显示.默认空为false
                                                 }
                                                }
     * @returns {*}
     */
    $.fn.fmatter.currencyExt = function (cellval, opts) {
        //继承 货币 全局配置
        var _grid = this, op = $.extend({}, opts.currency);
        if (opts.colModel !== undefined && opts.colModel.formatoptions !== undefined) {
            op = $.extend({}, op, opts.colModel.formatoptions);
        }
        if ($.fmatter.isEmpty(cellval)) {
            return op.defaultValue;
        }
        //判断是否 数值
        if (!isNaN(Number(cellval))) {
            var newCellVal = $.fmatter.util.NumberFormat(cellval, op);
            //首次构造表格时，单元格还未生成。
            //必须延迟
            var timeObj = window.setTimeout(function () {
                var $cellElm = $('td[aria-describedby="' + _grid.p.id + '_' + opts.colModel.name + '"]', '#' + opts.rowId);
                //负数样式
                if (cellval < 0) {
                    $cellElm.addClass("negative-cell");
                } else {
                    $cellElm.removeClass("negative-cell");
                }
                //
                window.clearTimeout(timeObj);
            }, 1);
            //零值时 至为空
            if (op.isZeroSetEmpty === true) {
                if (newCellVal == 0) {
                    return "";
                }
            }
            return newCellVal;
        } else {
            return "";
        }
    };
    $.fn.fmatter.currencyExt.unformat = function (cellval, options) {
        // specific for jqGrid only
        var ret,
            op = options.colModel.formatoptions || {}, sep,
            re = /([\.\*\_\'\(\)\{\}\+\?\\])/g,
            opts = $.jgrid.formatter || {}, stripTag;
        op = $.extend({}, opts.currency, op);
        sep = op.thousandsSeparator.replace(re, "\\$1");
        stripTag = new RegExp(sep, "g");
        ret = cellval;
        if (op.prefix && op.prefix.length) {
            ret = ret.substr(op.prefix.length);
        }
        if (op.suffix && op.suffix.length) {
            ret = ret.substr(0, ret.length - op.suffix.length);
        }
        ret = ret.replace(stripTag, '').replace(op.decimalSeparator, '.');
        if (ret !== undefined) {
            if (!isNaN(Number(ret))) {
                return ret;
            } else {
                return "";
            }
        } else {
            return $.jgrid.htmlDecode($(cellval).html());
        }
    };

    ///**
    // * 百分比 反格式化（暂时不用）
    // * @param cellval
    // * @param opts
    // * @returns {*}
    // */
    //$.fn.fmatter.percent = function (cellval, opts) {
    //    //判断是否 数值
    //    if (cellval) {
    //        if (!isNaN(Number(cellval))) {
    //            var newCellval = cellval * 100 + "%";
    //            return newCellval;
    //        } else {
    //            return "";
    //        }
    //    } else {
    //        return "";
    //    }
    //};
    ///**
    // * 百分比 反格式化（暂时不用）
    // * @param cellval
    // * @param opts
    // * @returns {*}
    // */
    //$.fn.fmatter.percent.unformat = function (cellval, options, cellTd) {
    //    var ret = cellval;
    //    //
    //    if (ret) {
    //        //去除 %
    //        ret = ret.replace('%', '');
    //        //判断是否是 数值
    //        if (!isNaN(Number(ret))) {
    //            return ret / 100;
    //        } else {
    //            return ret;
    //        }
    //    } else {
    //        return $.jgrid.htmlDecode($(cellval).html());
    //    }
    //};

    /**
     * 弃用
     *
     *   formatter: 'customStyleCol',
     *   actionType: 'custom',
     actionStyle: {
            types: ['edit', 'minus'],
            actionFunctions: {
                edit: 'pageObj.editById',
                minus: 'pageObj.deleteForListPageGrid'
            }
        }
     *
     * @param cellval 单元格值
     * @param opts 单元格配置
     * @param rowData 行数据
     * @returns {*}
     */
    $.fn.fmatter.customStyleCol = function (cellval, opts, rowData) {
        //从列配置中获取需要用到的属性配置，保存在自定义变量中
        var grid = $("#" + opts.gid)[0];
        opts.idName = grid.p.jsonReader.id;
        var op = {
            actionType: opts.colModel.actionType,
            actionStyle: opts.colModel.actionStyle,
            idName: opts.idName,
            actionFunction: opts.colModel.actionFunction,
            name: opts.colModel.name
        };
        //用户自定义动作列
        if (op.actionType == "custom" && opts.rowId) {
            var actionHtml = "";
            //通过样式触发动作
            if ((op.actionStyle == null || op.actionStyle == "")) {
                return;
            }
            if (op.actionStyle.types == null || op.actionStyle.types == "") {
                return;
            }
            actionHtml = $.map(op.actionStyle.types, function (value) {
                var actionFunctionName = "", clickStr = "";
                if (op.actionStyle.actionFunctions != null && op.actionStyle.actionFunctions[value] != null) {
                    actionFunctionName = op.actionStyle.actionFunctions[value];
                }
                clickStr += "$('#" + opts.gid + "').jqGrid('setSelection','" + opts.rowId + "',true);";
                clickStr += actionFunctionName + "(\'" + opts.rowId + "\');";
                return $.jgrid.format("<span class='sj-operate-s sj-icon-{0}' onclick=" + clickStr + "></span>", value);
            });
            actionHtml = $.jgrid.format("<div>{0}</div>", actionHtml.join(""));
            return "<a href='javascript:void(0);'>" + actionHtml + "</a>";
        } else {
            return cellval;
        }
    };


    /**
     *
     *默认提供 add,delete,edit
     * customAction: {
            add: {
                selection: false,                               //TODO:暂不支持 点击时 是否选中行
                functionName: "pageObj.addFunc" ,               //点击后 执行的方法
                hidden: true,                                   // 1.可以是一个返回布boolean的函数 2.可以是boolean值
                title: "新增"
            },
            del: {
                onRendering: function (rowId, rowData) {        //自定义渲染，其它配置就无效
                    return "";
                }
            },
            edit: {
                onRendering: function (rowId, rowData) {
                    return "";
                }
            }
        },
     formatter: 'customAction'
     *
     * 自定义动作列
     * @param cellval 单元格值
     * @param opts 单元格配置
     * @param rowData 行数据
     * @returns {*}
     */
    $.fn.fmatter.customAction = function (cellval, opts, rowData) {
        if (!$.isEmptyObject(opts.colModel.customAction) && opts.rowId) {
            var $grid = $("#" + opts.gid),
                actionKey,
                action,
                actionTitle,
                actionHtml = "",
                clickStr,
                hidden,
                visibleStyle;

            //新增列,编辑列,删除列
            for (actionKey in opts.colModel.customAction) {
                action = opts.colModel.customAction[actionKey];
                clickStr = "";
                actionTitle = "";

                if (action) {
                    hidden = $.isFunction(action.hidden) ? action.hidden.call($grid, opts.rowId, rowData) : action.hidden;
                    visibleStyle = hidden ? "display: none;" : "";

                    //自定义渲染
                    if ($.isFunction(action.onRendering)) {
                        actionHtml += "<span ";
                        if (hidden) {
                            actionHtml += " style=\"" + visibleStyle + "\" "
                        }
                        actionHtml += "id=\"" + actionKey + opts.rowId + "\">" + action.onRendering.call($grid, opts.rowId, rowData) + "</span>";
                    } else {
                        actionTitle = action.title || "";

                        if (actionKey === "add" && !actionTitle) {
                            actionTitle = $.jgrid.btn.add;
                        }
                        if (actionKey === "edit" && !actionTitle) {
                            actionTitle = $.jgrid.btn.edit;
                        }
                        if (actionKey === "del" && !actionTitle) {
                            actionTitle = $.jgrid.btn.del;
                        }
                        if (actionKey && !actionTitle) {
                            throw new Error(' default customAction:{add:{...},del:{...},edit:{...}} ');
                        }
                        //1.拼接onclick
                        //是否要选中行
                        //if (action.selection !== false) {
                        //    clickStr += "$('#" + opts.gid + "').jqGrid('setSelection','" + opts.rowId + "',true);";
                        //}
                        if (action["functionName"]) {
                            clickStr += action["functionName"] + "(\'" + opts.rowId + "\');"
                        }


                        //2.取样式
                        actionHtml += $.jgrid.format("<span class='sj-operate-s sj-icon-{0}' title='{1}' style='{2}' onclick=" + clickStr + "></span>", actionKey, actionTitle, visibleStyle);
                    }
                }
            }
            return actionHtml
        } else {
            return cellval ? cellval : "";
        }
    };


    /**
     * 遍历的formatter
     * @param cellval
     * @param opts
     * @param rowObject
     * @returns {string},
     * e.g.
     *  { label: "摘要", name: "summary",
     *      formatter: "forEach",
     *      formatoptions: {
     *          lastClass: "entry-last",
     *          classes: "entry",
     *          eachField: "voucherEntry",
     *          executor: function (cellVal, rowObject, rowId) {
     *              return cellVal["summary"] ? cellVal["summary"] : "&nbsp;";
     *      }},
     *  classes: "nowrap-td",
     *  width: 150},//摘要
     */
    $.fn.fmatter.forEach = function (cellval, opts, rowObject) {
        var op = {
                idName: opts.idName,
                style: opts.style || "",
                classes: opts.classes || "grid-entry",
                firstClass: opts.firstClass || "grid-entry-first",
                lastClass: opts.lastClass || "grid-entry-last",
                eachField: opts.eachField,
                executor: opts.executor || function (cellVal, rowObject, rowId) {
                    return cellVal || "&nbsp;";
                },
                title: opts.executor || true
            },
            listEle,
            eleHtml = "";

        if (opts.colModel !== undefined && opts.colModel.formatoptions !== undefined) {
            op = $.extend({}, op, opts.colModel.formatoptions);
        }

        listEle = rowObject[op.eachField];
        if (listEle) {
            $.each(listEle, function (index, eleCell) {
                var content = op.executor.call(this, eleCell, rowObject, opts.rowId, index, listEle.length);

                eleHtml += $.jgrid.format(
                    "<div style='{0}' class='{1} {2}' title='{3}' data-entry-index='{4}'>{5}</div>",
                    op.style,
                    op.classes,
                    (index == 0 && index == listEle.length - 1
                        ? op.firstClass + " " + op.lastClass
                        : index == 0
                        ? op.firstClass
                        : index == listEle.length - 1
                        ? op.lastClass
                        : ""),
                    op.title ? content : "",
                    index,
                    content
                );
            });
            return eleHtml;
        }

        return $.fn.fmatter.defaultFormat(cellval, opts);
    };
})(jQuery);