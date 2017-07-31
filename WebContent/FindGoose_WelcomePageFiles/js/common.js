
<!-- //find scroll width -->
$(document).ready(function(){
  $('.modal').on('show.bs.modal', function (e) {
    var swidth=(window.innerWidth-$(window).width());
    $('#mainNav').css('width','calc(100% - ' +swidth+'px');
    $('.modal').css('width','calc(100% + ' +swidth+'px');
  });
  $('.modal').on('hidden.bs.modal', function (e) {
    var swidth=(window.innerWidth-$(window).width());
    $('#mainNav').css('width','100%');
    $('.modal').css('width','auto');
  });
}); 


// ---- Initialize number to each modal
$(document).ready(function(){
  console.log("hello world");
  $('#myslider').on('slid.bs.carousel', function () {
      $holder = $(".myindicator .fourbox.active");
      $holder.removeClass('active');
      var idx = $('.carousel-indicators li.active').data('slide-to');
      $('.myindicator .fourbox[data-slide-to="'+idx+'"]').addClass('active');
  });
  $('.myindicator .fourbox').on("click",function(){ 
      $('.myindicator .fourbox.active').removeClass("active");
      $(this).addClass("active");
  });
});



// leave message click
$(document).on('click', '.leave-msg h3', function() {
  $('.leave-msg').toggleClass('leavetxt');
});