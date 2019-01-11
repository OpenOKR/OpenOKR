var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        init: function () {
            require(["zTree"], function () {
                pageObj.getUsersTree();
            });
            require(["laydate"], function () {
                pageObj.decareDate();
            });
            require(["jqForm"], function () {
                pageObj.getForm();
            });
        },

        validateRule: function () {
            var metricUnit = $("input[name='metricUnit']:checked").val();
            if (metricUnit === '1') {
                return {
                    name: {label: '名称', required: true, minLength:2,maxLength:32},
                    metricUnit: {label: '执行单位', required: true},
                    endTs: {label: '完成时间', required: true}
                };
            } else {
                return {
                    name: {label: '名称', required: true, minLength:2,maxLength:32},
                    metricUnit: {label: '执行单位', required: true},
                    endTs: {label: '完成时间', required: true},
                    targetValue: {label: '目标值', required: true, reqExp: /^\d+(\.\d+)?$/, reqExpMsg: '只允许输入数字'},
                    initialValue: {label: '初始值', required: true, reqExp: /^\d+(\.\d+)?$/, reqExpMsg: '只允许输入数字'}
                };
            }
        },

        getForm: function () {
            return $("#resultForm").jqForm({});
        },

        decareDate: function () {
            laydate.render({
                elem: '#endTs',
                eventElem: '#iEndTs',
                trigger: 'click',
                format: 'yyyy-MM-dd'
            });
        },

        // getUsersTree: function () {
        //     return $('#users').AutoTree({
        //         async: {
        //             dataSourceType: "onceRemote",
        //             url: App["contextPath"] + "/sys/organization/findContainUserOfAll.json"
        //         },
        //         view: {
        //             height: 150,
        //             inputFilterFieldNames: ["name"],
        //             viewUniqueFieldName: "name",
        //             widthRefer: function () {
        //                 return $(this).width() + 14;//引用当前自己输入框
        //             }
        //         },
        //         callback: {
        //             afterLoad: function (dataStore) {
        //                 console.log(dataStore);
        //                 dataStore[0].ischeck = false;
        //                 if ($('#users').val() !== '') {
        //                     $(this).AutoTree('setCheckedNodes', $('#users').val());
        //                 }
        //             }
        //         },
        //         treeConfig: {
        //             check: {
        //                 enable: true
        //             }
        //         }
        //     });
        // },

        /**
         * 用户树
         * @returns {*}
         */
        getUsersTree: function () {
            //渲染树
            var treeId = 'ulUsersTree', $tree = $('#' + treeId);
            var beforeClick = function (treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                zTree.checkNode(treeNode, !treeNode.checked, null, true);
                return false;
            };
            var onCheck = function (e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj(treeId),
                    nodes = zTree.getCheckedNodes(true),
                    v = "";
                for (var i=0, l=nodes.length; i<l; i++) {
                    v += nodes[i].realName + ",";
                }
                if (v.length > 0 ) v = v.substring(0, v.length-1);
                var cityObj = $("#users");
                cityObj.attr("value", v);
            };
            if (pageObj._initUsersTree !== true) {//防重复
                $.fn.zTree.init($tree, {
                    check: {enable: true},
                    view: {dblClickExpand: false},
                    data: {simpleData: {enable: true}, key: {name: "realName"}},
                    callback: {beforeClick: beforeClick, onCheck: onCheck}
                });
                //给用户树赋值数据
                $.ajax({
                    url: App["contextPath"] + "/sys/organization/findContainUserOfAll.json",
                    dataType: "json",
                    success: function (data) {
                        //初始化根节点不允许选择，默认选择关键结果中已关联的数据
                        var userIds = $('#usersId').val().split(',');
                        var checkFunc = function (nodes) {
                            $.each(nodes, function (idx, node) {
                                if (node.type === '1') {
                                    node.nocheck = true;
                                }
                                if (node.type !== '1' && $.inArray(node.id, userIds) !== -1) {
                                    node.checked = true;
                                }
                                if (node.children && node.children.length > 0) {
                                    checkFunc(node.children);
                                }
                            });
                        };
                        checkFunc(data);
                        pageObj.getUsersTree().addNodes(null, data);
                    },
                    error: function (res) {

                    }
                });
                pageObj._initUsersTree = true;
            }
            return $.fn.zTree.getZTreeObj(treeId);
        },

        showTree: function () {
            $('#treeContent').css({zIndex: '9999'});
            $("#treeContent").slideDown("fast");
            $("body").bind("mousedown", pageObj.onBodyDown);
        },
        hideTree: function () {
            $("#treeContent").fadeOut("fast");
            $("body").unbind("mousedown", pageObj.onBodyDown);
        },
        onBodyDown: function (event) {
            if (!(event.target.id == "#users" || event.target.id == "treeContent" || $(event.target).parents("#treeContent").length>0)) {
                pageObj.hideTree();
            }
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.init();

        // 单选按钮绑定事件
        $("input[name='metricUnit']").bind("click", function () {
            if ($(this).val() === '1') {
                $('#targetValue').parent().parent().hide();
                $('#initialValue').parent().parent().hide();
            } else {
                $('#targetValue').parent().parent().show();
                $('#initialValue').parent().parent().show();
            }
        });
    });
});