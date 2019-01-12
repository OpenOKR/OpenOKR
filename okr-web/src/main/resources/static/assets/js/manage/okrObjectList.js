var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        options: {useEasing: true, useGrouping: true},
        currentType: '',
        currentTeamId: '',
        editFlag: '',

        init: function () {
            pageObj.loadOKRObjects('1', null);
            pageObj.editFlag = '1';
            require(["AutoCombobox"], function () {
                pageObj.getStatusCombo();
                pageObj.getUserCombo();
            });
        },

        getStatusCombo: function () {
            //渲染控件
            return $("#statusName").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/application/enum/objectivesStatusList.json",
                    dataSourceType: "onceRemote"
                },
                view: {
                    singleColumnNotHead: true,
                    widthRefer: function () {
                        return $(this).parent().width();//引用当前自己输入框
                    },
                    colModels: [
                        {name: "code", label: "code", isHide: true},
                        {name: "name", label: "状态"}
                    ],
                    bindFill: {"#statusName": "name", "#status": "code"}
                }
            });
        },

        getUserCombo: function () {
            //渲染控件
            return $("#realName").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/sys/user/findByPageLikeInputValue.json",
                    dataSourceType: "remote"
                },
                view: {
                    singleColumnNotHead: true,
                    widthRefer: function () {
                        return $(this).parent().width();//引用当前自己输入框
                    },
                    colModels: [
                        {name: "id", label: "id", isHide: true},
                        {name: "realName", label: "真实姓名"}
                    ],
                    bindFill: {"#userName": "realName", "#userId": "id"}
                },
                callback: {
                    setRequestData: function (requestData) {
                        requestData.inputValue = this.value;
                    },
                    afterSelectRow: function (rowData) {
                        $('#realName').val(rowData.realName);
                        $('#userId').val(rowData.id);
                    }
                }
            });
        },

        loadOKRObjects: function (type, teamId) {
            var _this = this, keyword = $('#keyword').val(), status = $('#status').val(), userId = $('#userId').val();
            require(["jQueryBlockUI"], function () {
                $("#message").block();
                $.ajax({
                    url: App["contextPath"] + "/manage/okrObject/getOkrListByType.json",
                    type: "POST",
                    data: JSON.stringify({searchVO: {timeSessionId: top.mainObj.getCurrentTimeSession().id,
                        type: type, teamId: teamId, keyword: keyword, executeStatus: status, userId: userId}}),
                    contentType: 'application/json;charset=utf-8'
                }).done(function (res) {
                    pageObj.currentType = type;
                    pageObj.currentTeamId = teamId;
                    res && _this.buildOKRObjects(res);
                    res && _this.showHideSearchDom();
                }).always(function () {
                    $("#message").unblock();
                });
            });
        },

        showHideSearchDom: function () {
            if (pageObj.currentType === '1') {
                $('#realNameLabel').show();
                $('#realNameDiv').show();
            } else {
                $('#realNameLabel').hide();
                $('#realNameDiv').hide();
            }
        },

        buildOKRObjects: function (res) {
            require(["Underscore", "jQueryUtils", "AppUtils"], function () {
                var $okrObjectList = $("#okrObjectList"); $okrObjectList.empty();
                var statusList = enumUtil.getEnum("objectivesStatusList.json");
                var executeList = enumUtil.getEnum("executeStatusList.json");
                $.each(res.info, function (idx, object) {
                    if (idx !== 0) {
                        object.cssClass = "mt20";
                    }
                    object.href = App.contextPath + "/manage/okrObject/okrDetail.htm?id=" + object.id + "&type=" + object.type + "&userId=" + object.ownerId;
                    var okrBody =
                        '<div class="card-area2 [%=object.cssClass%]">' +
                        '   <div class="okr-header">' +
                        '       <div class="area-charts">' +
                        '           <div id="[%=object.id%]" style="width: 100%;height: 100%;"></div>' +
                        '           <div class="charts-total">' +
                        '               <span class="num" data-end="[%=object.progress%]" data-new="[%=object.progress%]">[%=object.progress%]</span>' +
                        '               <em>%</em>' +
                        '           </div>' +
                        '       </div>' +
                        '       <div class="okr-header-desc">' +
                        '           <h3 class="name">' +
                        '               [%=object.name%]' +
                        '               <div class="action">' +
                        '                   <em class="em-start [%=statusList[object.status - 1].cssClass%]">[%=statusList[object.status - 1].name%]</em>' +
                        '                   <a id="object-del-[%=object.id%]" class="btn-del text-primary" onclick="pageObj.deleteFunc(\'[%=object.id%]\');"><i class="icon-del"></i>删除</a>' +
                        '                   <a id="object-edit-[%=object.id%]" class="btn-del text-primary" onclick="pageObj.editObject(\'[%=object.id%]\');"><i class="icon-edit"></i>编辑</a>' +
                        '               </div>' +
                        '           </h3>' +
                        '           <p>[%=object.description%]</p>' +
                        '       </div>' +
                        '       <div class="desc"><a id="object-detail-[%=object.id%]" class="btn btn-primary waves-effect waves-light" onclick="top.mainObj.menuClick(null, \'[%=object.href%]\')">查看详情</a></div>' +
                        '   </div>' +
                        '   <div class="okr-con">' +
                        '       <ul class="okr-list">' +
                        '           [%if(!_.isNull(object.resultsExtList) && object.resultsExtList.length>0){%]' +
                        '               [%_.each(object.resultsExtList, function(item, idx){%]' +
                        '                   <li onmouseover="$(this).siblings().find(\'.participant\').hide(\'slow\');$(this).find(\'.participant\').show(\'slow\');">' +
                        '                       <div class="okr-list-in">' +
                        '                           <h4>' +
                        '                               K[%=idx+1%]：[%=item.name%]' +
                        '                               <div class="action">' +
                        '                                   <span class="txt-all [%=executeList[item.status].cssClass%]">' +
                        '                                       <i class="iconfont icon-dot"></i>[%=executeList[item.status].name%]' +
                        '                                   </span>' +
                        '                               </div>' +
                        '                           </h4>' +
                        '                           <div class="participant">' +
                        '                               <span class="name">参与人员：</span>' +
                        '                               <ul class="participant-list">' +
                        '                                   [%if(!_.isNull(item.joinUsers) && item.joinUsers.length>0){%]' +
                        '                                       [%_.each(item.joinUsers, function(user){%]' +
                        '                                           [%if(!_.isNull(user)){%]' +
                        '                                           [%if(user.realName.length>2)%]' +
                        '                                           <li class="part-item"><span class="image">[%=user.realName.substr(1,2)%]</span></li> [%else%]<li class="part-item"><span class="image">[%=user.realName%]</span></li>' +
                        '                                           [%;%]' +
                        '                                           [%}%]' +
                        '                                       [%});%]' +
                        '                                   <li class="part-item"><a href="javascript:void(0);"><i class="iconfont icon-more"></i></li></a>' +
                        '                                   [%}%]' +
                        '                               </ul>' +
                        '                           </div>' +
                        '                       </div>' +
                        '                       <div class="okr-list-tab">' +
                        '                           <p class="scroll-bar">' +
                        '                               <i class="complete"></i>' +
                        '                               <i class="new"></i>' +
                        '                               <span class="vals">' +
                        '                                   <em class="num" data-end="[%=item.preProgress%]" data-new="[%=item.progress%]">[%=item.progress%]</em>%' +
                        '                               </span>' +
                        '                           </p>' +
                        '                           <div class="action">' +
                        '                               <a id="result-del-[%=item.id%]" class="btn-del text-primary" onclick="pageObj.deleteResultFunc(\'[%=item.id%]\')"><i class="icon-del"></i>删除</a>' +
                        '                               [%if(object.status == 3){%]' +
                        '                                   <a id="result-checkin-[%item.id%]" class="btn-other text-primary" onclick="pageObj.addCheckin(\'[%=item.id%]\', \'[%=object.id%]\')"><i class="icon-refresh"></i>进度</a>' +
                        '                               [%}%]' +
                        '                               <a id="result-edit-[%item.id%]" class="btn-del text-primary" onclick="pageObj.editResult(\'[%=item.id%]\', \'[%=object.id%]\')"><i class="icon-edit"></i>编辑</a>' +
                        '                           </div>' +
                        '                       </div>' +
                        '                   </li>' +
                        '               [%});%]' +
                        '           [%}%]' +
                        '       </ul>' +
                        '   </div>' +
                        '</div>';
                    var okrList = UnderscoreUtil.getHtmlByText(okrBody, {object: object, statusList: statusList, executeList: executeList});
                    $okrObjectList.append(okrList);

                    pageObj.showHideOperationButton();
                    pageObj.pieEchartsFunc(object.id, object.progress);
                });
            });
        },

        // 操作按钮显示隐藏处理
        showHideOperationButton: function () {
            switch (pageObj.currentType) {
                case '1':
                    var searchUserId = $('#userId').val();
                    if (searchUserId !== null && searchUserId !== '') {
                        if (pageObj.currentUserId !== searchUserId) {
                            $('#addObject').hide();
                            $('.btn-del').hide(); $('.btn-other').hide();
                        }
                    }
                    break;
                case '2':
                    if (pageObj.editFlag !== 1) {
                        $('#addObject').hide();
                        $('.btn-del').hide(); $('.btn-other').hide();
                    }
                    break;
                case '3':
                    if ($('#companyEditFlag').val() !== 1) {
                        $('#addObject').hide();
                        $('.btn-del').hide(); $('.btn-other').hide();
                    }
                    break;
            }
            if (top.mainObj.getCurrentTimeSession().isActivate === '0') {
                $('#editObject').hide();
                $('a.btn-del').hide();
            }
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

        tabClick: function (dom, type, teamId) {
            if (type === '2') {
                pageObj.editFlag = $(dom).data('edit');
            }
            $(dom).addClass('active').siblings().removeClass('active');
            $('#keyword').val(''); $('#statusName').val(''); $('#status').val('');
            pageObj.loadOKRObjects(type, teamId);
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
                                        pageObj.loadOKRObjects(pageObj.currentType, pageObj.currentTeamId);
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
                    url: App["contextPath"] + "/manage/okrObject/okrObjectForm.htm?objectId=" + id +
                        "&type=" + pageObj.currentType + "&teamId=" + pageObj.currentTeamId,
                    title: '新增/编辑目标',
                    height: 440,
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

        editResult: function (id, objectId) {
            require(["artDialog", "jqForm", "zTree"], function () {
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
                                        pageObj.loadOKRObjects(pageObj.currentType, pageObj.currentTeamId);
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
                    url: App["contextPath"] + "/manage/okrResult/okrResultForm.htm?resultId=" + id + "&objectId=" + objectId,
                    title: '新增/编辑关键结果',
                    height: 400,
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
                            pageObj.loadOKRObjects(pageObj.currentType, pageObj.currentTeamId);
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
        pageObj.init();
    });
});