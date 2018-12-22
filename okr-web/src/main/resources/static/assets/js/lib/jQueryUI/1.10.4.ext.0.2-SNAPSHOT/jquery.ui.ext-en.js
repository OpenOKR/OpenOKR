/**
 * 框架扩展
 */
jQuery(function($){
	$.timepicker.regional['en-GB'] = {
		currentText : "Now",
		closeText : "Done",
		timeOnlyTitle : "Select time",
		timeText : "Time",
		hourText : "H",
		minuteText : "M",
		secondText : "S",
		millisecText : "MS"};
	$.timepicker.setDefaults($.timepicker.regional['en-GB']);
	//
	$.datepicker.regional['en-GB'] = $.extend(true,$.datepicker.regional['en-GB'],{
		cleanText : "Clean",
		fmtError:"Enters dates in an incorrect format!"
	});
	$.datepicker.setDefaults($.datepicker.regional['en-GB']);
});
