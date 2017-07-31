<!--   Author : @Diana
	   Copyright@ FindGoose
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>~~ Thank you! ~~</title>
		<link rel="stylesheet" href="subscribe/style.css" />
		<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
		<script src="subscribe/functions.js" type="text/javascript"></script>
		<meta charset="utf-8" />
		<meta name="description" content="" />
		<meta name ="keywords" content="" />
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
					<form>
					<div id="box-top">
						<h1>Thank you for your interest! </h1>
						<h1>We will contact you (<%=request.getAttribute("subscriberEmail") %>) on our launch.:)
						</h1>
						
					</div>
					</form>
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