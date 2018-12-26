var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        validateRule: function () {
            return {
                name: {label: '目标', required: false, minLength:2,maxLength:32}
            };
        },

        init: function () {
            require(["AutoCombobox"], function () {
                pageObj.getParentCombo();
            });
            require(["AutoTree"], function () {
                pageObj.getTeamsTree();
                pageObj.getLabelsTree();
            });
            pageObj.initDrag();
        },

        getParentCombo: function () {
            //渲染控件
            return $("#parentName").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/manage/okrObject/getOkrListByType.json",
                    dataSourceType: "onceRemote",
                    contentType: 'application/json;charset=utf-8',
                    requestData: {searchVO: {teamId: $('#teamId').val(), type: $('#type').val()}}
                },
                view: {
                    singleColumnNotHead: true,
                    showPager: false,
                    isAllowEmpty: false,
                    isSelectedFirstRow: false,
                    dropDownContainer: "#parentName",
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    },
                    autoHeightPadding: 30,
                    colModels: [
                        {name: "id", label: "id", isHide: true},
                        {name: "name", label: "目标名"}
                    ],
                    bindFill: {"#parentName": "name", "parentId": "id"}
                }
            });
        },

        getTeamsTree: function () {
            return $('#teamNames').AutoTree({
                async: {
                    dataSourceType: "onceRemote",
                    url: App["contextPath"] + "/team/getSports.json"
                },
                view: {
                    inputFilterFieldNames: ["name"],
                    viewUniqueFieldName: "name",
                    widthRefer: function () {
                        return $(this).width() + 14;//引用当前自己输入框
                    }
                },
                callback: {
                    beforeAjaxSuccess: function (treeData) {
                        //设置为全部展开
                        for (var i = 0; i < treeData.length; i++) {
                            treeData[i].open = true;
                        }
                    },
                    afterLoad: function (dataStore) {
                        if ($('#teamNames').val() !== '') {
                            $(this).AutoTree('setCheckedNodes', $('#teamNames').val());
                        }
                    }
                },
                treeConfig: {
                    check: {
                        enable: true
                    }
                }
            });
        },

        getLabelsTree: function () {
            return $('#labelNames').AutoTree({
                async: {
                    dataSourceType: "onceRemote",
                    url: App["contextPath"] + "/label/getSports.json"
                },
                view: {
                    inputFilterFieldNames: ["name"],
                    viewUniqueFieldName: "name",
                    widthRefer: function () {
                        return $(this).width() + 14;//引用当前自己输入框
                    }
                },
                callback: {
                    beforeAjaxSuccess: function (treeData) {
                        //设置为全部展开
                        for (var i = 0; i < treeData.length; i++) {
                            treeData[i].open = true;
                        }
                    },
                    afterLoad: function (dataStore) {
                        if ($('#labelNames').val() !== '') {
                            $(this).AutoTree('setCheckedNodes', $('#labelNames').val());
                        }
                    }
                },
                treeConfig: {
                    check: {
                        enable: true
                    }
                }
            });
        },

        initDrag: function () {
            //拖拽的一个插件开始
            var scroll = document.getElementById('drag-bar');
            var bar = document.getElementById('drag-hand');
            var mask = document.getElementById('drag-past');
            var ptxt = document.getElementById('confidenceLevel');
            var barleft = 0;
            bar.style.left = $('#confidenceLevel').html()* 10 + '%';
            mask.style.width = $('#confidenceLevel').html()* 10 + '%';
            bar.onmousedown = function(event){
                var event = event || window.event;
                var leftVal = event.clientX - this.offsetLeft;
                var that = this;
                // 拖动一定写到 down 里面才可以
                document.onmousemove = function(event){
                    var event = event || window.event;
                    barleft = event.clientX - leftVal;
                    if(barleft < 0)
                        barleft = 0;
                    else if(barleft > scroll.offsetWidth - bar.offsetWidth)
                        barleft = scroll.offsetWidth - bar.offsetWidth;
                    mask.style.width = barleft +'px' ;
                    that.style.left = barleft + "px";
                    ptxt.innerHTML =  parseInt(barleft/(scroll.offsetWidth-bar.offsetWidth) * 10) ;

                    //防止选择内容--当拖动鼠标过快时候，弹起鼠标，bar也会移动，修复bug
                    window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
                }
            };
            document.onmouseup = function(){
                document.onmousemove = null; //弹起鼠标不做任何操作
            }
        },

        getForm: function () {
            return $("#objectForm").jqForm();
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.init();
    });
});