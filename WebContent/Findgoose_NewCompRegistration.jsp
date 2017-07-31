<!--   Author : @Diana
	   Copyright@ FindGoose
	   Date Created : 19th Sept, 2016
-->
<%@page import="com.FG.utils.MyTeam_DButil"%>
<%@page import="com.FG.user.UserRegistrationInfo"%>
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
  <title>FindGoose-New Company Registration</title>
  <link href="FindGoose_WelcomePageFiles/css/bootstrap.min.css" rel="stylesheet">
  <link href="FindGoose_WelcomePageFiles/css/FG_Welcome_style.css" rel="stylesheet">
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  
   <script type="text/javascript">
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
 
</script>

<script>
$(document).ready(function(){
	<%response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
		 response.addHeader("Pragma", "no-cache"); 
		 response.addDateHeader ("Expires", 0);
		if (session.getAttribute("user") == null) {
		session.setAttribute("LoginSeeker", request.getRequestURI());
		response.sendRedirect("Findgoose_Login.jsp");
	}%>
	
	$("#acceptTerms").click (function(){
		
		$('#agree_tos').prop('checked', true);
		checkAgrees();
	});
	
});
</script>
  
</head>

<body>
<%	UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");%>

<!-- Fixed navbar -signed in user-->
<nav class="navbar navbar-default navbar-fixed-top" id="mainNav">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class=" navbar-logo" href="FindGoose_Dashboard.jsp">
        <img src="FindGoose_WelcomePageFiles\images\logo_orange-welcome-0.png">
      </a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">    	
      <ul class="nav navbar-nav navbar-right">        
        <li class="active"><a href="FindGoose_WelcomePage.jsp"><i class="fa fa-home" aria-hidden="true"></i></a></li>
        <li class="dropdown active">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-bell"></i> Notifications <span class="caret"></span></a>
            <ul class="dropdown-menu dropdown-menu-left dropdown-menu-common">
              <li><a href="#">Notification-1</a></li>
              <li><a href="#">Notification-2</a></li>
              <li><a href="#">Notification-3</a></li>
              <li><a href="#">Notification-4</a></li>
              <li><a href="#">Notification-5</a></li>
            </ul>
         </li>
        <li class="dropdown active">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i>
            <% if (session.getAttribute("user") != null){ 
            	UserRegistrationInfo userName=(UserRegistrationInfo)session.getAttribute("user");%>
            	 <%=user.getUser_firstname()%> <% } %> 
            	 <span class="caret"></span></a>
             <ul class="dropdown-menu dropdown-menu-common">
              <li><a href="Findgoose_MyProfile.jsp"><i class="fa fa-user" aria-hidden="true"></i>My Profile</a></li>
<!--               <li><a href="#"><i class="fa fa-cog" aria-hidden="true"></i>Settings</a></li> -->
              <li role="separator" class="divider"></li>
              <li><a href="Findgoose_NewCompRegistration.jsp"><i class="fa fa-users" aria-hidden="true"></i>Register my company</a></li>
              <li role="separator" class="divider"></li>
              <li><a href="LogOut"><i class="fa fa-power-off" aria-hidden="true"></i>Sign out</a></li>

            </ul>
          </li>
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

<section class="testimonial-section">
    <div class="row">
      <div class="col-sm-8 col-sm-offset-2">
          <div class="carousel-inner" role="listbox">
              <div class="client-say">
					<form  name="register-form" id="form" name="form" method="post" action="RegisterCompanyServlet" 
					 onsubmit="
					 			if(document.getElementById('agree_tos').checked) 
					 			{ return true; } 
					 			else 
					 			{ alert('Please indicate that you have read and agree to the Terms and Conditions and Privacy Policy'); 
					 				return false; 
					 			}">
						<div class="form-group">
					        <h3 class="heading">New Company Registration</h3>
					        <p class="timeline">Enter credentials(*mandatory)</p>
							<%
				    		if(request.getAttribute("isRegistrationError") != null && request.getAttribute("isRegistrationError") == "true"){
							%>
				 			<p style="color:red"> <%=request.getAttribute("RegistrationError").toString()%>  </p>
							<%
							request.removeAttribute("isRegistrationError");
							request.removeAttribute("RegistrationError");
							}%>	
							<fieldset>
							<br>
							<input class="form-control" placeholder="*Enter Company name" type="text" name="companyName" id="companyName" required/>
							<br>
							<input class="form-control" placeholder="*Enter Company URL" type="text" name="companyURL" id="companyURL" required/>
							<br>
							<input class="form-control" placeholder="*Describe in few words" type="text" name="description" id="description" required/>
							<br>
							<select class="form-control" class="form-control" id="location" name="location" required>
								<option value="" selected="selected">*Select Location</option>
								<%List<Locations> locations = Locations.getListOfLocations();
								for(Locations location:locations){%> 
								  <option value="<%=location.getLocation() %>"><%=location.getLocation() %></option>
								<%}%>
							</select>
							<br>
							<input class="form-control" placeholder="Enter Company's Angel Link" type="text" name="angelLink" id="angelLink"/>
							<br>
							<input class="form-control" placeholder="Enter Company's Blog Link" type="text" name="blogLink" id="blogLink"/>
							<br>
							<input class="form-control" placeholder="Enter Company's Crunchbase Link" type="text" name="crunchbaseLink" id="crunchbaseLink" />
							<br>
							<input class="form-control" placeholder="Enter Company's Linkedin Link" type="text" name="linkedinLink" id="linkedinLink" />
							<br>
							<input class="form-control" placeholder="Enter Company's Twitter Link" type="text" name="twitterLink" id="twitterLink" />
							<br>
							<input class="form-control" placeholder="*Company Contact Email(Ex: eric20@gmail.com)" type="email" name="contactEmail" id="contactEmail" required/>
							<br>
							<select class="form-control" id="industryTypes" name="industryTypes" required>
								<option value="" selected="selected">*Select Industry type</option>
								<%List<IndustryTypes> industryTypesList = IndustryTypes.getListOfIndustryTypes();
								for(IndustryTypes type:industryTypesList){%> 
								  <option value="<%=type.getIndustryType()%>"><%=type.getIndustryType() %></option>
								<%}%>
							</select>
							<br>
							
							<div class="spacer"></div>	 
							<div class="form-group">
								<input class="type" type="checkbox" name="agree_tos" id="agree_tos" tabindex="8" onclick="checkAgrees();">
								<label for="agree_tos">I agree to the 
									<a href="Findgoose_TermsConditionsUserRegistration.html" onclick="window.open(this.href,location=no,directories=no,scrollbars=yes,titlebar=no,toolbar=no,status=no,menubar=no); return false;" target="_blank">Terms and Conditions</a>
								</label>
							</div>
							<span id="confirmMessage" class="confirmMessage"></span>
							<div class="modal-footer">
								<input class="btn-submit" type="submit" value="Register" id="registerButton" disabled/>
								<input class="btn-submit" type="button" value="Reset" onclick="this.form.reset();"/>
							</div>
							</fieldset>
							
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