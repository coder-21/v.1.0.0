
$(document).ready(function(){
	var _html = "Find Goose terms and condition<br /><br />Yet to be decided<br /><br />By checking the box, you confirm that you understand and accept the terms outlined here and in and our Terms of Use and Copyright";
	
	$("#agree_to").prop("checked",false).on("click", function(){
		if( $(this).prop("checked") )
			$("#tos_trigger").trigger("click");
		else
			resetTOS();
		
	});
	$("#tos_trigger").stmModal({
		trigger: "click",
		type: "info",
		colorscheme: "info",
		extraAttr: "for-login", 
		heading: "Terms &amp; Conditions of Use",
		message: _html,
		button: "I Understand",
		showCancel: true,
		afterClose: "activateProcessRegistration",
		afterCancel: "resetTOS"
	});
	
	
});

var resetTOS = function(){			
	$("#agree_to").removeAttr("checked").prop("checked",false);
	$("#processRegistration").addClass("disabled").attr("disabled","disabled").off("click");
};

var activateProcessRegistration = function(){
	$("#agree_to").prop("checked",true);//.attr("disabled", "disabled").off("click");
	$("#processRegistration").removeClass("disabled").removeAttr("disabled")
	.on("click", validateRegistration);
}