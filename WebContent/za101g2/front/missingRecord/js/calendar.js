$(function() {
	$("#datepicker").datepicker({
	showOn: "button",
	buttonImage: "./images/calendar.gif",
	buttonImageOnly: true,	
	dateFormat: "yy-mm-dd",
	changeMonth: true,
	changeYear: true,
	minDate: "-1y",
	maxDate: 0
	});
});
		