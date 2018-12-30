/**
 * 依赖于 DropDownTree
 * @type {global|*|{}}
 */
var AutoTreeUtil = $.extend(true, {}, AutoTreeUtil, {

    ///**
    // * 参考配置
    // * 数据结构
    // [{name:1,code:1},
    // {name:2,code:2},
    // {name:3,code:4},
    // {name:1_1,code:1_1,parentId:1},
    // {name:1_1_1,code:1_1_,parentId:1_1}]
    // */
    //getMaterialType: {
    //    async: {
    //        dataSourceType: "onceRemote",
    //        url: global["contextPath"] + "/xxx/xxx/xxxTree.json"
    //    },
    //    view: {
    //        inputFilterFieldNames: ["code", "name"],
    //        positionRefer: function () {
    //            //联想控件输入框 和 "三个点"，被父<div>包着。
    //            //下拉层的定位参照 是父<div>
    //            return $(this).parent("div");
    //        }
    //    },
    //    treeConfig: {
    //        data: {
    //            simpleData: {
    //                enable: true,
    //                idKey: "id",
    //                pIdKey: "parentId"
    //            }
    //        }
    //    }
    //},

    /**
     * 查询全部
     */
    all: {
        async: {
            dataSourceType: "onceRemote",
            url: App["contextPath"] + "/sys/organization/findAll.json"
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
            }
        }
    },

    /**
     * 查询当前及子
     */
    currentAndChildren: {
        async: {
            dataSourceType: "onceRemote",
            url: App["contextPath"] + "/sys/organization/findCurrentAndChildren.json"
        },
        view: {
            inputFilterFieldNames: ["name"],
            viewUniqueFieldName: "name",
            widthRefer: function () {
                return $(this).parent("div").outerWidth() - 3;
            },
            positionRefer: function () {
                return $(this).parent("div");
            }
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
            }
        }
    }

});