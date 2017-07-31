<!--   Author : @Suvarna
	   Copyright@ FindGoose
	   Date Created : 28th Sept, 2016
	   
	   Industry Type listing : @Diana
 -->
 
<%@page import="com.FG.utils.MyTeam_DButil"%>
<%@page import="com.FG.user.UserRegistrationInfo"%>
<%@page import="com.FG.company.IndustryTypes"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html style=""
	class=" js flexbox canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths"
	lang="">
<head>
<title>FindGoose-My Profile</title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="robots" content="index,follow">
<meta name="msvalidate.01" content="F10159E9FCD41394C6C28E716C02D47B">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="shortcut icon"
	href="icons/findgoose_iconSmall.ico">
<link rel="icon" type="image/png" sizes="32x32" href="FindGoose_DashboardFiles/img/favicon-32x32.png">

  <link href="FindGoose_DashboardFiles/js/common.js" rel="stylesheet">
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <link href="FindGoose_WelcomePageFiles/css/bootstrap.min.css" rel="stylesheet">
  <link href="FindGoose_WelcomePageFiles/css/FG_Welcome_style.css" rel="stylesheet">
<!--   <link href="FindGoose_DashboardFiles/css/bootstrap.min.css" rel="stylesheet"> -->
<!--   <link href="FindGoose_DashboardFiles/css/FG_Dashboard_style.css" rel="stylesheet"> -->

<style>
label{
	text-align: right;
    clear: both;
    float:left;
    margin-right:15px;
}
input {

    float: left;
}
</style>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-latest.pack.js"></script>
	<script src="js/1_12_0_jquery.min.js"></script>

	
<script>

function checkPass() {
		var newpassword = document.getElementById('newPwd');
		var confirmpassword = document.getElementById('confirmPwd');
		var pasMessage = document.getElementById('confirmMessage');
		if (newpassword.value == confirmpassword.value) {
			
			pasMessage.style.color = "green";
			pasMessage.innerHTML = "Passwords Match!"
			document.getElementById('submitPwdButton').disabled = false;
		} else {
			pasMessage.style.color = "red";
			pasMessage.innerHTML = "Passwords Do Not Match!"
				document.getElementById('submitPwdButton').disabled= true;
		}
	}
	
function resetPass(){
	
}
	</script>
	
<script type="text/javascript">
	var USER_IS_SUBSCRIBER = false;

	var USER_IS_SUBSCRIBER = false;


	function checkPassConstraint() {
		var pass = document.getElementById('newPwd');
		var message = document.getElementById('passCheckMessage');
		if (/^[a-zA-Z0-9]/.test(pass.value) == false) {
			message.style.color = "red";
			message.innerHTML = "Password must not start with a special character.";
			document.getElementById('registerButton').disabled = true;
		} else if (pass.value.length<6|| pass.value.length>15) {
			message.style.color = "red";
			message.innerHTML = "Password length must be between 6 and 15.";
			document.getElementById('registerButton').disabled = true;
		} else {
			message.innerHTML = "";
			document.getElementById('registerButton').disabled = false;
		}
	}
	
</script>
<script>
$(window).load(function(){
	$("#resetform").hide();
	 });
	 
	$(document).ready(function() {
<%response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
		 response.addHeader("Pragma", "no-cache"); 
		 response.addDateHeader ("Expires", 0);
		if (session.getAttribute("user") == null) {
		session.setAttribute("LoginSeeker", request.getRequestURI());%>
		window.location.replace("Findgoose_Login.jsp");
	<%}%>
			
	
			
			$('form').each(function(){
		        $(this).data('serialized', $(this).serialize())
		    })
		    .on('change input', function(){
		        $(this)             
		            .find('input:submit, button:submit')
		                .prop('disabled', $(this).serialize() == $(this).data('serialized'))
		        ;
		     })
		    .find('input:submit, button:submit')
		        .prop('disabled', true);
	
	});
	
	


</script>
<style type="text/css" __jx__id="___$_2" class="jx_ui_StyleSheet"
	media="print">
.zopim {
	display: none !important
}
.logoutLblPos {
	position: fixed;
	top: 11px;
	right: 2px;
	width: 80px;
	line-height: 1.42857143;
	border: 1px solid transparent;
	padding: 6px 1px;
	border-radius: 4px;
	font-weight: bold;
	text-align: center;
	vertical-align: middle;
}
</style>
<iframe style="display: none;" id="cloudiq_hidden_frame"></iframe>
<link rel="icon" href="icons/findgoose_iconSmall.ico">
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

<div class="modal modal-common fade" id="changePwdModal" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Change Password</h4>
      </div>
				<div class="modal-body">
				<form  name="changePwd-form" id="form" name="form" method="post" action="ResetPwdServlet">
			          <div class="form-group">
			            <input type="password"name="currPwd" id="currPwd" class="form-control" placeholder="Enter current password" required/>
			          </div>
			          <div class="form-group">
			          <span	id="passCheckMessage" class="form-control-passCheckMessage"></span>
			          	<input type="password" name="newPwd" id="newPwd" class="form-control"onkeyup="checkPassConstraint();return false;"  placeholder="Enter new password" required/>
			          </div>
			          <div class="form-group">
			          <span id="confirmMessage" class="confirmMessage"></span>
			            <input type="password"	name="confirmPwd"class="form-control" id="confirmPwd" onkeyup="checkPass();return false;" placeholder="Confirm new password"/>
			          </div>

      				<div class="modal-footer">
		          	<input type="submit" value="Change" class="btn-submit" id="submitPwdButton" disabled/><br>
		        	<input class="btn-submit" type="button" value="Reset" onclick="this.form.reset();"/>
		            </div>
			 		<div align="right">
					</div>
				</form>
			</div>
    </div>
  </div>
</div>




		<!-- THIS IS THE BEGINNING OF PRIMARY CONTENT AREA -->

<section class="testimonial-section">
		<div class="container">
  <div class="row">
      <div class="col-sm-8 col-sm-offset-2">
          <div class="carousel-inner" role="listbox">
              <div class="client-say">

				<form name="profileChange-form" id="form" name="form" method="post"
					action="MyProfileServlet">
					<div class="form-group">
					        <h3 class="heading">User Profile</h3>
		        			<p class="timeline">General account settings</p>
							
					<%
						if (request.getAttribute("ProfileChanged") != null
								&& request.getAttribute("ProfileChanged") == "true") {
							request.removeAttribute("ProfileChanged");
					%>
					<div class="spacer"></div>
					<p style="color: green">
					Profile updated successfully
											</p>
					<%
						}
					%>
					<%
										if (request.getAttribute("ResetPwdResult") != null
													&& request.getAttribute("ResetPwdResult") == "true") {
											request.removeAttribute("ResetPwdResult");
									%>
									<center>
										<p style="color: blue">Password has been changed
											successfully.</p>
									</center>
									<%
										} else
												if (request.getAttribute("ResetPwdResult") != null
														&& request.getAttribute("ResetPwdResult") == "false") {
													request.removeAttribute("ResetPwdResult");
									%>
									<center>
										<p style="color: red">Password reset failed. Please try
											again</p>
									</center>
									<%
										} else
													if (request.getAttribute("authenticateResult") != null
															&& request.getAttribute("authenticateResult") == "false") {
														request.removeAttribute("authenticateResult");
									%>
									<center>
										<p style="color: red">Current password entered is
											incorrect</p>
									</center>
																	
									<%
										} else
												if (request.getAttribute("PrevPassResult") != null
														&& request.getAttribute("PrevPassResult") == "false") {
													request.removeAttribute("PrevPassResult");
									%>
									<center>
										<p style="color: red">Previous two passwords are not
											permitted</p>
									</center>
									<%
										}
									%>
					<%
						UserRegistrationInfo userObj = (UserRegistrationInfo) session.getAttribute("user");
						if (userObj != null) { %>
						<fieldset>
						<label for="firstName">First Name </label> <input type="text" name="firstName"
							id="firstName" class="form-control"value=<%=userObj.getUser_firstname()%>
							required="required" /> <label >Last Name </label> <input
							type="text" name="lastName" id="lastName" class="form-control"
							value="<%=userObj.getUser_lastname()%>" required /> <label>Email/Username
						</label> <input type="email" name="email" id="email" class="form-control"
							value="<%=userObj.getUser_email()%>" readonly required /> 
						<div id="pwdDiv" class="pwdDiv">
							<label>Password 
							</label> <input type="password" name="password" id="password"
								value="*********" readonly class="form-control" />
							
								<input type="button" data-target="#changePwdModal" data-toggle="modal" value="Reset Password"/>
								<div class="spacer"></div>
									
						</div>
					<label>Company
						</label> <input type="text" name="company" id="company"class="form-control"
							value=<%=userObj.getUser_company()%> required /> <label>User
							Type</label><input type="text" name="userType" id="userType"class="form-control"
							value="<%=userObj.getUser_type()%>" readonly required />
						<!-- 						<button type="submit" value="Submit" class="submit" -->
						<!-- 							id="UpgradeButton" disabled>Upgrade</button> -->
						<label>Interests
			 	<span class="small">Select from industry types</span> 
			 </label>
			<select class="form-control" id="industryTypes" name="industryTypes">
						<option value="<%=userObj.getUser_industries() %>" selected="selected"><%=userObj.getUser_industries() %></option>
						 <%List<IndustryTypes> types = IndustryTypes.getListOfIndustryTypes();
						for(IndustryTypes type:types){%> 
						  <option value="<%=type.getIndustryType()%>"><%=type.getIndustryType() %></option>
						<%}%>
			</select>
			
			<div class="spacer"></div>
						<div class="spacer"></div>



						<span id="confirmMessage" class="confirmMessage"></span>
						<button type="submit" value="Submit" class="submit"
							id="changeButton" enabled>Save Changes</button>
						<button class="reset" type="reset">Cancel</button>
					</fieldset>
					<%}
					%>

					<div class="spacer"></div>

				</form>
			</div>

</div>
</div>

		</div>
		</div>
</section>
		<!-- THIS IS THE END OF THE PRIMARY CONTENT AREA -->

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
        <img src="FindGoose_WelcomePageFiles/images/angellist.png" alt="AngelList"></a>      </div>
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