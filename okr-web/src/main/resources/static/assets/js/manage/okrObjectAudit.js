var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        init: function () {
            require(["jqForm"], function () {
                pageObj.getForm();
            });
        },

        initEvent: function () {
            $("input[name='radio']").bind('click', function () {
                if ($(this).val() === '0') {
                    $('#contentLi').show();
                } else {
                    $('#contentLi').hide();
                }

            });
        },

        getForm: function () {
            return $("#objectAuditForm").jqForm({});
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.init();
        pageObj.initEvent();
    });
});