;
(function ($) {
    $.ObjectUtils = $.ObjectUtils || {};
    $.extend($.ObjectUtils, {
        /**
         * 删除值为空的属性值，并返回值非空的对象
         * @param obj
         * @returns {*}
         */
        deleteEmptyProps: function(obj) {
            var result = {}, key, propValue, type;
            if(obj == null) {
                return null;
            }
            if($.isPlainObject(obj)){
                for(key in obj) {
                    propValue = obj[key];
                    type = $.type(propValue);
                    if(type === "string" && $.trim(propValue) !== "") {
                        result[key] = propValue;
                    }else if(type === "object" && !$.isEmptyObject(propValue)) {
                        result[key] = propValue;
                    }else if(type === "array" && propValue.length > 0) {
                        result[key] = propValue;
                    }else if(propValue != null) {
                        result[key] = propValue;
                    }
                }
            }
            return $.extend(true, {}, result);
        },

        /**
         * Fill in null properties in object with the first value present in the following list of defaults objects.
         * e.g. var iceCream = {flavor: "chocolate", price: null};
         *      $.objectUtil.defaults(iceCream, {flavor: "vanilla", sprinkles: "lots", price: 10});
         *      => Object {flavor: "chocolate", price: 10, sprinkles: "lots"}
         * @param obj
         * @returns {*}
         */
        defaults : function(obj) {
            $.each(Array.prototype.slice.call(arguments, 1), function(key, source) {
                if (source) {
                    for (var prop in source) {
                        if (obj[prop] == null) obj[prop] = source[prop];
                    }
                }
            });
            return obj;
        }
    });
})(jQuery);