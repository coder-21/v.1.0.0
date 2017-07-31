<!-- Note : Code updated for  
@Diana - 
 * Log in feature
 * User Registration feature
 * Retrieving IndustryTypes from DB
 * Retrieving Locations from DB
 
 @Suvarna - 
 *Visitor message feature
 *
 -->
<%@page import="com.FG.company.IndustryTypes"%>
<%@page import="java.util.List"%>
<%@page import="com.FG.company.Locations"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
 <link rel="icon" type="image/png" sizes="32x32" href="FindGoose_DashboardFiles/img/favicon-32x32.png">
  <title>FindGoose Home</title>
  <link href="FindGoose_WelcomePageFiles/css/bootstrap.min.css" rel="stylesheet">
  <link href="FindGoose_WelcomePageFiles/css/FG_Welcome_style.css" rel="stylesheet">
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  
  <script>
$(document).ready(function(){		
	$("#acceptTerms").click (function(){		
		$('#agree_tos').prop('checked', true);
		checkAgrees();
	});
});

function checkAgrees() {
	var termsMessage = document.getElementById('confirmMessage');
 if(document.getElementById('agree_tos').checked) {
	 document.getElementById('registerButton').disabled = false;
	 termsMessage.innerHTML="";
	}
 else{
	 termsMessage.style.color = "red";
	 termsMessage.innerHTML = "Please accept the terms and conditions!"
	 document.getElementById('registerButton').disabled = true;
	 }
 }

function checkPassConstraint() {
	var pass = document.getElementById('passwordSignup');
	var message = document.getElementById('passCheckMessage');
	if(/^[a-zA-Z0-9]/.test(pass.value) == false) {
		message.style.color = "red";
		message.innerHTML = "Password must not start with a special character.";
		document.getElementById('registerButton').disabled = true;
	}
	else if(pass.value.length<6|| pass.value.length>15){
		message.style.color = "red";
		message.innerHTML = "Password length must be between 6 and 15.";
		document.getElementById('registerButton').disabled = true;
	}
	else{
		message.innerHTML="";
		document.getElementById('registerButton').disabled = false;
	}	
}
</script>
  
</head>
<body>
<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top" id="mainNav">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
              <a class=" navbar-logo" href="FindGoose_WelcomePage.jsp">
          <img src="FindGoose_WelcomePageFiles\images\logo_orange-welcome-0.png">
        </a>
      <div class="navbar-show">
	    	<button class="btn btn-common" data-target="#loginmodal" data-toggle="modal">Log In</button>
	    	<button class="btn btn-common" data-target="#signupmodal" data-toggle="modal">Sign Up</button>
      </div>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
    	
      <ul class="nav navbar-nav navbar-right">
        <li class="active"><a href="FindGoose_WelcomePage.jsp"><i class="fa fa-home" aria-hidden="true"></i></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Company <span class="caret"></span></a>
          <ul class="dropdown-menu dropdown-menu-left dropdown-menu-common">
            <li><a title="About Us" href="FindGoose_AboutUs.jsp">About Us</a></li>
            <li><a title="Contact Us" href="FindGoose_AboutUs.jsp"><i class="fa fa-phone" aria-hidden="true"></i> Contact Us</a></li>
          </ul>
        </li>
<!--         <li class="dropdown"> -->
<!--           <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Product <span class="caret"></span></a> -->
<!--           <ul class="dropdown-menu dropdown-menu-left dropdown-menu-common"> -->
<!--             <li><a title="" href="#">Product-1</a></li> -->
<!--             <li><a title="" href="#">Product-2</a></li> -->
<!--             <li><a title="" href="#">Product-3</a></li> -->
<!--             <li><a title="" href="#">Product-4</a></li> -->
<!--           </ul> -->
<!--         </li> -->
        <li><button class="btn btn-common btn-desk" data-target="#loginmodal" data-toggle="modal">Log In</button></li>
        <li><button class="btn btn-common btn-desk" data-target="#signupmodal" data-toggle="modal">Sign Up</button></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>


<style type="text/css">
@media (max-width: 767px) { 
	.btn-desk{display: none;}
  .navbar-show {
    display: block !important;
  }
}
.navbar-show {
  display: none;
  position: absolute;
  z-index: 10;
  top: 14px;
  right: 80px;
}
.navbar-show button{
	border-radius: 2px;
	margin-left: 4px;
}
</style>



<!-- //login modal -->
<div class="modal modal-common fade" id="loginmodal" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Log In</h4>
      </div>
				<div class="modal-body">
				<form id="form" name="form" method="post" action="LoginServlet">
					<div class="form-group">
            			<input required type="email" name="email" id="email" class="form-control" placeholder="Enter User Email">
		          	</div>
		          	<div class="form-group">
		            	<input required type="password" name="password" id="password" class="form-control" placeholder="Enter Password">
		          	</div>
					<div class="modal-footer">
        				<button class="btn-submit" type="submit">Sign-in</button>
      				</div>
					
					
			 		<div id="redirects" class="modal-footer"> 
						<a href="Findgoose_forgotPassword.jsp">Forgot password??</a>
				     </div>
			 		<div align="right">
					</div>
				</form>
			</div>
    </div>
  </div>
</div>


<!-- //signup modal -->
<div class="modal modal-common fade" id="signupmodal" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Sign Up</h4>
      </div>
      <form  name="register-form" id="form" name="form" method="post" action="RegisterServlet">
      <div class="modal-body">
          <div class="form-group">
            <input type="text" name="firstName" id="firstName" class="form-control" placeholder="Enter First Name" required/>
          </div>
          <div class="form-group">
          	<input type="text" name="lastName" id="lastName" class="form-control" placeholder="Enter Last Name" required/>
          </div>
          <div class="form-group">
            <input type="email" class="form-control" placeholder="Enter Email" name="email" id="email" required/>
          </div>
          <div class="form-group"><span id="passCheckMessage" class="passCheckMessage"></span>
          	<input class="form-control" type="password" name="passwordSignup" id="passwordSignup" required onkeyup="checkPassConstraint(); return false;" placeholder="Password min. 6 chars"/>
          </div>
          <div class="form-group">
            <input type="text" class="form-control" placeholder="Enter Company" name="company" id="company" required/>
          </div>
          <div class="form-group">
          	<select class="form-control" id="industryTypes" name="industryTypes" required>
				<option value="" selected="selected">Select Industry</option>
				 <%List<IndustryTypes> types = IndustryTypes.getListOfIndustryTypes();
				for(IndustryTypes type:types){%> 
					  <option value="<%=type.getIndustryType()%>"><%=type.getIndustryType() %></option>
				<%}%>
			</select>
          </div>
          <div class="form-group">
          	<input type="radio" id="newsletter" name="newsletter" id="newsletter" value="yes" /> Subscribe.
          </div>
          <div class="form-group">
          	<input class="type" type="checkbox" name="agree_tos" id="agree_tos" tabindex="8" onclick="checkAgrees();">
          	<label for="agree_tos">I agree to the 
          	<a href="Findgoose_TermsConditionsUserRegistration.html" onclick="window.open(this.href,location=no,directories=no,scrollbars=yes,titlebar=no,toolbar=no,status=no,menubar=no); return false;" target="_blank">Terms and Conditions</a>
          	</label>
          </div>
          <div class="form-group"><span id="confirmMessage" class="confirmMessage"></span></div>
          <div class="form-group">
          	<input type="submit" value="Register" class="btn-submit" id="registerButton" disabled/></br>
        	<input class="btn-submit" type="button" value="Reset" onclick="this.form.reset();"/>
          </div>
      </div>
      </form>
    </div>
  	</div>
	</div>

<!-- //signup modal -->
<div class="modal modal-common fade" id="videomodal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="glyphicon glyphicon-facetime-video"></i> Virtual Video Tour</h4>
      </div>
      <div class="modal-body">
        <video width="100%" controls="" alt="" src="FindGoose_DashboardFiles/video/FindGooseHostingVideo.mp4"></video>
      </div>
    </div>
  </div>
</div>



<section class="top-section">
  <div class="container">
    <div class="row">
      <div class="col-sm-6">
        <div class="row myindicator">
          <div class="col-md-6">
            <div class="fourbox active" data-target="#myslider" data-slide-to="0">
              <div class="media">
                <div class="media-left media-middle">
                  <span class="imagebox imgbg1"></span>
                </div>
                <div class="media-body">
                  <h4 class="media-heading">Search</h4>
                  APIs enabling you to search and save the results as lists.
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="fourbox" data-target="#myslider" data-slide-to="3">
              <div class="media">
                <div class="media-left media-middle">
                  <span class="imagebox imgbg4"></span>
                </div>
                <div class="media-body">
                  <h4 class="media-heading">Track</h4>
                  Comparison APIs to enable for efficient decisions based on graphical charts.
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="fourbox" data-target="#myslider" data-slide-to="1">
              <div class="media">
                <div class="media-left media-middle">
                  <span class="imagebox imgbg2"></span>
                </div>
                <div class="media-body">
                  <h4 class="media-heading">Collaborate</h4>
                  APIs to collaborate Lists/Documents/Events within teams.
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="fourbox" data-target="#myslider" data-slide-to="2">
              <div class="media">
                <div class="media-left media-middle">
                  <span class="imagebox imgbg3"></span>
                </div>
                <div class="media-body">
                  <h4 class="media-heading">Interactive hub.</h4>
                  APIs to enable interaction between start-ups and investors.
                </div>
              </div>
            </div>
          </div>
          
        </div>
      </div>
      <div class="col-sm-6">
        <div id="myslider" class="carousel slide" data-ride="carousel">
          <ol class="carousel-indicators">
            <li data-target="#myslider" data-slide-to="0" class="active"></li>
            <li data-target="#myslider" data-slide-to="1"></li>
            <li data-target="#myslider" data-slide-to="2"></li>
            <li data-target="#myslider" data-slide-to="3"></li>
          </ol>  
          <div class="carousel-inner" role="listbox">
            <div class="item active">
              <img src="FindGoose_WelcomePageFiles/images/collaboration-1.jpg" alt="portfolio">
              <div class="carousel-caption">
                <button type="button" class="btn-submit" data-target="#videomodal" data-toggle="modal">
                  <i class="glyphicon glyphicon-facetime-video"></i>
                  Virtual Video Tour
                </button>
              </div>
            </div>
            <div class="item">
              <img src="FindGoose_WelcomePageFiles/images/search.jpg" alt="portfolio">
              <div class="carousel-caption">
                <button type="button" class="btn-submit" data-target="#videomodal" data-toggle="modal">
                  <i class="glyphicon glyphicon-facetime-video"></i>
                  Virtual Video Tour
                </button>
              </div>
            </div>
            <div class="item">
              <img src="FindGoose_WelcomePageFiles/images/slider1.jpg" alt="portfolio">
              <div class="carousel-caption">
                <button type="button" class="btn-submit" data-target="#videomodal" data-toggle="modal">
                  <i class="glyphicon glyphicon-facetime-video"></i>
                  Virtual Video Tour
                </button>
              </div>
            </div>
            <div class="item">
              <img src="FindGoose_WelcomePageFiles/images/slider3.jpg" alt="portfolio">
              <div class="carousel-caption">
                <button type="button" class="btn-submit" data-target="#videomodal" data-toggle="modal">
                  <i class="glyphicon glyphicon-facetime-video"></i>
                  Virtual Video Tour
                </button>
              </div>
            </div>
          </div>
          <!-- Controls -->
          <a class="left carousel-control" href="#myslider" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
          </a>
          <a class="right carousel-control" href="#myslider" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
          </a>
        </div>
      </div>
    </div>
  </div>
</section>

<section class="search-section">
  <div class="container">
    <div class="row">
      <div class="col-sm-12">
        <h3 class="heading"><!--Hub for ChitChat &amp; Food -->Search for Company/Investor
        </h3>
        <p class="timeline">
          Providing an Interactive/Management platform with information for company/investor research.
        </p>
      </div>
    </div>

    <div class="row">
      <div class="search-area">
        <div class="col-sm-6 col-sm-offset-4">
          <div class="input-group input-group-common">
          	<form id="searchTab" name="searchTab" method="post" action="BasicSearchResults">
	            <div class="input-group input-group-common">
					<input id="searchText" name="searchText" spellcheck="false"
						autocomplete="off" placeholder="Enter Company Name"
						class="form-control form-search" type="text">
					<span class="input-group-btn search-btn">
				        <button class="btn btn-default" type="submit">
				            <span class="glyphicon glyphicon-search"></span>
				        </button>
			        </span>
				</div>
            </form>
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="col-sm-12 mt15">
           <h4 class="text-center">Advanced Search</h4>
		   <h5 class="text-center">(Please <a href="Findgoose_Registration.jsp">SignUp</a> for advanced search listings)</h5>
        </div>
        <div class="col-sm-4">
          <select class="form-control form-search" id="locations">
			<option value="" selected="selected">Select Location</option>
			<%List<Locations> locations = Locations.getListOfLocations();
			for(Locations location:locations){%> 
				<option value="<%=location.getLocation()%>"><%=location.getLocation() %></option>
			<%}%>
          </select>
        </div>
        <div class="col-sm-4">
          <select class="form-control form-search" id="industryTypes">
			<option value="" selected="selected">Select Industry</option>
			<%List<IndustryTypes> industryTypesList = IndustryTypes.getListOfIndustryTypes();
			for(IndustryTypes type:industryTypesList){%> 
				<option value="<%=type.getIndustryType()%>"><%=type.getIndustryType() %></option>
			<%}%>
          </select>
        </div>
        <div class="col-sm-4">
          <div class="input-group input-group-common">
            <select class="form-control form-search" id="qs_annual_revenues" name="annual_revenues[]">
<!--               <select class="form-control show_qs_annual_revenues tooltipstered" id="qs_annual_revenues" name="annual_revenues[]"> -->
				<option value="" disabled="disabled" selected="selected">Revenue</option>
				<option value="">Any</option>
				<option value="1" disabled="disabled">&lt; $10,000,000</option>
				<option value="2" disabled="disabled">$10,000,000 - $100,000,000</option>
				<option value="3" disabled="disabled">$100,000,000 - $1,000,000,000</option>
				<option value="7" disabled="disabled">&gt; $1,000,000,000</option>
             </select>
            <span class="input-group-btn search-btn">
		    	<button  disabled title="SignUp for this free feature." class="btn btn-default" type="button">
		        	<span class="glyphicon glyphicon-search"></span>
		        </button>
		    </span>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
    </div>

  </div>
</section>


<section class="testimonial-section">
  <div class="container">
    <div class="row">
      <div class="col-sm-12">
         <h3 class="heading">What people are saying about us</h3>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-8 col-sm-offset-2">
        <div id="TestimonialSlider" class="carousel slide" data-ride="carousel">          
          <div class="carousel-inner" role="listbox">
            <div class="item active">
              <div class="client-say">
                <img src="FindGoose_WelcomePageFiles/images/client1.jpg" alt="">
                <h4>Anonymous User</h4>
                <p>
                  FindGoose is an excellent platform for collaborating a list of Companies with my team. As an enterprise analyst, this feature is what fascinates me.
                  <br>note: Temporary unreal testimony content.
                </p>
              </div>              
            </div>
<!--             <div class="item"> -->
<!--               <div class="client-say"> -->
<!--                 <img src="FindGoose_WelcomePageFiles/images/client2.jpg" alt=""> -->
<!--                 <h4>Merine Cene</h4> -->
<!--                 <p> -->
<!--                   Getting data from one place and then move to another account to collaborate it with the team was a tedious task prior to FindGoose. Happier now. Saves time! -->
<!--                 </p> -->
<!--               </div>        -->
<!--             </div> -->
<!--             <div class="item"> -->
<!--               <div class="client-say"> -->
<!--                 <img src="FindGoose_WelcomePageFiles/images/client3.jpg" alt=""> -->
<!--                 <h4>David Dav</h4> -->
<!--                 <p> -->
<!--                   Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod -->
<!--                   tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, -->
<!--                   quis nostrud exercitation. -->
<!--                 </p> -->
<!--               </div>        -->
<!--             </div> -->
          </div>
          <a class="left carousel-control" href="#TestimonialSlider" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
          </a>
          <a class="right carousel-control" href="#TestimonialSlider" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
          </a>
        </div>
      </div>
    </div>
  </div>
</section>



<section class="blog-section">
  <div class="container">
    <div class="row">
      <div class="col-sm-12">
        <h3 class="heading">Our Recent Blogs</h3>
      </div>
    </div>
    <div class="row">
      
      <div class="col-sm-4">
        <div class="thumbnail thumbnail-common">
          <img src="FindGoose_WelcomePageFiles/images/Blog1Thumb.jpg" alt="">
          <div class="caption">
            <h3>Why choose our Logo?</h3>
            <p>
               Too often in myth and lore we see the goose representing 
               silly attitudes or lazy dispositions.However, there's more to it.. 
            </p>
            <p><a href="https://7geese.com/7-lessons-we-can-learn-from-geese-to-succeed-at-work/" target="_blank" class="btn btn-sm btn-default active" role="button">Read More</a></p>
          </div>
        </div>
      </div>
      
      <div class="col-sm-4">
        <div class="thumbnail thumbnail-common">
          <img src="FindGoose_WelcomePageFiles/images/Blog1Thumb.jpg" alt="">
          <div class="caption">
            <h3>Why a find-'GOOSE'?</h3>
            <p>
               Too often in myth and lore we see the goose representing 
               silly attitudes or lazy dispositions.However, theres more to it..
            </p>
            <p><a href="http://www.whats-your-sign.com/animal-symbolism-goose.html" target="_blank" class="btn btn-sm btn-default active" role="button">Read More</a></p>
          </div>
        </div>
      </div>
      
       <div class="col-sm-4">
        <div class="thumbnail thumbnail-common">
          <img src="FindGoose_WelcomePageFiles/images/Blog1Thumb.jpg" alt="">
          <div class="caption">
            <h3>Welcome Page designing</h3>
            <p>
               Too often in myth and lore we see the goose representing 
               silly attitudes or lazy dispositions.However, theres more to it..
            </p>
            <p><a href="http://www.whats-your-sign.com/animal-symbolism-goose.html" target="_blank" class="btn btn-sm btn-default active" role="button">Read More</a></p>
          </div>
        </div>
      </div>
      
    </div>
  </div>
</section>



<footer class="footer">
  <div class="container">
    <div class="row">
      <div class="col-md-4 col-sm-12 mt15">
        
        <p>
          FindGoose is an intelligent interactive platform with smart search listings for decision making. 
          We are the enterprise platform for Investors, Startups, Development and Strategy teams.
        </p>
      </div>
      <div class="col-md-3 col-md-offset-1 col-sm-4 mt15">
        <h3>Company</h3>
        <ul>
          <li><a title="About Us" href="FindGoose_AboutUs.jsp">About Us</a></li>
          <li><a title="Contact Us" href="FindGoose_AboutUs.jsp"><i class="fa fa-phone" aria-hidden="true"></i> Contact Us</a></li>
        </ul>
      </div>
<!--       <div class="col-xs-6 col-md-2 col-sm-4 mt15"> -->
<!--         <h3>Product</h3> -->
<!--         <ul> -->
<!--           <li><a title="#" href="#">Product-1</a></li> -->
<!--           <li><a title="#" href="#">Product-2</a></li> -->
<!--           <li><a title="#" href="#">Product-3</a></li> -->
<!--           <li><a title="#" href="#">Product-4</a></li> -->
<!--         </ul> -->
<!--       </div> -->
      <div class="col-xs-6 col-md-2 col-sm-4 mt15">
        <h3>Social Links</h3>
        <a title="Facebook link in progress"  class="social-icons">
          <img src="FindGoose_WelcomePageFiles/images/facebook.png" alt="Facebook"></a>
        <a title="Twitter link in progress" class="social-icons">
        <img src="FindGoose_WelcomePageFiles/images/twitter.png" alt="Twitter"></a>
        <a title="Linkedin link in progress" class="social-icons">
        <img src="FindGoose_WelcomePageFiles/images/linkedin.png" alt="Linkedin"></a>
<!--         <a href="#" title="Instagram" class="social-icons"> -->
<!--         <img src="FindGoose_WelcomePageFiles/images/instagram.png" alt="Instagram"></a> -->
        <a href="https://angel.co/findgoose" title="AngelList" class="social-icons">
        <img src="FindGoose_WelcomePageFiles/images/angellist.png" alt="AngelList"></a>
      </div>
    </div>
  </div>
  <h6>
    Copyright FindGoose© 2016-2017. <a href="#">HomePage</a>
  </h6>
</footer>



<div class="leave-msg">

	<h3>Leave a Message</h3>
	<form action="VisitorMessageServlet" method="Post">
		<p>Sorry, we aren't online at the moment. Leave a message and we'll get back to you.</p>
		<input type="text" name="Name" placeholder="Name">
		<input type="email" name="Email" placeholder="Email">
		<textarea maxlength="150" name="Message" placeholder="Message 150 char*"></textarea>
		<button type="submit">Send</button>
		<% if(session.getAttribute("visitormessage")!=null) {
		session.removeAttribute("visitormessage");%>
		<h6 color="green">Your message has been sent </h6>
		<%} %>
		
	</form>
</div>


<!-- Placed at the end of the document so the pages load faster -->
<script src="FindGoose_WelcomePageFiles/js/jquery.min.js"></script>
<script src="FindGoose_WelcomePageFiles/js/bootstrap.min.js"></script>
<script src="FindGoose_WelcomePageFiles/js/common.js"></script>
</body>
</html>