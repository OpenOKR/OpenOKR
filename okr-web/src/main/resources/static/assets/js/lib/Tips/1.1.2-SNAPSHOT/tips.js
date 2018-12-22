//控件提示
(function () {
    isIE6 = !window.XMLHttpRequest;
    tips = function (options) {
        return new Tips(options);
    };
    Tips = function (options) {
        var defaults = {
            renderTo: 'body',
            type: 0,
            autoClose: true,
            removeOthers: true,
            time: undefined,
            top: 50,
            left: null,
            onClose: null,
            onShow: null
        };
        this.options = $.extend({}, defaults, options);
        this._init();

        !Tips._collection ? Tips._collection = [this] : Tips._collection.push(this);

    };

    Tips.TYPE = {
        SUCCESS: 0,
        ERROR: 1,
        WARN: 2
    };

    Tips.removeAll = function () {
        try {
            for (var i = Tips._collection.length - 1; i >= 0; i--) {
                Tips._collection[i].remove();
            }
        } catch (e) {
        }
    };

    Tips.prototype = {
        _init: function () {
            var self = this, opts = this.options, time;
            if (opts.removeOthers) {
                Tips.removeAll();
            }

            this._create();

            this.closeBtn.bind('click', function () {
                self.remove();
            });

            if (opts.autoClose) {
                time = opts.time || (opts.type == 1 || opts.type == 2 ? 6000 : 3000);
                window.setTimeout(function () {
                    self.remove();
                }, time);
            }

        },

        _create: function () {
            var opts = this.options;
            this.obj = $('<div class="ui-tips"><i></i><span class="close"></span></div>').append(opts.content);
            this.closeBtn = this.obj.find('.close');

            switch (opts.type) {
                case 0 :
                    this.obj.addClass('ui-tips-success');
                    break;
                case 1 :
                    this.obj.addClass('ui-tips-error');
                    break;
                case 2 :
                    this.obj.addClass('ui-tips-warning');
                    break;
                default :
                    this.obj.addClass('ui-tips-success');
                    break;
            }

            this.obj.appendTo('body').hide();
            this._setPos();
            if (opts.onShow) {
                opts.onShow();
            }

        },

        _setPos: function () {
            var self = this, opts = this.options;
            if (opts.width) {
                this.obj.css('width', opts.width);
            }
            var h = this.obj.outerHeight(), winH = $(window).height(), scrollTop = $(window).scrollTop();
            //var top = parseInt(opts.top) ? (parseInt(opts.top) + scrollTop) : (winH > h ? scrollTop+(winH - h)/2 : scrollTop);
            //var top = parseInt(opts.top) + scrollTop;TODO:不能随滚动条一直增加高度
            var top = parseInt(opts.top) ;
            this.obj.css({
                position: isIE6 ? 'absolute' : 'fixed',
                left : opts.left ? (opts.left + self.obj.outerWidth() / 2): '50%',
                top: top,
                zIndex: '9999',
                marginLeft: -self.obj.outerWidth() / 2
            });

            window.setTimeout(function () {
                self.obj.show().css({
                    marginLeft: -self.obj.outerWidth() / 2
                });
            }, 150);

            if (isIE6) {
                $(window).bind('resize scroll', function () {
                    var top = $(window).scrollTop() + parseInt(opts.top);
                    self.obj.css('top', top);
                });
            }
        },

        remove: function () {
            var opts = this.options;
            this.obj.fadeOut(200, function () {
                $(this).remove();
                if (opts.onClose) {
                    opts.onClose();
                }
            });
        }
    };
})();


var TipsUtil = TipsUtil || {

        /**
         * 提示 对话框
         */
        info: function (message, config) {
            var _config = config || {};
            tips($.extend({type: Tips.TYPE.SUCCESS, content: message, top: ($(window).height() * 0.01)}, _config));
        },

        /**
         * 错误提示 对话框
         */
        error: function (message, config) {
            var _config = config || {};
            tips($.extend({type: Tips.TYPE.ERROR, content: message, top: ($(window).height() * 0.01)}, _config));
        },

        /**
         * 警告提示 对话框
         */
        warn: function (message, config) {
            var _config = config || {};
            tips($.extend({type: Tips.TYPE.WARN, content: message, top: ($(window).height() * 0.01)}, _config));
        }

    };