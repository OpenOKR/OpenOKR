;
(function ($) {
    $.StringUtils = $.StringUtils || {};
    $.extend($.StringUtils, {
        //扩展代码
    });
})(jQuery);

(function ($) {
    $.DateUtils = $.DateUtils || {};
    $.extend($.DateUtils, {
        /**
         * 获取某一个月的最大天数
         * @param date
         * @returns {number}
         */
        maxDayOfDate: function(date){
            date.setDate(1);
            date.setMonth(date.getMonth() + 1);
            var time = date.getTime() - 24 * 60 * 60 * 1000;
            return new Date(time).getDate();
        },
        /**
         * 添加日期
         * @param date
         * @param item
         * @param n
         * @returns {Date}
         */
        add: function (date, item, n) {
            switch (item) {
                case 's' :
                    return new Date(date.getTime() + (1000 * n));
                case 'n' :
                    return new Date(date.getTime() + (60000 * n));
                case 'h' :
                    return new Date(date.getTime() + (3600000 * n));
                case 'd' :
                    return new Date(date.getTime() + (86400000 * n));
                case 'w' :
                    return new Date(date.getTime() + ((86400000 * 7) * n));
                case 'm' :
                    return new Date(date.getFullYear(), (date.getMonth()) + n, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
                case 'y' :
                    return new Date((date.getFullYear() + n), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
            }
        }
    });
})(jQuery);

(function ($) {
    $.ArrayUtils = $.ArrayUtils || {};
    $.extend($.ArrayUtils, {
        /**
         * 从数组中查找到ID对应的NAME(用于下拉框的翻译)
         * @param arr
         * @param idkey
         * @param namekey
         * @param idval
         */
        getNameFromArray: function(arr,idkey,namekey,idval) {
            var nameval = "";
            for(var i=0;i<arr.length;i++) {
                if(arr[i][idkey] == idval) {
                    nameval = arr[i][namekey];
                    break;
                }
            }
            return nameval;
        },
        /**
         * 移除数组元素
         * @param arr
         * @param e
         */
        removeElement: function(arr,e) {
            var newArr = [];
            for(var i=0;i<arr.length;i++) {
                if(arr[i] != e) {
                    newArr.push(arr[i]);
                }
            }
            return newArr;
        }
    });
})(jQuery);