(function ($) {
    $.jgrid.extend({
        /**
         * 修改grid的实际数据存储
         * @param rowId 行id
         * @param newRowData 行数据
         * @private
         */
        _setGridRowDataStoreById: function (rowId, newRowData) {
            var _grid = this, $grid = $(_grid), gridElm = _grid[0], p = gridElm.p, oldRowData;
            //先取出
            oldRowData = $grid.jqGrid("_getGridRowDataStoreById", rowId) || {};
            //
            if (oldRowData != null) {
                if (Number(oldRowData[p.rowStateName]) === $.jgrid.RowState.UNCHANGED) {
                    //如果 原行数据 状态未改变（UNCHANGED）,设置为修改状态(删除、新增 状态不能被修改)
                    oldRowData[p.rowStateName] = $.jgrid.RowState.MODIFIED;
                }
            }
            //新数据覆盖旧数据
            newRowData = $.extend({}, oldRowData, newRowData);
            //启用 被修改数据存储器
            if (p.enabledChangeStore) {
                if (p._originalDatas && p._originalDatas.hasOwnProperty(p.jsonReader.root)) {
                    var k = 0, key,
                        jsonReader = p.jsonReader,
                        originalArr = p._originalDatas[jsonReader.root],
                        originalRowData = {},
                        _setChangeStore = function (rowId, name, value, restore) {
                            //p._changeStore[rowId] = newRowData;
                            if ($.isEmptyObject(p._changeStore[rowId])) {
                                p._changeStore[rowId] = {};
                            }
                            //是否改回 原数据
                            if (!restore) {
                                $.extend(p._changeStore[rowId], $.parseJSON('{"' + name + '":"' + value + '"}'));
                            } else {
                                delete p._changeStore[rowId][name];
                                if ($.isEmptyObject(p._changeStore[rowId])) {
                                    delete p._changeStore[rowId];
                                }
                            }
                        };
                    //
                    //判断是 新增状态
                    if (newRowData[jsonReader.id]
                        && $.trim(newRowData[p.rowStateName]).length > 0
                        && newRowData[p.rowStateName] === $.jgrid.RowState.ADDED) {
                        for (key  in newRowData) {
                            _setChangeStore(newRowData[jsonReader.id], key, newRowData[key], false);
                        }
                    } else {
                        for (; k < originalArr.length; k++) {
                            originalRowData = originalArr[k];
                            //和最原始的数据对比，区分出被修改的字段
                            if (newRowData[jsonReader.id] === originalRowData[jsonReader.id]) {
                                for (key  in newRowData) {
                                    if (newRowData[key] !== originalRowData[key]) {
                                        _setChangeStore(newRowData[jsonReader.id], key, newRowData[key], false);
                                    } else {
                                        //如果又改回原值
                                        _setChangeStore(newRowData[jsonReader.id], key, newRowData[key], true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            p._gridDataStore[rowId] = newRowData;
            $grid.triggerHandler("afterSetGridRowDataEvent", [newRowData]);
        },
        /**
         * 根据id获取grid的实际数据存储
         * @param rowId 行id
         * @returns {*}
         * @private
         */
        _getGridRowDataStoreById: function (rowId) {
            var gridDataStore = this[0].p._gridDataStore;
            if (gridDataStore && gridDataStore[rowId]) {
                //防止获取的数据，因对象引用，直接被修改
                return $.extend({}, gridDataStore[rowId]);
            } else {
                return null;
            }
        },
        /**
         * 根据Id删除grid的实际数据存储
         * @param rowId
         * @param forceDeleteForStore 是否强制删除不保留在store中（可空）
         * @private
         */
        _deleteGridRowDataStoreById: function (rowId, forceDeleteForStore) {
            var _grid = this, $grid = $(_grid), gridElm = _grid[0], p = gridElm.p,
                rowData = $grid.jqGrid("_getGridRowDataStoreById", rowId);
            //新增状态 或者 指定强制删除 的数据直接删除
            if (Number(rowData[p.rowStateName]) === $.jgrid.RowState.ADDED || forceDeleteForStore === true) {
                delete gridElm.p._gridDataStore[rowId];
                //启用 被修改数据存储器
                if (p.enabledChangeStore) {
                    delete gridElm.p._changeStore[rowId];
                }
            } else {
                //未改变、修改状态的数据 从表格消失，实际改为删除状态，还保留在store，获取时能得到
                rowData[p.rowStateName] = $.jgrid.RowState.DELETED;
                //重新设置回去
                $grid.jqGrid("_setGridRowDataStoreById", rowId, rowData);
            }
            $grid.triggerHandler("afterDeleteGridRowDataEvent", [rowData]);
        },
        /**
         * 删除grid的实际数据存储
         * @param rowId
         * @private
         */
        _deleteGridRowDataStore: function (rowId) {
            delete this[0].p._gridDataStore[rowId];
        },
        /**
         * 获取grid所有的实际数据存储
         * @returns {Array|string|Blob|*}
         * @private
         */
        _getGridRowDataStore: function () {
            //防止获取的数据，因对象引用，直接被修改
            return $.extend({}, this[0].p._gridDataStore.slice(0));
        }
    });
})(jQuery);