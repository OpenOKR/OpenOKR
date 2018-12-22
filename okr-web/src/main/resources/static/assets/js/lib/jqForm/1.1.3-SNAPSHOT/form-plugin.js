/*
 * 商集唯你 研发中心前端架构组 自主研发。
 * 2015-11-18 之前未做版本管理，都默认为1.0.0。
 * 2015-11-18 开始为1.1.0。
 * 版本说明请阅读readme.txt
 */
;
(function ($) {
    "use strict";
    /** core ******************************************************************** */
    $.jForm = $.jForm || {};
    // 默认配置
    var defaults = {
        view: {
            focusSelectText: true//是否聚焦到 表单控件后，就全选中文本
        },
        //回调
        callback: {
            /**
             * 数据设置到表单前事件,必须返回boolean
             * 如:
             * beforeSetValue:function(formData){
             *      if(formData.age>18){
             *          //设置到表单前改变数据
             *          formData.adult = true;
             *          formData.people = "成年人";
             *          return true;
             *      }else{
             *          //小于18的值不能被设置
             *          alert("不能设置");
             *          return false;
             *      }
             * }
             */
            beforeSetValue: null,

            /**
             * 数据设置到表单后事件
             * afterSetValue:function(formData){
             *      var $form = $(this);
             *      //设置到表单后改变数据
             *      $form.find('[name="age"]').val(18);
             *      $form.find('[name="people"]').val("成年人");
             *      //或者其它操作
             *      $form.find('[name="field"]').prop("disabled",true);
             * }
             */
            afterSetValue: null,

            /**
             * 获取表单数据 后事件
             * afterGetValue:function(formData){
             *      if(formData.age>18){
             *          //设置到表单前改变数据
             *          formData.adult = true;
             *          formData.people = "成年人";
             *      }else{
             *          formData.adult = false;
             *          formData.people = "未成年人";
             *      }
             * }
             */
            afterGetValue: null,

            /**
             * 禁用元素后事件
             * afterDisableElement:function(configArr){
             *      //例：configArr 可以是 [{fieldName:"*"}] 或 [{fieldName:"field1"},{fieldName:"field2"}]
             *
             * }
             */
            afterDisableElement: null,

            /**
             * 激活元素后事件
             * afterEnableElement:function(configArr){
             *      //例：configArr 可以是 [{fieldName:"*"}] 或 [{fieldName:"field1"},{fieldName:"field2"}]
             *
             * }
             */
            afterEnableElement: null,

            /**
             * 点击后事件
             * afterClick:function(event,elementName,formData){
             *      //event事件对象
             *      //elementName 点击在表单内的元素名
             *      //formData 整个表单的数据(显示在表单上的 和 内存的)
             *      alert("点击的元素属性名:"+elementName)
             * }
             */
            afterClick: null,

            /**
             * 聚焦后事件
             * afterFocus:function(event,elementName,formData){
             *      //event事件对象
             *      //elementName 聚焦在表单内的元素名
             *      //formData 整个表单的数据(显示在表单上的 和 内存的)
             *      alert("聚焦的元素属性名:"+elementName)
             * }
             */
            afterFocus: null,

            /**
             * 失焦后事件
             * afterBlur:function(event,elementName,formData){
             *      //event事件对象
             *      //elementName 失焦在表单内的元素名
             *      //formData 整个表单的数据(显示在表单上的 和 内存的)
             *      alert("失焦的元素属性名:"+elementName)
             * }
             */
            afterBlur: null,

            /**
             * 按键按下事件
             * afterKeyUp:function(event,elementName,formData){
             *      //event事件对象
             *      //elementName 按键弹起在表单内的元素名
             *      //formData 整个表单的数据(显示在表单上的 和 内存的)
             *      alert("按键弹起的元素属性名:"+elementName)
             * }
             */
            afterKeyUp: null,

            /**
             * 按键弹起后事件
             * afterKeyDown:function(event,elementName,formData){
             *      //event事件对象
             *      //elementName 按键弹起在表单内的元素名
             *      //formData 整个表单的数据(显示在表单上的 和 内存的)
             *      alert("按键弹起的元素属性名:"+elementName)
             * }
             */
            afterKeyDown: null,

            /**
             * <select>改变值后事件
             * afterChange:function(event,elementName,formData){
             *      //event事件对象
             *      //elementName <select>改变值后在表单内的元素名
             *      //formData 整个表单的数据(显示在表单上的 和 内存的)
             *      alert("<select>改变值后的元素属性名:"+elementName)
             * }
             */
            afterChange: null,

            /**
             * 表单重置后事件
             * afterReset:function(){
             *      // $("#id").jqForm("reset");才会触发
             * }
             */
            afterReset: null
        },
        /**
         * 格式化
         *例：[
         //单据日期
         {fieldName: 'billDate', format: 'date'},
         //现开票
         {fieldName: 'isImmediateSettle', format: 'singleCheckbox', dataMap: {'checked': '1', 'unchecked': '0'}},
         //现开票
         {fieldName: 'isInitialization', format: 'singleCheckbox', dataMap: {'checked': '1', 'unchecked': '0'}},
         //发票日期
         {fieldName: 'invoiceDate', format: 'date'},
         //发票类型{"0": "发票", "1": "收据"}
         {fieldName: 'invoiceType', format: 'textValue', dataMap: {'0': '发票', '1': '收据'}}
         ]
         */
        formatter: [],

        /** 私有属性 */
        //表单默认值存储
        _defaultFormDataStore: {},
        _defaultFormData: null,
        //表单数据存储
        _formDataStore: {},
        //忽略赋值的字段
        _ignoreSetValueFiledNames: [],
        //判断是否设置过值
        _isSetValue: false
    };

    $.fn.jqForm = function (config) {
        if (typeof config === 'string') {
            var fn = $.jForm.getAccessor($.jForm, config);
            if (!fn) {
                alert("$.jForm - method not found:" + config);
                return;
            }
            var args = $.makeArray(arguments).slice(1);
            return fn.apply(this, args);
        }
        //解决不用初始化 $("#id").jqForm({})，可以直接 $("#id").jqForm("reset") 调用
        config = $.extend(true, {}, defaults, config);
        return this.each(function () {
            /**
             * 防止重复渲染
             */
            if (this.renderCompleted) {
                return;
            }
            var _formElm = this;
            /**
             * 判定非form 控件的渲染
             */
            if ($(_formElm).is("form") !== true) {
                alert("Non(form)controls can't initialize!");
                return;
            }
            /**
             * 配置继承
             */
            _formElm.p = $.extend(true, {
                id: _formElm.id
            }, $.jForm, defaults, config);
            //事件绑定
            _bindEvent.call(_formElm);
            // 渲染完成
            _formElm.renderCompleted = true;
        });
    };
    /** base begin ******************************************************************** */

    $.extend($.jForm, {
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

        /** 接口方法 begin ******************************************************************** */

        /**
         * 设置表单默认值
         * @param dataObj （不为空）数据对象JSON的{}，格式数据
         * @param callback （可空）回调function对象
         * @returns {*}
         */
        setDefaultValue: function (dataObj, callback) {
            return this.each(function () {
                var _formElm = this, p = _formElm.p, $form = $(_formElm);
                //
                if (dataObj == null || !$.isPlainObject(dataObj)) {
                    //throw new Error('表单数据为空或不是JSON对象！');
                    if (console && console.hasOwnProperty("info")) {
                        console.info("Form data is empty or not JSON object！")
                    }
                    return;
                }
                //强制重置表单
                $form.jqForm("reset", true);
                //默认表单数据存储
                p._defaultFormDataStore = $.extend(true, {}, dataObj);
                //表单数据存储
                p._formDataStore = $.extend(true, {}, dataObj);
                //回调
                if ($.isFunction(p.callback.beforeSetValue)) {
                    if (p.callback.beforeSetValue.call(_formElm, p._formDataStore) !== true) {
                        return;
                    }
                }
                //格式化数据
                _format.call(_formElm, p._formDataStore);
                //对表单控件，逐个赋值
                _setFormElementValue.call(_formElm, p._formDataStore);
                //回调 优先最低
                if ($.isFunction(p.callback.afterSetValue)) {
                    p.callback.afterSetValue.call(_formElm, p._formDataStore);
                }
                //回调 优先最高
                if (callback && $.isFunction(callback)) {
                    callback.apply(_formElm, [p._formDataStore]);
                }
                //暂时采用序列化表单数据，进行比对是否脏数据
                p._defaultFormData = $form.jqForm("getValue");
            });
        },

        /**
         * 设置表单值
         * @param dataObj （不为空）数据对象JSON的{}，格式数据
         * @param callback （可空）回调function对象
         * @returns {*}
         */
        setValue: function (dataObj, callback) {
            return this.each(function () {
                var _formElm = this, $form = $(_formElm), p = _formElm.p, currentFormData;
                //
                if (dataObj == null || !$.isPlainObject(dataObj)) {
                    //throw new Error('表单数据为空或不是JSON对象！');
                    if (console && console.hasOwnProperty("info")) {
                        console.info("Form data is empty or not JSON object!")
                    }
                    return;
                }
                //取出当前表单数据
                currentFormData = $form.jqForm("getValue");
                //与当前 表单数据存储 合并
                p._formDataStore = $.extend(true, {}, currentFormData, dataObj);
                //回调
                if ($.isFunction(p.callback.beforeSetValue)) {
                    if (p.callback.beforeSetValue.call(_formElm, p._formDataStore) !== true) {
                        return;
                    }
                }
                //格式化数据
                _format.call(_formElm, p._formDataStore);
                //对表单控件，逐个赋值
                _setFormElementValue.call(_formElm, p._formDataStore);
                //回调 优先最低
                if ($.isFunction(p.callback.afterSetValue)) {
                    p.callback.afterSetValue.call(_formElm, p._formDataStore);
                }
                //回调 优先最高
                if (callback && $.isFunction(callback)) {
                    callback.apply(_formElm, [p._formDataStore]);
                }
            });
        },

        /**
         * 获取表单值
         * @param isUnFormat （可空）是否反格式化
         * @param callback （可空）回调function对象
         * @returns {{}}
         */
        getValue: function (isUnFormat, callback) {
            var formData = {};
            this.each(function () {
                var _formElm = this, p = _formElm.p, value = null, name = null, $elm = null, $multiEle = null;
                //对表单控件，逐个取值
                $("[name]", _formElm).each(function () {
                    $elm = $(this);
                    name = $elm.attr("name");
                    $multiEle = $("[name='" + $elm.attr("name") + "']", _formElm);
                    //
                    if ($elm.is("input[type='radio']")) {
                        value = $("input[name='" + name + "']:checked", _formElm).val();
                        value = value === undefined ? null : value;
                    } else if ($elm.is("input[type='checkbox']") && $multiEle.length === 1) {
                        value = $("input[name='" + name + "']:checked", _formElm).val();
                        value = value === undefined ? null : value;
                    } else if ($multiEle.length > 1) {
                        //checkbox
                        value = [];
                        $multiEle.each(function () {
                            var $checkboxItemElm = $(this);
                            if ($checkboxItemElm.is("input[type='checkbox']")) {
                                if ($checkboxItemElm.prop("checked")) {
                                    value.push($.trim($checkboxItemElm.val()) === "" ? null : $checkboxItemElm.val());
                                }
                            }
                        });
                    }
                    else if ($elm.is("input") || $elm.is("textarea") || $elm.is("select")) {
                        value = $elm.val();
                    } else {
                        value = $elm.text();
                    }
                    //formData[name] = $.trim(value) === "" ? null : value;
                    _setValueByName(formData, name, value);
                    $multiEle = null;
                });
                if (isUnFormat === false) {
                } else {
                    //反格式化数据
                    _unFormat.call(_formElm, formData);
                }
                //表单上的数据 与 表单数据存储 合并
                formData = $.extend({}, p._formDataStore, formData);
                //回调
                if ($.isFunction(p.callback.afterGetValue)) {
                    p.callback.afterGetValue.call(_formElm, formData);
                }
                //回调
                if (callback && $.isFunction(callback)) {
                    callback.apply(_formElm, [formData]);
                }
            });
            return formData;
        },

        /**
         * 表单数据是否为脏数据（以setDefaultValue接口赋的默认值为参考，
         *  调用setValue接口的赋值动作，都认为改过表单数据）
         * @param ignoreDisable 忽略禁用的控件（可空，默认为true）
         * @returns {boolean}
         */
        isDirty: function (ignoreDisable) {
            if (this.length === 0) {
                return void(0);
            }

            var _formElm = this[0],
                p = _formElm.p,
                i = 0,
                formKeys, defaultValuesKeys,
                formData = {};

            //判断表单是否被修改过
            if (p._defaultFormData) {
                formData = $(_formElm).jqForm("getValue");

                if (!!ignoreDisable === false) {
                    formKeys = Object.keys(formData).sort();
                    defaultValuesKeys = Object.keys(p._defaultFormData).sort();

                    // 判断Key是否相同，不同则认为修改过
                    if (defaultValuesKeys.join(",") !== formKeys.join(",")) {
                        return true;
                    }

                    for (i = 0; i < formKeys.length; i++) {
                        if (formData[formKeys[i]] !== p._defaultFormData[formKeys[i]]) {
                            return true;
                        }
                    }

                    return false;
                }

                return formData && (JSON.stringify(formData) !== JSON.stringify(p._defaultFormData));
            }

            return !!p._isSetValue;
        },

        /**
         * 重置表单
         * @param forceReset （可空）是否强制重置
         * @returns {boolean}
         */
        reset: function (forceReset) {
            var $elm, _forceResetFormFunc = function (_formElm) {
                //原生重置表单
                _formElm.reset();
                //只对非 表单控件清空数据
                $("[name]", _formElm).each(function (index, elm) {
                    $elm = $(elm);
                    if ($elm.is("input") || $elm.is("textarea") || $elm.is("select")) {
                        //$elm.val("");
                        if ($elm.is("input[type='hidden']") === true) {
                            //TODO:不妥，控件渲染前的值也会被清空。
                            $elm.val("");
                        }
                    } else {
                        $elm.text("");
                    }
                });

            };
            return this.each(function () {
                var _formElm = this, p = _formElm.p;
                if (forceReset === true) {
                    p._defaultFormDataStore = {};
                    p._defaultFormData = null;
                    p._formDataStore = {};
                    p._isSetValue = false;
                    _forceResetFormFunc(_formElm);
                } else if (p._defaultFormDataStore && !$.isEmptyObject(p._defaultFormDataStore)) {
                    //将默认值的存储数据，回填到表单上
                    $(_formElm).jqForm("setDefaultValue", p._defaultFormDataStore);
                } else {
                    p._defaultFormDataStore = {};
                    p._defaultFormData = null;
                    p._formDataStore = {};
                    p._isSetValue = false;
                    _forceResetFormFunc(_formElm);
                }
                //回调
                if ($.isFunction(p.callback.afterReset)) {
                    p.callback.afterReset.call(_formElm);
                }
            });
        },

        /**
         * 禁用表单元素
         * @param configArr （非空）例：[{fieldName:"*"}] 或 [{fieldName:"field1"},{fieldName:"field2"}]
         * @param callback （可空）回调方法
         * @returns {*}
         */
        disableElement: function (configArr, callback) {
            if (!$.isArray(configArr)) {
                //throw new Error(configArr + '，必须是数组数据！');
                if (console && console.hasOwnProperty("info")) {
                    console.info(configArr + '，Must be an array！')
                }
                return;
            }
            return this.each(function () {
                //{fieldName:"*"},
                var _formElm = this, p = _formElm.p, $form = $(_formElm), i, config = {}, $formElm;
                for (i = 0; i < configArr.length; i++) {
                    config = configArr[i];
                    if (!config.fieldName) {
                        continue;
                    }
                    if (config.fieldName === "*") {
                        $form.find("[name]").each(function (index, formElm) {
                            $formElm = $(formElm);
                            //非 <input type="hidden"> 才可以禁用
                            if (!$formElm.is("[type='hidden']")) {
                                $formElm.attr({"disabled": "disabled"});
                            }
                        });
                    } else {
                        $form.find("[name='" + config.fieldName + "']").each(function (index, formElm) {
                            $formElm = $(formElm);
                            //非 <input type="hidden"> 才可以禁用
                            if (!$formElm.is("[type='hidden']")) {
                                $formElm.attr({"disabled": "disabled"});
                            }
                        });
                    }
                }
                //回调
                if ($.isFunction(p.callback.afterDisableElement)) {
                    p.callback.afterDisableElement.call(_formElm, configArr);
                }
                //回调
                if (callback && $.isFunction(callback)) {
                    callback.apply(_formElm, [configArr]);
                }
            });
        },

        /**
         * 启用表单元素
         * @param configArr （非空）例：[{fieldName:"*"}] 或 [{fieldName:"field1"},{fieldName:"field2"}]
         * @param callback （可空）回调方法
         * @returns {*}
         */
        enableElement: function (configArr, callback) {
            if (!$.isArray(configArr)) {
                //throw new Error(configArr + '，必须是数组数据！');
                if (console && console.hasOwnProperty("info")) {
                    console.info(configArr + '，Must be an array！')
                }
                return;
            }
            return this.each(function () {
                //[{fieldName:"*"}]
                var _formElm = this, p = _formElm.p, $form = $(_formElm), i, config = {};
                for (i = 0; i < configArr.length; i++) {
                    config = configArr[i];
                    if (!config.fieldName) {
                        continue;
                    }
                    if (config.fieldName === "*") {
                        $form.find("[name]").removeAttr("disabled");
                    } else {
                        $form.find("[name=" + config.fieldName + "]").removeAttr("disabled");
                    }
                }
                //回调
                if ($.isFunction(p.callback.afterEnableElement)) {
                    p.callback.afterEnableElement.call(_formElm, configArr);
                }
                //回调
                if (callback && $.isFunction(callback)) {
                    callback.apply(_formElm, [configArr]);
                }
            });
        },

        /**
         * 焦点定位到表单元素
         * @param name
         * @returns {*}
         */
        focusToElement: function (name) {
            return this.each(function () {
                var _formElm = this;
                $("[name='" + name + "']", _formElm).focus();
            });
        },

        /**
         * 获取允许聚焦的 的所有元素（）
         * @returns {*}
         */
        allowFocusElement: function () {
            var allowFocusElms = [];
            this.each(function () {
                var _formElm = this;
                $('input:enabled:not(:checkbox):not(:radio):not(:button):not([readonly="readonly"]):not(:hidden)', _formElm).each(function () {
                    allowFocusElms.push(this);
                });
                allowFocusElms = allowFocusElms.concat();
                $('select:enabled:not([readonly="readonly"]):not(:hidden)', _formElm).each(function () {
                    allowFocusElms.push(this);
                });
                $('textarea:enabled:not([readonly="readonly"]):not(:hidden)', _formElm).each(function () {
                    allowFocusElms.push(this);
                });
            });
            return allowFocusElms;
        },

        /**
         * 聚焦 第一个允许聚焦的元素（）
         * @returns {*}
         */
        focusFirstElement: function () {
            return this.each(function () {
                var _formElm = this, $form = $(_formElm);
                //
                var allowFocusElement = $form.jqForm("allowFocusElement");
                if (allowFocusElement && allowFocusElement.length > 0) {
                    $form.jqForm("focusToElement", $(allowFocusElement[0]).attr("name"));
                }
            });
        }

        /** 接口方法 end ******************************************************************** */

    });

    var
        /**
         * 事件绑定
         */
        _bindEvent = function () {
            $(this).each(function () {
                var _formElm = this, $form = $(_formElm), p = _formElm.p, formData;
                //点击事件
                if (p.callback.afterClick) {
                    $form.find("[name]").off("click.jqForm");
                    $form.find("[name]").on("click.jqForm", function (event) {
                        if ($.isFunction(p.callback.afterClick)) {
                            formData = $form.jqForm("getValue");
                            p.callback.afterClick.call(_formElm, event, $(this).attr("name"), formData);
                        }
                    });
                }
                //聚焦事件
                if (p.callback.afterFocus || p.view.focusSelectText) {
                    $form.find("[name]").off("focus.jqForm");
                    $form.find("[name]").on("focus.jqForm", function (event) {
                        var $elm = $(this);
                        //是否聚焦后全选文本，只支持文本输入框(非禁用非只读)
                        if (p.view.focusSelectText && $elm.is("input:text") && !$elm.prop("disabled") && !$elm.prop("readonly")) {
                            var timeObj = window.setTimeout(function () {
                                //如果当前元素失焦了,就不全选文本
                                if ($elm.is(":focus")) {
                                    //全选文本
                                    $elm.select();
                                }
                                window.clearTimeout(timeObj);
                            }, 1);
                        }
                        if ($.isFunction(p.callback.afterFocus)) {
                            formData = $form.jqForm("getValue");
                            p.callback.afterFocus.call(_formElm, event, $elm.attr("name"), formData);
                        }
                    });
                }
                //失焦事件
                if (p.callback.afterBlur) {
                    $form.find("[name]").off("blur.jqForm");
                    $form.find("[name]").on("blur.jqForm", function (event) {
                        if ($.isFunction(p.callback.afterBlur)) {
                            formData = $form.jqForm("getValue");
                            p.callback.afterBlur.call(_formElm, event, $(this).attr("name"), formData);
                        }
                    });
                }
                //按下键弹起事件
                if (p.callback.afterKeyUp) {
                    $form.find("[name]").off("keyup.jqForm");
                    $form.find("[name]").on("keyup.jqForm", function (event) {
                        if ($.isFunction(p.callback.afterKeyUp)) {
                            formData = $form.jqForm("getValue");
                            p.callback.afterKeyUp.call(_formElm, event, $(this).attr("name"), formData);
                        }
                    });
                }
                //按下键事件
                if (p.callback.afterKeyDown) {
                    $form.find("[name]").off("keydown.jqForm");
                    $form.find("[name]").on("keydown.jqForm", function (event) {
                        if ($.isFunction(p.callback.afterKeyDown)) {
                            formData = $form.jqForm("getValue");
                            p.callback.afterKeyDown.call(_formElm, event, $(this).attr("name"), formData);
                        }
                    });
                }

                //
                if (p.callback.afterChange) {
                    $form.find("select[name]").off("change.jqForm");
                    $form.find("select[name]").on("change.jqForm", function (event) {
                        if ($.isFunction(p.callback.afterChange)) {
                            formData = $form.jqForm("getValue");
                            p.callback.afterChange.call(_formElm, event, $(this).attr("name"), formData);
                        }
                    });
                }

            });
        },

        /**
         * 表单元素的
         * <input name = "obj1.obj2.obj3.name">
         * 要在对象中找到 123
         dataObj = {
                    obj1: {
                        obj2: {
                            obj3: {
                                name: 123
                            }
                        }
                    }
                };

         * @param dataObj 数据对象
         * @param name 属性名
         * @private
         */
        _getValueByName = function (dataObj, name) {
            var retValue;
            if (name.indexOf('.') > -1) {
                var subObjName = name.substring(0, name.indexOf('.'));
                //剩余的 obj2.obj3.name
                name = name.substring(name.indexOf('.') + 1, name.length);
                if (dataObj.hasOwnProperty(subObjName)) {
                    retValue = _getValueByName(dataObj[subObjName], name);
                } else {
                    retValue = undefined;
                }
            } else {
                retValue = dataObj[name];
            }
            return retValue;
        },

        /**
         * var obj = {name: 1}, name = "sub.subObj.code", value = "321";
         * 将值 value 设置到 obj，
         * 结果是 {"name":1,"sub":{"subObj":{"code":"321"}}};
         *
         * @param dataObj
         * @param name
         * @param value
         * @private
         */
        _setValueByName = function (dataObj, name, value) {
            if (name.indexOf('.') > -1) {
                var subObjName = name.substring(0, name.indexOf('.'));
                //剩余的 obj2.obj3.name
                name = name.substring(name.indexOf('.') + 1, name.length);
                dataObj[subObjName] = dataObj[subObjName] || {};
                //
                _setValueByName(dataObj[subObjName], name, value);
            } else {
                dataObj[name] = value;
            }
        },

        /**
         * 设置表单元素值
         * @param dataObj
         * @private
         */
        _setFormElementValue = function (dataObj) {
            return $(this).each(function () {
                var _formElm = this, p = _formElm.p, elms, $elm,
                    name, value, propertyValue, ec, i = 0, isIgnore = false;
                //设置过值
                p._isSetValue = true;
                //
                //for (fieldName in dataObj) {
                elms = $("[name]", _formElm);
                //对表单控件，逐个赋值
                for (ec = 0; ec < elms.length; ec++) {
                    $elm = $(elms[ec]);
                    name = $elm.attr("name");
                    //判断是否被忽略赋值
                    isIgnore = false;
                    if (name && name.length > 0) {
                        for (i = 0; i < p._ignoreSetValueFiledNames.length; i++) {
                            if (name === p._ignoreSetValueFiledNames[i]) {
                                isIgnore = true;
                                break;
                            }
                        }
                    }
                    if (isIgnore) continue;
                    //
                    propertyValue = _getValueByName(dataObj, name);
                    //
                    if ($elm.is("input[type='radio']")) {
                        $("input[name='" + name + "'][value='" + propertyValue + "']", _formElm).prop("checked", true);
                    } else if ($elm.is("input[type='checkbox']")) {
                        value = $.isArray(propertyValue) ? propertyValue : [propertyValue];
                        $("input[name='" + name + "']", _formElm).val(value);
                    } else if ($elm.is("input") || $elm.is("textarea") || $elm.is("select")) {
                        if (propertyValue !== null) {
                            $elm.val(propertyValue);
                        }
                    } else {
                        if (propertyValue !== null) {
                            $elm.text(propertyValue);
                        }
                    }
                }
                //}
            });
        },

        /**
         * 防重复，添加忽略设值的字段
         * @param fieldName
         * @returns {*}
         * @private
         */
        _putIgnoreSetValueFiledNames = function (fieldName) {
            return $(this).each(function () {
                var _formElm = this, p = _formElm.p, i, isExists = false;
                for (i = 0; i < p._ignoreSetValueFiledNames.length; i++) {
                    if (fieldName === p._ignoreSetValueFiledNames[i]) {
                        isExists = true;
                        break;
                    }
                }
                if (isExists === false) {
                    p._ignoreSetValueFiledNames.push(fieldName);
                }
            });
        },
        /**
         * 格式化数据
         * @param formData
         * @returns {*}
         * @private
         */
        _format = function (formData) {
            return $(this).each(function () {
                var _formElm = this, p = _formElm.p, i = 0, formatConfig = {}, fieldFormatText = '';
                if (p.formatter && p.formatter.length > 0) {
                    for (; i < p.formatter.length; i++) {
                        formatConfig = p.formatter[i];
                        if (formatConfig) {
                            if (formatConfig.format === 'date') {
                                fieldFormatText = _formatDate.call(_formElm, formatConfig.fieldName, formatConfig, _getValueByName(formData, formatConfig.fieldName));
                                _setValueByName(formData, formatConfig.fieldName, fieldFormatText);
                            }
                            if (formatConfig.format === 'textValue') {
                                fieldFormatText = _formatTextValue.call(_formElm, formatConfig.fieldName, formatConfig, _getValueByName(formData, formatConfig.fieldName));
                                _setValueByName(formData, formatConfig.fieldName, fieldFormatText);
                            }
                            if (formatConfig.format === 'singleCheckbox') {
                                fieldFormatText = _formatSingleCheckbox.call(_formElm, formatConfig.fieldName, formatConfig, _getValueByName(formData, formatConfig.fieldName));
                                _setValueByName(formData, formatConfig.fieldName, fieldFormatText);
                            }
                        }
                    }
                }
            });
        },

        /**
         * 反格式化数据
         * @returns {*}
         * @private
         */
        _unFormat = function (formData) {
            return $(this).each(function () {
                var _formElm = this, p = _formElm.p, i = 0, formatConfig = {}, fieldFormatValue = '';
                if (p.formatter && p.formatter.length > 0) {
                    for (; i < p.formatter.length; i++) {
                        formatConfig = p.formatter[i];
                        if (formatConfig) {
                            if (formatConfig.format === 'date') {
                                fieldFormatValue = _unFormatDate.call(_formElm, formatConfig.fieldName, formatConfig, _getValueByName(formData, formatConfig.fieldName));
                                _setValueByName(formData, formatConfig.fieldName, fieldFormatValue);
                            }
                            if (formatConfig.format === 'textValue') {
                                fieldFormatValue = _unFormatTextValue.call(_formElm, formatConfig.fieldName, formatConfig, _getValueByName(formData, formatConfig.fieldName));
                                _setValueByName(formData, formatConfig.fieldName, fieldFormatValue);
                            }
                            if (formatConfig.format === 'singleCheckbox') {
                                fieldFormatValue = _unFormatSingleCheckbox.call(_formElm, formatConfig.fieldName, formatConfig, _getValueByName(formData, formatConfig.fieldName));
                                _setValueByName(formData, formatConfig.fieldName, fieldFormatValue);
                            }
                        }
                    }
                }
            });
        },

        /**
         * 格式化日期（日期数值 转 日期文本格式）
         * @param fieldName
         * @param formatConfig
         * @param dataValue
         * @returns {number}
         * @private
         */
        _formatDate = function (fieldName, formatConfig, dataValue) {
            var fmt = formatConfig.dateFormat || "yyyy-MM-dd",
                formatFunc = function (fmt, longDate) {
                    var date = new Date(longDate),
                        o = {
                            "M+": date.getMonth() + 1, // 月份
                            "d+": date.getDate(), // 日
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
                            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                        }
                    }
                    return fmt;
                };
            if (dataValue) {
                return formatFunc(fmt, Number(dataValue));
            } else {
                return dataValue;
            }
        },

        /**
         * 反格式化日期（日期文本格式 转 日期数值）
         * @param fieldName
         * @param formatConfig
         * @param dataValue
         * @returns {number}
         * @private
         */
        _unFormatDate = function (fieldName, formatConfig, dataValue) {

            var _formElm = this,
                fmt = formatConfig.dateFormat || "yyyy-MM-dd";
            /*   var createDate = function (dateStr) {
             var regThree = /^\D*(\d{2,4})\D+(\d{1,2})\D+(\d{1,2})\D*$/,
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
             return new Date();
             else
             return date;
             };*/
            if (dataValue) {
                return Date.parse(_parseDate.call(_formElm, dataValue, fmt));
                //return Date.parse(createDate(dataValue));
            } else {
                return dataValue;
            }
        },

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
        _parseDate = function (value, format) {
            if (!value) return value;
            if (value instanceof Date) return value;
            var parts, val, part, format_fun_map,
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
            if (parts.length === format.parts.length) {
                for (var i = 0, cnt = format.parts.length; i < cnt; i++) {
                    val = parseInt(parts[i], 10);
                    part = format.parts[i];
                    format_fun_map[format.parts[i]](val);
                }
            }

            function _parseFormat(format) {
                //yyyy-MM-dd hh:mm:ss
                var format_part_pattern = /hh?|HH?|ss?|dd?|DD?|mm?|MM?|yy(?:yy)?/g,
                    // User as the delimiter of format
                    separators = format.replace(format_part_pattern, '\0').split('\0'),
                    parts = format.match(format_part_pattern);
                if (!separators || !separators.length || !parts || parts.length === 0) {
                    throw new Error('Invalid date format.');
                }
                return {separators: separators, parts: parts};
            }

            return new Date(dateJSONObj.yyyy, dateJSONObj.M, dateJSONObj.d,
                dateJSONObj.h, dateJSONObj.m, dateJSONObj.s);
        },

        /**
         *
         * @param fieldName
         * @param formatConfig
         * @param dataValue
         * @returns {*}
         * @private
         */
        _formatTextValue = function (fieldName, formatConfig, dataValue) {
            var dataMap = formatConfig.dataMap;
            if (dataMap) {
                return dataMap[dataValue];
            } else {
                throw new Error('dataMap no configuration!');
            }
        },

        /**
         *
         * @param fieldName
         * @param formatConfig
         * @param elmText
         * @returns {*}
         * @private
         */
        _unFormatTextValue = function (fieldName, formatConfig, elmText) {
            var dataMap = formatConfig.dataMap, dataKey, realValue = null;
            for (dataKey in dataMap) {
                if (dataMap[dataKey] === elmText) {
                    realValue = dataKey;
                    break;
                }
            }
            return realValue;
        },

        /**
         *
         * @param fieldName
         * @param formatConfig
         * @param dataValue
         * @returns {*}
         * @private
         */
        _formatSingleCheckbox = function (fieldName, formatConfig, dataValue) {
            $(this).each(function () {
                var _formElm = this,
                    $checkbox = $('input[name="' + fieldName + '"]', _formElm),
                    dataMap = formatConfig.dataMap;
                if ($checkbox.length === 0) {
                    return;
                }
                if (!$checkbox.is("input:checkbox")) {
                    throw new Error(fieldName + ' non checkbox control!');
                }
                if (dataMap) {
                    //判断 选中 或 反选
                    if (dataMap["checked"] === dataValue) {
                        $checkbox.prop("checked", true);
                    } else {
                        $checkbox.prop("checked", false);
                    }
                    //当前字段 将被列为忽略赋值的字段
                    _putIgnoreSetValueFiledNames.call(_formElm, fieldName);
                } else {
                    throw new Error('dataMap no configuration!');
                }
            });
            //SingleCheckbox 的返回 还是原值
            return dataValue;
        },

        /**
         *
         * @param fieldName
         * @param formatConfig
         * @param dataText
         * @returns {*}
         * @private
         */
        _unFormatSingleCheckbox = function (fieldName, formatConfig, dataText) {
            var checkboxValue;
            $(this).each(function () {
                var _formElm = this, $checkbox = $('input[name="' + fieldName + '"]', _formElm),
                    dataMap = formatConfig.dataMap, isChecked = false;
                if ($checkbox.length > 0) {
                    isChecked = $checkbox.prop("checked");
                    checkboxValue = isChecked === true ? dataMap["checked"] : dataMap["unchecked"];
                }
            });
            return checkboxValue;
        };
})
(jQuery);


var validateUtil = validateUtil || {
    /**
     * 正则表达式
     */
    reqExt: {
        /**
         * 邮件格式
         */
        email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
        /**
         * 电话
         */
        isPhone: /^([0-9]{3,4}-)?[0-9]{7,8}$/,
        /**
         * 手机
         */
        isMob: /^((\+?86)|(\(\+86\))|(0)|(\+?38)|(\(\+38\)))?(1[3|4|5|7|8][0-9]\d{8})$/,
        /**
         * 同时验证手机和电话
         */
        isMobOrPhone: /^(([0-9]{3,4}-)?[0-9]{7,8})|(((\+?86)|(\(\+86\))|(0)|(\+?38)|(\(\+38\)))?(1[3|4|5|7|8][0-9]\d{8}))$/,
        /**
         * 金额
         */
        isMoney: /^\-?[1-9]+\d*(\.\d+)?$/,

        /**
         * 判断是否含有中文
         */
        isZhcnCorp: /^(([\s\S]*)[\u4E00-\u9FFF]+([\s\S]*))+$/
    },

    /**
     * 验证数据
     * @param datas 验证的数据 数组 或 对象
     * @param validateRules 验证规则{}
     * @param isShowRowNum 是否显示行号true/false
     * @returns {{}} 验证信息对象，例如：{name:["不能为空"],price:["不能为空","必须大于1","必须小余100"]}
     *
     *
     * {
                name: {label: '名称'},
                code: {label: '编码'},
                field1: {label: '字段1',required:false},
                field2: {label: '字段2',minLength:6,maxLength:18},
                field3: {label: '字段3',maxValue:999.99,minValue:0.00:},
                field4: {label: '字段4',reqExp:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,reqExpMsg:"邮件格式不正确":},
                field5: {
                    label: '确认新密码', required: true, minLength: 6,
                    customFunc: function (buildMsgFunc) {
                        var $form = pageObj.getUserForm(),
                            formData = $form.jqForm("getValue");
                        if (formData.confirmNewPassword !== formData.newPassword) {
                            buildMsgFunc.call(this, "confirmNewPassword", "两次新密码输入不一致");
                        }
                    }
                }
         }
     *
     *
     */
    validateDatas: function (datas, validateRules, isShowRowNum) {
        //{fieldName:["不能为空"],fieldName:["不能为空","数字必须大小100","数字必须大于1"]}
        var tmpIsShowRowNum = isShowRowNum === true,
            validateMsgObj = {},
            buildMsgFunc = function (fieldName, _validateMsg) { //验证信息组装
                var msgArr = validateMsgObj[fieldName] ? validateMsgObj[fieldName] : [];
                if (_validateMsg) {
                    msgArr.push(_validateMsg);
                    validateMsgObj[fieldName] = msgArr;
                }
            };
        //如果 datas 不是数组，而是对象
        if (!$.isArray(datas) || $.isPlainObject(datas)) {
            datas = [datas];
        }
        //
        for (var i = 0; i < datas.length; i++) {
            var rowData = datas[i];
            //取出规则,逐个验证
            for (var fieldNameKey in validateRules) {
                if (!validateRules[fieldNameKey]) {
                    continue;
                }
                var validateRule = validateRules[fieldNameKey],
                    fieldValue = rowData[fieldNameKey],
                    validateMsg = "";
                //验证非空(required 不等于 false ，就认为是必填。大部分情况下 required=true，可以不用写)
                if (validateRule.required !== false) {
                    //避免fieldValue为数值0，校验错误
                    if ($.trim(fieldValue).length === 0) {
                        validateMsg = validateRule.label + " 不能为空";
                        if (tmpIsShowRowNum === true) {
                            validateMsg = "第" + (i + 1) + "行，" + validateMsg;
                        }
                        buildMsgFunc(fieldNameKey, validateMsg);
                    }
                }
                //验证最大值
                if (validateRule.maxValue) {
                    if (!isNaN(validateRule.maxValue)) {
                        if ($.isNumeric(fieldValue)) {
                            if (fieldValue > validateRule.maxValue) {
                                validateMsg = validateRule.label +
                                    (validateRule.maxValueMsg ? validateRule.maxValueMsg : " 不能大于" + validateRule.maxValue);
                                if (tmpIsShowRowNum === true) {
                                    validateMsg = "第" + (i + 1) + "行，" + validateMsg;
                                }
                                buildMsgFunc(fieldNameKey, validateMsg);
                            }
                        } else {
                            buildMsgFunc(fieldNameKey, validateRule.label + "，必须是数字");
                        }
                    } else {
                        throw Error("验证规则 maxValue 错误");
                    }
                }

                //验证最小值
                if (validateRule.minValue) {
                    if (!isNaN(validateRule.minValue)) {
                        if ($.isNumeric(fieldValue)) {
                            if (fieldValue < validateRule.minValue) {
                                validateMsg = validateRule.label + (validateRule.minValueMsg ? validateRule.minValueMsg : " 不能小于" + validateRule.minValue);
                                if (tmpIsShowRowNum === true) {
                                    validateMsg = "第" + (i + 1) + "行，" + validateMsg;
                                }
                                buildMsgFunc(fieldNameKey, validateMsg);
                            }
                        } else {
                            buildMsgFunc(fieldNameKey, validateRule.label + "，必须是数字");
                        }
                    } else {
                        throw Error("验证规则 minValue 错误，必须是数字");
                    }
                }

                //验证最小长度
                if (validateRule.minLength) {
                    if (!isNaN(validateRule.minLength)) {
                        validateMsg = validateRule.label + (validateRule.minLengthMsg ? validateRule.minLengthMsg : " 长度不能小于" + validateRule.minLength + "个字符！");
                        if (fieldValue != null && fieldValue !== undefined) {
                            if (fieldValue.length < validateRule.minLength) {
                                if (tmpIsShowRowNum === true) {
                                    validateMsg = "第" + (i + 1) + "行，" + validateMsg;
                                }
                                buildMsgFunc(fieldNameKey, validateMsg);
                            }
                        } else {
                            buildMsgFunc(fieldNameKey, validateMsg);
                        }
                    } else {
                        throw Error("验证规则 minLength 错误，必须是数字！");
                    }
                }

                //验证最大长度
                if (validateRule.maxLength) {
                    if (!isNaN(validateRule.maxLength)) {
                        validateMsg = "";
                        if (fieldValue != null && fieldValue !== undefined) {
                            validateMsg = validateRule.label + (validateRule.maxLengthMsg ? validateRule.maxLengthMsg : " 长度不能大于" + validateRule.maxLength + "个字符！");
                            if (fieldValue.length > validateRule.maxLength) {
                                if (tmpIsShowRowNum === true) {
                                    validateMsg = "第" + (i + 1) + "行，" + validateMsg;
                                }
                                buildMsgFunc(fieldNameKey, validateMsg);
                            }
                        } else {
                            buildMsgFunc(fieldNameKey, validateMsg);
                        }
                    } else {
                        throw Error("验证规则 maxLength 错误，必须是数字！");
                    }
                }

                //正则表达式
                if (validateRule.reqExp) {
                    if (validateRule.reqExpMsg) {
                        validateMsg = validateRule.label + (validateRule.reqExpMsg ? validateRule.reqExpMsg : "");
                        if (fieldValue && fieldValue.match(validateRule.reqExp) == null) {
                            buildMsgFunc(fieldNameKey, validateMsg);
                        }
                    } else {
                        throw Error("验证规则表达式 reqExpMsg 错误！");
                    }
                }
                //日期比较
                if (validateRule.compareDate) {
                    var sedValue = rowData[validateRule.compareDate];
                    if (sedValue || fieldValue) {
                        if (!fieldValue || fieldValue > sedValue) {
                            validateMsg = validateRule.label + "开始日期必须小于结束日期！";
                            buildMsgFunc(fieldNameKey, validateMsg);
                        }
                    }
                }
                //自定义
                if (validateRule.customFunc) {
                    validateRule.customFunc.call(this, buildMsgFunc);
                }
            }
        }
        return validateMsgObj;
    },

    /**
     * 拼接验证提示信息
     * @param validateMsgObj 验证消息对象
     * @param msgMaxCount 消息最大显示数(非必填，默认：-1，显示全部消息)
     * @returns {string}
     */
    concatValidateMsg: function (validateMsgObj, msgMaxCount) {
        var _showMsg = "", fieldNameKey, msgCount = 0, msgMaxCount = msgMaxCount ? msgMaxCount : -1;
        for (fieldNameKey in validateMsgObj) {
            if (!validateMsgObj[fieldNameKey]) {
                continue;
            }
            var msgArr = validateMsgObj[fieldNameKey];
            if (msgCount >= msgMaxCount && msgMaxCount > 0) {
                _showMsg += "......<br/>";
                break;
            }
            if (msgArr && msgArr.length > 0) {
                for (var i = 0; i < msgArr.length; i++) {
                    _showMsg += msgArr[i] + "<br/>";
                    msgCount++;
                }
            }
        }
        return _showMsg;
    },

    /**
     * 验证数据
     * @param datas 验证的数据[]
     * @param validateRules 验证规则{}
     * @param isShowRowNum 是否显示行号true/false
     * @param msgMaxCount  消息最大显示数
     * @returns {string}
     */
    validate: function (datas, validateRules, isShowRowNum, msgMaxCount) {
        var validateMsgObj = validateUtil.validateDatas(datas, validateRules, isShowRowNum);
        return validateUtil.concatValidateMsg(validateMsgObj, msgMaxCount);
    },

    /**
     * 获取第一个不通过验证的 name
     * @param validateMsgObj 验证信息对象，例如：{name:["不能为空"],price:["不能为空","必须大于1","必须小余100"]}
     * @returns {string}
     */
    getFirstNoPassName: function (validateMsgObj) {
        var firstName = "", name;
        if (!$.isEmptyObject(validateMsgObj)) {
            for (name in validateMsgObj) {
                if (validateMsgObj[name] && name) {
                    firstName = name;
                    break;
                }
            }
        }
        return firstName
    }

};