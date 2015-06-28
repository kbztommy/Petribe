$(document).ready(function() {
	$("#account").keydown(function(key) {
        if ((key.keyCode > 8) && (key.keyCode < 46 || key.keyCode > 57) && (key.keyCode < 96 || key.keyCode > 105)) return false;
    });	
});