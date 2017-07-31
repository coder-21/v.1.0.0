<!-- Note : Code updated for  
@Diana - 
 * Logged in user Check
 * LoggedIn Redirect
 * User/Company Registration exceptions display
 * My Lists Integration
	 * Selected List export
	 * CreateLists
	 * SaveLists
	 * ExportList
	 * RetrieveUserLists
	 * Implement UserLists Remove
	 * Implement UserLists Share with team members
	 * Validate Number of Lists per user
	 * Validate Number of TeamMembers to share a list to 2.
	 * Implement Task comment while List shared
 	 * Events
 	 * My Documents
	 * Basic Search Listing
	 * Company Profile Tab(retrieve Company info)
	 * Retrieve Company Employees(Company Team Tab)
 	 * Retrieve Company Investments info(Company Investments Tab
 	 
 	 *Migration from Lucene documents retrieval to Elasticsearch listings retrieval.
 
 
 @Suvarna - 
  *Imports packages for findGoose sources added
 *Invite others integrated
 *My Team Integrated
 	*Add team member
 	*Deleted Team member
 	*View team
 	*Accept team requests
 	*Delete Requests

 *My Task Integrated
 	*Create Task
 	*Assign Task
 	*View assigned and my tasks
 	*Change status of tasks
 *News & Interests
 	*View News
 	*Redirect to original news page
 	//*show the company profile
 * Contacts Imported
 	*Create Contact
 	*View contacts
 	*Delete Contacts
 	*Invite contact to FG	
 *Refresh dashlets on reload
 -->
<%@page import="com.FG.company.InvestmentInfo4ESIndexing"%>
<%@page import="com.FG.investor.InvestorInfo"%>
<%@page import="com.FG.elasticsearch4AmazonCloud.ElasticsearchUtil_4AmazonCloud"%>
<%@page import="com.FG.company.CompanyProfileService"%>
<%@page import="com.FG.user.SaveUserDocumentService"%>
<%@page import="com.FG.events.EventsService"%>
<%@page import="com.FG.user.Servlets.ShareUserListServlet"%>
<%@page import="com.FG.user.Servlets.DashboardServlet"%>
<%@page import="com.FG.utils.UserList_DBUtil"%>
<%@page import="com.FG.user.SaveUserListService"%>
<%@page import="com.FG.user.UserList"%>
<%@page import="com.FG.user.UserDocument"%>
<%-- <%@page import="com.FG.utils.SearchUtil"%> --%>
<%@page import="java.util.List"%>
<%-- <%@page import="org.apache.lucene.search.IndexSearcher"%> --%>
<%-- <%@page import="org.apache.lucene.document.Document"%> --%>
<%@page import="com.FG.user.UserRegistrationInfo"%>
<%@page import="com.FG.user.UserContactsInfo"%>
<%@page import="com.FG.company.CompanyEmployeesInfo"%>
<%@page import="com.FG.utils.UserList_DBUtil"%>
<%@page import="com.FG.company.Locations"%>
<%@page import="com.FG.events.EventsEnrolled"%>
<%@page import="java.util.Date" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.FG.user.TasksTableInfo"%>
<%@page import="com.FG.utils.MyTeam_DButil" %>
<%@page import="com.FG.user.UserTeamTableInfo" %>

<%@page import="com.FG.company.CompanyInfo" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,width=device-width,user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="icon" type="image/png" sizes="32x32" href="FindGoose_DashboardFiles/img/favicon-32x32.png">
  <title>Dashboard</title>
  <link href="FindGoose_DashboardFiles/css/bootstrap.min.css" rel="stylesheet">
  <link href="FindGoose_DashboardFiles/css/FG_Dashboard_style.css" rel="stylesheet">
  <link href="FindGoose_DashboardFiles/js/common.js" rel="stylesheet">
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  
<script src="js/1_12_0_jquery.min.js"></script>
<!-- <script src="js/3_3_6_bootstrap.min.js"></script> -->
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.pack.js"></script> -->
<!-- OAuth related scripts -->	
<script src="oauth-js-master/dist/oauth.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<!-- Google contact Api scripts -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://apis.google.com/js/client.js"></script>
<script type="text/javascript" src="http://platform.linkedin.com/in.js">
    api_key: 757ub8hsmjviqu;
    authorize: true    
</script>
<script type="text/javascript">
	$(document).ready(function() 
	{
		<%response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
		 response.addHeader("Pragma", "no-cache"); 
		 response.addDateHeader ("Expires", 0);
		if (session.getAttribute("user") == null) {
			session.setAttribute("LoginSeeker", request.getRequestURI());
			response.sendRedirect("Findgoose_Login.jsp");
		}%>
		var queryArr = [];
		$("tr td input[type=checkbox]").bind("click",function() 
		{
			var $this = $(this);
			if ($this.is(':checked')) 
			{
				$this.parents('tr').css({'background-color' : 'lightyellow'});
				var $row = $this.closest('tr');
				$row.css('background-color','lightyellow');
				var t = $(this).closest('tr').find('td:eq(1)').text();
				queryArr.push(t);
				//$this.parents('tr input').siblings('input type=[text]').attr("disabled", true);
			} else {
				$this.parents('tr').css({'background-color' : 'white'});
				var t = $(this).closest('tr').find('td:eq(1)').text();
				Array.prototype.remove = function(v) 
				{this.splice(this.indexOf(v) == -1 ? this.length: this.indexOf(v),1);}
				queryArr.remove(t);
			}
			var test = $('input[name="hiddenSelectedCompanies4Export"]').val(queryArr);
			var test = $('input[name="hiddenSelectedCompanies4SaveAsList"]').val(queryArr);
		});

		$(".checkbox1").prop('checked', $(this).prop("checked"));
		
		$('a[href="#listSelected"]').click(function() {//var contentId = $(this).text().substr(0,1);
			var contentId = $(this).attr('id');
			$('div[id*="_CompaniesOfSelectedLists"]').hide();
			$("."+ contentId+ "_CompaniesOfSelectedLists").show();
		});
		
		$('a[href="#shareListId"]').click(function() {//var contentId = $(this).text().substr(0,1);
			var contentId = $(this).attr('id');
			$('div[id*="_ShareLimit"]').hide();
			$("."+ contentId+ "_ShareLimit").show();
		});
						
		$('a[href="#shareDocumentId"]').click(function() {//var contentId = $(this).text().substr(0,1);
			var contentId = $(this).attr('id');
			$('div[id*="_ShareDocumentLimit"]').hide();
			$("."+ contentId+ "_ShareDocumentLimit").show();
		});
						
		$('#ListsTable tr').click(function() 
		{
			var $test = $(this).find('td').map(function() 
											{
												return $(this).text();
											}).get().join(" ");
			var $listId = $(this).find('td.listId').text();
			var $listCompanies = $(this).find('td.companies').text();
			$('#hiddenListIdLabel').text($listId);
			$('#hiddenListIdInput').text($listId);
			$('#hiddenListIdCompaniesInput').val($listCompanies);
		});

		$("li a").click(function() 
		{
			var $t = $(this).closest('tr').find('td:eq(0)').text();//Changed to fix ShareList Modal Data
			$('#shareListInputId').val($t);
			var $listName = $(this).siblings('.listNameInput').val();
			$('#selectedListIdOnModal').val($listName);
		});
		
		$('.confirmation').on('click', function() {return confirm('Are you sure you want to delete?');});
			
		//Begin - handles the dynamical division change on radio click section - @Diana//
		$('#teamRadio').click(function(){
			$('#teamMembersDropdownEvents').removeAttr("disabled");
			$('#friendEmail').attr("disabled","disabled");
			$('#friendRadio').removeAttr("checked");
			var friendEmailText = document.getElementById("friendEmail");
			friendEmailText.value = "";
		});
    					
		$('#friendRadio').click(function(){
			$('#friendEmail').removeAttr("disabled");
			var x=document.getElementById("teamMembersDropdownEvents");
          	x.disabled=true;
			$("#teamMembersDropdownEvents").val("");
			$('#teamRadio').removeAttr("checked");
		});
		
		//end - handles the dynamical division change on radio click section - @Diana//
	});
</script>
<!-- @Suvarna: OAuth google contacts api integration -->
<script type="text/javascript">
function auth() {
	  var config = {
	    'client_id': '352062871560-ovu3p3bn3ns03ejfdck5i8je2m38d6dp.apps.googleusercontent.com',
	    'scope': 'https://www.google.com/m8/feeds'
	  };
	  gapi.auth.authorize(config, function() {
	    fetch(gapi.auth.getToken());  
	   
	  });
	}
function fetch(token) {
    $.ajax({
    	url: "https://www.google.com/m8/feeds/contacts/default/full?access_token=" + token.access_token + "&alt=json",
	    dataType: "jsonp",
	    success:function(jsonData) {
                  console.log(JSON.stringify(jsonData));
	              $.ajax({
	      	        url: 'AddContacts',
	      	        type: 'POST',
	     	        data: { json: JSON.stringify(jsonData)},
	      	        dataType: 'json',
	      	        success:function(){
	      	        	console.log("ajax function success");
	      	        }
	      	    });
	    } });
  }
</script>

<!-- @Suvarna: OAuth initialization using findgoose key -->
<script type="text/javascript">
function oauthInitialize(){
	OAuth.initialize('qkZ5HyF5YO7uVy2LfTmxmbvF4Co');
	}
	
function googleAuth(){
	OAuth.popup('google')
    .done(function(result) {
      //use result.access_token in your API request 
      //or use result.get|post|put|del|patch|me methods (see below)
    })
    .fail(function (err) {
      //handle error with err
});
}
function linkedInAuth(){
	OAuth.popup('linkedin', {cache: true})
    .done(function(result) {
    	// Here you will get access token
    	var linkedin = OAuth.create('linkedin');
        console.log(result)
          result.me().done(function(linkedin) {
              // Here you will get basic profile details of user
              console.log(linkedin); 
              console.log(linkedin.name);
    })
    .fail(function (err) {
      //handle error with err
});
    });
}
</script>
</head>
<body>
<%	UserRegistrationInfo user = (UserRegistrationInfo) request.getSession().getAttribute("user");
	MyTeam_DButil myTeam_DButil = new MyTeam_DButil();
	
	if(user!=null&& request.getSession().getAttribute("InitialLoad")==null){
	%>
<jsp:forward page="/LoginServlet"/>
<%}
	else if(request.getSession().getAttribute("InitialLoad")!=null){
		request.getSession().removeAttribute("InitialLoad");
	}%>
<section class="top-header">
  <nav class="navbar navbar-default navbar-static-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand navbar-logo" href="FindGoose_Dashboard.jsp">
          <img src="FindGoose_DashboardFiles/img/logo_orange-0.png">
        </a>
        
        <form class="navbar-form navbar-left navform-mobile" id="searchTab" name="searchTab" method="post" action="BasicSearchResults">
			<div class="form-group">
				<input style="position: relative; vertical-align: top;" 
								id="searchText" name="searchText" spellcheck="false"
								autocomplete="off"
								placeholder="Search for company,investor or keyword"
								class="form-control" type="text">
<!-- 				<input type="submit" class="fa fa-search"/> -->
				<button type="submit" class="fa fa-search"></button>
			</div>
		</form>
        
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
        	<li>
        		<button title ="Feature in Progress" class="btn btn-warning btn-onhead" disabled>Upgrade</button>
        	</li>
          <li title="Feature in progress" class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-bell"></i> Notifications <span class="caret"></span></a>
            <ul title="Feature in progress" class="dropdown-menu dropdown-menu-left dropdown-menu-common">
              <li title="Feature in progress"><a >Notification-1</a></li>
              <li title="Feature in progress"><a >Notification-2</a></li>
              <li title="Feature in progress"><a >Notification-3</a></li>
            </ul>
          </li>
          <li class="dropdown">
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
        <form class="navbar-form navbar-left navform-common navform-desk" id="searchTab" name="searchTab" method="post" action="BasicSearchResults">
			<div class="form-group">
				<input style="position: relative; vertical-align: top;" 
								id="searchText" name="searchText" spellcheck="false"
								autocomplete="off"
								placeholder="Search for company,investor or keyword"
								class="form-control" type="text">
				 <button type="submit" class="fa fa-search"></button>
			</div>
		</form>
      </div><!--/.nav-collapse -->
    </div>
  </nav>
</section>

<section class="wrapper">
  <div class="left-section">
  <%if((request.getAttribute("SearchPerformed")!=null && request.getAttribute("SearchPerformed").toString().matches("true"))
		  || (request.getAttribute("SearchReturnedNull")!=null && request.getAttribute("SearchReturnedNull").toString().matches("true"))
		  || (request.getAttribute("CompanyProfileSelected")!=null && request.getAttribute("CompanyProfileSelected").toString().matches("true"))
		  || (request.getAttribute("InvestorProfileSelected")!=null && request.getAttribute("InvestorProfileSelected").toString().matches("true"))) 
   {%> 
  	<ul class="left-nav-list">	  
	  <li class="active">
	      <a href="#" class="searchdashboard">
	      <i class="fa fa-television"></i>
	      	<span>Back to Dashboard</span></a>
      </li>
	  <li class="list-noteffect">
	    <div class="tour-video" data-target="#videomodal" data-toggle="modal">
	      <strong class="fa fa-video-camera" aria-hidden="true"></strong>
	      <p>Virtual Tour Video</p>
	  	</div>
	  </li>
    </ul>
  <%}else{ %>
    <ul class="left-nav-list">
      <li class="active">
        <a href="#" class="dashboard">
          <i class="fa fa-television"></i> <span>Dashboard</span>
        </a>
      </li>
      <li>
        <a href="#" data-targetdashlate="myteam">
          <i class="fa fa-user"></i> <span>My Team</span>
        </a>
      </li>
      <li>
        <a href="#" data-targetdashlate="mylist">
          <i class="fa fa-list"></i> <span>My Lists</span>
        </a>
      </li>
      <li>
        <a href="#" data-targetdashlate="mydocuments">
          <i class="fa fa-folder"></i> <span>My Documents</span>
        </a>
      </li>
      <li>
       	<a href="#" data-targetdashlate="mytasks">
          <i class="fa fa-sliders"></i> <span>My Tasks</span>
        </a>
      </li>
      <li>
        <a href="#" data-targetdashlate="contacts">
          <i class="fa fa-users"></i> <span>Contacts</span>
        </a>
      </li>
      <li>
        <a href="#" data-targetdashlate="myevents">
          <i class="fa fa-calendar-check-o"></i> <span>My Events</span>
        </a>
      </li>
      <li>
        <a href="#" data-targetdashlate="inviteothers">
          <i class="fa fa-share"></i> <span>Invite Others</span>
        </a>
      </li>	  
      <li>
        <a href="#" data-targetdashlate="manageinterests">
          <i class="fa fa-sliders"></i> <span>Manage Interests</span>
        </a>
      </li>
      <li class="list-noteffect">
        <div class="tour-video" data-target="#videomodal" data-toggle="modal">
          <strong class="fa fa-video-camera" aria-hidden="true"></strong>
          <p>Virtual Tour Video</p>
        </div>
      </li>
    </ul>
	<%}%>
    <div class="minimize-menu"></div>
  </div>
  <div class="right-section">
    <div class="right-section-inner">
	<%if(request.getAttribute("SearchPerformed") == null 
		&& request.getAttribute("SearchReturnedNull") ==null
		&& request.getAttribute("CompanyProfileSelected") == null
		&& request.getAttribute("InvestorProfileSelected") == null)
     {%>
        <div class="row" id="dashboardErrorSection">
	        <div class="col-sm-4">
	    		<%if(request.getAttribute("isRegistrationsuccess") != null && request.getAttribute("isRegistrationsuccess") == "true"){%>
			    	<p style="color:green"> <%=request.getAttribute("Registrationsuccess").toString()%>  </p>
					<% request.removeAttribute("Registrationsuccess");
				   request.removeAttribute("isRegistrationsuccess");
				}%>
			</div>
		</div>
      <div class="row" id="top3box">
        <div title="Feature in progress" class="col-sm-4">
          <a  class="tiles-box">
            <h4>Register as Startup/Investor</h4>
            <p>
              
            </p>
          </a>
        </div>
        <div title="Feature in progress" class="col-sm-4">
          <a  class="tiles-box" >
            <h4>Find Events</h4>
            <p>
               
            </p>
          </a>
        </div>
        <div title="feature in progress" class="col-sm-4">
          <a  class="tiles-box">
            <h4>Compare Starups/Investors</h4>
            <p>
              
            </p>
          </a>
        </div>
	  </div>

      <div id="containment-wrapper" class="row">
		
<!-- @Suvarna starting of My Team module -->
        <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="myteam">
              <h3 class="panel-title">
                My Team
              </h3>
            </div>
            <div class="parent-tab">
              <ul class="nav nav-tabs mt5 ml3 touch-tabs box-tab" role="tablist">
<!--   					    <li role="presentation" class="active"><a href="#tab1" aria-controls="home" role="tab" data-toggle="tab">Add Member</a></li> -->
  					    <li role="presentation" ><a href="#tab1" aria-controls="profile" role="tab" data-toggle="tab">Team</a></li>
  					    <li role="presentation"><a href="#tab2" aria-controls="profile" role="tab" data-toggle="tab">Pending requests</a></li>
  					    <li role="presentation"><a href="#tab3" aria-controls="profile" role="tab" data-toggle="tab">Add Team Member</a></li>
  					  </ul>
            </div>
					  <div class="tab-content">
						
					    <div role="tabpanel" class="tab-pane fade in active" id="tab1">
					    	<div class="panel-body withtab">
					    	<%
				if (request.getAttribute("MyTeamMessage") != null) {
			%>
			<div class="spacer"></div>

			<p style="color: red"><%=request.getAttribute("MyTeamMessage")%></p>

			<div class="spacer"></div>
			<%
				request.removeAttribute("MyTeamMessage");
					}
			%>
					    			<TABLE id="ListsTable" cellspacing="0">
				

			



				
				<%
					String[] teamMemberList = (String[]) session.getAttribute("TeamMembers");
				if(teamMemberList!=null&&teamMemberList.length>=1){%>
					<th scope="col">Team member id</th>
					<th scope="col"></th>
					<TR>

					
				</TR>
					<%
						for (int i = 0; i < teamMemberList.length; i++) {
							String email = teamMemberList[i];
							if (email.length() > 1) {
				%>
				<TR>
					
				</TR>

				<TR>
					<TD>-<%=email%></TD>
					<TD><a
						href="MyTeamServlet?removeTeamMember=yes&selectedMemberID=<%=email%>"
						alt="remove" class="confirmation"><img width="20" height="20"
							src="<%=request.getContextPath()%>/icons/delete-icon.png" /></a></TD>
				</TR>
				<%
					}
						}
					}
				else{
				%>
				<TR><td>You have no team members to display</td></TR>
				<%} %>
			
			</TABLE>
		            </div>
					    </div>
					    <div role="tabpanel" class="tab-pane fade" id="tab2">
					    	<div class="panel-body withtab">
					    			<TABLE BORDER="0" id="ListsTable">
							<tr>
							<TH> Requests Received</TH>
							</tr>
				<tr></tr>

				<%
					String[] receivedList = (String[]) session.getAttribute("requestsReceived");
				if(receivedList!=null&&receivedList.length>2){
						for (int i = 0; i < receivedList.length; i++) {
							String mail = receivedList[i];
							if (mail.length() > 1) {
				%>
				<TR>

				</TR>

				<TR>
					<TD>-<%=mail%>
						<form id="formAccept" class="submitForms" name="acceptForm"
							method="post" action="MyTeamServlet?acceptForm=<%=mail%>">
							<input type="hidden" id="memberAccepted" value=<%=mail%>>
							<%
								
							%>
							<input id="acceptBtn" type="submit" value="Accept"
								display="inline">
						</form>
						<form id="formReject" class="submitForms" name="rejectForm"
							method="post" action="MyTeamServlet?rejectForm=<%=mail%>">
							<input type="hidden" id="memberRejected" value=<%=mail%>>
							<input id="rejectBtn" type="submit" value="Reject"
								display="inline">
						</form></TD>
				</TR>
				<%
					}
						}
							}
				else{
				%>
				<TR> <td>You have no Team requests</td></TR>
				<% } %>
</Table>
<div class="spacer"><br></div>
<Table>
<tr>
<TH> Requests Sent</TH></tr>

				<TR>
					<td></td>
				</TR>
				
				<%
					String[] sentList = (String[]) session.getAttribute("requestsSent");
				if(sentList!=null&&sentList.length>2){
						for (int i = 0; i < sentList.length; i++) {
							String emailSent = sentList[i];
							if (emailSent.length() > 1) {
				%>
				<TR>

				</TR>

				<TR>
					<TD>-<%=emailSent%>(Pending)
					</TD>
				</TR>
				<%
					}
						}
				}
				else{
				%>
				<TR><td>You have no active requests sent to display</td></TR>
				<%} %>
			</TABLE>
		            </div>
					    </div>
					    <div role="tabpanel" class="tab-pane fade" id="tab3">
					    	<div class="panel-body withtab">
					    		<table>    		
					<TR>		
					<td>
					<form id="formAddMember" class="submitForm"
							name="formAddMember" action="MyTeamServlet?addUser=true"
							method="post">
							<input type="text" size="50" name="addUserEmail"
								placeholder="Enter email address"> <input type="submit"
								value="Add member">

						</form></td>
				</TR>
				</TABLE>
					    	</div>
					    </div>
					  </div>            
          </div>
        </div>
<!-- My team ends -->
        
<!--  @My Lists Begins--------------------------------------------- -->
        <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="mylist">
              <h3 class="panel-title">
                Lists
              </h3>
            </div>
            <%if (session.getAttribute("MyListsClicked") == "true") {%> 
            <div class="parent-tab">
				<ul class="nav nav-tabs mt5 ml3 touch-tabs box-tab" role="tablist">
  					<li role="presentation" class="active"><a href="#tab8" aria-controls="profile" role="tab" data-toggle="tab">MyLists</a></li>
  					<li role="presentation"><a href="#tab9" aria-controls="profile" role="tab" data-toggle="tab">SharedLists</a></li>
				</ul>
            </div>
            <div class="tab-content">
	            	<div role="tabpanel" class="tab-pane fade in active" id="tab8">
						<div class="panel-body withtab">
							<div style="display: block" class="mylists">
							<%if (request.getAttribute("sharedListRemoved") != null) {%>
											<p style="color: red">SharedList <%=request.getAttribute("removedListId")%> has been removed! :)</p>
											<%request.removeAttribute("sharedListRemoved");
											request.removeAttribute("removedListId");
							}%>
								<%	
									SaveUserListService saveUserListService = new SaveUserListService();
									List<UserList> userlists = (List<UserList>) session.getAttribute("userLists");//saveUserListService.getUserLists(user.getUser_email());
										List<UserList> sharedUserlists = (List<UserList>) session.getAttribute("sharedUserLists");
										if (request.getAttribute("listRemoved") != null && request.getAttribute("sharedListRemoved")!=null) 
										{
											userlists = (List<UserList>)saveUserListService.getUserLists(user.getUser_email());
											sharedUserlists = (List<UserList>) saveUserListService.getSharedUserLists(user.getUser_email());
										}
										int userListsSize = userlists.size();
										int sharedUserListsSize = sharedUserlists.size();
										int totalLists = userListsSize + sharedUserListsSize;
										int listDisplayIndex = 0;
										if (userlists.size() < 1) {%>
											<TABLE cellspacing="0"><TR><TD style="border: none">
												<p style="" >You have no lists saved. <br>Use the search text above to create your Lists :)</p>
											</TD></TR></TABLE>
										<%} else {
										if (session.getAttribute("shareTeamMemberLimitExceeded") != null) {%>
											<p style="color: green"><%=session.getAttribute("shareTeamMemberLimitExceeded")%>
											</p>
											<%request.removeAttribute("shareTeamMemberLimitExceeded");
										}
										if (request.getAttribute("listAlreadyShared") != null) {
											%>
											<p style="color: green"><%=request.getAttribute("listAlreadyShared")%>
											</p>
											<%request.removeAttribute("listAlreadyShared");
										}%>
										<%if (request.getAttribute("listRemoved") != null) {%>
											<p style="color: red">List <%=request.getAttribute("removedListId")%> has been removed! :)</p>
											<%request.removeAttribute("listRemoved");
											request.removeAttribute("removedListId");
										}%>
								<TABLE id="ListsTable" cellspacing="0" summary="Your Lists of selected companies">
								<caption>My Lists </caption>
									<TR>
										<TH scope="col">ListName</TH>
										<TH scope="col" >List Of Companies</TH>
										<th scope="col">Shared With</th>
										<th scope="col"></th>
										<th scope="col"></th>
										<th scope="col"></th>
										<th scope="col"></th>
									</TR>
									<%for (int i = 0; i < userlists.size(); i++) 
									{
										UserList ul = userlists.get(i);
										listDisplayIndex = i;%>
									<TR>
										<Td class="listId">
											<%
										String listName = "";
										if(ul.getList_name().equalsIgnoreCase("") || ul.getList_name() ==null) 
										{listName = ul.getList_id();}
										else listName = ul.getList_name();%> 
										<a href="#listSelected" data-toggle="modal" data-target="#selectedListModal" id="<%=listDisplayIndex%>">
											<%=listName%>
										</a>
										
										<%session.setAttribute("selectedListIdSessionAttr", ul.getList_id()); %>
										</Td>
					
										<TD class="companies">
											<%String[] companies = ul.getCompany_docId().split(";");
// 												String arr = request.getParameter("hiddenSelectedCompanies4SaveAsList");
											for (String company : companies) {
// 												Document companyDoc = SearchUtil.getDocumentForSelectedCompany(company);
// 												String companyCID = companyDoc.get("cid");
												%> 
<%-- 												<a href="CompanyProfile?selectedCompanyName=<%=company%>&selectedCompanyCID=<%=companyCID%>"><%=company%></a>; --%>
												<a href="CompanyProfile?selectedCompanyName=<%=company%>"><%=company%></a>;
											<%}%>
										</TD>
					
										<TD class="sharedWith">
											<%String[] members = ul.getList_sharedWith().split(",");
											for(String str:members)
											{%>
												<%=str %><br><%-- 						<%=ul.getList_sharedWith()%> --%>
											<%} %>
										</td>
										<TD><img width="20" height="20"
											src="<%=request.getContextPath()%>/icons/edit-icon.png" /></TD>
					
										<TD><a
											href="MyLists?exportListSelected=yes&selectedListID=<%=ul.getList_id()%>" 
											alt="export"><img width="20" height="20"
												src="<%=request.getContextPath()%>/icons/export-icon.png" /></a>
										</TD>
					
										<TD><a
											href="MyLists?removeListSelected=yes&selectedListID=<%=ul.getList_id()%>"
											alt="remove" class="confirmation"><img width="20" height="20"
												src="<%=request.getContextPath()%>/icons/delete-icon.png" /></a></TD>
										<TD>
											<div class="shareList">
												<%
												String sharedWithMemberEmail = ul.getList_sharedWith();
												String[] sharedWith = sharedWithMemberEmail.split(",");
												
												UserTeamTableInfo userTeamMemberCount = myTeam_DButil.getUserTeam(user.getUser_email());//.getUser_email());
												String[] teamMembers = (String[]) userTeamMemberCount.getTeam_members().split(",");
												String strID = "";
												if(teamMembers!=null && !userTeamMemberCount.getTeam_members().toString().isEmpty())
												{
													if(sharedWith.length==2) 
														strID = "exceeded";
													else
														strID="notExceeded";
												}else 
													strID = "noTeamMembers";
												%>
												<li>									
												<a href="#shareListId" alt="share" data-toggle="modal"
													data-target="#shareList2TeamModal" id=<%=strID%>> <img width="20"
														height="20"
														src="<%=request.getContextPath()%>/icons/share-icon.png" />
												</a>
												<input name="listNameInput" id="listNameInput" 
													value="<%=ul.getList_id()%>" class="listNameInput" type="hidden"></input>
												</li>
											</div>
										</TD>
									</TR>
									<%}%>
								</TABLE>
								<%}%> <!-- userlists.size<1 if/else End -->
							</div><!-- class="mylists" ENDS -->
							<br>
					
			<!-- 				<div id="shareList2TeamModal" class="ui modal active" role="dialog"> -->
							<div id="shareList2TeamModal" class="modal modal-common fade" role="dialog">
								<div class="modal-dialog">
					
									<!-- Modal content begins-->
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Share with Team Members</h4>
										</div>
										<div class="modal-body">
											<form id="shareListForm" name="shareListForm" method="get" action="ShareUserList">
												<div id="mystyle2">
													<div id="noTeamMembers_ShareLimit" style="display: none" class="noTeamMembers_ShareLimit">
														<p style="color: green">You have no team members to share the list with.</br>
														You could use the "My Team" menu to add team members to your FG account.</p>
														<div class="modal-footer">									
															<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
														</div>
													</div>
													<div id="exceeded_ShareLimit" style="display: none" class="exceeded_ShareLimit">
														<p style="color: green">You could upgrade to FG+ to share your lists to more than 2 teamMembers.</p>
														<div class="modal-footer">									
															<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
														</div>
													</div>
													<div id="notExceeded_ShareLimit" style="display: none" class="notExceeded_ShareLimit">
														<input id="shareListInputId" name="shareListInputId" type="hidden" />
														<input id="selectedListIdOnModal" name="selectedListIdOnModal" type="hidden"/>
														<%
														UserTeamTableInfo userTeamMemberCount = myTeam_DButil.getUserTeam(user.getUser_email());
														String[] teamMembers = (String[]) userTeamMemberCount.getTeam_members().split(",");
														if(teamMembers!=null && !userTeamMemberCount.getTeam_members().toString().isEmpty()){%>
														<TABLE style="border: none">
															<TR>
																<td colspan="2" style="border: none"><select class="form-control" id="teamMembersDropdown"
																	name="teamMembersDropdown">
																		<%
																			for (int i = 0; i < teamMembers.length; i++) {
																		%>
																		<option id="<%=i%>" name="<%=i%>"
																			value="<%=teamMembers[i]%>">
																			<%=teamMembers[i]%>
																		</option>
																		<%
																			}
																		%>
																</select></td>
															</tr>
															<tr>
																<td colspan="2" style="border: none"><input
																	style="position: relative; vertical-align: top;"
																	id="taskCommentTextAreaId" name="taskCommentTextAreaName"
																	spellcheck="false" autocomplete="off" width="30"
																	placeholder="Assign your task around this list in here."
																	class="omni-search form-control tt-input" type="text" required/></td>
															</tr>
															<tr>
																<td colspan="2" style="border: none">
																<div class="modal-footer">
																	<button class="btn btn-submit" type="submit">Share</button>
			      												</div>
																</td>
															</tr>
														</TABLE>
														<%}else{%>
															<p style="color: green">You have no team members to share the list with.</br>
															You could use the "My Team" menu to add team members to your FG account.</p>
															<div class="modal-footer">									
																<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
															</div>
														<%} %>
													</div>
												</div>
											</form>
										</div>
									</div>
									<!-- Modal content ends-->
								</div>
							</div>
			
							<!-- DisplayUserListModal begins -->
							<div id="selectedListModal" class="modal modal-common fade" role="dialog">
								<div class="modal-dialog">
									<!-- Modal content begins-->
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">User List Details</h4>
										</div>
										<div class="modal-body">
											<% 	
// 												List<Document[]> listOfCompanyDocArrforEachUserList = (List<Document[]>) saveUserListService.getListOfCompanyDocArrForEachUserList(user);
												List<CompanyInfo[]> listOfCompanyInfosArrForEachUserList = (List<CompanyInfo[]>) saveUserListService.getListOfCompaniesForEachUserList_ES(user);
												List<String> listOfUserListIds = (List<String>)saveUserListService.getListOfUserListIds(user);
												for (int listIndex = 0; listIndex < listOfUserListIds.size(); listIndex++) {
											%>
											<Input id="hiddenListIdLabel" type="hidden"></input>
					
											<div id="<%=listIndex%>_CompaniesOfSelectedLists" style="display: none" class="<%=listIndex%>_CompaniesOfSelectedLists">
												<div class="form-group" style="width: 95%">
													<%	UserList_DBUtil userList_DButil = new UserList_DBUtil();
														UserList userlist = (UserList)userList_DButil.getUserListByListId(listOfUserListIds.get(listIndex));
																//Display Below table only if the lists has been shared.
																if (!(userlist.getList_sharedWith().equalsIgnoreCase(""))) {
													%>
													<center>
			
													<TABLE style="width: 95%" id="listsTable" cellspacing="0" summary="Team Members and Task assigned">
														<caption>Tasks Assigned</caption>
														<TR>
															<TH scope="col">Assigned To</TH>
															<TH scope="col">Tasks/Comments</TH>
														</TR>
														<%
															String[] teamShared = userlist.getList_sharedWith().split(",");
															String[] teamSharedComment = userlist.getSharedList_Comment().split(",");
														%>
														<%for (int sharedNum = 0; sharedNum < teamShared.length; sharedNum++) {%>
														<TR>
															<TD>
																<%=teamShared[sharedNum]%>								
															</td>	
															<TD>
																<%if(teamSharedComment.length >= sharedNum+1){%>
																<%=teamSharedComment[sharedNum]%>
																<%}%>
															</td>
														</TR>
														<%}%>
					
													</TABLE>
													</center>
													<%
														}//if
													%>
												</div>
												<br>
												<center>
												<div class="form-group" style="width: 95%">
												<TABLE style="width: 95%" class="listTable" cellspacing="0" summary="Team Members and Task assigned">
													<%String listName = "";
													if(userlist.getList_name().equalsIgnoreCase("") || userlist.getList_name() ==null) 
													{listName = userlist.getList_id();}
													else listName = userlist.getList_name();%>	
													<caption><%=listName%></caption>
													<TR>
														<TH scope="col">Name</TH>
														<TH scope="col">Location</TH>
														<TH scope="col">Industry</TH>
														<TH scope="col">HeadCount</TH>
														<TH scope="col">Revenue</TH>
														<TH scope="col">Investors</TH>
													</TR>
													<%
// 														Document[] docs = (Document[]) listOfCompanyDocArrforEachUserList.get(listIndex);
														CompanyInfo[] companies = (CompanyInfo[]) listOfCompanyInfosArrForEachUserList.get(listIndex);
																for (int i = 0; i < companies.length; i++) {%>
													<TR>
														<TD>
															<a href="CompanyProfile?selectedCompanyName=<%=companies[i].getName()%>">
																<%=companies[i].getName()%></a></td>
														<TD><%=companies[i].getLocation()%></td>
														<TD><%=companies[i].getIndustry()%></td>
														<TD><%=companies[i].getHeadcount()%></td>
														<TD><%=companies[i].getRevenue()%></td>
														<TD>
															<%String[] investors = companies[i].getInvestors().split(";");
																			for (String investor : investors) {%> 
															<a href="InvestorProfile?selectedInvestorName=<%=investor%>"><%=investor%></a>; 
															<%}%>
														</TD>
													</TR>
													<%}%>
												</TABLE>
												</div>
												</center>
											</div>
											<%}%>
										</div>
										<!-- DisplayUserListModal body ends-->
									</div>
									<!-- DisplayUserListModal content ends-->
								</div>
							</div>
							<!-- 	DisplayUserListModal Ends -->

			            </div><!--class="panel-body withtab"  END-->
			        </div><!-- class="tab-pane fade in -->
					
					<div role="tabpanel" class="tab-pane fade" id="tab9">
						<div class="panel-body withtab">
							<div style="display: block" class="mylists">
							<%if (request.getAttribute("sharedListRemoved") != null) {%>
											<p style="color: red">SharedList <%=request.getAttribute("removedListId")%> has been removed! :)</p>
											<%request.removeAttribute("sharedListRemoved");
											request.removeAttribute("removedListId");
							}%>
															
								<%if (sharedUserlists.size() < 1) {%>
								<TABLE >
									<TR >
										<TD style="border: none"><p>You have no lists Shared.</p></TD>
									</TR>
								</TABLE>
								<%} else {%>
								<TABLE id="ListsTable" cellspacing="0" summary="select the list to see the details of the task assigned">
									<caption>Shared Lists : </caption>
									<TR>
										<TH scope="col">ListName</TH>
										<TH scope="col">List Of Companies</TH>
										<th scope="col">Shared by</th>
										<th scope="col"></th>
										<th scope="col"></th>
									</TR>
									<%for (int i = 0; i < sharedUserlists.size(); i++) {
										UserList sharedul = (UserList)sharedUserlists.get(i);
									%>
									<TR>
										<TD class="listId">
											<%String listName = "";
											if(sharedul.getList_name().equalsIgnoreCase("") || sharedul.getList_name() ==null) 
											{listName = sharedul.getList_id();}
											else listName = sharedul.getList_name();%> 
											<a href="#listSelected" data-toggle="modal" data-target="#selectedSharedListModal" id="<%=i%>"><%=listName%> </a>
											<%session.setAttribute("selectedListIdSessionAttr", sharedul.getList_id()); %>
										</td>
										<TD class="companies">
											<%String[] companies = sharedul.getCompany_docId().split(";");
											for (String company : companies) {
// 												Document companyDoc = SearchUtil.getDocumentForSelectedCompany(company);
// 												String companyCID = companyDoc.get("cid");
												%> 
												<a href="CompanyProfile?selectedCompanyName=<%=company%>"><%=company%></a>; 
											<%}%>
										</TD>
										<TD class="sharedby"><%=sharedul.getUser_email()%></td>
										<TD><img width="20" height="20" src="<%=request.getContextPath()%>/icons/edit-icon.png" /></TD>
										<TD><a href="MyLists?removeSharedList=yes&selectedListID=<%=sharedul.getList_id()%>" alt="remove" class="confirmation"><img width="20" height="20" src="<%=request.getContextPath()%>/icons/delete-icon.png" /></a></TD>
									</TR>
									<%}%>
								</TABLE>
								<%}%><!-- sharedUserLists.size()<1 if/else ENDS -->
							</div><!-- class="mylists" -->
							
														
							<!-- DisplaySHAREDUserListModal begins -->
							<div id="selectedSharedListModal" class="modal modal-common fade" role="dialog">
								<div class="modal-dialog">
									<!-- Modal content begins-->
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Shared User List Details</h4>
										</div>
										<div class="modal-body">
											<%SaveUserListService saveUserListService2 = new SaveUserListService();
// 												List<Document[]> listOfCompanyDocArrforEachSharedUserList = (List<Document[]>) saveUserListService2.getListOfCompanyDocArrForEachSharedUserList(user);
												List<CompanyInfo[]> listOfCompanyInfosArrForEachSharedUserList = (List<CompanyInfo[]>) saveUserListService.getListOfCompaniesForEachSharedUserList_ES(user);
												List<String> listOfSharedUserListIds = (List<String>)saveUserListService2.getListOfSharedUserListIds(user);
												for (int sharedListIndex = 0; sharedListIndex < listOfSharedUserListIds.size();sharedListIndex++) {%>//listOfSharedUserListIds.size(); 
											<Input id="hiddenListIdLabel" type="hidden"></input>
					
											<div id="<%=sharedListIndex%>_CompaniesOfSelectedLists" style="display: none" class="<%=sharedListIndex%>_CompaniesOfSelectedLists">
												<% UserList_DBUtil userList_DButil2 = new UserList_DBUtil();
												UserList sharedUserlist = (UserList)userList_DButil2.getUserListByListId(listOfSharedUserListIds.get(sharedListIndex));%>
												<center>
												<TABLE class="listTable" cellspacing="0" summary="Team Members and Task assigned">
													<%String listName = "";
													if(sharedUserlist.getList_name().equalsIgnoreCase("") || sharedUserlist.getList_name() ==null) 
													{listName = sharedUserlist.getList_id();}
													else {listName = sharedUserlist.getList_name();}%> 
													<caption><%=listName%></caption>
													<TR>
														<TH scope="col">Name</TH>
														<TH scope="col">Location</TH>
														<TH scope="col">Industry</TH>
														<TH scope="col">HeadCount</TH>
														<TH scope="col">Revenue</TH>
														<TH scope="col">Investors</TH>
													</TR>
													<%
// 													Document[] docs = (Document[]) listOfCompanyDocArrforEachSharedUserList.get(sharedListIndex);
													CompanyInfo[] companies = (CompanyInfo[]) listOfCompanyInfosArrForEachSharedUserList.get(sharedListIndex);
													for (int i = 0; i < companies.length; i++) {//listOfCompanyInfosArrForEachSharedUserList%>
													<TR>
														<TD><a href="CompanyProfile?selectedCompanyName=<%=companies[i].getName()%>"><%=companies[i].getName()%></a></td>
														<TD><%=companies[i].getLocation()%></td>
														<TD><%=companies[i].getIndustry()%></td>
														<TD><%=companies[i].getHeadcount()%></td>
														<TD><%=companies[i].getRevenue()%></td>
														<%--<TD><%= docs[i].get("investors")%></td> --%>
														<TD><%String[] investors = companies[i].getInvestors().split(";");
															for (String investor : investors) {%> 
																<a href="InvestorProfile?selectedInvestorName=<%=investor%>"><%=investor%></a>; 
															<%} %>
														</TD>
													</TR>
													<%} %><!--  for loop ends -->
												</TABLE>
												</center>
											</div><!--div sharedListIndex_CompaniesOfSelectedLists -->
											<%} %>
										</div><!-- DisplaySHAREDUserListModal body ends-->
									</div><!-- DisplaySHAREDUserListModal modal-Content ends-->
								</div><!-- DisplaySHAREDUserListModal modal-Dialog -->
							</div><!--  DisplaySHAREDUserListModal -->
							
						</div><!--class="panel-body withtab"  END-->
			        </div><!-- class="tab-pane fade in -->
				<%}%><!-- if(("MyListsClicked") == "true") -->
		  	</div><!-- tab-content END -->
          </div>
        </div>
<!--  @My Lists Ends--------------------------------------------- -->


<!-- 	@My Documents Begin  ------------------------------------ -->
        <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="mydocuments">
              <h3 class="panel-title">
                My Documents
              </h3>
            </div>
            <div class="panel-body notab">
              	<!--@Diana------If My Documents Selected-->
				<%if (session.getAttribute("MyDocumentsClicked") != null && session.getAttribute("MyDocumentsClicked") == "true") {%>
				<div class="spacer"></div>
				<div style="display: block" class="myform">
						<center>
						<form method="post" action="MyDocuments" enctype="multipart/form-data">
							<table style="border: none" cellspacing="0">
								<tr>
									<td style="border: none">
										<input class="form-control" style="position: relative; vertical-align: top;"
													type="text" autocomplete="off" placeholder="Enter Document Name"
													name="userDocumentName" required> </td>
									<td style="border: none"><input class="form-control" type="file" name="uploadedFile" size="25" required/>
									</td>
									<td style="border: none">
											<button class="btn btn-common" id="SaveDocumentButton" name="SaveDocumentButton" type="submit">Upload</button>
									</td>
								</tr>
							</table>
						</form>
						
						<%if(request.getAttribute("documentRemoved")!=null || request.getAttribute("documentRemoved")=="true") 
						{String removedDocumentName = (String)request.getAttribute("removedDocumentName");%>
							<p style="color: red"><%=removedDocumentName%> has been removed! :)</p>
							<%request.removeAttribute("documentRemoved");
							request.removeAttribute("removedDocumentName");
						}%>
						<%if(request.getAttribute("sharedDocumentRemoved")!=null || request.getAttribute("sharedDocumentRemoved")=="true") 
						{
							String removedDocumentName = (String)request.getAttribute("removedSharedDocumentName");
							String removedDocSharedByEmail = (String)request.getAttribute("removedDocSharedByEmail");%>
							<p style="color: red"><%=removedDocumentName%> shared by <%=removedDocSharedByEmail %>has been removed! :)</p>
							<%request.removeAttribute("sharedDocumentRemoved");
							request.removeAttribute("removedSharedDocumentName");
							request.removeAttribute("removedDocSharedByEmail");
						}%>
						
					</center>
						<%
						SaveUserDocumentService saveUserDocumentService = new SaveUserDocumentService();
						List<UserDocument> userDocuments = (List<UserDocument>) session.getAttribute("userDocuments");
						List<UserDocument> sharedUserDocuments = (List<UserDocument>) session.getAttribute("sharedUserDocuments");
						if (request.getAttribute("documentRemoved") != null) 
						{
							userDocuments = (List<UserDocument>)saveUserDocumentService.getUserDocuments(user.getUser_email());
							sharedUserDocuments = (List<UserDocument>) saveUserDocumentService.getSharedUserDocuments(user.getUser_email());
						}
						int userDocumentsSize = userDocuments.size();
						int sharedUserDocumentsSize = sharedUserDocuments.size();
						int totalDocuments = userDocumentsSize + sharedUserDocumentsSize;
						if (totalDocuments < 1) {
						%>
						<center>
						<TABLE style="border: none" cellspacing="0">
							<TR>
								<TD style="border: none"><p style="">
										You have no Documents saved. 
										<br>Use the upload button to upload/share your documents.
								</p></TD>
							</TR>
						</TABLE>
						</center>
						<%}else{
							if (request.getAttribute("shareTeamMemberLimitExceeded") != null) {%>
								<center><p style="color: green"><%=request.getAttribute("shareTeamMemberLimitExceeded")%>
								</p></center>
								<%request.removeAttribute("shareTeamMemberLimitExceeded");
							}
							if (request.getAttribute("documentAlreadyShared") != null) {
								%>
								<center><p style="color: green"><%=request.getAttribute("documentAlreadyShared")%>
								</p></center>
								<%request.removeAttribute("documentAlreadyShared");
							}
							if(userDocumentsSize>=1){%>
								<br>
								<center>

							<TABLE class="listTable" cellspacing="0" summary="Team Members and Task assigned">
								<caption></caption>
								<TR>
									<TH scope="col">Name</TH>
									<TH scope="col">Doc Type</TH>
									<TH scope="col">SharedWith</TH>
									<TH scope="col">DocOwner</TH>
									<TH scope="col"></TH><!-- download -->
									<TH scope="col"></TH><!-- share -->
									<TH scope="col"></TH><!-- remove -->
								</TR>
								<%for(int i =0; i<userDocuments.size();i++) {%>
								<TR>
									<TD>
										<a href="MyDocuments?downloadDocumentSelected=yes&selectedDocumentID=<%=userDocuments.get(i).getUserDocumentId()%>" alt="download">
											<%=userDocuments.get(i).getUserDocumentName() %></a>
									</TD>
									<td><%=userDocuments.get(i).getUserDocumentType() %></td>
									<TD class="sharedWith">
										<%String[] members = userDocuments.get(i).getSharedWithIds().split(",");
										for(String str:members)
										{%><%=str %><br><%} %>
									</td>
									<TD><%=userDocuments.get(i).getUserDocumentOwneremail() %></TD>
									<%UserDocument userDocument = (UserDocument) userDocuments.get(i);%>
									<TD>
										<a href="MyDocuments?downloadDocumentSelected=yes&selectedDocumentID=<%=userDocument.getUserDocumentId()%>" alt="download">
										<img width="20" height="20" src="<%=request.getContextPath()%>/icons/download-icon.png" /></a>
									</TD>
									<TD>
										<a href="MyDocuments?removeDocumentSelected=yes&selectedDocumentID=<%=userDocument.getUserDocumentId()%>" alt="remove" class="confirmation">
										<img width="20" height="20" src="<%=request.getContextPath()%>/icons/delete-icon.png" /></a>
									</TD>
									<TD>
										<div  class="shareList">						
											<%
											session.setAttribute("selectedDocumentIdSessionAttr", userDocument.getUserDocumentId());									
											String sharedWithMemberEmail = userDocument.getSharedWithIds();
											String[] sharedWith = sharedWithMemberEmail.split(",");
											UserTeamTableInfo userTeamTable4Documents = myTeam_DButil.getUserTeam(user.getUser_email());
											String[] teamMembers4Documents = (String[]) userTeamTable4Documents.getTeam_members().split(",");
											String strID = "";
											if(teamMembers4Documents!=null && !userTeamTable4Documents.getTeam_members().toString().isEmpty())
											{
												if(sharedWith.length==2) 
													strID = "exceeded";
												else
													strID="notExceeded";
											}else 
												strID = "noTeamMembers";
											%>
											<li>
												<a href="#shareDocumentId" alt="share" data-toggle="modal"
												data-target="#shareDocument2TeamModal" id=<%=strID%>> 
												<img width="20" height="20" src="<%=request.getContextPath()%>/icons/share-icon.png" />
												</a>
											</li>
										</div>
									</TD>
								</TR>
								<%} %>
								</TABLE>
								</center>
							<%}else{ %>
								<br>
								<center>
								<TABLE style="border: none" cellspacing="0">
								<TR>
									<TD style="border: none"><p style="">
											<br>Use the upload button to upload/share your documents.
									</p></TD>
								</TR>
								</TABLE>
								</center>
							<%} %>
							<%if(request.getAttribute("userDocumentLimitExceeds")!=null || request.getAttribute("userDocumentLimitExceeds")=="true")
							{ %>
								<%request.removeAttribute("userDocumentLimitExceeds");%>
								<p style="color: green">Please upgrade to save more than 2 Documents.
									<br> However, others can share as many documents with you.
								</p>
							<%} %>
							<% if(sharedUserDocumentsSize>=1){%>
								<br>
								<center>

								<TABLE class="listTable" cellspacing="0" summary="Documents shared with you:">
								<caption></caption>
								<TR>
									<TH scope="col">Name</TH>
									<TH scope="col">Doc Type</TH>
									<TH scope="col">DocOwner</TH>
									<TH scope="col"></TH><!-- download -->
									<TH scope="col"></TH><!-- remove -->
								</TR>
								<%for(int i =0; i<sharedUserDocuments.size();i++) {%>
								<TR>
									<TD>
										<%UserDocument sharedUserDocument = (UserDocument)sharedUserDocuments.get(i); %>
											<a href="MyDocuments?downloadDocumentSelected=yes&selectedDocumentID=<%=sharedUserDocument.getUserDocumentId()%>" alt="download">
												<%=sharedUserDocument.getUserDocumentName() %></a>
									</TD>
									<td><%=sharedUserDocument.getUserDocumentType() %></td>
									<TD><%=sharedUserDocument.getUserDocumentOwneremail() %></TD>
									<TD>
										<a href="MyDocuments?downloadDocumentSelected=yes&selectedDocumentID=<%=sharedUserDocument.getUserDocumentId()%>" alt="download">
										<img width="20" height="20" src="<%=request.getContextPath()%>/icons/download-icon.png" /></a>
									</TD>
									<TD>
										<a href="MyDocuments?removeSharedDocumentSelected=yes&selectedSharedDocumentID=<%=sharedUserDocument.getUserDocumentId()%>" alt="remove" class="confirmation">
										<img width="20" height="20" src="<%=request.getContextPath()%>/icons/delete-icon.png" /></a>
									</TD>
								</TR>
								<%} %>
								</TABLE>
								</centre>
								<br>
							<%} %>
					<%}%> <!--  if-else loop for(totalDocuments < 1) ends -->
            		</div>
					
				<!-- 		Share Document model -->
				<div id="shareDocument2TeamModal" class="modal modal-common fade" role="dialog"> 
					<div class="modal-dialog">
		
						<!-- Modal content begins-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Share with Team Members</h4>
							</div>
							<div class="modal-body">
								<form id="shareDocumentForm" name="shareDocumentForm" method="get"
									action="ShareUserDocument">
									<div id="mystyle2">
										<div id="noTeamMembers_ShareDocumentLimit" style="display: none" class="noTeamMembers_ShareDocumentLimit">
											<p style="color: green">You have no team members to share the document with.</br>
											You could use the "My Team" menu to add team members to your FG account.</p>
											<div class="modal-footer">									
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
										</div>
										</div>
										<div id="exceeded_ShareDocumentLimit" style="display: none" class="exceeded_ShareDocumentLimit">
											<p style="color: green">You could upgrade to FG+ to share your documents to more than 2 teamMembers.</p>
											<div class="modal-footer">									
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											</div>
										</div>
										<div id="notExceeded_ShareDocumentLimit" style="display: none" class="notExceeded_ShareDocumentLimit">
											<%UserTeamTableInfo userTeamTableInfo = myTeam_DButil.getUserTeam(user.getUser_email());
											String[] teamMembersStrArray = (String[]) userTeamTableInfo.getTeam_members().split(",");
											if(teamMembersStrArray!=null && !userTeamTableInfo.getTeam_members().toString().isEmpty())
											{
											%>
											<TABLE style="border: none" cellspacing="0">
												<TR>
													<td style="border: none"><select class="form-control" id="teamMembersDropdown"
														name="teamMembersDropdown">
															<%for (int i = 0; i < teamMembersStrArray.length; i++) {%>
																<option id="<%=i%>" name="<%=i%>"
																	value="<%=teamMembersStrArray[i]%>">
																	<%=teamMembersStrArray[i]%>
																</option>
															<%}%>
													</select></td>
												</tr>
												<tr>
													<td style="border: none">
														<input class="form-control" style="position: relative; vertical-align: top;"
														id="taskCommentTextAreaId" name="taskCommentTextAreaName"
														spellcheck="false" autocomplete="off" width="30"
														placeholder="Assign your task for this Document in here."
														class="omni-search form-control tt-input" type="text" /></td>
												</tr>
												<tr>
													<td style="border: none">													
														<div class="modal-footer">
															<button class="btn btn-submit" type="submit">Share</button>
				      									</div>
													</td>
												</tr>
											</TABLE>
											<%} %>
											<input id="shareListInputId" name="shareListInputId" type="hidden" />
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div><!-- 		Share Document model ends-->
				<%}%> <!--  If Documents Clicked loop ends-->
				
          </div> <!--  panel-body notab -->
          </div> <!--  panel panel-default panel-common -->
        </div> <!-- col-sm-6 col-md-4 -->
<!-- 	@My Documents Ends ------------------------------------- -->

        <!--  @Suvarna starting of My Tasks module -->
        <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="mytasks">
              <h3 class="panel-title">
                Tasks
              </h3>
            </div>
            <div class="parent-tab">
              <ul class="nav nav-tabs mt5 ml3 touch-tabs box-tab" role="tablist">
<!--   					    <li role="presentation" class="active"><a href="#tab1" aria-controls="home" role="tab" data-toggle="tab">Add Member</a></li> -->
  					    <li role="presentation"  class="active"><a href="#tab5" aria-controls="profile" role="tab" data-toggle="tab">My Tasks</a></li>
  					    <li role="presentation"><a href="#tab6" aria-controls="profile" role="tab" data-toggle="tab">Assigned Tasks</a></li>
  					    <li role="presentation"><a href="#tab7" aria-controls="profile" role="tab" data-toggle="tab">Create Tasks</a></li>
  					  </ul>
            </div>
					  <div class="tab-content">
					   <div role="tabpanel" class="tab-pane fade" id="tab7">
					    	<div class="panel-body withtab">
            
              <% List<TasksTableInfo> tasksLists=null;
              List<TasksTableInfo> assignedList=null;			
			if (session.getAttribute("TasksSet") != null && session.getAttribute("TasksSet") == "true") {

			 tasksLists=(List<TasksTableInfo>)session.getAttribute("Tasks");
			 assignedList=(List<TasksTableInfo>)session.getAttribute("AssignedTasks");}
					%>
		<div class="tasksList" id="tasksList" style="display: block"
			class="myform">
			
			
			<%if(request.getAttribute("TaskMessage")!=null) {%>
				<h6><%=request.getAttribute("TaskMessage") %></h6>
			<% request.removeAttribute("TaskMessage");
			
			}%> 
			<div class="createTask">
			<% 
					String[] teamMembers4Tasks = (String[]) session.getAttribute("TeamMembers");
					String[] teamlist = null;
				  
				    if(session.getAttribute("TeamMembers")!=null){
				    	teamlist = new String[teamMembers4Tasks.length+1];
				    	for(int i=0;i<teamMembers4Tasks.length;i++){
					teamlist[i]=teamMembers4Tasks[i];
				}
				teamlist[teamMembers4Tasks.length]=user.getUser_email();
			}
			
				%>
				<form id="createTask" name="createTask" method="post" action="MyTaskServlet">
				<TABLE style="border: none">
					<TR>
						<td>Assign to :</td>
						<td><select id="teamMembersDropdown"
							name="teamMembersDropdown">
							
								<% if(teamlist!=null){
									for (int i = 0; i < teamlist.length; i++) {
								if(teamlist[i].length()>1){%>
								<option id="<%=i%>" name="<%=i%>" value="<%=teamlist[i]%>">
									<%=teamlist[i]%>
								</option>
								<%
								}}}
								%>
						</select></td>
					</tr>
					<tr>
						<td colspan="2"><input
							style="position: relative; vertical-align: top;"
							id="taskNameId" name="taskName"
							spellcheck="false" autocomplete="off" width="30"
							placeholder="Please enter the task name."
							class="omni-search form-control tt-input" type="text" /></td>
					</tr>
					<tr>
						<td colspan="2"><input
							style="position: relative; vertical-align: top;"
							id="taskDescriptionTextAreaId" name="taskDescriptionTextAreaName"
							spellcheck="false" autocomplete="off" width="30"
							placeholder="Please describe your task here."
							class="omni-search form-control tt-input" type="text" /></td>
							<td>
							<button type="submit">Create</button>
						</td>
					</tr>
					<tr>
						
					</tr>
				</TABLE>
				</form>
				<input id="shareListInputId" name="shareListInputId" type="hidden" />
			</div>
			 </div>
			 </div>
			 </div>
			 <div role="tabpanel" class="tab-pane fade in active" id="tab5">
					    	<div class="panel-body withtab">
			<div style="display: block">
			<div class="spacer"></div>
			
			<%if(tasksLists!=null&&tasksLists.size()>0) { %>
			<TABLE BORDER="1" id="TasksTable">
				<TR> 
					<TH scope="col">Task ID</TH>
					<TH scope="col">Task Name</TH>
					<th scope="col">Description</th>
					<th scope="col">Created on</th>
					<th scope="col">Status</th>
					<th scope="col">Created by</th>
				</TR>
					
					
					<%for(TasksTableInfo task:tasksLists){ %>
					
					<TR>
					<form id="editTask" name="editTask" method="get" action="MyTaskServlet">
					<td><%=task.getTask_id()%></td>
					<td><%=task.getTask_name()%></td>
					<td><%=task.getDesc()%></td>
					<td><%=task.getTask_date()%><input type="hidden" name="TaskID" value=<%=task.getTask_id() %>></td>
					<td>
					<select id="taskStatusDropdown" name="taskStatusDropdown">
					<option id="<%task.getStatus();%>" value="<%task.getStatus();%>" selected><%=task.getStatus()%></option>
					<%if(task.getStatus().equalsIgnoreCase("open")){%>
					<option id="closed"  value="closed">closed</option>
					<option id="ongoing" value="ongoing">ongoing</option>
					<%} %>
					<%if(task.getStatus().equalsIgnoreCase("ongoing")){%>
					<option id="ongoing" value="closed">closed</option>
					<%} %>
					</select></td>
					<td><%=task.getCreatedby()%></td>
					<td><button type="submit" value="save">Update</button></td>
				</form>
				</TR>
				
				<%} %>
				</TABLE>
				<%} else{%>
				You have no Tasks to be displayed
				<%} %>
				</div>
				</div>
				</div>
				<div role="tabpanel" class="tab-pane fade" id="tab6">
					    	<div class="panel-body withtab">
				<div class="spacer"></div>
				<%if(tasksLists!=null&&tasksLists.size()>0) { %>
				<TABLE cellspacing="0" id="TasksAssignedTable">
				<TR>

					<TH scope="col">Task ID</TH>
					<TH scope="col">Task Name</TH>
					<th scope="col">Description</th>
					<th scope="col">Created on</th>
					<th scope="col">Status</th>
					<th scope="col">Assigned to</th>
				</TR>
				
				
					<%for(TasksTableInfo task:assignedList){ %>
					<TR>
					<td><%=task.getTask_id()%></td>
					<td><%=task.getTask_name()%></td>
					<td><%=task.getDesc()%></td>
					<td><%=task.getTask_date()%></td>
					<td><%=task.getStatus()%></td>
					<td><%=task.getAssignto()%></td>
					</TR>
					<% }%>
					
				</TABLE>
				<%} else{%>
				You have no assigned tasks to be displayed
				<%} %>
				
			</div>
		</div>	
		</div>
            </div>
          </div>
          <!--  My Task ends -->
        
<!-- @Suvarna Contacts starting -->
          <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="contacts">
              <h3 class="panel-title">
                Contacts
              </h3>
            </div>
            <div class="parent-tab">
              <ul class="nav nav-tabs mt5 ml3 touch-tabs box-tab" role="tablist">
  					    <li role="presentation" ><a href="#tabContacts" aria-controls="profile" role="tab" data-toggle="tab">Contacts</a></li>
  					    <li role="presentation"><a href="#tabCreateContact" aria-controls="profile" role="tab" data-toggle="tab">Create contact</a></li>
  					    </ul>
            </div>
					  <div class="tab-content">
						
					    <div role="tabpanel" class="tab-pane fade in" id="tabCreateContact">
					    	<div class="panel-body withtab">
              
         		
			<button type="button" value="linkedin" onclick="linkedInAuth()">LinkedIn </button> 
			<button onclick="auth()">GET google CONTACTS FEED</button>
			<%
			if(session.getAttribute("GoogleContacts")!=null){
			%>
<%-- 			<a href="#" data-toggle="modal" data-target="#addContactsModal" id="<%=listDisplayIndex%>"> --%>
											
<!-- 			</a> -->
			<%} %>
			<h6>Enter the following details</h6> 
			<fieldset>
			<form id="createContact" name="createContact" method="post" action="ContactsServlet">
				<TABLE style="border: none">
						
					<tr style="border: none">
			 			<td style="border: none">
			 			<label>Contact Name
				 		
			 			</label></td>
			 			<td style="border: none">
			 			<input type="text" name="contactName" id="contactName" required/></td>
				 </tr>
				 <tr style="border: none">
			 			<td style="border: none">
			 			<label>Contact number
				 		
			 			</label></td>
			 			<td style="border: none">
			 			<input type="text" name="contactNumber" id="contactNumber" required/></td>
				 </tr>
				  <tr style="border: none">
			 			<td style="border: none"><label>Email
			 			</label></td><td style="border: none">
			 			<input type="email" name="contactEmail" id="contactNumber" required/></td>
				
							<td>
							<button type="submit">Create</button> 
						</td>
					</tr>
					<tr>
						
					</tr>
				</TABLE>
				</form>
				</fieldset>
				</div>
				</div>
			<div role="tabpanel" class="tab-pane fade in  active" id="tabContacts">
			<div class="panel-body withtab">
<!-- 			<button type="button" value="linkedin" onclick="linkedInAuth()">LinkedIn </button>  -->
<!-- 			<button onclick="auth()">GET google CONTACTS FEED</button> -->
			
					<%if(request.getAttribute("ContactMessage")!=null) {%>
					<h6 style="color: green"><%=request.getAttribute("ContactMessage") %></h6>
					<% request.removeAttribute("ContactMessage");
			
					}%>
					<%
					if (session.getAttribute("contactsSet") != null && session.getAttribute("contactsSet") == "true") {
					ArrayList<UserContactsInfo> userContacts = (ArrayList<UserContactsInfo>)session.getAttribute("userContacts");
					if(userContacts!=null) {%>
					<TABLE BORDER="1">
					<TR>
						
						<TH>Name</TH>
						<TH>Email</TH>
						<TH>Phone</TH>
					</TR>
					<%
						for (int i = 0; i < userContacts.size(); i++) {
							UserContactsInfo contact = userContacts.get(i); %>
								<tr>
								<TD><%=contact.getContact_name()%></td>
								<TD><%=contact.getContact_email()%></td>
								<TD><%=contact.getContact_number()%></td>
								<TD><a
						href="ContactsServlet?removeContactSelected=yes&selectedContactemail=<%=contact.getContact_email() %>&selectedContactID=<%=contact.getContact_id() %>"
						alt="remove" class="confirmation"><img width="20" height="20"
							alt="remove" src="<%=request.getContextPath()%>/icons/delete-icon.png" /></a></TD>
					<TD><a
						href="ContactsServlet?InviteToFG=yes&selectedContactemail=<%=contact.getContact_email() %>"
						 alt="Invite to FG"><img width="20" height="20" alt="Invite to FG"
						src="<%=request.getContextPath()%>/icons/FindGoose_Invite.png" /></a></TD>
				
								</tr>
							<% }%>
					</TABLE>
							<%}%>
					<%} %>
		</div>
            </div>
          </div>
        </div>
        </div>
        <!-- Contacts ends -->

<!-- 	@My Events Begins  ------------------------------------- -->
        <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="myevents">
              <h3 class="panel-title">
                My Events
              </h3>
            </div>
            <div class="panel-body notab">
              <!--@Diana------If Events Selected-->
				<%if (session.getAttribute("eventsClicked") != null && session.getAttribute("eventsClicked") == "true") {%>
				<div class="spacer"></div>
				<div style="display: block" class="myform">
						<center>
						<h2>Events</h2>
						<form method="post" action="Events">
						<center>
							<table style="border: none">
								<tr>
									<td style="border: none">
										<input class="form-control" style="position: relative; vertical-align: top;"
													type="text" autocomplete="off" placeholder="Event Name"
												name="eventName" id="eventName" required> 
									</td>
									<td style="border: none">
		<!-- 								<input type="date" data-role="date" id="datepicker" name="datepicker" placeholder="Enter Event Date"> -->
										<input class="form-control" required type="text" id="datepicker" name="datepicker" placeholder="Event Date(yyyy-mm-dd)">
									</td>
									<td style="border: none">
										<input class="form-control" style="position: relative; vertical-align: top;"
													type="text" autocomplete="off" placeholder="Event URL"
													name="eventURL" id="eventURL" required> </td>
								</tr><tr>
									<td  colspan ="2" style="border: none">
										<div class="form-group">
										 <select class="form-control" id="locations" name="locations" required>
										<option value="" selected="selected">Event Location</option>
									  		<%List<Locations> locations = Locations.getListOfLocations();
									  		for(Locations location:locations){%> 
										  		<option value="<%=location.getLocation()%>"><%=location.getLocation() %></option>
											<%}%>
									  	</select>
		                            	</div>
									</td>				
									<td style="border: none">	
											<center><button class="btn btn-common " id="SaveEventButton" name="SaveEventButton" type="submit">Add Event</button></center>
									</td>
								</tr>
							</table>
						</center>
						</form>
						
						<%if(request.getAttribute("eventRemoved")!=null || request.getAttribute("eventRemoved")=="true") 
						{%>
							<p style="color: red">Event has been removed! :)</p>
							<%request.removeAttribute("eventRemoved");
						}%>
						<%if(request.getAttribute("eventsCreated")!=null || request.getAttribute("eventsCreated")=="true") 
						{%>
							<p style="color: green">Event has been added! :)</p>
							<%request.removeAttribute("eventsCreated");
						}%>				
						<%if(request.getAttribute("eventsCreationFailed")!=null) 
						{%>
							<p style="color: red"><%=request.getAttribute("FailedReason") %></p>
							<%request.removeAttribute("eventsCreationFailed");
							request.removeAttribute("FailedReason");
						}%>
						<% String notifiedEmail = (String) request.getAttribute("notifiedEmail");
						if(request.getAttribute("userNotifiedSuccess")!=null || request.getAttribute("userNotifiedSuccess")=="true") 
						{%>
							<p style="color: green">Event has been notified to <%=notifiedEmail%>! :)</p>
							<%request.removeAttribute("userNotifiedSuccess");
							request.removeAttribute("notifiedEmail");
						}
						else if(notifiedEmail!=null)
						{%>
							<p style="color: red">Please try notifying again! 
							<br>There seems to be a problem!
							<br>Check if you selecetd a team meber or added a friend's email before you tried to notify.
							</p>
							<%request.removeAttribute("userNotifiedSuccess");
							request.removeAttribute("notifiedEmail");
						}%>
					</center>
						<%
						List<EventsEnrolled> events = EventsService.getListOfEventsInDB();%>
								<br>
								<center>
								<TABLE class="listTable" cellspacing="0" summary="Events created and listsed in FindGoose">
								<caption></caption>
								<TR>
									<TH scope="col">EventName</TH>
									<TH scope="col">EventDate<br>(yyyy-mm-dd)</TH>
									<TH scope="col">Location</TH>
									<TH scope="col">URL</TH>
									<TH scope="col">notify</TH><!-- notify team member-->
									<TH scope="col"></TH><!-- remove -->
								</TR>
								<%for(int i =0; i<events.size();i++) {%>
								<TR>
									
									<TD><%=events.get(i).getEventName() %></TD>
									<%
									Date date = events.get(i).getEventDate();
								    Calendar cal = Calendar.getInstance();
								    cal.setTime(date);
								    String year = String.valueOf(cal.get(Calendar.YEAR));
								    String month = String.valueOf(cal.get(Calendar.MONTH)+1);
								    String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
								    String url = "http://"+events.get(i).getEventURL();
									%>
									<td><%=year + "-" + month+"-" + day%></td>
									<TD><%=events.get(i).getEventLocation() %></TD>
									<TD><a href="<%=url %>" target="_blank"><%=events.get(i).getEventURL() %></a></TD>
									<%EventsEnrolled eventEnrolled = (EventsEnrolled) events.get(i);
									session.setAttribute("selectedEventIdSessionAttr", eventEnrolled.getEventId());%>
									<TD>
										<a href="#notifyEventId2" data-toggle="modal"
												data-target="#notifyEvent2TeamModal" id="notifyModalId2"> 
										<img title="notify a team member" width="20" height="20" src="<%=request.getContextPath()%>/icons/notify-icon.png" /></a>
									</TD>
									<td>
										<%
										if(eventEnrolled.getEventCreatorEmail().matches("FindGoose") || !(eventEnrolled.getEventCreatorEmail().matches(user.getUser_email()))){;}
										else{%>									
										<a href="Events?removeEventSelected=yes&selectedEventID=<%=eventEnrolled.getEventId()%>" alt="remove" class="confirmation">
										<img width="20" height="20" src="<%=request.getContextPath()%>/icons/delete-icon.png" /></a>
										<%}%>
									</TD>
								</TR>
								<%} %>
								</TABLE>
								</centre>			
          </div><!-- class="myform" div ends -->
          <div id="notifyEvent2TeamModal" class="modal modal-common fade" role="dialog">					 
			<div class="modal-dialog">		
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Notify a team member or a friend on this event.</h4>
					</div>
					<div class="modal-body">
						<form id="notifyEventForm" name="notifyEventForm" method="get" action="NotifyEvent">
							<div align="center" id="notifyEvent2TeamMemberDivId" style="display: block" class="notifyEvent2TeamMemberDivId">
								<%UserTeamTableInfo userTeamTableInfo = myTeam_DButil.getUserTeam(user.getUser_email());
								String[] teamMembers = (String[]) userTeamTableInfo.getTeam_members().split(",");
								String strID = "";%>
								<TABLE style="border: none">
								<tr><td style="border: none"><center>
									<%if(teamMembers!=null && !userTeamTableInfo.getTeam_members().toString().isEmpty())
									{ %>
										<input class="teamRadio" type="radio" name="teamRadio" id="teamRadio" checked="checked"/></center></td>
									<%} %>
								<td style="border: none"></td>
								<td style="border: none"><center><input class="friendRadio" type="radio" name="friendRadio" id="friendRadio"/></center></td>
								</tr>
								<TR>
									<%if(teamMembers!=null && !userTeamTableInfo.getTeam_members().toString().isEmpty())
									{ %>
										<td style="border: none"><center>
										<select required class="form-control" placeholder="select a team member" id="teamMembersDropdownEvents" name="teamMembersDropdownEvents">
											<%for (int i = 0; i < teamMembers.length; i++) {%>
												<option id="<%=i%>" name="<%=i%>" value="<%=teamMembers[i]%>">
													<%=teamMembers[i]%>
												</option>
											<%}%>
										</select></center></td>
									<%}else{%>
										<td style="border: none">
											<p style="color: green">You have no</br> team to notify.</br></p>
										</td>
									<%}%>
									<td style="border: none"><center>(or)</center></td>
									<td style="border: none"><center>
										<%if(teamMembers!=null && !userTeamTableInfo.getTeam_members().toString().isEmpty())
										{ %>
											<input class="form-control" required placeholder="Enter email to notify" id="friendEmail" name="friendEmail" type="email" disabled="disabled"/></center>
										<%}else{ %>
											<input class="form-control" required placeholder="Enter email to notify" id="friendEmail" name="friendEmail" type="text"/></center>
										<%} %>
									</td>
								</tr>
								<tr><td colspan="3" style="border: none"></td></tr>
								<tr><td colspan="3" style="border: none">
									<input class="form-control" style="position: relative; vertical-align: top;"
														id="eventCommentTextAreaId" name="eventCommentTextAreaName"
														spellcheck="false" autocomplete="off" width="30"
														placeholder="Comment on this event."
														class="omni-search form-control tt-input" type="text" required/></td>
								</tr>
								<tr><td style="border: none">
									<button class="btn-submit" type="submit">Notify</button></center>
								</td></tr>
								</TABLE>
							</div>								
						</form>
					</div>
				</div>
			</div>
		</div><!-- 		Notify team Event module ends-->	
		<%}%>              
            
        </div><!-- <div class="panel-body notab"> ends -->
        </div>
        </div>
<!--    @My Events Ends   --------------------------------------- -->

        
        <!-- @Suvarna Starting of invite other module -->
        <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="inviteothers">
              <h3 class="panel-title">
                Invite to FindGoose
              </h3>
            </div>
            <div class="panel-body notab">
              <strong>Invite Others </strong>
		<div class="spacer"></div>
		<div class="InviteOthers" style="display: inline-block">
		<%
		if(request.getAttribute("UserInvited")!=null && request.getAttribute("UserInvited")=="yes"){%>
			Invitation has been sent to <%=request.getAttribute("inviteEmail") %>
			<% request.removeAttribute("UserInvited");
			   request.removeAttribute("inviteEmail");
		
		}
		else if(request.getAttribute("UserInvited")!=null && request.getAttribute("UserInvited")=="no"){%>
		<%=request.getAttribute("inviteEmail") %> is already a member of FindGoose
		<% request.removeAttribute("UserInvited");
		   request.removeAttribute("inviteEmail");
	
		}%>
			<form id="form" class="submitForm" name="form"
				method="post" action="InviteOthersServlet">
				<table border="0">
				<tr><td><input type="email" size="50" name="inviteEmail" id="inviteEmail"
					style="display: inline" placeholder="Enter Email id"></td>
				<td><input type="submit" class="btn btn-submit" style="display: inline" value="Invite to FG">
				</td>
			    </tr></table>
			 </form>

		</div>
            </div>
          </div> <!--  panel panel-default panel-common -->
        </div> <!-- col-sm-6 col-md-4 -->
        <!-- Invite module ends -->
        
        
        



          <!-- @Suvarna starting of news & Interests module -->
        <div class="col-sm-6 col-md-4">
          <div class="panel panel-default panel-common">
            <div class="panel-heading expand-box" data-leftmenu="manageinterests">
              <h3 class="panel-title">
                News & Interests
              </h3>
            </div>
            <div class="panel-body">
              
              			<form id="detailedNews" method="post" action="InterestsServlet">

				<input type="hidden" name="companyText" id="companyText"
					value="google" />

				<div class="news">
			
					<ul>
						
							<li><a title="click for company details"
								href="InterestsServlet?company=Google" data-toggle="modal">Google
							</a>Reports 13 Near-Miss Incidents With Self-Driving Cars<br>
						</li>
						
							<li>Former Executive on <a title="click for company details"
								href="InterestsServlet?company=IBM" data-toggle="modal">IBM's</a>
								Watson to Start Own AI Firm
						</li>
							<li>Crossing borders: <a title="click for company details"
								href="InterestsServlet?company=Uber" data-toggle="modal">Uber</a>
								takes San Diego riders to Mexico <a
								href="http://www.sandiegouniontribune.com/news/2016/mar/17/uber-passport-san-diego-border/"
								target="new">read more..</a>
						</li>
						<li><a title="click for company details"
								href="InterestsServlet?company=Microsoft" data-toggle="modal">Microsoft</a> Stock Gets Thumbs Up From Oppenheimer
						<a
							href="http://www.investors.com/news/technology/click/microsoft-stock-gets-thumbs-up-from-oppenheimer/"></a>
						</li>
					</ul>
				</div>

			</form>
            </div>
          </div>
        </div>
        <!--  News and Interests ends -->
      
	  </div><!-- -----containment-wrapper ENDS ------- -->
      <%}%> <!--(if Searchresults==null || Ends------) -->
      
      <!--@Diana------If "Company/Investor" Selected in the Basic Search Tab-->      
      <%if (request.getAttribute("SearchPerformed") != null && request.getAttribute("SearchPerformed") == "true") 
      {	request.removeAttribute("SearchPerformed");%>
			<div id="containment-wrapper" style="display: block">
		          	<div class="panel panel-default panel-common">
		          	
			            <div class="panel-heading expand-box" data-leftmenu="hiddenSearchMenu">
			              <h3 class="panel-title">
			                Search Results</h3>
			            </div>
						<div class="spacer"></div>
						<form method="post" action="ExportSelectedList">
						<table>
						<tr><td style="border: none">
							<button id="ExportListButton" name="ExportListButton" class="btn btn-common" type="submit">
								Export selected Companies</button>
							<br> <input name="hiddenSelectedCompanies4Export" type="hidden">
						</td>
						<td style="border: none">
							<%if (request.getAttribute("isListNotSelected4Export") != null) {%>
								<p style="color: red">Please select a list of companies to Export</p>
								<%request.removeAttribute("isListNotSelected4Export");
							}%>
						</td></tr></table>
						</form>
						<form method="post" action="MyLists">	
						<Table>
						<tr><td style="border: none">					
							<input class="form-control" style="position: relative; vertical-align: top;"
							type="text" autocomplete="off" placeholder="Enter List Name(optional)"
							name="userListName"></td><td style="border: none">
							<button id="SaveListButton" name="SaveListButton" class="btn btn-common" type="submit">Save List</button>
							<input name="hiddenSelectedCompanies4SaveAsList" type="hidden">
						</td></tr>
						<tr><td style="border: none">
							<%if (request.getAttribute("userListSaved") != null) {%>
								<p style="color: green">List Saved for user! :)</p>
								<%request.removeAttribute("userListSaved");
							}
							if (request.getAttribute("userListLimitExceeds") != null && request.getAttribute("userListLimitExceeds") == "true") {%>
								<p style="color: green">Please upgrade to save more than 2 lists.</p>
								<%request.removeAttribute("userListLimitExceeds");
							}%>
							<%if (request.getAttribute("isListNotSelected") != null) {%>
								<p style="color: red">Please select a list of companies to Save.</p>
								<%request.removeAttribute("isListNotSelected");
							}%>
						</td></tr>
						</table>
			            <div class="panel-body notab no-copy">										 
								<TABLE class="listTable" cellspacing="0" summary="Select companies to Save or Export">
									<TR>
										<TH scope="col"></TH>
										<TH scope="col">Name</TH>
										<TH scope="col">Location</TH>
										<TH scope="col">Industry</TH>
										<TH scope="col">HeadCount</TH>
										<TH scope="col">Revenue</TH>
										<TH scope="col">Investors</TH>
									</TR>
									<%
										List<CompanyInfo> companyNames = (List<CompanyInfo>) request.getAttribute("companybasicsearchResults");
										for(int i=0;i<companyNames.size();i++)
										{%>
											<TR style="cursor: pointer;">
												<TD><input type="checkbox" name="check[]" value="searchResults"></TD> 
												<TD><a href="CompanyProfile?selectedCompanyName=<%=companyNames.get(i).getName()%>&selectedCompanyCID=<%=companyNames.get(i).getCid()%>">
														<%=companyNames.get(i).getName()%></a></td>
												<TD><%=companyNames.get(i).getLocation()%></td>
												<TD><%=companyNames.get(i).getIndustry()%></td>
												<td><%=companyNames.get(i).getHeadcount()%></td>
												<TD><%=companyNames.get(i).getRevenue()%></td>
												<TD>
													<%String[] investors = companyNames.get(i).getInvestors().split(";");
			 										for (String investor : investors) 
		 											{%>  
														<a href="InvestorProfile?selectedInvestorName=<%=investor%>"><%=investor%></a>; 
													<%}%>
												</TD>
											</TR>
										<%}%>
										<%session.setAttribute("searchResultTable", companyNames);%>
<%-- 									<%}%> <!-- if(request.getAttribute("companybasicsearchResults")!=null)  --> --%>
								</TABLE>
<%-- 							<%}%> --%>
						</div>						
					</form>
					</div>				
			</div>
	  <%}else if(request.getAttribute("SearchReturnedNull") !=null && request.getAttribute("SearchReturnedNull") == "true")
	  {	request.removeAttribute("SearchReturnedNull");%>
				<div id="containment-wrapper" style="display: block">
		          	<div class="panel panel-default panel-common">
		          	
			            <div class="panel-heading expand-box" data-leftmenu="hiddenSearchMenu">
			            	<a href="FindGoose_Dashboard.jsp">Back to Dashboard</a>
			            	<br>
			              <h3 class="panel-title">
			                Search Results
			              </h3>
			            </div>
			            <div class="panel-body notab">
							<TABLE  cellspacing="0">
							<TR>
								<TD><p style="">
										<b>Oops!</b> No results on this search. <br>							
										<a href="Findgoose_NewCompRegistration.jsp" title="Invite others">Register</a> your company (or) yourself as an investor so we could help you invest.<br>
										Or <a href="InviteOthersServlet?InviteOthersClicked=true" title="Invite others">
											Invite the Company/Investor </a>to FindGoose :).<br>
			
									</p></TD>
							</TR>
							</TABLE>
						</div>
					</div>
			</div>
	  <%}%><!--   ENDS    if -else of (request.getAttribute("SearchPerformed") != null)-->

		<!--@Diana------If "Company Profile" Selected-->
		<%if (request.getAttribute("CompanyProfileSelected") == "true") 
		{	request.removeAttribute("CompanyProfileSelected");%>
			<div id="containment-wrapper" style="display: block">
		    	<div class="panel panel-default panel-common">
		         	<div class="panel-heading expand-box" data-leftmenu="hiddenSearchMenu">
						<%
// 						Document selCompDocument = (Document) request.getAttribute("selectedCompDocument");						
						CompanyInfo companyInfo4SelectedCompanyName = (CompanyInfo)request.getAttribute("companyInfo4SelectedCompanyName");
						%>
			              <h3 class="panel-title">Profile of Company : <b><%=companyInfo4SelectedCompanyName.getName()%></b></h3>
						<h1></h1>
			        </div>
			        <div class="panel-body notab">
						<%if ((companyInfo4SelectedCompanyName.getCrunchbaseLink().length() > 0)) {;%><%-- 	${pageContext.request.contextPath} --%>
							<a href="<%=companyInfo4SelectedCompanyName.getCrunchbaseLink()%>" target="_blank">
								<img width="20" height="20" src="<%=request.getContextPath()%>/icons/socialProfileIcons/crunchbase-icon.png" />
							</a>
						<%}
						if ((companyInfo4SelectedCompanyName.getTwitterLink().length() > 0)) {;%>
							<a href="<%=companyInfo4SelectedCompanyName.getTwitterLink()%>" target="_blank">
								<img width="20" height="20" src="<%=request.getContextPath()%>/icons/socialProfileIcons/twitter-icon.png" />
							</a>
						<%}
						if ((companyInfo4SelectedCompanyName.getAngelLink().length() > 0)) {;%>
							<a href="<%=companyInfo4SelectedCompanyName.getAngelLink()%>" target="_blank">
								<img width="20" height="20" src="<%=request.getContextPath()%>/icons/socialProfileIcons/Angellist-icon.png" />
							</a>
						<%}
						if ((companyInfo4SelectedCompanyName.getLinkedinLink().length() > 0)) { ;%>
							<a href="<%=companyInfo4SelectedCompanyName.getLinkedinLink()%>" target="_blank">
								<img width="20" height="20" src="<%=request.getContextPath()%>/icons/socialProfileIcons/Linkedin-icon.png" />
							</a>
						<%}
						if ((companyInfo4SelectedCompanyName.getBlogLink().length() > 0)) {;%>
							<a href="<%=companyInfo4SelectedCompanyName.getBlogLink()%>" target="_blank">blog</a>
						<%}%>
						<h1></h1>
						<%--   <h1><%=selCompDocument.get("cid")%></h1> --%>
						<br>
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#home">About</a></li>
<%-- 							<%if (request.getAttribute("employeesOfSelCompNotNull") != null) {%> --%>
								<%if (request.getAttribute("Employees4companyInfoSelectedIsNotnull") != null) {%>
								<li><a data-toggle="tab" href="#menu1">Team</a></li>
							<%}
							if (request.getAttribute("InvestmentsOfSelCompNotNull") != null) { %>
								<li><a data-toggle="tab" href="#menu2">Investments</a></li>
							<%}%>
							<li><a data-toggle="tab" href="#menu3">Updates</a></li>
							<li><a data-toggle="tab" href="#menu4">Competitors</a></li>
							<li><a data-toggle="tab" href="#menu5">Compare reports</a></li>
						</ul>
						<div class="tab-content">
							<div id="home" class="tab-pane fade in active">
							<div class="spacer"></div>
								<table style="border: none">
									<%if ((companyInfo4SelectedCompanyName.getLocation().length() > 0)) {;%>
									<TR>
										<TD style="border:none"></TD>
										<TD style="border:none"><label>Location: </label></td>
										<td style="border:none"><%=companyInfo4SelectedCompanyName.getLocation()%></td>
									</tr>
									<%}
									if ((companyInfo4SelectedCompanyName.getIndustry().length() > 0)) {;%>
									<TR>
										<TD style="border:none"></TD>
										<TD style="border:none"><label>Industry: </label></td>
										<td style="border:none"><%=companyInfo4SelectedCompanyName.getIndustry()%></td>
									</tr>
									<%}
									if ((companyInfo4SelectedCompanyName.getHeadcount()> 0)) {;%>
									<TR>
										<TD style="border:none"></TD>
										<TD style="border:none"><label>Headcount : </label></td>
										<td style="border:none"><%=companyInfo4SelectedCompanyName.getHeadcount()%></td>
									</tr>
									<%}
									if (companyInfo4SelectedCompanyName.getRevenue().length() > 0) { ;%>
									<TR>
										<TD style="border:none"></TD>
										<TD style="border:none"><label>Revenue : </label></td>
										<td style="border:none"><%=companyInfo4SelectedCompanyName.getRevenue()%></td>
									</tr>
									<%}
									if ((companyInfo4SelectedCompanyName.getInvestors().length() > 0)) { ;%>
									<TR>
										<TD style="border:none"></TD>
										<TD style="border:none"><label>Investors : </label></td>
										<%-- 							<td><%= selCompDocument.get("investors")%></td> --%>
										<TD style="border:none">
											<% String[] investors = companyInfo4SelectedCompanyName.getInvestors().split(";");
											for (String investor : investors) {%> 
												<a href="InvestorProfile?selectedInvestorName=<%=investor%>"><%=investor%></a>; 
											<%}%>
										</TD>
									</tr>
									<%}%>
								</table>
							<div class="spacer"></div>
							</div>
							<div id="menu1" class="tab-pane fade">
								<table style="border: none">
									<%
// 									Document[] doc = SearchUtil.getDoc();
// 									for (int i = 0; i < SearchUtil.getHit().length; i++) {
									if(request.getAttribute("EmployeesList4companyInfoSelected")!=null)
									{
										List<CompanyEmployeesInfo> employees = (List<CompanyEmployeesInfo>) request.getAttribute("EmployeesList4companyInfoSelected");
											for(int i=0; i<employees.size();i++){
										%>
										<TR>
											<TD style="border:none"></TD>
											<TD style="border:none">- 
												<%if ((employees.get(i).getLinkedin().length() > 0)) {%> 
												<a href="<%=employees.get(i).getLinkedin()%>" target="_blank"><img
													width="20" height="20"
													src="<%=request.getContextPath()%>/icons/socialProfileIcons/Linkedin-icon.png" /></a>
												<%}%> 
												<%=employees.get(i).getName()%> (<%=employees.get(i).getDesignation()%>)
											</td>
										</TR>
										<tr><td colspan="3" style="border:none"></td></tr>
										<%}%>
									<%}%> <!--if(request.getAttribute("EmployeesList4companyInfoSelected")!=null) END -->
								</table>
							</div>
							<div id="menu2" class="tab-pane fade">
<!-- 								<h3>Investments</h3> -->
								<div class="spacer"></div>
								<TABLE class="listTable" cellspacing="0" summary="List of Investments">
<!-- 									<caption>Investments</caption> -->
									<TR>
										<TH scope="col">Invested Company</TH>
										<TH scope="col">Amount</TH>
										<TH scope="col">Round</TH>
										<TH scope="col">Investor Type</TH>
									</TR>
									<% 
//										Document[] docInvestments = SearchUtil.getInvestmentsDoc();
//										for (int i = 0; i < SearchUtil.getHitInvestments().length; i++) {
										if(request.getAttribute("Investments4companyInfoSelected")!=null)
										{
											List<InvestmentInfo4ESIndexing> investments = (List<InvestmentInfo4ESIndexing>) request.getAttribute("Investments4companyInfoSelected");
											for(int i=0; i<investments.size();i++)
											{%>
											<TR>
												<TD><%=investments.get(i).getInvestee_name()%></TD>
												<TD><%=investments.get(i).getAmount()%></td>
												<TD><%=investments.get(i).getRound()%></td>
												<TD><%=investments.get(i).getInvestor_type()%></td>
											</TR>
											<%}%>
										<%}%><!-- if(request.getAttribute("Investments4companyInfoSelected")!=null) ENDS -->
								</TABLE>
								<div class="spacer"></div>
							</div>
							<div id="menu3" class="tab-pane fade">
<!-- 								<h3>NEWS</h3> -->
								<div class="spacer"></div>
								<p>news text.</p>
								<div class="spacer"></div>
							</div>
							<div id="menu4" class="tab-pane fade">
							<br>
							<div class="spacer"></div>
											<button id="disable"><b>"BASIC User"</b></button>
											<button id="enable">"FG+ / Investor / Start-up Claimer"</button>
								<div class="spacer"></div>
								<div class="demo" disable="true">
								<table style="border: none" class="demo" disable="true" >						
									<TR>
										<TD style="border:none">Competitors</TD>
										<td style="border:none">
											<a class="demo" href="http://www.findgoose.com">FindGoose</a><br>
											<!--<button class="demo">Click me</button><br> -->
											<textarea class="demo">Some text</textarea><br>
											<div>Some widget</div>
										</td>							
									</TR>
								</table>
								</div>
								<div class="spacer"></div>
							</div>
							<div id="menu5" class="tab-pane fade" >
								<div class="spacer"></div>
								<table style="border: none">						
									<TR>
										<TD style="border:none">Compare Reports</TD>							
									</TR>
								</table>
							</div>
					</div>
			</div>
		</div>
		</div>
		<%}%><!-- if (request.getAttribute("CompanyProfileSelected") == "true") -->

		<!--@Diana------If "Investor Profile" Selected-->
		<% if (request.getAttribute("InvestorProfileSelected") == "true") {
				request.removeAttribute("InvestorProfileSelected");%>
<!-- 		<div style="display: block" class="container"> -->
			<div id="containment-wrapper" style="display: block">
		    	<div class="panel panel-default panel-common">
		         	<div class="panel-heading expand-box" data-leftmenu="hiddenSearchMenu">
						<%
// 						Document selInvestorDocument = (Document) request.getAttribute("selectedInvestorDocument");
						InvestorInfo investorSelected = (InvestorInfo) request.getAttribute("selectedInvestorInfoProfile");
						%>
			            <h3 class="panel-title">Profile of Investor : <b><%=investorSelected.getInvestor_name()%></b></h3>
			        </div>
			        <div class="panel-body notab">
			        	<div class="spacer"></div>
						<div class="tab-content">
							<div class="spacer"></div>
							<table style="border: none">
								<% if ((investorSelected.getInvestor_name().length() > 0)) { ;%>
								<TR>
									<TD style="border:none"></TD>
									<TD style="border:none"><label>Investor Name: </label></td>
									<td style="border:none"><%=investorSelected.getInvestor_name()%></td>
								</tr>
								<%}if ((investorSelected.getInvestedCompanies().length() > 0)) { ;%>
								<TR>
									<TD style="border:none"></TD>
									<TD style="border:none"><label>Invested Companies: </label></td>
									<%--<td><%= selInvestorDocument.get("investedCompanies")%></td> --%>
									<TD style="border:none">
										<%
											String[] companies = investorSelected.getInvestedCompanies().split(";");
													for (String company : companies) {
// 														Document companyDoc = SearchUtil.getDocumentForSelectedCompany(company);
// 														String companyCID = companyDoc.get("cid");
										%> <a href="CompanyProfile?selectedCompanyName=<%=company%>"><%=company%></a>
										; <%}%>
									</TD>
								</tr>
								<%}if ((investorSelected.getInvestorType().length() > 0)) { ;%>
								<TR>
									<TD style="border:none"></TD>
									<TD style="border:none"><label>Investor Type : </label></td>
									<td style="border:none"><%=investorSelected.getInvestorType()%></td>
								</tr>
								<%}%>
							</table>
							<div class="spacer"></div>
						</div>
				</div>
				</div>
			</div>
			<%} %><!-- if (request.getAttribute("InvestorProfileSelected") == "true") -->
      <!-- //footer section -->
      <footer>
        <div class="row">
          <div class="col-sm-12">
            <p>
              &copy; 2016 XYZ, All Rights Reserved.
            </p>
          </div>
        </div>
      </footer>

    </div><!-- ENDS <div class="right-section-inner"> -->
  </div><!-- ENDS <div class="right-section"> -->
</section>

<!-- @Suvarna Google contacts Modal opens -->
<!-- AddContacts Modal begins -->
							

<div class="modal modal-common fade" id="changePwdModal" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Google Imported Contacts</h4>
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
<!-- AddContacts Modal ends -->

<div class="modal fade" id="videomodal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Virtual Tour Video</h4>
      </div>
      <div class="modal-body">
        <video src="FindGoose_DashboardFiles/video/FindGooseHostingVideo.mp4" alt="" width="100%" controls> </video>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<script src="FindGoose_DashboardFiles/js/jquery.min.js"></script>
<script src="FindGoose_DashboardFiles/js/jquery-ui.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/1_12_0_jquery.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.pack.js"></script>
<script src="FindGoose_DashboardFiles/js/bootstrap.min.js"></script>
<script src="FindGoose_DashboardFiles/js/common.js"></script>

</body>
</html>
