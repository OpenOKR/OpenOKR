;
(function($){
    $.EventUtils = $.EventUtils || {};
    $.extend($.EventUtils, {
        /**
         * 事件对象
         * @param {Object} event
         */
        getEvent: function(event){
            if (!event) 
                event = window.event;
            return event;
        },
        /**
         * 键盘keycode
         * @param {Object} event
         */
        getKeyCodeByEvent: function(event){
            event = $.EventUtils.getEvent(event);
            if (event.keyCode) 
                return event.keyCode;
            else if (event.which) 
				return event.which;
        },
        /**
         * 停止事件冒泡
         * @param {Object} event
         */
        stopBubble: function(event){
//            // 如果传入了事件对象.那么就是非IE浏览器
//            if (event) {
//                // 因此它支持W3C的stopPropation()方法
//                event.stopPropagation();
//            }else {
//                // 否则,我们得使用IE的方式来取消事件冒泡
//                window.event.cancelBubble = true;
//            }
			
			event = event || window.event;
			
			if(event.stopPropagation){
				event.stopPropagation();
			}else{
				event.cancelBubble = true;
			}
        },
        /**
         * 根据事件获取目标dom对象
         * @param {Object} event
         */
        getTargetByEvent: function(event){
            event = $.EventUtils.getEvent(event);
			if(event instanceof jQuery.Event){ 
				return event.target||event.srcElement;
			}else if ($.browser.msie) {
                return event.srcElement;
            }else {
                return event.target;
            }
        },
        /**
         * 禁用 BackSpace返回上一页
         * @param {Object} enabled
         */
        disabledBackSpaceToPrePage: function(){
            if ($.browser.msie) {
                document.onkeydown = function(event){
                    var event = $.EventUtils.getEvent(event), keyCode = $.EventUtils.getKeyCodeByEvent(event), target = $.EventUtils.getTargetByEvent(event);
                    if ((keyCode == 8) &&
                    ((target.type != "text" &&
                    target.type != "textarea" &&
                    target.type != "password") ||
                    target.readOnly == true)) {
                        event.keyCode = 0;
                        event.returnValue = false;
                    }
                    return true;
                }
            }
            else {
                document.onkeypress = function(event){
                    var event = $.EventUtils.getEvent(event), keyCode = $.EventUtils.getKeyCodeByEvent(event), target = $.EventUtils.getTargetByEvent(event);
                    if ((keyCode == 8) &&
                    ((target.type != "text" &&
                    target.type != "textarea" &&
                    target.type != "password") ||
                    target.readOnly == true)) {
                        return false;
                    }
                    return true;
                }
            }
        }
    });
})(jQuery);
