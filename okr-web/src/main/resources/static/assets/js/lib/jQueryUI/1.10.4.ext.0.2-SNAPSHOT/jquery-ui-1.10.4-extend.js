//=====================================
// timepicker
(function ($) {
    function Timepicker() {
        this.regional = [],
            this.regional[""] = {
                currentText : "当前时间",
                closeText : "确定",
                ampm : !1,
                amNames : ["AM", "A"],
                pmNames : ["PM", "P"],
                timeFormat : "hh:mm tt",
                timeSuffix : "",
                timeOnlyTitle : "选择时间",
                timeText : "时间",
                hourText : "时",
                minuteText : "分",
                secondText : "秒",
                millisecText : "毫秒",
                timezoneText : "Time Zone"
            },
            this._defaults = {
                showButtonPanel : !0,
                timeOnly : !1,
                showHour : !0,
                showMinute : !0,
                showSecond : !1,
                showMillisec : !1,
                showTimezone : !1,
                showTime : !0,
                stepHour : 1,
                stepMinute : 1,
                stepSecond : 1,
                stepMillisec : 1,
                hour : 0,
                minute : 0,
                second : 0,
                millisec : 0,
                timezone : "+0000",
                hourMin : 0,
                minuteMin : 0,
                secondMin : 0,
                millisecMin : 0,
                hourMax : 23,
                minuteMax : 59,
                secondMax : 59,
                millisecMax : 999,
                minDateTime : null,
                maxDateTime : null,
                onSelect : null,
                hourGrid : 0,
                minuteGrid : 0,
                secondGrid : 0,
                millisecGrid : 0,
                alwaysSetTime : !0,
                separator : " ",
                altFieldTimeOnly : !0,
                showTimepicker : !0,
                timezoneIso8609 : !1,
                timezoneList : null,
                addSliderAccess : !1,
                sliderAccessArgs : null
            },
            $.extend(this._defaults, this.regional[""])
    }
    function extendRemove(a, b) {
        $.extend(a, b);
        for (var c in b)
            (null === b[c] || void 0 === b[c]) && (a[c] = b[c]);
        return a
    }
    $.extend($.ui, {
        timepicker : {
            version : "0.9.9"
        }
    }),
        $.extend(Timepicker.prototype, {
            $input : null,
            $altInput : null,
            $timeObj : null,
            inst : null,
            hour_slider : null,
            minute_slider : null,
            second_slider : null,
            millisec_slider : null,
            timezone_select : null,
            hour : 0,
            minute : 0,
            second : 0,
            millisec : 0,
            timezone : "+0000",
            hourMinOriginal : null,
            minuteMinOriginal : null,
            secondMinOriginal : null,
            millisecMinOriginal : null,
            hourMaxOriginal : null,
            minuteMaxOriginal : null,
            secondMaxOriginal : null,
            millisecMaxOriginal : null,
            ampm : "",
            formattedDate : "",
            formattedTime : "",
            formattedDateTime : "",
            timezoneList : null,
            setDefaults : function (a) {
                return extendRemove(this._defaults, a || {}),
                    this
            },
            _newInst : function ($input, o) {
                var tp_inst = new Timepicker,
                    inlineSettings = {};
                for (var attrName in this._defaults) {
                    var attrValue = $input.attr("time:" + attrName);
                    if (attrValue)
                        try {
                            inlineSettings[attrName] = eval(attrValue)
                        } catch (err) {
                            inlineSettings[attrName] = attrValue
                        }
                }
                if (tp_inst._defaults = $.extend({}, this._defaults, inlineSettings, o, {
                        beforeShow : function (a, b) {
                            return $.isFunction(o.beforeShow) ? o.beforeShow(a, b, tp_inst) : void 0
                        },
                        onChangeMonthYear : function (a, b, c) {
                            tp_inst._updateDateTime(c),
                            $.isFunction(o.onChangeMonthYear) && o.onChangeMonthYear.call($input[0], a, b, c, tp_inst)
                        },
                        onClose : function (a, b) {
                            tp_inst.timeDefined === !0 && "" != $input.val() && tp_inst._updateDateTime(b),
                            $.isFunction(o.onClose) && o.onClose.call($input[0], a, b, tp_inst)
                        },
                        timepicker : tp_inst
                    }), tp_inst.amNames = $.map(tp_inst._defaults.amNames, function (a) {
                        return a.toUpperCase()
                    }), tp_inst.pmNames = $.map(tp_inst._defaults.pmNames, function (a) {
                        return a.toUpperCase()
                    }), null === tp_inst._defaults.timezoneList) {
                    for (var timezoneList = [], i = -11; 12 >= i; i++)
                        timezoneList.push((i >= 0 ? "+" : "-") + ("0" + Math.abs(i).toString()).slice(-2) + "00");
                    tp_inst._defaults.timezoneIso8609 && (timezoneList = $.map(timezoneList, function (a) {
                        return "+0000" == a ? "Z" : a.substring(0, 3) + ":" + a.substring(3)
                    })),
                        tp_inst._defaults.timezoneList = timezoneList
                }
                return tp_inst.hour = tp_inst._defaults.hour,
                    tp_inst.minute = tp_inst._defaults.minute,
                    tp_inst.second = tp_inst._defaults.second,
                    tp_inst.millisec = tp_inst._defaults.millisec,
                    tp_inst.ampm = "",
                    tp_inst.$input = $input,
                o.altField && (tp_inst.$altInput = $(o.altField).css({
                    cursor : "pointer"
                }).focus(function () {
                    $input.trigger("focus")
                })),
                (0 == tp_inst._defaults.minDate || 0 == tp_inst._defaults.minDateTime) && (tp_inst._defaults.minDate = new Date),
                (0 == tp_inst._defaults.maxDate || 0 == tp_inst._defaults.maxDateTime) && (tp_inst._defaults.maxDate = new Date),
                void 0 !== tp_inst._defaults.minDate && tp_inst._defaults.minDate instanceof Date && (tp_inst._defaults.minDateTime = new Date(tp_inst._defaults.minDate.getTime())),
                void 0 !== tp_inst._defaults.minDateTime && tp_inst._defaults.minDateTime instanceof Date && (tp_inst._defaults.minDate = new Date(tp_inst._defaults.minDateTime.getTime())),
                void 0 !== tp_inst._defaults.maxDate && tp_inst._defaults.maxDate instanceof Date && (tp_inst._defaults.maxDateTime = new Date(tp_inst._defaults.maxDate.getTime())),
                void 0 !== tp_inst._defaults.maxDateTime && tp_inst._defaults.maxDateTime instanceof Date && (tp_inst._defaults.maxDate = new Date(tp_inst._defaults.maxDateTime.getTime())),
                    tp_inst
            },
            _addTimePicker : function (a) {
                var b = this.$altInput && this._defaults.altFieldTimeOnly ? this.$input.val() + " " + this.$altInput.val() : this.$input.val();
                this.timeDefined = this._parseTime(b),
                    this._limitMinMaxDateTime(a, !1),
                    this._injectTimePicker()
            },
            _parseTime : function (a, b) {
                var c,
                    d = this._defaults.timeFormat.toString().replace(/h{1,2}/gi, "(\\d?\\d)").replace(/m{1,2}/gi, "(\\d?\\d)").replace(/s{1,2}/gi, "(\\d?\\d)").replace(/l{1}/gi, "(\\d?\\d?\\d)").replace(/t{1,2}/gi, this._getPatternAmpm()).replace(/z{1}/gi, "(z|[-+]\\d\\d:?\\d\\d)?").replace(/\s/g, "\\s?") + this._defaults.timeSuffix + "$",
                    e = this._getFormatPositions(),
                    f = "";
                if (this.inst || (this.inst = $.datepicker._getInst(this.$input[0])), b || !this._defaults.timeOnly) {
                    var g = $.datepicker._get(this.inst, "dateFormat"),
                        h = new RegExp("[.*+?|()\\[\\]{}\\\\]", "g");
                    d = "^.{" + g.length + ",}?" + this._defaults.separator.replace(h, "\\$&") + d
                }
                if (c = a.match(new RegExp(d, "i"))) {
                    if (-1 !== e.t && (void 0 === c[e.t] || 0 === c[e.t].length ? (f = "", this.ampm = "") : (f = -1 !== $.inArray(c[e.t].toUpperCase(), this.amNames) ? "AM" : "PM", this.ampm = this._defaults["AM" == f ? "amNames" : "pmNames"][0])), -1 !== e.h && (this.hour = "AM" == f && "12" == c[e.h] ? 0 : "PM" == f && "12" != c[e.h] ? (parseFloat(c[e.h]) + 12).toFixed(0) : Number(c[e.h])), -1 !== e.m && (this.minute = Number(c[e.m])), -1 !== e.s && (this.second = Number(c[e.s])), -1 !== e.l && (this.millisec = Number(c[e.l])), -1 !== e.z && void 0 !== c[e.z]) {
                        var i = c[e.z].toUpperCase();
                        switch (i.length) {
                            case 1:
                                i = this._defaults.timezoneIso8609 ? "Z" : "+0000";
                                break;
                            case 5:
                                this._defaults.timezoneIso8609 && (i = "0000" == i.substring(1) ? "Z" : i.substring(0, 3) + ":" + i.substring(3));
                                break;
                            case 6:
                                this._defaults.timezoneIso8609 ? "00:00" == i.substring(1) && (i = "Z") : i = "Z" == i || "00:00" == i.substring(1) ? "+0000" : i.replace(/:/, "")
                        }
                        this.timezone = i
                    }
                    return !0
                }
                return !1
            },
            _getPatternAmpm : function () {
                var a = [];
                return o = this._defaults,
                o.amNames && $.merge(a, o.amNames),
                o.pmNames && $.merge(a, o.pmNames),
                    a = $.map(a, function (a) {
                        return a.replace(/[.*+?|()\[\]{}\\]/g, "\\$&")
                    }),
                "(" + a.join("|") + ")?"
            },
            _getFormatPositions : function () {
                var a = this._defaults.timeFormat.toLowerCase().match(/(h{1,2}|m{1,2}|s{1,2}|l{1}|t{1,2}|z)/g),
                    b = {
                        h : -1,
                        m : -1,
                        s : -1,
                        l : -1,
                        t : -1,
                        z : -1
                    };
                if (a)
                    for (var c = 0; c < a.length; c++)
                        -1 == b[a[c].toString().charAt(0)] && (b[a[c].toString().charAt(0)] = c + 1);
                return b
            },
            _injectTimePicker : function () {
                var a = this.inst.dpDiv,
                    b = this._defaults,
                    c = this,
                    d = parseInt(b.hourMax - (b.hourMax - b.hourMin) % b.stepHour, 10),
                    e = parseInt(b.minuteMax - (b.minuteMax - b.minuteMin) % b.stepMinute, 10),
                    f = parseInt(b.secondMax - (b.secondMax - b.secondMin) % b.stepSecond, 10),
                    g = parseInt(b.millisecMax - (b.millisecMax - b.millisecMin) % b.stepMillisec, 10),
                    h = this.inst.id.toString().replace(/([^A-Za-z0-9_])/g, "");
                if (0 === a.find("div#ui-timepicker-div-" + h).length && b.showTimepicker) {
                    var i,
                        j = ' style="display:none;"',
                        k = '<div class="ui-timepicker-div" id="ui-timepicker-div-' + h + '"><dl><dt class="ui_tpicker_time_label" id="ui_tpicker_time_label_' + h + '"' + (b.showTime ? "" : j) + ">" + b.timeText + '</dt><dd class="ui_tpicker_time" id="ui_tpicker_time_' + h + '"' + (b.showTime ? "" : j) + '></dd><dt class="ui_tpicker_hour_label" id="ui_tpicker_hour_label_' + h + '"' + (b.showHour ? "" : j) + ">" + b.hourText + "</dt>",
                        l = 0,
                        m = 0,
                        n = 0,
                        o = 0;
                    if (k += '<dd class="ui_tpicker_hour"><div id="ui_tpicker_hour_' + h + '"' + (b.showHour ? "" : j) + "></div>", b.showHour && b.hourGrid > 0) {
                        k += '<div style="padding-left: 1px"><table class="ui-tpicker-grid-label"><tr>';
                        for (var p = b.hourMin; d >= p; p += parseInt(b.hourGrid, 10)) {
                            l++;
                            var q = b.ampm && p > 12 ? p - 12 : p;
                            10 > q && (q = "0" + q),
                            b.ampm && (0 == p ? q = "12a" : q += 12 > p ? "a" : "p"),
                                k += "<td>" + q + "</td>"
                        }
                        k += "</tr></table></div>"
                    }
                    if (k += "</dd>", k += '<dt class="ui_tpicker_minute_label" id="ui_tpicker_minute_label_' + h + '"' + (b.showMinute ? "" : j) + ">" + b.minuteText + '</dt><dd class="ui_tpicker_minute"><div id="ui_tpicker_minute_' + h + '"' + (b.showMinute ? "" : j) + "></div>", b.showMinute && b.minuteGrid > 0) {
                        k += '<div style="padding-left: 1px"><table class="ui-tpicker-grid-label"><tr>';
                        for (var r = b.minuteMin; e >= r; r += parseInt(b.minuteGrid, 10))
                            m++, k += "<td>" + (10 > r ? "0" : "") + r + "</td>";
                        k += "</tr></table></div>"
                    }
                    if (k += "</dd>", k += '<dt class="ui_tpicker_second_label" id="ui_tpicker_second_label_' + h + '"' + (b.showSecond ? "" : j) + ">" + b.secondText + '</dt><dd class="ui_tpicker_second"><div id="ui_tpicker_second_' + h + '"' + (b.showSecond ? "" : j) + "></div>", b.showSecond && b.secondGrid > 0) {
                        k += '<div style="padding-left: 1px"><table><tr>';
                        for (var s = b.secondMin; f >= s; s += parseInt(b.secondGrid, 10))
                            n++, k += "<td>" + (10 > s ? "0" : "") + s + "</td>";
                        k += "</tr></table></div>"
                    }
                    if (k += "</dd>", k += '<dt class="ui_tpicker_millisec_label" id="ui_tpicker_millisec_label_' + h + '"' + (b.showMillisec ? "" : j) + ">" + b.millisecText + '</dt><dd class="ui_tpicker_millisec"><div id="ui_tpicker_millisec_' + h + '"' + (b.showMillisec ? "" : j) + "></div>", b.showMillisec && b.millisecGrid > 0) {
                        k += '<div style="padding-left: 1px"><table><tr>';
                        for (var t = b.millisecMin; g >= t; t += parseInt(b.millisecGrid, 10))
                            o++, k += "<td>" + (10 > t ? "0" : "") + t + "</td>";
                        k += "</tr></table></div>"
                    }
                    k += "</dd>",
                        k += '<dt class="ui_tpicker_timezone_label" id="ui_tpicker_timezone_label_' + h + '"' + (b.showTimezone ? "" : j) + ">" + b.timezoneText + "</dt>",
                        k += '<dd class="ui_tpicker_timezone" id="ui_tpicker_timezone_' + h + '"' + (b.showTimezone ? "" : j) + "></dd>",
                        k += "</dl></div>",
                        $tp = $(k),
                    b.timeOnly === !0 && ($tp.prepend('<div class="ui-widget-header ui-helper-clearfix ui-corner-all"><div class="ui-datepicker-title">' + b.timeOnlyTitle + "</div></div>"), a.find(".ui-datepicker-header, .ui-datepicker-calendar").hide()),
                        this.hour_slider = $tp.find("#ui_tpicker_hour_" + h).slider({
                            orientation : "horizontal",
                            value : this.hour,
                            min : b.hourMin,
                            max : d,
                            step : b.stepHour,
                            slide : function (a, b) {
                                c.hour_slider.slider("option", "value", b.value),
                                    c._onTimeChange()
                            }
                        }),
                        this.minute_slider = $tp.find("#ui_tpicker_minute_" + h).slider({
                            orientation : "horizontal",
                            value : this.minute,
                            min : b.minuteMin,
                            max : e,
                            step : b.stepMinute,
                            slide : function (a, b) {
                                c.minute_slider.slider("option", "value", b.value),
                                    c._onTimeChange()
                            }
                        }),
                        this.second_slider = $tp.find("#ui_tpicker_second_" + h).slider({
                            orientation : "horizontal",
                            value : this.second,
                            min : b.secondMin,
                            max : f,
                            step : b.stepSecond,
                            slide : function (a, b) {
                                c.second_slider.slider("option", "value", b.value),
                                    c._onTimeChange()
                            }
                        }),
                        this.millisec_slider = $tp.find("#ui_tpicker_millisec_" + h).slider({
                            orientation : "horizontal",
                            value : this.millisec,
                            min : b.millisecMin,
                            max : g,
                            step : b.stepMillisec,
                            slide : function (a, b) {
                                c.millisec_slider.slider("option", "value", b.value),
                                    c._onTimeChange()
                            }
                        }),
                        this.timezone_select = $tp.find("#ui_tpicker_timezone_" + h).append("<select></select>").find("select"),
                        $.fn.append.apply(this.timezone_select, $.map(b.timezoneList, function (a) {
                            return $("<option />").val("object" == typeof a ? a.value : a).text("object" == typeof a ? a.label : a)
                        })),
                        this.timezone_select.val("undefined" != typeof this.timezone && null != this.timezone && "" != this.timezone ? this.timezone : b.timezone),
                        this.timezone_select.change(function () {
                            c._onTimeChange()
                        }),
                    b.showHour && b.hourGrid > 0 && (i = 100 * l * b.hourGrid / (d - b.hourMin), $tp.find(".ui_tpicker_hour table").css({
                        width : i + "%",
                        marginLeft : i / (-2 * l) + "%",
                        borderCollapse : "collapse"
                    }).find("td").each(function () {
                        $(this).click(function () {
                            var a = $(this).html();
                            if (b.ampm) {
                                var d = a.substring(2).toLowerCase(),
                                    e = parseInt(a.substring(0, 2), 10);
                                a = "a" == d ? 12 == e ? 0 : e : 12 == e ? 12 : e + 12
                            }
                            c.hour_slider.slider("option", "value", a),
                                c._onTimeChange(),
                                c._onSelectHandler()
                        }).css({
                            cursor : "pointer",
                            width : 100 / l + "%",
                            textAlign : "center",
                            overflow : "hidden"
                        })
                    })),
                    b.showMinute && b.minuteGrid > 0 && (i = 100 * m * b.minuteGrid / (e - b.minuteMin), $tp.find(".ui_tpicker_minute table").css({
                        width : i + "%",
                        marginLeft : i / (-2 * m) + "%",
                        borderCollapse : "collapse"
                    }).find("td").each(function () {
                        $(this).click(function () {
                            c.minute_slider.slider("option", "value", $(this).html()),
                                c._onTimeChange(),
                                c._onSelectHandler()
                        }).css({
                            cursor : "pointer",
                            width : 100 / m + "%",
                            textAlign : "center",
                            overflow : "hidden"
                        })
                    })),
                    b.showSecond && b.secondGrid > 0 && $tp.find(".ui_tpicker_second table").css({
                        width : i + "%",
                        marginLeft : i / (-2 * n) + "%",
                        borderCollapse : "collapse"
                    }).find("td").each(function () {
                        $(this).click(function () {
                            c.second_slider.slider("option", "value", $(this).html()),
                                c._onTimeChange(),
                                c._onSelectHandler()
                        }).css({
                            cursor : "pointer",
                            width : 100 / n + "%",
                            textAlign : "center",
                            overflow : "hidden"
                        })
                    }),
                    b.showMillisec && b.millisecGrid > 0 && $tp.find(".ui_tpicker_millisec table").css({
                        width : i + "%",
                        marginLeft : i / (-2 * o) + "%",
                        borderCollapse : "collapse"
                    }).find("td").each(function () {
                        $(this).click(function () {
                            c.millisec_slider.slider("option", "value", $(this).html()),
                                c._onTimeChange(),
                                c._onSelectHandler()
                        }).css({
                            cursor : "pointer",
                            width : 100 / o + "%",
                            textAlign : "center",
                            overflow : "hidden"
                        })
                    });
                    var u = a.find(".ui-datepicker-buttonpane");
                    if (u.length ? u.before($tp) : a.append($tp), this.$timeObj = $tp.find("#ui_tpicker_time_" + h), null !== this.inst) {
                        var v = this.timeDefined;
                        this._onTimeChange(),
                            this.timeDefined = v
                    }
                    var w = function () {
                        c._onSelectHandler()
                    };
                    if (this.hour_slider.bind("slidestop", w), this.minute_slider.bind("slidestop", w), this.second_slider.bind("slidestop", w), this.millisec_slider.bind("slidestop", w), this._defaults.addSliderAccess) {
                        var x = this._defaults.sliderAccessArgs;
                        setTimeout(function () {
                            if (0 == $tp.find(".ui-slider-access").length) {
                                $tp.find(".ui-slider:visible").sliderAccess(x);
                                var a = $tp.find(".ui-slider-access:eq(0)").outerWidth(!0);
                                a && $tp.find("table:visible").each(function () {
                                    var b = $(this),
                                        c = b.outerWidth(),
                                        d = b.css("marginLeft").toString().replace("%", ""),
                                        e = c - a,
                                        f = d * e / c + "%";
                                    b.css({
                                        width : e,
                                        marginLeft : f
                                    })
                                })
                            }
                        }, 0)
                    }
                }
            },
            _limitMinMaxDateTime : function (a, b) {
                var c = this._defaults,
                    d = new Date(a.selectedYear, a.selectedMonth, a.selectedDay);
                if (this._defaults.showTimepicker) {
                    if (null !== $.datepicker._get(a, "minDateTime") && void 0 !== $.datepicker._get(a, "minDateTime") && d) {
                        var e = $.datepicker._get(a, "minDateTime"),
                            f = new Date(e.getFullYear(), e.getMonth(), e.getDate(), 0, 0, 0, 0);
                        (null === this.hourMinOriginal || null === this.minuteMinOriginal || null === this.secondMinOriginal || null === this.millisecMinOriginal) && (this.hourMinOriginal = c.hourMin, this.minuteMinOriginal = c.minuteMin, this.secondMinOriginal = c.secondMin, this.millisecMinOriginal = c.millisecMin),
                            a.settings.timeOnly || f.getTime() == d.getTime() ? (this._defaults.hourMin = e.getHours(), this.hour <= this._defaults.hourMin ? (this.hour = this._defaults.hourMin, this._defaults.minuteMin = e.getMinutes(), this.minute <= this._defaults.minuteMin ? (this.minute = this._defaults.minuteMin, this._defaults.secondMin = e.getSeconds()) : this.second <= this._defaults.secondMin ? (this.second = this._defaults.secondMin, this._defaults.millisecMin = e.getMilliseconds()) : (this.millisec < this._defaults.millisecMin && (this.millisec = this._defaults.millisecMin), this._defaults.millisecMin = this.millisecMinOriginal)) : (this._defaults.minuteMin = this.minuteMinOriginal, this._defaults.secondMin = this.secondMinOriginal, this._defaults.millisecMin = this.millisecMinOriginal)) : (this._defaults.hourMin = this.hourMinOriginal, this._defaults.minuteMin = this.minuteMinOriginal, this._defaults.secondMin = this.secondMinOriginal, this._defaults.millisecMin = this.millisecMinOriginal)
                    }
                    if (null !== $.datepicker._get(a, "maxDateTime") && void 0 !== $.datepicker._get(a, "maxDateTime") && d) {
                        var g = $.datepicker._get(a, "maxDateTime"),
                            h = new Date(g.getFullYear(), g.getMonth(), g.getDate(), 0, 0, 0, 0);
                        (null === this.hourMaxOriginal || null === this.minuteMaxOriginal || null === this.secondMaxOriginal) && (this.hourMaxOriginal = c.hourMax, this.minuteMaxOriginal = c.minuteMax, this.secondMaxOriginal = c.secondMax, this.millisecMaxOriginal = c.millisecMax),
                            a.settings.timeOnly || h.getTime() == d.getTime() ? (this._defaults.hourMax = g.getHours(), this.hour >= this._defaults.hourMax ? (this.hour = this._defaults.hourMax, this._defaults.minuteMax = g.getMinutes(), this.minute >= this._defaults.minuteMax ? (this.minute = this._defaults.minuteMax, this._defaults.secondMax = g.getSeconds()) : this.second >= this._defaults.secondMax ? (this.second = this._defaults.secondMax, this._defaults.millisecMax = g.getMilliseconds()) : (this.millisec > this._defaults.millisecMax && (this.millisec = this._defaults.millisecMax), this._defaults.millisecMax = this.millisecMaxOriginal)) : (this._defaults.minuteMax = this.minuteMaxOriginal, this._defaults.secondMax = this.secondMaxOriginal, this._defaults.millisecMax = this.millisecMaxOriginal)) : (this._defaults.hourMax = this.hourMaxOriginal, this._defaults.minuteMax = this.minuteMaxOriginal, this._defaults.secondMax = this.secondMaxOriginal, this._defaults.millisecMax = this.millisecMaxOriginal)
                    }
                    if (void 0 !== b && b === !0) {
                        var i = parseInt(this._defaults.hourMax - (this._defaults.hourMax - this._defaults.hourMin) % this._defaults.stepHour, 10),
                            j = parseInt(this._defaults.minuteMax - (this._defaults.minuteMax - this._defaults.minuteMin) % this._defaults.stepMinute, 10),
                            k = parseInt(this._defaults.secondMax - (this._defaults.secondMax - this._defaults.secondMin) % this._defaults.stepSecond, 10),
                            l = parseInt(this._defaults.millisecMax - (this._defaults.millisecMax - this._defaults.millisecMin) % this._defaults.stepMillisec, 10);
                        this.hour_slider && this.hour_slider.slider("option", {
                            min : this._defaults.hourMin,
                            max : i
                        }).slider("value", this.hour),
                        this.minute_slider && this.minute_slider.slider("option", {
                            min : this._defaults.minuteMin,
                            max : j
                        }).slider("value", this.minute),
                        this.second_slider && this.second_slider.slider("option", {
                            min : this._defaults.secondMin,
                            max : k
                        }).slider("value", this.second),
                        this.millisec_slider && this.millisec_slider.slider("option", {
                            min : this._defaults.millisecMin,
                            max : l
                        }).slider("value", this.millisec)
                    }
                }
            },
            _onTimeChange : function () {
                var a = this.hour_slider ? this.hour_slider.slider("value") : !1,
                    b = this.minute_slider ? this.minute_slider.slider("value") : !1,
                    c = this.second_slider ? this.second_slider.slider("value") : !1,
                    d = this.millisec_slider ? this.millisec_slider.slider("value") : !1,
                    e = this.timezone_select ? this.timezone_select.val() : !1,
                    f = this._defaults;
                "object" == typeof a && (a = !1),
                "object" == typeof b && (b = !1),
                "object" == typeof c && (c = !1),
                "object" == typeof d && (d = !1),
                "object" == typeof e && (e = !1),
                a !== !1 && (a = parseInt(a, 10)),
                b !== !1 && (b = parseInt(b, 10)),
                c !== !1 && (c = parseInt(c, 10)),
                d !== !1 && (d = parseInt(d, 10));
                var g = f[12 > a ? "amNames" : "pmNames"][0],
                    h = a != this.hour || b != this.minute || c != this.second || d != this.millisec || this.ampm.length > 0 && 12 > a != (-1 !== $.inArray(this.ampm.toUpperCase(), this.amNames)) || e != this.timezone;
                h && (a !== !1 && (this.hour = a), b !== !1 && (this.minute = b), c !== !1 && (this.second = c), d !== !1 && (this.millisec = d), e !== !1 && (this.timezone = e), this.inst || (this.inst = $.datepicker._getInst(this.$input[0])), this._limitMinMaxDateTime(this.inst, !0)),
                f.ampm && (this.ampm = g),
                    this.formattedTime = $.datepicker.formatTime(this._defaults.timeFormat, this, this._defaults),
                this.$timeObj && this.$timeObj.text(this.formattedTime + f.timeSuffix),
                    this.timeDefined = !0,
                h && this._updateDateTime()
            },
            _onSelectHandler : function () {
                var a = this._defaults.onSelect,
                    b = this.$input ? this.$input[0] : null;
                a && b && a.apply(b, [this.formattedDateTime, this])
            },
            _formatTime : function (a, b) {
                a = a || {
                        hour : this.hour,
                        minute : this.minute,
                        second : this.second,
                        millisec : this.millisec,
                        ampm : this.ampm,
                        timezone : this.timezone
                    };
                var c = (b || this._defaults.timeFormat).toString();
                return c = $.datepicker.formatTime(c, a, this._defaults),
                    arguments.length ? c : void(this.formattedTime = c)
            },
            _updateDateTime : function (a) {
                a = this.inst || a;
                var b = $.datepicker._daylightSavingAdjust(new Date(a.selectedYear, a.selectedMonth, a.selectedDay)),
                    c = $.datepicker._get(a, "dateFormat"),
                    d = $.datepicker._getFormatConfig(a),
                    e = null !== b && this.timeDefined;
                this.formattedDate = $.datepicker.formatDate(c, null === b ? new Date : b, d);
                var f = this.formattedDate;
                void 0 !== a.lastVal && a.lastVal.length > 0 && 0 === this.$input.val().length || (this._defaults.timeOnly === !0 ? f = this.formattedTime : this._defaults.timeOnly !== !0 && (this._defaults.alwaysSetTime || e) && (f += this._defaults.separator + this.formattedTime + this._defaults.timeSuffix), this.formattedDateTime = f, this._defaults.showTimepicker ? this.$altInput && this._defaults.altFieldTimeOnly === !0 ? (this.$altInput.val(this.formattedTime), this.$input.val(this.formattedDate)) : this.$altInput ? (this.$altInput.val(f), this.$input.val(f)) : this.$input.val(f) : this.$input.val(this.formattedDate), this.$input.trigger("change"))
            }
        }),
        $.fn.extend({
            timepicker : function (a) {
                a = a || {};
                var b = arguments;
                return "object" == typeof a && (b[0] = $.extend(a, {
                    timeOnly : !0
                })),
                    $(this).each(function () {
                        $.fn.datetimepicker.apply($(this), b)
                    })
            },
            datetimepicker : function (a) {
                a = a || {};
                var b = arguments;
                return "string" == typeof a ? "getDate" == a ? $.fn.datepicker.apply($(this[0]), b) : this.each(function () {
                    var a = $(this);
                    a.datepicker.apply(a, b)
                }) : this.each(function () {
                    var b = $(this);
                    b.datepicker($.timepicker._newInst(b, a)._defaults)
                })
            }
        }),
        $.datepicker.formatTime = function (a, b, c) {
            c = c || {},
                c = $.extend($.timepicker._defaults, c),
                b = $.extend({
                    hour : 0,
                    minute : 0,
                    second : 0,
                    millisec : 0,
                    timezone : "+0000"
                }, b);
            var d = a,
                e = c.amNames[0],
                f = parseInt(b.hour, 10);
            return c.ampm && (f > 11 && (e = c.pmNames[0], f > 12 && (f %= 12)), 0 === f && (f = 12)),
                d = d.replace(/(?:hh?|mm?|ss?|[tT]{1,2}|[lz])/g, function (a) {
                    switch (a.toLowerCase()) {
                        case "hh":
                            return ("0" + f).slice(-2);
                        case "h":
                            return f;
                        case "mm":
                            return ("0" + b.minute).slice(-2);
                        case "m":
                            return b.minute;
                        case "ss":
                            return ("0" + b.second).slice(-2);
                        case "s":
                            return b.second;
                        case "l":
                            return ("00" + b.millisec).slice(-3);
                        case "z":
                            return b.timezone;
                        case "t":
                        case "tt":
                            return c.ampm ? (1 == a.length && (e = e.charAt(0)), "T" == a.charAt(0) ? e.toUpperCase() : e.toLowerCase()) : ""
                    }
                }),
                d = $.trim(d)
        },
        $.datepicker._base_selectDate = $.datepicker._selectDate,
        $.datepicker._selectDate = function (a, b) {
            var c = this._getInst($(a)[0]),
                d = this._get(c, "timepicker");
            d ? (d._limitMinMaxDateTime(c, !0), c.inline = c.stay_open = !0, this._base_selectDate(a, b), c.inline = c.stay_open = !1, this._notifyChange(c), this._updateDatepicker(c)) : this._base_selectDate(a, b)
        },
        $.datepicker._base_updateDatepicker = $.datepicker._updateDatepicker,
        $.datepicker._updateDatepicker = function (a) {
            var b = a.input[0];
            if (!($.datepicker._curInst && $.datepicker._curInst != a && $.datepicker._datepickerShowing && $.datepicker._lastInput != b || "boolean" == typeof a.stay_open && a.stay_open !== !1)) {
                this._base_updateDatepicker(a);
                var c = this._get(a, "timepicker");
                c && c._addTimePicker(a)
            }
        },
        $.datepicker._base_doKeyPress = $.datepicker._doKeyPress,
        $.datepicker._doKeyPress = function (a) {
            var b = $.datepicker._getInst(a.target),
                c = $.datepicker._get(b, "timepicker");
            if (c && $.datepicker._get(b, "constrainInput")) {
                var d = c._defaults.ampm,
                    e = $.datepicker._possibleChars($.datepicker._get(b, "dateFormat")),
                    f = c._defaults.timeFormat.toString().replace(/[hms]/g, "").replace(/TT/g, d ? "APM" : "").replace(/Tt/g, d ? "AaPpMm" : "").replace(/tT/g, d ? "AaPpMm" : "").replace(/T/g, d ? "AP" : "").replace(/tt/g, d ? "apm" : "").replace(/t/g, d ? "ap" : "") + " " + c._defaults.separator + c._defaults.timeSuffix + (c._defaults.showTimezone ? c._defaults.timezoneList.join("") : "") + c._defaults.amNames.join("") + c._defaults.pmNames.join("") + e,
                    g = String.fromCharCode(void 0 === a.charCode ? a.keyCode : a.charCode);
                return a.ctrlKey || " " > g || !e || f.indexOf(g) > -1
            }
            return $.datepicker._base_doKeyPress(a)
        },
        $.datepicker._base_doKeyUp = $.datepicker._doKeyUp,
        $.datepicker._doKeyUp = function (a) {
            var b = $.datepicker._getInst(a.target),
                c = $.datepicker._get(b, "timepicker");
            if (c && c._defaults.timeOnly && b.input.val() != b.lastVal)
                try {
                    $.datepicker._updateDatepicker(b)
                } catch (d) {
                    $.datepicker.log(d)
                }
            return $.datepicker._base_doKeyUp(a)
        },
        $.datepicker._base_gotoToday = $.datepicker._gotoToday,
        $.datepicker._gotoToday = function (a) {
            var b = this._getInst($(a)[0]),
                c = b.dpDiv;
            this._base_gotoToday(a);
            var d = new Date,
                e = this._get(b, "timepicker");
            if (e && e._defaults.showTimezone && e.timezone_select) {
                var f = d.getTimezoneOffset(),
                    g = f > 0 ? "-" : "+";
                f = Math.abs(f);
                var h = f % 60;
                f = g + ("0" + (f - h) / 60).slice(-2) + ("0" + h).slice(-2),
                e._defaults.timezoneIso8609 && (f = f.substring(0, 3) + ":" + f.substring(3)),
                    e.timezone_select.val(f)
            }
            this._setTime(b, d),
                $(".ui-datepicker-today", c).click()
        },
        $.datepicker._disableTimepickerDatepicker = function (a) {
            var b = this._getInst(a),
                c = this._get(b, "timepicker");
            $(a).datepicker("getDate"),
            c && (c._defaults.showTimepicker = !1, c._updateDateTime(b))
        },
        $.datepicker._enableTimepickerDatepicker = function (a) {
            var b = this._getInst(a),
                c = this._get(b, "timepicker");
            $(a).datepicker("getDate"),
            c && (c._defaults.showTimepicker = !0, c._addTimePicker(b), c._updateDateTime(b))
        },
        $.datepicker._setTime = function (a, b) {
            var c = this._get(a, "timepicker");
            if (c) {
                var d = c._defaults,
                    e = b ? b.getHours() : d.hour,
                    f = b ? b.getMinutes() : d.minute,
                    g = b ? b.getSeconds() : d.second,
                    h = b ? b.getMilliseconds() : d.millisec;
                (e < d.hourMin || e > d.hourMax || f < d.minuteMin || f > d.minuteMax || g < d.secondMin || g > d.secondMax || h < d.millisecMin || h > d.millisecMax) && (e = d.hourMin, f = d.minuteMin, g = d.secondMin, h = d.millisecMin),
                    c.hour = e,
                    c.minute = f,
                    c.second = g,
                    c.millisec = h,
                c.hour_slider && c.hour_slider.slider("value", e),
                c.minute_slider && c.minute_slider.slider("value", f),
                c.second_slider && c.second_slider.slider("value", g),
                c.millisec_slider && c.millisec_slider.slider("value", h),
                    c._onTimeChange(),
                    c._updateDateTime(a)
            }
        },
        $.datepicker._setTimeDatepicker = function (a, b, c) {
            var d = this._getInst(a),
                e = this._get(d, "timepicker");
            if (e) {
                this._setDateFromField(d);
                var f;
                b && ("string" == typeof b ? (e._parseTime(b, c), f = new Date, f.setHours(e.hour, e.minute, e.second, e.millisec)) : f = new Date(b.getTime()), "Invalid Date" == f.toString() && (f = void 0), this._setTime(d, f))
            }
        },
        $.datepicker._base_setDateDatepicker = $.datepicker._setDateDatepicker,
        $.datepicker._setDateDatepicker = function (a, b) {
            var c = this._getInst(a),
                d = b instanceof Date ? new Date(b.getTime()) : b;
            this._updateDatepicker(c),
                this._base_setDateDatepicker.apply(this, arguments),
                this._setTimeDatepicker(a, d, !0)
        },
        $.datepicker._base_getDateDatepicker = $.datepicker._getDateDatepicker,
        $.datepicker._getDateDatepicker = function (a, b) {
            var c = this._getInst(a),
                d = this._get(c, "timepicker");
            if (d) {
                this._setDateFromField(c, b);
                var e = this._getDate(c);
                return e && d._parseTime($(a).val(), d.timeOnly) && e.setHours(d.hour, d.minute, d.second, d.millisec),
                    e
            }
            return this._base_getDateDatepicker(a, b)
        },
        $.datepicker._base_parseDate = $.datepicker.parseDate,
        $.datepicker.parseDate = function (a, b, c) {
            var d;
            try {
                d = this._base_parseDate(a, b, c)
            } catch (e) {
                if (!(e.indexOf(":") >= 0))
                    throw e;
                d = this._base_parseDate(a, b.substring(0, b.length - (e.length - e.indexOf(":") - 2)), c)
            }
            return d
        },
        $.datepicker._base_formatDate = $.datepicker._formatDate,
        $.datepicker._formatDate = function (a, b, c, d) {
            var e = this._get(a, "timepicker");
            if (e) {
                if (b) {
                    this._base_formatDate(a, b, c, d)
                }
                return e._updateDateTime(a),
                    e.$input.val()
            }
            return this._base_formatDate(a)
        },
        $.datepicker._base_optionDatepicker = $.datepicker._optionDatepicker,
        $.datepicker._optionDatepicker = function (a, b, c) {
            var d = this._getInst(a),
                e = this._get(d, "timepicker");
            if (e) {
                var f,
                    g,
                    h;
                "string" == typeof b ? "minDate" === b || "minDateTime" === b ? f = c : "maxDate" === b || "maxDateTime" === b ? g = c : "onSelect" === b && (h = c) : "object" == typeof b && (b.minDate ? f = b.minDate : b.minDateTime ? f = b.minDateTime : b.maxDate ? g = b.maxDate : b.maxDateTime && (g = b.maxDateTime)),
                    f ? (f = 0 == f ? new Date : new Date(f), e._defaults.minDate = f, e._defaults.minDateTime = f) : g ? (g = 0 == g ? new Date : new Date(g), e._defaults.maxDate = g, e._defaults.maxDateTime = g) : h && (e._defaults.onSelect = h)
            }
            return void 0 === c ? this._base_optionDatepicker(a, b) : this._base_optionDatepicker(a, b, c)
        },
        $.timepicker = new Timepicker,
        $.timepicker.version = "0.9.9"
})(jQuery);


var DatePickerUtil = DatePickerUtil || {

        /**
         * 在表单上使用
         */
        datePickerConfig: {
            showOn: "both",//focus,button,both
            //buttonImage: App["staticContextPath"] + "/assets/js/framework/widgets/jQueryUI/1.10.4.ext.0.2-SNAPSHOT/css/images/extend/date-icon.png",
            buttonImageOnly: true,
            showButtonPanel: true,
            changeMonth: true,
            yearRange: "c-10:c+50",
            changeYear: true,
            beforeShow: function (input, inst) {
                //焦点事件分为：人为聚焦 和 js脚本聚焦
                if (window.event && window.event.type == "focus") {
                    if (event.relatedTarget) {
                        return true;
                    } else {
                        //js脚本聚焦，只聚焦
                        return false;
                    }
                }
                return true;
            },
            positionRefer: function () {
                //控件输入框 被父<label>包着。
                //下拉层的定位参照 是父<label>
                return $(this).parent("label");
            }
        },

        /**
         * 渲染所有日期控件
         */
        render: function (props) {
            props = props || {};
            $("input[componentType='datepicker']").datepicker($.extend({}, DatePickerUtil.datePickerConfig, props));
        }
    };