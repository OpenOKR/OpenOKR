/**
 * 框架扩展
 */
jQuery(function($){
	$.timepicker.regional['zh-CN'] = {
		currentText : "当前时间",
		closeText : "确定",
		timeOnlyTitle : "选择时间",
		timeText : "时间",
		hourText : "时",
		minuteText : "分",
		secondText : "秒",
		millisecText : "毫秒"};
	$.timepicker.setDefaults($.timepicker.regional['zh-CN']);
	//
	$.datepicker.regional['zh-CN'] = $.extend(true,$.datepicker.regional['zh-CN'],{
		cleanText : "清空",
		fmtError:" 输入日期的格式不正确!"
	});
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});