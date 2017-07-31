$('#demo').on('shown.bs.modal',function(e){
    $(this).find('.modal-title').html($(e.relatedTarget).text());
});
/* Request a Demo modal - loading content */
	if( $('#demo').length ) {
		$("#request-a-demo-modal").html("<i class='fa fa-spinner fa-pulse'></i>");
		$('#demo').on('shown.bs.modal', function() {
			//$("#request-a-demo-modal").load("request-a-demo.html #rad-content", function(data){
			$("#request-a-demo-modal").load("FindGoose_Reset", function(data){
				$("#request-a-demo-modal .col-md-9").attr("class","col-md-12");
				_isAjaxReq = true;
				setupDemoRequestForm();
			});
		});
		
		$('#demo').on('hidden.bs.modal', function () {
			$("#request-a-demo-modal").html("<i class='fa fa-spinner fa-pulse'></i>");
			$("#request-demo-form input, #request-demo-form select").addClass("ignore");
			$("#request-demo-form").validate({ ignore: ".ignore"});
			_isAjaxReq = false;
		});	
	}
	else {
		if( $("#request-demo-form").length ) {
			setupDemoRequestForm();
		}
	}	
	
	function setupDemoRequestForm(){
		
		$("#request-demo-form").validate({
			submitHandler: function(form) {
				
				var _xhr = $.ajax({
					//url: '/premium-access/schedule-a-live-demo',
					//url: 'requestdemo.php',
					url: ROOT_URL + '/marketing/send-demo-request',
					type: 'POST',
					data: {
						name: $("#demo_name").val(),
						email: $("#demo_email").val(),
						phone: $("#demo_phone").val(),
						company_name: $("#demo_company").val(),
						job_title: $("#demo_title").val(),
						number_of_employees: $("#demo_employees").val(),
						zip_code: $("#demo_country").val(),
						learned_about: $("#demo_referer").val(),
						isAjax: _isAjaxReq
					},
					success: function(data){
						var _return = JSON.parse(data);					
						_func_afterClose = _isAjaxReq ? "backToMarketingSite" : "backToMarketingSiteHomepage";
						
						_return_type = _return.result;
						_return_text = _return.message;
						
						if(_return_type == "success"){
							
							//alert("success");
							_html = "<div class='para'>Your PrivCo Demo Request has been sent.</div><div class='para'>One of our awesome sales team members will be contacting you pretty soon.</div>";
							$("#rad-trigger").stmModal({
								format: "hbar",
								trigger: "click",
								type: "success",
								colorscheme: "success",
								heading: "All Done!",
								message: _html,
								button: "OK",
								afterClose: _func_afterClose
							});
							var _fauxClick = setTimeout(function(){
								$(".cancel-button").click();
								$("#rad-trigger").click();
								clearTimeout(_fauxClick);
							}, 10);	
						}
						else {
							_html = "<div class='para'>Sorry, but we couldn't send your request.</div><div class='para'>Please contact PrivCo support directly: <a href=\"mailto:sales@privco.com\">sales@privco.com</a>.</div>";
							$("#rad-trigger").stmModal({
								format: "hbar",
								trigger: "click",
								type: "error",
								colorscheme: "error",
								heading: "Oh no...",
								message: _html,
								button: "OK"
							});
							var _fauxClick = setTimeout(function(){
								$(".cancel-button").click();
								$("#rad-trigger").click().off();
								clearTimeout(_fauxClick);
							}, 10);
						}
					}
				})
				.fail(function(){
						//alert("fail");
						_html = "<div class='para'>Sorry, but our system is acting up right now.</div><div class='para'>Please contact PrivCo support directly: <a href=\"mailto:support@privco.com\">support@privco.com</a>.</div>";
						$("#rad-trigger").stmModal({
							format: "hbar",
							trigger: "click",
							type: "error",
							colorscheme: "error",
							heading: "Oh no...",
							message: _html,
							button: "OK"
						});
						var _fauxClick = setTimeout(function(){
							$("#rad-trigger").click();
							clearTimeout(_fauxClick);
						}, 10);						
				});	

				//alert("valid submission");
				return false;
			},
			rules: {
				demo_name: {
					required: true
				},
				demo_email: {
					required: true,
					email: true
				},
				demo_phone: {
					required: true,
					digits: true
				},
				demo_company: {
					required: true
				},
				demo_title: {
					required: true
				},
				demo_employees: {
					required: true
				},
				demo_country: {
					required: true
				}
			},
			messages: {
				demo_name: { required: "Please enter your name." },
				demo_email: { required: "Please enter your email address.", email: "Please enter a valid email address." },
				demo_phone: { required: "Please enter your phone number.", digits: "Please enter digits only." },
				demo_company: { required: "Please enter your company name." },
				demo_title: { required: "Please enter your title." },
				demo_employees: { required: "Please select the number of employees at your company." },
				demo_country: { required: "Please select the country your company is located." }
			}
		});
	}
