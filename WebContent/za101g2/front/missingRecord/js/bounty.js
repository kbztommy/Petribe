$(document).ready(function() {
	$("p").hide();
	$("#bounty").attr("disabled", true);
	$(".creditCard").attr("disabled", true);
	
	if ($("#fullBounty").prop("checked")) {
		$("p").show();
		$("#bounty").attr("disabled", false);
		$(".creditCard").attr("disabled", false);
		$(".creditCard").keydown(function(key) {
	        if ((key.keyCode > 8) && (key.keyCode < 46 || key.keyCode > 57) && (key.keyCode < 96 || key.keyCode > 105)) return false;
	    });
	}

	$("#fullBounty").click(function() {
		$("p").show(1000);
		$("#bounty").attr("disabled", false);
		$(".creditCard").attr("disabled", false);
		$(".creditCard").keydown(function(key) {
	        if ((key.keyCode > 8) && (key.keyCode < 46 || key.keyCode > 57) && (key.keyCode < 96 || key.keyCode > 105)) return false;
	    });
	});
	$("#emptyBounty").click(function() {
		$("p").hide(1000);
		$("#bounty").attr("disabled", true);
		$(".creditCard").attr("disabled", true);
	});
});
