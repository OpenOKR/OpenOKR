var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        options: {useEasing: true, useGrouping: true},

        loadOKRObjectDetail: function () {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#detail").block();
                $.ajax({
                    url: App["contextPath"] + "/manage/okrObject/getOkrDetail.json",
                    type: "POST",
                    data: JSON.stringify({searchVO: {objectId: pageObj.id, type: pageObj.type, userId: pageObj.userId}}),
                    contentType: 'application/json;charset=utf-8'
                }).done(function (res) {
                    pageObj.objectOwnerId = res.ownerId;
                    res && _this.buildOKRObjectDetail(res.info);
                }).always(function () {
                    $("#detail").unblock();
                });
            });
        },

        buildOKRObjectDetail: function (object) {
            require(["Underscore", "jQueryUtils", "AppUtils"], function () {
                var $okrObjectDetail = $("#detail"); $okrObjectDetail.empty();
                var $okrObjectHistory = $('#historyList'); $okrObjectHistory.empty();
                var statusList = enumUtil.getEnum("objectivesStatusList.json");
                var executeList = enumUtil.getEnum("executeStatusList.json");
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
                    '               <em class="em-start [%=statusList[object.status - 1].cssClass%]">[%=statusList[object.status - 1].name%]</em>' +
                    '               <a class="btn-del text-primary" onclick="pageObj.deleteFunc(\'[%=object.id%]\');"><i class="icon-del"></i>删除</a>' +
                    '               <a class="btn-del text-primary" onclick="pageObj.editObject(\'[%=object.id%]\');"><i class="icon-edit"></i>编辑</a>' +
                    '           </div>' +
                    '       </h3>' +
                    '       <p>[%=object.description%]</p>' +
                    '   </div>' +
                    '   <div class="desc">' +
                    '       <span class="txt-item text-muted">把握度：</span>' +
                    '       <span id="confidenceLevel" class="txt-item" data-value="[%=object.confidenceLevel%]">把握</span>' +
                    '   </div>' +
                    '</div>';
                var okrCon = '<div class="okr-con">' +
                    '   <ul class="okr-list">' +
                    '       [%if(!_.isNull(object.resultsExtList) && object.resultsExtList.length>0){%]' +
                    '           [%_.each(object.resultsExtList, function(item, idx){%]' +
                    '               <li onmouseover="$(this).siblings().find(\'.participant\').hide(\'slow\');$(this).find(\'.participant\').show(\'slow\');">' +
                    '                   <div class="okr-list-in">' +
                    '                       <h4>' +
                    '                           K[%=idx+1%]：[%=item.name%]' +
                    '                           <div class="action">' +
                    '                               <span class="txt-all [%=executeList[item.status].cssClass%]">' +
                    '                                   <i class="iconfont icon-dot"></i>[%=executeList[item.status].name%]' +
                    '                               </span>' +
                    '                           </div>' +
                    '                       </h4>' +
                    '                       <div class="participant">' +
                    '                           <span class="name">参与人员：</span>' +
                    '                           <ul class="participant-list">' +
                    '                               [%if(!_.isNull(item.joinUsers) && item.joinUsers.length>0){%]' +
                    '                                   [%_.each(item.joinUsers, function(user){%]' +
                    '                                       [%if(!_.isNull(user)){%]' +
                    '                                           <li class="part-item"><span><img src="/assets/images/temp/pic.png" title="[%=user.realName%]" alt="[%=user.realName%]"></span></li>' +
                    '                                       [%}%]' +
                    '                                   [%});%]' +
                    '                                   <li class="part-item"><a href="javascript:void(0);"><i class="iconfont icon-more"></i></a></li>' +
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
                    '                           <a class="btn-del text-primary" onclick="pageObj.deleteResultFunc(\'[%=item.id%]\')"><i class="icon-del"></i>删除</a>' +
                    '                           [%if(object.status == 3){%]' +
                    '                               <a class="btn-other text-primary" onclick="pageObj.addCheckin(\'[%=item.id%]\', \'[%=object.id%]\')"><i class="icon-refresh"></i>进度</a>' +
                    '                           [%}%]' +
                    '                           <a class="btn-del text-primary" onclick="pageObj.editResult(\'[%=object.id%]\', \'[%=item.id%]\')"><i class="icon-edit"></i>编辑</a>' +
                    '                           <a></a>' +
                    '                       </div>' +
                    '                   </div>' +
                    '               </li>' +
                    '           [%});%]' +
                    '       [%}%]' +
                    '   </ul>' +
                    '   <div class="okr-ohter">' +
                    '       <a onclick="pageObj.editResult(\'[%=object.id%]\', \'\');" class="txt-all text-primary"><i class="iconfont icon-add"></i>新增KR</a>' +
                    '   </div>' +
                    '</div>' +
                    '<div class="card-footer">' +
                    '   <span class="mes-area waring">' +
                    '       <i class="iconfont icon-waring"></i>' +
                    '       对内容进行重新编辑，需要再次提交确认！' +
                    '   </span>' +
                    '   [%if(object.status != 2 && object.status != 3){%]' +
                    '       <div class="action">' +
                    '           <a onclick="pageObj.auditSubmit(\'[%=object.id%]\');" class="btn btn-primary waves-effect waves-light">提交确认</a>' +
                    '       </div>' +
                    '   [%}%]' +
                    '</div>';
                var header = UnderscoreUtil.getHtmlByText(okrHeader, {object: object, statusList: statusList});
                $okrObjectDetail.append(header);
                $('#confidenceLevel').html(pageObj.sectionToChinese($('#confidenceLevel').data('value')) + $('#confidenceLevel').html());

                var con = UnderscoreUtil.getHtmlByText(okrCon, {object: object, executeList: executeList});
                $okrObjectDetail.append(con);

                pageObj.showHideOperationButton();

                pageObj.pieEchartsFunc(object.id, object.progress);
                var currentDateStr = $.DateUtils.getDateString(new Date());
                $.each(object.operateRecordList, function (idx, item) {
                    if (currentDateStr === $.DateUtils.getDateString(new Date(item.createTs))) {
                        item.createTsStr = $.DateUtils.getFormatDateString(new Date(item.createTs), "HH:mm:ss");
                        item.cssClass = "past";
                    } else {
                        item.createTsStr = $.DateUtils.getDateTimeString(new Date(item.createTs));
                    }
                    if (idx === 0) {
                        item.cssClass = "active";
                    }
                    var okrHistory = '<div class="area-process-li [%=cssClass%]">' +
                        '   <em class="area-process-em"></em>' +
                        '   <p class="area-process-date">[%=createTsStr%]</p>' +
                        '   <div class="area-process-con">' +
                        '       <h4>[%=message%]</h4>' +
                        '   </div>' +
                        '</div>';
                    var history = UnderscoreUtil.getHtmlByText(okrHistory, item);
                    $okrObjectHistory.append(history);
                });
            });
        },

        pieEchartsFunc: function (id, progress) {
            require(["echarts"], function (echarts){
                var color = '#f57677';
                if (progress >= 40 && progress < 80) {
                    color = '#fdb44d';
                } else if (progress >= 80) {
                    color = '#83affc';
                }
                var option = {
                    title: {text: '', x: 'center', y:"80%", textStyle: {fontSize: '12px', color:"#545454"}},
                    tooltip: {trigger: 'item', formatter: "{b} {d}%"},
                    legend: {orient: 'vertical', x: 'left', y:"-20%", data:['已完成','']},
                    series: [
                        {name:'', type:'pie', radius: ['60%', '75%'], avoidLabelOverlap: false, color:[color,'#dedede'],
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

        deleteFunc: function (id) {
            require(["artDialog"], function () {
                artDialogUtil.confirm("确认删除该Object吗？", function () {
                    $.ajax({
                        url: App["contextPath"] + "/manage/okrObject/deleteObject.json?objectId=" + id,
                        dataType: "json",
                        success: function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
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

        editObject: function (id) {
            require(["artDialog", "jqForm", "AutoTree"], function () {
                var _func = function (dialogObj) {
                    var $form = $(window.frames[dialogObj.id].window.pageObj.getForm()),
                        formData = $form.jqForm("getValue"),
                        checkedTeams, checkedLabels;
                    //验证
                    var validateMsgObj = validateUtil.validateDatas(formData, $(window.frames[dialogObj.id].window.pageObj.validateRule())[0]);
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
                    checkedTeams = $(window.frames[dialogObj.id].window.pageObj.getTeamsTree()).AutoTree("getCheckedNodes");
                    formData.relTeams = checkedTeams;
                    //赋值 labels
                    checkedLabels = $(window.frames[dialogObj.id].window.pageObj.getLabelsTree()).AutoTree("getCheckedNodes");
                    formData.relLabels = checkedLabels;
                    //
                    //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
                    require(["Tips"], function () {
                        var _saveFunc = function () {
                            //保存
                            ajaxUtil.ajaxWithBlock({
                                url: App["contextPath"] + "/manage/okrObject/saveObject.json",
                                type: "post",
                                data: JSON.stringify({objectVO: formData}),
                                contentType: 'application/json;charset=utf-8' //设置请求头信息
                            }, function (data) {
                                require(["Tips"], function () {
                                    if (data.success) {
                                        TipsUtil.info(data.message);
                                        dialogObj.close();
                                        pageObj.loadOKRObjectDetail();
                                    } else {
                                        TipsUtil.warn(data.message);
                                    }
                                });
                            });
                        };
                        //
                        _saveFunc();
                    });
                };
                var dialogObj = dialog({
                    url: App["contextPath"] + "/manage/okrObject/okrObjectForm.htm?objectId=" + id + "&type=" + pageObj.currentType,
                    title: '新增/编辑目标',
                    quickClose: false,
                    okValue: "保存",
                    cancelValue: "关闭",
                    ok: function () {
                        _func(dialogObj);
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

        editResult: function (objectId, resultId) {
            require(["artDialog", "jqForm", "AutoTree"], function () {
                var _func = function (dialogObj) {
                    var $form = $(window.frames[dialogObj.id].window.pageObj.getForm()),
                        formData = $form.jqForm("getValue"),
                        checkedAll, checkedUsers = [];
                    //验证
                    var validateMsgObj = validateUtil.validateDatas(formData, $(window.frames[dialogObj.id].window.pageObj.validateRule())[0]);
                    if (!$.isEmptyObject(validateMsgObj)) {
                        require(["Tips"], function () {
                            //提示 拼接的验证信息
                            TipsUtil.warn(validateUtil.concatValidateMsg(validateMsgObj));
                            //焦点定位到 第一个 验证不通过的控件
                            $form.jqForm("focusToElement", validateUtil.getFirstNoPassName(validateMsgObj));
                        });
                        return;
                    }
                    //赋值 users
                    checkedAll = $(window.frames[dialogObj.id].window.pageObj.getUsersTree().getCheckedNodes());
                    $.each(checkedAll, function (idx, item) {
                        checkedUsers.push(item);
                    });
                    formData.joinUsers = checkedUsers;
                    //
                    //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
                    require(["Tips"], function () {
                        var _saveFunc = function () {
                            //保存
                            ajaxUtil.ajaxWithBlock({
                                url: App["contextPath"] + "/manage/okrResult/saveResult.json",
                                type: "post",
                                data: JSON.stringify({resultVO: formData}),
                                contentType: 'application/json;charset=utf-8' //设置请求头信息
                            }, function (data) {
                                require(["Tips"], function () {
                                    if (data.success) {
                                        TipsUtil.info(data.message);
                                        dialogObj.close();
                                        pageObj.loadOKRObjectDetail();
                                    } else {
                                        TipsUtil.warn(data.message);
                                    }
                                });
                            });
                        };
                        //
                        _saveFunc();
                    });
                };
                var dialogObj = dialog({
                    url: App["contextPath"] + "/manage/okrResult/okrResultForm.htm?objectId=" + objectId + "&resultId=" + resultId,
                    title: '新增/编辑关键结果',
                    quickClose: false,
                    okValue: "保存",
                    cancelValue: "关闭",
                    ok: function () {
                        _func(dialogObj);
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
                                    pageObj.loadOKRObjectDetail();
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

        addCheckin: function (id, objectId) {
            require(["artDialog", "Tips"], function () {
                var _func = function (dialogObj) {
                    var resultId = $(window.frames[dialogObj.id].document).find("#resultId").val(), currentValue,
                        status = $(window.frames[dialogObj.id].document).find("input[name='metricUnit']:checked").val(),
                        description = $(window.frames[dialogObj.id].document).find("#description").val();
                    if ($(window.frames[dialogObj.id].document).find("#currentValue").length > 0) {
                        currentValue = $(window.frames[dialogObj.id].document).find("#currentValue").val();
                        var regexp = new RegExp(/^\d+(\.\d+)?$/);
                        if (!regexp.test(currentValue)) {
                            TipsUtil.warn("当前值不符合规则，只能输入数值！");
                            return;
                        }
                    } else {
                        currentValue = $(window.frames[dialogObj.id].document).find("input[name='currentValue']:checked").val();
                    }
                    ajaxUtil.ajaxWithBlock({
                        url: App["contextPath"] + "/manage/okrResult/saveCheckins.json",
                        type: "post",
                        data: JSON.stringify({checkinVO:{resultId: resultId, currentValue: currentValue, status: status, description: description}}),
                        contentType: 'application/json;charset=utf-8' //设置请求头信息
                    }, function (data) {
                        if (data.success) {
                            TipsUtil.info(data.message);
                            dialogObj.close();
                            pageObj.loadOKRObjectDetail();
                        } else {
                            TipsUtil.warn(data.message);
                        }
                    });
                };
                var dialogObj = dialog({
                    url: App["contextPath"] + "/manage/okrResult/checkinsForm.htm?resultId=" + id + "&objectId=" + objectId,
                    title: '每周更新关键结果',
                    quickClose: false,
                    okValue: "保存",
                    cancelValue: "关闭",
                    ok: function () {
                        _func(dialogObj);
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

        auditSubmit: function (id) {
            ajaxUtil.ajaxWithBlock({
                url: App["contextPath"] + "/manage/okrObject/auditSubmit.json",
                type: "post",
                data: JSON.stringify({id:id}),
                contentType: 'application/json;charset=utf-8' //设置请求头信息
            }, function (data) {
                require(["artDialog", "Tips"], function () {
                    if (data.success) {
                        TipsUtil.info(data.message);
                        pageObj.loadOKRObjectDetail();
                    } else {
                        TipsUtil.warn(data.message);
                    }
                });
            });
        },

        chnNumChar: ["零成","一成","二成","三成","四成","五成","六成","七成","八成","九成","十成"],
        sectionToChinese: function (section) {
            return pageObj.chnNumChar[section];
        },

        // 操作按钮显示隐藏处理
        showHideOperationButton: function () {
            switch (pageObj.type) {
                case '1':
                    if (pageObj.userId !== pageObj.objectOwnerId) {
                        $('.btn-del').hide(); $('.btn-other').hide();
                        $('.okr-ohter').hide();
                    }
                    break;
                case '2':
                    if (pageObj.editFlag !== '1') {
                        $('.btn-del').hide();
                    }
                    break;
                case '3':
                    if ($('#companyEditFlag').val() !== '1') {
                        $('.btn-del').hide();
                    }
                    break;
            }
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.loadOKRObjectDetail();
    });
});