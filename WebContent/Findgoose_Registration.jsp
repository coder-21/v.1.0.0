<!--   Author : @Diana
	   Copyright@ FindGoose
	   Date Created : 19th Sept, 2016
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
  <title>FindGoose User Registration</title>
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
		$('#agree_tos2').prop('checked', true);
		checkAgrees();
	});
});

function checkAgrees() {
	var termsMessage = document.getElementById('confirmMessage');
	var termsMessage2 = document.getElementById('confirmMessage2');
 if(document.getElementById('agree_tos').checked) {
	 document.getElementById('registerButton').disabled = false;
	 termsMessage.innerHTML="";
	}
 else{
	 termsMessage.style.color = "red";
	 termsMessage.innerHTML = "Please accept the terms and conditions!"
	 document.getElementById('registerButton').disabled = true;
	 }
 if(document.getElementById('agree_tos2').checked) {
	 document.getElementById('registerButton2').disabled = false;
	 termsMessage2.innerHTML="";
	}
 else{
	 termsMessage2.style.color = "red";
	 termsMessage2.innerHTML = "Please accept the terms and conditions!"
	 document.getElementById('registerButton2').disabled = true;
	 }
 
 }
 
function checkNewsletter() {
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
<!-- Fixed navbar for non-signed in user-->
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
          	<input type="radio" id="newsletter" name="newsletter" id="newsletter" value="yes" checked="checked"/> Subscribe.
          </div>
          <div class="form-group">
          	<input class="type" type="checkbox" checked="checked" name="agree_tos" id="agree_tos" tabindex="8" onclick="checkAgrees();">
          	<label for="agree_tos">I agree to the 
          	<a href="Findgoose_TermsConditionsUserRegistration.html" onclick="window.open(this.href,location=no,directories=no,scrollbars=yes,titlebar=no,toolbar=no,status=no,menubar=no); return false;" target="_blank">Terms and Conditions</a>
          	</label>
          </div>
          <div class="form-group"><span id="confirmMessage" class="confirmMessage"></span></div>
          <div class="form-group">
          	<input type="submit" value="Register" class="btn-submit" id="registerButton" onclick="checkAgrees();"/>
        	<input class="btn-submit" type="button" value="Reset" onclick="this.form.reset();"/>
          </div>
      </div>
      </form>
    </div>
  	</div>
	</div>

<section class="testimonial-section">
<div class="row">
      <div class="col-sm-8 col-sm-offset-2">
          <div class="carousel-inner" role="listbox">
              <div class="client-say">
					 <form  name="register-form" id="form" name="form" method="post" action="RegisterServlet">
						<div class="form-group ">
					        <h3 class="heading">New User Registration</h3>
	        				<p class="timeline">Enter below credentials(*mandatory)</p>
							<%if(request.getAttribute("isRegistrationError") != null && request.getAttribute("isRegistrationError") == "true"){%>
				 				<p style="color:red"> <%=request.getAttribute("RegistrationError").toString()%>  </p>
							<%}%>	
							<fieldset>
								<input class="form-control" placeholder="*Enter your first name" name="firstName" id="firstName" required/>
								<br>
								<input class="form-control" placeholder="*Enter your Last name" name="lastName" id="lastName" required/>
								<br>
								<input class="form-control" placeholder="*Email/Username(Ex: eric20@gmail.com)" type="email" name="email" id="email" required/>
								<br>
								<span id="passCheckMessage" class="passCheckMessage"></span>
								<input class="form-control" placeholder="*Password min. 6 chars" type="password" name="passwordSignup" id="passwordSignup" required onkeyup="checkPassConstraint(); return false;"/>
								<br>
								<input  class="form-control" placeholder="*Enter your Company" type="text" name="company" id="company" required/>
								<br>
								<select class="form-control" id="industryTypes" name="industryTypes" required>
									<option value="" selected="selected">*Select Industry</option>
									<%List<IndustryTypes> types2 = IndustryTypes.getListOfIndustryTypes();
									for(IndustryTypes type:types2){%> 
									  <option value="<%=type.getIndustryType()%>"><%=type.getIndustryType() %></option>
									<%}%>
								</select>
								<br>
								<div class="spacer"></div>
								<div class="form-group">	 	 
									<input class="type" type="radio" id="newsletter" name="newsletter" id="newsletter" value="yes"/>	
									<label>Subscribe to our newsletter.</label>
								</div>
								<div class="form-group">
									<input class="type" type="checkbox" checked="checked" name="agree_tos2" id="agree_tos2" tabindex="8" onclick="checkAgrees();">
									<label for="agree_tos2">I agree to the 
										<a href="Findgoose_TermsConditionsUserRegistration.html" onclick="window.open(this.href,location=no,directories=no,scrollbars=yes,titlebar=no,toolbar=no,status=no,menubar=no); return false;" target="_blank">Terms and Conditions</a>
									</label>
								</div>
								<div class="form-group"><span id="confirmMessage2" class="confirmMessage2"></span></div>
<!-- 								<div class="modal-footer"> -->
<!-- 								</div> -->
							</fieldset>
								<div class="form-group">
									<input class="btn-submit" type="submit" value="Register" id="registerButton2" onclick="checkAgrees();"/>
									<input class="btn-submit" type="button" value="Reset" onclick="this.form.reset();"/>
								</div>
							<div class="spacer"></div>
						</div>
					 </form>	
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
      <div class="col-xs-6 col-md-2 col-sm-4 mt15">
        <h3>Social Links</h3>
        <a href="#" title="Facebook" class="social-icons">
          <img src="FindGoose_WelcomePageFiles/images/facebook.png" alt="Facebook"></a>
        <a href="#" title="Twitter" class="social-icons">
        <img src="FindGoose_WelcomePageFiles/images/twitter.png" alt="Twitter"></a>
        <a href="#" title="Linkedin" class="social-icons">
        <img src="FindGoose_WelcomePageFiles/images/linkedin.png" alt="Linkedin"></a>
<!--         <a href="#" title="Instagram" class="social-icons"> -->
<!--         <img src="FindGoose_WelcomePageFiles/images/instagram.png" alt="Instagram"></a> -->
        <a href="https://angel.co/findgoose" title="AngelList" class="social-icons">
        <img src="FindGoose_WelcomePageFiles/images/angellist.png" alt="AngelList"></a>
      </div>
    </div>
  </div>
        <h6>
           Copyright FindGoose© 2016-2017.
        </h6>
</footer>

<!-- Placed at the end of the document so the pages load faster -->
<script src="FindGoose_WelcomePageFiles/js/jquery.min.js"></script>
<script src="FindGoose_WelcomePageFiles/js/bootstrap.min.js"></script>
<script src="FindGoose_WelcomePageFiles/js/common.js"></script>
</body>
</html>