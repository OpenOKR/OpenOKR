$.extend(true, window.dialog.defaults, {
    // 设置遮罩透明度
    backdropOpacity: 0.1
});

var artDialogUtil = {

    /**
     * （模式）是否确认 对话框（确定与取消按钮）
     */
    confirm: function (message, okCallback, config, cancelCallback) {
        var config = config || {},
            dialogObj = dialog(
                $.extend({}, {
                    title: '提示',
                    content: message,
                    okValue: '确定',
                    ok: function () {
                        return okCallback.call(this);
                    },
                    cancelValue: '取消',
                    cancel: function () {
                        if (cancelCallback) {
                            return cancelCallback.call(this);
                        }
                    }
                }, config));
        dialogObj.showModal();
    },

    /**
     * 有确定按钮的提示对话框
     */
    showMessage: function (message, config) {
        var config = config || {},
            dialogObj = dialog(
                $.extend({}, {
                    title: '提示',
                    content: message,
                    okValue: '确定',
                    ok: function () {
                        return true;
                    }
                }, config));
        dialogObj.show();
    },

    /**
     * （非模式）是否确认 对话框（确定与取消按钮）
     */
    confirmAndShow: function (message, okCallback, config) {
        var config = config || {},
            dialogObj = dialog(
                $.extend({}, {
                    title: '提示',
                    content: message,
                    okValue: '确定',
                    ok: function () {
                        return okCallback.call(this);
                    },
                    cancelValue: '取消',
                    cancel: function () {
                    }
                }, config));
        dialogObj.show();
    },


    /**
     *  自定义按钮弹窗
     */
    customConfirm: function (message, okCallback, config, cancelCallback) {
        var config = config || {},
            dialogObj = dialog(
                $.extend({}, {
                    title: '提示',
                    content: message
                }, config));
        dialogObj.showModal();
    }
};