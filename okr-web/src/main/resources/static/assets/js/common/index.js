var mainObj = mainObj || {};
require(["jQuery"], function () {
    $.extend(mainObj, {
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
                    e.stopPropagation();
                    $(this).find(".menu-list").slideDown("flow");
                },function(){
                    $(this).find(".menu-list").slideUp("flow");
                });
            });
        },

        initEvent: function () {
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
        }
    });

    $(window).ready(function () {
        //为了让菜单打开的 <iframe> 里的top.mainObj 指向当前的 mainObj
        window.mainObj = mainObj;
        if (!mainObj.flag) {
            mainObj.loadMenu(); //初始化菜单
            //mainObj.initMenuScroll();//初始化菜单滚动插件
            mainObj.initEvent();
        }
        window.onresize = function () {
            mainObj.changeFrameHeight();
        };
        mainObj.changeFrameHeight();
    });
});