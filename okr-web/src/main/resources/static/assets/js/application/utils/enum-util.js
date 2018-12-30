/**
 * 枚举类 工具js
 * denpendence: [jQuery]
 */
var App = App || {}, enumUtil = {

    /**
     *
     * @param url
     * @returns {*}
     * @private
     */
    _ajax: function (url) {
        var retData = null;
        $.ajax({
            type: "POST",
            async: false,// 同步处理
            url: App["contextPath"] + "/application/enum/" + url,
            success: function (data) {
                retData = data;
            },
            error: function (xhr, ts, err) {
                throw  new Error("请求失败，" + err);
            }
        });
        return retData;
    },

    /** 深度拷备数据 */
    _copy: function (data) {
        return $.extend(true, $.isArray(data) ? [] : {}, data);
    },

    /**
     * 获得枚举数据
     * @param url
     * @returns {*}
     */
    getEnum: function (url) {
        // 如果是要进行ajax请求的数据
        if ($.trim(url) !== "") {
            //如果前端已经存在，不再请求
            if (!top.App.enumStore || top.App.enumStore === undefined) {
                top.App.enumStore = {};
            }
            // 缓存数据
            if (top.App.enumStore[url] === undefined) {
                top.App.enumStore[url] = enumUtil._ajax(url);
            }
        }
        return enumUtil._copy(top.App.enumStore[url]);
    }

};