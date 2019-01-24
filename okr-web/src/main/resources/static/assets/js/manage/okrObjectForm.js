var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        validateRule: function () {
            return {
                name: {label: '目标', required: true, minLength:2,maxLength:32},
                teamId: {label: '所属团队', required: true}
            };
        },

        init: function () {
            var _this = this;
            require(["AutoCombobox"], function () {
                _this.getTeamCombo();
                _this.getParentCombo();
            });
            require(["AutoTree"], function () {
                _this.getTeamsTree();
                _this.getLabelsTree();
            });
            require(["jqForm"], function () {
                _this.getForm();
            });
            _this.initDrag();
            _this.initRelData('relTeams', '#teamNames');
            _this.initRelData('relLabels', '#labelNames');

            require(["AutoTree"], function () {
                // 处理完关联数据之后 刷新2个tree控件
                _this.getTeamsTree().AutoTree('reload');
                _this.getLabelsTree().AutoTree('reload');
            });
        },

        initRelData: function (name, targetName) {
            var nameArr = [];
            $.each($("input[name='" + name + "']"), function (ind, team) {
                nameArr.push($(team).data('name'));
            });
            if (nameArr.length > 0) {
                $(targetName).val(nameArr.join(','));
            }
        },

        getTeamCombo: function () {
            //渲染控件
            return $("#teamName").AutoCombobox({
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
                        {name: "name", label: "目标名"}
                    ],
                    bindFill: {"#teamName": "name", "#teamId": "id"}
                }
            });
        },

        getParentCombo: function () {
            //渲染控件
            return $("#parentName").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/manage/okrObject/getParentObject.json?type=" + (parseInt($('#type').val()) + 1),
                    dataSourceType: "onceRemote"
                },
                view: {
                    singleColumnNotHead: true,
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    },
                    colModels: [
                        {name: "id", label: "id", isHide: true},
                        {name: "name", label: "目标名"}
                    ],
                    bindFill: {"#parentName": "name", "#parentId": "id"}
                }
            });
        },

        getTeamsTree: function () {
            return $('#teamNames').AutoTree({
                async: {
                    dataSourceType: "onceRemote",
                    url: App["contextPath"] + "/manage/okrTeam/getTeamList.json"
                },
                view: {
                    inputFilterFieldNames: ["name"],
                    viewUniqueFieldName: "name",
                    widthRefer: function () {
                        return $(this).width() + 14;//引用当前自己输入框
                    },
                    dropDownWithin: "form"
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
                    url: App["contextPath"] + "/manage/okrLabel/getAllLabel.json"
                },
                view: {
                    inputFilterFieldNames: ["name"],
                    viewUniqueFieldName: "name",
                    widthRefer: function () {
                        return $(this).width() + 14;//引用当前自己输入框
                    },
                    dropDownWithin: "form"
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
            var scroll = document.getElementById('drag-bar'),
                bar = document.getElementById('drag-hand'),
                mask = document.getElementById('drag-past'),
                confidenceLevelSpan = document.getElementById('confidenceLevelSpan'),
                confidenceLevel = $('#confidenceLevel'),
                barleft = 0;
            bar.style.left = $('#confidenceLevelSpan').html()* 10 + '%';
            mask.style.width = $('#confidenceLevelSpan').html()* 10 + '%';
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
                    confidenceLevelSpan.innerHTML =  parseInt(barleft/(scroll.offsetWidth-bar.offsetWidth) * 10) ;
                    confidenceLevel.val(confidenceLevelSpan.innerHTML);
                    //防止选择内容--当拖动鼠标过快时候，弹起鼠标，bar也会移动，修复bug
                    window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
                }
            };
            document.onmouseup = function(){
                document.onmousemove = null; //弹起鼠标不做任何操作
            }
        },

        getForm: function () {
            return $("#objectForm").jqForm({});
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.init();
    });
});