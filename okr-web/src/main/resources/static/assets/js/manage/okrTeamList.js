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
                    var teamList =
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
                        '           <ul id="teamUsers-[%=team.id%]" data-edit="0" class="participant-list nicescroll participant-list">' +
                        '               [%if(!_.isNull(team.teamRelUsers) && team.teamRelUsers.length>0){%]' +
                        '                   [%_.each(team.teamRelUsers, function (user){%]' +
                        '                       <li class="part-item">' +
                        '                           <i data-id="[%=user.id%]" class="iconfont icon-close"></i>' +
                        '                           <span><img src="[%=user.photoUrl%]">头像</span>' +
                        '                           <strong>[%=user.realName%]</strong>' +
                        '                       </li>' +
                        '                   [%});%]' +
                        '               [%};%]' +
                        '               [%if(type === \'1\'){%]<li class="part-item"><a href="" id="teamUsersAdd-[%=team.id%]"><i class="iconfont icon-add"></i></a></li>[%};%]' +
                        '           </ul>' +
                        '       </div>' +
                        '   </div>' +
                        '</div>' +
                        '[%if(type === \'1\'){%]' +
                        '   <div class="col-sm-4 mb15">' +
                        '       <div class="meg-outer ui-addFile">' +
                        '           <div class="meg-in">' +
                        '               <i class="icon icon-add"></i>' +
                        '               <h4 class="meg-h4">新建团队</h4>' +
                        '           </div>' +
                        '       </div>' +
                        '   </div>' +
                        '[%};%]';
                    var header = UnderscoreUtil.getHtmlByText(teamList, {team: team, type: type});
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
            $('#teamUsers-' + id).removeClass('participant-list').addClass('operation');
            if ($('#teamUsersAdd-' + id).length > 0) {
                $('#teamUsersAdd-' + id).parent().hide();
            }
            $('#operation-' + id).append('<a name="save" class="btn btn-primary waves-effect waves-light" onclick="pageObj.saveTeamUsers(' + id + ')">保存</a>');
            $('#operation-' + id).append('<a name="cancel" class="btn btn-default waves-effect waves-light" onclick="pageObj.resetTeamUsers(' + id + ')">取消</a>');
            $('#teamUsers-' + id).find('.icon-close').click(function () {
                $(this).parent().hide();
            });
        },

        resetTeamUsers: function (id) {
            $("#operation-" + id).find('span').show();
            $('#operation-' + id).find("a").remove();
            $('#teamUsers-' + id).find('.icon-close').parent().show();
            $('#teamUsersAdd-' + id).parent().show();
            $('#teamUsers-' + id).removeClass('operation').addClass('participant-list');
        },

        saveTeamUsers: function (id) {
            var teamUsers = [];
            $.each($('#teamUsers-' + id).find('.icon-close'), function (idx, obj) {
                if ($(obj).is(':visible')) {
                    teamUsers.push($(obj).data('id'));
                }
            });
            //保存数据，删除hide的li元素
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