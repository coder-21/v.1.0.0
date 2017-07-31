var CURRENT_PROTOCOL = location.protocol;
var ALT_PROTOCOL;
//var ROOT_URL = location.protocol + '//' + location.host;
var ROOT_URL = CURRENT_PROTOCOL + '//' + location.host;
var _SEARCH_ROOT_URL = location.protocol + '//' + location.host;

var _HTTP_MKTG_LOGIN = "//" + location.host + '/marketing/login';
var _HTTPS_MKTG_LOGIN = 'https://' + location.host + '/marketing/login';
var _HTTP_MKTG_LOGOUT = "//" + location.host + '/user/logout/';
var _HTTPS_MKTG_LOGOUT = 'https://' + location.host + '/user/logout/';



var _HTTP_LOGIN = "//" + location.host + '/login';
var _HTTPS_LOGIN = 'https://' + location.host + '/login';
var _HTTP_LOGOUT = "//" + location.host + '/user/logout/';
var _HTTPS_LOGOUT = 'https://' + location.host + '/user/logout/';



var _logged_in = false;
var _logged_in_username = "User";
var _login_protocol = location.protocol == "https:" ? _HTTPS_LOGIN : _HTTP_LOGIN;
var _logout_protocol = location.protocol == "https:" ? _HTTPS_LOGOUT : _HTTP_LOGOUT;
var _rr_loaded = false;
var _return_type = "";
var _return_type = "";
var recent_research = "";
var _num_research_posts = 3;
if( typeof _isAjaxReq == "undefined" ) var _isAjaxReq = false;
if( typeof USER_IS_SUBSCRIBER == "undefined" ) var USER_IS_SUBSCRIBER = false;

var _redirect;

var _plan_comp_url = $("#pricing-link a").attr("href") || ROOT_URL + "/marketing/plan-comparison";
var _contact_us_url = $("#contact-link a").attr("href") || ROOT_URL + "/marketing/contact-us";
var _request_demo_url = $("#requestdemo-link a").attr("href") || ROOT_URL + "/marketing/request-a-demo";

var login_err_msgs = Array(
	["remote_address_not_within_require_ip", "This account can only be used from on campus or through your library\'s website if you are off campus.<br />If you are off-campus and getting this error message, please just try connecting to PrivCo through your library\'s website or proxy server again prior to accessing PrivCo.<br />If you are still having trouble, please email your PrivCo account manager Dan Gingert (dan@privco.com) for an immediate response, or contact your library."],
	["remote_address_excluded", "This is a single-user account and you are not authorized as an additional user.<br />Please email <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a> or call 212-645-1686 to upgrade or add additional users."],
	["delete", "This account is no longer active. Please contact us at <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a>."],
	["inactive", "This account is no longer active. Please contact us at <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a>."],
	["pending", "This account is not activated yet. Please check your email inbox (and your spam folder) for your verification link, and if you still don't see it, contact us at <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a>."],
	["not_yet_active", "This account is not yet active. For questions or concerns, please contact us at <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a>."],
	["expired", "This account has expired. Please contact us at <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a>."]
);

var client_logos = Array(
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-berkshire-partners.png", "Berkshire Partners", "investors/private-equity/berkshire-partners-llc"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-wellington-management.png", "Wellington Management", "private-company/wellington-management-company-llc"],
["//res.cloudinary.com/privco1/image/upload/v1441923723/clients/client-good-technology.png", "Good Technology", "private-company/good-technology-inc"],
["//res.cloudinary.com/privco1/image/upload/v1441923723/clients/client-dtz.png", "DTZ", "private-company/dtz"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-google.png", "Google", "private-company/google-inc"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-andreessen-horowitz.png", "Andreessen Horowitz", "investors/venture-capital/andreessen-horowitz-llc"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-the-hershey-co.png", "The Hershey Company", "private-company/hershey-foods-corporation"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-nestle.png", "Nestle Corp.", "private-company/nestle-sa"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-at-kearney.png", "AT Kearney Inc.", "private-company/a-t-kearney-inc"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-ge-oil-and-gas.png", "GE Oil &amp; Gas", "private-company/ge-oil-and-gas-pressure-control-limited"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-greenhill.png", "Greenhill &amp; Co.", "private-company/greenhill-and-co-inc"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-gartner.png", "Gartner, Inc.", "private-company/gartner-inc"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-bain.png", "Bain Capital, LLC", "investors/private-equity/bain-capital-llc"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-nasa.png", "NASA", "private-company/national-aeronautics-and-space-administration"],
["//res.cloudinary.com/privco1/image/upload/v1441923723/clients/client-kornferry.png", "Korn/Ferry", "private-company/korn-ferry-international"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-hggc.png", "HGGC, LLC", "investors/private-equity/huntsman-gay-global-capital"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-tpg.png", "TPG", "investors/private-equity/tpg-capital"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-uscc.png", "US Chamber of Commerce", "https://www.uschamber.com/"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-boozco.png", "booz&amp;co", "private-company/booz-and-company-gmbh"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-deloitte.png", "Deloitte", "private-company/deloitte-touche-tohmatsu-limited"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-cooley.png", "Cooley LLP", "private-company/cooley-llp"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-microsoft.png", "Microsoft", "private-company/microsoft-corporation"],
["//res.cloudinary.com/privco1/image/upload/v1441923723/clients/client-grainger.png", "Grainger", "private-company/ww-grainger-inc"],
["//res.cloudinary.com/privco1/image/upload/v1441923723/clients/client-hellman-friedman.png", "Hellman &amp; Friedman LLC", "investors/private-equity/hellman-and-friedman-llc"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-profidence-equity.png", "Providence Equity Partners LLC", "investors/private-equity/providence-equity-partners-llc"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-proskauer.png", "Proskauer Rose LLP", "private-company/proskauer-rose-llp"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-verisign.png", "VeriSign, Inc.", "private-company/verisign-inc"],
["//res.cloudinary.com/privco1/image/upload/v1441923722/clients/client-panasonic.png", "Panasonic", "private-company/panasonic-corporation"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-blackberry.png", "Blackberry", "//us.blackberry.com/"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-alvarez-marsal.png", "Alvarez &amp; Marsal", "private-company/alvarez-and-marsal-securities-llc"],
["//res.cloudinary.com/privco1/image/upload/v1441923724/clients/client-ubs.png", "UBS &mdash; Equity Capital Markets", "private-company/ubs-bank-usa"],
["//res.cloudinary.com/privco1/image/upload/v1441923725/clients/client-xerox.png", "Xerox", "private-company/xerox-corporation"],
["//res.cloudinary.com/privco1/image/upload/v1441923721/clients/client-clorox.png", "Clorox", "private-company/clorox-company"],
["//res.cloudinary.com/privco1/image/upload/v1441923723/clients/client-prudential.png", "Prudential", "investors/private-equity/prudential-capital-partners-lp"]
);
/*
["", "", ""]
*/

var client_carousel_items = 6;

/* banner slider note: add an img to active the slider */
if( $("#banner").length ) 
$("#banner").backstretch([
      "//res.cloudinary.com/stmprivco/image/upload/v1437774353/DSC03395.jpg",
  ], {duration: 6000, fade: 750});
  
var faqs = Array(
["","Where does your data come from?", "Our data is gathered from a variety of different sources. We use a hybrid model that combines proprietary data technology and human financial analysis. Our proprietary technology gleans information from nearly 15,000 sources, including local newspapers, business journals, trade publications, blogs, press releases, news articles from across the country and internationally, government documents, and organizes relevant data in a comprehensive way so that our human analysts can comprehend, synthesize, and supplement it."],
["","What if I can't find the company I am looking for?", "Please email <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a> and we would be happy to help. For certain companies, we may be able to provide a custom research report."],
["","When will my report be ready?", "It will be ready in 1 business day. We ask for 1 business day because an analyst will review the profile to ensure that you get the latest information on the company."],
["","What is included in a report?", "We serve in the marketplace to save clients time. Our goal is to provide as much information (both qualitative and quantitative) as possible. Sometimes this can include Full Financials, other times it will include Revenue, Employee Productivity as well as VC/PE funding and M&A activity. On the qualitative front, we do our best to provide a business summary, recent developments, list of competitors and relevant exhibits. With specific questions, contact <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a> or chat with us on our website."],
["","What is the difference between Premium and Enterprise access?", "A premium subscription is best geared towards users that need our platform for a single user for a limited time. Online subscriptions will have a lower data usage level than enterprise. Enterprise access is best suited for corporations that have multiple users that consistently need private company intelligence."],
["quotasApply","Are there limitations to my Premium access?", "Yes. Premium subscriptions have certain caps.<br />Below are current monthly caps for a premium online  subscription.<br><br><strong class=\"centered btn-block\">Premium Online Quota</strong><table class=\"faq-table\"><tr><td>Company Profile Views</td><td>50</td></tr><tr><td>Investor Profile Views</td><td>50</td></tr><tr><td>Deal Views</td><td>50</td></tr><tr><td>Page Views per Search</td><td>5</td></tr><tr><td>Excel Exports</td><td>25</td></tr><tr><td>PDF Downloads</td><td>25</td></tr></table><br><br>For more information, contact <a href=\""+_contact_us_url+"?mailto=sales\">sales@privco.com</a>."],
["","Is there a student discount?", "Yes, we provide a student discount. Please email <a href=\""+_contact_us_url+"?mailto=support\">support@privco.com</a> with your .edu email and we will send you a promo code to apply to your purchase."]
);

$(document).ready(function () {
	
	_initSetup();

	/* client filter */
	/* Filters */
    $('.filterOptions li a').click(function () {
        var ourClass = $(this).attr('class');
        $('.filterOptions li').removeClass('active');
        $(this).parent().addClass('active');
        if (ourClass == 'all') {
            $('.ourHolder').children().show();
        } else {
            $('.ourHolder').children('div:not(.' + ourClass + ')').hide();
            $('.ourHolder').children('div.' + ourClass).show();
        }
        return false;
    });
});


function random(owlSelector){
	owlSelector.children().sort(function(){
		return Math.round(Math.random()) - 0.5;
	}).each(function(){
		$(this).appendTo(owlSelector);
	});
}

function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}

function newsletterSubscriptionResultsHandler(result, code){
	//console.log(result);
	//console.log(code);
	switch(result){
		case "success":
			_type = "success";
			_cscheme = "success";
			_heading = "You're Subscribed!";
			_html = "<div class='para'>You'll start receiving our newsletter soon.</div><div class='para'>And don't worry, we won't sell or share your email address. We hate spam too.</div>";
			_afterClose = "backToMarketingSite";
		break;
		
		case "error":
			_type = "error";
			_cscheme = "error";
			_heading = "Oh no...";
			
			switch(code){
				case "invalid":
					_html = "<div class='para'>Well THIS is embarrassing...</div><div class='para'>It's great that you want to subscribe, but you're doing it wrong.</div>";
				break;
				
				case "existing":
					_heading = "Oh wow...";
					_type = "info";
					_cscheme = "info";
					_html = "<div class='para'>You really love us huh?</div><div class='para'>We can totally tell, since you're trying to sign up <strong><u><em>again</em><u></strong> =)</div>";
					_afterClose = "doNothing";
				break;
				
				case "failed_saving":
					_html = "<div class='para'>Our system isn't quite cooperating right now.</div><div class='para'>No worries. Send us an email directly to <a href='mailto:support@privco.com'>support@privco.com</a> and we'll get you signed up ASAP!</div>";
				break;				
			}
		break;
	}	
	
	/*
	console.log(_type);
	console.log(_cscheme);
	console.log(_heading);
	console.log(_html);
	console.log(_afterClose);
	*/	
	
	$("#newsletter-trigger").unbind().off().stmModal({
		format: "hbar",
		trigger: "click",
		type: _type,
		colorscheme: _cscheme,
		heading: _heading,
		message: _html,
		button: "OK",
		afterClose: _afterClose
	});
	var _fauxClick = setTimeout(function(){
		/*
		$ntc = $("#newsletter-trigger").clone().attr({
			"id": "newsletter_trigger_clone",
			"class": "hide btn btn-blue"
		});
		
		$ntc.insertAfter("#newsletter-trigger");
		*/
		$("#newsletter-trigger").click().unbind().off();//.remove();
		/*
		$ntc.attr("id","newsletter-trigger");
		$("#newsletter-trigger").unbind().off();
		*/
		clearTimeout(_fauxClick);
	}, 10);	
}

function backToMarketingSite(){
	$("#newsletter-form").unbind().off();
	$("#newsletter-subscribe, #email").unbind().off().attr("disabled","disabled");
	return false;
}

function backToMarketingSiteHomepage(){
	top.location = '../.';
}

function isUserLoggedIn(){
		//console.log(_logged_in);
		//console.log(_logged_in_username);
	if( _logged_in && _logged_in_username ) {
		//$("#primaryNav").addClass("hide");
		//if( $("#getStartedBtn").length ) $("#getStartedBtn").addClass("hide");
		//if( $("#getStartedPrefooter").length ) $("#getStartedPrefooter").addClass("hide");
		//$("#userLoggedIn").html(_logged_in_username);
		//$("#loggedInSession").removeClass("hide");
	}	
	else {
		//deleteCookie("logged_in");
		//deleteCookie("logged_in_username");
		//$("#primaryNav").removeClass("hide");
		//if( $("#getStartedBtn").length ) $("#getStartedBtn").removeClass("hide");
		//if( $("#getStartedPrefooter").length ) $("#getStartedPrefooter").removeClass("hide");
		//$("#userLoggedIn").html("");
		//$("#loggedInSession").addClass("hide");
	}
	
	/* Logout Button */
	/*
	$("#signOut").off().on("click", function(){
		var _xhr = $.ajax({
			url: _logout_protocol,
			type: 'GET',
			dataType: 'json',
			data: {
				isAjax: 1
			},
			success: function(data){
				//_logged_in = data.logged_in;
				//_logged_in_username = data.username;
				//console.log("_logged_in="+_logged_in);
				//isUserLoggedIn();
				backToMarketingSiteHomepage();
			}
		})
		.fail(function(){
			console.log("fail");
		});
	});	
	*/
}

function setupNameSearch() {
	//$('#primary_search').addClass('default-text').val("Enter Private Company, Advisor or Investor Name");
	
	// Hook onto auto-suggest events
	var primary_search_cache = {};
	
	$('#primary_search').on("focus", function(){ $(this).addClass("active-search"); }).on("blur", function(){  $(this).removeClass("active-search");  });
	
	//if( $('#primary_search').val().length > 3 )
	$("#primary_search").autocomplete({
		source: function(request, response) {
			if($(this).data('_last_xhr')) {
				$(this).data('_last_xhr').abort();
			}
			
			var term = request.term;
			if ( term in primary_search_cache ) {
				response($.map(primary_search_cache[ term ].companies, function( item ) {
					if(item.hasOwnProperty('__additional_results')) {
						return item;
					}
					return {
						label: item.name,
						value: item.value,
						type: item.type,
						permalink: item.permalink,
						location: item.location
					}
				}));
				return;
			}
			
			var xhr = $.ajax({
				url: _SEARCH_ROOT_URL+"/company/search/rpc",
				dataType: 'json',
				data: {
					'q': $.trim(request.term),
					'cmd': 'suggest-all',
					'include_public': $('input#top_nav_search_include_public').attr('checked') ? 'Yes' : 'No'
				},
				success: function(data) {
					primary_search_cache[ term ] = data;
					response($.map(data.companies, function( item ) {
						if(item.hasOwnProperty('__additional_results')) {
							return item;
						}
						
						return {
							label: item.name,
							value: item.value,
							type: item.type,
							permalink: item.permalink,
							location: item.location
						}
					}));
				}
			});
			
			$(this).data('_last_xhr', xhr);
		},
		
		//appendTo: $('#masthead .search-form'),
		appendTo: $('#home-search-results'),
		minLength: 3,
		delay: 0,
		
		create: function() {},
		search: function() {
			$('div#top_nav_search_settings_content').hide();
			$('a#open_settings_btn').show();
		},
		open: function() {},
		select: function(event, ui, item) {
			var item = ui.item;
			if(item && item.hasOwnProperty('permalink')) {
				var permalink = item.permalink;
				if(permalink.length>0) {
					window.location.href = permalink;
				}
			}
		}

	//}).data("autocomplete")._renderItem = function(ul, item) {
	}).data("ui-autocomplete")._renderItem = function(ul, item) {
		if(item.hasOwnProperty('__additional_results')) {
			var goBtn = $("<a class=\"top-nav-auto-suggest-btn-main btn-green\" target=\"_blank\" href=\""+item.target_url+"\">View more results &rarr;</a>");
			//goBtn.on("click",function(){ window.location.href = $(this).attr('href'); return false; });
			goBtn.on("click",function(){ window.open($(this).attr('href')); return false; });
		
			//return $("<li></li>").data("item.autocomplete", item).append(goBtn).appendTo(ul);
			return $("<li></li>").data("ui-autocomplete-item", item).append(goBtn).appendTo(ul);
		}
		
		var term = $.trim($("#primary_search").val());
		
		var term_clean = term.replace(/[^a-zA-Z 0-9 ]+/g,'');
		var words = term_clean.split(' ');
		
		var itemPermalink = item.permalink;
		
		pathArray = itemPermalink.split( '/' );
		protocol = pathArray[0];
		host = pathArray[2];
		var BASE_URL = protocol + '//' + host ;
		
		itemPermalink = itemPermalink.replace(BASE_URL, ROOT_URL);
		
		var locationEl = item.location.length>0 ? ' <small>('+item.location+')</small>' : '';
		var typeEl = item.type == 'Public Company'? "<x-small>" + item.type + "</x-small>":"<small><b>" + item.type + "</b></small>";
		
		var goBtn = $("<a target='_blank' class=\"top-nav-auto-suggest-btn-main\" href=\""+itemPermalink+"\">" + item.label + "<br />" + typeEl +locationEl+"</a>");
		//goBtn.on("click",function(){ window.location.href = $(this).attr('href'); return false; });
		goBtn.on("click",function(){ window.open($(this).attr('href')); return false; });
		
		return $("<li></li>")
			.data("item.autocomplete", item)
			.append(goBtn)
			.appendTo(ul);
	};
	
	// Hook onto Name/Keyword Search
	$('input.search-keywords').closest('form').submit(function(){
		var inputValue = $('.search-keywords', $(this)).val();
		inputValue = $.trim(inputValue);
		if(!inputValue.length) return false;
		inputValue = inputValue.replace('/', ' ');
		if(inputValue!='Enter Private Company or Investor Name' && inputValue!='') {
		    window.location.href = '/company/search/'+inputValue+'/all';	
		}
		return false;
	});
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function deleteCookie(cname) {
    var d = new Date();
	neg_exdays = -1;
    d.setTime(d.getTime() + (neg_exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
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

function setupContactForm(){
	/* Contact Us Form */
	if( $("#contact-form").length ) {
				
		if(getQueryVariable("mailto")) {
			transTo('contact-form-container','-110');
			$("#contact_custom_recip").val(getQueryVariable("mailto"));
		}
		
		$("#contact-form").validate({
			submitHandler: function(form) {
				
				var _xhr = $.ajax({
					//url: 'contact.php',
					url: ROOT_URL + '/marketing/send-contact-inquiry',
					type: 'POST',
					data: {
						name: $("#contact_name").val(),
						email: $("#contact_email").val(),
						phone: $("#contact_phone").val(),
						comments: $("#contact_comments").val(),
						custom_recip: $("#contact_custom_recip").val()
					},
					success: function(data){
						//console.log(data);
						var _return = JSON.parse(data);
						_return_type = _return.result;
						_return_text = _return.message;
						
						if(_return_type != "success"){
							_html = "<div class='para'>Sorry, but our system is acting up right now.</div><div class='para'>Please contact PrivCo support directly for assistance at: <a href=\"mailto:support@privco.com\">support@privco.com</a>.</div>";
							$("#cu-trigger").stmModal({
								format: "hbar",
								trigger: "click",
								type: "error",
								colorscheme: "error",
								heading: "Oh no...",
								message: _html,
								button: "OK"
							});
							var _fauxClick = setTimeout(function(){
								$("#cu-trigger").click().off();
								clearTimeout(_fauxClick);
							}, 10);
						}
						else {
							_html = "<div class='para'>"+_return_text+"</div><div class='para'>Someone will follow up with you shortly.</div>";
							$("#cu-trigger").stmModal({
								format: "hbar",
								trigger: "click",
								type: "success",
								colorscheme: "success",
								heading: "Message Sent!",
								message: _html,
								button: "OK"
							});
							var _fauxClick = setTimeout(function(){
								$("#cu-trigger").click().off();
								clearTimeout(_fauxClick);
								$("#contact-form input, #contact-form textarea, #contact-form button").attr("disabled","disabled").off();
							}, 10);											
						}
						return false;
					}
				})
				.fail(function(){
						_html = "<div class='para'>Sorry, but we couldn't send your message.</div><div class='para'>Please contact PrivCo support directly.</div>";
						$("#cu-trigger").stmModal({
							format: "hbar",
							trigger: "click",
							type: "error",
							colorscheme: "error",
							heading: "Oh no...",
							message: _html,
							button: "OK"
						});
						var _fauxClick = setTimeout(function(){
							$("#cu-trigger").click().off();
							clearTimeout(_fauxClick);
						}, 10);						
				});	

				return false;
			},
			rules: {
				contact_name: {
					required: true
				},
				contact_email: {
					required: true,
					email: true
				},
				contact_phone: {
					required: true,
					digits: true
				},
				contact_comments: {
					required: true
				}
			},
			messages: {
				contact_name: { required: "Please enter your name." },
				contact_email: { required: "Please enter your email address.", email: "Please enter a valid email address." },
				contact_phone: { required: "Please enter your phone number.", digits: "Please enter digits only." },
				contact_comments: { required: "Please enter your message so that we can respond appropriately." }
			}
		});		
	}
}

function loadRecentResearch(){
	if (_rr_loaded) { return false; }
	var _xhr = $.ajax({
		//url: 'contact.php',
		url: ROOT_URL + '/marketing/get-recent-research/'+_num_research_posts,
		type: 'GET',
		data: {
			name: $("#contact_name").val(),
			email: $("#contact_email").val(),
			phone: $("#contact_phone").val(),
			comments: $("#contact_comments").val(),
			custom_recip: $("#contact_custom_recip").val()
		},
		success: function(data){
			//console.log(data);
			//recent_research = data;
			recent_research = JSON.parse(data);
					
			_code = "";
			for(r=0; r<recent_research.length; r++){
				//console.log(recent_research[r]);
				if( recent_research[r]["thumbnail_url"] ) _img = recent_research[r]["thumbnail_url"]; else _img = "//placehold.it/350/9B9B9B/FFFFFF?text=PrivCo&txtsize=50";
				
				
				// uniform thumbnail image protocols
				//console.log(_img);
				if( CURRENT_PROTOCOL == "https:" ) ALT_PROTOCOL = "http:";
				else ALT_PROTOCOL = "https:";

				if ( _img.indexOf(CURRENT_PROTOCOL) == -1 )
					_img = _img.replace(ALT_PROTOCOL, CURRENT_PROTOCOL);
				
				_code+= '\
					<div class="col-md-4 box h centered rr-article" style="display:none">\
						<img class="img-responsive img-rounded" alt="" src="'+_img+'" height="360" />\
						<h3>'+recent_research[r]["title"]+'</h3>\
						<p class="centered"><a target="_blank" href="'+ROOT_URL+'/'+recent_research[r]["url_slug"]+'" class="btn btn-blue">Read More</a>\
						</p>\
					</div>\
				';
			}
			$("#recent-research").html(_code);
			$('div.rr-article').each(function(i) {
			  $(this).fadeOut(0).delay(500*i).fadeIn(500);
			});	
			
		}
	})
	.fail(function(){
			console.log("fail");
	});	
		
	_rr_loaded = true;
}

function loadDatabaseMetrics(){
	var _xhr = $.ajax({
		//url: 'contact.php',
		url: ROOT_URL + '/marketing/get-database-metrics',
		type: 'GET',
		data: { },
		success: function(data){
			dbmetrics = JSON.parse(data);
			$.each(dbmetrics, function(index, value) {
				if( $("#databaseMetrics .counter[data-counter-id='"+index+"']").length ){
					$("#databaseMetrics .counter[data-counter-id='"+index+"']").html(value);
				}
			});
			
			/* Counter on the homepage */
			if( $('.counter').length ) {
				$('.counter').counterUp({
					time: 1000
				});
			}		
		}
	})
	.fail(function(){
			console.log("fail");
	});
}

function _initSetup(){
	checkScrollPosition(document.body.scrollTop);
	killAutoScroll();
	
	/* Fix for weird mobile menu behavior (doesn't collapse after option is clicked) */
	
	/*
	$("#primaryNav a:not('.dropdown-toggle')").on("click", function(){
		$(".navbar-default .navbar-toggle .icon-bar").click();
	});	
	*/
	
	/* setup primary nav */
	if( $("#primaryNavContainer").length ) {
		//console.log("Setup navigation.");
		/*
		$("#primaryNavContainer").html("").load("./primaryNav.html",function(){ 
			if( _page_identifier == "homepage") {
				$(".homepage-menu").removeClass("hide");
				$(".subpage-menu").addClass("hide");
			}
			else {
				$(".homepage-menu").addClass("hide");
				$(".subpage-menu").removeClass("hide");				
			}
			console.log("Navigation loaded."); 
			setupLoginCheck();
		});
		*/
		
		setupLoginCheck();

		/* Waypoints for Header Nav */
		var headWP1 = new Waypoint({
			element: document.getElementById('privco-body'),
			handler: function() {
				$("#top").addClass("has-bg").removeClass("no-bg");
				$(".back-to-top").fadeIn(200, function(){
					manageToggleMenus();
				});
			},
			offset: -50
		});
		
		var headWP2 = new Waypoint({
			element: document.getElementById('privco-body'),
			handler: function() {				
				checkAndToggleLogos();
			},
			offset: -50
		});		
		
		var headWP3 = new Waypoint({
			element: document.getElementById('privco-body'),
			handler: function() {
				$("#top").addClass("no-bg").removeClass("has-bg");
				$(".back-to-top").fadeOut(200, function(){
					manageToggleMenus();
				});
				checkAndToggleLogos();
			},
			offset: -5
		});
	}
	
	$(window).on("resize",function(){ manageToggleMenus(); });	

	/* Login form */
	if( $("#login-form").length ){
		//$(this).attr("action",_login_protocol);	
		$(this).attr("action",_HTTPS_LOGIN);	
		$("#login-form").validate({
			submitHandler: function(form) {
				var isModal = $("#isModal").val();
				var _xhr = $.ajax({
					//url: _login_protocol,
					url: _HTTPS_LOGIN,
					type: 'POST',
					crossDomain: true,
					dataType: 'json',
					/*
					dataType: 'jsonp',
					jsonp: 'jsonp',
					jsonpCallback: 'jsonLoginCB',
					*/					
					data: {
						//mbx: true,
						user_name: $("#username").val(),
						password: $("#password").val(),
						redirect: $("#redirect").val(),
						isAjax: $("#isAjax").val()
					},
					success: function(data){

						_logged_in = data.logged_in || false;
						_logged_in_username = data.username || false;

						if(_logged_in && _logged_in_username) {
							
							//setCookie("logged_in",_logged_in,1);
							//setCookie("logged_in_username",_logged_in_username,1);
							if( $("#getStartedBtn").length )
								$("#getStartedBtn, #getStartedPrefooter").fadeOut(500,function(){ 
									$(this).addClass("hide").removeAttr("style");
									if( isModal ) {
										$('#loginModal').modal('hide').on('hidden.bs.modal', function() {
											isUserLoggedIn();
										});
									}
									else top.location = "../.";
								});
							else {
								openDashboard();
								
							}
						}
						else {
							//console.log("returned error");
							loginErrorHandler(data);							
						}
					}
				})
				.fail(function(){
					console.log("fail");
				});			
			},
			rules: {
				user_name: {
					required: true
				},
				password: {
					required: true
				}
			},
			messages: {
				user_name: {
					required: "Please enter your username"
				},
				password: {
					required: "Please enter your password"
				}
			}
		});	
	}	
	
	
	/* Request a Demo modal - loading content */
	if( $('#demo').length ) {
		$("#request-a-demo-modal").html("<i class='fa fa-spinner fa-pulse'></i>");
		$('#demo').on('shown.bs.modal', function() {
			//$("#request-a-demo-modal").load("request-a-demo.html #rad-content", function(data){
			$("#request-a-demo-modal").load("marketing/request-a-demo #rad-content", function(data){
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
	
	/* Newsletter form */
	if( $("#newsletter-form").length ){
		$("#newsletter-form #email").removeAttr("placeholder").val("Enter your email address").on("focus",function(){
			if( $(this).val() == "Enter your email address" )
				$(this).val("");
		}).on("blur", function(){
			if( $(this).val() == "" ) {
				$(this).val("Enter your email address");
				if( $("#newsletter-form #email-error").length )
					$("#newsletter-form #email-error").remove();
			}
		});
	
		$("#newsletter-form").validate({
			submitHandler: function(form) {
				var _xhr = $.ajax({
					url: ROOT_URL+'/marketing/newsletter-subscription',
					type: 'POST',
					data: {
						email: $("#newsletter-form #email").val()
					},
					success: function(data){						
						//console.log(data);						
						_return = JSON.parse(data);
						//console.log(_return);						
						newsletterSubscriptionResultsHandler(_return.result, _return.result_code);
					}
				})
				.fail(function(){
					//console.log("fail");
					newsletterSubscriptionResultsHandler("error", "failed_saving");
				});			
			},
			rules: {
				email: {
					required: true,
					email: true
				}
			},
			messages: {
				email: {
					required: "Please enter your email address",
					email: "Please enter a valid email address"
				}
			}
		});
	}	
	
	
	// SETUP ALL OTHER PAGES
	
	switch(_page_identifier){
		case "homepage": setupHomepage(); break;
		case "pricing": setupPricingPage(); break;
		case "contact-us": setupContactForm(); break;
		case "login": setupLoginPage(); break;
		case "register": setupRegistrationPage(); break;
		case "press": setupPressPage(); break;
		case "request-a-demo": setupRequesADemoPage(); break;
	}	
}

function setupHomepage(){
	/* Who Relies on PrivCo Carousels */
	/* You're in Good Company Carousels */
	/* Waypoints for Lazyloading of client logos */

	if( $("#client-logos").length ) {
		total_carousel_items = client_logos.length;
		
		if( total_carousel_items < client_carousel_items ) {
			total_carousels = 1;
		}
		else {
			total_carousels = 3;
			client_carousel_items = Math.ceil( total_carousel_items / total_carousels ); // max rows = 3
		}
		
		_carousel_code = "";
		for(c=1; c<=total_carousels; c++){
			_carousel_id = "good-company-carousel"+c;
			_carousel_code += '<div id="'+_carousel_id+'" class="good-company-carousel owl-carousel">';
			for(i=0; i<client_carousel_items; i++){
				_sci = (c*client_carousel_items)-client_carousel_items+i;
				//console.log(_sci);
				
				if( client_logos[_sci] )
					_carousel_code += '<div data-attr-title="'+client_logos[_sci][1]+'" data-attr-href="'+client_logos[_sci][2]+'" class="box"><img class="img-responsive" alt="" src="'+client_logos[_sci][0]+'" width="200" height="200"></div>';
			}
			_carousel_code += '</div>';
		}
		
		$("#client-logos").html(_carousel_code);
		$(".good-company-carousel").owlCarousel({
			loop:true,
			items : 3, 
			responsive:{
				0:{
					items:1
				},
				600:{
					items:2
				},
				900:{
					items:3
				},
				1000:{
					items:3
				}
			},
			itemsMobile : false,
			autoplay:true,
			autoplayTimeout:5000,
			autoplayHoverPause:false,
			stopOnHover: false,
			animateOut: 'slideOutDown',
			animateIn: 'flipInX',
			lazyLoad : true,
			nav: false,
			slideBy: true,
			dots: false
		});
	}	
	
	if( $("#who-relies-carousel").length )
	$("#who-relies-carousel").owlCarousel({
		items : 3, //10 items above 1000px browser width
		responsive:{
			0:{
				items:1
			},
			600:{
				items:2
			},
			900:{
				items:3
			},
			1000:{
				items:3
			}
		},
		itemsMobile : false,
		autoplay:true,
		autoplayTimeout:5000,
		autoplayHoverPause:false,
		stopOnHover: true,
		//animateOut: 'slideOutDown',
		//animateIn: 'flipInX',
		lazyLoad : true,
		nav: false,
		slideBy: true,
		dots: false
	});	

	/* Name Search on Homepage */
	if( $("input#primary_search").length ) {
		$("input#primary_search").val("Enter Private Company, Advisor or Investor Name")
		.on("focus",function(){ 
			transTo('home-search-container','-75px');
			if( $(this).val() == "Enter Private Company, Advisor or Investor Name" )
				$(this).val(''); 
		}).on("mouseover",function(){
			$("#header-navigation-links li ul").removeAttr("style");//css("visibility","hidden");
		}).on("blur",function(){
			if( $(this).val() == "" ) 
				$(this).val("Enter Private Company, Advisor or Investor Name");
		});	
		setupNameSearch();	
	}
	
	if( $("#search-screener").length ) {
		$("#qs_location option.child-option").prepend("&nbsp;&nbsp;&mdash;&nbsp;&nbsp;");
		
	}	
	
	/* Research */
	if( $("#recent-research").length ){
		var rrWP = new Waypoint({
		  element: document.getElementById('newsletter-block'),
		  handler: function() {
			  loadRecentResearch();
		  },
		  offset: 'bottom-in-view'
		});		
	}
	
	// Revenue tooltip
	if( $(".show_qs_annual_revenues").length ) {
		//console.log("Revenues tooltip");
		
		var _tooltip_enterprise_phone = $('<span class="privco-tooltip-content">The <strong>Annual Revenues</strong> search criteria is available with a PrivCo Premium Subscription.<br /><br /><a href="'+_plan_comp_url+'" class="btn btn-blue btn-block">Check out Our Pricing</a></span>');
		//$('div#enterprise > div.pricing_header > span#no_id').tooltipster({
		//$('#enterprise').tooltipster({
		$(".show_qs_annual_revenues").tooltipster({
			position: 'top',
			trigger: 'hover',
			autoClose: true,
			interactive: true,
			contentAsHTML: true,
			animation: 'grow',
			delay: 200,
			maxWidth: 300,
			//theme: 'tooltipster-privco',
			content: _tooltip_enterprise_phone
		});
	}
	
	/* Counter */
	if( $("#databaseMetrics").length ) {
		loadDatabaseMetrics();		
	}
	
	if( $(".academic-access-info").length )
		$(".academic-access-info").on("click", function(){
			top.location = "./academic-access/";
		});
}


function setupPricingPage(){
	var _active_rate = $(".rate-cost.active").attr("data-rate-type");
	
	//console.log(_active_rate);
	$("#rate-cost, #rate-desc").attr("data-rate-type",_active_rate);
	var _active_rate_id = $(".rate-cost.active").attr("data-rate-id");
	$("#selectPlanTrigger").attr("data-active-rate",_active_rate_id);
	//console.log(_active_rate_id);
	
	$(".rate-cost").off().on("click",function(){
		var _new_rate = $(this).attr("data-rate-type");
		//console.log(_new_rate);
		$(".rate-cost").removeClass("active");
		$(this).addClass("active");
		$("#rate-cost, #rate-desc").attr("data-rate-type",_new_rate);
		var _new_rate_id = $(this).attr("data-rate-id");
		
		$("#selectPlanTrigger").attr("data-active-rate",_new_rate_id);
		
		$("#selectPlanTrigger").on("click", function(){
			var _href = $(this).attr("data-href");
			var _selected_plan = $(this).attr("data-active-rate");
			var _new_href = _href+'?subscription_type='+_selected_plan;
			/*
			console.log(_href);
			console.log(_selected_plan);
			console.log(_new_href);
			*/
			$(this).attr("href", _new_href);
			return true;
			
		});
		
	});
	
	
	$("#selectPlanTrigger").on("click", function(){
		var _href = $(this).attr("data-href");
		var _selected_plan = $(this).attr("data-active-rate");
		var _new_href = _href+'?subscription_type='+_selected_plan;
		/*
		console.log(_href);
		console.log(_selected_plan);
		console.log(_new_href);
		*/
		$(this).attr("href", _new_href);
		return true;
		
	});
	 
	 
	/* Yearly discount tooltip */
	var _tooltip_yearly_discount = $('<span class="privco-tooltip-content">58% Discount!</span>');
	$(".rate-cost[data-rate-type='yearly']").tooltipster({
		position: 'bottom',
		trigger: 'click',
		autoClose: true,
		interactive: true,
		contentAsHTML: true,
		animation: 'grow',
		delay: 200,
		maxWidth: 300,
		theme: 'tooltipster-privco',
		content: _tooltip_yearly_discount
	});
	$(".rate-cost[data-rate-type='yearly']").tooltipster('show');
	
	/* FAQs */	
	_faq_block = '<div class="row mb">\n<div class="col-md-12 centered"><h3>Click each question to view the answer.</h3></div></div>';
	qn = 1;
	for(q=0; q<faqs.length; q++){
		if(qn%3 == 1) _faq_block += '<div class="row">\n';
			_faq_trigger = "";
			_faq_id_trigger = faqs[q][0];
			
			if(_faq_id_trigger) _faq_trigger = ' id="'+_faq_id_trigger+'"';
			_faq_block += '\
				<div class="col-md-4">\
					<div class="faq-question"'+_faq_trigger+'><strong>'+faqs[q][1]+'</strong></div>\
					<div class="faq-answer">'+faqs[q][2]+'</div>\
				</div>\
			';
		if(qn%3 == 0) _faq_block += '</div>\n';
		qn++;
		
	}
	
	$("#faqs-container").html(_faq_block);
	
	$("#faqs-container .faq-question").on("click", function(){
		_faq_modal_content = $(this).parent().html();
		$("#faqModal #single-faq-content").html(_faq_modal_content);
		$("#faqModal").modal("show");
	});
	
	if( $("#quotasApply").length && $(".quotasApplyTrigger").length ) {
		$(".quotasApplyTrigger").on("click", function(){
			$("#quotasApply").trigger("click");
		});
	}
	
	/* Get Pricing Modal */
	if( $("#getPricing").length ) {
		if( $("#get-pricing-form").length) {
			
			$('#getPricing').on('hidden.bs.modal', function() {
				$('#getPricing label.error').remove();
			});
				
			$("#get-pricing-form").validate({
				submitHandler: function(form) {
					
					var _xhr = $.ajax({
						url: ROOT_URL+'/marketing/send-pricing-inquiry',
						type: 'POST',
						data: {
							name: $("#pricing_name").val(),
							email: $("#pricing_email").val(),
							phone: $("#pricing_phone").val(),
							message: $("#pricing_message").val()
						},
						success: function(data){
							//console.log(data);
							_response = JSON.parse(data);
							if(_response.result == "success"){
								
								//alert("success");
								_html = "<div class='para'>Your PrivCo Pricing Inquiry has been sent.</div><div class='para'>One of our awesome sales team members will be contacting you pretty soon.</div>";
								$("#gp-trigger").stmModal({
									format: "hbar",
									trigger: "click",
									type: "success",
									colorscheme: "success",
									heading: "All Done!",
									message: _html,
									button: "OK"
								});
								var _fauxClick = setTimeout(function(){
									$("#gp-trigger").click();
									$(".close-button").click();
									clearTimeout(_fauxClick);
								}, 10);	
							}
							else {
								_html = "<div class='para'>Sorry, but we couldn't send your request.</div><div class='para'>Please contact PrivCo sales directly: <a href=\"mailto:sales@privco.com\">sales@privco.com</a>.</div>";
								$("#gp-trigger").stmModal({
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
									$("#gp-trigger").click().off();
									clearTimeout(_fauxClick);
								}, 10);
							}
						}
					})
					.fail(function(){
						//console.log("fail");
						_html = "<div class='para'>Sorry, but we couldn't send your pricing inquiry.</div><div class='para'>Please contact PrivCo at <a href=\"mailto:sales@privco.com\">sales@privco.com</a> directly for assistance.</div>";
						$("#gp-trigger").stmModal({
							format: "hbar",
							trigger: "click",
							type: "error",
							colorscheme: "error",
							heading: "Oh no...",
							message: _html,
							button: "OK"
						});
						var _fauxClick = setTimeout(function(){
							$("#gp-trigger").click();
							clearTimeout(_fauxClick);
						}, 10);						
					});		
					
				},
				rules: {
					pricing_name: {
						required: true
					},
					pricing_email: {
						required: true,
						email: true
					},
					pricing_phone: {
						required: true,
						digits: true
					},
					pricing_message: {
						required: true
					}
				},
				messages: {
					pricing_name: {
						required: "Please enter your name"
					},
					pricing_email: {
						required: "Please enter your email address",
						email: "Please enter a valid email address"
					},
					pricing_phone: {
						required: "Please enter your phone number",
						email: "Please enter a valid telephone number (digits only)"
					},
					pricing_message: {
						required: "Please enter a message about your pricing request"
					}
				}
			});			
			
		}
	}
}

function loginErrorHandler(data){
	
	if(!data) { return; }

	_type = data.return_type;
	_cscheme = data.return_type;
	_heading = data.return_heading;
	_html = data.return_message;
	_redirect = data.redirect_url;
	_afterClose = _redirect ? "redirectTo" : "doNothing";

	$("#li-trigger").off().stmModal({
		format: "hbar",
		trigger: "click",
		type: _type,
		colorscheme: _cscheme,
		heading: _heading,
		message: _html,
		button: "OK",
		afterClose: _afterClose
	});
	var _fauxClick = setTimeout(function(){
		$("#li-trigger").click().unbind().off();
		clearTimeout(_fauxClick);
	}, 10);		
	
	
}

function manageToggleMenus(){
	if( !$(".navbar-toggle").is(":visible") && $("#primaryNavContainer").hasClass("in") ){
		$("#primaryNavContainer").removeClass("in");
	}	
}

function checkScrollPosition(cpos){
	$("#top").ready(function(){
		//console.log(cpos);
		if ( cpos >= 50 ) { 
			$("#top").removeClass("no-bg").addClass("has-bg"); 
			$(".back-to-top").fadeIn(10);
		}
		checkAndToggleLogos();
	});
}

function checkAndToggleLogos(){

	_logo_lg = $(".logo-lg").attr("data-orig-src");
	_logo_lg_inv = $(".logo-lg").attr("data-inv-src");
	_logo_sm = $(".logo-sm").attr("data-orig-src");
	_logo_sm_inv = $(".logo-sm").attr("data-inv-src");
	
	if( $("#top").hasClass("has-bg") ){
		//console.log("Inverting logos");
		if( _logo_lg_inv ) $(".logo-lg").attr("src", _logo_lg_inv);
		if( _logo_sm_inv ) $(".logo-sm").attr("src", _logo_sm_inv);
	}
	else{
		//console.log("Resetting logos");
		if( _logo_lg ) $(".logo-lg").attr("src", _logo_lg);
		if( _logo_sm ) $(".logo-sm").attr("src", _logo_sm);
	}	
	
}

function transTo(el,offset){
	if( !offset ) offset=0;
	$el = $("#"+el);
	if($el.length > 0) {
		var elpos = $el.position().top + parseInt(offset);
		//console.log("Found element: "+ el+" at: "+elpos);
		//console.log("elpos="+elpos);	
		$('html, body').animate({ scrollTop: elpos }, 500, "easeInOutQuint", function(){ 
			__page = location.pathname.substring(1);
			__full_page = ROOT_URL + "/" + location.pathname.substring(1);
			
			//console.log(__full_page);
			//history.replaceState(null, null, __page);
			history.replaceState(null, null, __full_page);
			return false;
		});		
	}
	else { return true; }	
}

function getQueryVariable(variable) {
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i=0;i<vars.length;i++) {
		var pair = vars[i].split("=");
		if(pair[0] == variable){return pair[1];}
	}
	return(false);
}

function killAutoScroll(){
	if( window.location.hash.length ) {
		//console.log("killAutoScroll()");
		$('html, body').animate({ scrollTop: 0 }, 0, "linear", function(){
			getURLHash(); 
			return false; 
		});		
	return false;	
	}
}

function getURLHash() {
	if( window.location.hash.length ) {
		//console.log("getURLHash()");
		var _el = window.location.hash.substring(1);
		setTimeout(function(){
			$(document).ready(function(){
				transTo(_el,'-75');
			})
		},150);
	return;
	}
	else { return false; }
}

function openDashboard(){
	h = $(window).innerHeight();
	w = $(window).innerWidth();
	//window.open("./dashboard", "_blank", "toolbar=no, scrollbars=yes, resizable=yes, top=0, left=0, width="+w+", height="+h);
	//var privCoDashboard = window.open("./dashboard", "privCoDashboard", "toolbar=no, scrollbars=yes, resizable=yes, top=0, left=0, width="+w+", height="+h);
	var privCoDashboard = window.open("./dashboard");
	top.location = "../.";
	return false;
}

function doNothing(){
	console.log("Fine. I won't do anything except print this message in the console.");
	return false;
}

function redirectTo(){
	console.log(_redirect);
}


function fauxSTMClick(element, options){
	
	var $el = $("#"+element);
	if( $el.length ) {
		_format = options["format"];
		_type = options["type"];
		_colorscheme = options["colorscheme"];
		_heading = options["heading"];
		_message = options["message"];
		_button = options["button"];
		_afterClose = options["afterClose"];
		
		$el.off().stmModal({
			format: _format,
			trigger: "click",
			type: _type,
			colorscheme: _colorscheme,
			heading: _heading,
			message: _message,
			button: _button,
			afterClose: _afterClose
		});
		var _fauxClick = setTimeout(function(){
			$el.click().unbind().off();//.remove();
			clearTimeout(_fauxClick);
		}, 10);		
	}
}

function setupLoginPage(){
	//console.log("fx setupLoginPage");
	setupLoginCheck();
	/*
	if(_logged_in) {
		$("#li-trigger").off().stmModal({
			format: "hbar",
			trigger: "click",
			type: "info",
			colorscheme: "info",
			heading: "You are already logged in!",
			message: "If you want to switch accounts, please <a href='javascript:triggerSignOut();'>Logout</a> first.",
			button: "OK",
			afterClose: "backToMarketingSiteHomepage"
		});
		var _fauxClick = setTimeout(function(){
			$("#li-trigger").click().unbind().off();
			clearTimeout(_fauxClick);
		}, 10);	
	}
	*/
}

function setupRegistrationPage(){
	setupLoginCheck();
	/*
	if(_logged_in) {
		$("#pr-trigger").off().stmModal({
			format: "hbar",
			trigger: "click",
			type: "info",
			colorscheme: "info",
			heading: "You are currently logged in!",
			message: "If you want to create a new account, please <a href='javascript:triggerSignOut();'>Logout</a> first.",
			button: "OK",
			afterClose: "backToMarketingSiteHomepage"
		});
		var _fauxClick = setTimeout(function(){
			$("#pr-trigger").click().unbind().off();
			clearTimeout(_fauxClick);
		}, 10);	
	}
	*/
}

// for system flipped to Zend
function alertUserAlreadyLoggedIn(action){
	switch(action){
		case "register":
			var _heading = "You are currently logged in!";
			var _message = "If you want to create a new account, please <a href='javascript:triggerSignOut();'>Logout</a> first.";
			var $trig = $("#pr-trigger");
			var _afterClose = "backToMarketingSiteHomepage";
			var _autoTrigger = true;
		break;
		case "login":
			var _heading = "You are already logged in!";
			var _message = "If you want to switch accounts, please <a href='javascript:triggerSignOut();'>Logout</a> first.";
			var $trig = $("#li-trigger");
			var _afterClose = "backToMarketingSiteHomepage";
			var _autoTrigger = true;
		break;	
		case "pricing":
			var _heading = "You already have a subscription!";
			var _message = "If you want to upgrade your current subscription, please <a href='"+_contact_us_url+"'>Contact Us</a>.";
			var $trig = $("#selectPlanTrigger");
			var _afterClose = "doNothing";
			var _autoTrigger = false;
		break;	
		case "default":
			var _heading = "You are currently logged in!";
			var _message = "If you want to switch accounts or register a new account, please <a href='javascript:triggerSignOut();'>Logout</a> first.";
			var $trig = $("#login_my_account");
			var _afterClose = "backToMarketingSiteHomepage";
			var _autoTrigger = true;
		break;
	}
	
	$trig.off().unbind().on("click",function(){
		return false;
	}).stmModal({
		format: "hbar",
		trigger: "click",
		type: "info",
		colorscheme: "info",
		heading: _heading,
		message: _message,
		button: "OK",
		afterClose: _afterClose
	});
	
	if(_autoTrigger)
	var _fauxClick = setTimeout(function(){
		$trig.click().unbind().off();
		clearTimeout(_fauxClick);
	}, 10);	
}

function setupLoginCheck(){
	
	/* Logged In? */
	/*
	_logged_in = getCookie("logged_in") || false;
	_logged_in_username = getCookie("logged_in_username") || null;
	isUserLoggedIn();	
	*/
	
	/* LOGOUT BUTTON (for full page login) */
	$("#signOut").off().on("click", function(){
		var _xhr = $.ajax({
			url: _logout_protocol,
			type: 'GET',
			dataType: 'json',
			data: {
				isAjax: 1
			},
			success: function(data){
				//_logged_in = data.logged_in;
				//_logged_in_username = data.username;
				//console.log("_logged_in="+_logged_in);
				//isUserLoggedIn();
				backToMarketingSiteHomepage();
			}
		})
		.fail(function(){
			console.log("fail");
		});
	});	
	
	/* LOGIN BUTTON (for full page login) */
	if( $("#loginPageTrigger").length ) {
		$("#loginPageTrigger").off().on("click", function(){
			top.location = _HTTPS_MKTG_LOGIN;// + ".html";
		});
		
	}
	
	/* FORCE SSL */
	if( _page_identifier == "login" && CURRENT_PROTOCOL != "https:" ) top.location = _HTTPS_MKTG_LOGIN;// + ".html";
}


function triggerSignOut(){
	$("#signOut").trigger("click");
	if( $(".stmm").length ) {
		$(".stmm").fadeOut(200, function(){ 
			$("body").removeClass("hasstmModal"); 
			$("#username").focus();
			$(this).remove();
		});
	}
}

function setupPressPage(){
	
	// first, filter by active year
	_active_timeline_year = $(".timeline-point.active").attr("data-timeline-year");
	// console.log(_active_timeline_year);
	
	// bind action to all timeline points
	$(".timeline-point").on("click", function(){
		$(".timeline-point").removeClass("active");
		$(".timeline-articles").hide();
		$(this).addClass("active");
		_active_timeline_year = $(this).attr("data-timeline-year");
		$(".timeline-articles[data-timeline-year='"+_active_timeline_year+"']").fadeIn(300);
	});
	
	
	if( $(".timeline-articles").length ){
		if( $(".single-article").length && $("#single-timeline-article-content").length ){
			
		// uniform heights
		_maxHeight = 0;
		 $(".single-article").each(function(){
		   _maxHeight = $(this).innerHeight() > _maxHeight ? $(this).innerHeight() : _maxHeight;
		});
		
		// $(".single-article").css("height",_maxHeight);
			
		// show active year
		$(".timeline-articles[data-timeline-year='"+_active_timeline_year+"']").fadeIn(300);
		
			$(".single-article").on("click", function(){
				if( $(this).attr("data-timeline-article") ) {
					_article_ref_url = ROOT_URL + "/marketing/press-articles/ #"+$(this).attr("data-timeline-article").toString();	
					//alert(_article_ref_url);
					
					$("#single-timeline-article-content").load(_article_ref_url, function(response, status, xhr ){
						//console.log(response);
						$("body").addClass("no-overflow");
						$(".back-to-top").addClass("hide");
						$("#pressModal").modal("show").on('hidden.bs.modal', function(){
							$("body").removeClass("no-overflow");
							$(".back-to-top").removeClass("hide");
						});
					});
				}
			});
		}
	}
	
}

function setupRequesADemoPage(){
	
	_ih = $(window).innerHeight();
	$("#demo-info").css("height",_ih);
	
	$(window).on("resize", function(){
		_ih = $(window).innerHeight();
		$("#demo-info").css("height",_ih);
	});
	
}