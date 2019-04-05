var mainObj = mainObj || {};
require(["jQuery"], function () {
    $.extend(mainObj, {

        init: function () {
            require(["AutoCombobox"], function (){
                mainObj.loadTimeSession();
            });
            mainObj.loadMenu(); //初始化菜单
        },

        /**
         * 获取当前时间段方法，供子页面使用
         * @returns {*|jQuery}
         */
        getCurrentTimeSession: function () {
            return {id: $('#timeSessionId').val(), isActivate: $('#timeSessionIsActivate').val()};
        },

        /**
         * 初始化时间段
         */
        loadTimeSession: function () {
            //渲染控件
            return $("#timeSessionName").AutoCombobox({
                async: {
                    url: App["contextPath"] + "/manage/okrTimeSessions//getTimeSessionList.json",
                    dataSourceType: "onceRemote"
                },
                view: {
                    singleColumnNotHead: true,
                    widthRefer: function () {
                        return $(this).width() + 16;//引用当前自己输入框
                    },
                    colModels: [
                        {name: "id", label: "id", isHide: true},
                        {name: "name", label: "时间段"},
                        {name: "isActivate", label: "是否激活", isHide: true}
                    ],
                    bindFill: {"#timeSessionName": "name", "#timeSessionId": "id", "#timeSessionIsActivate": "isActivate"}
                },
                callback: {
                    afterSelectRow: function (rowData) {
                        require(["jQueryBlockUI"], function () {
                            $('#mainContent').attr('src', $('#mainContent').attr('src'));
                        });
                    }
                }
            });
        },

        /**
         * 加载菜单数据，成功后渲染菜单html结构
         */
        loadMenu: function () {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#menuUL").block();
                $.ajax({
                    url: App["contextPath"] + "/sys/menu/findTreeOfView.json",
                    type: "GET",
                    dataType: "json"
                }).done(function (res) {
                    res && _this.buildMenu(res);
                }).always(function () {
                    $("#menuUL").unblock();
                });
            });
        },

        /**
         * 构建菜单
         */
        buildMenu: function (rootMenu) {
            require(["Underscore"], function () {
                var $menuItem = $("#menuUL"),
                    templateText =
                        '[%if(children.length>0){%]' +
                        '    <li id="menu-[%=id%]">' +
                        '        <a>[%=name%]</a>' +
                        '        <div class="menu-list">' +
                        '           <div class="menu-content clearfix">' +
                        '               <dl>' +
                        '                   [%_.each(children, function(item){%]' +
                        '                   <dd>' +
                        '                       <a onclick="mainObj.menuClick(this, \'[%=item.url%]\');"><span class="mtitle">[%=item.name%]</span></a>' +
                        '                   </dd>' +
                        '                   [%});%]' +
                        '               </dl>' +
                        '           </div>' +
                        '        </div>' +
                        '    </li>' +
                        '[%}else{%]' +
                        '    <li id="menu-[%=id%]"><a onclick="mainObj.menuClick(this, \'[%=url%]\');">[%=name%]</a></li>' +
                        '[%}%]';
                $.each(rootMenu.children, function (idx, obj) {
                    var html = UnderscoreUtil.getHtmlByText(templateText, obj);
                    $menuItem.append(html);
                });
                $(".hearder-menu li").hover(function(e){
                    $(this).find(".menu-list").finish();
                    $(this).find(".menu-list").slideDown("flow");
                },function(){
                    $(this).find(".menu-list").slideUp("flow");
                });
            });
        },

        initEvent: function () {
            $("#timeSessionIcon").click(function (){
                mainObj.loadTimeSession().AutoCombobox('triggerAction');
            });
        },

        menuClick: function (dom, url, id) {
            var $iframe = $('#mainContent');
            $(dom).parents('li').addClass('active').siblings().removeClass('active');
            $iframe.attr('src', url);
            mainObj.changeFrameHeight();
            if ($("#" + id).length > 0) {
                $("#" + id).addClass('active').siblings().removeClass('active');
            }
        },

        changeFrameHeight: function () {
            var $iframe = $('#mainContent');
            $iframe.height(document.documentElement.clientHeight - 56);
        },

        editPassword: function () {
            require(["artDialog", "Tips", "Underscore", "RSA"], function () {
                var template = $("#editPassword").html();
                var _func = function (oldPassword, newPassword, confirmPassword) {
                    // 保存
                    ajaxUtil.ajaxWithBlock({
                        url: App["contextPath"] + "/sys/user/editPassword.json",
                        type: "post",
                        data: JSON.stringify({oldPassword: oldPassword, newPassword: newPassword, confirmPassword: confirmPassword}),
                        contentType: 'application/json;charset=utf-8' //设置请求头信息
                    }, function (data) {
                        require(["Tips"], function () {
                            if (data.success) {
                                TipsUtil.info(data.message);
                                dialogObj.remove();
                            } else {
                                TipsUtil.warn(data.message);
                            }
                        });
                    });
                };
                var dialogObj = dialog({
                    title: '修改密码',
                    content: template,
                    quickClose: false,
                    okValue: '确定',
                    cancelValue: '取消',
                    ok: function () {
                        var oldPassword = $('#oldPassword').val(), newPassword = $('#newPassword').val(), confirmPassword = $('#confirmPassword').val();
                        if (oldPassword === '' || newPassword === '' || confirmPassword === '') {
                            TipsUtil.warn('请确保所有输入框完整');
                            return false;
                        }
                        if (newPassword !== confirmPassword) {
                            TipsUtil.warn('新密码和确认密码不一致，请检查');
                            return false;
                        }
                        _func(oldPassword, newPassword, confirmPassword);
                        return false;
                    },
                    cancel: function (){
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
        //为了让菜单打开的 <iframe> 里的top.mainObj 指向当前的 mainObj
        window.mainObj = mainObj;
        if (!mainObj.flag) {
            mainObj.init();
            //mainObj.initMenuScroll();//初始化菜单滚动插件
            mainObj.initEvent();
        }
        window.onresize = function () {
            mainObj.changeFrameHeight();
        };
        mainObj.changeFrameHeight();
    });
});