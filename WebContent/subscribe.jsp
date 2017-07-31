<!--   Author : @Diana
	   Copyright@ FindGoose
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Launching Soon</title>
		<link rel="stylesheet" href="subscribe/style.css" />
		<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
		<script src="subscribe/functions.js" type="text/javascript"></script>
		<meta charset="utf-8" />
		<meta name="description" content="" />
		<meta name ="keywords" content="" />
		<script>	
		$(document).ready(function(){
			$("#countdown").countdown({
				date: "18 january 2013 12:00:00",
				format: "on"
			},
			
			function() {
				// callback function
			});
		});
	
		</script>
		
		<script type="text/javascript"> 
		function hgsubmit() 
		{ 
		if (/\S+/.test(document.hgmailer.name.value) == false) alert ("Please provide your name."); 
		else if (/^\S+@[a-z0-9_.-]+\.[a-z]{2,6}$/i.test(document.hgmailer.email.value) == false) alert ("A valid email address is required."); 
		 else if (/\S+/.test(document.hgmailer.comment.value) == false) alert ("Your email content is needed."); 
		  else { alert ("Thank you!\nYour email will be sent."); 
		       document.hgmailer.submit(); 
		       alert ("Thank you!\nYour email is sent."); 
		       } 
		} 
		</script> 
	</head>
	<body>

		<img src="subscribe/images/bg.jpg" alt="" class="bg"/>

		<section id="wrapper">
			
			<div id="cont">

				<!-- Logo Header -->
				<header>
					<a href="Subscribe"><img src="subscribe/images/logo.png" width="294" height="79" id="logo" alt="" /></a>
					<p>We're launching soon!</p>
					<div class="clear"></div>
				</header>
				<!-- end Logo Header -->


				<section id="box">
					<div class="arrow-up"></div>
					<div id="box-top"><h1></h1>
					<%
		    		if(request.getAttribute("isSubscriptionError") != null && request.getAttribute("isSubscriptionError") == "true"){
		    			request.removeAttribute("isSubscriptionError");
// 						request.removeAttribute("SubscriptionError");
					%>
			 			<h5 style="color:red"><%=request.getAttribute("SubscriptionError").toString()%>  </h5>
<!-- 			 			<p style="color:red"> </p> -->
					<%
					}%>
<!-- 						<h1>The day is coming!</h1> -->
						
						<!-- Signup -->
						<div id="form">
<!-- 							<form action="http://www.findgoose.com/cgi-sys/formmail.pl" method="post" name="hgmailer">  -->
							<form action="SubscribeToFG" method="post">
								<input type="text"  name="name" size="30" id="name" value="Your Name goes Here" onfocus="if(this.value=='Your Name goes Here') this.value='';"
									onblur="if(this.value=='') this.value='Your Name goes Here';"> <br><br>
								<input type="text"  name="email" size="30" id="email" value="Enter a Valid Email Here" onfocus="if(this.value=='Enter a Valid Email Here') this.value='';"
									onblur="if(this.value=='') this.value='Enter a Valid Email Here';" required> <br><br>
								<input type="text"  name="comment" id="comment" value="Comments??" onfocus="if(this.value=='Comments??') this.value='';"
									onblur="if(this.value=='') this.value='Comments??';">
<!-- 								<input type="hidden" name="recipient" value="info@findgoose.com">  -->
								<input type="hidden" name="subject" value="FindGoose"> 
								
								<input type="submit" id="submit" value="Notify Me">
								<br><br>
							</form>
						</div>
						<!-- END Signup -->
					</div>
					
						<!-- TIMER -->
						
						<div id="box-bottom">
						
							 
						
						</div>
						<!-- END Timer -->
					
				</section>
				
				<!-- FOOTER -->
				<div id="footer">
					<p class="note">2016 @ FindGoose</p>
					
				</div>
				<!-- End Footer -->

			</div>
		</section>			
	</body>
</html>