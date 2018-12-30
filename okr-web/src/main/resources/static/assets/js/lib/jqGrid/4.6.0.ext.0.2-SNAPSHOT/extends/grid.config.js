;
(function ($) {
    $.jgrid = $.jgrid || {};

    //* 1、值是字符 居左
    //* 2、值是数值 居右
    //  3、值长度固定相同 居中
    //*
    //* 4、列头标题宽度：是4个中文 宽度70，加一个中文字宽度加15,少一个字少15（5个中文85，6个中文100，7个中文115，8个中文130）
    //* 5、值宽度：如果是（金额、价格、合计、成本）长数值的，以值优先默认100
    //* 6、如果值宽度，少于列头标题宽度，以更长的为准

    $.jgrid.defaults = $.jgrid.defaults || {};
    $.extend($.jgrid.defaults, {

        /** 原生属性 */
        datatype: "json",
        page: 1,
        rowNum: 10,
        altRows: true,
        altclass: 'trEven',
        viewsortcols: [false],
        viewrecords: true,
        mtype: 'post',
        cmTemplate: {title: false, sortable: false},
        loadtext: "<div class='ui-jqgrid-loading-img'></div>",//将等待提示文本置空，配合样式“.loading-img” 一起使用

        /** 扩展属性 */
        enabledLoad: true,
        // 用来标记状态的列名
        rowStateName: "rowState",
        // 激活单元格 文本对齐方式（字符居左，数值居右）
        enabledCellTextAlign: true,
        // 激活单元格 变为可编辑状态后 选中文本
        enabledCellSelect: true,
        //自动宽度的间隔宽度
        autoWidthPadding: 0,
        //自动高度的间隔高度
        autoHeightPadding: 0,

        //上分页栏时 不显示分页按钮
        topPagerButtons: true,

        //上分页栏时 不显示分页数据信息
        topPagerRecordInfo: true,

        //与后台page的命名映射
        pageJsonNameMap: {pageInfo: "pageInfo"},

        //隐藏底部分页栏
        hideBottomPager: false,

        //隐藏 分页记录条数 的下拉选择
        hideRowList: false,

        //启用 行数据改变存储
        enabledChangeStore: false,
        //表头数组 当表头大于1时，需要设置
        groupHeaders: [],

        /** 私有扩展属性 */
        _gridDataStore: {},
        _originalDatas: {},
        _changeStore: {},
        //标识 调用了此接口的 postData 为最优先取用（比如：分页码、排序字段、查询条件）
        _isSetRequestData: false,

        /** ********** begin 扩展的可回调接口 */
        /**
         *单元格 变为可编辑 后事件
         *参数说明(行Id、列名、单元格值、单元格行下标、单元格列下标)
         *使用方式
         * afterGridEditCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
         * }
         */
        afterGridEditCell: null,
        /**
         *单元格 从编辑控件变为 不可编辑 前事件
         *参数说明(行Id、列名、单元格值、单元格行下标、单元格列下标)
         *使用方式
         * beforeGridSaveCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
         *  //必须返回单元格值
         *  return cellValue;
         *}
         */
        beforeGridSaveCell: null,
        /**
         *单元格 回复不可编辑 后事件
         *参数说明(行Id、列名、单元格值、单元格行下标、单元格列下标)
         *使用方式
         * afterGridSaveCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
         * }
         */
        afterGridSaveCell: null,
        /**
         *行点击 前事件
         *参数说明(行Id、事件对象)
         *使用方式
         * beforeSelectGridRow: function (rowId, event) {
         *  //必须返回 true/false
         *  return true;
         * }
         */
        beforeSelectGridRow: null,
        /**
         *行点击 事件
         *参数说明(行Id、行点击状态（true/false）、事件对象)
         *使用方式
         * onSelectGridRow: function (rowId, status, event) {
         * }
         */
        onSelectGridRow: null,
        /**
         *不可编辑单元格 选中 事件
         *参数说明(行Id、列名、单元格值、单元格行下标、单元格列下标)
         *使用方式
         * afterSelectGridCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
         * }
         */
        afterSelectGridCell: null,
        /**
         *发起远程请求前事件
         *使用方式
         * beforeGridRequest: function () {
         *  //必须返回 true/false
         *  return true;
         * }
         */
        beforeGridRequest: null,
        /**
         *jqGrid ajax请求成功后，数据处理前事件。返回false后续动作将不进行。
         *参数说明(请求成功的数据,请求状态,XMLHttpRequest对象)
         *使用方式
         * beforeGridProcessing: function (data,textStatus,xhr) {
         * //必须返回 true/false
         *  return true;
         * }
         */
        beforeGridProcessing: null,
        /**
         *插入行数据 后事件
         *
         *参数说明(行ID,行数据,行元素(tr))
         *使用方式
         * afterInsertGridRow: function (rowId,rowData,rowElem) {
         * }
         */
        afterInsertGridRow: null,
        /*
         *表格 已经构造完成（在loadComplete之前）
         *配置方式
         * afterGridComplete: function () {
         * }
         */
        afterGridComplete: null,
        /**
         *表格 完成数据装载（在afterGridComplete之后）
         *参数说明(表格数据)
         *配置方式
         * afterLoadGridComplete: function (gridData) {
         * }
         */
        afterLoadGridComplete: null,
        /**
         *当单元格变成可编辑状态时，的键盘输入(keyup)事件
         *参数说明(事件对象、行Id、列名、单元格值、单元格行下标、单元格列下标)
         *使用方式
         * onEditCellKeyup: function (event, rowId, colName, cellValue, cellRowIndex, cellColIndex) {
         *
         * }
         */
        onEditCellKeyup: null,

        /**
         *当点击排序列但是数据还未进行变化时触发此事件。
         *参数说明(列名,列位置索引,排序状态desc或者asc)
         *使用方式
         * beforeSortCol: function (colName,colIndex,sortType) {
         * //必须返回 true/false
         *  return true;
         * }
         */
        beforeSortCol: null,

        /**
         * 展开行 之前事件。返回false后续动作将不进行。
         * 参数说明(行展开区域Id，行Id)
         * 使用方式
         * beforeSubGridRowExpand: function (subGridRowId,rowId) {
         * //必须返回 true/false
         *  return true;
         * }
         */
        beforeSubGridRowExpand: null,

        /**
         * 展开行 之后事件。
         * 参数说明(行展开区域Id，行Id)
         * 使用方式
         * afterSubGridRowExpand: function (subGridRowId,rowId) {
         * }
         */
        afterSubGridRowExpand: null,

        /**
         * 收缩行 之前事件。返回false后续动作将不进行。
         * 参数说明(行展开区域Id，行Id)
         * 使用方式
         * beforeSubGridRowCollapse: function (subGridRowId,rowId) {
         * //必须返回 true/false
         *  return true;
         * }
         */
        beforeSubGridRowCollapse: null,

        /**
         * 初始化表格后
         */
        afterInitGrid: null,

        /** ********** end 扩展的可回调接口 */

        /** ********** begin 对原生回调接口的实现
         * （一般不推荐直接实现原生回调接口，例：原生回调 beforeRequest 被框架实现后，提供了 beforeGridRequest 可用）
         */

        /**
         *发起远程请求前事件
         * @returns {boolean}
         */
        beforeRequest: function () {

            var _grid = this,
                p = _grid.p,
                postData = p.postData,
                pageInfo = {};
            //
            if (p.enabledLoad) {
                if ($.isFunction(p.beforeGridRequest)) {
                    //返回false不能再继续后面
                    if (!p.beforeGridRequest.call(_grid)) {
                        return false;
                    }
                }

                //原生分页命名映射 prmNames: {page:"page",rows:"rows", sort: "sidx",order: "sord"}
                //
                if ($.isPlainObject(eval("postData." + p.pageJsonNameMap.pageInfo))) {
                    pageInfo = eval("postData." + p.pageJsonNameMap.pageInfo);
                    //
                    if (p._isSetRequestData) {
                        if (pageInfo[p.jsonReader.page] > 0) {
                            //当前页码 取开发自定义的（扩展的 pageInfo 赋值给 原生）
                            p.page = pageInfo[p.jsonReader.page];
                            postData[p.prmNames.page] = pageInfo[p.jsonReader.page];
                        } else {
                            //取 postData 的
                            p.page = postData[p.prmNames.page];
                            pageInfo[p.jsonReader.page] = postData[p.prmNames.page];
                        }
                        if (pageInfo[p.jsonReader.rows] > 0) {
                            //每页行数 取开发自定义的（扩展的 pageInfo 赋值给 原生）
                            p.rowNum = pageInfo[p.jsonReader.rows];
                            postData[p.prmNames.rows] = pageInfo[p.jsonReader.rows];
                        } else {
                            //取 postData 的
                            p.rowNum = postData[p.prmNames.rows];
                            pageInfo[p.jsonReader.rows] = postData[p.prmNames.rows];
                        }
                        //取用完后，至为false
                        p._isSetRequestData = false;
                    } else {
                        //当前页码 支持原生的（原生 赋值给 扩展的 pageInfo）
                        if (postData[p.prmNames.page] > 0) {
                            //取 postData 的
                            p.page = postData[p.prmNames.page];
                            pageInfo[p.jsonReader.page] = postData[p.prmNames.page];
                        }
                        //每页行数 支持原生的（原生 赋值给 扩展的 pageInfo）
                        if (postData[p.prmNames.rows] > 0) {
                            //取 postData 的
                            p.rowNum = postData[p.prmNames.rows];
                            pageInfo[p.jsonReader.rows] = postData[p.prmNames.rows];
                        }
                    }
                }
                //序列化情况1/2，将请求的数据序列化
                if (p.ajaxGridOptions.contentType == "application/json") {
                    //如果没有实现自定义的序列化（兼容之前开发方式）
                    if (!$.isFunction(p.serializeGridData)) {
                        p.postData = $.isPlainObject(postData) ? JSON.stringify(postData) : postData;
                    }
                }
                return true;
            } else {
                return false;
            }
        },

        /**
         * 翻页时请求事件
         */
        onGridPaging: null,

        onPaging: function () {
            var _grid = this, p = _grid.p, rel = void 0;

            if ($.isFunction(p.onGridPaging)) {
                var args = $.makeArray(arguments);
                rel = p.onGridPaging.apply(_grid, args);
            }

            return rel;
        },

        /**
         * jqGrid ajax请求成功后，数据处理前事件。返回false后续动作将不进行。
         * @param data 请求成功的数据
         * @param textStatus 请求状态
         * @param xhr XMLHttpRequest 对象
         */
        beforeProcessing: function (data, textStatus, xhr) {
            var _grid = this, p = _grid.p,
                jsonReader = _grid.p.jsonReader, rowsKey = jsonReader.root,
                rows = [], rowData = {};
            if ($.isFunction(p.beforeGridProcessing)
                && p.beforeGridProcessing.call(_grid, data, textStatus, xhr) === false) {
                return;
            }
            if (!data.hasOwnProperty(jsonReader.root)) {
                throw new Error("数据结构错误，必须有 " + jsonReader.root + " 属性！");
            }
            //1.加载数据后(响应为success)
            if (data) {
                //重新查询必须清空
                _grid.p._originalDatas = {};
                _grid.p._gridDataStore = {};
                //原始数据
                _grid.p._originalDatas = $.extend({}, data);
                //grid的实际数据存储
                rows = data[rowsKey];
                if (rows) {
                    for (var i = 0, length = rows.length; i < length; i++) {
                        rowData = rows[i];
                        // 将主键空格去除
                        rowData[jsonReader.id] = $.trim(rowData[jsonReader.id]);
                        //将数据存储到store
                        _grid.p._gridDataStore[rowData[jsonReader.id]] = rowData;
                    }
                }
            }
        },

        /**
         * 单元格 变为可编辑控件 后事件
         * @param rowId 行id
         * @param colName 列名
         * @param cellValue 单元格值
         * @param cellRowIndex 单元格行下标
         * @param cellColIndex 单元格列下标
         */
        afterEditCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
            var _grid = this, $grid = $(_grid), p = _grid.p;
            if (p.enabledCellTextAlign == true ||
                p.enabledCellSelect == true) {
                //取到单元格
                var cellElmId = cellRowIndex + "_" + colName,
                    $cellElm = $("#" + cellElmId),
                    currentColModel = $grid.jqGrid("getColModelByName", colName);
                //单元格变为可编辑控件时，选中控件所有值
                if ($cellElm.is("input:text") || $cellElm.is("textarea")) {
                    if (p.enabledCellSelect == true) {
                        var selectTimeObj = window.setTimeout(function () {
                            //选中全部文本
                            $cellElm.select();
                            window.clearTimeout(selectTimeObj);
                        }, 300);
                    }
                    if (p.enabledCellTextAlign === true) {
                        //数值列居右，其他统一居左
                        if (currentColModel.formatter
                            && (currentColModel.formatter === 'integer'
                                || currentColModel.formatter === 'number'
                                || currentColModel.formatter === 'currency'
                                || currentColModel.formatter === 'currencyExt'
                                || currentColModel.formatter === 'percent')) {
                            $cellElm.css({"text-align": "right"});
                        } else {
                            $cellElm.css({"text-align": "left"});
                        }
                    }
                }
            }
            if ($.isFunction(_grid.p.afterGridEditCell)) {
                _grid.p.afterGridEditCell.call(_grid, rowId, colName, cellValue, cellRowIndex, cellColIndex);
            }
        },

        /**
         * 单元格 从编辑控件变为 不可编辑 前事件
         * @param rowId 行id
         * @param colName 列名
         * @param cellValue 单元格值
         * @param cellRowIndex 单元格行下标
         * @param cellColIndex 单元格列下标
         */
        beforeSaveCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
            var _grid = this;
            //
            if ($.isFunction(_grid.p.beforeGridSaveCell)) {
                return _grid.p.beforeGridSaveCell.call(_grid, rowId, colName, cellValue, cellRowIndex, cellColIndex);
            }
            return cellValue;
        },

        /**
         * 单元格 从编辑控件变为 不可编辑 后事件
         * @param rowId 行id
         * @param colName 列名
         * @param cellValue 单元格值
         * @param cellRowIndex 单元格行下标
         * @param cellColIndex 单元格列下标
         */
        afterSaveCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
            var _grid = this, p = _grid.p, $grid = $(_grid), rowDataStore = {}, colModel,
                cellElm;
            //
            colModel = $grid.jqGrid("getColModelByName", colName);
            cellElm = $grid.jqGrid("getGridCellElement", rowId, colName);
            //
            //数据 设置到 数据存储
            rowDataStore[colName] = $.unformat(cellElm, {colModel: colModel});
            //
            $grid.jqGrid("_setGridRowDataStoreById", rowId, rowDataStore);
            //
            if ($.isFunction(_grid.p.afterGridSaveCell)) {
                _grid.p.afterGridSaveCell.call(_grid, rowId, colName, cellValue, cellRowIndex, cellColIndex);
            }
        },
        /**
         * 发送获取数据请求前(ajax 已声明但未请求)
         //* @param xhr
         //* @param settings
         * @returns {boolean|enabledLoad|a.jgrid.defaults.enabledLoad}
         */
        loadBeforeSend: function () {//xhr, settings
            var _grid = this, p = _grid.p,
                postData = p.postData;
            //序列化情况2/2，将请求的数据反序列化，还原 p.postData 原结构
            if (p.ajaxGridOptions.contentType == "application/json") {
                if (!$.isPlainObject(postData)) {
                    if (typeof postData === "string") {
                        p.postData = $.parseJSON(postData);
                    }
                }
            }
            return this.p.enabledLoad;
        },

        /**
         * 数据加载失败实现
         * @param xhr
         * @param st
         * @param error
         */
        loadError: function (xhr, st, error) {
            if (xhr["responseJSON"] && xhr["responseJSON"]["message"]) {
                if (console && console.hasOwnProperty("log")) {
                    console.log(xhr["responseJSON"]["message"] + " " + error);
                } else {
                    alert(xhr["responseJSON"]["message"] + " " + error);
                }
            } else {
                if (console && console.hasOwnProperty("log")) {
                    console.log("Grid Data loading failure .Type: " + st + "; Response: " + xhr.status + " " + xhr.statusText);
                } else {
                    alert("Type: " + st + "; Response: " + xhr.status + " " + xhr.statusText);
                }
            }
        },

        /**
         * 行点击前事件
         * @param rowId
         * @param event
         */
        beforeSelectRow: function (rowId, event) {
            var _grid = this, $grid = $(_grid), p = _grid.p,
                $target = $(event.target), parentAttr = $target.parent().attr("aria-describedby");
            //判断是否为复选框
            if ($target.is("input:checkbox") && parentAttr) {
                //判断是否是表字段，非表格的多选框
                if (parentAttr != p.id + "_cb") {
                    var currentFieldName = parentAttr.replace(p.id + "_", "");
                    //根据字段取到colModel
                    var currentColModel = $grid.jqGrid("getColModelByName", currentFieldName);
                    var currentCkbValMap = (currentColModel.editoptions) ? currentColModel.editoptions.value.split(":") : ["Yes", "No"];
                    //checkbox 选中或反选对应的值
                    var currentCkbVal = $target.is(":checked") ? currentCkbValMap[0] : currentCkbValMap[1];
                    //设置到行数据上
                    var currentObj = {};
                    currentObj[currentFieldName] = currentCkbVal;
                    $grid.jqGrid('setGridRowData', rowId, currentObj);
                }
            }

            if ($.isFunction(p.beforeSelectGridRow)) {
                return p.beforeSelectGridRow.call(_grid, rowId, event);
            } else {
                return true;
            }
        },

        /**
         * 行点击事件
         * @param rowId 行ID
         * @param status 行点击状态（true/false）
         * @param event 事件对象
         */
        onSelectRow: function (rowId, status, event) {
            var _grid = this, p = _grid.p;
            if ($.isFunction(p.onSelectGridRow)) {
                p.onSelectGridRow.call(_grid, rowId, status, event);
            }
        },
        /**
         * 不可编辑单元格 选中 事件
         * @param rowId 行id
         * @param colName 列名
         * @param cellValue 单元格值
         * @param cellRowIndex 单元格行下标
         * @param cellColIndex 单元格列下标
         */
        onSelectCell: function (rowId, colName, cellValue, cellRowIndex, cellColIndex) {
            var _grid = this, p = _grid.p;
            //
            if ($.isFunction(p.afterSelectGridCell)) {
                p.afterSelectGridCell.call(_grid, rowId, colName, cellValue, cellRowIndex, cellColIndex);
            }
        },
        /**
         * 插入行数据 后事件
         * @param rowId 行ID
         * @param rowData  行数据
         * @param rowElem  行元素(tr)
         */
        afterInsertRow: function (rowId, rowData, rowElem) {
            var _grid = this, p = _grid.p;
            if ($.isFunction(p.afterInsertGridRow)) {
                p.afterInsertGridRow.call(_grid, rowId, rowData, rowElem);
            }
        },

        /**
         * 表格 已经构造完成（在loadComplete之前）
         */
        gridComplete: function () {
            var _grid = this, p = _grid.p;

            //
            if ($.isFunction(p.afterGridComplete)) {
                p.afterGridComplete.call(_grid);
            }
        },
        /**
         * 表格 完成数据装载（在gridComplete之后）
         * @param datas 表格数据
         */
        loadComplete: function (datas) {
            var _grid = this, p = _grid.p;
            //
            if ($.isFunction(p.afterLoadGridComplete)) {
                p.afterLoadGridComplete.call(_grid, datas);
            }
        },

        /**
         * 初始化表格后
         */
        onInitGrid: function () {
            var _grid = this, $grid = $(_grid), p = _grid.p,
                $window;

            // 1.重写dragEnd
            _grid.grid.dragEnd = dragEnd(_grid);

            $(this).on("jqGridLoadComplete", function () {
                var _grid = this, p = _grid.p,
                    $firstRow = $(".jqgfirstrow", _grid);

                // 解决当没有数据时，仍然要显示滚动条的问题
                if (p.shrinkToFit === false && p.reccount === 0 && $firstRow.width() > p.width) {
                    $firstRow.css("height", "1px");
                }
            });

            //激活自动宽度
            if (p.autoWidthPadding > 0) {
                $grid.jqGrid("setGridWidth", $.jgrid.getWidthToRight($grid, p.autoWidthPadding));

                var timeOutWidthObj = -1;
                $window = $(window);
                $window.off("resize.jqGridAutoWidth", $grid);
                $window.on("resize.jqGridAutoWidth", $grid, function () {
                    //清除时间对象，防止反复并发
                    window.clearTimeout(timeOutWidthObj);
                    //
                    timeOutWidthObj = window.setTimeout(function () {
                        $grid.closest(".ui-jqgrid").css({"width": "20px", "overflow": "hidden"});
                        $grid.jqGrid("setGridWidth", $.jgrid.getWidthToRight($grid, p.autoWidthPadding));
                        $grid.closest(".ui-jqgrid").css({"overflow": "visible"});
                        window.clearTimeout(timeOutWidthObj);
                    }, 300);//设定间隔时间，只有停下指定毫秒不动后，再执行调整
                });
            }

            //激活自动高度
            if (p.autoHeightPadding > 0) {
                $grid.jqGrid("setGridHeight", $.jgrid.getHeightToBottom($grid, p.autoHeightPadding));

                var timeOutHeightObj = -1;
                $window = $(window);
                //
                $window.off("resize.jqGridAutoHeight", $grid);
                $window.on("resize.jqGridAutoHeight", $grid, function () {
                    //清除时间对象，防止反复并发
                    window.clearTimeout(timeOutHeightObj);
                    //
                    timeOutHeightObj = window.setTimeout(function () {
                        $grid.jqGrid("setGridHeight", $.jgrid.getHeightToBottom($grid, p.autoHeightPadding));
                        window.clearTimeout(timeOutHeightObj);
                    }, 300);//设定间隔时间，只有停下指定毫秒不动后，再执行调整
                });
            }

            //分页显示在上方
            if (p.toppager) {
                //上分页栏时 不显示分页按钮
                if (!p.topPagerButtons) {
                    $("#" + p.id + "_toppager_" + p.pagerpos).hide();
                }
                //上分页栏时 不显示分页数据信息
                if (!p.topPagerRecordInfo) {
                    $("#" + p.id + "_toppager_" + p.recordpos).hide();
                }
            }
            //
            if (p.hideBottomPager) {
                //去除下方分页栏
                $(p.pager).hide();
            }

            //隐藏 分页记录条数 的下拉选择
            if (p.hideRowList) {
                //去除下方分页栏
                $('select.ui-pg-selbox[role="listbox"]', p.pager).hide();
            }

            if ($.isFunction(p.afterInitGrid)) {
                p.afterInitGrid.call(_grid);
            }
        },

        /**
         * 列排序事件
         * @param colName 列名
         * @param colIndex 列位置索引
         * @param sortOrder 排序状态
         * @returns {string}
         */
        onSortCol: function (colName, colIndex, sortOrder) {
            var _grid = this, p = _grid.p;
            if ($.isFunction(p.beforeSortCol)) {
                if (!p.beforeSortCol.call(_grid, colName, colIndex, sortOrder)) {
                    return "stop";
                }
            }
        },

        /**
         * 展开行 之前事件
         * @param subGridId 行展开区域Id
         * @param rowId 行Id
         * @returns {*}
         */
        subGridBeforeExpand: function (subGridId, rowId) {
            //
            var _grid = this, p = _grid.p;
            //
            if ($.isFunction(p.beforeSubGridRowExpand)) {
                return p.beforeSubGridRowExpand.call(_grid, subGridId, rowId);
            } else {
                return true;
            }
        },

        /**
         * 展开行 事件
         * @param subGridId 行展开区域Id
         * @param rowId 行Id
         */
        subGridRowExpanded: function (subGridId, rowId) {
            //
            var _grid = this, p = _grid.p;
            //
            if ($.isFunction(p.afterSubGridRowExpand)) {
                p.afterSubGridRowExpand.call(_grid, subGridId, rowId);
            }
        },

        /**
         * 收缩行 事件
         * @param subGridId 行展开区域Id
         * @param rowId 行Id
         * @returns {*}
         */
        subGridRowColapsed: function (subGridId, rowId) {
            //
            var _grid = this, p = _grid.p;
            //
            if ($.isFunction(p.beforeSubGridRowCollapse)) {
                return p.beforeSubGridRowCollapse.call(_grid, subGridId, rowId);
            } else {
                return true;
            }
        },

        /**
         * 最后一个可编辑单元格的回调函数(editNextCell时触发),
         * 可覆盖,我们默认将单元格从可编辑状态恢复成非编辑状态
         */
        onLastEditableCell: function (iRow, iCol) {
            var _grid = this;

            $(_grid).jqGrid("restoreGridCell");
        }

        /** ********** end 对原生回调接口的实现 */

    });
    $.jgrid.RowState = {
        /**
         * 0 = 未改变状态
         */
        UNCHANGED: 0,
        /**
         * 1 = 新增的状态
         */
        ADDED: 1,
        /**
         * 2 = 删除的状态
         */
        DELETED: 2,
        /**
         * 3 = 修改过的状态
         */
        MODIFIED: 3
    };

    $.jgrid.search = $.jgrid.search || {};
    $.extend($.jgrid.search, {
        edit: false,
        add: false,
        del: false,
        search: false
    });

    /**
     * 默认格式化的配置
     * @type {{}|*}
     */
    $.jgrid.formatter = $.jgrid.formatter || {};
    $.extend($.jgrid.formatter, {
        integer: {thousandsSeparator: ",", defaultValue: '0'},
        number: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
        currency: {
            decimalSeparator: ".",
            thousandsSeparator: ",",
            decimalPlaces: 2,
            prefix: "",
            suffix: "",
            defaultValue: ''
        },
        checkbox: {disabled: true},
        idName: 'id'
    });

    //==============================================================================================
    // 重写dragEnd是,因为有多表头时拉动不起作用
    var dragEnd = function (ts) {
        var p = ts.p;
        return function () {
            this.hDiv.style.cursor = "default";
            if (this.resizing) {
                var idx = this.resizing.idx,
                    nw = this.headers[idx].newWidth || this.headers[idx].width;
                nw = parseInt(nw, 10);
                this.resizing = false;
                $("#rs_m" + $.jgrid.jqID(p.id)).css("display", "none");
                p.colModel[idx].width = nw;
                this.headers[idx].width = nw;
                this.headers[idx].el.style.width = nw + "px";
                this.cols[idx].style.width = nw + "px";
                // TODO start 解决多表头时设置不能对齐的问题
                var $firstRowHeader = $(".jqg-first-row-header", this.hDiv);
                if ($firstRowHeader.length > 0) {
                    $firstRowHeader[0].cells[idx].style.width = nw + "px";
                }
                if (this.footers.length > 0) {
                    this.footers[idx].style.width = nw + "px";
                }
                if (p.forceFit === true) {
                    var newWidth = this.headers[idx + p.nv].newWidth || this.headers[idx + p.nv].width;
                    this.headers[idx + p.nv].width = newWidth;
                    this.headers[idx + p.nv].el.style.width = newWidth + "px";
                    this.cols[idx + p.nv].style.width = newWidth + "px";
                    if ($firstRowHeader.length > 0) {
                        $firstRowHeader[0].cells[idx + p.nv].style.width = newWidth + "px";
                    }
                    if (this.footers.length > 0) {
                        this.footers[idx + p.nv].style.width = newWidth + "px";
                    }
                    p.colModel[idx + p.nv].width = newWidth;
                    // TODO END
                } else {
                    p.tblwidth = this.newWidth || p.tblwidth;
                    $('table:first', this.bDiv).css("width", p.tblwidth + "px");
                    $('table:first', this.hDiv).css("width", p.tblwidth + "px");
                    this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                    if (p.footerrow) {
                        $('table:first', this.sDiv).css("width", p.tblwidth + "px");
                        this.sDiv.scrollLeft = this.bDiv.scrollLeft;
                    }
                }
                $(ts).triggerHandler("jqGridResizeStop", [nw, idx]);
                if ($.isFunction(p.resizeStop)) {
                    p.resizeStop.call(ts, nw, idx);
                }
            }
            this.curGbox = null;
            document.onselectstart = function () {
                return true;
            };
        };
    };

    // 重写原生方法
    $.jgrid.extend({
        // 重写原因:多表头时调用此方法会出现对不齐的情况
        setGridWidth: function (nwidth, shrink) {
            return this.each(function () {
                if (!this.grid) {
                    return;
                }
                var $t = this, cw,
                    initwidth = 0, brd = $.jgrid.cell_width ? 0 : $t.p.cellLayout, lvc, vc = 0, hs = false, scw = $t.p.scrollOffset, aw, gw = 0, cr;
                if (typeof shrink !== 'boolean') {
                    shrink = $t.p.shrinkToFit;
                }
                if (isNaN(nwidth)) {
                    return;
                }
                nwidth = parseInt(nwidth, 10);
                $t.grid.width = $t.p.width = nwidth;
                $("#gbox_" + $.jgrid.jqID($t.p.id)).css("width", nwidth + "px");
                $("#gview_" + $.jgrid.jqID($t.p.id)).css("width", nwidth + "px");
                $($t.grid.bDiv).css("width", nwidth + "px");
                $($t.grid.hDiv).css("width", nwidth + "px");
                if ($t.p.pager) {
                    $($t.p.pager).css("width", nwidth + "px");
                }
                if ($t.p.toppager) {
                    $($t.p.toppager).css("width", nwidth + "px");
                }
                if ($t.p.toolbar[0] === true) {
                    $($t.grid.uDiv).css("width", nwidth + "px");
                    if ($t.p.toolbar[1] === "both") {
                        $($t.grid.ubDiv).css("width", nwidth + "px");
                    }
                }
                if ($t.p.footerrow) {
                    $($t.grid.sDiv).css("width", nwidth + "px");
                }
                if (shrink === false && $t.p.forceFit === true) {
                    $t.p.forceFit = false;
                }
                if (shrink === true) {
                    $.each($t.p.colModel, function () {
                        if (this.hidden === false) {
                            cw = this.widthOrg;
                            initwidth += cw + brd;
                            if (this.fixed) {
                                gw += cw + brd;
                            } else {
                                vc++;
                            }
                        }
                    });
                    if (vc === 0) {
                        return;
                    }
                    $t.p.tblwidth = initwidth;
                    aw = nwidth - brd * vc - gw;
                    if (!isNaN($t.p.height)) {
                        if ($($t.grid.bDiv)[0].clientHeight < $($t.grid.bDiv)[0].scrollHeight || $t.rows.length === 1) {
                            hs = true;
                            aw -= scw;
                        }
                    }
                    initwidth = 0;
                    var cle = $t.grid.cols.length > 0;
                    var $firstRowHeader = $(".jqg-first-row-header", $t.grid.hDiv);
                    $.each($t.p.colModel, function (i) {
                        if (this.hidden === false && !this.fixed) {
                            cw = this.widthOrg;
                            cw = Math.round(aw * cw / ($t.p.tblwidth - brd * vc - gw));
                            if (cw < 0) {
                                return;
                            }
                            this.width = cw;
                            initwidth += cw;
                            $t.grid.headers[i].width = cw;
                            $t.grid.headers[i].el.style.width = cw + "px";
                            // TODO start
                            if ($firstRowHeader.length > 0) {
                                $firstRowHeader[0].cells[i].style.width = cw + "px";
                            }
                            // TODO END
                            if ($t.p.footerrow) {
                                $t.grid.footers[i].style.width = cw + "px";
                            }
                            if (cle) {
                                $t.grid.cols[i].style.width = cw + "px";
                            }
                            lvc = i;
                        }
                    });

                    if (!lvc) {
                        return;
                    }

                    cr = 0;
                    if (hs) {
                        if (nwidth - gw - (initwidth + brd * vc) !== scw) {
                            cr = nwidth - gw - (initwidth + brd * vc) - scw;
                        }
                    } else if (Math.abs(nwidth - gw - (initwidth + brd * vc)) !== 1) {
                        cr = nwidth - gw - (initwidth + brd * vc);
                    }
                    $t.p.colModel[lvc].width += cr;
                    $t.p.tblwidth = initwidth + cr + brd * vc + gw;
                    if ($t.p.tblwidth > nwidth) {
                        var delta = $t.p.tblwidth - parseInt(nwidth, 10);
                        $t.p.tblwidth = nwidth;
                        cw = $t.p.colModel[lvc].width = $t.p.colModel[lvc].width - delta;
                    } else {
                        cw = $t.p.colModel[lvc].width;
                    }
                    $t.grid.headers[lvc].width = cw;
                    $t.grid.headers[lvc].el.style.width = cw + "px";
                    if (cle) {
                        $t.grid.cols[lvc].style.width = cw + "px";
                    }
                    if ($t.p.footerrow) {
                        $t.grid.footers[lvc].style.width = cw + "px";
                    }
                }
                if ($t.p.tblwidth) {
                    $('table:first', $t.grid.bDiv).css("width", $t.p.tblwidth + "px");
                    $('table:first', $t.grid.hDiv).css("width", $t.p.tblwidth + "px");
                    $t.grid.hDiv.scrollLeft = $t.grid.bDiv.scrollLeft;
                    if ($t.p.footerrow) {
                        $('table:first', $t.grid.sDiv).css("width", $t.p.tblwidth + "px");
                    }
                }
            });
        }
    });
    //==============================================================================================

    /**
     * 获得要元素设置高度为从起始top的位置到窗口底部的值
     * @param jqEpr jqEpr 元素或jQuery expression
     * @param paddingHeight 底部的留白高度
     * @returns {number}
     */
    $.jgrid.getHeightToBottom = function (jqEpr, paddingHeight) {
        var winHeight = $(window).height(),
            $element = $(jqEpr),
            element = $element[0],
            offsetTop, $bDiv;

        paddingHeight = paddingHeight || 0;

        // 如果是grid
        if (element.grid) {
            $bDiv = $($element[0].grid.bDiv);
            // 不采用$element.offset().top是因为$element可能已经滚动了，造成计算不准确
            offsetTop = $bDiv.offset().top;
        } else {
            offsetTop = $element.offset().top;
        }

        return winHeight - offsetTop - paddingHeight;
    };

    /**
     * 获得要元素设置高度为从起始top的位置到窗口底部的值
     * @param jqEpr jqEpr 元素或jQuery expression
     * @param paddingWidth 右边留白宽度
     * @returns {number}
     */
    $.jgrid.getWidthToRight = function (jqEpr, paddingWidth) {
        var winWidth = $(window).width(),
            $elements = $(jqEpr);
        paddingWidth = paddingWidth || 0;
        return winWidth - $elements.offset().left - paddingWidth;
    };

})(jQuery);
