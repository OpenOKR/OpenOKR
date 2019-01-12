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
            return {
                name: {label: '名称', required: true, minLength:2,maxLength:32},
                metricUnit: {label: '执行单位', required: true},
                endTs: {label: '完成时间', required: true},
                targetValue: {label: '目标值', required: false, reqExp: /^\d+(\.\d+)?$/, reqExpMsg: '只允许输入数字'},
                initialValue: {label: '初始值', required: false, reqExp: /^\d+(\.\d+)?$/, reqExpMsg: '只允许输入数字'}
            };
        },

        initEvent: function () {
            $("input[name='radio']").bind('click', function () {
                if ($(this).val() === '0') {
                    $('#contentLi').show();
                    $("li[data-name='addResult']").hide();
                    $('#targetValue').parent().parent().hide();
                    $('#initialValue').parent().parent().hide();
                    $('input:radio[name=metricUnit]').attr('checked',false);
                } else {
                    $('#contentLi').hide();
                    $("li[data-name='addResult']").show();
                }
            });

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
        },

        decareDate: function () {
            laydate.render({
                elem: '#endTs',
                eventElem: '#iEndTs',
                trigger: 'click'
            });
        },

        getForm: function () {
            return $("#resultAuditForm").jqForm({});
        },

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
                    v += nodes[i].name + ",";
                }
                if (v.length > 0 ) v = v.substring(0, v.length-1);
                var cityObj = $("#users");
                cityObj.attr("value", v);
            };
            if (pageObj._initUsersTree !== true) {//防重复
                $.fn.zTree.init($tree, {
                    check: {enable: true},
                    view: {dblClickExpand: false},
                    data: {simpleData: {enable: true}},
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
        pageObj.initEvent();
    });
});