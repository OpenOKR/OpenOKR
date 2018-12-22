(function ($) {
    "use strict";
    $.jgrid.extend({

        /**
         * 根据单个Id获取行数据,返回{}
         * @param rowId 行ID（不为空）
         * @returns {*}
         */
        getGridRowDataById: function (rowId) {
            if (rowId) {
                var _grid = this, $grid = $(_grid);

                return $grid.jqGrid("_getGridRowDataStoreById", rowId);
            }
        },
        /**
         * 根据多Id获取行数据,返回[]
         * @param rowIds 行Id集（可空）
         * @returns {Array}
         */
        getGridRowDatasByIds: function (rowIds) {
            var _grid = this, $grid = $(_grid), rowDatas = [], rowData;
            for (var i = 0; i < rowIds.length; i++) {
                rowData = $grid.jqGrid('getGridRowDataById', rowIds[i]);
                if (rowData) {
                    rowDatas.push(rowData);
                }
            }
            return rowDatas;
        },

        /**
         * 获取所有行数据,返回[]
         * @param isIncludeDelete 是否包含删除的（但还保留在data store）里的数据，默认包含（可空）
         * @returns {*}
         */
        getGridRowDatas: function (isIncludeDelete) {
            var _grid = this, $grid = $(_grid),
                rowIds = $grid.jqGrid('getGridRowIds', isIncludeDelete);
            return $grid.jqGrid('getGridRowDatasByIds', rowIds);
        },
        /**
         * 获取所有行的Id,返回[]
         * @param isIncludeDelete 是否包含删除的（但还保留在data store）里的数据，默认包含（可空）
         * @returns {Array}
         */
        getGridRowIds: function (isIncludeDelete) {
            var _grid = this, $grid = $(_grid), deleteRowIds = [],
                rowIds = $grid.jqGrid('getDataIDs');
            if (isIncludeDelete === false) {
                deleteRowIds = [];
            } else {
                deleteRowIds = $grid.jqGrid('getGridRowIdsByDelete');
            }
            //合并
            rowIds = rowIds.concat(deleteRowIds);
            return rowIds;
        },
        /**
         * 获取选中行的id,返回[]
         * @returns {Array}
         */
        getGridRowIdsBySelected: function () {
            var _grid = this, $grid = $(_grid), gridElm = _grid[0], rowIds = [];
            //不允许多行选中时,取当前选中行
            if (!gridElm.p.multiselect === true) {
                var selectRowId = $grid.jqGrid('getGridParam', 'selrow');
                if (selectRowId != null && selectRowId.length > 0) {
                    rowIds[0] = selectRowId;
                }
            } else {
                rowIds = $grid.jqGrid('getGridParam', 'selarrrow');
            }
            return rowIds;
        },
        /**
         * 获取选中的行数据,返回[]
         * @returns {Array}
         */
        getGridRowDatasBySelected: function () {
            var _grid = this, $grid = $(_grid), rowIds, rowDatas;
            rowIds = $grid.jqGrid("getGridRowIdsBySelected");
            rowDatas = $grid.jqGrid('getGridRowDatasByIds', rowIds);
            return rowDatas;
        },
        /**
         * 获取被改过的行Id(新增,修改,删除),返回[]
         * @returns {Array}
         */
        getGridRowIdsByChange: function () {
            var _grid = this, gridElm = _grid[0], p = gridElm.p, rowId, ids = [],
                //获取实际grid的store
                rowDatas = p._gridDataStore;
            for (rowId in rowDatas) {
                if (rowDatas[rowId]) {
                    var rowData = rowDatas[rowId];
                    //收集到被修改的数据id
                    if (rowId && rowData && (Number(rowData[p.rowStateName]) === $.jgrid.RowState.ADDED
                        || Number(rowData[p.rowStateName]) === $.jgrid.RowState.DELETED
                        || Number(rowData[p.rowStateName]) === $.jgrid.RowState.MODIFIED)) {
                        ids.push(rowId);
                    }
                }
            }
            return ids;
        },
        /**
         * 获取被改过的行数据(新增,修改,删除),返回[]
         * @returns {Array}
         */
        getGridRowDatasByChange: function () {
            var _grid = this, $grid = $(_grid),
                //获取改变过的行ID
                ids = $grid.jqGrid('getGridRowIdsByChange');
            return $grid.jqGrid('getGridRowDatasByIds', ids);
        },

        /**
         * 获取被删除（但还存在于data store）的行Id,返回[]
         * @returns {Array}
         */
        getGridRowIdsByDelete: function () {
            var _grid = this, gridElm = _grid[0], p = gridElm.p, rowId, ids = [],
                //获取实际grid的store
                rowDatas = p._gridDataStore;
            for (rowId in rowDatas) {
                if (rowDatas[rowId]) {
                    var rowData = rowDatas[rowId];
                    //收集到被修改的数据id
                    if (rowId && rowData && (Number(rowData[p.rowStateName]) == $.jgrid.RowState.DELETED)) {
                        ids.push(rowId);
                    }
                }
            }
            return ids;
        },
        /**
         * 获取被删除（但还存在于data store）的行数据,返回[]
         * @returns {Array}
         */
        getGridRowDatasByDelete: function () {
            var _grid = this, $grid = $(_grid), ids;
            //获取改变过的行ID
            ids = $grid.jqGrid('getGridRowIdsByDelete');
            return $grid.jqGrid('getGridRowDatasByIds', ids);
        },

        /**
         * 根据列名 和 列值，获取行数据存储
         * @param name 列名
         * @param value 值
         * @returns {{}}
         */
        getGridRowDataStoreByNameAndValue: function (name, value) {
            var _grid = this, gridElm = _grid[0], p = gridElm.p,
                gridDataStore = p._gridDataStore, rowDataStore = {}, rowId;
            //
            for (rowId in gridDataStore) {
                if (gridDataStore[rowId] && gridDataStore[rowId][name] == value) {
                    rowDataStore[rowId] = gridDataStore[rowId];
                }
            }
            return rowDataStore;
        },

        /**
         * 创建行Id
         * @returns {*}
         */
        createRowId: function () {
            return $.jgrid.randId();
        },
        /**
         * 新增表格数据
         * @param rowId 行数据ID（不可空）
         * @param rowData 行数据（不可空）
         * @param position 表格的位置（可空，默认last。其它'last','first','after','before'）
         * @param refRowId 参照行数据ID（可空，位置等于 'after','before' 必须给定）
         * @returns {boolean}
         */
        addGridRowData: function (rowId, rowData, position, refRowId) {
            var _grid = this, gridElm = _grid[0], $grid = $(_grid), p = gridElm.p,
                isSuccess,
                rName = p.rowStateName;
            if (rowData[rName] === undefined
                && $.trim(rowData[rName]).length === 0
                && Number(rowData[rName]) !== $.jgrid.RowState.UNCHANGED
                && Number(rowData[rName]) !== $.jgrid.RowState.MODIFIED
                && Number(rowData[rName]) !== $.jgrid.RowState.DELETED) {
                //设置新增状态
                rowData[rName] = $.jgrid.RowState.ADDED;
            }
            isSuccess = $grid.jqGrid('addRowData', rowId, rowData, position, refRowId);
            $grid.jqGrid("_setGridRowDataStoreById", rowId, rowData);
            return isSuccess;
        },
        /**
         * 批量新增表格数据
         * @param rowDatas 行数据（不可空）
         * @param position 表格的位置（可空，默认last。其它'last','first','after','before'）
         * @param refRowId 参照行数据ID（可空，位置等于 'after','before' 必须给定）
         * @returns {boolean}
         */
        addGridRowDatas: function (rowDatas, position, refRowId) {
            var _grid = this, gridElm = _grid[0], $grid = $(_grid), p = gridElm.p, isSuccess = false, i = 0, rowId;
            for (; i < rowDatas.length; i++) {
                if (rowDatas[i][p.jsonReader.id]) {
                    rowId = rowDatas[i][p.jsonReader.id];
                } else {
                    //创建行Id
                    rowId = $grid.jqGrid('createRowId');
                }
                isSuccess = $grid.jqGrid('addGridRowData', rowId, rowDatas[i], position, refRowId);
            }
            return isSuccess;
        },
        /**
         *
         * 设置表格数据
         * @param rowId 行数据ID（不可空）
         * @param rowData 行数据（不可空）
         * @param cssProperty css style配置（可空，例：{color:red,width:"130px"}）
         * @returns {boolean}
         */
        setGridRowData: function (rowId, rowData, cssProperty) {
            //新增状态 的数据 状态不变
            //删除状态数据看不见，不能被修改到
            //未改变状态 的数据才能被修改
            //获取能得到
            var _grid = this, $grid = $(_grid), gridElm = _grid[0], p = gridElm.p, isSuccess, oldRowData;
            //先取出行数据
            oldRowData = $grid.jqGrid('getGridRowDataById', rowId);
            //设置修改状态
            if (oldRowData) {
                if (Number(oldRowData[p.rowStateName]) === $.jgrid.RowState.UNCHANGED) {
                    //如果 原行数据 状态未改变（UNCHANGED）,设置为修改状态(删除、新增 状态不能被修改)
                    rowData[p.rowStateName] = $.jgrid.RowState.MODIFIED;
                }
            }
            isSuccess = $grid.jqGrid('setRowData', rowId, rowData, cssProperty);
            //设置成功，同时设置GridDataStore
            if (isSuccess === true) {
                $grid.jqGrid("_setGridRowDataStoreById", rowId, rowData);
            }
            return isSuccess;
        },
        /**
         * 根据行Id 删除表格数据
         * @param rowId 行数据ID（不可空）
         * @param forceDeleteForStore 是否强制删除不保留在store中（可空，默认false）
         * @returns {boolean}
         */
        deleteGridRowDataById: function (rowId, forceDeleteForStore) {
            //新增状态 的数据直接删除
            //未改变状态 从表格消失，改为删除状态，还保留在store
            //获取能得到
            var _grid = this, $grid = $(_grid), isSuccess;
            isSuccess = $grid.jqGrid('delRowData', rowId);
            //删除成功，同时删除 GridDataStore
            $grid.jqGrid("_deleteGridRowDataStoreById", rowId, forceDeleteForStore);
            return isSuccess;
        },
        /**
         *
         * 根据多行Id 批量删除表格数据
         * @param ids 批量行数据ID（不可空）
         * @param forceDeleteForStore 是否强制删除不保留在store中（可空，默认false）
         * @returns {boolean}
         */
        deleteGridRowDataByIds: function (ids, forceDeleteForStore) {
            var _grid = this, $grid = $(_grid), processSuccess = false, resultSuccess = true, i = (ids.length - 1);
            //批量删除
            for (; i >= 0; i--) {
                processSuccess = $grid.jqGrid('deleteGridRowDataById', ids[i], forceDeleteForStore);
                if (!processSuccess) {
                    //TODO:出现不成功，给出警告
                    resultSuccess = false;
                }
            }
            return resultSuccess;
        },
        /**
         * 删除所有表格数据
         * @param forceDeleteForStore 是否强制删除不保留在store中（可空）
         * @returns {boolean}
         */
        deleteGridRowDatas: function (forceDeleteForStore) {
            var _grid = this, $grid = $(_grid),
                //得到所有表格的id
                ids = $grid.jqGrid('getGridRowIds');
            //批量删除
            return $grid.jqGrid('deleteGridRowDataByIds', ids, forceDeleteForStore);
        },
        /**
         *
         * @param rowId 行数据ID（不可空）
         * @param colName 列名（不可空）
         * @param newCellValue 单元格新数据（不可空）
         * @param cssProperty css style配置（可空，例：{color:red,width:"130px"}）
         * @param attrProperty 元素属性配置（可空，例：{attr:"1",attr2:"2"}）
         * @param forceUpdate 是否强制更新（可空，默认false）
         * @returns {*}
         */
        setGridCell: function (rowId, colName, newCellValue, cssProperty, attrProperty, forceUpdate) {
            var _grid = this, $grid = $(_grid), gridElm = _grid[0], p = gridElm.p, rowData = {}, oldRowData;
            //数据 设置到 数据存储
            rowData[colName] = newCellValue;
            //先取出行数据
            oldRowData = $grid.jqGrid('getGridRowDataById', rowId);
            //设置修改状态
            if (oldRowData) {
                if (Number(oldRowData[p.rowStateName]) === $.jgrid.RowState.UNCHANGED) {
                    //如果 原行数据 状态未改变（UNCHANGED）,设置为修改状态(删除、新增 状态不能被修改)
                    rowData[p.rowStateName] = $.jgrid.RowState.MODIFIED;
                }
            }
            //
            $grid.jqGrid("_setGridRowDataStoreById", rowId, rowData);
            //
            return _grid.each(function () {
                $grid.jqGrid("setCell", rowId, colName, newCellValue, cssProperty, attrProperty, forceUpdate);
            });
        },
        /**
         * 重载grid
         */
        reloadGrid: function () {
            return this.each(function () {
                var _grid = this, $grid = $(_grid), gridElm = _grid, p = gridElm.p;
                //激活加载
                p.enabledLoad = true;
                //触发重载
                $grid.trigger("reloadGrid");
                //表格内编辑需要重置一下"行列"坐标,和最新选中行
                if (p.cellEdit === true) {
                    p.iRow = -1;
                    p.iCol = -1;
                    p.selrow = "";
                }
            });
        },
        /**
         * 获取表格总记录数(包含 分页 与 不分页 情况)
         */
        getGridTotalRecords: function () {
            return this[0].p.records;
        },
        /**
         * 获取所有列Model
         * @returns {*}
         */
        getColModels: function () {
            return this[0].p.colModel;
        },
        /**
         * 根据name获取列Model
         * @param name 列名
         * @returns {Array}
         */
        getColModelByName: function (name) {
            var _grid = this, gridElm = _grid[0], p = gridElm.p, colModel, i;
            for (i = 0; i < p.colModel.length; i++) {
                if (p.colModel[i].name === name) {
                    colModel = p.colModel[i];
                    break;
                }
            }
            return colModel;
        },
        /**
         * 如果启用表格内编辑，需要恢复正在编辑的单元格到不可编辑状态
         */
        restoreGridCell: function () {
            return this.each(function () {
                var _grid = this, $grid = $(_grid), gridElm = _grid, p = gridElm.p;
                if (p.iRow > 0 && p.iCol > 0) {
                    $grid.jqGrid("saveGridCell", p.iRow, p.iCol);
                }
            });
        },

        /**
         * 根据单元格 行坐标、列坐标，激活单元格为编辑状态
         * @param rowIndex 行坐标（不可空）
         * @param colIndex 列坐标（不可空）
         * @param editable 激活（可空,默认true）
         * @returns {*}
         */
        editGridCell: function (rowIndex, colIndex, editable) {
            return this.each(function () {
                var _grid = this, $grid = $(_grid),
                    editable = editable === false ? false : true;
                $grid.jqGrid("editCell", rowIndex, colIndex, editable);
            });
        },

        /**
         * 如果启用表格内编辑，保存正在编辑的单元格到不可编辑状态
         * @param rowIndex 行坐标（不可空）
         * @param colIndex 列坐标（不可空）
         * @returns {*}
         */
        saveGridCell: function (rowIndex, colIndex) {
            return this.each(function () {
                var _grid = this, $grid = $(_grid), gridElm = _grid, p = gridElm.p, oldRowData = {}, rowData = {};
                if (p.cellEdit === true) {
                    // 如果已经有正在编辑的单元格必须先保存(防重复)
                    if (p.savedRow.length > 0 && rowIndex > -1 && colIndex > -1) {
                        $grid.jqGrid("saveCell", rowIndex, colIndex);
                        //再取出行数据
                        if (p.selrow) {
                            oldRowData = $grid.jqGrid('getGridRowDataById', p.selrow);
                        } else {
                            //alert(p.selrow);
                        }
                        //设置修改状态
                        if (oldRowData) {
                            if (Number(oldRowData[p.rowStateName]) === $.jgrid.RowState.UNCHANGED) {
                                //如果 原行数据 状态未改变（UNCHANGED）,设置为修改状态(删除、新增 状态不能被修改)
                                rowData[p.rowStateName] = $.jgrid.RowState.MODIFIED;
                                //将数据设置会表格
                                $grid.jqGrid('setGridRowData', p.selrow, rowData);
                            }
                        }
                    }
                }
            });
        },

        /**
         * 根据列名 隐藏列
         * @param columnName 列名
         */
        hideColumnByName: function (columnName) {
            return this.each(function () {
                $(this).jqGrid("hideCol", columnName);
            });
        },
        /**
         * 根据列名 显示列
         * @param columnName 列名
         */
        showColumnByName: function (columnName) {
            return this.each(function () {
                $(this).jqGrid("showCol", columnName);
            });
        },
        /**
         * 根据行Id 和 列名 获取单元格元素
         * @param rowId 行Id
         * @param colName 列名
         * @returns {*|HTMLElement}
         */
        getGridCellElement: function (rowId, colName) {
            var p = this[0].p,
                $cellElm = $('td[aria-describedby="' + p.id + '_' + colName + '"]', $('#' + rowId, $(this)));
            if ($cellElm && $cellElm.length > 0) {
                return $cellElm[0];
            } else {
                return null;
            }
        },
        /**
         * 根据行Id 和 列名 获取单元格列元素
         * @param colName 列名
         * @returns {*|HTMLElement}
         */
        getGridHeadElement: function (colName) {
            //<th id="tableDetail_batchCode" role="columnheader" class="ui-state-default ui-th-column ui-th-ltr" style="width: 180px;">
            // <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
            // <div id="jqgh_tableDetail_batchCode" class="ui-jqgrid-sortable">
            // 批次
            // <span class="s-ico" style="display:none">
            // <span sort="asc" class="ui-grid-ico-sort ui-icon-asc ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-ltr"></span>
            // <span sort="desc" class="ui-grid-ico-sort ui-icon-desc ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-ltr"></span>
            // </span>
            // </div>
            // </th>
            var p = this[0].p,
                $headElm = $('#' + p.id + '_' + colName);
            if ($headElm && $headElm.length > 0) {
                return $headElm[0];
            } else {
                return null;
            }
        },
        /**
         * 根据行Id 获取行元素（TR）
         * @param rowId 行Id
         * @returns {*}
         */
        getGridRowElementById: function (rowId) {
            return $(this).jqGrid("getGridRowById", rowId);
        },
        /**
         * 获取当前最新选中的行ID值(当然当前是反选，则为null)
         * @returns {*}
         */
        getCurrentSelectedRowId: function () {
            return this[0].p.selrow;
        },
        /**
         * 获取当前最新选中的行数据(当然当前是反选，则为null)
         * @returns {*}
         */
        getCurrentSelectedRowData: function () {
            var _grid = this, $grid = $(_grid);
            return $grid.jqGrid("getGridRowDataById", $grid.jqGrid("getCurrentSelectedRowId"));
        },
        /**
         * 根据ID，选中或反选 行
         * @param rowId 行Id
         * @param isSelection 是否选中
         * @param event 事件对象
         */
        setSelectionRow: function (rowId, isSelection, event) {
            return this.each(function () {
                return $(this).jqGrid("setSelection", rowId, isSelection, event);
            });
        },

        /**
         * 根据ID，反选行
         * @param rowId 行Id
         */
        resetSelectionRow: function (rowId) {
            return this.each(function () {
                return $(this).jqGrid("resetSelection", rowId);
            });
        },

        /**
         * 根据ID，反选所有行
         */
        resetAllSelectionRow: function () {
            return this.each(function () {
                var _grid = this, $grid = $(_grid);
                //排除删除行Id
                var rowIds = $grid.jqGrid('getGridRowIds', true), i;
                for (i = rowIds.length - 1; i >= 0; i--) {
                    $grid.jqGrid("resetSelectionRow", rowIds[i]);
                }
            });
        },

        /**
         * 设置请求数据
         * @param requestData 例如：{paramName1:"value",paramName2:"value2"}
         * @returns {*}
         */
        setRequestData: function (requestData) {
            return this.each(function () {
                if ($.isPlainObject(requestData)) {
                    var gridElm = this, p = gridElm.p;
                    $.extend(p.postData, requestData);
                    //标识 调用了此接口的 postData 为最优先取用（比如：分页码、排序字段、查询条件）
                    //在 beforeRequest 时将会取用
                    p._isSetRequestData = true;
                }
            });
        },

        /**
         * 上一行Id,可以根据当前已选中的为参照行
         * @param isRefer 是否参照当前已选中行，默认true。（可空）
         * @returns {*}
         */
        previousGridRowId: function (isRefer) {
            var currentFocusSelectionRowId = null, isFirstRow = false;
            this.each(function () {
                isRefer = isRefer || false;
                var _grid = this, $grid = $(_grid), gridElm = _grid, p = gridElm.p,
                    $previousAllRows, allRowIds;
                currentFocusSelectionRowId = (isRefer === true ? p.selrow : null) || p.currentFocusSelectionRowIndex;
                //如果参照最新选中的行
                if (currentFocusSelectionRowId) {
                    $previousAllRows = $($grid.jqGrid("getGridRowElementById", currentFocusSelectionRowId)).prevAll("[role='row'][class!='jqgfirstrow']");
                    //如果上一行不为空
                    if ($previousAllRows.length > 0) {
                        currentFocusSelectionRowId = p.currentFocusSelectionRowIndex = $previousAllRows[0].id;
                    }
                    if ($previousAllRows.length == 1 || $previousAllRows.length == 0) {
                        isFirstRow = true;
                    }
                } else {
                    allRowIds = $grid.jqGrid("getGridRowIds");
                    currentFocusSelectionRowId = p.currentFocusSelectionRowIndex = allRowIds[allRowIds.length - 1];
                }
            });
            return [currentFocusSelectionRowId, isFirstRow];
        },

        /**
         * 下一行Id,可以根据当前已选中的为参照行
         * @param isRefer 是否参照当前已选中行，默认true。（可空）
         * @returns {*}
         */
        nextGridRowId: function (isRefer) {
            var currentFocusSelectionRowId = null, isEndRow = false;
            this.each(function () {
                isRefer = isRefer || false;
                var _grid = this, $grid = $(_grid), gridElm = _grid, p = gridElm.p,
                    $nextAllRows, allRowIds;
                currentFocusSelectionRowId = (isRefer === true ? p.selrow : null) || p.currentFocusSelectionRowIndex;
                //如果参照最新选中的行
                if (currentFocusSelectionRowId) {
                    $nextAllRows = $($grid.jqGrid("getGridRowElementById", currentFocusSelectionRowId)).nextAll("[role='row'][class!='jqgfirstrow']");
                    //如果下一行不为空
                    if ($nextAllRows.length > 0) {
                        currentFocusSelectionRowId = p.currentFocusSelectionRowIndex = $nextAllRows[0].id;
                    }
                    if ($nextAllRows.length == 1 || $nextAllRows.length == 0) {
                        isEndRow = true;
                    }
                } else {
                    allRowIds = $grid.jqGrid("getGridRowIds");
                    currentFocusSelectionRowId = p.currentFocusSelectionRowIndex = allRowIds[0];
                }
            });
            return [currentFocusSelectionRowId, isEndRow];
        },

        /**
         * 根据行Id，获取第一个可编辑单元格的坐标
         * @param rowId 行Id（可空）
         * @returns {Array}
         */
        getFirstEditableCellRowCol: function (rowId) {
            var _grid = this, $grid = $(_grid), gridElm = _grid[0], p = gridElm.p,
                indexArr = [-1, -1], rowIndex = -1, colIndex = -1, i;
            //
            if (gridElm.grid && p.cellEdit === true) {
                //
                if (rowId) {
                    rowIndex = $($grid.jqGrid("getGridRowElementById", rowId)).prevAll("[role='row'][class!='jqgfirstrow']").length + 1;
                } else {
                    rowIndex = 1;
                }
                //
                for (i = 0; i < p.colModel.length; i++) {
                    if (p.colModel[i].editable === true) {
                        colIndex = i;
                        break;
                    }
                }
                indexArr[0] = rowIndex;
                indexArr[1] = colIndex;
            }
            return indexArr;
        },

        /**
         * 获取当页记录数(非所有分页的总记录)
         * @returns {*}
         */
        getRecordCount: function () {
            return $(this).jqGrid("getGridParam", "reccount");
        },

        /**
         * 根据 相同列名和列值 行数据
         * @param columnName 列名
         * @param columnValue 列值
         * @param isIncludeDelete 是否包含删除的（但还保留在data store）里的数据，默认包含（可空）
         * @returns {Array}
         */
        getRowDatasByColumnNameAndValue: function (columnName, columnValue, isIncludeDelete) {
            var _grid = this, $grid = $(_grid),
                rowDatas, rowData,
                pushData = [];
            //取到全部数据
            rowDatas = $grid.jqGrid('getGridRowDatas', isIncludeDelete);
            //
            for (var i = rowDatas.length - 1; i >= 0; i--) {
                rowData = rowDatas[i];
                //列名存在，并且 列值相同
                if (rowData.hasOwnProperty(columnName) && rowData[columnName] == columnValue) {
                    pushData.push(rowData);
                }
            }
            return pushData;
        },

        /**
         * 获取多个列合计值，根据列名
         * @param columnNames 列名
         * @param selected 是否只获取选中的
         * @param filterFunc 过滤 function
         * @returns {*}
         */
        getTotalValueByColumnNames: function (columnNames, selected, filterFunc) {
            var totalObj = {};
            $(this).each(function () {
                var _grid = this, $grid = $(_grid),
                    rowDatas, rowData,
                    columnName,
                    columnValue,
                    i, j,
                    firstDecimal,
                    secondDecimal,
                    _func = function (num) {//获取精度
                        var decimalCount = 0;
                        //先转字符
                        num = num + '';
                        //
                        if (num.indexOf('.') > -1) {
                            decimalCount = num.length - 1 - num.indexOf('.')
                        }
                        return decimalCount;
                    };


                if (selected === true) {
                    rowDatas = $grid.jqGrid('getGridRowDatasBySelected', false);
                } else {
                    //获取全部，不包括删除的数据
                    rowDatas = $grid.jqGrid('getGridRowDatas', false);
                }
                //格式化数据
                rowDatas = $grid.jqGrid('formatGridData', rowDatas, columnNames);
                //
                for (j = columnNames.length - 1; j >= 0; j--) {
                    totalObj[columnNames[j]] = 0;
                }
                //
                for (i = rowDatas.length - 1; i >= 0; i--) {
                    rowData = rowDatas[i];
                    //如果
                    if($.isFunction(filterFunc) && !filterFunc(rowData)){
                        continue;
                    }
                    //
                    for (j = columnNames.length - 1; j >= 0; j--) {
                        columnValue = 0;
                        columnName = columnNames[j];
                        //判断 列名存在
                        if (rowData.hasOwnProperty(columnName)) {
                            columnValue = rowData[columnName];
                            //列值 是数值类型
                            if (!isNaN(columnValue)) {
                                //统计对象是否存在 列名
                                if (!totalObj.hasOwnProperty(columnName)) {
                                    totalObj[columnName] = 0;
                                }
                                //同列值 相加(取小数位更多的，作为精度，避免相加后出现 很多小数位)
                                firstDecimal = _func(totalObj[columnName]);
                                secondDecimal = _func(columnValue);
                                //
                                totalObj[columnName] = Number(totalObj[columnName]) + Number(columnValue);
                                if (firstDecimal > secondDecimal) {
                                    totalObj[columnName] = Number(totalObj[columnName]).toFixed(firstDecimal);
                                }
                                if (firstDecimal < secondDecimal) {
                                    totalObj[columnName] = Number(totalObj[columnName]).toFixed(secondDecimal);
                                }
                                if (firstDecimal == secondDecimal) {
                                    totalObj[columnName] = Number(totalObj[columnName]).toFixed(firstDecimal);
                                }
                            }
                        }
                    }
                }
            });
            return totalObj;
        },

        /**
         *distinct整列显示的数据
         * @param columnName
         * @returns {Array}
         */
        getDistinctValueByColName: function (columnName) {
            var distinctValues = [],
                allValues = [];
            $(this).each(function () {
                var _grid = this, $grid = $(_grid),
                    rowDatas, rowData,
                    i,
                    unique = function getArray(colNames) {
                        var hash = {},
                            len = colNames.length,
                            result = [];

                        for (var i = 0; i < len; i++) {
                            if (!hash[colNames[i]]) {
                                hash[colNames[i]] = true;
                                if (colNames[i]) {
                                    result.push(colNames[i]);
                                }
                            }
                        }
                        return result;
                    };

                //不包括删除的数据
                rowDatas = $grid.jqGrid('getGridRowDatas', false);
                //格式化数据
                rowDatas = $grid.jqGrid('formatGridData', rowDatas, columnName);

                //
                for (i = rowDatas.length - 1; i >= 0; i--) {
                    rowData = rowDatas[i];
                    allValues.push(rowData[columnName]);
                }
                distinctValues = unique(allValues);
            });
            return distinctValues;
        },

        /**
         * 批量启用或禁用，单元格编辑模式，根据RowId 和 ColumnNames
         *
         * @param rowId 行Id
         * @param columnNames 多个列名
         * @param flag 启用true 或 禁用false
         */
        enableCellsEditableByRowId: function (rowId, columnNames, flag) {
            return this.each(function () {
                var _grid = this, $grid = $(_grid),
                    columnName,
                    cellElement,
                    i;
                //$($grid.jqGrid("getGridCellElement", rowId, "materialCode")).addClass("not-editable-cell");
                //
                for (i = columnNames.length - 1; i >= 0; i--) {
                    columnName = columnNames[i];
                    //查找单元格
                    cellElement = $grid.jqGrid("getGridCellElement", rowId, columnName);
                    //单元格存在
                    if (cellElement) {
                        //禁用
                        if (!flag) {
                            $(cellElement).addClass("not-editable-cell");
                        } else {
                            $(cellElement).removeClass("not-editable-cell");
                        }
                    }
                }
            });
        },

        /**
         * 批量设置单元格样式，根据RowId 和 ColumnNames
         *
         * @param rowId 行Id
         * @param columnNames 多个列名
         * @param style 样式对象(例： {"background-color":"#f2f2f2","background-image":"none"})
         */
        setCellsStyleByRowId: function (rowId, columnNames, style) {
            return this.each(function () {
                var _grid = this, $grid = $(_grid),
                    columnName,
                    cellElement,
                    i;
                for (i = columnNames.length - 1; i >= 0; i--) {
                    columnName = columnNames[i];
                    //查找单元格
                    cellElement = $grid.jqGrid("getGridCellElement", rowId, columnName);
                    //单元格存在
                    if (cellElement) {
                        //不为空
                        if (!$.isEmptyObject(style)) {
                            //设置样式
                            $(cellElement).css(style);
                        }
                    }
                }
            });
        },

        /**
         * 格式化数据
         * @param rowDatas 表格数据
         * @param columnNames 列名（可空）
         * @returns {*}
         */
        formatGridData: function (rowDatas, columnNames) {
            this.each(function () {
                var _grid = this, p = _grid.p,
                    rowData,
                    columnName, cellValue,
                    i, j, k, cm,
                    columnNameModelMap = {},
                    editoptions,
                    _getColModelByName = function (colName) {
                        //
                        if (!columnNameModelMap.hasOwnProperty(colName)) {
                            for (k = 0; k < p.colModel.length; k++) {
                                cm = p.colModel[k];
                                if (cm.formatter && cm.name == colName) {
                                    columnNameModelMap[colName] = cm;
                                    break;
                                    //var opts = {colModel: cm, gid: p.id, pos: k};
                                    //cellValue = $.fn.fmatter.call(_grid, cm.formatter, cellValue, opts, {}, "add");
                                    //cellValue = cellValue.replace(/\,/g, "");//去除千分位的逗号
                                }
                            }
                        }
                        if (!columnNameModelMap.hasOwnProperty(colName)) {
                            //alert(p.id + "，没有列" + colName + ".");
                            //throw new Error(p.id + "，没有列" + colName + ".");
                            return {};
                        } else {
                            return columnNameModelMap[colName];
                        }
                    };

                //如果没有指定格式化列，就取全部列名
                if (!$.isArray(columnNames) || columnNames.length == 0) {
                    columnNames = [];
                    for (k = 0; k < p.colModel.length; k++) {
                        cm = p.colModel[k];
                        columnNames.push(cm.name);
                    }
                }

                for (i = 0; i < rowDatas.length; i++) {
                    rowData = rowDatas[i];
                    //如果指定 格式化列
                    for (j = 0; j < columnNames.length; j++) {
                        columnName = columnNames[j];
                        //首先数据 必须有此列
                        if (!rowData.hasOwnProperty(columnName)) {
                            continue;
                        }
                        //获取列对应的 colModel
                        cm = _getColModelByName(columnName);
                        //
                        //TODO:目前只处理 currencyExt 和 currency
                        //formatter: 'currencyExt'
                        //formatter: 'currency'
                        if (cm.formatter && (cm.formatter == 'currencyExt' ||
                            cm.formatter == 'currency')) {
                            cellValue = $.fn.fmatter.call(_grid, cm.formatter, rowData[columnName], {colModel: cm});
                            cellValue = cellValue.replace(/\,/g, "");//去除千分位的逗号
                            //格式化后的数据，赋值回去
                            rowData[columnName] = cellValue;
                        } else if (cm.formatter && cm.formatter == 'select' && cm.editoptions) {
                            // 下拉框选值获取文本展现
                            editoptions = cm.editoptions.value;
                            if (editoptions) {
                                cellValue = rowData[columnName]; //列值
                                for (var key in editoptions) {
                                    if (key == cellValue || (key + '') == cellValue) {
                                        rowData[columnName] = editoptions[key];
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            });
            return rowDatas;
        },

        /**
         *
         * @param buttonConfigArr 自定义按钮配置的数组，格式如：[{
                        caption:按钮上的文本，可以是空值；
                        buttonicon:按钮上的图标，如果设为“none”，则只显示按钮上的文本；
                        onClickButton:当点击按钮时所调用的方法函数，默认为null；
                        position:添加新按钮的位置，first或last；可空，默认为last；
                        title:新按钮的tooltip，可空；
                        cursor:当鼠标滑过按钮时的光标样式，默认为pointer，可空；
                        id:为按钮设置id，可空}]
         * @param position 按钮位置，可空；默认值top，三个值(top=上方,bottom=下方,both=上下都有)
         * @param navGridConfig grid的按钮栏配置，可空，默认为{
                        refresh: false, //刷新按钮
                        add: false, //新增按钮
                        edit: false,    //编辑按钮
                        del: false, //删除按钮
                        search: false,  //搜索按钮
                        view: false,    //视图按钮
                        cloneToTop: false   //下方按钮克隆到上方}
         * @returns {*}
         */
        addButtonToPager: function (buttonConfigArr, position, navGridConfig) {
            return $(this).each(function () {
                var _grid = this, p = _grid.p, $grid = $(_grid),
                    _navGridConfig = navGridConfig || {
                        refresh: false,
                        add: false,
                        edit: false,
                        del: false,
                        search: false,
                        view: false,
                        cloneToTop: true
                    },
                    _position = position || "top",
                    i,
                    buttonConfig;
                //
                $grid.jqGrid('navGrid', p.pager, _navGridConfig);
                //
                //caption:按钮上的文本，可以是空值；
                //buttonicon:按钮上的图标，如果设为“none”，则只显示按钮上的文本；
                //onClickButton:当点击按钮时所调用的方法函数，默认为null；
                //position:添加新按钮的位置，first或last；默认为last；
                //title:新按钮的tooltip
                //cursor:当鼠标滑过按钮时的光标样式，默认为pointer；
                //id:为按钮设置id。
                if ($.isArray(buttonConfigArr)) {
                    for (i = 0; i < buttonConfigArr.length; i++) {
                        //
                        buttonConfig = buttonConfigArr[i];
                        //如果没有Id
                        if (!buttonConfig.hasOwnProperty("id")) {
                            buttonConfig.id = "btn" + $.jgrid.randId();
                        }
                        //下方 或 两者
                        if (_position == "bottom" || _position == "both") {
                            //在下方分页栏增加按钮
                            $grid.jqGrid('navButtonAdd', p.pager, buttonConfig);
                        }
                        //上方 或 两者
                        if (_position == "top" || _position == "both") {
                            //在上方分页栏增加按钮
                            $grid.jqGrid('navButtonAdd', "pg_" + p.id + "_toppager", buttonConfig);
                        }
                    }
                }
            });
        },

        /**
         * 获取未选的row id,返回[]
         * @returns {Array}
         */
        getGridRowIdsByUnSelected: function () {
            var _grid = this, $grid = $(_grid),
                unSelectedRowIds = [],
                selectedRowIds, i, isExists = false,
                allRowIds, j;
            //已选
            selectedRowIds = $grid.jqGrid('getGridRowIdsBySelected');
            //全部row Id 包括删除的
            allRowIds = $grid.jqGrid('getGridRowIds', true);
            //
            if (selectedRowIds && selectedRowIds.length > 0) {
                //
                for (j = 0; j < allRowIds.length; j++) {
                    isExists = false;
                    for (i = 0; i < selectedRowIds.length; i++) {
                        if (allRowIds[j] == selectedRowIds[i]) {
                            isExists = true;
                            break;
                        }
                    }
                    //如果不存在
                    if (!isExists) {
                        unSelectedRowIds.push(allRowIds[j]);
                    }
                }
            } else {
                unSelectedRowIds = allRowIds;
            }
            return unSelectedRowIds;
        },

        /**
         * 获取未选的row id,返回[]
         * @returns {Array}
         */
        getGridRowDatasByUnSelected: function () {
            var _grid = this, $grid = $(_grid), unSelectedRowIds;
            //未选Id
            unSelectedRowIds = $grid.jqGrid('getGridRowIdsByUnSelected');
            //
            return $grid.jqGrid('getGridRowDatasByIds', unSelectedRowIds);
        },
        /**
         * 根据列名 修改列头标题
         * @param colName
         * @param newLabel
         */
        setHeadLabel: function (colName, newLabel) {
            return $(this).each(function () {
                var _grid = this, p = _grid.p, $grid = $(_grid), $th, $labelDiv, $labelChildren;
                $th = $("th#" + p.id + "_" + colName + '[role="columnheader"]');
                $labelDiv = $th.find("#jqgh_" + p.id + "_" + colName);
                //
                $labelChildren = $labelDiv.children().clone(true);
                //
                $labelDiv.html(newLabel).append($labelChildren);
            });
        },

        /**
         * 判断表格是否被修改
         * @returns {*|boolean}
         */
        isDirty: function () {
            var _grid = this, $grid = $(_grid),
                //获取改变过的行ID
                ids = $grid.jqGrid('getGridRowIdsByChange');
            return $.isArray(ids) && ids.length > 0;
        },

        /**
         * 根据 rowId(如果为空 获取第一行) 聚焦到第一个可以编辑的单元格
         * @param rowId 行id(可空)
         */
        focusFirstEditableCellByRowId: function (rowId) {
            return $(this).each(function () {
                var _grid = this, $grid = $(_grid),
                    timeObj,
                    indexArr;
                if ($.trim(rowId).length === 0) {
                    var allRowDatas = $grid.jqGrid("getGridRowDatas", false);
                    //聚焦第一行
                    if ($.isArray(allRowDatas) && allRowDatas.length > 0) {
                        rowId = allRowDatas[0];
                    }
                }
                //还是没有值，不聚焦
                if ($.trim(rowId).length !== 0) {
                    timeObj = window.setTimeout(function () {
                        //获取新增行的 第一个可编辑单元格行列坐标
                        indexArr = $grid.jqGrid("getFirstEditableCellRowCol", rowId);
                        //触发编辑状态
                        $grid.jqGrid("editGridCell", indexArr[0], indexArr[1]);
                        //回收时间对象
                        window.clearTimeout(timeObj);
                    }, 100);
                }
            });
        },

        /**
         * 过滤行，留下搜索文本的行
         * @param searchText 搜索文本(可空)
         * @param colNameArr 列名数组(可空)
         * @returns {*}
         */
        filterRow: function (searchText, colNameArr) {
            return this.each(function () {
                var _grid = this, $grid = $(_grid), gridElm = _grid, p = gridElm.p,
                    rowIds,
                    rowId,
                    $row,
                    $tds,
                    $td,
                    k, j,
                    isExist;
                if (searchText && $.trim(searchText).length > 0) {
                    //
                    rowIds = $grid.jqGrid('getGridRowIds', false);
                    for (k = 0; k < rowIds.length; k++) {
                        isExist = false;
                        rowId = rowIds[k];
                        //遍历行
                        $row = $grid.find("tr#" + rowId + "[role='row']");
                        //搜索指定列
                        if (colNameArr && colNameArr.length > 0) {
                            for (j = 0; j < colNameArr.length; j++) {
                                $td = $row.find('td[role="gridcell"][aria-describedby="' + p.id + '_' + colNameArr[j] + '"]');
                                //判断单元格 是否包含搜索值
                                if ($td &&
                                    $td.length === 1 &&
                                    $.trim($td.text()).length > 0 &&
                                    $td.text().indexOf(searchText) >= 0) {
                                    isExist = true;
                                    break;
                                }
                            }
                        } else {//搜索所以列
                            $tds = $row.find('td[role="gridcell"][aria-describedby]');
                            if ($tds && $tds.length > 0) {
                                for (j = 0; j < $tds.length; j++) {
                                    $td = $($tds[j]);
                                    //判断单元格 是否包含搜索值
                                    if ($td &&
                                        $td.length === 1 &&
                                        $.trim($td.text()).length > 0 &&
                                        $td.text().indexOf(searchText) >= 0) {
                                        isExist = true;
                                        break;
                                    }
                                }
                            }
                        }
                        //如果不包含，隐藏行
                        if (!isExist) {
                            if ($row.is(":visible")) {
                                $row.hide();
                            }
                        } else {
                            if ($row.is(":hidden")) {
                                $row.show();
                            }
                        }
                    }
                } else {
                    //搜索文本为空时，显示所以行
                    rowIds = $grid.jqGrid('getGridRowIds', false);
                    for (k = 0; k < rowIds.length; k++) {
                        isExist = false;
                        rowId = rowIds[k];
                        //遍历行
                        $row = $grid.find("tr#" + rowId + "[role='row']");
                        if ($row.is(":hidden")) {
                            $row.show();
                        }
                    }
                }
            });
        },

        /**
         * 获取 行数据改变存储
         */
        getChangeStore: function () {
            var _grid = this, gridElm = _grid[0], p = gridElm.p;
            $(_grid).jqGrid("restoreGridCell");
            return p._changeStore;
        },

        /**
         * 清除 行数据改变存储
         */
        clearChangeStore: function () {
            this[0].p._changeStore = {};
        },

        /**
         * 根据 rowId 和 列名 获取 列坐标
         * @param rowId 行Id值
         * @param name 列名
         */
        getColIndexByRowIdAndName: function (rowId, name) {
            var _grid = this, gridElm = _grid[0], p = gridElm.p,
                colIndex = -1, i;
            //
            if (gridElm.grid && p.cellEdit === true) {
                //
                for (i = 0; i < p.colModel.length; i++) {
                    if (p.colModel[i].editable === true && p.colModel[i].name === name) {
                        colIndex = i;
                        break;
                    }
                }
            }
            return colIndex;
        },

        /**
         * 设置表格属性(对于部分属性设置后,会对grid起到作用,不是全部属性都可以动态设置)
         * 例如：
         *  $grid.jqGrid('setAttr',{"cellEdit":false,"rowNum":18})    同时 修改 表格编辑 和 分页记录数
         */
        setAttr: function (attributeConfig) {
            return this.each(function () {
                $.extend(this.p, attributeConfig);
            });
        }

    });

    /**
     * 根据列名 统计列值的
     * @param rowDatas 多行行数据
     * @param columnName 列名
     */
    $.jgrid.calculateTotalValueByColumnName = function (rowDatas, columnName) {
        var rowData, columnValue, i, totalValue = 0;
        //
        for (i = rowDatas.length - 1; i >= 0; i--) {
            rowData = rowDatas[i];
            columnValue = Number(rowData[columnName]);
            //列名存在，并且 列值 是数值类型
            if (rowData.hasOwnProperty(columnName) && !isNaN(columnValue)) {
                totalValue += columnValue;
            }
        }
        return totalValue;
    };

})(jQuery);