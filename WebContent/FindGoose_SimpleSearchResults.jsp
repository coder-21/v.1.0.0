<!--   Author : @Diana
	   Copyright@ FindGoose
	   Date Created : 16th Sept, 2016
	   
	   updated for elasticsearch listings over lucene
-->
<%@page import="com.FG.company.IndustryTypes"%>
<%@page import="java.util.List"%>
<%@page import="com.FG.company.Locations"%>
<%-- <%@page import="com.FG.utils.SearchUtil"%> --%>
<%-- <%@page import="org.apache.lucene.search.IndexSearcher"%> --%>
<%-- <%@page import="org.apache.lucene.document.Document"%> --%>
<%@page import="com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud"%>
<%@page import="io.searchbox.client.JestClient" %>
<%@page import="com.FG.company.CompanyInfo" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="icon" type="image/png" sizes="32x32" href="FindGoose_DashboardFiles/img/favicon-32x32.png">
  <title>FindGoose Search Listings</title>
  <link href="FindGoose_WelcomePageFiles/css/bootstrap.min.css" rel="stylesheet">
  <link href="FindGoose_WelcomePageFiles/css/FG_Welcome_style.css" rel="stylesheet">
  
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  
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

<section class="testimonial-section">
		  <div class="container">		
		    <div class="row">
		      <div class="search-area-full">
		        <div class="col-sm-6 col-sm-offset-4">
		          <div class="input-group input-group-common">
			        <form id="searchTab" name="searchTab" method="post" action="BasicSearchResults">
						<div class="input-group input-group-common">
							<input id="searchText" name="searchText" spellcheck="false"
											autocomplete="off"
											placeholder="Enter Company Name"
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
		        
<!-- -----------Begin-Search Listing area---------------  -->
		        <div class="clearfix"></div>
		        <div class="col-sm-12 mt15">
			      <%if (request.getAttribute("SearchPerformed") != null && request.getAttribute("SearchPerformed") == "true") 
 			      {	request.removeAttribute("SearchPerformed");%>
						<div id="containment-wrapper" class="row maincontainer no-copy" style="display: block, align:center">
							<h3 class="heading">Search Results</h3>
							<div class="spacer"></div>
							<TABLE align="center" class="listTable" cellspacing="0" summary="Select companies to Save or Export">
							<TR>
								<TH scope="col">Name</TH>
								<TH scope="col">Location</TH>
								<TH scope="col">Industry</TH>
								<TH scope="col">HeadCount</TH>
								<TH scope="col">Revenue</TH>
								<TH scope="col">Investors</TH>
								<TH scope="col"> ... </TH>
							</TR>
							<%List<CompanyInfo> companyNames = (List<CompanyInfo>) request.getAttribute("companybasicsearchResults");
							for(int i=0;i<companyNames.size();i++)
							{%>
								<TR style="cursor: pointer;">
									<TD><%=companyNames.get(i).getName()%></td>
									<TD><%=companyNames.get(i).getLocation()%></td>
									<TD><%=companyNames.get(i).getIndustry()%></td>
									<TD colspan="4"> Accessible only for Registered users.</TD>
								</TR>
							<%}%>
							
							</TABLE>
						</div>
				  <%}else if(request.getAttribute("SearchReturnedNull") !=null && request.getAttribute("SearchReturnedNull") == "true")
				  {	request.removeAttribute("SearchReturnedNull");%>
						<div id="containment-wrapper" class="row maincontainer" style="display: block, align:center">
							<h3 class="heading">Search Results</h3>
							<div class="spacer"></div>
							<TABLE  align="center" cellspacing="0">
							<TR>
							<TD style="border:none">
								<p style="color:red">
									<b>Oops!</b> No results on this search. 
								</p>	<br>
								<p style="font-size:120%;">						
									<a href="Findgoose_NewCompRegistration.jsp" title="Invite others">Register</a> your company (or) yourself as an investor so we could help you invest.
								</p>
									Or
								<p style="font-size:120%;">
									<a href="InviteOthersServlet?InviteOthersClicked=true" title="Invite others">
									Invite the Company/Investor </a>to FindGoose :).
								</p>
							</TD>
							</TR>
							</TABLE>
						</div>
				  <%}%><!--   ENDS    if -else of (request.getAttribute("SearchPerformed") != null)-->
		        </div>
<!-- ----------- End-Search Listing area---------------- -->
		        <div class="clearfix"></div>
		        <div class="col-sm-12 mt15">
		          <h3 class="heading">Advanced Search</h3>
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
		        
		        
		        
		        
		      </div><!--ENDS - section search area -->
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