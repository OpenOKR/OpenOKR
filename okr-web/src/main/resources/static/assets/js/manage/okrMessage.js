var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        loadOKRMessage: function (loadPage, curr) {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#message").block();
                $.ajax({
                    url: App["contextPath"] + "/manage/okrMessage/getAllMessage.json",
                    type: "POST",
                    data: JSON.stringify({currentPage: curr, pageSize: 10, pageInfo: {currentPage: curr, pageSize: 10}}),
                    contentType: 'application/json;charset=utf-8'
                }).done(function (res) {
                    if (loadPage) {
                        require(["laypage"], function (laypage) {
                            laypage({cont: 'page', pages: res.totalPage, curr: $('#pageNum').val(),
                                first: '首页', last: '尾页', skin: '#1E9FFF',
                                jump: function(e, first){
                                    if (!first) {
                                        pageObj.loadOKRMessage(false, e.curr);
                                    }
                                }
                            });
                        });
                        if (res.totalRecord === 0) {
                            $('#page').hide();
                        }
                    }
                    res && _this.buildOKRMessage(res);
                }).always(function () {
                    $("#message").unblock();
                });
            });
        },

        buildOKRMessage: function (res) {
            require(["Underscore", "jQueryUtils", "AppUtils"], function () {
                var $messageList = $("#messageList"); $messageList.empty();
                $.each(res.records, function (idx, item) {
                    item.createTsStr = $.DateUtils.getDateTimeString(new Date(item.createTs));
                });
                var markList = enumUtil.getEnum("messageMarkList.json");
                var templateText =
                    '<ul class="new-list ui-other">' +
                    '   [%_.each(list, function(msg, idx){%]' +
                    '       <li id="[%=msg.id%]" data-isread="[%=msg.isRead%]">' +
                    '           <div class="new-item">' +
                    '               <i class="[%=markList[msg.mark - 1].cssClass%]"></i>' +
                    '               [%if(msg.type == 1 || msg.isProcessed == 1){%]' +
                    '                   <h4>[%=msg.title%]</h4>' +
                    '               [%}else if (msg.type == 2){%]' +
                    '                   <h4><a onclick="pageObj.objectAudit(\'[%=msg.id%]\')">[%=msg.title%]</a></h4>' +
                    '               [%}else if (msg.type == 3){%]' +
                    '                   <h4><a onclick="pageObj.resultAudit(\'[%=msg.id%]\')">[%=msg.title%]</a></h4>' +
                    '               [%}else {%]' +
                    '                   <h4><a onclick="top.mainObj.menuClick(null, App.contextPath + \'/manage/okrObject/okrDetail.htm?id=[%=msg.targetId%]\', \'menu-2\');">[%=msg.title%]</a></h4>' +
                    '               [%}%]' +
                    '               <p>[%=msg.createTsStr%]</p>' +
                    '               <div class="action">' +
                    '                   [%if(msg.isProcessed == 1 && msg.isRead == 1){%]' +
                    '                       <i class="iconfont icon-dot text-muted"></i>' +
                    '                   [%} else {%]' +
                    '                       <i class="iconfont icon-dot text-warning"></i>' +
                    '                   [%}%]' +
                    '                   <i class="iconfont icon-arrowR"></i>' +
                    '               </div>' +
                    '           </div>' +
                    '           <div class="new-other" style="display: none;">[%=msg.content%]</div>' +
                    '       </li>' +
                    '   [%});%]' +
                    '</ul>';
                var html = UnderscoreUtil.getHtmlByText(templateText, {list: res.records, markList: markList});
                $messageList.append(html);
                $(".ui-other .icon-arrowR").click(function(){
                    $(this).parents('li').siblings().find(".new-other").slideUp();
                    $(this).parents("li").toggleClass("active").find(".new-other").slideToggle();
                    pageObj.updateOKRMessage($(this), $(this).parents('li').attr('id'), $(this).parents('li').data('isread'));
                });
            });
        },

        updateOKRMessage: function ($this, id, isRead) {
            if (isRead === 0) {
                $("#" + id).block();
                $.ajax({
                    url: App["contextPath"] + "/manage/okrMessage/update.json",
                    type: "POST",
                    data: JSON.stringify({messageVO: {id: id, isRead: '1'}}),
                    contentType: 'application/json;charset=utf-8'
                }).done(function (res) {
                    if (res && res.success) {
                        $($this).parents('li').data('isread', 1);
                        $($this).parent('div').find('.text-warning').remove();
                        $($this).parent('div').prepend('<i class="iconfont icon-dot text-muted"></i>');
                    }
                }).always(function () {
                    $("#" + id).unblock();
                });
            }
        },

        objectAudit: function (id) {
            require(["artDialog", "jqForm", "zTree", "Tips"], function () {
                var _func = function (dialogObj) {
                    var $form = $(window.frames[dialogObj.id].window.pageObj.getForm()),
                        formData = $form.jqForm("getValue");
                    if (formData.radio === '0' && (formData.content === null || formData.content === undefined || formData.content === '')) {
                        TipsUtil.warn("不同意请填写驳回理由。");
                        return;
                    }
                    var _saveFunc = function () {
                        //保存
                        ajaxUtil.ajaxWithBlock({
                            url: App["contextPath"] + "/manage/okrObject/auditConfirm.json",
                            type: "post",
                            data: JSON.stringify({vo: formData}),
                            contentType: 'application/json;charset=utf-8' //设置请求头信息
                        }, function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                    dialogObj.close();
                                    pageObj.loadOKRMessage(true, 1);
                                } else {
                                    TipsUtil.warn(data.message);
                                }
                            });
                        });
                    };
                    //
                    _saveFunc();
                };
                var dialogObj = dialog({
                    url: App.contextPath + "/manage/okrObject/audit.htm?id=" + id,
                    title: '目标审核',
                    quickClose: false,
                    okValue: "确认",
                    cancelValue: "关闭",
                    fixed: true,
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

        resultAudit: function (id) {
            require(["artDialog", "jqForm", "zTree", "Tips"], function () {
                var _func = function (dialogObj) {
                    var $form = $(window.frames[dialogObj.id].window.pageObj.getForm()),
                        formData = $form.jqForm("getValue"),
                        checkedAll, checkedUsers = [];
                    //验证
                    if (formData.radio === '0' && (formData.content === null || formData.content === undefined || formData.content === '')) {
                        TipsUtil.warn("不同意请填写驳回理由。");
                        return;
                    }
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
                    var _saveFunc = function () {
                        //保存
                        ajaxUtil.ajaxWithBlock({
                            url: App["contextPath"] + "/manage/okrResult/auditConfirm.json",
                            type: "post",
                            data: JSON.stringify({resultVO: formData}),
                            contentType: 'application/json;charset=utf-8' //设置请求头信息
                        }, function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                    dialogObj.close();
                                    pageObj.loadOKRMessage(true, 1);
                                } else {
                                    TipsUtil.warn(data.message);
                                }
                            });
                        });
                    };
                    //
                    _saveFunc();
                };
                var dialogObj = dialog({
                    url: App.contextPath + "/manage/okrResult/audit.htm?id=" + id,
                    title: '协同审核',
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
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.loadOKRMessage(true, 1);
    });
});