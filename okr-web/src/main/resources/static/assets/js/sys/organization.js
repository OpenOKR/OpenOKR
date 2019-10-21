var pageObj = pageObj || {};
require(["jQuery"], function () {

    $.extend(pageObj, {

        validateRule: function () {
            return {
                name: {label: '机构名称', required: true},
                parentName: {label: '上级机构', required: true},
                code: {label: '编码', required: true}
            };
        },

        init: function () {
            require(["AutoTree"], function () {
                pageObj.getTree();
                pageObj.getParentNameTree();
            });
            require(["jqForm", "AppUtils"], function () {
                pageObj.getForm();
            });
        },

        getTree: function () {
            //渲染控件
            return $("#queryOrganizationTree").AutoTree({
                async: {
                    dataSourceType: "remote",
                    url: App["contextPath"] + "/sys/organization/findAll.json"
                },
                view: {
                    inputFilterFieldNames: ["name"],
                    viewUniqueFieldName: "name",
                    dropDownContainer: "#queryOrganizationTreeContainer",
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
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        }
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode) {
                            pageObj.getForm().jqForm("setDefaultValue", treeNode);
                        }
                    }
                }
            });
        },

        getParentNameTree: function () {
            return $('[name="parentName"]').AutoTree(
                $.extend(true, {}, AutoTreeUtil.all, {
                    view: {
                        bindFill: {
                            "#parentId": "id", "#parentName": "name"
                        }
                    }
                })
            );
        },

        /**
         * 定义并渲染表单对象，
         * @returns {*|jQuery} 表单jQuery对象
         */
        getForm: function () {
            return $("#organizationForm").jqForm({
                formatter: []
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
                formData = $form.jqForm("getValue"),
                $tree = pageObj.getTree(),
                $parentTree = pageObj.getParentNameTree();
            //
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
            //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
            require(["Tips"], function () {
               // if ($form.jqForm("isDirty")) {
                    //保存
                    $.ajax({
                        type: "post",
                        url: App["contextPath"] + "/sys/organization/save.json",
                        data: JSON.stringify({
                            vo: formData
                        }),//将对象序列化成JSON字符串
                        dataType: "json",
                        contentType: 'application/json;charset=utf-8', //设置请求头信息
                        success: function (data) {
                            if (data.success) {
                                //刷新控件
                                $tree.AutoTree("reload");
                                $parentTree.AutoTree("reload");
                                //重置表单
                                $form.jqForm("reset", true);
                                TipsUtil.info(data.message);
                            } else {
                                TipsUtil.error(data.message);
                            }
                        },
                        error: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });
                //} else {
                //    TipsUtil.warn("数据未修改，无需保存！");
                //}
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
                        artDialogUtil.confirm("确认删除机构（" + selectedNodes[0].name + "）吗？", function () {
                            $.ajax({
                                url: App["contextPath"] + "/sys/organization/delete.json?id=" + selectedNodes[0].id,
                                type: "GET",
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
                    TipsUtil.info("请先选择要删除的机构");
                }
            });
        }

    });

    $(window).ready(function () {
        pageObj.init();
    });

});