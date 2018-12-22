/*
 * 商集唯你 研发中心前端架构组 自主研发。
 * 2015-11-18 之前未做版本管理，都默认为1.0.0。
 * 2015-11-18 开始为1.1.0。
 * 版本说明请阅读readme.txt
 */
;
/**
 * 依赖jQuery,Underscore
 */
(function ($, undefined) {
    "use strict";
    /** core ******************************************************************** */
    $.autoTree = $.autoTree || {
        defaultConfig: {}
    };
    // 默认配置
    var defaultConfig = {
        event: {
            /** 控件下拉显示事件 (事件触发要 避免 click,focus,mousedown 的并发情况)*/
            actionEvents: "click.auto-tree mousedown.auto-tree focus.auto-tree",
            /** 控制输入事件 */
            inputEvents: "keyup.auto-tree",
            /** 控件下拉隐藏事件（事件是绑定在window上） */
            lossEventsForWindow: "mousedown.auto-tree scroll.auto-tree",
            //            lossEventsForWindow: "",//TODO:测试
            /** 控件下拉隐藏事件（事件是绑定在控件上） */
            lossEventsForSelf: "blur.auto-tree"
            //            lossEventsForSelf: ""//TODO:调试使用
        },
        view: {
            /** 每页显示记录数 */
            //pageSize: 10,
            /** 高度 */
            height: 0,
            /** 最大高度 */
            maxHeight: 200,
            /** 控件宽度 */
            width: 0,
            /** 最大宽度 */
            maxWidth: 160,
            /**（弃用） 左右、上下、偏移 {left: 0, top: 0} */
            //offset: {left: 0, top: 0},
            /** 是否默认选中第一行 */
            isSelectedFirstRow: true,
            /** 是否记忆值
             * （控件不负责赋值到 输入框，数据不确定是否来自于tree，通过没有执行 onSelectRow 来判定）
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
             * async.dataType:"local" 才有效
             */
            localData: [],
            /**
             * 没有数据的html
             */
            emptyRecordsHtml: "没有数据！",
            /**
             * 输入时会过滤的字段名
             */
            inputFilterFieldNames: [],
            /**
             * 分隔符（当树是多选时，分割节点nane）
             */
            separator: ",",
            /**
             * (treeConfig.check.enable=true)树多选时使用（树节点字段名，次字段的数据在树上必须唯一）
             */
            viewUniqueFieldName: "",
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
             * 自动宽度的间隔宽度
             * 必须 dropDownContainer不为空 并且 width = 0,才生效
             * //优先级：1、width；2、autoWidthPadding（dropDownContainer不等于null）；3、widthRefer；
             */
            autoWidthPadding: -1,
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
             * 选中节点时将选中值拼接 同步到文本框
             * (treeConfig.check.enable = true 时生效)
             */
            checkedSyncValue: true,
            /**
             * 显示清除按钮
             */
            showClearButton: false
        },
        callback: {
            // beforeAction(event) 控件弹出下拉前动作，必须返回true或false。
            beforeAction: null,
            //下拉打开后 afterAction(event)
            afterAction: null,
            //下拉消失前 beforeLose(event)
            beforeLose: null,
            //下拉消失后 afterLose(event)
            afterLose: null,
            //请求发起前,返回boolean,false拒绝后续,true继续后续. beforeAjaxBeforeSend(xhr, settings)
            beforeAjaxBeforeSend: null,
            //数据加载成功,但还未装载到控件 beforeAjaxSuccess(responseData, st, xhr)
            beforeAjaxSuccess: null,
            //数据加载失败 afterAjaxError(xhr, st, err)
            afterAjaxError: null,
            // beforeSelectRow(rowData)
            //beforeSelectRow: null,
            // afterSelectRow(rowData)
            //afterSelectRow: null,
            //输入前,返回boolean,false拒绝后续,true继续后续. beforeInput(event)
            beforeInput: null,
            //输入后 afterInput(event)
            afterInput: null,
            //比beforeAjaxBeforeSend更前,设置请求的数据. setRequestData(requestData)
            setRequestData: null,
            //数据装载完成.afterLoad(dataStore)
            afterLoad: null
        },
        async: {
            url: "",
            type: "POST",
            dataType: "json",
            /** 数据来源方式：local = 本页面、onceRemote = 一次远程 */
            dataSourceType: "onceRemote",
            async: true,
            data: {}
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
        },
        treeConfig: {
            check: {
                enable: false
            },
            callback: {
                /**
                 * 用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作
                 * 如果返回 false，zTree 将不会选中节点，也无法触发 onClick 事件回调函数
                 * @param treeId 对应 zTree 的 treeId
                 * @param treeNode 被单击的节点 JSON 数据对象
                 * @param clickFlag 节点被点击后的选中操作类型，详细看下表
                 clickFlag | selectedMulti | autoCancelSelected&&event.ctrlKey/metaKey | isSelected | 选中操作
                 1    true    false    false    普通选中
                 1    true    false    true    普通选中
                 2    true    true    false    追加选中
                 0    true    true    true    取消选中
                 1    false    false    false    普通选中
                 1    false    false    true    普通选中
                 1    false    true    false    普通选中
                 0    false    true    true    取消选中
                 * @returns {boolean} 返回值是 true / false
                 */
                beforeClick: function (treeId, treeNode, clickFlag) {
                    return true;
                },
                /**
                 * 用于捕获节点被点击的事件回调函数
                 如果设置了 setting.callback.beforeClick 方法，且返回 false，将无法触发 onClick 事件回调函数。
                 * @param event 标准的 js event 对象
                 * @param treeId 对应 zTree 的 treeId
                 * @param treeNode 被单击的节点 JSON 数据对象
                 */
                onClick: function (event, treeId, treeNode) {
                },
                /**
                 * 用于捕获 勾选 或 取消勾选 之前的事件回调函数，并且根据返回值确定是否允许 勾选 或 取消勾选
                 * 如果返回 false，将不会改变勾选状态，并且无法触发 onCheck 事件回调函数
                 * @param treeId 对应 zTree 的 treeId
                 * @param treeNode 进行 勾选 或 取消勾选 的节点 JSON 数据对象
                 * @returns {boolean} 返回值是 true / false
                 */
                beforeCheck: function (treeId, treeNode) {
                    return true;
                },
                /**
                 * 用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
                 如果设置了 setting.callback.beforeCheck 方法，且返回 false，将无法触发 onCheck 事件回调函数。
                 * @param event 标准的 js event 对象
                 * @param treeId 对应 zTree 的 treeId
                 * @param treeNode 被勾选 或 取消勾选的节点 JSON 数据对象
                 */
                onCheck: function (event, treeId, treeNode) {
                }
            }
        }
    };
    // 其它配置
    var defaultOtherConfig = {
        /** 公开属性 */
        //下拉是否显示
        isDropDownShow: false,
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
        _inputFilterFieldNames: null,
        // 下拉、数据面板、是否已经装载完(重复点击不会重复提交、只呈现下拉)
        _isLoaded: false,
        //标识触发动作是否停止
        _actionStop: true,
        //焦点节点
        _currentFocusNode: null,
        //焦点节点下标
        _focusNodeIndex: -1,
        //判断当前数据是否来至于树
        _isCurrentValueFromTree: false,
        //记忆的值
        _rememberValue: "",
        //用于控制 仅重载数据，不显示
        _justReload: false,
        //判断鼠标是否移出下拉框
        _isMouseOutDropDown: true,
        //判断鼠标是否移入清除按钮
        _isMouseOverClearBtn: false,
        //树是否初始化
        _isTreeInit: false,
        //树是多选时，已选节点值store(以树的呈现)
        _checkedNodeStore: {}
    };
    $.fn.AutoTree = function (config) {
        if (typeof config === 'string') {
            var fn = _getAccessor($.autoTree, config);
            if (!fn) {
                alert("AutoTree method not found:" + config);
                return;
            } else {
                //前缀是下划线的方法不可调用
                if (config.indexOf("_") === 0) {
                    alert("AutoTree don't call the private method:" + config);
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
            /**
             * 判定非input:text,textarea 控件的渲染
             */
            if (!$(_thisElm).is("input:text") && !$(_thisElm).is("textarea")) {
                alert("Non(input:text)controls can't initialize!");
                return;
            }
            /**
             * 配置继承
             */
            _thisElm.p = $.extend(true, {}, {
                id: _thisElm.id ? _thisElm.id : "_" + parseInt((Math.random() * 100000000))//ID 不配置采用随机数ID
            }, defaultOtherConfig, $.autoTree);
            //
            p = _thisElm.p;
            //
            _thisElm.id = p.id;
            _thisElm.componentType = "AutoTree";
            // 继承
            p.event = $.extend(true, {}, defaultConfig.event, $.autoTree.defaultConfig.event, config.event);
            p.view = $.extend(true, {}, defaultConfig.view, $.autoTree.defaultConfig.view, config.view);
            p.callback = $.extend(true, {}, defaultConfig.callback, $.autoTree.defaultConfig.callback, config.callback);
            p.async = $.extend(true, {}, defaultConfig.async, $.autoTree.defaultConfig.async, config.async);
            p.input = $.extend(true, {}, defaultConfig.input, $.autoTree.defaultConfig.input, config.input);
            p.treeConfig = $.extend(true, {}, defaultConfig.treeConfig, $.autoTree.defaultConfig.treeConfig, config.treeConfig);
            //
            var $container = $(p.view.dropDownContainer);
            if ($container && $container.length > 0 && $container[0].id === (p.id + 'DropDown') && App && App.hasOwnProperty("src") && App.src === true) {
                _printWarnLog('配置项p.view.dropDownContainer=' + p.view.dropDownContainer + '，对应的元素id不能命名为（' + p.id + 'DropDown）建议改为（' + p.id + 'Container）');
            }
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
    /** action ******************************************************************** */
    $.extend($.autoTree, {

        /**
         * 触发控件的Action
         * @param event
         */
        triggerAction: function (event) {
            $(this).each(function () {
                var _thisElm = this, p = _thisElm.p, $this = $(_thisElm);
                if (!$this.prop("disabled")) {
                    //非只读 和 只读时也允许触发
                    if (!$this.prop("readonly") || p.view.isAllowReadonlyAction) {
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
                p.isDropDownShow = _isDropDownShow;
                if ($c == null)
                    return;
                if (_isDropDownShow) {
                    //下拉直接显示在指定区域
                    if (p.view.dropDownContainer) {
                        //下拉必须显示
                        //$c.css({width: p.view.width});
                        //重新计算下拉尺寸
                        _reCalDropDownSize.call(_thisElm);
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
                            'z-index': p.view.zIndex,
                            width: p.view.width,
                            height: p.view.height,
                            overflow: "auto"
                        });
                        //
                        $c.show(function () {
                            //重新计算下拉尺寸
                            _reCalDropDownSize.call(_thisElm);
                            window.setTimeout(function () {
                                //如果控件 被销毁了，下拉就不显示
                                if ($("#" + p.id).length === 0) {
                                    $c.hide();
                                } else {
                                    $positionRefer = _getPositionRefer.call(_thisElm);
                                    $c.positioner({
                                        my: "left top",
                                        at: "left bottom",
                                        of: $positionRefer,
                                        collision: "flip flip"
                                    });
                                }
                            }, 0);
                        });
                    }
                } else {
                    //是否记忆数据 并且 不允许为空
                    if (p.view.isRememberValue === true && (p.view.isAllowEmpty === false || $.trim($this.val()).length !== 0)) {
                        //判断输入框值 是否 来自于dataGrid
                        //（控件不负责赋值到 输入框，数据不确定是否来自于dataGrid，通过没有执行 onSelectRow来判定）
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
                            //
                            p._isAlreadyRemember = false;
                        }
                    }
                    //下拉直接显示在指定区域
                    if (p.view.dropDownContainer) {
                        //重载到全部的数据
                        _reloadByValue.call(_thisElm, "");
                        //重新显示 一次，将重新计算宽度、高度
                        p.showDropDown.call(_thisElm, true);
                    } else {
                        $c.css({
                            left: '-9999px',
                            top: '-9999px',
                            position: 'absolute',
                            'z-index': -9999
                        }).hide();
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
                        'z-index': 9999
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
         * 获取树Id
         * @returns {string}
         */
        getTreeId: function () {
            return this.p.id + "Tree";
        },

        /**
         * 获取树对象
         * @returns {*}
         */
        getTreeObj: function () {
            var treeObj = null;
            $(this).each(function () {
                var _thisElm = this, p = _thisElm.p, treeId = p.getTreeId.call(_thisElm);
                if (!p._isTreeInit) {
                    //扩展树配置
                    _extendTreeConfig.call(_thisElm);
                    $.fn.zTree.init($("#" + treeId), p.treeConfig);
                    p._isTreeInit = true;
                }
                treeObj = $.fn.zTree.getZTreeObj(treeId);
            });
            return treeObj;
        },

        /**
         * 获取树对象setting
         * @returns {*}
         */
        getTreeSetting: function () {
            var setting = {};
            $(this).each(function () {
                var _thisElm = this, p = _thisElm.p;
                setting = p.getTreeObj.call(_thisElm).setting;
            });
            return setting;
        },

        /**
         * 显示树节点
         * @param nodes
         * @param flag
         * @returns {*}
         */
        showTreeNodes: function (nodes, flag) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    treeObj = p.getTreeObj.call(_thisElm),
                    treeObjSetting = treeObj.setting,
                    node, childrens, i;
                //
                for (i = 0; i < nodes.length; i++) {
                    node = nodes[i];
                    //先隐藏全部节点
                    childrens = node[treeObjSetting.data.key.children];
                    if (childrens && childrens.length > 0) {
                        if (flag) {
                            p.showTreeNodes.call(_thisElm, childrens, true);
                        } else {
                            p.hideTreeNodes.call(_thisElm, childrens);
                        }
                    }
                    if (flag) {
                        treeObj.showNode(node);
                    } else {
                        treeObj.hideNode(node);
                    }
                }
            });
        },

        /**
         * 隐藏树节点
         * @param nodes
         */
        hideTreeNodes: function (nodes) {
            return $(this).each(function () {
                this.p.showTreeNodes.call(this, nodes, false);
            });
        },

        /**
         * 获取已选数据
         * @returns {Array}
         */
        getCheckedNodes: function () {
            var selectedNodes = [];
            $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    storeKey;
                for (storeKey in p._checkedNodeStore) {
                    selectedNodes.push(p._checkedNodeStore[storeKey]);
                }
            });
            return selectedNodes;
        },

        /**
         * 获取所有树节点
         */
        getNodes: function () {
            var allNodes = [];
            $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    treeObj = p.getTreeObj.call(_thisElm);
                allNodes = treeObj.getNodes();
            });
            return allNodes;
        },

        /**
         * 设置选中的节点
         *  根据在<input>(带分隔符p.view.separator)的值(每个值是在节点上必须是唯一),选中树节点
         *
         * @returns {Array}
         */
        setCheckedNodes: function (value) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    strArr, str, j,
                    treeObj = p.getTreeObj.call(_thisElm),
                    node;
                //如果是多选
                if (p.treeConfig.check.enable &&
                    value && value.length > 0 &&
                    treeObj) {
                    //
                    strArr = value.split(p.view.separator);
                    //清空已选节点store
                    p._checkedNodeStore = {};
                    //反选所有节点
                    treeObj.checkAllNodes(false);
                    //
                    for (j = 0; j < strArr.length; j++) {
                        str = strArr[j];
                        //根据分割的值查找节点
                        node = treeObj.getNodeByParam(p.view.viewUniqueFieldName, str);
                        if (node && !$.isEmptyObject(node)) {
                            //选中
                            treeObj.checkNode(node, true, false, false);
                            //存入store
                            p._checkedNodeStore[str] = node;
                        }
                    }
                    //
                    if (p.view.checkedSyncValue) {
                        _setValueByCheckedNodes.call(_thisElm);
                    }
                }
            });
        },

        /**
         * 重置选中的节点
         * @returns {Array}
         */
        resetCheckedNodes: function () {
            return $(this).each(function () {
                var _thisElm = this, treeObj = this.p.getTreeObj.call(this);
                //清空已选节点store
                this.p._checkedNodeStore = {};
                //反选所有节点
                treeObj.checkAllNodes(false);
                //
                _setValueByCheckedNodes.call(_thisElm);
            });
        },

        /**
         * 选中所有节点
         * @returns {Array}
         */
        checkAllNodes: function () {
            return $(this).each(function () {
                var _thisElm = this, p = this.p, treeObj = this.p.getTreeObj.call(this),
                    allNodes, treeNode, k;
                //反选所有节点
                treeObj.checkAllNodes(true);
                //选节点store
                allNodes = p.getNodes.call(_thisElm);
                for (k = 0; k < allNodes.length; k++) {
                    treeNode = allNodes[k];
                    p._checkedNodeStore[treeNode[p.view.viewUniqueFieldName]] = treeNode;
                }
                _setValueByCheckedNodes.call(_thisElm);
            });
        },

        /**
         * 移除全部节点,并且会清空 已选的(通过getCheckedNodes将获得空)
         */
        removeAllNodes: function () {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    allNodes = p.getNodes.call(_thisElm),
                    i;
                //
                for (i = allNodes.length - 1; i >= 0; i--) {
                    p.removeNode.call(_thisElm, allNodes[i]);
                }
            });
        },

        /**
         * 移除单个节点,并且会移除 已选的(通过getCheckedNodes将获取不到移除的)
         * @param node 节点对象
         */
        removeNode: function (node) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    treeObj = p.getTreeObj.call(_thisElm);
                //从store移除选中
                delete p._checkedNodeStore[node[p.view.viewUniqueFieldName]];
                //从树上移除
                treeObj.removeNode(node);
            });
        },

        /**
         *
         * @param newNoe JSON / Array(JSON)
         *                                  需要增加的节点数据 JSON 对象集合，数据只需要满足 zTree 的节点数据必需的属性即可，详细请参考“treeNode 节点数据详解”
         *                                  1、v3.x 支持单独添加一个节点，即如果只新增一个节点，不用必须包在数组中
         *                                  2、使用简单数据模式，请参考 setting.data.simpleData 内的属性说明
         * @param parentNode    JSON
         *                                      指定的父节点(可空)，如果增加根节点，请设置 parentNode 为 null 即可。
         *                                      请务必保证此节点数据对象 是 zTree 内部的数据对象
         * @param isSilent Boolean
         *                                      设定增加节点后是否自动展开父节点。
         *                                      isSilent = true 时，不展开父节点，其他值或缺省状态都自动展开。
         * @returns {*}
         */
        addNode: function (node, parentNode, isSilent) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    treeObj = p.getTreeObj.call(_thisElm);
                //
                treeObj.addNodes(parentNode, node, isSilent);
            });
        },

        /**
         * 销毁控件
         */
        destroy: function () {
            $(this).each(function () {
                var _thisElm = this,
                    p = _thisElm.p,
                    $this = $(_thisElm),
                    treeObj,
                    windowEvent = _splitEventString.call(_thisElm, p.event.lossEventsForWindow, p.id);
                //注销事件
                $this.off(p.event.actionEvents + p.id);
                $this.off(p.event.inputEvents + p.id);
                $(window.document).off(windowEvent);
                $this.off(p.event.lossEventsForSelf + p.id);
                p._dropDown.off('mouseover.auto-tree' + p.id);
                p._dropDown.off('mouseout.auto-tree' + p.id);
                //销毁树
                treeObj = p.getTreeObj.call(_thisElm);
                treeObj.destroy();
                //
                _thisElm.renderCompleted = false;
                _thisElm.p = null;
            });
        },

        /**
         * 重新装载
         * @returns {*}
         */
        reload: function () {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p;
                p._justReload = true;
                _onLoad.call(_thisElm);
            });
        },

        clearValue: function () {
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
                    }
                    if (p.treeConfig.check.enable) {
                        p.resetCheckedNodes.call(_thisElm);
                    }
                    _hideClearButton.call(_thisElm);
                }
            });
        }

    });
    /** public interface ******************************************************************** */


    var
        /**
         * 对象属性访问器 如： obj = {"a":1,"b":2} expr = "a" 即返回 1
         */
        _getAccessor = function (obj, expr) {
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
                $this.off(p.event.inputEvents + p.id);
                $this.on(p.event.inputEvents + p.id, _onInput);
                //
                $(window.document).off(windowEvent);
                $(window.document).on(windowEvent, function () {
                    // 关闭下拉框的事件
                    _onLose.apply(_thisElm, arguments);
                });
                //
                $this.off(p.event.lossEventsForSelf + p.id);
                $this.on(p.event.lossEventsForSelf + p.id, function (event) {
                    // 关闭下拉框的事件
                    _onLose.apply(_thisElm, arguments);
                    //
                    _hideClearButton.call(_thisElm, event);
                });
                //1.计算必要信息
                _calculateInfo.call(_thisElm);
                //2.下拉创建
                if (p._dropDown === null) {
                    _createDropDown.call(_thisElm);
                }
                //情况1/4：控件初始化前就有值
                if ($this.val() && p.view.isRememberValue) {
                    p._isCurrentValueFromTree = true;
                    p._isAlreadyRemember = true;
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
         * 重新计算下拉尺寸
         * @returns {*}
         */
        _reCalDropDownSize = function () {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p;
                //下拉固定在页面的 不能重复调整两次
                if (p._hasResize !== true || p.view.dropDownContainer == null) {
                    //如果下拉的宽度是引用的(如果 宽度引用的目标是隐藏的，宽度将计算有误，这里需要重新获取 )
                    if (p.view.widthRefer) {
                        p.view.width = _getWidthRefer.call(_thisElm);
                    }
                    p._dropDown.css({width: p.view.width});
                    //
                    p._hasResize = true;
                }
            });
        },

        /**
         * 根据输入框的值，重载
         * @param filterValue
         * @private
         */
        _reloadByValue = function (filterValue) {
            var _thisElm = this, p = _thisElm.p,
                _filterValue = filterValue ? filterValue : "";
            switch (p.async.dataSourceType) {
                case "remote" :
                // // 将输入的值作为参数，提交到远程
                // p.async.data[p.input.inputQueryParamName] = _filterValue;
                // //
                // _onRemote.call(_thisElm);
                // break;
                case "onceRemote" :
                case "local" :
                    // 过滤节点数据
                    _filterTreeNode.call(_thisElm, _filterValue);
                    // 隐藏等待条
                    p.hideLoadingBar.call(_thisElm);
                    //
                    if (!p._justReload) {
                        p.showDropDown.call(_thisElm, true);
                    } else {
                        p._justReload = false;
                    }
                    //判断 设置选中第一行
                    //p.setSelectedFirstRow.call(_thisElm);
                    // 装载完成
                    p._isLoaded = true;
                    //第一次走 reload，此处不需要 afterLoad
                    // if ($.isFunction(p.callback.afterLoad)) {
                    //     p.callback.afterLoad.call(_thisElm, filterStore);
                    // }
                    break;
            }
        },

        ///**
        // * 设置 高亮效果
        // * @param inputValue
        // * @param dataStore
        // * @returns {*}
        // * @private
        // */
        //_setHighlightEffect = function (inputValue, dataStore) {
        //    //TODO:过滤结构要重新写，zTree数据支持简单JSON和标准JSON
        //    var _thisElm = this, p = _thisElm.p, i = 0, j = 0, indexOf = -0, matchStr,
        //        filterFieldName, rowData = {},
        //        inputFilterFieldNames = p.view.inputFilterFieldNames,
        //        tempStore = $.extend(true, {}, dataStore);
        //    if (inputValue.length > 0) {
        //        for (; i < tempStore.length; i++) {
        //            rowData = tempStore[i];
        //            for (j = 0; j < inputFilterFieldNames.length; j++) {
        //                filterFieldName = inputFilterFieldNames[j];
        //                // 如果有其中一个字段匹配，整行都保留
        //                if (rowData && rowData[filterFieldName]) {
        //                    indexOf = (rowData[filterFieldName] + "").toLowerCase().indexOf(inputValue.toLowerCase());
        //                    if (indexOf > -1) {
        //                        matchStr = rowData[filterFieldName].substr(indexOf, inputValue.length);
        //                        rowData[filterFieldName] = rowData[filterFieldName].replace(matchStr, "<span style='color: #cc0000;'>" + matchStr + "</span>");
        //                    }
        //                }
        //            }
        //        }
        //    }
        //    return tempStore;
        //},

        ///**
        // * 获取 输入值 被过滤后的数据集
        // * @param inputValue
        // * @param dataStore
        // * @returns {*}
        // * @private
        // */
        //_getFilterRowDatas = function (inputValue, dataStore) {
        //    //TODO:过滤结构要重新写，zTree数据支持简单JSON和标准JSON
        //    var _thisElm = this, p = _thisElm.p, i = 0, j = 0,
        //        filterRowDatas = [], filterFieldName, rowData = {},
        //        inputFilterFieldNames = p.view.inputFilterFieldNames,
        //        tempStore = $.extend(true, {}, dataStore);
        //    if (inputValue.length > 0) {
        //        for (; i < tempStore.length; i++) {
        //            rowData = tempStore[i];
        //            for (j = 0; j < inputFilterFieldNames.length; j++) {
        //                filterFieldName = inputFilterFieldNames[j];
        //                // 如果有其中一个字段匹配，整行都保留
        //                if ((rowData[filterFieldName]).toLowerCase().indexOf(inputValue.toLowerCase()) > -1) {
        //                    filterRowDatas.push(rowData);
        //                    break;
        //                }
        //            }
        //        }
        //        tempStore = filterRowDatas;
        //    } else {
        //        tempStore = dataStore;
        //    }
        //    //设置高亮效果
        //    tempStore = _setHighlightEffect.call(_thisElm, inputValue, tempStore);
        //    return tempStore;
        //},

        /**
         * 设置选中节点效果
         * @param node
         * @private
         */
        _setSelectedNodeEffect = function (node) {
            $("a.autoTree-node-selected").removeClass("autoTree-node-selected");
            $("#" + node.tId + "_a").addClass("autoTree-node-selected");
        },

        ///**
        // * 光标定位到最后
        // * @private
        // */
        //_cursorToEnd = function () {
        //    var _thisElm = this,
        //        val = _thisElm.value,
        //        len = val ? val.length : 0;
        //    if (val && len > 0) {
        //        var _timeObj = window.setTimeout(function () {
        //            if (_thisElm.createTextRange) {
        //                var range = _thisElm.createTextRange();
        //                range.collapse(true);
        //                range.moveEnd('character', len);
        //                range.moveStart('character', len);
        //                range.select();
        //            } else if (_thisElm.setSelectionRange) {
        //                _thisElm.select();
        //                _thisElm.setSelectionRange(len, len);
        //            }
        //            window.clearTimeout(_timeObj);
        //        }, 100);
        //    }
        //},

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
         * 计算 私有用配置信息
         *
         * @returns {Array}
         * @private
         */
        _calculateInfo = function () {
            var _thisElm = this, p = _thisElm.p;
            //优先1.指定的值
            if (p.view.width === 0) {
                //优先级：1、width；2、autoWidthPadding（dropDownContainer不等于null）；3、widthRefer；
                if (p.view.autoWidthPadding > 0 && p.view.dropDownContainer) {
                    p.view.width = $.oui.utils.element.getWidthToRight(p.view.dropDownContainer, p.view.autoWidthPadding);
                } else {
                    if (p.view.widthRefer) {
                        p.view.width = _getWidthRefer.call(_thisElm);
                    } else {
                        p.view.width = p.view.maxWidth;
                    }
                }
            }
            //高度
            if (p.view.height === 0) {
                //自动高度
                //固定下拉区域
                if (p.view.autoHeightPadding > -1 && p.view.dropDownContainer) {
                    p.view.height = $.oui.utils.element.getHeightToBottom(p.view.dropDownContainer, p.view.autoHeightPadding);
                } else {
                    p.view.height = p.view.maxHeight;
                }
            }
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
                p._loadingBar = $('<div id="' + lbid + '" style="display: none;position: absolute;top: 0;left: 0;"><div class="autoTree-loadingbar-img"></div></div>');
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
                p._clearBtn = $('<div id="' + cbId + '" style="background-color: #FFFFFF;height:18px;width:18px;display: none;position: absolute;top: 0;left: 0;" onclick="$(\'#' + p.id + '\').AutoTree(\'clearValue\');"><div class="autoTree-clear-img"></div></div>');
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
                        display: "block"
                    }).show()
                    //让图片居中
                        .find("div.autoTree-clear-img").css({"position": "relative", "top": ((clearImgBoxWH - clearImgWH) / 2) + "px", "left": ((clearImgBoxWH - clearImgWH) / 2) + "px"});
                }
            }
        },
        /**
         * 隐藏清除按钮
         */
        _hideClearButton = function () {
            var _thisElm = this, p = _thisElm.p;
            if (p._clearBtn && p._clearBtn.length > 0) {
                //鼠标 离开了 清除按钮
                if (p._isMouseOverClearBtn === false) {
                    p._clearBtn.hide();
                }
                if (event && event.type === "click") {
                    p._clearBtn.hide();
                }
            }
        },

        /**
         * 控件输入事件
         */
        _onInput = function (event) {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm),
                treeObj = p.getTreeObj.call(_thisElm),
                childrenNodes,
                selector,
                $tree,
                bindFill = p.view.bindFill;
            if ($.isFunction(p.callback.beforeInput) && !p.callback.beforeInput.call(_thisElm, event)) {
                return;
            }
            if (event && event.keyCode) {
                // 可让value改变的 Keycode
                if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 65 && event.keyCode <= 90)
                    || event.keyCode === 8 || event.keyCode === 32 || event.keyCode === 46
                    || (event.keyCode >= 96 && event.keyCode <= 111)
                    || (event.keyCode >= 186 && event.keyCode <= 192)
                    || (event.keyCode >= 219 && event.keyCode <= 222)) {
                    //输入改变了值，不是来至于选择面板
                    p._isCurrentValueFromTree = false;
                    // 输入查询
                    if (p.input.enableInputAction) {
                        // 清除时间对象
                        window.clearTimeout(p._inputQueryTimeobj);
                        //根据输入框值，重载数据
                        p._inputQueryTimeobj = window.setTimeout(function () {
                            //如果树 是多选情况下
                            if (p.treeConfig.check.enable) {
                                //根据分隔符 最后一个值
                                var str = $this.val().substring($this.val().lastIndexOf(p.view.separator) + 1, $this.val().length);
                                _reloadByValue.call(_thisElm, str);
                            } else {
                                //重载
                                _reloadByValue.call(_thisElm, $this.val());
                            }
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
                    //如果树 是多选情况下（并且删除 BackSpace = 8）
                    if (p.treeConfig.check.enable && event.keyCode === 8) {
                        //将值 进行分割
                        var valueArr = $this.val().split(p.view.separator),
                            storeKey, i, isExists;
                        if (valueArr && valueArr.length > 0) {
                            for (storeKey in p._checkedNodeStore) {
                                isExists = false;
                                for (i = 0; i < valueArr.length; i++) {
                                    if (storeKey === valueArr[i]) {
                                        isExists = true;
                                        break;
                                    }
                                }
                                //如果输入框 分割的值，不包含在已选store 内，则删除
                                if (!isExists) {
                                    delete p._checkedNodeStore[storeKey];
                                }
                            }
                            //反选所有节点
                            treeObj.checkAllNodes(false);
                            //选中剩余
                            for (storeKey in p._checkedNodeStore) {
                                treeObj.checkNode(p._checkedNodeStore[storeKey], true, false, false);
                            }
                        } else {
                            p._checkedNodeStore = {};
                        }
                        //
                        if (p.view.checkedSyncValue) {
                            _setValueByCheckedNodes.call(_thisElm);
                        }
                    }
                }
                // 键盘Keycode范围（上下左右）
                if (event.keyCode === 37 || event.keyCode === 38 || event.keyCode === 39 || event.keyCode === 40) {
                    $tree = $("#" + p.getTreeId.call(_thisElm), p._dataPanel);
                    //先确定下拉是否显示
                    if (!p.isDropDownShow) {
                        p.showDropDown.call(_thisElm, true);
                    }
                    var allNodes = _getTreeNodeSortArray.call(_thisElm);
                    //
                    if (allNodes.length > 0) {
                        //
                        p._treeNodeHeight = p._treeNodeHeight > 0 ? p._treeNodeHeight : $("#" + allNodes[0].tId + "_a").height();
                        //上38下40箭头
                        if (event.keyCode === 40 || event.keyCode === 38) {
                            //按向下箭头
                            if (event.keyCode === 40) {
                                if (p._focusNodeIndex === -1) {//首次点击
                                    p._focusNodeIndex = 0;
                                } else {
                                    p._focusNodeIndex += 1;
                                    if (p._focusNodeIndex === allNodes.length) {
                                        p._focusNodeIndex = 0;
                                    }
                                }
                                if (p._focusNodeIndex > allNodes.length) {
                                    p._focusNodeIndex = 0;
                                }
                                p._currentFocusNode = allNodes[p._focusNodeIndex];
                            }
                            //按向上箭头
                            if (event.keyCode === 38) {
                                if (p._focusNodeIndex === -1) {//首次点击
                                    p._focusNodeIndex = allNodes.length - 1;
                                } else {
                                    p._focusNodeIndex -= 1;
                                    if (p._focusNodeIndex === -1) {
                                        p._focusNodeIndex = allNodes.length - 1;
                                    }
                                }
                                if (p._focusNodeIndex > allNodes.length) {
                                    p._focusNodeIndex = 0;
                                }
                                p._currentFocusNode = allNodes[p._focusNodeIndex];
                            }
                            //驱动 数据网格所在下拉，滚动条滚动
                            $tree[0].scrollTop = (p._treeNodeHeight * p._focusNodeIndex);
                            //设置 选中行 效果
                            _setSelectedNodeEffect.call(_thisElm, p._currentFocusNode);
                        }
                        //左37右39箭头
                        if ((event.keyCode === 37 || event.keyCode === 39) && p._currentFocusNode) {
                            childrenNodes = p._currentFocusNode.children ? p._currentFocusNode.children : null;
                            //按向右箭头
                            if (event.keyCode === 39) {
                                if (p._currentFocusNode.isParent && childrenNodes && childrenNodes.length > 0) {
                                    treeObj.expandNode(p._currentFocusNode, true, false, false, true);
                                }
                            }
                            //按向左箭头
                            if (event.keyCode === 37) {
                                if (p._currentFocusNode.isParent && childrenNodes && childrenNodes.length > 0) {
                                    treeObj.expandNode(p._currentFocusNode, false, false, false, true);
                                }
                            }
                        }
                    }
                }
                //键盘Keycode范围（回车）
                if (event.keyCode === 13) {
                    //下拉不显示不执行
                    if (!p.isDropDownShow) {
                        return;
                    }
                    //回车在树节点上
                    var $aTreeNodeElem = p._dataPanel.find("a.autoTree-node-selected[treenode_a]");
                    if ($aTreeNodeElem && $aTreeNodeElem.length == 1) {
                        _onSelectTreeNode.call(_thisElm, $aTreeNodeElem, event);
                    }
                }
            }
            //如果控件的值被清空，同时把其它绑定填充的也清空
            if ($.trim($this.val()).length === 0) {
                for (selector in bindFill) {
                    if (bindFill[selector] && bindFill.hasOwnProperty(selector)) {
                        $(selector).val("");
                    }
                }
            }
            //
            if ($.isFunction(p.callback.afterInput)) {
                p.callback.afterInput.call(_thisElm, event);
            }
            // TODO:暂时不需要 停止事件冒泡
            // event.stopPropagation();
        },

        /**
         *
         * @param event
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
            if (p.callback.beforeLose && !p.callback.beforeLose.call(_thisElm, event)) {
                return;
            }
            //判断鼠标焦是否离开 下拉面板(mouse out)
            if (!p._isMouseOutDropDown) {
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
            event.stopPropagation();
        },

        /**
         *
         */
        _bindDropDownEvents = function () {
            var _thisElm = this, p = _thisElm.p;
            // 避免重复绑定
            if (p._isBindEventDropDown) {
                return;
            }
            p._dropDown.off('mouseover.auto-tree' + p.id);
            p._dropDown.off('mouseout.auto-tree' + p.id);
            // 绑定鼠标移入事件
            p._dropDown.on('mouseover.auto-tree' + p.id, function (event) {
                _onMouseOverDropDown.call(_thisElm, event);
            });
            // 绑定鼠标移出事件
            p._dropDown.on('mouseout.auto-tree' + p.id, function (event) {
                _onMouseOutDropDown.call(_thisElm, event);
            });
            p._isBindEventDropDown = true;
        },

        /**
         * 鼠标移入下拉
         * @param event
         */
        _onMouseOverDropDown = function (event) {
            var _thisElm = this, p = _thisElm.p;
            //
            p._isMouseOutDropDown = false;
            //
            event.stopPropagation();
        },

        /**
         * 鼠标离开下拉
         * @param event
         */
        _onMouseOutDropDown = function (event) {
            var _thisElm = this, p = _thisElm.p;
            //
            p._isMouseOutDropDown = true;
            //
            event.stopPropagation();
        },

        /**
         *
         * @private
         */
        _setValueByCheckedNodes = function () {
            var _thisElm = this, p = _thisElm.p, storeKey, value = "";
            //拼接到输入框
            for (storeKey in p._checkedNodeStore) {
                value += (storeKey + p.view.separator);
            }
            //情况4/4：控件渲染后，通过其它js方式赋值（此时联想控件才渲染）
            if (p.view.isRememberValue) {
                p._isCurrentValueFromTree = false;
                p._isAlreadyRemember = true;
                p._rememberValue = value;
            }
        },

        /**
         * 验证指定的唯一字段，是否存在多次
         * @param checkedNodes
         * @private
         */
        _validateViewUniqueField = function (checkedNodes) {
            var _thisElm = this, p = _thisElm.p,
                i, j, existsCount = 0,
                iNode, jNode;
            for (i = 0; i < checkedNodes.length; i++) {
                iNode = checkedNodes[i];
                existsCount = 0;
                for (j = 0; j < checkedNodes.length; j++) {
                    jNode = checkedNodes[j];
                    if (iNode[p.view.viewUniqueFieldName] === jNode[p.view.viewUniqueFieldName]) {
                        existsCount++;
                    }
                }
                if (existsCount >= 2) {
                    if (console && console.warn) {
                        console.warn(($.oui.utils.string.format($.autoTree.i18n.info.keepTheLastOneNodeData, iNode[p.view.viewUniqueFieldName])));
                    }
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
                    case "remote" :
                        _onRemote.call(_thisElm);
                        break;
                    case "onceRemote" :
                        _onOnceRemote.call(_thisElm);
                        break;
                    case "local" :
                        _onLocal.call(_thisElm);
                        break;
                }
            });
        },

        /**
         * 远程（异步）数据实现
         */
        _onRemote = function () {
            var _thisElm = this, p = _thisElm.p;
            // 显示等待条
            p.showLoadingBar.call(_thisElm);
            //
            _onAjaxRequest.call(_thisElm);
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
                p.callback.setRequestData.call(_thisElm, p.async.data);
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
         * @param treeData
         * @param st
         * @param xhr
         */
        _onAjaxSuccess = function (treeData, st, xhr) {
            var _thisElm = this, p = _thisElm.p;
            // 1、
            if ($.isFunction(p.callback.beforeAjaxSuccess)) {
                p.callback.beforeAjaxSuccess.call(_thisElm, treeData, st, xhr);
            }
            if (!$.isArray(treeData)) {
                _onAjaxError.call(_thisElm, xhr, st, "Response data format error,data must be array!");
                return;
            }
            if (treeData && treeData.length <= 0) {
                //数据为空
                //p._dataPanel.append("<span>" + p.view.emptyRecordsHtml + "</span>");
            } else {
                if (p.async.dataSourceType === 'onceRemote' || p.async.dataSourceType === 'remote') {
                    // 原始数据存储(私有)
                    p._originalDataStore = $.extend(true, [], treeData);
                    //
                    if (!p.view.isOnActionIgnoreFilter) {
                        // 过滤节点数据
                        _filterTreeNode.call(_thisElm, _thisElm.value);
                    } else {
                        //添加节点到树
                        _addNodesToTree.call(_thisElm, p._originalDataStore);
                    }
                } else {
                    // 原始数据存储(私有)
                    p._originalDataStore = $.extend(true, [], treeData);
                    //添加节点到树
                    _addNodesToTree.call(_thisElm, p._originalDataStore);
                }
            }
            // 装载完成
            p._isLoaded = true;
            //
            p._actionStop = true;
            // 隐藏等待条
            p.hideLoadingBar.call(_thisElm);
            //
            if ($.isFunction(p.callback.afterLoad)) {
                p.callback.afterLoad.call(_thisElm, p._originalDataStore);
            }
            //
            if (!p._justReload) {
                p.showDropDown.call(_thisElm, true);
            } else {
                p._justReload = false;
            }
        },

        _onAjaxError = function (xhr, st, err) {
            var _thisElm = this, p = _thisElm.p;
            if ($.isFunction(p.callback.afterAjaxError)) {
                p.callback.afterAjaxError.call(_thisElm, xhr, st, err);
            }
            xhr = null;
            // 隐藏等待条
            p.hideLoadingBar.call(_thisElm);
            throw new Error("Request error:" + err);
        },

        /**
         * 本页数据实现
         */
        _onLocal = function () {
            var _thisElm = this, p = _thisElm.p;
            if (!$.isArray(p.view.localData)) {
                _onAjaxError.call(_thisElm, null, null, "Response data format error,data must be array!");
                return;
            }
            // 显示等待条
            p.showLoadingBar.call(_thisElm);
            // 原始数据存储(私有)
            p._originalDataStore = $.extend(true, [], p.view.localData);
            //添加节点到树
            _addNodesToTree.call(_thisElm, p._originalDataStore);
            //
            if (!p._justReload) {
                p.showDropDown.call(_thisElm, true);
            } else {
                p._justReload = false;
            }
            //判断 设置选中第一行
            //p.setSelectedFirstRow.call(_thisElm);
            // 装载完成
            p._isLoaded = true;
            //
            if ($.isFunction(p.callback.afterLoad)) {
                p.callback.afterLoad.call(_thisElm, p._originalDataStore);
            }
            //
            p._actionStop = true;
            // 隐藏等待条
            p.hideLoadingBar.call(_thisElm);
        },

        ///**
        // * 设置数据id
        // *
        // * @param rowDatas
        // * @private
        // */
        //_setRowId = function (rowDatas) {
        //    var _thisElm = this, p = _thisElm.p,
        //        rowDataNum = 0, rowData = {},
        //        rowIdPropertyName = p.getDataRowIdPropertyName.call(_thisElm);
        //    if (rowDatas) {
        //        for (; rowDataNum < rowDatas.length; rowDataNum++) {
        //            rowData = rowDatas[rowDataNum];
        //            rowData[rowIdPropertyName] = "id" + rowDataNum;
        //        }
        //    }
        //},

        /**
         * 添加节点到树
         * @param dataStore
         * @private
         */
        _addNodesToTree = function (dataStore) {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm),
                strArr, str, data, i, j;
            // 清空所以节点
            p.removeAllNodes.call(_thisElm);
            //如果是多选
            if (p.treeConfig.check.enable && $this.val() && $this.val().length > 0) {
                //
                strArr = $this.val().split(p.view.separator);
                //
                for (i = 0; i < dataStore.length; i++) {
                    data = dataStore[i];
                    for (j = 0; j < strArr.length; j++) {
                        str = strArr[j];
                        if (data[p.view.viewUniqueFieldName] === str) {
                            //选中已选节点
                            data.checked = true;
                            break;
                        }
                    }
                }
            }
            if (p.async.dataSourceType === 'local') {
                var treeId = p.getTreeId.call(_thisElm);
                $.fn.zTree.init($("#" + treeId), p.getTreeObj.call(_thisElm).setting, p.view.localData);
            } else {
                // 添加节点到树
                p.getTreeObj.call(_thisElm).addNodes(null, dataStore);
            }
        },

        /**
         * 获取匹配值的节点
         * @param inputValue
         * @param nodes
         * @returns {Array}
         */
        _getMatchNodes = function (inputValue, nodes) {
            var _thisElm = this, p = _thisElm.p,
                treeObj = p.getTreeObj.call(_thisElm),
                treeObjSetting = treeObj.setting,
                inputFilterFieldNames = [],
                matchNodes = [], matchChildrenNodes = [],
                node, childes,
                i, k;

            //过滤的字段
            if (p.view.inputFilterFieldNames.length === 0) {
                inputFilterFieldNames.push(treeObjSetting.data.key.name);
            } else {
                inputFilterFieldNames = $.extend(true, [], p.view.inputFilterFieldNames);
            }
            //
            for (i = 0; i < nodes.length; i++) {
                node = nodes[i];
                matchChildrenNodes = [];
                childes = node[treeObjSetting.data.key.children];
                if (childes && childes.length > 0) {
                    matchChildrenNodes = _getMatchNodes.call(_thisElm, inputValue, childes);
                    //如果子节点有匹配的，那么把当前节点也加入
                    if (matchChildrenNodes.length > 0) {
                        matchNodes.push(node);
                        matchNodes = matchNodes.concat(matchChildrenNodes);
                    } else {
                        for (k = 0; k < inputFilterFieldNames.length; k++) {
                            //判断当前节点 根据过滤字段，是否匹配 输入的值
                            if (node[inputFilterFieldNames[k]] &&
                                node[inputFilterFieldNames[k]].indexOf(inputValue) > -1) {
                                matchNodes.push(node);
                                break;
                            }
                        }
                    }
                } else {
                    for (k = 0; k < inputFilterFieldNames.length; k++) {
                        //判断当前节点 根据过滤字段，是否匹配 输入的值
                        if (node[inputFilterFieldNames[k]] &&
                            node[inputFilterFieldNames[k]].indexOf(inputValue) > -1) {
                            matchNodes.push(node);
                            break;
                        }
                    }
                }
            }
            return matchNodes.concat(matchChildrenNodes);
        },

        /**
         * 选择行事件实现
         */
        _onSelectTreeNode = function ($treeNode) {
            //触发节点点击
            $treeNode.trigger("click");
        },

        /**
         * 扩展树配置
         * @private
         */
        _extendTreeConfig = function () {
            var _thisElm = this, p = _thisElm.p;
            p.treeConfig.callback = p.treeConfig.callback ? p.treeConfig.callback : {};

            //扩展：树节点点击事件
            var _onClickTreeNodePrefix = function (event, treeId, treeNode, clickFlag) { // 树节点点击前缀事件
                    var _thisElm = this, p = _thisElm.p,
                        selector, fieldName, bindFill = p.view.bindFill,
                        treeObj = p.getTreeObj.call(_thisElm);
                    //单多选情况1/2：单选树
                    if (!p.treeConfig.check.enable) {
                        //自动填充
                        if (!$.isEmptyObject(bindFill)) {
                            for (selector in bindFill) {
                                if (bindFill[selector] && bindFill.hasOwnProperty(selector)) {
                                    fieldName = bindFill[selector];
                                    $(selector).val(treeNode[fieldName]);
                                }
                            }
                        }
                    } else {
                        //!treeNode.checked 为了区分正选反选（会联动触发 _onCheckTreeNodePrefix）
                        treeObj.checkNode(treeNode, !treeNode.checked, false, true);
                    }
                },
                //选中
                _onClickTreeNodeSuffix = function () { // 树节点点击后缀事件
                    var _thisElm = this, p = _thisElm.p, $this = $(_thisElm);
                    //情况3/4：值从数据面板选择
                    if (p.view.isRememberValue) {
                        //输入框值 来自于tree
                        p._isCurrentValueFromTree = true;
                        //
                        p._rememberValue = $this.val();
                        //
                        p._isAlreadyRemember = true;
                    }
                    // 关闭下拉框
                    p.hideDropDown.call(_thisElm);
                };
            if (p.treeConfig.callback.onClick) {
                p.treeConfig.callback.onClickHandle = p.treeConfig.callback.onClick;
                p.treeConfig.callback.onClick = function (event, treeId, treeNode, clickFlag) {
                    //
                    _onClickTreeNodePrefix.call(_thisElm, event, treeId, treeNode, clickFlag);
                    //
                    p.treeConfig.callback.onClickHandle.call(_thisElm, event, treeId, treeNode, clickFlag);
                    //
                    _onClickTreeNodeSuffix.call(_thisElm, event, treeId, treeNode, clickFlag);
                }
            } else {
                p.treeConfig.callback.onClick = function (event, treeId, treeNode, clickFlag) {
                    var treeObj = p.getTreeObj.call(_thisElm);
                    //
                    _onClickTreeNodePrefix.call(_thisElm, event, treeId, treeNode, clickFlag);
                    //
                    _onClickTreeNodeSuffix.call(_thisElm, event, treeId, treeNode, clickFlag);
                    //多选树
                    if (p.treeConfig.check.enable) {
                        //勾选，并触发勾选事件
                        treeObj.checkNode(treeNode, true, false, true);
                    }
                }
            }
            //扩展：树的节点展开事件
            //if (p.treeConfig.callback.onExpand) {
            //    p.treeConfig.callback.onExpandHandle = p.treeConfig.callback.onExpand;
            //    p.treeConfig.callback.onExpand = function (event, treeId, treeNode) {
            //        p.treeConfig.callback.onExpandHandle.call(_thisElm, event, treeId, treeNode);
            //    }
            //}
            //扩展：树的节点勾选事件
            //勾选后缀实现
            var _onCheckTreeNodePrefix = function (event, treeId, treeNode) {
                var _thisElm = this, p = _thisElm.p,
                    treeObj = p.getTreeObj.call(_thisElm),
                    checkedNodes,
                    storeKey,
                    value = "";

                //单多选情况2/2：多选树
                if (p.treeConfig.check.enable) {
                    //验证已选的树节点，唯一字段值是否唯一
                    checkedNodes = treeObj.getCheckedNodes(true);
                    _validateViewUniqueField.call(_thisElm, checkedNodes);
                    //正选
                    if (treeNode.checked) {
                        //以树节点数据，唯一字段值作为 key
                        p._checkedNodeStore[treeNode[p.view.viewUniqueFieldName]] = treeNode;
                    } else {
                        //反选
                        delete p._checkedNodeStore[treeNode[p.view.viewUniqueFieldName]];
                    }
                    if (p.view.checkedSyncValue) {
                        //拼接到输入框
                        for (storeKey in p._checkedNodeStore) {
                            value += (storeKey + p.view.separator);
                        }
                        //赋值
                        _thisElm.value = value;
                        //
                        _setValueByCheckedNodes.call(_thisElm)
                    }
                }
            };
            if (p.treeConfig.callback.onCheck) {
                p.treeConfig.callback.onCheckHandle = p.treeConfig.callback.onCheck;
                p.treeConfig.callback.onCheck = function (event, treeId, treeNode) {
                    p.treeConfig.callback.onCheckHandle.call(_thisElm, event, treeId, treeNode);
                    //
                    _onCheckTreeNodePrefix.call(_thisElm, event, treeId, treeNode);
                }
            } else {
                p.treeConfig.callback.onCheck = function (event, treeId, treeNode) {
                    //
                    _onCheckTreeNodePrefix.call(_thisElm, event, treeId, treeNode);
                }
            }
        },

        /**
         *
         */
        _getTreeNodeSortArray = function () {
            var _thisElm = this, p = _thisElm.p, treeObj, sortArray, isParentOpenHiddenByRecursion;
            treeObj = p.getTreeObj.call(_thisElm);
            //递归判断父节点是是否 是隐藏 或 未展开
            isParentOpenHiddenByRecursion = function (node) {
                if (node.getParentNode()) {
                    if (!node.getParentNode().isHidden && node.getParentNode().open) {
                        if (node.getParentNode().getParentNode()) {
                            return isParentOpenHiddenByRecursion(node.getParentNode());
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            };
            sortArray = treeObj.getNodesByFilter(function (node) {
                if (!node.isHidden) {
                    if (node.getParentNode()) {
                        return isParentOpenHiddenByRecursion(node);
                    } else {
                        return true;
                    }
                }
                return false;
            });
            return sortArray;
        },

        /**
         * 控件下拉显示事件
         */
        _onAction = function (event) {
            var _thisElm = this, p = _thisElm.p, $this = $(_thisElm);
            if (p._actionStop) {
                p._actionStop = false;
            } else {
                //触发没有停止，返回
                return;
            }
            //焦点事件分为：人为聚焦 和 js脚本聚焦
            if (event && event.type === "focus") {
                if (event.relatedTarget) {
                } else {
                    //js脚本聚焦(只聚焦，不执行_onAction)
                    p._actionStop = true;
                    return;
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
            if ($this.prop("readonly") && !p.view.isAllowReadonlyAction) {
                p._actionStop = true;
                return;
            }
            //情况2/4：控件渲染后，通过其它js方式赋值（此时联想控件才渲染）
            if ($this.val() && p.view.isRememberValue) {
                p._isCurrentValueFromTree = true;
                p._isAlreadyRemember = true;
                p._rememberValue = $this.val();
            }
            if (p.callback.beforeAction && !p.callback.beforeAction.call(_thisElm, event)) {
                p._actionStop = true;
                return;
            }
            //如果是多选
            if (p.treeConfig.check.enable) {
                //光标定位到最后
                //_cursorToEnd.call(_thisElm);
            }
            // 下拉、数据面板、是否已经装载完
            if (!p._isLoaded) {
                // 重新装载数据
                _onLoad.call(_thisElm);
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
                    p._dropDown = $dpc.addClass("autoTree-dropDown").css({
                        "width": p.view.width,
                        "height": p.view.height,
                        "overflow": "auto"
                    });
                    p._dropDown.show();
                } else {
                    p._dropDown = $('<div id="' + cid + '" class="autoTree-dropDown"></div>');
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
                p._dropDown.append(_createLoadingBar.call(_thisElm));
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
         * 创建数据面板
         * @returns {*|HTMLElement}
         */
        _createDataPanel = function () {
            var _thisElm = this, p = _thisElm.p, dpid = p.id + 'DataPanel';
            // 页面有可能已创建过（在表格内有可能被反复创建）
            p._dataPanel = $("#" + dpid);
            if (p._dataPanel && p._dataPanel.length > 0) {
                // alert("_dataPanel已经创建");
            } else {
                p._dataPanel = $('<div id="' + dpid + '"><ul id="' + p.getTreeId.call(_thisElm) + '" class="ztree"></ul></div>');
                $(document.body).append(p._dataPanel);
            }
            // 绑定事件
            //p.bindDataPanelEvents.call(_thisElm);
            //
            return p._dataPanel;
        },

        /**
         * 根据输入值过滤节点
         * @param inputValue
         * @returns {*}
         */
        _filterTreeNode = function (inputValue) {
            return $(this).each(function () {
                var _thisElm = this, p = _thisElm.p,
                    treeObj = p.getTreeObj.call(_thisElm),
                    matchNodes,
                    nodes = treeObj.getNodes();

                if (inputValue && inputValue.length > 0) {
                    //先隐藏全部节点
                    p.hideTreeNodes.call(_thisElm, nodes);
                    //再显示匹配的节点
                    matchNodes = _getMatchNodes.call(_thisElm, inputValue, nodes);
                    if (matchNodes && matchNodes.length > 0) {
                        treeObj.showNodes(matchNodes);
                    }
                } else {
                    //显示全部节点
                    p.showTreeNodes.call(_thisElm, nodes, true);
                }
            });
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
        };

})
(jQuery);


var AutoTreeUtil = AutoTreeUtil || {

    /**
     * 渲染所有联想树
     */
    render: function () {
        //
        $("input[componentType='auto-tree']").each(function (index, inputEl) {
            var $inputEl = $(inputEl),
                componentConfigName = $inputEl.attr("componentConfig"),
                componentConfigExt = $inputEl.attr("componentConfigExt"),
                bindFill = $inputEl.attr('bindFill') ? JSON.parse($inputEl.attr('bindFill')) : {},
                componentConfig = {};
            if (!componentConfig) {
                return;
            }
            if ($.isFunction(AutoTreeUtil[componentConfigName])) {
                componentConfig = AutoTreeUtil[componentConfigName].call(AutoTreeUtil);
            } else if ($.isPlainObject(AutoTreeUtil[componentConfigName])) {
                componentConfig = AutoTreeUtil[componentConfigName];
            }
            if ($.isFunction(eval(componentConfigExt))) {
                componentConfig = $.extend(true, {}, componentConfig, {view: {"bindFill": bindFill}}, eval(componentConfigExt)());
            } else {
                componentConfig = $.extend(true, {}, componentConfig, {view: {"bindFill": bindFill}}, eval(componentConfigExt));
            }
            //渲染控件
            $inputEl.AutoTree(componentConfig);
        });
        $("[componentType='auto-tree-trigger']").each(function (index, elm) {
            var $elm = $(elm),
                triggerElementId = $elm.attr("triggerElementId");
            if (triggerElementId) {
                $elm.bind("click", function (event) {
                    $("#" + triggerElementId).AutoTree("triggerAction", event);
                });
            } else {
                alert("没有配置triggerElementId");
            }
        });
    }
};