var pageObj = pageObj || {};
require(["jQuery"], function () {

    $.extend(pageObj, {

        SELECT_ROWID: '',

        validateRule: function () {
            return {
                name: {label: '角色名', required: true}
            };
        },

        init: function () {
            require(["AutoCombobox"], function () {
                //渲染控件
                pageObj.getCombo();
            });
            require(["jqForm"], function () {
                //初始化表单
                pageObj.getForm();
            });
            require(["zTree"], function () {
                //初始化菜单树
                pageObj.getMenuTree();///tree.json
            });
        },

        /**
         * 输入下拉框
         * @returns {jQuery}
         */
        getCombo: function () {
            return $("#queryRole").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/sys/role/findByPageLikeInputValue.json"
                },
                view: {
                    pageSize: 20,
                    isSelectedFirstRow: false,
                    dropDownContainer: "#queryRoleDropdown",
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    },
                    autoHeightPadding: 30,
                    colModels: [
                        {
                            name: "name",
                            label: "角色名称",
                            width: 80
                        }
                    ]
                },
                callback: {
                    afterSelectRow: function (rowData) {
                        //赋值到表单（setDefaultValue  与 setValue 的区别在于，setDefaultValue 可以判断表单数据是否被修改）
                        pageObj.getForm().jqForm("setDefaultValue", rowData);
                        //根据角色Id 勾选 权限项
                        pageObj.checkPermissions(rowData.id);
                    },
                    afterLoad: function (dataStore,$dataPanel) {
                        if (pageObj.SELECT_ROWID !== '') {
                            $("tr[_row_id='" + pageObj.SELECT_ROWID + "']").addClass('selected');
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
            return $("#roleForm").jqForm({});
        },

        /**
         * 菜单树
         * @returns {*}
         */
        getMenuTree: function () {
            //渲染树
            var treeId = "ulMenuTree", $tree = $("#" + treeId);
            if (pageObj._initMenuTree !== true) {//防重复
                $.fn.zTree.init($tree, {
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        }
                    },
                    view: {
                        addDiyDom: function (treeId, treeNode) {
                            // console.info(JSON.stringify(treeNode));
                            var $node = $("#" + treeNode.tId + "_a"),
                                html = "", i = 0, permission;
                            if ($.isArray(treeNode.permissionVOExtList)) {
                                for (; i < treeNode.permissionVOExtList.length; i++) {
                                    permission = treeNode.permissionVOExtList[i];
                                    //勾选或反选 设置 权限被修改过
                                    html += '&nbsp;&nbsp;<label title="' + permission.description + '"><input type="checkbox" onclick="pageObj.isDirtyPermission = true;" permission style="width: 20px;height: 20px;" value="' + permission.id + '">' + permission.name + '</label>';
                                }
                            }
                            //把链接赋空，防止可点击
                            $node.append(html).removeAttr("href");
                        }
                    }
                });
                //给菜单树赋值数据
                $.ajax({
                    url: App["contextPath"] + "/sys/menu/findContainPermissionOfAll.json",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        //更改菜单数据
                        pageObj.changeMenuData(data);
                        //添加数据
                        pageObj.getMenuTree().addNodes(null, data);
                        //标识装载完成
                        pageObj._menuTreeLoaded = true;
                    },
                    error: function (res) {
                    }
                });
                //
                require(["OUI"], function () {
                    //填充高度
                    $tree.height($.oui.utils.element.getHeightToBottom($tree, 92));
                });
                pageObj._initMenuTree = true;
            }
            return $.fn.zTree.getZTreeObj(treeId);
        },

        /**
         * 更改菜单数据
         */
        changeMenuData: function (menuData) {
            if ($.isArray(menuData)) {
                for (var i = 0; i < menuData.length; i++) {
                    menuData[i].open = true;
                }
            }
        },

        /**
         * 选中 权限项
         */
        checkPermissions: function (roleId) {
            $.ajax({
                type: "get",
                url: App["contextPath"] + "/sys/role/loadPermissionsByRoleId.json?roleId=" + roleId,
                cache: false,
                dataType: "json",
                success: function (permissions) {
                    // console.info(permissions);
                    //菜单树 是异步，要等待 菜单装载完毕
                    var intervalTimeObj = window.setInterval(function () {
                        //判断菜单树已经完成数据装载
                        if (pageObj._menuTreeLoaded) {
                            //勾选权限
                            for (var i = 0; i < permissions.length; i++) {
                                $('input:checkbox[value="' + permissions[i] + '"]').prop({checked: true});
                            }
                            //设置权限未被修改
                            pageObj.isDirtyPermission = false;
                            window.clearInterval(intervalTimeObj);
                        }
                    }, 100);
                },
                error: function (res) {
                }
            });
        },

        /**
         * 新增
         */
        add: function () {
            var $form = pageObj.getForm();
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
        },

        /**
         * 保存
         */
        save: function () {
            var $form = pageObj.getForm(),
                formData = $form.jqForm("getValue");
            //验证
            var validateMsgObj = validateUtil.validateDatas(formData, pageObj.validateRule());
            if (!$.isEmptyObject(validateMsgObj)) {
                require(["Tips"], function () {
                    //提示 拼接的验证信息
                    TipsUtil.warn(validateUtil.concatValidateMsg(validateMsgObj));
                    //焦点定位到 第一个 验证不通过的控件
                    $form.jqForm("focusToElement", validateUtil.getFirstNoPassName(validateMsgObj));
                });
                return;
            }
            //组装全部勾选的权限
            var permissionIds = [];
            $("input:checkbox:checked[permission]").each(function (ind, item) {
                permissionIds.push(item.value);
            });
            //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
            require(["Tips"], function () {
                if ($form.jqForm("isDirty") || pageObj.isDirtyPermission) {
                    //保存
                    $.ajax({
                        type: "post",
                        url: App["contextPath"] + "/sys/role/save.json",
                        data: JSON.stringify({
                            vo: formData,
                            permissionIds: permissionIds
                        }),//将对象序列化成JSON字符串
                        dataType: "json",
                        contentType: 'application/json;charset=utf-8', //设置请求头信息
                        success: function (data) {
                            if (data.success) {
                                //刷新控件
                                pageObj.getCombo().AutoCombobox("reload");
                                pageObj.SELECT_ROWID = formData._rowId;
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
            var formData = pageObj.getForm().jqForm("getValue");
            //
            require(["artDialog"], function () {
                artDialogUtil.confirm("确认删除角色 " + formData.name + " 吗？", function () {
                    $.ajax({
                        url: App["contextPath"] + "/sys/role/delete.json?id=" + formData.id,
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {
                                TipsUtil.info(data.message);
                                //刷新 控件
                                pageObj.getCombo().AutoCombobox("reload");
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
        }

    });

    $(window).ready(function () {
        pageObj.init();
    });

});