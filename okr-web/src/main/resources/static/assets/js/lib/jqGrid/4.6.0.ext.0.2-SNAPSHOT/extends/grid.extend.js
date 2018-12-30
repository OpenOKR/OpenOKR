//以下给jqgrid添加方法
(function ($) {
    "use strict";
    $.jgrid.extend({
        /**
         * 编辑右一个单元格
         */
        editNextCell: function (iRow, iCol) {
            this.each(function () {
                var $t = this, colLen = $t.p.colModel.length,
                    //获取当前单元格dom
                    $curCell = $("td:eq(" + iCol + ")", $t.rows[iRow]),
                    rowLen = $($t).jqGrid("getGridParam", "reccount"), $cell,
                    nRow = iCol + 1 === colLen ? iRow + 1 : iRow,
                    nCol = iCol + 1 === colLen ? 0 : iCol + 1;

                if (!$t.grid || $t.p.cellEdit !== true) {
                    return;
                }

                if (nRow > rowLen) {
                    return $t.p.onLastEditableCell.call($t, iRow, iCol);
                }

                outerloop:
                    for (; nRow <= rowLen; nRow++) {
                        for (; nCol < colLen;) {
                            $cell = $("td:eq(" + nCol + ")", $t.rows[nRow]);
                            if ($t.p.colModel[nCol].hidden !== true && !$cell.hasClass("not-editable-cell") && ( $t.p.colModel[nCol].editable === true)) {
                                break outerloop;
                            }
                            if ((nCol + 1) === colLen) {
                                nCol = 0;
                                if (nRow === rowLen) {
                                    return $t.p.onLastEditableCell.call($t, iRow, iCol);
                                } else {
                                    break;
                                }

                            } else {
                                nCol = nCol + 1;
                            }
                        }
                    }
                // 判断表格是否有subGrid，下一个单元格的父级table id 与 当前单元格父级table id 是否一致
                if ($t.p.subGrid && $($cell).parents('table:eq(0)').attr('id') !== $($curCell).parents('table:eq(0)').attr('id')) {
                    return $t.p.onLastEditableCell.call($t, iRow, iCol);
                }
                $($t).jqGrid("editCell", nRow, nCol, true);
            });
        },

        /**
         * 编辑左一个单元格
         */
        editPrevCell: function (iRow, iCol) {
            this.each(function () {
                var $t = this, colLen = $t.p.colModel.length, $cell,
                    nRow = iCol - 1 === 0 ? iRow - 1 : iRow,
                    nCol = iCol - 1 === 0 ? colLen - 1 : iCol - 1;

                if (!$t.grid || $t.p.cellEdit !== true) {
                    return;
                }
                if (iCol - 1 === 0 && iRow === 1) {
                    return;
                }
                outerloop:
                    for (; nRow >= 0; nRow--) {
                        for (; nCol >= 0;) {
                            $cell = $("td:eq(" + nCol + ")", $t.rows[nRow]);
                            if ($t.p.colModel[nCol] && !$t.p.colModel[nCol].hidden &&
                                !$cell.hasClass("not-editable-cell") &&
                                $t.p.colModel[nCol].hasOwnProperty("editable") &&
                                $t.p.colModel[nCol].editable === true) {
                                break outerloop;
                            }
                            if (nCol === 0) {
                                nCol = colLen;
                                nRow--;
                            } else {
                                nCol--;
                            }
                        }
                    }

                // 如果已经到了第1行的第1个可编辑的单元格,则恢复到非编辑状态
                if (nRow < 1) {
                    $($t).jqGrid("restoreGridCell");
                } else {
                    $($t).jqGrid("editCell", nRow, nCol, true);
                }
            });
        },
        /**
         * 编辑上一个单元格
         * @param iRow
         * @param iCol
         */
        editUpCell: function (iRow, iCol) {
            this.each(function () {
                var _grid = this, $grid = $(_grid), nRow,
                    recordCount = $grid.jqGrid("getRecordCount");
                //
                if ((iRow - 1) >= 1) {
                    nRow = iRow - 1;
                } else {
                    nRow = recordCount;
                }
                $grid.jqGrid("editCell", nRow, iCol, true);
            });
        },
        /**
         * 编辑下一个单元格
         * @param iRow
         * @param iCol
         */
        editDownCell: function (iRow, iCol) {
            this.each(function () {
                var _grid = this, $grid = $(_grid), nRow,
                    recordCount = $grid.jqGrid("getRecordCount");
                //
                if ((iRow + 1) <= recordCount) {
                    nRow = iRow + 1;
                } else {
                    nRow = 1;
                }
                $grid.jqGrid("editCell", nRow, iCol, true);
            });
        }
    });
})(jQuery);
