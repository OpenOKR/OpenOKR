var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        init: function () {
            require(["AutoCombobox"], function (){
                pageObj.getParentCombo();
            });
            require(["zTree"], function (){
                pageObj.getUsersTree();
            });
            require(["jqForm"], function () {
                pageObj.getForm();
            });
        },

        validateRule: function () {
            return {
                name: {label: '名称', required: true, minLength:2,maxLength:32}
            };
        },

        getParentCombo: function () {
            //渲染控件
            return $("#parentName").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/manage/okrTeam/getTeamList.json",
                    dataSourceType: "onceRemote"
                },
                view: {
                    singleColumnNotHead: true,
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    },
                    colModels: [
                        {name: "id", label: "id", isHide: true},
                        {name: "name", label: "团队名"}
                    ],
                    bindFill: {"#parentName": "name", "#parentId": "id"}
                }
            });
        },

        getForm: function () {
            return $("#teamForm").jqForm({});
        },

        /**
         * 用户树
         * @returns {*}
         */
        getUsersTree: function () {
            //渲染树
            var treeId = 'ulUsersTree', $tree = $('#' + treeId);
            if (pageObj._initUsersTree !== true) {//防重复
                $.fn.zTree.init($tree, {
                    check: {
                        enable: true,
                        checkboxType: {"Y": "ps", "N": "ps"}
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                });
                //给用户树赋值数据
                $.ajax({
                    url: App["contextPath"] + "/sys/organization/findContainUserOfAll.json",
                    dataType: "json",
                    success: function (data) {
                        //递归更改选择用户更数据
                        pageObj.checkUsers(data);
                        //添加数据
                        var intervalTimeObj = window.setInterval(function () {
                            //判断菜单树已经完成数据装载
                            if (pageObj._usersTreeCheckLoaded) {
                                pageObj.getUsersTree().addNodes(null, data);
                                window.clearInterval(intervalTimeObj);
                            }
                        }, 100);
                        pageObj.hideNodes = [];
                        fuzzySearch('ulUsersTree','#searchKey', null, true, pageObj.hideNodes); //初始化模糊搜索方法
                    },
                    error: function (res) {

                    }
                });
                pageObj._initUsersTree = true;
            }
            return $.fn.zTree.getZTreeObj(treeId);
        },

        checkUsers: function (nodes) {
            if (pageObj.teamId) {
                $.ajax({
                    type: 'get',
                    url: App["contextPath"] + "/manage/okrTeam/getUsersByTeamId.json?teamId=" + pageObj.teamId,
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data && data.length > 0) {
                            //判断用户树已经完成数据装载
                            var userIds = [];
                            $.each(data, function (idx, item) {
                                userIds.push(item.id);
                            });
                            //勾选用户
                            var checkFunc = function (nodes, parent, count) {
                                var _count = 0;
                                $.each(nodes, function (idx, node) {
                                    if (node.type !== '1' && $.inArray(node.id, userIds) !== -1) {
                                        node.checked = true; _count++;
                                    }
                                    if (node.children && node.children.length > 0) {
                                        checkFunc(node.children, node, node.children.length);
                                    }
                                });
                                if (count === _count && parent) {
                                    parent.checked = true;
                                }
                            };
                            checkFunc(nodes, null, nodes.length);
                            pageObj._usersTreeCheckLoaded = true;
                        } else {
                            pageObj._usersTreeCheckLoaded = true;
                        }
                    },
                    error: function (res) {

                    }
                });
            } else {
                pageObj._usersTreeCheckLoaded = true;
            }
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.init();
    });
});