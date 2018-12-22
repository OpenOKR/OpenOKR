/*
 * 商集唯你 研发中心前端架构组 自主研发。
 * 2015-11-18 之前未做版本管理，都默认为1.0.0。
 * 2015-11-18 开始为1.1.0。
 * 版本说明请阅读readme.txt
 */
;
/**
 * 依赖 OUI,Underscore
 */
(function ($, undefined) {
    "use strict";

    /** core ******************************************************************** */
    $.autoCombo = $.autoCombo || {
        defaultConfig: {}
    };

    // 基本配置
    var
        /** 默认列配置 */
        _defaultColModel = {
            isQueryField: false,
            isHide: false,
            width: 60,
            isInputFilter: true
        },
        /** 默认控件配置 */
        _defaultConfig = {
            event: {
                isIgnoreRelatedTarget: false,
                /** 控件下拉显示事件 (事件触发要 避免 click,focus 的并发情况)*/
                actionEvents: "click.auto-combobox mousedown.auto-combobox focus.auto-combobox",
                /** TODO:(弃用,内部分开到 onkeydown 和 onkeyup ) 控制输入事件*/
                // inputEvents: "keyup.auto-combobox",
                /** 控件下拉隐藏事件（事件是绑定在window上） */
                lossEventsForWindow: "click.auto-combobox mousedown.auto-combobox scroll.auto-combobox",
                //lossEventsForWindow: "",//TODO:调试使用
                /** 控件下拉隐藏事件（事件是绑定在控件上） */
                lossEventsForSelf: "blur.auto-combobox"
                //lossEventsForSelf: ""//TODO:调试使用
            },
            view: {
                /** 单列不显示表头 */
                singleColumnNotHead: false,
                /** 强制隐藏表头，优先级最高（即使是多列，并且 singleColumnNotHead=true，也会不显示） */
                forceHideHead: false,
                /** 是否显示分页栏 */
                showPager: true,
                /** 分页栏位置 top:上方,bottom:下方*/
                pagerPosition: "bottom",
                /** 每页显示记录数 */
                pageSize: 10,
                /**
                 * （下拉）高度
                 */
                height: 0,
                /**
                 * （下拉）宽度
                 * //优先级：1、width；2、autoWidthPadding（dropDownContainer不等于null）；3、widthRefer；
                 * 1.默认为0时，将依照 列指定的宽度撑开；
                 * 2.大于0时，将忽略每列的宽度（列宽 将平均分配）；
                 */
                width: 0,
                /**
                 * 最小宽度（dataSourceType="remote",showPager= true, 时生效）
                 */
                minWidth: 220,
                /**(弃用) 左右、上下、偏移 {left: 0, top: 0} */
                //offset: {left: 0, top: 0},
                /** 是否默认选中第一行 */
                isSelectedFirstRow: true,
                /** isSelectedFirstRow=true, 是否默认选中第一行后，并触发_onSelectRow */
                isSelectedFirstRowAction: false,
                /** 是否记忆值
                 * （控件不负责赋值到 输入框，数据不确定是否来自于dataGrid，通过没有执行 _onSelectRow来判定）
                 *  _onLose的将回复到_onAction记忆的值
                 */
                isRememberValue: true,
                /**
                 *是否允许为空。（true/false）
                 */
                isAllowEmpty: true,
                /**
                 * 输入时会按照输入的数据过滤
                 * 但离开控件后，再触发了(_onAction)控件，就忽略控件上已有 值 进行过滤
                 */
                isOnActionIgnoreFilter: true,
                /**
                 * 是否允许在 控件 只读时也能触发动作
                 */
                isAllowReadonlyAction: true,
                /**
                 * 绑定填充，选择时会自动填充，删除为空值时会自动清除。
                 * {"#textBillStatusName":"name","#hiddenBillStatusValue":"code"}
                 * {"jquery selector":"data grid选中行的字段名"}
                 */
                bindFill: {},
                /**
                 * 展现的列模型 格式[{
                 * name:字段名，为Entity的字段名；
                 * label:不配置和name一致；
                 * isQueryField:默认false；
                 * isHide:默认false；
                 * width:列宽默认60；
                 * align:left居左，center居中，right居右；
                 * isInputFilter:默认false，输入值如有包含，会被过滤；如果dataSourceType=remote，isInputFilter=true的字段将被标为高亮。
                 * formatter:{
                 *          name: "currency",             //货币/数值/金额 格式器
                 *          decimalPlaces: 2,           //小数位(可空)
                 *          thousandsSeparator      //千分位分隔符(可空)
                 *          decimalSeparator          //小数符默认"."(可空)
                 *          prefix                             //前缀(可空)
                 *          suffix                             //后缀(可空)
                 *      }
                 *      或
                 *      {
                 *          name: "date",   //日期格式器
                 *          dateFormat:"" //日期格式
                 *      }
                 *      或
                 *      {
                 *          name: "checkbox",   //复选框
                 *      }
                 * },......]
                 */
                colModels: [],
                /**
                 * dataGridHtmlTemplate: "<table class=\"auto-comboBox-dataPanel-table\"><thead>
                 * <tr>
                 * <td>code</td>
                 * <td>名称</td>
                 * <td>备注</td>
                 * </tr>
                 * </thead><tbody><%_.each(records, function(r){%>
                 * <tr>
                 * <td><%=r.code%></td>
                 * <td><%=r.name%></td>
                 * <td><%=r.remark%></td>
                 * </tr>
                 * <%});%></tbody></table>",
                 */
                dataGridHtmlTemplate: null,
                /** html 模板占位符正则表达式 */
                templateSettings: {
                    interpolate: /##(.+?)##/g
                },
                /**
                 * 分页栏模板
                 */
                pagerBarHtmlTemplate: '<div class="auto-comboBox-pager" id="<%=pagerId%>">'
                    + '<table><tbody><tr>'
                    + '<td class="auto-comboBox-pager-btn" id="<%=firstPageBtnId%>"><span class="first"></span></td>'
                    + '<td class="auto-comboBox-pager-btn" id="<%=prevPageBtnId%>"><span class="prev"></span></td>'
                    + '<td style="padding: 0;">'
                    + '<input type="text" style="width: 20px;height: 16px" readonly="readonly" value="<%=currentPage%>" />'
                    + '</td>'
                    + '<td class="auto-comboBox-pager-btn" id="<%=nextPageBtnId%>"><span class="next"></span></td>'
                    + '<td class="auto-comboBox-pager-btn" id="<%=lastPageBtnId%>"><span class="end"></span></td>'
                    + '<td>###pageInfoFormat###</td></tr></tbody></table></div>',
                /**
                 * 本地数据
                 * async.dataType:"local" 才有效
                 */
                localData: [],
                /**
                 * 下拉层的 层重叠顺序
                 */
                zIndex: 9999,
                /**
                 * 下拉位置 参照物
                 *  例："#id"、$("#id").child("div")、$("#id").parent("div")
                 *     或者是 function(){
                 *           //this 是输入框
                 *           return $(this).parent("div");
                 *          }
                 * 默认当前输入框
                 */
                positionRefer: null,

                /**
                 * 将下拉固定在页面的指定区域
                 */
                dropDownContainer: null,
                /**
                 * 是否自动触发(dropDownContainer 不为空时生效)
                 */
                autoLoad: true,
                /**
                 * TODO:暂不支持,自动宽度的间隔宽度
                 * 必须 dropDownContainer不为空 并且 width = 0,才生效
                 * //优先级：1、width；2、autoWidthPadding（dropDownContainer不等于null）；3、widthRefer；
                 */
                //autoWidthPadding: -1,
                /**
                 * 自动高度的间隔高度
                 * 必须 dropDownContainer不为空 并且 height = 0,才生效
                 */
                autoHeightPadding: -1,
                /**
                 * 宽度参照（类似positionRefer）
                 * //优先级：1、width；2、autoWidthPadding（dropDownContainer不等于null）；3、widthRefer；
                 */
                widthRefer: null,
                /**
                 * 给下拉指定范围
                 */
                dropDownWithin: "body",
                /**
                 *是否高亮
                 */
                isHighlight: true,
                /**
                 * 是否转码数据
                 */
                isEncode: false,
                /**
                 * 显示清除按钮
                 */
                showClearButton: false
            },
            callback: {
                // beforeAction(event) 控件弹出下拉前动作，必须返回true或false。
                beforeAction: null,
                //下拉打开后 afterAction(event)
                afterAction: null,//下拉框显示后
                //下拉消失前 beforeLose(event)
                beforeLose: null,//下拉框消失前
                //下拉消失后 afterLose(event)
                afterLose: null,//下拉框消失后
                //请求发起前,返回boolean,false拒绝后续,true继续后续. beforeAjaxBeforeSend(xhr, settings)
                beforeAjaxBeforeSend: null,//dataSourceType=remote,发起远程请求前
                //数据加载成功,但还未装载到控件 beforeAjaxSuccess(responseData, st, xhr)
                beforeAjaxSuccess: null,//dataSourceType=remote,异步请求成功前
                //数据加载失败 afterAjaxError(xhr, st, err)
                afterAjaxError: null,//dataSourceType=remote,异步请求错误
                //选中行前 beforeSelectRow(rowData)
                beforeSelectRow: null,
                //选中行后 afterSelectRow(rowData)
                afterSelectRow: null,

                //TODO:(弃用,内部分开到 keydown),返回boolean,false拒绝后续,true继续后续. beforeInput(event)
                beforeInput: null,//输入前事件
                //TODO:(弃用,内部分开到 keydown) afterInput(event)
                afterInput: null,//输入后事件

                //按键按下前,返回boolean,false拒绝后续,true继续后续. beforeKeyDown(event)
                beforeKeyDown: null,//按键按下前事件
                //按键按下后 afterKeyDown(event)
                afterKeyDown: null,//按键按下后事件

                //比beforeAjaxBeforeSend更前,设置请求的数据. setRequestData(requestData)
                setRequestData: null,//dataSourceType=remote,发起远程请求前,对发送数据设置

                //数据装载完成.afterLoad(dataStore,$dataPanel)
                afterLoad: null,//下拉框数据都装载完后

                //点击清除按钮后
                afterClickClear: null
            },
            async: {
                url: "",
                type: "POST",
                dataType: "json",
                /** 数据来源方式：remote = 远程、local = 本页面、onceRemote = 一次远程 */
                dataSourceType: "remote",
                async: true,
                requestData: {},
                /**
                 * 分页字段定义（分页字段命名可以被自定义）
                 */
                pageFieldDefine: {
                    // 每页显示记录数
                    pageSize: "pageSize",
                    // 查询结果总记录数
                    totalRecord: "totalRecord",
                    // 当前页面
                    currentPage: "currentPage",
                    // 总共页数
                    totalPage: "totalPage",
                    // 记录集
                    records: "records"
                }
            },
            input: {
                /** 延迟触发输入后的响应（毫秒。用户连续输入不会反复触发，停止指定的毫米数后不输入，才触发后续） */
                queryDelay: 200,
                /** 启用 输入查询（在控件上输入的值，将按延迟[queryDelay]时间向后台提交） */
                enableInputAction: true,
                /** 启用 输入查询的参数名称，默认值如下（只有enableInputAction=true才生效） */
                inputQueryParamName: "inputValue",
                /**
                 * 控件默认情况下是开启了<  autocomplete="on">
                 *     这里默认关闭控件的 autocomplete="off" 自动完成功能
                 */
                enableAutoComplete: false
            }
        };

    // 其它属性
    var _otherProperty = {
        /** 公开属性 */
        //下拉是否显示
        isDropDownShow: false,
        /**
         * 当前选中行Id
         */
        currentSelectedRowId: null,
        /** 私有属性 */
        //下拉面板
        _dropDown: null,
        // 数据面板
        _dataPanel: null,
        // 避免重复绑定
        _isBindEventDataPanel: false,
        // 原始数据存储
        _originalDataStore: [],
        // 会被过滤字段
        _inputFilterFieldNames: [],
        // 显示的字段
        _showFieldNames: [],
        // 需要格式化的字段映射
        //{field1:{name: "date"},field2:{name: "currency"}}
        _formatterMap: {},
        //总列宽
        _totalColWidth: 0,
        // 下拉、数据面板、是否已经装载完(重复点击不会重复提交、只呈现下拉)
        _isLoaded: false,
        //
        _actionStop: true,
        //焦点行下标
        _focusRowIndex: -1,
        /**
         * （下拉）最大高度
         *  如果 height = 0, 不能让 实际数据高度 无限撑高.
         *  TODO:  height = 0 并且 实际数据高度很大,已经撑入到页面最底部 被遮挡了,需要动态计算.
         */
        _maxHeight: 200,
        /**
         * （下拉）最小高度
         *  如果 height = 0, 不能让 实际数据高度 无限缩小.
         *  TODO:  height = 0 并且高度是动态的,不能无限缩小
         */
        _minHeight: 160,
        //判断当前数据是否来至于数据网格
        _isCurrentValueFromDataGrid: false,
        //记忆的值
        _rememberValue: "",
        //用于控制 仅重载数据，不显示
        _justReload: false,
        //判断鼠标是否移出下拉框
        _isMouseOutDropDown: true,
        //判断鼠标是否移入清除按钮
        _isMouseOverClearBtn: true,
        //判断是否 接口设置值
        _isSettedLocalData: false
    };

    $.fn.AutoCombobox = function (config) {
        if (typeof config === 'string') {
            var fn = $.autoCombo.getAccessor($.autoCombo, config);
            if (!fn) {
                alert("AutoCombobox method not found:" + config);
                return;
            } else {
                //前缀是下划线的方法不可调用
                if (config.indexOf("_") === 0) {
                    alert("AutoCombobox don't call the private method:" + config);
                    return;
                }
            }
            var args = $.makeArray(arguments).slice(1);
            return fn.apply(this, args);
        }
        return this.each(function () {
            /**
             * 防止重复渲染
             */
            if (this.renderCompleted) {
                return;
            }
            var _thisElm = this, p;
            //
            _thisElm.componentType = "AutoCombobox";
            /**
             * 判定非input:text 控件的渲染
             */
            if (!$(_thisElm).is("input:text, textarea")) {
                alert("#" + _thisElm.id + "[name='" + _thisElm.name + "']" + "Non(input:text)controls can't initialize!");
                return;
            }
            /**
             * 配置继承
             */
            _thisElm.p = $.extend(true, {}, {
                id: _thisElm.id ? _thisElm.id : "_" + parseInt((Math.random() * 100000000))//ID 不配置采用随机数ID
            }, _otherProperty, $.autoCombo);
            //
            p = _thisElm.p;
            //
            _thisElm.id = p.id;
            //
            p.event = $.extend(true, {}, _defaultConfig.event, $.autoCombo.defaultConfig.event, config.event);
            p.view = $.extend(true, {}, _defaultConfig.view, $.autoCombo.defaultConfig.view, config.view);
            p.callback = $.extend(true, {}, _defaultConfig.callback, $.autoCombo.defaultConfig.callback, config.callback);
            p.async = $.extend(true, {}, _defaultConfig.async, $.autoCombo.defaultConfig.async, config.async);
            p.input = $.extend(true, {}, _defaultConfig.input, $.autoCombo.defaultConfig.input, config.input);
            //检查
            if ($.isFunction(p.callback.beforeInput) && App && App.hasOwnProperty("src") && App.src === true) {
                _printWarnLog("p.callback.beforeInput 后续版本将不支持，推荐使用 p.callback.beforeKeyDown")
            }
            if ($.isFunction(p.callback.afterInput) && App && App.hasOwnProperty("src") && App.src === true) {
                _printWarnLog("p.callback.afterInput 后续版本将不支持，推荐使用 p.callback.afterKeyDown")
            }
            var $container = $(p.view.dropDownContainer);
            if ($container && $container.length > 0 && $container[0].id === (p.id + 'DropDown') && App && App.hasOwnProperty("src") && App.src === true) {
                _printWarnLog('配置项p.view.dropDownContainer=' + p.view.dropDownContainer + '，对应的元素id不能命名为（' + p.id + 'DropDown）建议改为（' + p.id + 'Container）');
            }
            //特殊国际化处理
            p.view.pagerBarHtmlTemplate = p.view.pagerBarHtmlTemplate.replace("###pageInfoFormat###", $.autoCombo.i18n.pageInfoFormat);
            // 请求参数初始化
            p.async.requestData[p.async.pageFieldDefine.pageSize] = p.view.pageSize;
            p.async.requestData[p.async.pageFieldDefine.totalRecord] = -1;
            p.async.requestData[p.async.pageFieldDefine.currentPage] = 1;
            //是否关闭控件的 自动完成 功能
            if (p.input.enableAutoComplete) {
                _thisElm.autocomplete = "on";
            } else {
                _thisElm.autocomplete = "off";
            }
            // 初始化
            _initial.call(_thisElm);

            // 渲染完成
            _thisElm.renderCompleted = true;
        });
    };

    /** core ******************************************************************** */

    /** interface ******************************************************************** */
    $.extend($.autoCombo, {

        /**
         * 对象属性访问器 如： obj = {"a":1,"b":2} expr = "a" 即返回 1
         */
        getAccessor: function (obj, expr) {
            var ret, p, prm = [], i;
            if (typeof expr === 'function') {
                return expr(obj);
            }
            ret = obj[expr];
            if (ret === undefined) {
                try {
                    if (typeof expr === 'string') {
                        prm = expr.split('.');
                    }
                    i = prm.length;
                    if (i) {
                        ret = obj;
                        while (ret && i--) {
                            p = prm.shift();
                            ret = ret[p];
                        }
                    }
                } catch (e) {
                }
            }
            return ret;
        },

        /**
         * 触发控件的Action
         * @param event
         */
        triggerAction: function (event) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p, $this = $(_thisElm);
                //timeObj;
                //timeObj = window.setTimeout(function () {
                if ($this.prop("disabled") === false) {
                    //非只读 和 只读时也允许触发
                    if ($this.prop("readonly") === false || p.view.isAllowReadonlyAction) {
                        //window.setTimeout(function () {
                        if (!p._isLoaded) {
                            _onAction.call(_thisElm, event);
                        } else {
                            p.showDropDown.call(_thisElm, true);
                            window.event.cancelBubble = true;
                            window.event.stopPropagation();
                        }
                        //$this.focus();
                        //}, 10);
                    }
                }
                //window.clearTimeout(timeObj);
                //}, 100);
            });
        },

        /**
         * 显示
         */
        showDropDown: function (_isDropDownShow) {
            $(this).each(function () {
                var _thisElm = this, p = _thisElm.p, $this = $(_thisElm),
                    $c = p._dropDown,
                    $positionRefer;
                //position = {};
                p.isDropDownShow = _isDropDownShow;
                if ($c === null)
                    return;

                if (_isDropDownShow) {
                    //先立即显示,再进行后续调整
                    $c.show();
                    //下拉直接显示在指定区域
                    if (p.view.dropDownContainer) {
                        //重新计算下拉尺寸
                        _resizeDropDown.call(_thisElm);
                    } else {
                        //如果当前控件隐藏 或 被销毁
                        if ($this.length === 0 || $this.is(":hidden")) {
                            return;
                        }
                        $c.css({
                            left: '-9999px',
                            top: '-9999px',
                            position: 'absolute',
                            //'z-index': -9999
                            'z-index': p.view.zIndex
                        });
                        var _afterShow = function () {
                            //重新计算下拉尺寸
                            _resizeDropDown.call(_thisElm);
                            //
                            var _showTimeObj = window.setTimeout(function () {
                                if ($c.offset().left < 0 && $c.offset().top < 0) {
                                    //根据位置引用,定位下拉框显示位置
                                    $positionRefer = _getPositionRefer.call(_thisElm);
                                    //检查控件是否隐藏 或者被销毁
                                    var _checkShowTimeObj = window.setTimeout(function () {
                                        //如果控件 被销毁了,下拉被隐藏
                                        if ($this.length === 0 || $this.is(":hidden")) {
                                            $c.hide();
                                            p.isDropDownShow = false;
                                        } else {
                                            $c.positioner({
                                                my: "left top",
                                                at: "left bottom",
                                                of: $positionRefer,
                                                collision: "flipfit flipfit"
                                            });
                                            p.isDropDownShow = true;
                                            // 下拉打开后, 设置选中效果
                                            _showCurrentSelectedRow.call(_thisElm);
                                            if (p.callback.afterAction) {
                                                p.callback.afterAction.call(_thisElm, event);
                                            }
                                        }
                                        //回收时间对象
                                        window.clearTimeout(_checkShowTimeObj);
                                    }, 1);
                                } else {
                                    p.isDropDownShow = false;
                                }
                                //回收时间对象
                                window.clearTimeout(_showTimeObj);
                            }, 1);
                        };
                        _afterShow();
                    }
                } else {
                    //是否记忆数据 并且 不允许为空
                    if (p.view.isRememberValue === true && (p.view.isAllowEmpty === false || $.trim($this.val()).length !== 0)) {
                        //判断输入框值 是否 来自于dataGrid
                        //（控件不负责赋值到 输入框，数据不确定是否来自于dataGrid，通过没有执行 _onSelectRow来判定）
                        if (p._isCurrentValueFromDataGrid === false) {
                            //回复到记忆的值
                            $this.val(p._rememberValue);
                            //
                            p._justReload = true;
                            if (!p.view.isOnActionIgnoreFilter) {
                                //重载数据，否则再次打开没有数据
                                _reloadByValue.call(_thisElm, $this.val());
                            } else {
                                _reloadByValue.call(_thisElm, "");
                            }
                        }
                    }
                    //下拉直接显示在指定区域
                    if (p.view.dropDownContainer) {
                        //重载到全部的数据
                        //_reloadByValue.call(_thisElm, "");
                        //重新显示 一次，将重新计算宽度、高度
                        //p.showDropDown.call(_thisElm, true);
                    } else {
                        //先立即隐藏,再进行后续调整
                        $c.hide();
                        window.setTimeout(function () {
                            if ($c.offset().left >= 0 && $c.offset().top >= 0) {
                                //$c.hide(function(){
                                //    $(this).css({
                                //        left: '-9999px',
                                //        top: '-9999px',
                                //        position: 'absolute',
                                //        'z-index': -9999
                                //    });
                                //});
                                $c.css({
                                    left: '-9999px',
                                    top: '-9999px',
                                    position: 'absolute',
                                    'z-index': -9999
                                });
                                p.isDropDownShow = false;
                            } else {
                                p.isDropDownShow = true;
                            }
                        }, 1);
                    }
                }
            });
        },

        /**
         * 隐藏控件下拉框
         */
        hideDropDown: function () {
            var _thisElm = this, p = _thisElm.p;
            p._actionStop = true;
            p.showDropDown.call(_thisElm, false);
        },

        /**
         * 显示等待条
         */
        showLoadingBar: function () {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm),
                _thisElmTop = $this.offset().top,
                _thisElmLeft = $this.offset().left;
            // 遮挡数据面板
            if (p._loadingBar.is(":hidden")) {
                p._loadingBar.css({top: -9999, left: -9999}).show(function () {
                    $(this).css({
                        top: _thisElmTop + 'px',
                        left: _thisElmLeft + 'px',
                        display: "block",
                        'z-index': p.view.zIndex
                    });
                });
            }
        },
        /**
         * 隐藏等待条
         */
        hideLoadingBar: function () {
            if (!this.p._loadingBar.is(":hidden")) {
                this.p._loadingBar.hide(function () {
                    $(this).css({
                        display: "none",
                        'z-index': -9999
                    });
                });
            }
        },

        /**
         * 销毁控件
         */
        destroy: function () {
            return $(this).each(function () {
                var _thisElm = this, $this = $(_thisElm), windowEvent, p = _thisElm.p;

                if (_thisElm.p) {
                    // 1.取消绑定的事件
                    $this.off(p.event.actionEvents + p.id);
                    $this.off("keydown." + p.id);
                    windowEvent = _splitEventString.call(_thisElm, p.event.lossEventsForWindow, p.id);
                    $(window.document).off(windowEvent);
                    $this.off(p.event.lossEventsForSelf + p.id);
                    p._dataPanel.off('mousedown.' + p.id);
                    p._dataPanel.off('keyup.' + p.id);
                    // 2.
                    _thisElm.renderCompleted = false;
                    _thisElm.p = null;
                }
            });
        },

        /**
         * 重新装载
         * @returns {*}
         */
        reload: function () {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p;
                //重置当前选择行
                p.currentSelectedRowId = null;
                _onLoad.call(this);
            });
        },

        /**
         * 设置本地数据
         */
        setLocalData: function (data) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p;
                if ($.isArray(data)) {
                    // 原始数据存储(私有)
                    p._originalDataStore = $.extend(true, [], data);
                    p.view.localData = p._originalDataStore;
                    p._isSettedLocalData = true;
                    p._focusRowIndex = -1;
                    p._isLoaded = false;
                    // 给每行数据设置rowId
                    _setRowId.call(_thisElm, p._originalDataStore);
                    //
                    if (p.view.dropDownContainer) {
                        //
                        _onLocal.call(_thisElm);
                    }
                }
            });
        },

        /**
         * 清空本地数据
         */
        clearLocalData: function () {
            return $(this).each(function () {
                this.p._originalDataStore = this.p.view.localData = [];
            });
        },

        /**
         * 获取本地数据
         */
        getLocalData: function () {
            return this[0].p._originalDataStore;
        },

        /**
         * 获取当前选中的行数据
         * @returns {{}}
         */
        getCurrentSelectedRowData: function () {
            var rowData = {};
            $(this).each(function () {
                var _thisElm = this, p = _thisElm.p;
                rowData = _getRowData.call(_thisElm, p.currentSelectedRowId);
            });
            return rowData;
        },

        clearValue: function (event) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p, $this = $(_thisElm),
                    bindFill = p.view.bindFill,
                    selector;
                if (p.view.showClearButton) {
                    if ($.trim($this.val()).length > 0) {
                        if (!$.isEmptyObject(bindFill)) {
                            for (selector in bindFill) {
                                if (bindFill[selector]) {
                                    $(selector).val("");
                                }
                            }
                        } else {
                            $this.val('');
                        }
                        if ($.isFunction(p.callback.afterClickClear)) {
                            p.callback.afterClickClear.call(_thisElm, event);
                        }
                    }
                    _hideClearButton.call(_thisElm, event);
                }
            });
        }

    });

    /** interface ******************************************************************** */

    var

        _dataSourceType = {
            l: "local",
            o: "onceRemote",
            r: "remote"
        },

        /**
         * 控件初始化
         */
        _initial = function () {
            $(this).each(function () {
                var _thisElm = this, $this = $(_thisElm), p = _thisElm.p,
                    windowEvent;
                //
                windowEvent = _splitEventString.call(_thisElm, p.event.lossEventsForWindow, p.id);
                //
                $this.off(p.event.actionEvents + p.id);
                $this.on(p.event.actionEvents + p.id, function (event) {
                    _onAction.call(_thisElm, event);
                    //
                    _showClearButton.call(_thisElm);
                });
                //
                $this.off("keydown.auto-combobox" + p.id);
                $this.on("keydown.auto-combobox" + p.id, _onKeyDown);
                //
                $(window.document).off(windowEvent);
                $(window.document).on(windowEvent, function () {
                    // 关闭下拉框的事件
                    _onLose.apply(_thisElm, arguments);

                    // 关闭清除按钮
                    p._isMouseOverClearBtn = false;
                    _hideClearButton.call(_thisElm, event);
                });
                //
                $this.off(p.event.lossEventsForSelf + p.id);
                $this.on(p.event.lossEventsForSelf + p.id, function (event) {
                    // 关闭下拉框的事件
                    _onLose.apply(_thisElm, arguments);
                    //
                    _hideClearButton.call(_thisElm, event);
                });
                // 模板采用Underscore
                // if (_) {
                // 替换模板占位符，默认是使用<%%>.
                // _.templateSettings = p.view.templateSettings;
                // }
                //1.计算必要信息
                _calculateInfo.call(_thisElm);
                //2.下拉创建
                if (p._dropDown === null) {
                    _createDropDown.call(_thisElm);
                }
                //
                //情况1/3：控件初始化前就有值
                if ($.trim($this.val()).length > 0 && p.view.isRememberValue === true) {
                    p._isCurrentValueFromDataGrid = true;
                    p._rememberValue = $this.val();
                }
                //自动装载
                if (p.view.dropDownContainer && p.view.autoLoad) {
                    p.triggerAction.call(_thisElm);
                }
            });
        },

        /**
         * 分割 事件字符
         * @param eventString
         * @param suffix
         * @returns {*}
         * @private
         */
        _splitEventString = function (eventString, suffix) {
            var eventStr = eventString, eventArr;
            if ($.trim(eventString).length > 0) {
                eventArr = eventString.split(' ');
                //
                if ($.isArray(eventArr)) {
                    eventStr = eventArr.join(suffix + ' ');
                    eventStr += suffix + ' ';
                }
            }
            return eventStr;
        },

        /**
         * 控件下拉显示事件
         */
        _onAction = function (event) {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm);
            if (p._actionStop === true) {
                p._actionStop = false;
            } else {
                //触发没有停止，返回
                return;
            }
            //焦点事件分为：人为聚焦 和 js脚本聚焦
            if (!p.event.isIgnoreRelatedTarget) {
                if (event && event.type === "focus") {
                    if (event.relatedTarget) {
                    } else {
                        //js脚本聚焦(只聚焦，不执行onAction)
                        p._actionStop = true;
                        return;
                    }
                }
            }
            if (p == null) {
                p._actionStop = true;
                return;
            }
            if (p.isDropDownShow) {
                p._actionStop = true;
                return;
            }
            if ($this.prop("readonly") === true && !p.view.isAllowReadonlyAction) {
                p._actionStop = true;
                return;
            }
            //情况2/3：控件渲染后，通过其它js方式赋值（此时联想控件才渲染）
            if ($this.val() && p.view.isRememberValue === true) {
                p._isCurrentValueFromDataGrid = true;
                p._rememberValue = $this.val();
            }
            if (p.callback.beforeAction && p.callback.beforeAction.call(_thisElm, event) === false) {
                p._actionStop = true;
                return;
            }
            // 下拉、数据面板、是否已经装载完
            if (!p._isLoaded) {
                // 重新装载数据
                p.reload.call(_thisElm);
            } else {
                if (!p.view.isOnActionIgnoreFilter) {
                    // 重新装载数据
                    _reloadByValue.call(_thisElm, $this.val());
                } else {
                    _reloadByValue.call(_thisElm, "");
                }
            }
            //
            if (p.callback.afterAction) {
                p.callback.afterAction.call(_thisElm, event);
            }
            // TODO:暂时不需要 停止事件冒泡
            // event.stopPropagation();
        },

        /**
         * 按键弹起实现
         */
        _onKeyDown = function (event) {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm),
                selector, bindFill = p.view.bindFill,
                rowIdAttrName = getDataGridRowIdAttributeName.call(_thisElm);
            //兼容 beforeInput 的处理
            if ((p.callback.beforeKeyDown &&
                p.callback.beforeKeyDown.call(_thisElm, event) === false)
                || (p.callback.beforeInput &&
                    p.callback.beforeInput.call(_thisElm, event) === false)) {
                return;
            }
            if (event && event.keyCode) {
                // 可让value改变的 Keycode
                // keyCode为229时, 为中文状态下(如微软拼音中文状态下输入的字符)
                if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 65 && event.keyCode <= 90)
                    || event.keyCode === 8 || event.keyCode === 32 || event.keyCode === 46
                    || (event.keyCode >= 96 && event.keyCode <= 111)
                    || (event.keyCode >= 186 && event.keyCode <= 192)
                    || (event.keyCode >= 219 && event.keyCode <= 222)
                    || event.keyCode === 229) {
                    //输入改变了值，不是来至于选择面板
                    p._isCurrentValueFromDataGrid = false;
                    // 输入查询
                    if (p.input.enableInputAction === true) {
                        // 清除时间对象
                        window.clearTimeout(p._inputQueryTimeobj);
                        //根据输入框值，重载数据
                        p._inputQueryTimeobj = window.setTimeout(function () {
                            //重置到第一页
                            p.async.requestData[p.async.pageFieldDefine.currentPage] = 1;
                            //重载
                            _reloadByValue.call(_thisElm, $this.val());
                            //判断是否要显示 清除按钮
                            if (p.view.showClearButton) {
                                if ($.trim($this.val()).length > 0) {
                                    _showClearButton.call(_thisElm);
                                } else {
                                    _hideClearButton.call(_thisElm);
                                }
                            }
                        }, p.input.queryDelay);
                    }
                }
                // 键盘KeyCode范围（向上 向下）
                if (event.keyCode === 40 || event.keyCode === 38) {
                    //先确定下拉是否显示
                    if (!p.isDropDownShow) {
                        p.showDropDown.call(_thisElm, true);
                    }
                    // 找到所有有 _row_id 的 tr
                    var $dataGridTrs = p._dataPanel.find("tr[" + rowIdAttrName + "]");
                    if ($dataGridTrs && $dataGridTrs.length > 0) {
                        //按向下箭头
                        if (event.keyCode === 40) {
                            if (p._focusRowIndex === 0 || p._focusRowIndex > 0) {
                                if (p._focusRowIndex < $dataGridTrs.length - 1) {
                                    p._focusRowIndex += 1;
                                } else {
                                    // 分页最后一行执行翻页动作
                                    if (p.view.showPager) {
                                        _onClickDataPanel.call(_thisElm, getNextPageBtnId.call(_thisElm));
                                    }
                                    p._focusRowIndex = 0;
                                }
                            } else {
                                p._focusRowIndex = 0;
                            }
                        }
                        //按向上箭头
                        if (event.keyCode === 38) {
                            if (p._focusRowIndex === 0 || p._focusRowIndex > 0) {
                                if (p._focusRowIndex > 0) {
                                    p._focusRowIndex -= 1;
                                } else {
                                    // 分页第一行执行翻页动作
                                    if (p.view.showPager) {
                                        _onClickDataPanel.call(_thisElm, getPrevPageBtnId.call(_thisElm));
                                    }
                                    p._focusRowIndex = $dataGridTrs.length - 1;
                                }
                            } else {
                                p._focusRowIndex = $dataGridTrs.length - 1;
                            }
                        }
                        //设置 选中行 效果
                        _setSelectedRowEffect.call(_thisElm, $dataGridTrs);
                    }
                }
                //键盘Keycode范围（回车）
                if (event.keyCode === 13 || event.keyCode === 9) {
                    //下拉不显示不执行
                    if (!p.isDropDownShow) {
                        return;
                    } else {
                        var $trElem = p._dataPanel.find("tr.selected[" + rowIdAttrName + "]");
                        if ($trElem && $trElem.length === 1) {
                            _onSelectRow.call(_thisElm, event, $trElem.attr(rowIdAttrName));
                        }
                        //兼容afterInput的处理
                        if ($.isFunction(p.callback.afterKeyDown)) {
                            p.callback.afterKeyDown.call(_thisElm, event);
                        } else if ($.isFunction(p.callback.afterInput)) {
                            p.callback.afterInput.call(_thisElm, event);
                        }
                    }
                }
            }
            $this.off('keyup')
                .on('keyup', function () {
                    //如果控件的值被清空，同时把其它绑定填充的也清空
                    if ($.trim($this.val()).length === 0) {
                        for (selector in bindFill) {
                            if (bindFill[selector]) {
                                $(selector).val("");
                            }
                        }
                    }
                    //兼容afterInput的处理
                    if ($.isFunction(p.callback.afterKeyDown)) {
                        p.callback.afterKeyDown.call(_thisElm, event);
                    } else if ($.isFunction(p.callback.afterInput)) {
                        p.callback.afterInput.call(_thisElm, event);
                    }
                });

            // TODO:暂时不需要 停止事件冒泡
            // event.stopPropagation();
        },

        /**
         * 控件下拉隐藏事件
         */
        _onLose = function (event) {
            var target = event ? event.target : {}, _thisElm = this, p = _thisElm.p;
            if (target == _thisElm && event.type !== "blur") {
                return;
            }
            if (p == null) {
                return;
            }
            if (!p.isDropDownShow || p.view.dropDownContainer) {
                return;
            }
            if (p.callback.beforeLose && p.callback.beforeLose.call(_thisElm, event) === false) {
                return;
            }
            //判断鼠标焦是否离开 下拉面板(mouse out)
            if (p._isMouseOutDropDown !== true) {
                return;
            }
            // 隐藏等待条
            p.hideLoadingBar.call(_thisElm);
            // 关闭下拉框的具体执行内容，不直接调用
            p.hideDropDown.call(_thisElm);
            if (p.callback.afterLose) {
                p.callback.afterLose.call(_thisElm, event);
            }
            // 停止事件冒泡
            //event.stopPropagation();
        },

        /**
         * 创建下拉
         * @returns {*|HTMLElement}
         */
        _createDropDown = function () {
            var _thisElm = this, p = _thisElm.p, cid = p.id + 'DropDown', $dpc = $(p.view.dropDownContainer);
            // 页面有可能已创建过（在表格内有可能被反复创建）
            p._dropDown = $("#" + cid);
            if (p._dropDown && p._dropDown.length > 0) {
                // alert("_dropDown已经创建");
            } else {
                //
                if ($dpc && $dpc.length > 0) {
                    p._dropDown = $dpc.addClass("auto-comboBox-dropDown").css({
                        width: p.view.width, height: p.view.height
                    });
                    p._dropDown.show();
                } else {
                    p._dropDown = $('<div id="' + cid + '" class="auto-comboBox-dropDown" ></div>');
                    $(p.view.dropDownWithin).append(p._dropDown);
                }
            }
            // 创建数据面板
            if (p._dataPanel && p._dataPanel.length > 0) {
            } else {
                p._dropDown.append(_createDataPanel.call(_thisElm));
            }
            // 创建等待条
            if (p._loadingBar && p._loadingBar.length > 0) {
            } else {
                _createLoadingBar.call(_thisElm);
            }
            // 创建清除按钮
            if (p.view.showClearButton) {
                if (p._clearBtn && p._clearBtn.length > 0) {
                } else {
                    _createClearButton.call(_thisElm);
                }
            }
            //绑定下拉事件
            _bindDropDownEvents.call(_thisElm);
            //
            return p._dropDown;
        },

        /**
         *
         */
        _bindDropDownEvents = function () {
            var _thisElm = this, p = _thisElm.p,
                view = p.view;

            // 避免重复绑定
            if (p._isBindEventDropDown) {
                return;
            }
            p._dropDown.off('mouseover.auto-combobox' + p.id);
            p._dropDown.off('mouseout.auto-combobox' + p.id);

            // 绑定鼠标移入事件
            p._dropDown.on('mouseover.auto-combobox' + p.id, function (event) {
                _mouseOverDropDown.call(_thisElm, event);
            });
            // 绑定鼠标移出事件
            p._dropDown.on('mouseout.auto-combobox' + p.id, function (event) {
                _mouseOutDropDown.call(_thisElm, event);
            });
            //页面调整事件
            if (view.dropDownContainer &&
                (view.autoHeightPadding > -1 || view.autoHeightPadding > -1)) {
                var timeOutHeightObj = -1, $window = $(window);
                //
                $window.off('resize.auto-combobox' + p.id);
                //
                $window.on('resize.auto-combobox' + p.id, function (event) {
                    //清除时间对象，防止反复并发
                    window.clearTimeout(timeOutHeightObj);
                    //
                    timeOutHeightObj = window.setTimeout(function () {
                        //重新计算高度
                        view.height = $.oui.utils.element.getHeightToBottom(view.dropDownContainer, view.autoHeightPadding);
                        //重新下拉下拉尺寸
                        //不能无限缩小,控制在最小高度
                        if (view.height <= p._minHeight) {
                            view.height = p._minHeight;
                        }
                        p._dropDown.height(view.height);
                        //重新计算下拉尺寸
                        _resizeDropDown.call(_thisElm, true);
                        window.clearTimeout(timeOutHeightObj);
                    }, 300);//设定间隔时间，只有停下指定毫秒不动后，再执行调整
                });
            }
            p._isBindEventDropDown = true;
        },

        /**
         * 鼠标移入下拉
         */
        _mouseOverDropDown = function () {
            //
            this.p._isMouseOutDropDown = false;
            //
            //event.stopPropagation();
        },

        /**
         * 鼠标离开下拉
         */
        _mouseOutDropDown = function () {
            //
            this.p._isMouseOutDropDown = true;
            //event.stopPropagation();
        },

        /**
         * 创建加载条
         */
        _createLoadingBar = function () {
            var _thisElm = this, p = _thisElm.p, lbid = p.id + 'LoadingBar';
            // 页面有可能已创建过
            p._loadingBar = $("#" + lbid);
            if (p._loadingBar && p._loadingBar.length > 0) {
                // alert("_loadingBar已经创建");
            } else {
                p._loadingBar = $('<div id="' + lbid + '" style="display: none;position: absolute;top: 0;left: 0;"><div class="auto-comboBox-loadingbar-img"></div></div>');
                $(document.body).append(p._loadingBar);
            }
        },

        /**
         * 创建清除按钮
         */
        _createClearButton = function () {
            var _thisElm = this, p = _thisElm.p, cbId = p.id + 'ClearBtn';
            // 页面有可能已创建过
            p._clearBtn = $("#" + cbId);
            if (p._clearBtn && p._clearBtn.length > 0) {
                // alert("_clearBtn已经创建");
            } else {
                p._clearBtn = $('<div id="' + cbId + '" style="background-color: #FFFFFF;height:18px;width:18px;display: none;position: absolute;top: 0;left: 0;" title="' + $.autoCombo.i18n.clearButtonTitle + '" onmousedown="$(\'#' + p.id + '\').AutoCombobox(\'clearValue\',event);"><div class="auto-comboBox-clear-img"></div></div>');
                $(document.body).append(p._clearBtn);
                //鼠标 离开 进入、事件
                p._clearBtn.off("mouseover.auto-combobox")
                    .on("mouseover.auto-combobox" + p.id, function () {
                        p._isMouseOverClearBtn = true;
                    }).off("mouseout.auto-combobox")
                    .on("mouseout.auto-combobox" + p.id, function () {
                        p._isMouseOverClearBtn = false;
                    });
            }
        },

        /**
         * 显示清除按钮
         */
        _showClearButton = function () {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm);
            if (p._clearBtn && p._clearBtn.length > 0 && $.trim($this.val()).length > 0) {
                var _thisElmTop = $this.offset().top,
                    _thisElmLeft = $this.offset().left,
                    height = $this.outerHeight(),
                    width = $this.outerWidth(),
                    //清除图标父div 18*18
                    clearImgBoxWH = 18,
                    //清除图标 12*12
                    clearImgWH = 12,
                    paddingH = (height - clearImgBoxWH) / 2;
                // 遮挡数据面板
                if (p._clearBtn && p._clearBtn.length > 0) {
                    p._clearBtn.css({
                        top: (_thisElmTop + paddingH) + 'px',
                        left: (_thisElmLeft + width - clearImgBoxWH) + 'px',
                        display: "block",
                        "z-index": "9999"
                    }).show()
                    //让图片居中
                        .find("div.auto-comboBox-clear-img").css({"position": "relative", "top": ((clearImgBoxWH - clearImgWH) / 2) + "px", "left": ((clearImgBoxWH - clearImgWH) / 2) + "px"});
                }
            }
        },

        /**
         * 隐藏清除按钮
         */
        _hideClearButton = function (event) {
            var _thisElm = this, p = _thisElm.p;
            if (p._clearBtn && p._clearBtn.length > 0) {
                //鼠标 离开了 清除按钮
                if (p._isMouseOverClearBtn === false) {
                    p._clearBtn.hide();
                }
                if (event && event.type === "mousedown") {
                    p._clearBtn.hide();
                }
            }
        },

        /**
         * 装载数据
         */
        _onLoad = function () {
            var _thisElm = this, p = _thisElm.p;
            $(_thisElm).each(function () {
                switch (p.async.dataSourceType) {
                    case _dataSourceType.r :
                        _onRemote.call(_thisElm);
                        break;
                    case _dataSourceType.o :
                        _onOnceRemote.call(_thisElm);
                        break;
                    case _dataSourceType.l :
                        _onLocal.call(_thisElm);
                        break;
                }
            });
        },

        /**
         * 远程（异步）数据实现
         */
        _onRemote = function () {
            //
            _onAjaxRequest.call(this);
        },

        /**
         * 一次远程（异步）数据实现
         */
        _onOnceRemote = function () {
            //
            _onAjaxRequest.call(this);
        },

        /**
         * 发起Ajax请求
         */
        _onAjaxRequest = function () {
            var _thisElm = this, p = _thisElm.p;
            // 调用 设置请求数据 接口
            if ($.isFunction(p.callback.setRequestData)) {
                p.callback.setRequestData.call(_thisElm, p.async.requestData);
            }
            if (p.async.contentType && p.async.contentType.indexOf('application/json') > -1) {
                p.async.data = JSON.stringify(p.async.requestData);
            } else {
                p.async.data = $.extend({}, p.async.data, p.async.requestData);
            }
            // 发起请求
            $.ajax($.extend({}, {
                success: function (data, st, xhr) {
                    _onAjaxSuccess.call(_thisElm, data, st, xhr);
                },
                error: function (xhr, st, err) {
                    _onAjaxError.call(_thisElm, xhr, st, err);
                },
                beforeSend: function (xhr, settings) {
                    return _onAjaxBeforeSend.call(_thisElm, xhr, settings);
                }
            }, p.async));
        },

        /**
         * 请求前
         *
         * @param xhr
         * @param settings
         * @returns {boolean}
         */
        _onAjaxBeforeSend = function (xhr, settings) {
            var _thisElm = this, p = _thisElm.p, flag = true;
            if ($.isFunction(p.callback.beforeAjaxBeforeSend)) {
                flag = p.callback.beforeAjaxBeforeSend.call(_thisElm, xhr, settings);
            }
            // 显示等待条
            p.showLoadingBar.call(_thisElm);
            return flag;
        },

        /**
         * 请求成功
         *
         * @param responseData
         * @param st
         * @param xhr
         */
        _onAjaxSuccess = function (responseData, st, xhr) {
            var _thisElm = this, p = _thisElm.p, $dp = p._dataPanel,
                dataGridHtml = "", pagerBarHtml = "", filterStore = {},
                _recStore = {};

            // 获取输入框焦点，防止键盘事件失效
            _thisElm.focusEvent ? $(_thisElm).focus() : null;
            //delete _thisElm.focusEvent;
            // 1、
            if ($.isFunction(p.callback.beforeAjaxSuccess)) {
                p.callback.beforeAjaxSuccess.call(_thisElm, responseData, st, xhr);
            }
            if (p.async.dataSourceType === _dataSourceType.o) {
                if (!$.isArray(responseData)) {
                    _onAjaxError.call(_thisElm, xhr, st, "Response data format error!");
                    return;
                }
                // 原始数据存储(私有)
                p._originalDataStore = $.extend(true, [], responseData);
                // 给每行数据设置rowId
                _setRowId.call(_thisElm, p._originalDataStore);
                _recStore[p.async.pageFieldDefine.records] = p._originalDataStore;
                //格式化数据
                filterStore = _format.call(_thisElm, _recStore);
                //
                if (!p.view.isOnActionIgnoreFilter) {
                    // 获取过滤后的数据
                    filterStore = _getFilterRowDatas.call(_thisElm, _thisElm.value, filterStore);
                }
                // 构造数据html
                dataGridHtml = _buildDataGridHtml.call(_thisElm, filterStore);
                // 添加到数据面板中
                $dp.empty().append(dataGridHtml);
                //
                if (p.view.isHighlight) {
                    //设置高亮效果
                    _setHighlightEffect.call(_thisElm, _thisElm.value);
                }
            } else {
                // 原始数据存储(私有)
                p._originalDataStore = $.extend(true, [], $.autoCombo.getAccessor(responseData, p.async.pageFieldDefine.records));
                if (!responseData[p.async.pageFieldDefine.records]) {
                    _onAjaxError.call(_thisElm, xhr, st, "Response data format error!");
                    return;
                }
                // 给每行数据设置rowId
                _setRowId.call(_thisElm, p._originalDataStore);
                _recStore[p.async.pageFieldDefine.records] = p._originalDataStore;
                //格式化数据
                filterStore = _format.call(_thisElm, _recStore);
                // 构造数据html
                dataGridHtml = _buildDataGridHtml.call(_thisElm, filterStore);
                // 构造分页栏html
                if (p.view.showPager) {
                    pagerBarHtml = _buildPagerBarHtml.call(_thisElm, responseData);
                }
                // 添加到数据面板中
                if (p.view.pagerPosition === 'bottom') {
                    $dp.empty().append(dataGridHtml).append(pagerBarHtml);
                } else {
                    $dp.empty().append(pagerBarHtml).append(dataGridHtml);
                }
                //
                if (p.view.isHighlight) {
                    //设置高亮效果
                    _setHighlightEffect.call(_thisElm, _thisElm.value);
                }
                // 分页参数要继承响应回来的(反射获取分页参数，分页参数命名可以被自定义)
                var asyncDataStr = "{\"" + p.async.pageFieldDefine.pageSize + "\":"
                    + $.autoCombo.getAccessor(responseData, p.async.pageFieldDefine.pageSize) + ",\""
                    + p.async.pageFieldDefine.currentPage + "\":"
                    + $.autoCombo.getAccessor(responseData, p.async.pageFieldDefine.currentPage) + ",\""
                    + p.async.pageFieldDefine.totalPage + "\":"
                    + $.autoCombo.getAccessor(responseData, p.async.pageFieldDefine.totalPage) + ",\""
                    + p.async.pageFieldDefine.totalRecord + "\":"
                    + $.autoCombo.getAccessor(responseData, p.async.pageFieldDefine.totalRecord) + "}";
                $.extend(p.async.requestData, $.parseJSON(asyncDataStr));
            }
            //判断 设置选中第一行
            _setSelectedFirstRow.call(_thisElm, _thisElm.isPrePageOfKey || null);
            // 装载完成
            p._isLoaded = true;
            //
            p._actionStop = true;
            // 隐藏等待条
            p.hideLoadingBar.call(_thisElm);
            //
            if ($.isFunction(p.callback.afterLoad)) {
                p.callback.afterLoad.call(_thisElm, p._originalDataStore, $dp);
            }
            //
            if (p._justReload === false) {
                p.showDropDown.call(_thisElm, true);
            } else {
                p._justReload = false;
            }
        },

        /** 请求错误*/
        _onAjaxError = function (xhr, st, err) {
            var _thisElm = this, p = _thisElm.p;
            if ($.isFunction(p.callback.afterAjaxError)) {
                p.callback.afterAjaxError.call(_thisElm, xhr, st, err);
            }
            xhr = null;
            //
            p._actionStop = true;
            // 隐藏等待条
            p.hideLoadingBar.call(_thisElm);
            throw new Error("Request error:" + err);
        },

        /**
         * 本页数据实现
         */
        _onLocal = function () {
            var _thisElm = this, p = _thisElm.p, $dp = p._dataPanel,
                filterStore, dataGridHtml;
            // 显示等待条
            p.showLoadingBar.call(_thisElm);
            //判断是否 接口设置值 或者是 初始设置的值
            if (p._isSettedLocalData ||
                (p._originalDataStore.length === 0 && p.view.localData.length > 0)) {
                if (p._originalDataStore.length > 0) {
                    p.view.localData = $.extend(true, [], p._originalDataStore);
                }
                // 原始数据存储(私有)
                p._originalDataStore = $.extend(true, [], p.view.localData);
            }
            // 给每行数据设置rowId
            _setRowId.call(_thisElm, p._originalDataStore);
            var _recStore = {};
            _recStore[p.async.pageFieldDefine.records] = p._originalDataStore;
            //格式化数据
            filterStore = _format.call(_thisElm, _recStore);
            //
            if (!p.view.isOnActionIgnoreFilter) {
                // 获取过滤后的数据
                filterStore = _getFilterRowDatas.call(_thisElm, _thisElm.value, filterStore);
            }
            // 构造数据html
            dataGridHtml = _buildDataGridHtml.call(_thisElm, filterStore);
            // 添加到数据面板中
            $dp.empty().append(dataGridHtml);
            //
            if (p.view.isHighlight) {
                //设置高亮效果
                _setHighlightEffect.call(_thisElm, _thisElm.value);
            }
            //
            p.showDropDown.call(_thisElm, true);
            //判断 设置选中第一行
            _setSelectedFirstRow.call(_thisElm);
            // 装载完成
            p._isLoaded = true;
            //
            if ($.isFunction(p.callback.afterLoad)) {
                p.callback.afterLoad.call(_thisElm, p._originalDataStore, $dp);
            }
            //
            p._actionStop = true;
            // 隐藏等待条
            p.hideLoadingBar.call(_thisElm);
        },

        /**
         * 设置数据id
         *
         * @param rowDatas
         * @private
         */
        _setRowId = function (rowDatas) {
            var _thisElm = this,
                rowDataNum = 0, rowData = {},
                rowIdPropertyName = getDataRowIdPropertyName.call(_thisElm);
            if (rowDatas) {
                for (; rowDataNum < rowDatas.length; rowDataNum++) {
                    rowData = rowDatas[rowDataNum];
                    rowData[rowIdPropertyName] = "id" + rowDataNum;
                }
            }
        },

        /**
         * 创建数据面板
         * @returns {*|HTMLElement}
         */
        _createDataPanel = function () {
            var _thisElm = this, p = _thisElm.p, dpId = p.id + 'DataPanel';
            // 页面有可能已创建过（在表格内有可能被反复创建）
            p._dataPanel = $("#" + dpId);
            if (p._dataPanel && p._dataPanel.length > 0) {
                // alert("_dataPanel已经创建");
            } else {
                p._dataPanel = $('<div id="' + dpId + '"></div>');
                $(document.body).append(p._dataPanel);
            }
            // 绑定事件
            _bindDataPanelEvents.call(_thisElm);
            //
            return p._dataPanel;
        },

        /**
         *
         */
        _bindDataPanelEvents = function () {
            var _thisElm = this, p = _thisElm.p;
            // 避免重复绑定
            if (p._isBindEventDataPanel) {
                return;
            }
            p._dataPanel.off('mousedown.' + p.id);
            p._dataPanel.off('keyup.' + p.id);
            // 绑定点击事件
            p._dataPanel.on('mousedown.' + p.id, function (event) {
                _onClickDataPanel.call(_thisElm, event);

                // 获取输入框焦点，防止键盘事件失效
                _thisElm.focusEvent = true;
            });
            // 绑定按键事件
            p._dataPanel.on('keyup.' + p.id, function (event) {
                _onKeyupDataPanel.call(_thisElm, event);
            });
            p._isBindEventDataPanel = true;
        },

        /**
         * @param event
         */
        _onClickDataPanel = function (event) {
            var _thisElm = this, p = _thisElm.p,
                target = event.target || null, $t = $(target) || null,
                rowIdAttrName = getDataGridRowIdAttributeName.call(_thisElm),
                rowId = $t.parents("tr").attr(rowIdAttrName) || null,
                $td = $t.parents("td") || null,
                tdId;
            // 点击数据表格事件
            if (rowId) {
                _onSelectRow.call(_thisElm, event, rowId);
            } else {
                // 点击分页栏表格事件
                //if ($td.length > 1) {
                //    throw new Error('多级td结构！');
                //}
                // 如果点击在<span>上，并且父标签是<td>
                if ($t.is('span') && $td.is('td') || typeof event === 'string') {
                    tdId = $td.attr('id') || event;
                    var _currentPage = p.async.requestData[p.async.pageFieldDefine.currentPage], _isRefresh = false;
                    // 首页按钮事件
                    if (tdId === getFirstPageBtnId.call(_thisElm)) {
                        // 当前页
                        if (_currentPage !== 1) {
                            _currentPage = 1;
                            _isRefresh = true;
                        }
                    }
                    // 上一页按钮事件
                    if (tdId === getPrevPageBtnId.call(_thisElm)) {
                        // 上一页
                        if (_currentPage > 1) {
                            _currentPage = _currentPage - 1;
                            _isRefresh = true;
                            _thisElm.isPrePageOfKey = (typeof event === 'string');
                        }
                    }
                    // 下一页按钮事件
                    if (tdId === getNextPageBtnId.call(_thisElm)) {
                        // 下一页
                        if (_currentPage < p.async.requestData[p.async.pageFieldDefine.totalPage]) {
                            _currentPage = _currentPage + 1;
                            _isRefresh = true;
                            _thisElm.isPrePageOfKey = false;
                        }
                    }
                    // 末页按钮事件
                    if (tdId === getLastPageBtnId.call(_thisElm)) {
                        // 末页
                        if (_currentPage !== p.async.requestData[p.async.pageFieldDefine.totalPage]) {
                            _currentPage = p.async.requestData[p.async.pageFieldDefine.totalPage];
                            _isRefresh = true;
                        }
                    }
                    if (_isRefresh === true) {
                        // 设置当前页码
                        p.async.requestData[p.async.pageFieldDefine.currentPage] = _currentPage;
                        // 重载数据面板
                        _onRemote.call(_thisElm);
                    }
                }
            }
            //点击事件必须冒泡出去
            //event.stopPropagation();
        },

        /**
         * @param event
         */
        _onKeyupDataPanel = function (event) {
            //event.stopPropagation();
        },

        /**
         * 选择行事件实现
         */
        _onSelectRow = function (event, rowId) {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm),
                //$target = $(event.target),
                selector, fieldName, bindFill = p.view.bindFill,
                rowData = _getRowData.call(_thisElm, rowId),
                rowIdAttrName = getDataGridRowIdAttributeName.call(_thisElm),
                $dataGridTrElems = p._dataPanel.find("tr[" + rowIdAttrName + "]");
            //
            p._focusRowIndex = $dataGridTrElems.index(p._dataPanel.find("tr[" + rowIdAttrName + "=" + rowId + "]"));
            //设置 选中行 效果
            _setSelectedRowEffect.call(_thisElm, $dataGridTrElems);
            // 行事件选择前
            if (p.callback.beforeSelectRow
                && p.callback.beforeSelectRow.call(_thisElm, rowData) === false) {
                return;
            }
            //当前选择行Id
            p.currentSelectedRowId = rowId;
            //自动填充
            if (!$.isEmptyObject(bindFill)) {
                for (selector in bindFill) {
                    if (bindFill[selector]) {
                        fieldName = bindFill[selector];
                        $(selector).val(rowData[fieldName]);
                    }
                }
            }
            // 行事件选择后
            if (p.callback.afterSelectRow) {
                p.callback.afterSelectRow.call(_thisElm, rowData);
            }
            //情况3/3：值从数据面板选择
            if (p.view.isRememberValue === true) {
                //输入框值 来自于dataGrid
                //（控件不负责赋值到 输入框，数据不确定是否来自于dataGrid，通过没有执行 _onSelectRow来判定）
                p._isCurrentValueFromDataGrid = true;
                //
                p._rememberValue = $this.val();
            }
            // 关闭下拉框
            p.hideDropDown.call(_thisElm);
        },

        /**
         * 构造分页栏html
         */
        _buildPagerBarHtml = function (data) {
            var _thisElm = this, p = _thisElm.p;
            //分页栏模板
            var _compiled = _.template(_getPagerBarHtmlTemplate.call(_thisElm));
            //首页，上一页，下一页，末页 Id
            data.firstPageBtnId = getFirstPageBtnId.call(_thisElm);
            data.prevPageBtnId = getPrevPageBtnId.call(_thisElm);
            data.nextPageBtnId = getNextPageBtnId.call(_thisElm);
            data.lastPageBtnId = getLastPageBtnId.call(_thisElm);
            data.pagerId = getPagerId.call(_thisElm);
            data.currentPage = data[p.async.pageFieldDefine.currentPage];
            data.totalPage = data[p.async.pageFieldDefine.totalPage];
            data.totalRecord = data[p.async.pageFieldDefine.totalRecord];
            //
            return _compiled(data);
        },

        /**
         * 获取数据网格的html模板
         */
        _getDataGridHtmlTemplate = function () {
            var _thisElm = this, p = _thisElm.p;
            // 优先用设置的html模板
            if (p.view.dataGridHtmlTemplate) {
                return p.view.dataGridHtmlTemplate;
            } else {
                return _buildDataGridHtmlTemplate.call(_thisElm);
            }
        },

        /**
         * 获取数据网格的html模板
         */
        _buildDataGridHtmlTemplate = function () {
            var _thisElm = this, p = _thisElm.p,
                i, cm, cms = p.view.colModels,
                showFieldNames = p._showFieldNames,
                rowIdAttrName = getDataGridRowIdAttributeName.call(_thisElm),
                rowIdPropertyName = getDataRowIdPropertyName.call(_thisElm),
                dataGridHeadId = getDataGridHeadId.call(_thisElm),//数据网格 表头Id
                dataGridId = getDataGridId.call(_thisElm),//数据网格Id
                _tableTemplate = "",
                tdStyle = "";
            //---------构造 表头(列名)所在的div
            // 判断可显示的列是否只有一个，并且开启单列不显示 表头(并且强制不显示=false)
            if ((!p.view.singleColumnNotHead || showFieldNames.length > 1) && (!p.view.forceHideHead)) {
                _tableTemplate = '<div class="auto-comboBox-dataPanel-heard">';
                _tableTemplate += '<table id="' + dataGridHeadId + '" class="auto-comboBox-dataPanel-table"><thead><tr>';
                for (i = 0; i < cms.length; i++) {
                    cm = cms[i];
                    // <td>code</td><td>名称</td><td>备注</td>
                    cm.label = cm.label ? cm.label : cm.name;
                    //
                    cm.align = cm.align ? cm.align : "center";
                    //
                    if (cm.isHide === true) {
                        _tableTemplate += '<td style="display: none;">' + cm.label + '</td>';
                    } else {
                        tdStyle = 'style="';
                        if (cm.align) {
                            //tdStyle += 'text-align:' + cm.align + ';';
                            tdStyle += 'text-align:center;';
                        }
                        if (cm.width > 0) {
                            tdStyle += 'width:' + cm.width + 'px;';
                        }
                        tdStyle += '"';
                        _tableTemplate += '<td isHead colName="' + cm.name + '" ' + tdStyle + '>' + cm.label + '</td>';
                    }
                }
                _tableTemplate += '</tr></thead></table></div>';
            }
            //---------构造 表体(数据网格)所在的div
            _tableTemplate += '<div style="overflow-y:auto;">';
            //有数据
            _tableTemplate += '<table id="' + dataGridId + '" class="auto-comboBox-dataPanel-table">';
            _tableTemplate += '<%if(_.size(' + p.async.pageFieldDefine.records + ')>0){%>';
            _tableTemplate += '<tbody><%_.each(' + p.async.pageFieldDefine.records + ', function(r,idx){%>';
            // 设置数据列的列id
            _tableTemplate += '<tr ' + rowIdAttrName + '="<%=r.' + rowIdPropertyName + '%>">';
            // 组装数据列html模板
            for (i = 0; i < cms.length; i++) {
                cm = cms[i];
                // "<td><%=r.code%></td><td><%=r.name%></td><td><%=r.remark%></td>"
                if (cm.isHide === true) {
                    _tableTemplate += '<td style="display: none;"><%=r.' + cm.name + '%></td>';
                } else {
                    tdStyle = 'style="';
                    if (cm.align) {
                        tdStyle += 'text-align:' + cm.align + ';';
                    }
                    if (cm.width > 0) {
                        tdStyle += 'width:' + cm.width + 'px;';
                    }
                    tdStyle += '"';
                    _tableTemplate += '<td colName="' + cm.name + '" ' + tdStyle + '><%=r.' + cm.name + '%></td>';
                }
            }
            _tableTemplate += '</tr><%});%>';
            _tableTemplate += '</tbody>';
            _tableTemplate += '<%}else{%>';
            //没有数据(保持和控件宽度一致)
            _tableTemplate += '<tbody><tr><td style="border-width: 0;">' + $.autoCombo.i18n.emptyRecordsHtml + '</td></tr></tbody>';
            _tableTemplate += '<%}%>';
            _tableTemplate += '</table></div>';

            p.view.dataGridHtmlTemplate = _tableTemplate;
            return p.view.dataGridHtmlTemplate;
        },

        /**
         * 构造数据网格(可以是表格 或 非表格，根据html模板来定)
         * @param data
         * @returns {*}
         */
        _buildDataGridHtml = function (data) {
            var _thisElm = this, p = _thisElm.p,
                records, rowData, k, key,
                _compiled = _.template(_getDataGridHtmlTemplate.call(_thisElm)),
                _tempData = $.extend(true, {}, data);
            if (p.view.isEncode) {
                records = _tempData[p.async.pageFieldDefine.records];
                for (k = records.length - 1; k >= 0; k--) {
                    rowData = records[k];
                    for (key in rowData) {
                        rowData[key] = _encode(rowData[key]);
                    }
                }
            }
            return _compiled(_tempData);
        },

        /**
         * 获取分页栏的html模板
         * @returns {*|a.view.pagerBarHtmlTemplate}
         */
        _getPagerBarHtmlTemplate = function () {
            var _thisElm = this, p = _thisElm.p;
            //
            return p.view.pagerBarHtmlTemplate;
        },

        /**
         * 重新计算下拉尺寸
         * @returns {*}
         * @private
         */
        _resizeDropDown = function () {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    view = p.view,
                    async = p.async;
                //下拉是浮动
                if (view.dropDownContainer === null) {
                    //本地
                    if (async.dataSourceType === _dataSourceType.l) {
                        _resizeForLocal.call(_thisElm, false);
                    }
                    //一次性远程
                    if (async.dataSourceType === _dataSourceType.o) {
                        _resizeForOnceRemote.call(_thisElm, false);
                    }
                    //远程
                    if (async.dataSourceType === _dataSourceType.r) {
                        _resizeForRemote.call(_thisElm, false);
                    }
                } else {//下拉固定在页面
                    //本地
                    if (async.dataSourceType === _dataSourceType.l) {
                        _resizeForLocal.call(_thisElm, true);
                    }
                    //一次性远程
                    if (async.dataSourceType === _dataSourceType.o) {
                        _resizeForOnceRemote.call(_thisElm, true);
                    }
                    //远程
                    if (async.dataSourceType === _dataSourceType.r) {
                        _resizeForRemote.call(_thisElm, true);
                    }
                }
            });
        },

        /**
         * 下拉是浮动
         * 下拉固定在页面
         * @param hasDropDownContainer 是否固定下拉
         * @private
         */
        _resizeForLocal = function (hasDropDownContainer) {
            var _thisElm = this, p = _thisElm.p,
                minusHeight,
                $dataGrid = $("#" + getDataGridId.call(_thisElm)),
                $dataGridHead = $("#" + getDataGridHeadId.call(_thisElm)),
                view = p.view,
                dropDownWidth = 0,
                dataGridParentHeight = 0,
                realRowTotalHeight;
            //1.先算宽度,宽度不够数据被挤压,高度会有偏差
            //宽度是引用的
            if (view.widthRefer != null) {
                dropDownWidth = _getWidthRefer.call(_thisElm);
            } else {
                if (view.width != 0) {
                    dropDownWidth = view.width;
                } else {
                    dropDownWidth = p._totalColWidth;
                }
            }
            //1.1.设置下拉宽度
            p._dropDown.width(dropDownWidth);
            //1.2.设置每个列
            _resizeColWidth.call(_thisElm, dropDownWidth);
            //2.计算高度
            //减去宽度 = 列头高度
            minusHeight = $dataGridHead.outerHeight();
            minusHeight = minusHeight >= 0 ? minusHeight : 0;
            //实际数据行高
            realRowTotalHeight = _getRealRowTotalHeight.call(_thisElm);
            //实际高度 大于 指定高度
            if (view.height != 0) {
                //实际行数据高度 + 未减去的高度 >= 指定的高度
                if ((realRowTotalHeight + minusHeight) >= view.height) {
                    //调整 数据网格 所属的div 固定高度（减去头部高度）
                    dataGridParentHeight = view.height - minusHeight;
                } else {
                    dataGridParentHeight = realRowTotalHeight;
                }
            } else {
                //实际行数据高度 + 未减去的高度 >= 最大高度(不能让数据随意撑大)
                if ((realRowTotalHeight + minusHeight) >= p._maxHeight) {
                    //调整 数据网格 所属的div 固定高度（减去头部高度）
                    dataGridParentHeight = p._maxHeight - minusHeight;
                } else {
                    dataGridParentHeight = realRowTotalHeight;
                }
            }
            //2.2.设置数据网格所在 元素高度
            if (p._dropDown.height() == 0 && hasDropDownContainer) {
                //下拉框高度为0
                p._dropDown.height(dataGridParentHeight + minusHeight);
            }
            $dataGrid.parent().height(dataGridParentHeight);
        },

        /**
         * 下拉是浮动
         * 下拉固定在页面
         * @param hasDropDownContainer 是否固定下拉
         * @private
         */
        _resizeForOnceRemote = function (hasDropDownContainer) {
            var _thisElm = this, p = _thisElm.p,
                minusHeight,
                $dataGrid = $("#" + getDataGridId.call(_thisElm)),
                $dataGridHead = $("#" + getDataGridHeadId.call(_thisElm)),
                view = p.view,
                dropDownWidth = 0,
                dataGridParentHeight = 0,
                realRowTotalHeight;
            //1.先算宽度,宽度不够数据被挤压,高度会有偏差
            //宽度是引用的
            if (view.widthRefer != null) {
                dropDownWidth = _getWidthRefer.call(_thisElm);
            } else {
                if (view.width != 0) {
                    dropDownWidth = view.width;
                } else {
                    dropDownWidth = p._totalColWidth;
                }
            }
            //1.1.设置下拉宽度
            p._dropDown.width(dropDownWidth);
            //1.2.设置每个列
            _resizeColWidth.call(_thisElm, dropDownWidth);
            //2.计算高度
            //减去宽度 = 列头高度
            minusHeight = $dataGridHead.outerHeight();
            minusHeight = minusHeight >= 0 ? minusHeight : 0;
            //实际数据行高
            realRowTotalHeight = _getRealRowTotalHeight.call(_thisElm);
            //实际高度 大于 指定高度
            if (view.height != 0) {
                //实际行数据高度 + 未减去的高度 >= 指定的高度
                if ((realRowTotalHeight + minusHeight) >= view.height) {
                    //调整 数据网格 所属的div 固定高度（减去头部高度）
                    dataGridParentHeight = view.height - minusHeight;
                } else {
                    dataGridParentHeight = realRowTotalHeight;
                }
            } else {
                //实际行数据高度 + 未减去的高度 >= 最大高度(不能让数据随意撑大)
                if ((realRowTotalHeight + minusHeight) >= p._maxHeight) {
                    //调整 数据网格 所属的div 固定高度（减去头部高度）
                    dataGridParentHeight = p._maxHeight - minusHeight;
                } else {
                    dataGridParentHeight = realRowTotalHeight;
                }
            }
            //2.2.设置数据网格所在 元素高度
            if (p._dropDown.height() == 0 && hasDropDownContainer) {
                //下拉框高度为0
                p._dropDown.height(dataGridParentHeight + minusHeight);
            }
            $dataGrid.parent().height(dataGridParentHeight);
        },

        /**
         * 下拉是浮动
         * 下拉固定在页面
         * @param hasDropDownContainer 是否固定下拉
         * @private
         */
        _resizeForRemote = function (hasDropDownContainer) {
            var _thisElm = this, p = _thisElm.p,
                minusHeight,
                $dataGrid = $("#" + getDataGridId.call(_thisElm)),
                $dataGridHead = $("#" + getDataGridHeadId.call(_thisElm)),
                $pager = $("#" + getPagerId.call(_thisElm)),
                view = p.view,
                dropDownWidth = 0,
                dataGridParentHeight = 0,
                realRowTotalHeight;
            //1.先算宽度,宽度不够数据被挤压,高度会有偏差
            //宽度是引用的
            if (view.widthRefer != null) {
                dropDownWidth = _getWidthRefer.call(_thisElm);
            } else {
                if (view.width != 0) {
                    dropDownWidth = view.width;
                } else {
                    dropDownWidth = p._totalColWidth;
                }
            }
            //（dataSourceType="remote",showPager= true, 时生效）
            if (view.showPager) {
                //必须 大于最小宽度）
                if (dropDownWidth <= p.view.minWidth) {
                    dropDownWidth = p.view.minWidth;
                }
            }
            //1.1.设置下拉宽度
            p._dropDown.width(dropDownWidth);
            //1.2.设置每个列
            _resizeColWidth.call(_thisElm, dropDownWidth);
            //2.计算高度
            //减去宽度 = 列头高度
            minusHeight = $dataGridHead.outerHeight();
            minusHeight = minusHeight >= 0 ? minusHeight : 0;
            //减去宽度 += 分页栏高度
            minusHeight += $pager.outerHeight();
            minusHeight = minusHeight >= 0 ? minusHeight : 0;
            //实际数据行高
            realRowTotalHeight = _getRealRowTotalHeight.call(_thisElm);
            //如果指定了高度,并且 实际高度 大于 指定高度
            if (view.height != 0) {
                //是固定下拉时 不按实际数据高度 收缩高度,会导致 分页栏 和 数据行 贴紧
                if (!hasDropDownContainer) {
                    //实际行数据高度 + 未减去的高度 >= 指定的高度
                    if ((realRowTotalHeight + minusHeight) >= view.height) {
                        //调整 数据网格 所属的div 固定高度（减去头部高度和分页栏）
                        dataGridParentHeight = view.height - minusHeight;
                    } else {
                        dataGridParentHeight = realRowTotalHeight;
                    }
                } else {
                    //调整 数据网格 所属的div 固定高度（减去头部高度和分页栏）
                    dataGridParentHeight = view.height - minusHeight;
                }
            } else {
                //是固定下拉时 不按实际数据高度 收缩高度,会导致 分页栏 和 数据行 贴紧
                if (!hasDropDownContainer) {
                    //实际行数据高度 + 未减去的高度 >= 最大高度(不能让数据随意撑大)
                    if ((realRowTotalHeight + minusHeight) >= p._maxHeight) {
                        //调整 数据网格 所属的div 固定高度（减去头部高度和分页栏）
                        dataGridParentHeight = p._maxHeight - minusHeight;
                    } else {
                        dataGridParentHeight = realRowTotalHeight;
                    }
                } else {
                    //调整 数据网格 所属的div 固定高度（减去头部高度和分页栏）
                    dataGridParentHeight = p._maxHeight - minusHeight;
                }
            }
            //2.2.设置数据网格所在 元素高度
            if (p._dropDown.height() == 0 && hasDropDownContainer) {
                //下拉框高度为0
                p._dropDown.height(dataGridParentHeight + minusHeight);
            }
            $dataGrid.parent().height(dataGridParentHeight);
        },

        /**
         * 获取实际 行数据的 总行高
         * @private
         */
        _getRealRowTotalHeight = function () {
            var _thisElm = this,
                p = _thisElm.p,
                $dataGridTrs,
                rowIdAttrName,
                totalRowHeight = 0;
            //去除高度空白(如果实际数据不多，就按实际数据高度)
            rowIdAttrName = getDataGridRowIdAttributeName.call(_thisElm);
            // 找到所有有 _row_id 的 tr
            $dataGridTrs = p._dataPanel.find("tr[" + rowIdAttrName + "]");
            //
            if ($dataGridTrs && $dataGridTrs.length > 0) {
                $dataGridTrs.each(function (index, elm) {
                    totalRowHeight += $(elm).outerHeight();
                });
                totalRowHeight = (totalRowHeight + 1);//多加一个像素,防止最后一行边宽被遮住
            }
            // 没有数据时直接设置表格高度
            else {
                totalRowHeight = $("table", p._dataPanel).height();
            }
            return totalRowHeight;
        },

        /**
         * 重新设置列宽
         * @param totalWidth
         * @private
         */
        _resizeColWidth = function (totalWidth) {
            var _thisElm = this, p = _thisElm.p,
                colModel, i,
                view = p.view;
            //每次都会重新设置 列宽(列宽 _buildDataGridHtmlTemplate 已经被写入，再输入过滤时_reloadByValue 又会回复原默认)
            //colName="name"
            for (i = 0; i < view.colModels.length; i++) {
                colModel = view.colModels[i];
                //所有同名列(包括列头、单元格) 宽度重调整
                if (colModel.isHide !== true) {
                    //列宽 = 列宽比例 * (总列宽-滚动条宽度)
                    colModel.width = colModel._widthRatio * totalWidth;
                    $('[colName="' + colModel.name + '"]', p._dropDown).css({"width": colModel.width + "px"});
                }
            }
        },

        /**
         * 分页栏 首页按钮ID
         * @returns {string}
         */
        getFirstPageBtnId = function () {
            return this.p.id + "FirstPageBtn";
        },

        /**
         * 分页栏 上一页按钮ID
         * @returns {string}
         */
        getPrevPageBtnId = function () {
            return this.p.id + "PrevPageBtn";
        },

        /**
         * 分页栏 下一页按钮ID
         * @returns {string}
         */
        getNextPageBtnId = function () {
            return this.p.id + "NextPageBtn";
        },

        /**
         * 分页栏 末页按钮ID
         * @returns {string}
         */
        getLastPageBtnId = function () {
            return this.p.id + "LastPageBtn";
        },

        /**
         * 分页栏Id
         * @returns {string}
         */
        getPagerId = function () {
            return this.p.id + "Pager";
        },

        /**
         * 数据网格 表头Id
         * @returns {string}
         */
        getDataGridHeadId = function () {
            return this.p.id + "DataGridHead";
        },

        /**
         * 数据网格Id
         * @returns {string}
         */
        getDataGridId = function () {
            return this.p.id + "DataGrid";
        },

        /**
         * 获取rowId，在dataGrid（html tr）上的属性名
         * @returns {string}
         */
        getDataGridRowIdAttributeName = function () {
            return "_row_id";
        },

        /**
         * 获取数据rowId的属性名
         * @returns {string}
         */
        getDataRowIdPropertyName = function () {
            return "_rowId";
        },

        /**
         * 下拉定位引用
         * @returns {*}
         * @private
         */
        _getPositionRefer = function () {
            var _thisElm = this, $this = $(_thisElm),
                p = _thisElm.p,
                _positionRefer = p.view.positionRefer,
                $positionRefer;
            if ($.isFunction(_positionRefer)) {
                $positionRefer = _positionRefer.call(_thisElm);
                $positionRefer = ($positionRefer && $positionRefer.length > 0) ? $positionRefer : $this;
            } else {
                $positionRefer = $(_positionRefer);
                $positionRefer = ($positionRefer && $positionRefer.length > 0) ? $positionRefer : $this;
            }
            return $positionRefer;
        },

        /**
         * 宽度引用
         * @returns {*}
         * @private
         */
        _getWidthRefer = function () {
            var _thisElm = this,
                p = _thisElm.p,
                _widthRefer = p.view.widthRefer;
            if ($.isFunction(_widthRefer)) {
                return _widthRefer.call(_thisElm);
            } else {
                return _widthRefer;
            }
        },

        /**
         * 根据单个rowId获取行数据
         * @param rowId
         * @returns {{}}
         */
        _getRowData = function (rowId) {
            var _thisElm = this, p = _thisElm.p, ods = {},
                rowIdPropertyName = getDataRowIdPropertyName.call(_thisElm);
            for (var i = 0; i < p._originalDataStore.length; i++) {
                var _ods = p._originalDataStore[i];
                if (_ods[rowIdPropertyName] === rowId) {
                    ods = _ods;
                    break;
                }
            }
            if ($.isEmptyObject(ods)) {
                //throw new Error('所选行数据为空，行数据ID值为空或者为设置行数据ID字段名！');
            }
            return ods;
        },

        /**
         * 判断 设置选中第一行
         */
        _setSelectedFirstRow = function (isLast) {
            var _thisElm = this, p = _thisElm.p,
                rowIdAttrName = getDataGridRowIdAttributeName.call(_thisElm), rowNum = 0;
            //判断是否默认选中第一行
            if (p.view.isSelectedFirstRow === true) {
                var $dataGridTrElems = p._dataPanel.find("tr[" + rowIdAttrName + "]");
                if ($dataGridTrElems && $dataGridTrElems.length > 0) {
                    //如果下拉面板固定在页面上，并且已经选中了行
                    if (p.currentSelectedRowId && p.view.dropDownContainer) {
                        //设置 选中行 效果
                        _setSelectedRowEffect.call(_thisElm, $dataGridTrElems);
                        return;
                    }

                    //元素上没值，如果有设置默认第一行就去选中第一行或者尾行下标
                    //文本框输入值，去模糊匹配的时候，默认选中第一行
                    //元素有值，就强制不去选中第一行
                    if (!_thisElm.value || p._rememberValue !== _thisElm.value) {
                        //第一行或者尾行下标
                        rowNum = isLast ? p._originalDataStore.length - 1 : 0;
                        p._focusRowIndex = rowNum;
                    }

                    //设置 选中行 效果
                    _setSelectedRowEffect.call(_thisElm, $dataGridTrElems);
                    //触发动作
                    if (p.view.isSelectedFirstRowAction === true) {
                        var $trElem = p._dataPanel.find("tr.selected[" + rowIdAttrName + "]");
                        _onSelectRow.call(_thisElm, event, $trElem.attr(rowIdAttrName));
                    }
                }
            }
        },

        /**
         * 根据输入框的值，重载
         * @param filterValue
         * @private
         */
        _reloadByValue = function (filterValue) {
            var _thisElm = this, p = _thisElm.p,
                filterStore = {}, formatStore = {},
                $dp = p._dataPanel, dataGridHtml = "",
                _filterValue = filterValue ? filterValue : "";
            switch (p.async.dataSourceType) {
                case _dataSourceType.r :
                    // 将输入的值作为参数，提交到远程
                    p.async.requestData[p.input.inputQueryParamName] = _filterValue;
                    //
                    _onRemote.call(_thisElm);
                    break;
                case _dataSourceType.o :
                case _dataSourceType.l :
                    var _recStore = {};
                    //判断是否 接口设置值 或者是 初始设置的值
                    if (p._isSettedLocalData ||
                        (p._originalDataStore.length === 0 && p.view.localData.length > 0)) {
                        if (p._originalDataStore.length > 0) {
                            p.view.localData = $.extend(true, [], p._originalDataStore);
                        }
                        // 原始数据存储(私有)
                        p._originalDataStore = $.extend(true, [], p.view.localData);
                        // 给每行数据设置rowId
                        _setRowId.call(_thisElm, p._originalDataStore);
                    }
                    //
                    _recStore[p.async.pageFieldDefine.records] = p._originalDataStore;
                    //格式化数据
                    filterStore = _format.call(_thisElm, _recStore);
                    // 获取过滤后的数据
                    formatStore = _getFilterRowDatas.call(_thisElm, _filterValue, filterStore);
                    // 构造数据html（并且格式化数据）
                    dataGridHtml = _buildDataGridHtml.call(_thisElm, formatStore);
                    // 添加到数据面板中
                    $dp.empty().append(dataGridHtml);
                    //设置高亮效果
                    //
                    if (p.view.isHighlight) {
                        //设置高亮效果
                        _setHighlightEffect.call(_thisElm, _filterValue);
                    }
                    // 隐藏等待条
                    p.hideLoadingBar.call(_thisElm);
                    //第一次走 reload，此处不需要 afterLoad
                    // if ($.isFunction(p.callback.afterLoad)) {
                    //     p.callback.afterLoad.call(_thisElm, filterStore, $dp);
                    // }
                    //
                    if (p._justReload === false) {

                        p.showDropDown.call(_thisElm, true);
                    } else {
                        p._justReload = false;
                    }
                    //判断 设置选中第一行
                    _setSelectedFirstRow.call(_thisElm);
                    // 装载完成
                    p._isLoaded = true;
                    break;
            }
        },

        /**
         * 获取 输入值 被过滤后的数据集
         * @param inputValue
         * @param dataStore
         * @returns {*}
         * @private
         */
        _getFilterRowDatas = function (inputValue, dataStore) {
            var _thisElm = this, p = _thisElm.p, i = 0, j = 0,
                filterRowDatas = [], filterFieldName, rowData = {},
                inputFilterFieldNames = _getInputFilterFieldNames.call(_thisElm),
                tempStore = $.extend(true, {}, dataStore),
                tempDatas = $.autoCombo.getAccessor(tempStore, p.async.pageFieldDefine.records);
            if (inputValue.length > 0 && inputFilterFieldNames.length > 0) {
                for (; i < tempDatas.length; i++) {
                    rowData = tempDatas[i];
                    if (!$.isEmptyObject(rowData)) {
                        for (j = 0; j < inputFilterFieldNames.length; j++) {
                            filterFieldName = inputFilterFieldNames[j];
                            // 如果有其中一个字段匹配，整行都保留
                            if ($.trim(rowData[filterFieldName]) &&
                                ($.trim(rowData[filterFieldName])).toLowerCase().indexOf(inputValue.toLowerCase()) > -1) {
                                filterRowDatas.push(rowData);
                                break;
                            }
                        }
                    }
                }
                tempStore[p.async.pageFieldDefine.records] = filterRowDatas;
            } else {
                tempStore = dataStore;
            }
            return tempStore;
        },

        /**
         * 设置 高亮效果
         * @param matchStr
         * @returns {*}
         * @private
         */
        _setHighlightEffect = function (matchStr) {
            var _thisElm = this, p = _thisElm.p,
                regExp = new RegExp(/\//g),
                regExp1 = new RegExp(/\'/g),
                regExp2,//不忽略大小写,
                //匹配出的数组
                matchArr = [], i;
            // var s = "1001\\/15";console.info(eval('/'+s+'/gi'));  输出: /1001\/15/gi
            matchStr = matchStr.replace(regExp, '\\/');//先转义  ' / '  斜杠
            matchStr = matchStr.replace(regExp1, "\\'");//先转义  " ' "  单引号
            regExp = eval('new RegExp(\'/' + matchStr + '/gi\');');//和指定的字符全匹配并忽略大小写,
            //
            $('td', p._dataPanel).each(function (ind, elm) {
                var $td = $(elm),
                    text;
                if ($td && $td.length > 0) {
                    text = $td.text();
                    if ($.trim(text).length > 0) {
                        //匹配
                        if ($.isArray(text.match(regExp)) && text.match(regExp).length > 0) {
                            matchArr = text.match(regExp);
                            //去除重复
                            matchArr = _.uniq(matchArr);
                            //
                            for (i = 0; i < matchArr.length; i++) {
                                regExp2 = eval('/' + matchArr[i] + '/g');//不忽略大小写,
                                text = text.replace(regExp2, "+_)(*&^%$#@!~" + matchArr[i] + "~!@#$%^&*()_+");
                            }
                            //将占位符替换
                            regExp2 = eval('/\\+\\_\\)\\(\\*\\&\\^\\%\\$\\#\\@\\!\\~/g');
                            text = text.replace(regExp2, "<span class='match'>");
                            regExp2 = eval('/\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+/g');
                            text = text.replace(regExp2, "</span>");
                            //
                            $td.html(text);
                        }
                    }
                }
            });
        },

        /**
         * 获取 输入值 被过滤的 字段
         *
         * @returns {Array}
         * @private
         */
        _getInputFilterFieldNames = function () {
            var _thisElm = this, p = _thisElm.p, i = 0, colModel = {};
            if (p._inputFilterFieldNames.length > 0) {
            } else {
                p._inputFilterFieldNames = [];
                for (; i < p.view.colModels.length; i++) {
                    colModel = p.view.colModels[i];
                    // 指定不被过滤的
                    if (colModel.isInputFilter === false) {
                        continue;
                    }
                    // 不隐藏的、指定被过滤的
                    if (colModel.isInputFilter || colModel.isHide !== true) {
                        p._inputFilterFieldNames.push(colModel.name);
                    }
                }
            }
            return p._inputFilterFieldNames;
        },

        /**
         * 计算 私有用配置信息
         *
         * 高度 view.height
         * 宽度 view.width
         * 总列宽 p._totalColWidth
         * 显示的字段 p._showFieldNames
         * 显示的字段的宽度比率 colModel._widthRatio
         * 需要格式化的字段配置 p._formatterMap
         *
         * @returns {Array}
         * @private
         */
        _calculateInfo = function () {
            var _thisElm = this, p = _thisElm.p, i, colModel = {};
            //计算 显示的字段、总宽度、格式化的字段模型
            p._showFieldNames = [];
            p._formatterMap = {};
            for (i = 0; i < p.view.colModels.length; i++) {
                //默认列属性值
                p.view.colModels[i] = $.extend(true, {}, _defaultColModel, p.view.colModels[i]);
                colModel = p.view.colModels[i];
                // 显示字段
                if (colModel.isHide !== true) {
                    p._showFieldNames.push(colModel.name);
                    //累加总宽度
                    p._totalColWidth += colModel.width;
                }
                //需要格式化的字段
                if (colModel.formatter) {
                    p._formatterMap[colModel.name] = colModel.formatter;
                }
            }
            //算出每个列的宽度比例
            for (i = 0; i < p.view.colModels.length; i++) {
                colModel = p.view.colModels[i];
                if (colModel.isHide !== true) {
                    //列宽比例 = (配置列宽/总列宽)
                    colModel._widthRatio = (colModel.width / p._totalColWidth);
                }
            }
            //高度
            if (p.view.height === 0) {
                //自动高度 并且 指定了固定下拉区域
                if (p.view.autoHeightPadding > -1 && p.view.dropDownContainer) {
                    p.view.height = $.oui.utils.element.getHeightToBottom(p.view.dropDownContainer, p.view.autoHeightPadding);
                }
            }
        },

        /**
         * 设置选中行效果
         * @param $dataGridTrs
         * @private
         */
        _setSelectedRowEffect = function ($dataGridTrs) {
            var _thisElm = this, p = _thisElm.p, $focusRowTr, $dataGridParent, totalHeight = 0;
            //先去除所有背景色
            $dataGridTrs.css({"background-color": ""}).removeClass("selected");
            //选中
            $focusRowTr = $($dataGridTrs[p._focusRowIndex]);
            $focusRowTr.addClass("selected");
            //
            $dataGridParent = $("#" + getDataGridId.call(_thisElm)).parent();
            //总高度
            $($dataGridTrs).each(function (index, elm) {
                if (p._focusRowIndex > index) {
                    totalHeight += $(elm).height();
                }
            });

            //驱动 数据网格所在下拉，滚动条滚动
            $dataGridParent[0].scrollTop = totalHeight;
        },

        /**
         * 显示当前选中信息到可视范围
         */
        _showCurrentSelectedRow = function () {
            var _thisElm = this, p = _thisElm.p,
                rowIdAttrName = getDataGridRowIdAttributeName.call(_thisElm),
                $dataGridTrs = p._dataPanel.find("tr[" + rowIdAttrName + "]"),
                focusRowIndex;

            if (p._focusRowIndex < 0) {
                focusRowIndex = -1;
                if ($dataGridTrs && $dataGridTrs.length > 0) {
                    for (var i = 0; i < $dataGridTrs.length; i++) {
                        if ($dataGridTrs[i].innerText === _thisElm.value) {
                            focusRowIndex = i;
                            break;
                        }
                    }
                }
                p._focusRowIndex = focusRowIndex;
            }
            _setSelectedRowEffect.call(_thisElm, $dataGridTrs);
        },

        /**
         * 格式化入口
         * @param store
         * @returns {*}
         * @private
         */
        _format = function (store) {
            var _thisElm = this, p = _thisElm.p,
                dataStore = $.extend(true, {}, store),
                records = dataStore[p.async.pageFieldDefine.records],
                rowData, i,
                fieldName, formatterName;
            //
            if ($.isEmptyObject(p._formatterMap)) {
                return store;
            }
            //
            if (dataStore && records && records.length > 0) {
                for (i = 0; i < records.length; i++) {
                    rowData = records[i];
                    for (fieldName in p._formatterMap) {
                        if (!p._formatterMap.hasOwnProperty(fieldName)) {
                            continue;
                        }
                        //取到格式化方法名
                        formatterName = p._formatterMap[fieldName]["name"];
                        if (formatterName === "date") {
                            //格式化数据
                            rowData[fieldName] = _formatDate.call(_thisElm, fieldName, rowData[fieldName], p._formatterMap[fieldName]);
                        }
                        if (formatterName === "currency") {
                            rowData[fieldName] = _formatCurrency.call(_thisElm, fieldName, rowData[fieldName], p._formatterMap[fieldName]);
                        }
                        if (formatterName === "checkbox") {
                            rowData[fieldName] = _formatCheckbox.call(_thisElm, fieldName, rowData[fieldName], p._formatterMap[fieldName]);
                        }
                    }
                }
            }
            return dataStore;
        },

        /**
         *
         * @param fieldName
         * @param fieldValue
         * @param formatConfig
         * @returns {*}
         * @private
         */
        _formatDate = function (fieldName, fieldValue, formatConfig) {
            return $.oui.utils.date.format(new Date(fieldValue), (formatConfig.dateFormat || "yyyy-MM-dd"));
        },

        /**
         * 格式化数值
         * @param fieldName
         * @param fieldValue
         * @param formatConfig
         * @returns {*}
         * @private
         */
        _formatCurrency = function (fieldName, fieldValue, formatConfig) {
            return $.oui.utils.number.currency(fieldValue, formatConfig.decimalPlaces,
                formatConfig.thousandsSeparator, formatConfig.decimalSeparator,
                formatConfig.prefix, formatConfig.suffix);
        },

        /**
         * 格式化复选框
         * @param fieldName
         * @param fieldValue
         * @param formatConfig
         * @returns {*}
         * @private
         */
        _formatCheckbox = function (fieldName, fieldValue, formatConfig) {
            return "<input type='checkbox' disabled " + (fieldValue == '1' ? "checked='checked'" : +"") + ">";
        },

        _printInfoLog = function (msg) {
            if (console) {
                if (console.hasOwnProperty("info")) {
                    console.info(msg);
                } else if (console.hasOwnProperty("log")) {
                    console.log(msg);
                }
            }
        },

        _printWarnLog = function (msg) {
            if (console) {
                if (console.hasOwnProperty("warn")) {
                    console.warn(msg);
                } else if (console.hasOwnProperty("log")) {
                    console.log(msg);
                }
            }
        },

        _encode = function (value) {
            return !value ? value : String(value).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
        };

})(jQuery);


var AutoComboboxUtil = AutoComboboxUtil || {

    /**
     * 渲染所有联想控件
     */
    render: function () {
        //
        $("input[componentType='auto-combobox']").each(function (index, inputEl) {
            var $inputEl = $(inputEl),
                componentConfigName = $inputEl.attr("componentConfig"),
                componentConfigArg = $inputEl.attr("componentConfigArg"),
                componentConfigExt = $inputEl.attr("componentConfigExt"),
                bindFill = $inputEl.attr('bindFill') ? JSON.parse($inputEl.attr('bindFill')) : {},
                componentConfig = {};
            if (!componentConfig) {
                return;
            }
            if ($.isFunction(AutoComboboxUtil[componentConfigName]) && componentConfigArg && $.isArray($.parseJSON(componentConfigArg))) {
                componentConfig = AutoComboboxUtil[componentConfigName].apply(AutoComboboxUtil, $.parseJSON(componentConfigArg));
            } else if ($.isFunction(AutoComboboxUtil[componentConfigName])) {
                componentConfig = AutoComboboxUtil[componentConfigName].call(AutoComboboxUtil);
            } else if ($.isPlainObject(AutoComboboxUtil[componentConfigName])) {
                componentConfig = AutoComboboxUtil[componentConfigName];
            }
            if ($.isFunction(eval(componentConfigExt))) {
                componentConfig = $.extend(true, {}, componentConfig, {view: {"bindFill": bindFill}}, eval(componentConfigExt)());
            } else {
                componentConfig = $.extend(true, {}, componentConfig, {view: {"bindFill": bindFill}}, eval(componentConfigExt));
            }
            //渲染控件
            $inputEl.attr("autocomplete", "off");
            $inputEl.AutoCombobox(componentConfig);
        });
        $("[componentType='auto-combobox-trigger']").each(function (index, elm) {
            var $elm = $(elm),
                triggerElementId = $elm.attr("triggerElementId");
            if (triggerElementId) {
                $elm.bind("click", function (event) {
                    $("#" + triggerElementId).AutoCombobox("triggerAction", event);
                });
            } else {
                alert("没有配置triggerElementId");
            }
        });
    }

};