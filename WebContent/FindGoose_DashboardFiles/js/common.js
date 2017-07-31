$(document).ready(function() {
  $('.right-section-inner').on('scroll',function(){
    var scrollT = $(this).scrollTop();
    if (scrollT>10) {
      $('.navbar').addClass('header-shadow');
    }
    else{
      $('.navbar').removeClass('header-shadow');
    }
  });


	$(document).on('click','.minimize-menu', function(){
		$('.left-section, .minimize-menu, .right-section, .tour-video').toggleClass('small-menu');
	});


  // ========================
  $(document).on('dblclick','.panel-heading', function(){
    var checkclass = $(this).hasClass('box-expand');

    var getset = $(this).data('leftmenu');
    console.log(getset);

    $('.left-nav-list li').removeClass('active');
    
    if (checkclass==false) {
      $(this).addClass('box-expand');
      var getparent = $(this).parent().addClass('common');
      var superparent = $(this).parent().parent().addClass('col-full'); 
      $('#containment-wrapper').addClass('maincontainer');
      $('.left-section, .minimize-menu, .right-section, .tour-video').addClass('small-menu');
      $('#top3box').hide();
      $('[data-targetdashlate="'+getset+'"]').parent().addClass('active');
    }
    else{
      $(this).removeClass('box-expand');
      $('.panel-common').removeClass('common');
      $('.left-section, .minimize-menu, .right-section, .tour-video').removeClass('small-menu');
      $('#containment-wrapper .col-sm-6').removeClass('col-full');
      $('#containment-wrapper').removeClass('maincontainer');
      $('#top3box').show();
      $('.left-nav-list li:first-child').addClass('active');
    }
  });


  // =======================
  $('.panel-common .panel-body').on('dblclick', function(){
    $(this).attr('contenteditable', 'true');
  });


  //============================
  $(document).on('click dblclick', function(e) {
    if (!$(e.target).is('.panel-common .panel-body, .panel-heading, .panel-title, .minimize-menu, .box-tab li a, .panel-common .panel-body *')) {
      $('.panel-common .panel-body').removeAttr('contenteditable');
    }
  });


  // now click on left menu and show dashlet
  $(document).on('click', '.left-nav-list li a', function(event) {
    event.preventDefault();
    $('.left-nav-list li').removeClass('active');
    $(this).parent().addClass('active');
    $('[data-leftmenu]').parent().removeClass('common');
    $('[data-leftmenu]').parent().parent().removeClass('col-full'); 
    var checkclass2 = $(this).hasClass('dashboard');
    var checksearchDashboardClass = $(this).hasClass('searchdashboard');
    if (checkclass2==true) {
      var getData = $(this).data('targetdashlate');
      $('[data-leftmenu="'+getData+'"]').removeClass('box-expand');
      $('.panel-common').removeClass('common');
      $('.left-section, .minimize-menu, .right-section, .tour-video').removeClass('small-menu');
      $('#containment-wrapper .col-sm-6').removeClass('col-full');
      $('#containment-wrapper').removeClass('maincontainer');
      $('#top3box').show();
    }
    else{
		    var getData = $(this).data('targetdashlate');
		    $('[data-leftmenu="'+getData+'"]').addClass('box-expand');
			var getparent = $('[data-leftmenu="'+getData+'"]').parent().addClass('common');
			var superparent = $('[data-leftmenu="'+getData+'"]').parent().parent().addClass('col-full'); 
			$('#containment-wrapper').addClass('maincontainer');
			$('.left-section, .minimize-menu, .right-section, .tour-video').addClass('small-menu');
			$('#top3box').hide();
    }
    if(checksearchDashboardClass==true){
    	var url = "FindGoose_Dashboard.jsp";
		$(location).attr('href',url);
    }
  });
});


$( function() {
  $( "#containment-wrapper" ).sortable({
    revert: true,
    containment: "#containment-wrapper",
    scroll: true,
    helper: "clone",
    handle: ".panel-heading",
  });
});


(function($) {
  var $window = $(window);
  function resize() {
    if ($window.width() < 1040) {
      return $('.left-section, .minimize-menu, .right-section, .tour-video').addClass('small-menu');
    }
    $('.left-section, .minimize-menu, .right-section, .tour-video').removeClass('small-menu');
  }
  $window.resize(resize).trigger('resize');
  resize();
})(jQuery);