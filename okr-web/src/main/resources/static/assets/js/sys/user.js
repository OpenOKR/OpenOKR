var pageObj = pageObj || {};
require(["jQuery"], function () {

    $.extend(pageObj, {

        validateRule: function () {
            return {
                userName: {label: '用户名', required: false,reqExp:/^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$/,reqExpMsg:"不能包含特殊字符",minLength:2,maxLength:20},
                active: {label: '是否启用', required: false},
                organizationName: {label: '所属机构', required: false},
                realName: {label: '真实姓名', required: true},
                email:{label:'Email',required: true, reqExp: /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/, reqExpMsg:"格式不正确"},
                phone:{label:'手机号码',required: true, reqExp:/^(1[0-9]\d{9})$/,reqExpMsg:"格式不正确"},
                roleId: {label: '用户角色', required: true}
            };
        },

        init: function () {
            require(["AutoTree"], function () {
                pageObj.getOrganizationNameTree();
                pageObj.getRoleNameTree();

                require(["AutoCombobox"], function () {
                    pageObj.getCombo();
                });
            });

            require(["jqForm"], function () {
                pageObj.getForm();
            });
        },

        getCombo: function () {
            var _this = this;
            //渲染控件
            return $("#queryUserCombo").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/sys/user/findByPageLikeInputValue.json"
                },
                view: {
                    isSelectedFirstRow: false,
                    dropDownContainer: "#queryUserComboContainer",
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    },
                    autoHeightPadding: 30,
                    colModels: [
                        {name: "userName", label: "用户名", width: 80},
                        {name: "realName", label: "真实姓名", width: 80}
                    ]
                },
                callback: {
                    afterSelectRow: function (rowData) {
                        //赋值到表单
                        _this.getForm().jqForm("setDefaultValue", rowData);
                    }
                }
            });
        },

        getOrganizationNameTree: function () {
            return $('#organizationName').AutoTree(
                $.extend(true, {}, AutoTreeUtil.all, {
                    view: {
                        bindFill: {
                            "#organizationId": "id", "#organizationName": "name", "#orgCode": "code"
                        }
                    },
                    treeConfig: {
                        callback: {
                            onClick: function (event, treeId, treeNode) {

                            }
                        }
                    }
                })
            );
        },

        getRoleNameTree: function () {
            //渲染控件
            return $('[name="roleName"]').AutoTree({
                async: {
                    dataSourceType: "remote",
                    url: App["contextPath"] + "/sys/role/findByCurrentUserId.json"
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
                                roleName: treeNode.name,
                                roleId: treeNode.id
                            });
                        }
                    }
                }
            });
        },

        getForm: function () {
            return $("#userForm").jqForm({
                formatter: [
                    {fieldName: 'active', format: 'singleCheckbox', dataMap: {'checked': true, 'unchecked': false}}
                ]
            });
        },

        /**
         * 重置密码
         */
        resetPassword: function () {
            require(["Tips"], function () {
                var formData = pageObj.getForm().jqForm("getValue");
                //id 为空 或者 rowState为空
                if ($.trim(formData.id).length === 0 || $.trim(formData.rowState).length === 0) {
                    TipsUtil.info("该用户还未保存，无法重置密码");
                    return;
                }
                //
                require(["artDialog"], function () {
                    artDialogUtil.confirm("确认重置密码为\"123456\"？", function () {
                        $.ajax({
                            url: App["contextPath"] + "/sys/user/resetPassword.json?userId=" + formData.id,
                            success: function (data) {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                } else {
                                    TipsUtil.warn(data.message);
                                }
                            }
                        });
                    });
                });
            });
        },

        /**
         * 新增
         */
        add: function () {
            var _this = this,
                $form = pageObj.getForm(),
                addFunc = function () {
                    //重置表单
                    $form.jqForm("reset", true).jqForm("focusToElement", "username");
                };
            //
            if ($form.jqForm("isDirty")) {
                require(["artDialog"], function () {
                    artDialogUtil.confirm("数据未保存，确认清空并新增吗？", function () {
                        addFunc();
                    });
                });
            } else {
                addFunc();
                _this.getOrganizationNameTree().AutoTree('removeAllNodes').AutoTree('reload');
                _this.getRoleNameTree().AutoTree('removeAllNodes').AutoTree('reload');
            }
            _this.getCombo().AutoCombobox('reload');
        },

        /**
         * 保存
         */
        save: function () {
            var _this = this,
                $form = pageObj.getForm(),
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
            //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
            require(["Tips"], function () {
                //表格单元格 如果处于编辑状态，需要回复成不可编辑，才可取到单元格数据。
                //if ($form.jqForm("isDirty") || $roleGrid.jqGrid("isDirty")) {
                //被删除的角色
                var _saveFunc = function () {
                        //保存
                        $.ajax({
                            type: "post",
                            url: App["contextPath"] + "/sys/user/save.json",
                            data: JSON.stringify({
                                vo: formData
                            }),//将对象序列化成JSON字符串
                            dataType: "json",
                            contentType: 'application/json;charset=utf-8', //设置请求头信息
                            success: function (data) {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                    _this.getCombo().AutoCombobox('reload');
                                } else {
                                    TipsUtil.warn(data.message);
                                    if (data.code === '1') {
                                        $form.jqForm("focusToElement", "username");
                                    } else if (data.code === '2') {
                                        $form.jqForm("focusToElement", "phone");
                                    }
                                }
                            },
                            error: function (res) {
                                alert(JSON.stringify(res));
                            }
                        });
                    };
                //
                _saveFunc();
                //} else {
                //    TipsUtil.warn("数据未修改，无需保存！");
                //}
            });
        },

        /**
         * 删除
         */
        deleteFunc: function () {
            var formData = pageObj.getForm().jqForm("getValue");
            //
            require(["artDialog"], function () {
                artDialogUtil.confirm("确认删除用户（" + formData.username + "）吗？", function () {
                    $.ajax({
                        url: App["contextPath"] + "/sys/user/delete.json?id=" + formData.id,
                        dataType: "json",
                        success: function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                    //刷新 控件
                                    pageObj.getCombo().AutoCombobox("reload");
                                    pageObj.getForm().jqForm('reset', true);
                                } else {
                                    TipsUtil.warn(data.message);
                                }
                            });
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