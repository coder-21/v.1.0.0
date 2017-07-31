<!--   Author : @Diana
	   Copyright@ FindGoose
	   Date Created : 20th Sept, 2016
-->
 
<%@page import="com.FG.utils.MyTeam_DButil"%>
<%@page import="com.FG.user.UserRegistrationInfo"%>
<%@page import="com.FG.company.IndustryTypes"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="icon" type="image/png" sizes="32x32" href="FindGoose_DashboardFiles/img/favicon-32x32.png">
  <title>FindGoose - Set New Password</title>
  <link href="FindGoose_WelcomePageFiles/css/bootstrap.min.css" rel="stylesheet">
  <link href="FindGoose_WelcomePageFiles/css/FG_Welcome_style.css" rel="stylesheet">
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  
  <script>
	$( document ).ready(function() {
    <%response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
		 response.addHeader("Pragma", "no-cache"); 
		 response.addDateHeader ("Expires", 0);
		if (session.getAttribute("user") == null) {
		session.setAttribute("LoginSeeker",request.getRequestURI());
		response.sendRedirect("Findgoose_Login.jsp");
	}
	%> 
	});
	</script>

<script type="text/javascript">
	 function checkPass()
	 {
	     var newpassword = document.getElementById('newpassword');
	     var confirmpassword = document.getElementById('confirmpassword');
	     var message = document.getElementById('confirmMessage');
	     if(newpassword.value == confirmpassword.value){
	    	 confirmpassword.style.backgroundColor = "green";
	         message.style.color = "green";
	         message.innerHTML = "Passwords Match!"
	         document.getElementById("setPwdButton").disabled = false;
	     }else{
	    	 document.getElementById("setPwdButton").disabled = true;
	    	 confirmpassword.style.backgroundColor = "red";
	         message.style.color = "red";
	         message.innerHTML = "Passwords Do Not Match!"
	     }
	 }  
	 
	 function checkPassConstraint() {
			var pass = document.getElementById('newpassword');
			var message = document.getElementById('passCheckMessage');
			if(/^[a-zA-Z0-9]/.test(pass.value) == false) {
				message.style.color = "red";
				message.innerHTML = "Password must not start with a special character.";
					document.getElementById('submitPwdButton').disabled = true;
			}
			else if(pass.value.length<6|| pass.value.length>15){
				message.style.color = "red";
				message.innerHTML = "Password length must be between 6 and 15.";
					document.getElementById('submitPwdButton').disabled = true;
			}
			else{
				message.innerHTML="";
			}				
		}
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
      <a class=" navbar-logo" href="FindGoose_WelcomePage.jsp">
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
					 <form id="form" name="form" method="post" action="SetForgotPasswordServlet">	
						<div class="form-group">
					        <h3 class="heading">Set New Password</h3>
		        			<p class="timeline">Please enter your New Password</p>
							<span id="passCheckMessage" class="passCheckMessage"></span>
							 <input class="form-control" placeholder="Enter New Password" type="password" name="newpassword" id="newpassword" required onkeyup="checkPass();checkPassConstraint(); return false;"/>
							 <br>
							 <input class="form-control" placeholder="Re-Enter Password" type="password" name="confirmpassword" id="confirmpassword" required onkeyup="checkPass(); return false;"/>
				            <span id="confirmMessage" class="confirmMessage"></span>
							 
							 <div class="modal-footer">
								<button class="btn-submit" type="submit" id="setPwdButton" name="setPwdButton" disabled>Set Password</button>
							</div>
							 <div class="spacer"></div>
							<%
				    		if(request.getAttribute("newPwdIsPrev2Pwds")!= null && request.getAttribute("newPwdIsPrev2Pwds") == "true"){
							%>
				 			<p style="color:red"> Previous 2 passwords are not accepted! </p>
							<%}%>
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