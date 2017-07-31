/*
DATE: 6/8/2015
AUTHOR: STM
VERSION: 1.0
WWW: shashimamitchell.com
DESC: Modal Plugin that works with multiple concurrent instances
*/

var supports = (function() {
   var div = document.createElement('div'),
      vendors = 'Khtml Ms O Moz Webkit'.split(' ');
 
   return function(prop) {
      if ( prop in div.style ) return true;
 
      prop = prop.replace(/^[a-z]/, function(val) {
         return val.toUpperCase();
      });
 
	  len = vendors.length;
      while(len--) {
         if ( vendors[len] + prop in div.style ) {
            // browser supports box-shadow. Do what you need.
            // Or use a bang (!) to test if the browser doesn't.
            return true;
         } 
      }
      return false;
   };
})();
if ( !supports('backgroundBlendMode') ) {
   document.documentElement.className += ' no-bg-blend';
}

(function ( $ ) {
 
    $.fn.stmModal = function( options ) {
	
		var plugin = this;
		var $element = $(this);		
		var _animation__speed = 500;
		var _animation__speed_wrapper = 250;
		
        // This is the easiest way to have default options.
        var settings = $.extend({
            // These are the defaults.
			overlay: "dark",
			format: "standard", // standard (icon, heading, main content, button), colloqial (large header with text, heading, main content)
			trigger: "click", // click, hover
            type: "info", // system-notice, warning, success, error, info, help
            colorscheme: "warning", //"#BF3726", will finish later....... ZZzzzz
			extraAttr: "",
			autoDismiss: false,
			autoDismissTimeout: 5000,
            heading: "Warning",
            message: "This is your warning message.<br /><br /><strong><u>It can take HTML in this parameter.<br /><br />Enjoy.",
            button: "Dismiss",
			afterClose: null,
			showCancel: false,
			afterCancel: null
        }, options );
		
		var methods = {
			init : function(options) {
				
				var _type;
				switch(settings.type){
					case "warning":
						_type = "exclamation-triangle";
					break;
					case "system-notice":
						_type = "exclamation-circle";
					break;
					case "info":
						_type = "info-circle";
					break;
					case "error":
						_type = "times-circle";
					break;
					case "help":
						_type = "question-circle";
					break;
					case "success":
						_type = "check-circle";
					break;
				}
				//$element.hide();	
				
				
				$("body").addClass("hasstmModal");
				var $wrapper = $( "<div class='stmm'></div>");//.appendTo("body");

				//if( $(".stmm").length == 0 ) $wrapper.appendTo( $("#append-here-only") );
				if( $(".stmm").length == 0 ) $("div#append-here-only").append( $wrapper );
				
				if( settings.overlay == "light" ) $wrapper.addClass("light");
				
				
				var _format;
				var $markup;
				
				var _cancelbtn = "";
				if(settings.showCancel) _cancelbtn = "<button class='cancel-request'>Cancel</button>";
				
				
				switch(settings.format){
					case "hbar":
						$markup = $("\
							<div class='stmm-markup hbar-frame "+ settings.colorscheme +" "+ settings.extraAttr + "' data-stmm-master='"+ $element.attr("id") +"'>\
								<div class='icon-block'><i class='stm-icon fa fa-"+ _type +"'></i></span></div>\
								<div class='message-block'>\
									<h1 class='heading'>"+ settings.heading +"</h1>\
									<div class='message'>"+ settings.message +"</div>\
									<div class='autodismisser'></div>\
								</div>\
								<div class='button-block'>\
									<button class='stmm-Dismiss'>"+ settings.button +"</button>\
									"+ _cancelbtn +"\
								</div>\
							</div>\
						");
					break;
					case "empty":
						$markup = $("\
							<div class='stmm-markup "+ settings.format +" "+ settings.colorscheme+" "+ settings.extraAttr + "' data-stmm-master='"+ $element.attr("id") +"'>\
							<div class='autodismisser'></div>\
								"+ settings.message +"\
							</div>\
						");
					break;
					case "colloquial":
						_cancelbtn = "<button class='cancel-request'>Cancel</button>";
						$markup = $("\
							<div class='stmm-markup "+ settings.format +" "+ settings.colorscheme+" "+ settings.extraAttr + "' data-stmm-master='"+ $element.attr("id") +"'>\
								<div class='heading-block'>\
									<h1 class='heading'></h1>\
									<h2 class='sub-heading'></h2>\
								</div>\
								<div class='message-block'>\
									<h3 class='heading'>"+ settings.heading +"</h3>\
									<div class='content'>"+ settings.message +"\
										<div class='button-block'>\
											<button class='process-request'>"+ settings.button +"</button>\
											"+ _cancelbtn +"\
										</div>\
									</div>\
								</div>\
							</div>\
						");
					break;
					case "standard":
					default:
						$markup = $("\
							<div class='stmm-markup "+ settings.colorscheme +" "+ settings.extraAttr + "' data-stmm-master='"+ $element.attr("id") +"'>\
								<div class='icon-block'><i class='stm-icon fa fa-"+ _type +"'></i></span></div>\
								<div class='message-block'>\
									<h1 class='heading'>"+ settings.heading +"</h1>\
									<div class='message'>"+ settings.message +"</div>\
									<div class='autodismisser'></div>\
								</div>\
								<div class='button-block'>\
									<button class='stmm-Dismiss'>"+ settings.button +"</button>\
									"+ _cancelbtn +"\
								</div>\
							</div>\
						");
					break;
				}
				
				
				
				


				$markup.appendTo(".stmm");
				//$markup.prependTo(".stmm");
				
				var _winO = $(window).scrollTop();
				var _winH = $(window).innerHeight();
				var _winW = $(window).innerWidth();
				var _stomH = $markup.innerHeight();
				var _stomW = $markup.innerWidth();
				var _stomT = ((_winH/2)-(_stomH/2));//+(_winO/2);
				var _stomL = ((_winW/2)-(_stomW/2));
				
				//console.log(_winO);
				
				if( settings.format != "hbar" )
					$markup.css({
						"left": _stomL+"px"
					});	
				

				$wrapper.fadeIn(50, function(){
					$markup.animate({
						"top": _stomT+"px",
						"opacity": 1,
					},_animation__speed, "easeInOutBounce", function(){
						$markup.attr("data-stmm-waypoint",_stomT);
					});
				});
				
				var $autodismisser = $(".autodismisser");
				if(settings.autoDismiss) {
					$autodismisser.animate({
						"width": "100%",
						"opacity": 1,
					},settings.autoDismissTimeout, "linear", function(){
						// done
						methods.dismiss($element.attr("id"));
					});
					/*
					var _autoDismiss = setTimeout(function(){
						clearTimeout(_autoDismiss);
						methods.dismiss($element.attr("id"));
					}, settings.autoDismissTimeout);
					*/
				}
				else {
					$autodismisser.remove();
				}
				
				/*
				$(window).on("keyup", function(e){
					if (e.keyCode == 27) methods.dismiss($element.attr("id"));
				});
				$wrapper.on("click", function(e){
					 if (e.target !== this) return;
					methods.dismiss($element.attr("id"));
				});
				
				var $master_dismiss_btn = $wrapper.find(".stmm-markup[data-stmm-master='"+$element.attr("id")+"'] button.stmm-Dismiss");
				
				$master_dismiss_btn.on("click", function(){
					alert("clicked dismiss button");
					return;
					methods.dismiss($element);
				});
				*/
				var _master;
				$("button.stmm-Dismiss").on("click", function(){
					//_master = $(this).parent().parent().attr("data-stmm-master");
					_master = $(this).closest("section").attr("data-stmm-master");
					_master = $(this).parent().parent().attr("data-stmm-master") || $(this).parent().parent().parent().parent().attr("data-stmm-master");
					methods.dismiss( _master );
				});
				
				$("button.cancel-request").on("click", function(){
					_master = $(this).parent().parent().attr("data-stmm-master") || $(this).parent().parent().parent().parent().attr("data-stmm-master");
					//_master = $(this).closest("section").attr("data-stmm-master");
					//console.log( "_master="+_master );
					methods.cancel( _master );
				});
				
				$(window).on("resize", function(){
					_master = $(".stmm-markup").attr("data-stmm-master");
					//console.log( "_master="+_master );
					methods.centerInView( _master );
				});
				
			
			},
			centerInView: function( master ){
				var $wrapper = $(".stmm");
				var $markup = $wrapper.find(".stmm-markup[data-stmm-master='"+master+"']");
				var $markup = $wrapper.find(".stmm-markup");
					
				_winH = $(window).innerHeight();
				_winW = $(window).innerWidth();
				_stomH = $markup.innerHeight();
				_stomW = $markup.innerWidth();
				_stomT = (_winH/2)-(_stomH/2);
				_stomL = (_winW/2)-(_stomW/2);
				
				//console.log( _stomL );

				if( settings.format == "hbar" ) _stomL=0;

				$markup.animate({
					"left": _stomL+"px",
					"top": _stomT+"px",
					"opacity": 1,
				},1, "easeInOutBounce", function(){
					$markup.attr("data-stmm-waypoint",_stomT);
				});
				
				
			},
			dismiss : function( master ) {
					//console.log("1");
					var $wrapper = $(".stmm");
					//var $markup = $(".stmm-markup");
					//console.log(master);
					
					var $markup = $wrapper.find(".stmm-markup[data-stmm-master='"+master+"']");
					//var _waypoint = $markup.attr("data-stmm-waypoint");
					//console.log($markup);
					
					var totalModals = $(".stmm-markup").length;
					var _waypoint = $markup.height();
					//console.log($markup);
					//console.log("2");
					$markup.animate({
						"top": "-"+_waypoint+"px",
						"opacity": 0,
					},{
						duration: _animation__speed, 
						easing: "easeInOutCirc", 
						start: function(){
							
						},
						step: function(now, fx){
							//console.log(totalModals);

							if(totalModals <= 1) 

								$wrapper.fadeOut(_animation__speed_wrapper, function(){
									$(this).remove();
									$("body").removeClass("hasstmModal");
									//$element.show();	
								});
						},
						complete: function(now, fx){
							$(this).remove();
							totalModals = $(".stmm-markup").length;

							/*
							if(totalModals <= 0) 
								$wrapper.fadeOut(_animation__speed_wrapper, function(){
									$(this).remove();
									$("body").removeClass("hasstmModal");
									//$element.show();	
								});	
							*/
							
							if( settings.afterClose ) {
								window[settings.afterClose]();
							}
							
						}
					});					
					
					
			},
			cancel: function( master ){	

				var $wrapper = $(".stmm");
					//var $markup = $(".stmm-markup");
					//console.log($wrapper);
					
					var $markup = $wrapper.find(".stmm-markup[data-stmm-master='"+master+"']");
					//var _waypoint = $markup.attr("data-stmm-waypoint");
					//console.log($markup);
					
					var totalModals = $(".stmm-markup").length;
					var _waypoint = $markup.height();



				
					$markup.animate({
						"top": "-"+_waypoint+"px",
						"opacity": 0,
					},{
						duration: _animation__speed, 
						easing: "easeInOutCirc", 
						start: function(){

						},
						step: function(now, fx){

							if(totalModals <= 1) 

								$wrapper.fadeOut(_animation__speed_wrapper, function(){
									$(this).remove();
									$("body").removeClass("hasstmModal");
							
								});
						},
						complete: function(){
							$(this).remove();
							totalModals = $(".stmm-markup").length;

							
							if( settings.afterCancel ) {
								window[settings.afterCancel]();
							}
						}
					});	
					
					return false;
			}
		};

		$element.on(settings.trigger, function(){
			//console.log( settings.trigger );
			methods.init();
			//alert( $element.attr("id") );
		});
 
    };
 
}( jQuery ));