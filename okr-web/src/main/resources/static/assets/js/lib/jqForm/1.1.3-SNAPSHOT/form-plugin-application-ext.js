validateUtil = $.extend(validateUtil, {

    /**
     * 验证字段
     * @param fieldValue
     * @param validateRule
     * @returns {Array}
     */
    validateField: function (fieldValue, validateRule) {
        var validateMsgObj = [], validataMsg = "";
        //验证非空
        if (validateRule.required) {
            fieldValue = $.trim(fieldValue);
            //避免fieldValue为数值0，校验错误
            if (fieldValue.length == 0) {
                validataMsg = validateRule.label + "不能为空";
                validateMsgObj.push(validataMsg);
            }
        }
        //验证最大值
        if (validateRule.maxValue) {
            if (!isNaN(validateRule.maxValue)) {
                if (fieldValue) {
                    if (!isNaN(fieldValue)) {
                        if (fieldValue > validateRule.maxValue) {
                            validataMsg = validateRule.label +
                                (validateRule.maxValueMsg ? validateRule.maxValueMsg : "不能大于" + validateRule.maxValue);
                            validateMsgObj.push(validataMsg);
                        }
                    } else {
                        validateMsgObj.push(validateRule.label + "必须是数字");
                    }
                }
            } else {
                throw Error("验证规则 maxValue 错误");
            }
        }
        //验证最小值
        if (validateRule.minValue) {
            if (!isNaN(validateRule.minValue)) {
                if (fieldValue) {
                    if (!isNaN(fieldValue)) {
                        if (fieldValue < validateRule.minValue) {
                            validataMsg = validateRule.label + (validateRule.minValueMsg ? validateRule.minValueMsg : " 不能小于" + validateRule.minValue);
                            validateMsgObj.push(validataMsg);
                        }
                    } else {
                        validateMsgObj.push(validateRule.label + "必须是数字");
                    }
                }
            } else {
                throw Error("验证规则 minValue 错误，必须是数字");
            }
        }

        //验证最小长度
        if (validateRule.minLength) {
            if (!isNaN(validateRule.minLength)) {
                validataMsg = validateRule.label + (validateRule.minLengthMsg ? validateRule.minLengthMsg : "长度不能小于" + validateRule.minLength);
                if (fieldValue) {
                    if (fieldValue.length < validateRule.minLength) {
                        validateMsgObj.push(validataMsg);
                    }
                } else {
                    validateMsgObj.push(validataMsg);
                }
            } else {
                throw Error("验证规则 minLength 错误，必须是数字！");
            }
        }

        //验证最大长度
        if (validateRule.maxLength) {
            if (!isNaN(validateRule.maxLength)) {
                validataMsg = validateRule.label + (validateRule.maxLengthMsg ? validateRule.maxLengthMsg : "长度不能大于" + validateRule.maxLength);
                if (fieldValue) {
                    if (fieldValue.length > validateRule.maxLength) {
                        validateMsgObj.push(validataMsg);
                    }
                }
            } else {
                throw Error("验证规则 maxLength 错误，必须是数字！");
            }
        }

        //正则表达式
        if (fieldValue && validateRule.reqExp) {
            if (validateRule.reqExpMsg) {
                validataMsg = validateRule.label + (validateRule.reqExpMsg ? validateRule.reqExpMsg : "");
                if (fieldValue.match(validateRule.reqExp) == null) {
                    validateMsgObj.push(validataMsg);
                }
            } else {
                throw Error("验证规则表达式 reqExpMsg 错误！");
            }
        }
        //正则表达式
        if (validateRule.custom) {
            if ($.isFunction(validateRule.custom)) {
                var result = validateRule.custom.call(this);
                if (result != true) {
                    validataMsg = result == false ? "验证出错" : result;
                    validateMsgObj.push(validataMsg);
                }
            } else {
                throw Error("自定义验证规则必须是Function！");
            }
        }
        return validateMsgObj;
    },

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

                //验证最小值并且不能等于最小值
                if (validateRule.minValue1) {
                    if (!isNaN(validateRule.minValue1)) {
                        if ($.isNumeric(fieldValue)) {
                            if (fieldValue <= validateRule.minValue1) {
                                validateMsg = validateRule.label + (validateRule.minValueMsg ? validateRule.minValueMsg : " 不能小于等于" + validateRule.minValue1);
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
    }

});

define(["jqForm"], function () {
    var _formUtil = {};
    var _defaultValidate = {
        errorClass: 'input-error',
        msgNode: 'span',
        msgNodeClass: 'input-error-tip',
        msgParentNode: 'li,.form-group',
        rule: {}
    };
    _defaultValidate.onfielderror = function (input, msgs) {
        if (null === this.form) {
            return;
        }
        if ($.isArray(msgs) && msgs.length > 0) {
            if (!input.hasClass(this.errorClass)) {
                input.addClass(this.errorClass);
            }
            var _li = input.closest(this.msgParentNode,this.form).last();
            var _span = _li.children(this.msgNode + '.' + this.msgNodeClass);
            if (_span.size() > 0) {
                _span.html(msgs[0]);
            } else {
                _li.append('<' + this.msgNode + ' class="' + this.msgNodeClass + '">' + msgs[0] + '</' + this.msgNode + '>');
            }
        } else {
            this.onclearerror.call(this, input);
        }
    };
    //将表单进行序列化成对象
    _defaultValidate.serializeObject = function () {
        if (null == this.form) {
            return {};
        }
        var _valueArray = this.form.serializeArray();
        var _value = {};
        $.each(_valueArray, function (k, v) {
            _value[v.name] = v.value;
        });
        return _value;
    };
    _defaultValidate.onclearerror = function (input) {
        if (null === this.form) {
            return;
        }
        if ('object' === $.type(input)) {
            input.removeClass(this.errorClass);
            var _li = input.closest(this.msgParentNode,this.form).last();
            _li.children(this.msgNode + '.' + this.msgNodeClass).remove();
        } else {
            //删除所有的错误样式
            this.form.find('input, textarea').removeClass(this.errorClass);
            //删除所有的错误提示
            this.form.find(this.msgParentNode + ' > ' + this.msgNode + '.' + this.msgNodeClass).remove();
        }
    };
    _defaultValidate.validate = function (show, input, isFocus) {
        isFocus = isFocus ? isFocus : false;
        if (null === this.form) {
            return true;
        }
        if (true === show) {
            this.onclearerror.call(this, input);
        }
        if ('object' === $.type(input)) {
            var _name = input.attr('data-rule-name') ? input.attr('data-rule-name') : input.attr('name');
            if ('string' !== $.type(_name)) return true;
            if ('object' === $.type(this.rule[_name])) {
                var _validate = validateUtil.validateField.call(input[0], input.val(), this.rule[_name]);
                if ($.isArray(_validate) && _validate.length > 0) {
                    if (true === show) {
                        this.onfielderror.call(this, input, _validate);
                    }
                    if (true === isFocus) {
                        input.focus();
                    }
                    return false;
                }
            }
            return true;
        } else {
            var _validate = validateUtil.validateData(this.serializeObject(), this.rule);
            if (!$.isEmptyObject(_validate)) {
                if (true === show) {
                    var _this = this;
                    $.each(_validate, function (p, item) {
                        var _input = _this.form.find('input[name="' + p + '"], textarea[name="' + p + '"]');
                        _this.onfielderror.call(_this, _input, item);
                    });
                }
                return false;
            } else {
                return true;
            }
        }
    };
    _formUtil.init = function (form, config) {
        var _formHelper = $.extend({}, _defaultValidate);
        if (config && typeof config == "object") {
            _formHelper = $.extend({}, _formHelper, config);
        }
        _formHelper.form = $(form);
        //没有内容，不进行处理
        if (_formHelper.form.size() <= 0) {
            _formHelper.form = null;
            return _formHelper;
        }
        //placeholder初始化
        //_formHelper.form.find('input, textarea').placeholder();
        // 表单元素事件绑定
        _formHelper.form.on('change, blur', 'input, textarea, select', function () {
            var $this = $(this);
            if (!$this.is(':disabled')) {
                if ($this.attr("componentType") && $this.attr("componentType").indexOf('picker') >= 0) {
                    // 因为datepicker的赋值是在blur事件之后
                    setTimeout(function () {
                        _formHelper.validate(true, $this);
                    },300);
                } else {
                    _formHelper.validate(true, $this);
                }
            }
        });
        // 调用validate事件, 验证未通过会获取焦点
        _formHelper.form.on('validate', 'input, textarea, select', function (e) {
            e.stopPropagation();
            _formHelper.validate(true, $(this), true);
        });
        //
        _formHelper.form.on('clearerror', 'input, textarea, select', function (e) {
            e.stopPropagation();
            _defaultValidate.onclearerror($(this));
        });
        // 验证全部事件
        _formHelper.form.on("validate", function (event, callback) {
            var isValidate = true;
            _formHelper.form.find("input, textarea, select").each(function () {
                if (!$(this).is(':disabled')) {
                    if (!_formHelper.validate(true, $(this), true)) {
                        isValidate = false;
                        return false;
                    }
                }
            });
            callback.call(this, isValidate);
        });
        return _formHelper;
    };
    return _formUtil;
});