var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        options: {useEasing: true, useGrouping: true},
        currentType: '',
        currentTeamId: '',
        editFlag: '',

        loadOKRObjects: function (type, teamId) {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#message").block();
                $.ajax({
                    url: App["contextPath"] + "/manage/okrObject/getOkrListByType.json",
                    type: "POST",
                    data: JSON.stringify({searchVO: {type: type, teamId: teamId}}),
                    contentType: 'application/json;charset=utf-8'
                }).done(function (res) {
                    pageObj.currentType = type;
                    pageObj.currentTeamId = teamId;
                    res && _this.buildOKRObjects(res);
                }).always(function () {
                    $("#message").unblock();
                });
            });
        },

        buildOKRObjects: function (res) {
            require(["Underscore", "jQueryUtils", "AppUtils"], function () {
                var $okrObjectList = $("#okrObjectList"); $okrObjectList.empty();
                var statusList = enumUtil.getEnum("objectivesStatusList.json");
                var executeList = enumUtil.getEnum("executeStatusList.json");
                $.each(res.info, function (idx, object) {
                    object.href = App.contextPath + "/manage/okrObject/okrDetail.htm?id=" + object.id + "&type=" + object.type;
                    var okrHeader =
                        '<div class="okr-header">' +
                        '   <div class="area-charts">' +
                        '       <div id="[%=object.id%]" style="width: 100%;height: 100%;"></div>' +
                        '       <div class="charts-total">' +
                        '           <span class="num" data-end="[%=object.progress%]" data-new="[%=object.progress%]">[%=object.progress%]</span>' +
                        '           <em>%</em>' +
                        '       </div>' +
                        '   </div>' +
                        '   <div class="okr-header-desc">' +
                        '       <h3 class="name">' +
                        '           [%=object.name%]' +
                        '           <div class="action">' +
                        '               <em class="em-start [%=statusList[object.status].cssClass%]">[%=statusList[object.status].name%]</em>' +
                        '               <a id="object-del-[%=object.id%]" class="btn-del text-primary" onclick="pageObj.deleteFunc([%=object.id%]);"><i class="icon-del"></i>删除</a>' +
                        '               <a id="object-edit-[%=object.id%]" class="btn-del text-primary" onclick="pageObj.editObject([%=object.id%]);"><i class="icon-edit"></i>编辑</a>' +
                        '           </div>' +
                        '       </h3>' +
                        '       <p>[%=object.description%]</p>' +
                        '   </div>' +
                        '   <div class="desc"><a id="object-detail-[%=object.id%]" class="btn btn-primary waves-effect waves-light" onclick="top.mainObj.menuClick(null, \'[%=object.href%]\')">查看详情</a></div>' +
                        '</div>';
                    var okrCon = '<div class="okr-con">' +
                        '   <ul class="okr-list">' +
                        '       [%if(!_.isNull(object.resultsExtList) && object.resultsExtList.length>0){%]' +
                        '           [%_.each(object.resultsExtList, function(item){%]' +
                        '               <li onmouseover="$(this).siblings().find(\'.participant\').hide(\'slow\');$(this).find(\'.participant\').show(\'slow\');">' +
                        '                   <div class="okr-list-in">' +
                        '                       <h4>' +
                        '                           K1：[%=item.name%]' +
                        '                           <div class="action">' +
                        '                               <span class="txt-all [%=executeList[item.status].cssClass%]">' +
                        '                                   <i class="iconfont icon-dot"></i>[%=executeList[item.status].name%]' +
                        '                               </span>' +
                        '                           </div>' +
                        '                       </h4>' +
                        '                       <div class="participant">' +
                        '                           <span class="name">参与人员：</span>' +
                        '                           <ul class="participant-list">' +
                        '                               [%if(!_.isNull(object.joinUsers) && object.joinUsers.length>0){%]' +
                        '                                   [%_.each(object.joinUsers, function(user){%]' +
                        '                                       <li class="part-item"><span><img src="/assets/images/temp/pic.png"></span></li>' +
                        '                                   [%});%]' +
                        '                                   <li class="part-item"><a href=""><i class="iconfont icon-more"></i></a></li>' +
                        '                               [%}%]' +
                        '                           </ul>' +
                        '                       </div>' +
                        '                   </div>' +
                        '                   <div class="okr-list-tab">' +
                        '                       <p class="scroll-bar">' +
                        '                           <i class="complete"></i>' +
                        '                           <i class="new"></i>' +
                        '                           <span class="vals">' +
                        '                               <em class="num" data-end="[%=item.preProgress%]" data-new="[%=item.progress%]">[%=item.progress%]</em>%' +
                        '                           </span>' +
                        '                       </p>' +
                        '                       <div class="action">' +
                        '                           <a id="result-del-[%=item.id%]" class="btn-del text-primary" onclick="pageObj.deleteResultFunc([%=item.id%])"><i class="icon-del"></i>删除</a>' +
                        '                           <a id="result-checkin-[%item.id%]" class="btn-other text-primary" onclick="pageObj.addCheckin([%=item.id%])"><i class="icon-refresh"></i>进度</a>' +
                        '                           <a id="result-edit-[%item.id%]" class="btn-del text-primary" onclick="pageObj.editResult([%=item.id%], [%=object.id%])"><i class="icon-edit"></i>编辑</a>' +
                        '                       </div>' +
                        '                   </div>' +
                        '               </li>' +
                        '           [%});%]' +
                        '       [%}%]' +
                        '   </ul>' +
                        '</div>';
                    var header = UnderscoreUtil.getHtmlByText(okrHeader, {object: object, statusList: statusList});
                    $okrObjectList.append(header);
                    var con = UnderscoreUtil.getHtmlByText(okrCon, {object: object, executeList: executeList});
                    $okrObjectList.append(con);

                    pageObj.showHideOperationButton();
                    pageObj.pieEchartsFunc(object.id, object.progress);
                });
            });
        },

        // 操作按钮显示隐藏处理
        showHideOperationButton: function () {
            switch (pageObj.currentType) {
                case '1':
                    $('#addObject').show();
                    break;
                case '2':
                    if (pageObj.editFlag === '1') {
                        $('#addObject').show();
                    } else {
                        $('#addObject').hide();
                        $('.btn-del').hide();
                    }
                    break;
                case '3':
                    if ($('#companyEditFlag').val() === '1') {
                        $('#addObject').show();
                    } else {
                        $('#addObject').hide();
                        $('.btn-del').hide();
                    }
                    break;
            }
        },

        pieEchartsFunc: function (id, progress) {
            require(["echarts"], function (echarts){
                var option = {
                    title: {text: '', x: 'center', y:"80%", textStyle: {fontSize: '12px', color:"#545454"}},
                    tooltip: {trigger: 'item', formatter: "{b} {d}%"},
                    legend: {orient: 'vertical', x: 'left', y:"-20%", data:['已完成','']},
                    series: [
                        {name:'', type:'pie', radius: ['60%', '75%'], avoidLabelOverlap: false, color:['#f57677','#dedede'],
                            label: {normal: {show: false, position: 'center'}, emphasis: {show: true, textStyle: {fontSize: '22px'}}},
                            labelLine: {normal: {show: false}},
                            data:[{value:progress, name: '已完成'}, {value: (100 - progress), name: '未完成'}]
                        }
                    ]
                };
                var myChart = echarts.init(document.getElementById(id));
                myChart.setOption(option);
            });
            require(["countUp"], function (CountUp) {
                $(".charts-total .num,.vals .num").each(function () {
                    var countUp = new CountUp(this, 0, $(this).data("new"), 0, 1, pageObj.options);
                    countUp.start();
                });
                $(".vals .num").each(function () {
                    var $bar=$(this).parents(".scroll-bar");
                    $bar.find(".complete").width($(this).data("end")+"%");
                    $bar.find(".new").width($(this).data("new")+"%");
                });
            });
        },

        tabClick: function (dom, type, teamId) {
            if (type === '2') {
                pageObj.editFlag = $(dom).data('editFlag');
            }
            $(dom).addClass('active').siblings().removeClass('active');
            pageObj.loadOKRObjects(type, teamId);
        },

        editObject: function (id) {
            require(["artDialog"], function () {
                var dialogObj = dialog({
                    url: App["contextPath"] + "/manage/okrObject/okrObjectForm.htm?objectId=" + id + "&type=" + pageObj.currentType,
                    title: '新增/编辑目标',
                    quickClose: false,
                    okValue: "保存",
                    cancelValue: "关闭",
                    ok: function () {
                        var _this = this,
                            $form = pageObj.getForm(),
                            formData = $form.jqForm("getValue"),
                            checkedTeams, checkedLabels;
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
                        //赋值 teams
                        checkedTeams = _this.getTeamsTree().AutoTree("getCheckedNodes");
                        formData.relTeams = checkedTeams;
                        //赋值 teams
                        checkedLabels = _this.getLabelsTree().AutoTree("getCheckedNodes");
                        formData.relLabels = checkedLabels;
                        //
                        //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
                        require(["Tips"], function () {
                            //表格单元格 如果处于编辑状态，需要回复成不可编辑，才可取到单元格数据。
                            //if ($form.jqForm("isDirty") || $roleGrid.jqGrid("isDirty")) {
                            //被删除的角色
                            var _saveFunc = function () {
                                //保存
                                $.ajax({
                                    type: "post",
                                    url: App["contextPath"] + "/manage/okrObject/saveObject.json",
                                    data: JSON.stringify({searchVO: formData}),//将对象序列化成JSON字符串
                                    dataType: "json",
                                    contentType: 'application/json;charset=utf-8', //设置请求头信息
                                    success: function (data) {
                                        if (data.success) {
                                            TipsUtil.info(data.message);
                                        } else {
                                            TipsUtil.warn(data.message);
                                        }
                                    },
                                    error: function (res) {
                                        alert(JSON.stringify(res));
                                    }
                                });
                            };
                            //
                            _saveFunc();
                        });
                        dialogObj.close();
                        pageObj.loadOKRObjects(pageObj.currentType, pageObj.currentTeamId);
                        return false;
                    },
                    cancel: function () {
                        //关闭对话框
                        dialogObj.close();
                        return false;
                    }
                });
                dialogObj.showModal();
            });
        },

        editResult: function (id, objectId) {
            require(["artDialog"], function () {
                var dialogObj = dialog({
                    url: App["contextPath"] + "/manage/okrResult/okrResultForm.htm?resultId=" + id + "&objectId=" + objectId,
                    title: '新增/编辑关键结果',
                    quickClose: false,
                    okValue: "保存",
                    cancelValue: "关闭",
                    ok: function () {
                        return false;
                    },
                    cancel: function () {
                        //关闭对话框
                        dialogObj.close();
                        return false;
                    }
                });
                dialogObj.showModal();
            });
        },

        addCheckin: function (id) {
            require(["artDialog"], function () {
                var dialogObj = dialog({
                    url: App["contextPath"] + "/manage/okrCheckin/okrCheckinForm.htm?resultId=" + id,
                    title: '每周更新关键结果',
                    quickClose: false,
                    okValue: "保存",
                    cancelValue: "关闭",
                    ok: function () {
                        return false;
                    },
                    cancel: function () {
                        //关闭对话框
                        dialogObj.close();
                        return false;
                    }
                });
                dialogObj.showModal();
            });
        },

        deleteFunc: function (id) {
            require(["artDialog"], function () {
                artDialogUtil.confirm("确认删除该目标吗？", function () {
                    $.ajax({
                        url: App["contextPath"] + "/manage/okrObject/deleteObject.json?objectId=" + id,
                        dataType: "json",
                        success: function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                    pageObj.loadOKRObjects(pageObj.currentType, pageObj.currentTeamId);
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
        },

        deleteResultFunc: function (id) {
            require(["artDialog"], function () {
                artDialogUtil.confirm("确认删除该关键结果吗？", function () {
                    $.ajax({
                        url: App["contextPath"] + "/manage/okrResult/deleteResult.json?resultId=" + id,
                        dataType: "json",
                        success: function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                    pageObj.loadOKRObjects(pageObj.currentType, pageObj.currentTeamId);
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
        window.pageObj = pageObj;
        pageObj.loadOKRObjects('1', null);
        pageObj.editFlag = '1';
    });
});