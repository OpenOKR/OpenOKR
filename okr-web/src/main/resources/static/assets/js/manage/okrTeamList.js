var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        loadOKRTeams: function (type) {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                var url = App["contextPath"] + "/manage/okrTeam/getCreatedTeam.json";
                if (type === '2') {
                    url = App["contextPath"] + "/manage/okrTeam/getTeamList.json";
                }
                $("#teamList").block();
                $.ajax({
                    url: url
                }).done(function (teamList) {
                    teamList && _this.buildOKRTeams(teamList, type);
                }).always(function () {
                    $("#teamList").unblock();
                });
            });
        },

        buildOKRTeams: function (teamList, type) {
            require(["Underscore", "jQueryUtils", "AppUtils"], function () {
                var $okrTeamList = $("#teamList"); $okrTeamList.empty();
                $.each(teamList, function (idx, team) {
                    team.createTsStr = $.DateUtils.getDateString(new Date(team.createTs));
                    var html =
                        '<div class="col-sm-4 mb15">' +
                        '   <div class="card-area4 has-shadow">' +
                        '       <div class="card-header ui-st1">' +
                        '           <em class="em-radio blue">[%=team.name%]</em>' +
                        '           <h4 class="ell">[%=team.name%]</h4>' +
                        '           <p class="ell">创建时间：[%=team.createTsStr%]</p>' +
                        '           [%if(type === \'1\'){%]' +
                        '               <div class="action" id="operation-[%=team.id%]">' +
                        '                   <span class="txt-all uir btn-more">' +
                        '                       编辑<i class="iconfont icon-arrowB"></i>' +
                        '                       <div class="dropdown-menu">' +
                        '                           <ul class="dropdown-content">' +
                        '                               <li onclick="pageObj.editTeam(\'[%=team.id%]\')">编辑</li>' +
                        '                               <li onclick="pageObj.deleteUser(\'[%=team.id%]\')">删减人员</li>' +
                        '                               <li onclick="pageObj.deleteFunc(\'[%=team.id%]\')">解散团队</li>' +
                        '                           </ul>' +
                        '                       </div>' +
                        '                   </span>' +
                        '               </div>' +
                        '           [%};%]' +
                        '       </div>' +
                        '       <div class="participant ui-l">' +
                        '           <span class="name">参与人员：</span>' +
                        '           <ul id="teamUsers-[%=team.id%]" data-edit="0" class="participant-list nicescroll">' +
                        '               [%if(!_.isNull(team.teamRelUsers) && team.teamRelUsers.length>0){%]' +
                        '                   [%_.each(team.teamRelUsers, function (user){%]' +
                        '                       <li class="part-item">' +
                        '                           [%if(user.id != team.ownerId){%]' +
                        '                               <i data-id="[%=user.id%]" class="iconfont icon-close"></i>' +
                        '                           [%};%]' +
                        '                           <span><img src="[%=user.photoUrl%]">头像</span>' +
                        '                           <strong>[%=user.realName%]</strong>' +
                        '                       </li>' +
                        '                   [%});%]' +
                        '               [%};%]' +
                        '               [%if(type === \'1\'){%]<li class="part-item"><a onclick="pageObj.editTeam(\'[%=team.id%]\')">' +
                        '                   <i class="iconfont icon-add"></i></a></li>[%};%]' +
                        '           </ul>' +
                        '       </div>' +
                        '   </div>' +
                        '</div>';
                    if (idx === (teamList.length -1)) {
                        html +=
                            '<div class="col-sm-4 mb15">' +
                            '   <div class="meg-outer ui-addFile" onclick="pageObj.editTeam(\'\');">' +
                            '       <div class="meg-in">' +
                            '           <i class="icon icon-add"></i>' +
                            '           <h4 class="meg-h4">新建团队</h4>' +
                            '       </div>' +
                            '   </div>' +
                            '</div>';
                    }
                    var header = UnderscoreUtil.getHtmlByText(html, {team: team, type: type});
                    $okrTeamList.append(header);
                });

            });
        },

        tabClick: function (dom, type) {
            $(dom).addClass('active').siblings().removeClass('active');
            pageObj.loadOKRTeams(type);
        },

        deleteUser: function (id) {
            $('#operation-' + id).find('span').hide();
            $('#teamUsers-' + id).addClass('operation');
            if ($('#teamUsersAdd-' + id).length > 0) {
                $('#teamUsersAdd-' + id).parent().hide();
            }
            $('#operation-' + id).append('<a name="save" class="btn btn-primary waves-effect waves-light" onclick="pageObj.saveTeamUsers(\'' + id + '\')">保存</a>');
            $('#operation-' + id).append('<a name="cancel" class="btn btn-default waves-effect waves-light" onclick="pageObj.resetTeamUsers(\'' + id + '\')">取消</a>');
            $('#teamUsers-' + id).find('.icon-close').click(function () {
                $(this).parent().hide();
            });
        },

        resetTeamUsers: function (id) {
            //相关节点隐藏，显示
            $("#operation-" + id).find('span').show();
            $('#operation-' + id).find("a").remove();
            $('#teamUsers-' + id).find('.icon-close').parent().show();
            $('#teamUsersAdd-' + id).parent().show();
            $('#teamUsers-' + id).removeClass('operation').addClass('participant-list');
        },

        saveTeamUsers: function (id) {
            var deleteTeamUsers = [], oldTeamUsers = [];
            $.each($('#teamUsers-' + id).find('.icon-close'), function (idx, obj) {
                oldTeamUsers.push($(obj).data('id'));
                if ($(obj).is(':hidden')) {
                    deleteTeamUsers.push($(obj).data('id'));
                }
            });

            //保存数据，删除hide的li元素，保存成功需要删除对应li；失败不进行操作，等待用户取消或重新保存
            if (deleteTeamUsers.length > 0) {
                $.ajax({
                    type: "post",
                    url: App["contextPath"] + "/manage/okrTeam/deleteTeamUser.json",
                    data: JSON.stringify({
                        teamId: id, userIdList: deleteTeamUsers
                    }),//将对象序列化成JSON字符串
                    dataType: "json",
                    contentType: 'application/json;charset=utf-8', //设置请求头信息
                    success: function (data) {
                        if (data.success) {
                            //相关节点隐藏，显示
                            $("#operation-" + id).find('span').show();
                            $('#operation-' + id).find("a").remove();
                            $('#teamUsersAdd-' + id).parent().show();
                            $('#teamUsers-' + id).removeClass('operation').addClass('participant-list');
                            TipsUtil.info(data.message);
                        } else {
                            TipsUtil.error(data.message);
                        }
                    },
                    error: function (res) {
                        alert(JSON.stringify(res));
                    }
                });
            } else {
                TipsUtil.warn('未删除任意成员！！');
            }
        },

        editTeam: function (id) {
            require(["artDialog", "jqForm", "AutoTree"], function () {
                var _editTeamFunc = function (dialogObj) {
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
                    $(window.frames[dialogObj.id].window.pageObj.getUsersTree().showNodes($(window.frames[dialogObj.id].window.pageObj.hideNodes)));
                    checkedAll = $(window.frames[dialogObj.id].window.pageObj.getUsersTree().getCheckedNodes());
                    $.each(checkedAll, function (idx, item) {
                        if (item.type === '2') {
                            checkedUsers.push(item);
                        }
                    });
                    formData.teamRelUsers = checkedUsers;
                    //
                    //判断表单是否被修改( isDirty 是根据 setDefaultValue 设置的默认数据进行判断)
                    require(["Tips"], function () {
                        var _saveFunc = function () {
                            //保存
                            ajaxUtil.ajaxWithBlock({
                                url: App["contextPath"] + "/manage/okrTeam/saveTeams.json",
                                type: "post",
                                data: JSON.stringify({teamExtVO: formData}),
                                contentType: 'application/json;charset=utf-8' //设置请求头信息
                            }, function (data) {
                                require(["Tips"], function () {
                                    if (data.success) {
                                        TipsUtil.info(data.message);
                                        dialogObj.close();
                                        pageObj.loadOKRTeams('1');
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
                    url: App["contextPath"] + "/manage/okrTeam/teamForm.htm?id=" + id,
                    title: '新增/编辑团队',
                    quickClose: false,
                    okValue: "保存",
                    cancelValue: "关闭",
                    ok: function () {
                        _editTeamFunc(dialogObj);
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
                artDialogUtil.confirm("确认解散该团队吗？", function () {
                    $.ajax({
                        url: App["contextPath"] + "/manage/okrTeam/disbandTeam.json?teamId=" + id,
                        dataType: "json",
                        success: function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                    pageObj.loadOKRTeams('1');
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
        pageObj.loadOKRTeams('1');
    });
});