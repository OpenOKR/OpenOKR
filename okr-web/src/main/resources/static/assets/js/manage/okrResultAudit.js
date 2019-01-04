var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        init: function () {
            $("input[name='radio']").bind('click', function () {
                if ($(this).val() === '0') {
                    $('#contentLi').show();
                    $("li[data-name='addResult']").hide();
                    $('#targetValue').parent().parent().hide();
                    $('#initialValue').parent().parent().hide();
                    $('input:radio[name=metricUnit]').attr('checked',false);
                } else {
                    $('#contentLi').hide();
                    $("li[data-name='addResult']").show();
                }
            });

            // 单选按钮绑定事件
            $("input[name='metricUnit']").bind("click", function () {
                if ($(this).val() === '1') {
                    $('#targetValue').parent().parent().hide();
                    $('#initialValue').parent().parent().hide();
                } else {
                    $('#targetValue').parent().parent().show();
                    $('#initialValue').parent().parent().show();
                }
            });
        },

        getForm: function () {
            return $("#resultAuditForm").jqForm({});
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.init();
    });
});