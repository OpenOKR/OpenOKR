var pageObj = pageObj || {};
require(["jQuery"], function () {

    $.extend(pageObj, {

        validateRule: function () {
            return {
                name: {label: '菜单名称', required: true},
                parentName: {label: '上级菜单', required: true},
                priority: {label: '优先级', required: true},
                url: {label: '链接', required: true},
                permissionPrefixCode: {label: '权限前缀码', required: true}
            };
        },

        gridValidateRule: function (permissionPrefixCode) {
            return {
                code: {label: '权限码', required: true, reqExp: eval("/^(" + permissionPrefixCode + ")+/"), reqExpMsg: "权限码前缀必须是" + permissionPrefixCode},
                name: {label: '权限名称', required: true}
            };
        },

        getPermissionBaseData: function () {
            var $grid = pageObj.getPermissionGrid(),
                hasArr = $grid.jqGrid("getDistinctValueByColName", "code"),
                $form = pageObj.getForm(),
                formData = $form.jqForm("getValue"),
                //基础权限
                baseArr = [{code: formData.permissionPrefixCode + ":view", name: "查看"},
                    {code: formData.permissionPrefixCode + ":delete", name: "删除"},
                    {code: formData.permissionPrefixCode + ":edit", name: "编辑"}], // 默认情况下"编辑" 权限 包括了"新增"、"修改"、"保存"，其它情况以实际场景需求 自定义
                exist = false, i, k,
                retArr = [];
            //排除 表格上已有的
            for (i = 0; i < baseArr.length; i++) {
                exist = false;
                for (k = 0; k < hasArr.length; k++) {
                    if (baseArr[i].code === hasArr[k]) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    retArr.push(baseArr[i]);
                }
            }
            return retArr;
        },

        init: function () {
            require(["AutoTree"], function () {
                pageObj.getTree();
                pageObj.getParentNameTree();
            });
            require(["jqGrid"], function () {
                pageObj.getPermissionGrid();
            });
            require(["jqForm"], function () {
                pageObj.getForm();
            });
        },

        getTree: function () {
            //渲染控件
            return $("#queryMenuTree").AutoTree({
                async: {
                    dataSourceType: "remote",
                    url: App["contextPath"] + "/sys/menu/findAll.json"
                },
                view: {
                    inputFilterFieldNames: ["name"],
                    viewUniqueFieldName: "name",
                    dropDownContainer: "#queryMenuTreeContainer",
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    },
                    autoHeightPadding: 30
                },
                callback: {
                    beforeAjaxSuccess: function (treeData) {
                        var i = 0;
                        //设置为全部展开
                        for (; i < treeData.length; i++) {
                            treeData[i].open = true;
                        }
                    }
                },
                treeConfig: {
                    view: {
                        addDiyDom: function (treeId, treeNode) {
                            var $node = $("#" + treeNode.tId + "_a"),
                                $nodeSpan = $("#" + treeNode.tId + "_span");
                            //把链接赋空，防止可点击
                            $node.removeAttr("href");
                            $nodeSpan.text(treeNode.priority + " - " + treeNode.name);
                        }
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        }
                    },
                    callback: {
                        beforeClick: function () {
                            if (pageObj.getForm().jqForm("isDirty") || pageObj.getPermissionGrid().jqGrid("isDirty")) {
                                return window.confirm("数据未保存，确认继续吗？");
                            }
                        },
                        onClick: function (event, treeId, treeNode) {
                            pageObj.getForm().jqForm("setDefaultValue", treeNode);
                            //重载
                            pageObj.reloadPermissionGrid(treeNode.id);
                        }
                    }
                }
            });
        },

        getParentNameTree: function () {
            //渲染控件
            return $('[name="parentName"]').AutoTree({
                async: {
                    dataSourceType: "remote",
                    url: App["contextPath"] + "/sys/menu/findAll.json"
                },
                view: {
                    inputFilterFieldNames: ["name"],
                    viewUniqueFieldName: "name",
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    }
                },
                callback: {
                    beforeAjaxSuccess: function (treeData) {
                        var i = 0;
                        //设置为全部展开
                        for (; i < treeData.length; i++) {
                            treeData[i].open = true;
                            treeData[i].url = "";//把链接赋空，防止可点击
                        }
                    }
                },
                treeConfig: {
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        }
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode) {
                            pageObj.getForm().jqForm("setValue", {
                                parentName: treeNode.name,
                                parentId: treeNode.id
                            });
                        }
                    }
                }
            });
        },

        /**
         * 定义并渲染表单对象，
         * @returns {*|jQuery} 表单jQuery对象
         */
        getForm: function () {
            return $("#menuForm").jqForm({});
        },

        getPermissionGrid: function () {
            var $grid = $("#permissionGrid");
            return $grid.jqGrid({
                colModel: [
                    {name: "code", label: "权限码", editable: true},
                    {name: "name", label: "权限名称", editable: true},
                    {name: "description", label: "描述"}
                ],
                datatype: "local",
                cellEdit: true,
                cellsubmit: "clientArray",
                enabledLoad: false,
                multiselect: true,
                toppager: false,
                topPagerButtons: false,
                topPagerRecordInfo: false,
                //自动宽度的间隔宽度
                autoWidthPadding: 36,
                //自动高度的间隔高度
                autoHeightPadding: 80,
                /**
                 * 单元格 变为可编辑控件 后事件
                 */
                afterGridEditCell: function (rowId, colName, cellValue, cellRowIndex) {
                    var cellElmId = cellRowIndex + "_" + colName,
                        $cellElm = $("#" + cellElmId),
                        timeoutObj;
                    //单元格渲染成联想控件
                    if (colName === 'code') {
                        require(["AutoCombobox"], function () {
                            timeoutObj = window.setTimeout(function () {
                                $cellElm.AutoCombobox({
                                    view: {
                                        isRememberValue: false,//不记忆值，随意输入的值可以当做数据
                                        colModels: [
                                            {name: "code", label: "权限码"}
                                        ],
                                        widthRefer: function () {
                                            return $(this).outerWidth() - 3;//引用当前输入框
                                        },
                                        localData: pageObj.getPermissionBaseData()
                                    },
                                    async: {
                                        dataSourceType: "local"
                                    },
                                    callback: {
                                        afterSelectRow: function (rowData) {
                                            //赋值 其它单元格
                                            $grid.jqGrid("setGridRowData", rowId, {
                                                "name": rowData.name
                                            });
                                            //当前单元格正处于编辑状态是，自己 不能通过 setGridRowData 或 setGridCell 接口
                                            $cellElm.val(rowData.code);
                                        },
                                        afterKeyDown: function (event) {
                                            var $target = $(event.target);
                                            //当清空值要将填充的值清空
                                            if ($target.val().length === 0) {
                                                $grid.jqGrid("setGridRowData", rowId, {
                                                    "name": ""
                                                });
                                            }
                                            //跳到下一个可编辑单元格
                                            if (event.keyCode === 13) {
                                                $grid.jqGrid("editNextCell", $grid[0].p.iRow, $grid[0].p.iCol);
                                            }
                                        }
                                    }
                                });
                                //【重点】立即触发点击
                                $cellElm.trigger("click");
                                //【重点】清理时间对象
                                window.clearTimeout(timeoutObj);
                            }, 10);
                        });
                    }
                }
            });
        },

        reloadPermissionGrid: function (menuId) {
            require(["jqGrid"], function () {
                var $grid = pageObj.getPermissionGrid();
                $.ajax({
                    type: 'GET',
                    url: App["contextPath"] + "/sys/permission/findByMenuId.json?menuId=" + menuId,
                    success: function (response) {
                        //正在编辑的单元格到不可编辑状态
                        $grid.jqGrid("restoreGridCell");
                        //清空
                        $grid.jqGrid("deleteGridRowDatas", true);
                        if (response && response.length > 0) {
                            //添加
                            $grid.jqGrid("addGridRowDatas", response);
                        }
                    }
                });
            });
        },

        /**
         * 新增
         */
        addPermission: function () {
            var $grid = pageObj.getPermissionGrid(),
                rowId = $grid.jqGrid("createRowId"),
                rowColArr;
            //表格单元格 如果处于编辑状态，需要回复成不可编辑，才可取到单元格数据。
            $grid.jqGrid("restoreGridCell");
            $grid.jqGrid("addGridRowData", rowId, {});
            //根据rowId 获取行 第一个可编辑单元格 行列 坐标
            rowColArr = $grid.jqGrid("getFirstEditableCellRowCol", rowId);
            //激活可编辑状态
            $grid.jqGrid("editGridCell", rowColArr[0], rowColArr[1], true);
        },

        /**
         * 删除
         */
        deletePermission: function () {
            var $grid = pageObj.getPermissionGrid(), ids;
            //表格单元格 如果处于编辑状态，需要回复成不可编辑，才可取到单元格数据。
            $grid.jqGrid("restoreGridCell");
            ids = $grid.jqGrid("getGridRowIdsBySelected");
            if (ids.length > 0) {
                $grid.jqGrid("deleteGridRowDataByIds", ids);
            } else {
                require(["Tips"], function () {
                    TipsUtil.warn("请先选择要删除的权限");
                });
            }
        },

        /**
         * 新增
         */
        add: function () {
            var $form = pageObj.getForm(),
                $grid = pageObj.getPermissionGrid();
            //
            if ($form.jqForm("isDirty")) {
                require(["artDialog"], function () {
                    artDialogUtil.confirm("数据未保存，确认新增吗？", function () {
                        $form.jqForm("reset", true).jqForm("focusToElement", "name");
                    });
                });
            } else {
                $form.jqForm("reset", true).jqForm("focusToElement", "name");
            }
            //表格单元格 如果处于编辑状态，需要回复成不可编辑，才可取到单元格数据。
            $grid.jqGrid("restoreGridCell");
            //清空
            $grid.jqGrid("deleteGridRowDatas", true);
        },

        /**
         * 保存
         */
        save: function () {
            var $form = pageObj.getForm(),
                formData = $form.jqForm("getValue"),
                $tree = pageObj.getTree(),
                $parentTree = pageObj.getParentNameTree(),
                $grid = pageObj.getPermissionGrid(),
                gridData,
                formValidateMsgObj,
                gridValidateMsgObj,
                validateMsg;
            //
            //表格单元格 如果处于编辑状态，需要回复成不可编辑，才可取到单元格数据。
            $grid.jqGrid("restoreGridCell");
            //获取数据，不包含删除的，进行验证
            gridData = $grid.jqGrid("getGridRowDatas", false);
            //
            //验证
            formValidateMsgObj = validateUtil.validateDatas(formData, pageObj.validateRule());
            gridValidateMsgObj = validateUtil.validateDatas(gridData, pageObj.gridValidateRule(formData.permissionPrefixCode));
            //
            if (!$.isEmptyObject(formValidateMsgObj) || !$.isEmptyObject(gridValidateMsgObj)) {
                require(["Tips"], function () {
                    //提示 拼接的验证信息
                    validateMsg = validateUtil.concatValidateMsg(formValidateMsgObj);
                    if (validateMsg) {
                        validateMsg += '<br>' + validateUtil.concatValidateMsg(gridValidateMsgObj);
                    } else {
                        validateMsg = validateUtil.concatValidateMsg(gridValidateMsgObj);
                    }
                    TipsUtil.warn(validateMsg);
                    //焦点定位到 第一个 验证不通过的控件
                    $form.jqForm("focusToElement", validateUtil.getFirstNoPassName(formValidateMsgObj));
                });
                return;
            }
            //全部数据，包含删除的，进行保存
            gridData = $grid.jqGrid("getGridRowDatas", true);
            //
            require(["Tips"], function () {
                //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
                if ($form.jqForm("isDirty") || $grid.jqGrid("isDirty")) {
                    //保存
                    $.ajax({
                        type: "post",
                        url: App["contextPath"] + "/sys/menu/save.json",
                        data: JSON.stringify({
                            vo: formData,
                            permissionEntityMapList: gridData
                        }),//将对象序列化成JSON字符串
                        dataType: "json",
                        contentType: 'application/json;charset=utf-8', //设置请求头信息
                        success: function (data) {
                            if (data.success) {
                                //重置
                                $form.jqForm("reset", true);
                                //清空
                                $grid.jqGrid("deleteGridRowDatas", true);
                                //刷新控件
                                $tree.AutoTree("reload");
                                $parentTree.AutoTree("reload");
                            }
                            TipsUtil.info(data.message);
                        },
                        error: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });
                } else {
                    TipsUtil.warn("数据未修改，无需保存！");
                }
            });
        },

        /**
         * 删除
         */
        deleteFunc: function () {
            var $form = pageObj.getForm(),
                $tree = pageObj.getTree(),
                $parentTree = pageObj.getParentNameTree(),
                $treeObj = $tree.AutoTree("getTreeObj"),
                selectedNodes = $treeObj.getSelectedNodes();
            //
            require(["Tips"], function () {
                if ($.isArray(selectedNodes) && selectedNodes.length > 0) {
                    require(["artDialog"], function () {
                        artDialogUtil.confirm("确认删除菜单（" + selectedNodes[0].name + "）吗？", function () {
                            $.ajax({
                                url: App["contextPath"] + "/sys/menu/delete.json?id=" + selectedNodes[0].id,
                                dataType: "json",
                                success: function (data) {
                                    if (data.success) {
                                        TipsUtil.info(data.message);
                                        //刷新 控件
                                        $tree.AutoTree("reload");
                                        $parentTree.AutoTree("reload");
                                        //重置表单
                                        $form.jqForm("reset", true)
                                    } else {
                                        TipsUtil.warn(data.message);
                                    }
                                },
                                error: function (res) {
                                    alert(JSON.stringify(res));
                                }
                            });
                        });
                    });
                } else {
                    TipsUtil.info("请先选择要删除的菜单");
                }
            });
        }

    });

    $(window).ready(function () {
        pageObj.init();
    });

});