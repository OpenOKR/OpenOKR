var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        loadOKRMessage: function (loadPage, curr) {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#message").block();
                $.ajax({
                    url: App["contextPath"] + "/okrMessage/getAllMessage.json",
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
                    }
                    res && _this.buildOKRMessage(res);
                }).always(function () {
                    $("#message").unblock();
                });
            });
        },

        buildOKRMessage: function (res) {
            require(["Underscore", "jQueryUtils"], function () {
                var $messageList = $("#messageList"); $messageList.empty();
                $.each(res.records, function (idx, item) {
                    item.createTsStr = $.DateUtils.getDateTimeString(new Date(item.createTs));
                });
                var templateText =
                    '<ul class="new-list ui-other">' +
                    '   [%_.each(records, function(msg, idx){%]' +
                    '       <li id="[%=msg.id%]" data-isread="[%=msg.isRead%]">' +
                    '           <div class="new-item">' +
                    '               [%if(msg.mark == 1){%]' +
                    '                   <i class="iconfont icon-waring text-primary"></i>' +
                    '               [%} else if (msg.mark == 2) {%]' +
                    '                   <i class="iconfont icon-succ text-success"></i>' +
                    '               [%} else if (msg.mark == 3) {%]' +
                    '                   <i class="iconfont icon-waring text-warning"></i>' +
                    '               [%} else {%]' +
                    '                   <i class="iconfont icon-tip text-danger"></i>' +
                    '               [%}%]' +
                    '               <h4>[%=msg.title%]</h4>' +
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
                var html = UnderscoreUtil.getHtmlByText(templateText, res);
                $messageList.append(html);
                $(".ui-other .icon-arrowR").click(function(){
                    $(this).parents("li").toggleClass("active").find(".new-other").slideToggle();
                    pageObj.updateOKRMessage($(this), $(this).parents('li').attr('id'), $(this).parents('li').data('isread'));
                });
            });
        },

        updateOKRMessage: function ($this, id, isRead) {
            if (isRead === 0) {
                $("#" + id).block();
                $.ajax({
                    url: App["contextPath"] + "/okrMessage/update.json",
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
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.loadOKRMessage(true, 1);
    });
});